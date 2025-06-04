package week18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ12996 {

    static int S, A, B, C;
    static int[][][][] dp;
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        S = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        dp = new int[S + 1][A + 1][B + 1][C + 1];
        for (int i = 0; i < S + 1; i++) {
            for (int j = 0; j < A + 1; j++) {
                for (int k = 0; k < B + 1; k++) {
                    Arrays.fill(dp[i][j][k], -1);
                }
            }
        }

        System.out.println(recur(S, A, B, C));
    }

    private static int recur(int s, int a, int b, int c) {
        if (s == 0) {
            if (a == 0 && b == 0 && c == 0) {
                return 1;
            }

            return 0;
        }

        if (dp[s][a][b][c] != -1) {
            return dp[s][a][b][c];
        }

        int ret = 0;
        if (a > 0) {
            ret += recur(s - 1, a - 1, b, c);
            ret %= MOD;
        }

        if (a > 0 && b > 0) {
            ret += recur(s - 1, a - 1, b - 1, c);
            ret %= MOD;
        }

        if (a > 0 && c > 0) {
            ret += recur(s - 1, a - 1, b, c - 1);
            ret %= MOD;
        }

        if (b > 0) {
            ret += recur(s - 1, a, b - 1, c);
            ret %= MOD;
        }

        if (b > 0 && c > 0) {
            ret += recur(s - 1, a, b - 1, c - 1);
            ret %= MOD;
        }

        if (c > 0) {
            ret += recur(s - 1, a, b, c - 1);
            ret %= MOD;
        }

        if (a > 0 && b > 0 && c > 0) {
            ret += recur(s - 1, a - 1, b - 1, c - 1);
            ret %= MOD;
        }

        dp[s][a][b][c] = ret;
        return dp[s][a][b][c];
    }
}
