package week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시간 제한: 2초
 * 1. 이 성에 있는 방의 개수
 * 2. 가장 넓은 방의 넓이
 * => bfs
 *
 * 3. 하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기
 * - 2중 for loop을 돌며 각 칸에서 왼쪽, 아래, 오른쪽 벽의 유무를 확인
 * - 가장 왼쪽, 아래, 오른쪽은 각각 왼쪽, 아래, 오른쪽 벽에 대해서는 무시한다.
 * - 벽을 제거할 때 마다 bfs()
 */
public class BOJ2234 {

    static int m, n; // 가로, 세로
    static int[][] arr;
    static boolean[][] visit;

    static int[] answer;

    // 상 하 좌 우
    static int[] wall = {2, 8, 1, 4};
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        answer = new int[3];
        answer[1] = Integer.MIN_VALUE;
        answer[2] = Integer.MIN_VALUE;

        visit = new boolean[n][m];

        // 1, 2 조회
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visit[i][j]) {
                    answer[1] = Math.max(answer[1], bfs(i, j)); // 가장 넓은 방의 넓이
                    answer[0]++;    // 방의 개수
                }
            }
        }

        // 3
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == n - 1) {
                    for (int k = 0; k < 4; k++) {
                        if (k == 1) {
                            continue;
                        }

                        if ((arr[i][j] & wall[k]) > 0) {
                            int tmp = arr[i][j];
                            arr[i][j] = arr[i][j] & ~(wall[k]);
                            visit = new boolean[n][m];
                            answer[2] = Math.max(answer[2], bfs(i, j));
                            arr[i][j] = tmp;
                        }
                    }
                } else if (j == 0) {
                    for (int k = 0; k < 4; k++) {
                        if (k == 2) {
                            continue;
                        }

                        if ((arr[i][j] & wall[k]) > 0) {
                            int tmp = arr[i][j];
                            arr[i][j] = arr[i][j] & ~(wall[k]);
                            visit = new boolean[n][m];
                            answer[2] = Math.max(answer[2], bfs(i, j));
                            arr[i][j] = tmp;
                        }
                    }
                } else if (j == m - 1) {
                    for (int k = 0; k < 4; k++) {
                        if (k == 3) {
                            continue;
                        }

                        if ((arr[i][j] & wall[k]) > 0) {
                            int tmp = arr[i][j];
                            arr[i][j] = arr[i][j] & ~(wall[k]);
                            visit = new boolean[n][m];
                            answer[2] = Math.max(answer[2], bfs(i, j));
                            arr[i][j] = tmp;
                        }
                    }
                } else {
                    for (int k = 0; k < 4; k++) {
                        if ((arr[i][j] & wall[k]) > 0) {
                            int tmp = arr[i][j];
                            arr[i][j] = arr[i][j] & ~(wall[k]);
                            visit = new boolean[n][m];
                            answer[2] = Math.max(answer[2], bfs(i, j));
                            arr[i][j] = tmp;
                        }
                    }
                }
            }
        }


        System.out.println(answer[0]);
        System.out.println(answer[1]);
        System.out.println(answer[2]);
    }

    private static int bfs(int y, int x) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(y, x));
        visit[y][x] = true;

        int size = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            size++;

            for (int i = 0; i < 4; i++) {
                // 벽이 있는 경우
                if ((arr[node.y][node.x] & wall[i]) > 0) {
                    continue;
                }

                // 다음 위치
                int ny = node.y + dy[i];
                int nx = node.x + dx[i];

                // 범위를 벗어났거나 이전에 탐색한 경우
                if (OOB(ny, nx) || visit[ny][nx]) {
                    continue;
                }

                visit[ny][nx] = true;
                queue.add(new Node(ny, nx));
            }
        }

        return size;
    }

    // 범위 검사 함수
    private static boolean OOB (int y, int x) {
        return y >= n || y < 0 || x >= m || x < 0;
    }

    private static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
