/*
 * 퍼즐
 * 1. bfs로 접근?? => 9개의 상태를 어떻게 저장할지? 10진수 9자리(정수형 or 문자열) => 방문 처리는? 배열로 하면 낭비가 심해서(터짐, 10^10개) map으로? 10! = 10 * 9 * 8 * 7 * 720 = 10 * 504 * 720 은 대충 37 * 10^4
 */

package week15;

import java.io.*;
import java.util.*;

public class BOJ1525 {
    private static StringBuilder firstState;
    private static String answer = "123456780";
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        firstState = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                firstState.append(st.nextToken());
            }
        }
        System.out.println(bfs());
    }

    private static int bfs() {
        if (match(firstState.toString())) return 0;
        int move = 0;
        Queue<StringBuilder> queue = new LinkedList();
        HashMap<String, Boolean> visited = new HashMap<>();

        queue.offer(firstState);
        visited.put(firstState.toString(), true);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int t = 0; t < size; t++) {
                StringBuilder cur = queue.poll();
                int pos = findZeroPos(cur);
                for (int nPos : nextPositions(pos)) {
                    if (nPos == -1) continue;
                    StringBuilder nextState = new StringBuilder(cur.toString());
                    char adj = nextState.charAt(nPos);
                    nextState.setCharAt(pos, adj);
                    nextState.setCharAt(nPos, '0');
                    String convertedNextState = nextState.toString();
                    if (visited.containsKey(convertedNextState)) continue;
                    if (match(convertedNextState)) return move + 1;
                    visited.put(convertedNextState, true);
                    queue.offer(nextState);
                }
            }
            move++;
        }

        return -1;
    }

    private static int findZeroPos(StringBuilder state) {
        for (int i = 0; i < 10; i++) {
            if (state.charAt(i) == '0') {
                return i;
            }
        }
        return -1;
    }

    private static int[] nextPositions(int pos) {
        int[] arr = new int[4];
        int x, y, nx, ny, npos;
        x = pos / 3;
        y = pos % 3;
        for (int d = 0; d < 4; d++) {
            nx = x + dx[d];
            ny = y + dy[d];
            if (nx < 0 || nx >= 3 || ny < 0 || ny >= 3) {
                arr[d] = -1;
            } else {
                npos = nx * 3 + ny;
                arr[d] = npos;
            }
        }
        return arr;
    }

    private static boolean match(String state) {
        if (state.equals(answer)) return true;
        return false;
    }
}
