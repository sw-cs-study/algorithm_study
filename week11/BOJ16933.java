/*
 * 16933. 벽 부수고 이동하기 3
 * bfs로 방문 처리하며 진행
 * 방문 처리는 낮밤 여부, 좌표, 부술 수 있는 벽의 갯수로 함. 그래서 시간복잡도는 2 * 10^6 * 10 = 2 * 10^7 임
 * -> 낮밤 여부는 방문 처리에서 할 필요가 없음. 밤일 때 벽을 만나면 방문 처리여부를 안따지고 큐에 넣으면 bfs로 자연스럽게 돔.
 * -> 부술 수 있는 벽의 갯수도 필요없음. 방문 배열의 값을 현재까지 몇개의 벽을 부수고 방문했는지를 저장하면 됨. 
 * ->-> 그 이유는 어떤 점을 벽을 많이 부수고 빨리 왔다면 다른 지점에 빨리 도달할 것이고, 어떤 점을 벽을 최대한 안부수고 왔다면 다른 지점에 느리게 도착할 것인데,
 * ->-> 뒤에 부술 벽이 더 이상 없으면 다행이지만, 그렇지 않다면 벽을 더 부술 수 있는 경우까지 bfs에서 처리함.
 * 틀린 부분
 * -> bfs 4방향 for문에서 각 방향에 대해 cur.rest를 바로 수정해서 다른 방향에 영향을 줌
 * -> 시작지점과 도착지점이 같은경우
 */

package week11;

import java.io.*;
import java.util.*;

public class BOJ16933 {
    private static int n, m, k;
    private static char[][] board;
    private static boolean[][][] visited; // 방문할때 기준
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    private static class Element {
        int x, y, day, rest, dist;
        public Element (int x, int y, int day, int rest, int dist) {
            this.x = x;
            this.y = y;
            this.day = day;
            this.rest = rest;
            this.dist = dist;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        visited = new boolean[n][m][k+1];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j);
            }
            
        }
        System.out.println(bfs());

    }

    private static boolean check(int x, int y) {
        if (x < 0 || x >= n) return false;
        if (y < 0 || y >= m) return false;
        return true;
    }

    private static int bfs() {
        if (n == 1 && m == 1) return 1;
        ArrayDeque<Element> queue = new ArrayDeque<>();
        queue.offer(new Element(0, 0, 1, k, 1));
        visited[0][0][k] = true;

        while (!queue.isEmpty()) {
            Element cur = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if (nx == n-1 && ny == m-1) { // 도착하면 return
                    return cur.dist+1;
                }
                if (!check(nx, ny)) continue;
                int nDay = (cur.day + 1) % 2;
                if (board[nx][ny] == '1') {
                    // wall
                    if (cur.rest == 0) continue;
                    if (cur.day == 1) {
                        // 낮이면 벽뚫고 가기
                        if (visited[nx][ny][cur.rest-1]) continue;
                        queue.offer(new Element(nx, ny, nDay, cur.rest-1, cur.dist+1));
                        visited[nx][ny][cur.rest-1] = true;
                    } else {
                        // 밤이면 원래 위치에서 하루 대기
                        queue.offer(new Element(cur.x, cur.y, nDay, cur.rest, cur.dist+1));
                        visited[cur.x][cur.y][cur.rest] = true;
                    }
                    
                } else {
                    // not wall
                    if (visited[nx][ny][cur.rest]) continue;
                    queue.offer(new Element(nx, ny, nDay, cur.rest, cur.dist+1));
                    visited[nx][ny][cur.rest] = true;
                }

            }
        }

        return -1;
    }


}
