/*
 * 일요일 아침의 데이트
 * dfs로 모든 길에 다 구하면 터짐. 4 ^ 2500
 * 1. 쓰레기를 적게 지나는게 첫번째 조건이고 그 다음이 쓰레기옆을 적게 지나는 것임. 그래서 두 가지 조건으로 우선순위 큐(좌표, 지나간 쓰레기 구간, 스쳐간 쓰레기 구간)를 돌면됨.
 * 2. 쓰레기가 가장 적게, 같다면 쓰레기옆을 가장 적게 지나는 지점에서 4방향으로 이동함. -> 방문 처리는? 객체로 관리해서 방문 처리? -> 2500 * 2byte = 5kb, 128m / 5k = 25k 망함
 * 고려할점
 * 1. 우선순위 큐로 먼저 방문한 곳은 무조건 최적의 경로인가? -> 최적임
 * 2. 우선순위 큐로 방문하면 여러가지 길을 산발적으로 방문하는 데 이때 방문처리는???
 * 3. 
 */
package week14;

import java.io.*;
import java.util.*;

public class BOJ1445 {
    static int n, m;
    static char[][] map;
    static int[] start = new int[2];
    static boolean[][] visited;
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new char[n][m];
        visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'S') {
                    start[0] = i; 
                    start[1] = j;
                }
            }
        }
        int[] di = {-1, 0, 1, 0};
        int[] dj = {0, 1, 0, -1};
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            else return a[1] - b[1];
        });
        pq.offer(new int[]{0, 0, start[0], start[1]});
        visited[start[0]][start[1]] = true;
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int invitedTrashNumber = cur[0];
            int passedTrashNumber = cur[1];
            int curI = cur[2];
            int curJ = cur[3];
            for (int d = 0; d < 4; d++) {
                int nextI = curI + di[d];
                int nextJ = curJ + dj[d];
                if (!check(nextI, nextJ)) continue;
                if (visited[nextI][nextJ]) continue;
                visited[nextI][nextJ] = true;
                if (map[nextI][nextJ] == '.') {
                    if (passingTrash(nextI, nextJ)) {
                        pq.offer(new int[]{invitedTrashNumber, passedTrashNumber+1, nextI, nextJ});
                    } else {
                        pq.offer(new int[]{invitedTrashNumber, passedTrashNumber, nextI, nextJ});
                    }
                } else if (map[nextI][nextJ] == 'g') {
                    pq.offer(new int[]{invitedTrashNumber+1, passedTrashNumber, nextI, nextJ});
                } else if (map[nextI][nextJ] == 'F') {
                    String answer = Integer.toString(invitedTrashNumber) + " ";
                    answer += Integer.toString(passedTrashNumber);
                    System.out.println(answer);
                    return;
                }
                
            }
        }
    }

    public static boolean check(int a, int b) {
        if (a < 0 || a >= n) return false;
        if (b < 0 || b >= m) return false;
        return true;
    }

    public static boolean passingTrash(int a, int b) {
        int[] di = {-1, 0, 1, 0};
        int[] dj = {0, 1, 0, -1};
        for (int d = 0; d < 4; d++) {
            int nextA = a + di[d];
            int nextB = b + dj[d];
            if (!check(nextA, nextB)) continue;
            if (map[nextA][nextB] == 'g') return true;
        }
        return false;
    }
}
