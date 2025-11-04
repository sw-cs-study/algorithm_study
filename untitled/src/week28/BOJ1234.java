package week28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1234 {

    static int level, red, green, blue;
    static long[][][][] dp;

    static long[] fact = new long[11];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        level = Integer.parseInt(st.nextToken());
        red = Integer.parseInt(st.nextToken());
        green = Integer.parseInt(st.nextToken());
        blue = Integer.parseInt(st.nextToken());

        dp = new long[level + 1][red + 1][green + 1][blue + 1];
        for (int i = 0; i < level + 1; i++) {
            for (int j = 0; j < red + 1; j++) {
                for (int k = 0; k < green + 1; k++) {
                    Arrays.fill(dp[i][j][k], -1);
                }
            }
        }

        fact[0] = 1;
        for (int i = 1; i < 11; i++) {
            fact[i] = fact[i - 1] * i;
        }

        System.out.println(recur(1, red, green, blue));
    }

    private static long recur(int l, int r, int g, int b) {
        if (r < 0 || g < 0 || b < 0) {
            return 0;
        }
        if (l == level + 1) {
            return 1;
        }

        if (dp[l][r][g][b] != -1) {
            return dp[l][r][g][b];
        }

        long res = 0;

        // 1색
        if (r >= l) {
            res += recur(l + 1, r - l, g, b);
        }
        if (g >= l) {
            res += recur(l + 1, r, g - l, b);
        }
        if (b >= l) {
            res += recur(l + 1, r, g, b - l);
        }

        // 2색 (레벨이 2로 나누어떨어져야 색을 고루 칠할 수 있음)
        if (l % 2 == 0) {
            int min = l / 2;    // 각 색이 가지고 있어야 하는 최소 수
            long nCk = nCk(l, l / 2);
            if (r >= min && g >= min) {
                res += nCk * recur(l + 1, r - min, g - min, b);
            }
            if (g >= min && b >= min) {
                res += nCk * recur(l + 1, r, g - min, b - min);
            }
            if (b >= min && r >= min) {
                res += nCk * recur(l + 1, r - min, g, b - min);
            }
        }

        // 3색
        if (l % 3 == 0) {
            int min = l / 3;
            long nCk = nCk(l, min) * nCk(l - min, min);
            if (r >= min && g >= min && b >= min) {
                res += nCk * recur(l + 1, r - min, g - min, b - min);
            }
        }

        dp[l][r][g][b] = res;
        return dp[l][r][g][b];
    }

    private static long nCk(int n, int k) {
        return fact[n] / (fact[k] * fact[n - k]);
    }
}
