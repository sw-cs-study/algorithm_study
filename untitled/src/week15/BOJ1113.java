package week15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1113 {

    static int n, m;
    static int[][] arr;
    static boolean[][] visit;

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 높이 낮은게 우선
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (n1, n2) -> Integer.compare(n1.value, n2.value)
        );

        arr = new int[n][m];
        visit = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            String row = bf.readLine();
            for (int j = 0; j < m; j++) {
                arr[i][j] = Character.getNumericValue(row.charAt(j));

                // 경계 설정
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    pq.add(new Node(i, j, arr[i][j]));
                    visit[i][j] = true;
                }
            }
        }

        int answer = 0;
        while (!pq.isEmpty()) {
            Node now = pq.poll();

            for (int i = 0; i < 4; i++) {
                int ny = now.y + dy[i];
                int nx = now.x + dx[i];

                if (OOB(ny, nx) || visit[ny][nx]) {
                    continue;
                }

                // 물을 채울 수 있는 경우
                if (arr[ny][nx] < now.value) {
                    answer += (now.value - arr[ny][nx]);
                }

                pq.add(new Node(ny, nx, Math.max(arr[ny][nx], now.value)));
                visit[ny][nx] = true;
            }

        }

        System.out.println(answer);

    }

    private static boolean OOB(int y, int x) {
        return y >= n || y < 0 || x >= m || x < 0;
    }

    private static class Node {
        int y;
        int x;
        int value;

        public Node(int y, int x, int value) {
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
}
