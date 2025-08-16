package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16957 {

    static int r, c;

    static int[][] map;
    static int[][] balls;

    static Node[][] dp;

    // <- 부터 시게방향
    static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new int[r][c];
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < c; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        balls = new int[r][c];
        dp = new Node[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Node node = solve(i, j);
                balls[node.y][node.x]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(balls[i][j]).append(" ");
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }

    private static Node solve(int y, int x) {
        // 현재 위치에서 최종 공의 위치를 알고있는 경우
        if (dp[y][x] != null) {
            return dp[y][x];
        }

        int minY = y;
        int minX = x;
        int minValue = map[y][x];

        for (int i = 0; i < 8; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (OOB(ny, nx)) {
                continue;
            }

            // 공이 이동할 수 있는 경우
            if (map[ny][nx] < minValue) {
                minValue = map[ny][nx];
                minY = ny;
                minX = nx;
            }
        }

        if (minY == y && minX == x) {   // 이동 X
            dp[y][x] = new Node(y, x);
        } else {    // 이동
            Node next = solve(minY, minX);
            dp[y][x] = new Node(next.y, next.x);
        }

        return dp[y][x];
    }

    private static boolean OOB(int y, int x) {
        return y < 0 || y >= r || x < 0 || x >= c;
    }

    private static class Node {
        int y;
        int x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
