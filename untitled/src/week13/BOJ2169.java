package week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2169 {

    static int n, m;
    static int[][] arr;
    static int[][][] dp;

    // 아래쪽, 왼쪽, 오른쪽
    static int[] dy = {1, 0, 0};
    static int[] dx = {0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[n][m][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        System.out.println(recur(0, 0, 0));
    }

    // dir: 0(아래), 1(왼쪽), 2(오른쪽)
    private static int recur(int y, int x, int dir) {
        // 탐사 끝
        if (y == n - 1 && x == m - 1) {
            return arr[y][x];
        }

        // 이미 탐사한 곳이라면 이전에 계산한 가치 합 반환
        if (dp[y][x][dir] != -1) {
            return dp[y][x][dir];
        }

        dp[y][x][dir] = -100_000_000; // 입력 중 음수가 있음
        for (int i = 0; i < 3; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (OOB(ny, nx)) {
                continue;
            }

            // 오른쪽(왼쪽)으로 온 경우 다음 이동 지역이 왼쪽(오른쪽)일 수 없음
            if (dir == 2 && i == 1 || dir == 1 && i == 2) {
                continue;
            }

            dp[y][x][dir] = Math.max(dp[y][x][dir], recur(ny, nx, i) + arr[y][x]);
        }

        return dp[y][x][dir];
    }

    // 탐색 범위 체크
    private static boolean OOB(int y, int x) {
        return y >= n || y < 0 || x >= m || x < 0;
    }
}

