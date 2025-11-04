package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ5626 {
    static int n;
    static int[] arr;
    static int[] height;

    static int[][] dp;

    private static int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        if ((arr[0] != -1 && arr[0] != 0) || (arr[n - 1] != -1 && arr[n - 1] != 0)) {
            System.out.println(0);
            return;
        }

        height = new int[n];
        for (int i = 0; i < n; i++) {
            height[i] = Math.min(i, n - 1 - i);
            if (arr[i] != -1 && (arr[i] > height[i])) {
                System.out.println(0);
                return;
            }
        }

        dp = new int[n][n / 2 + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(dfs(n - 1, 0));
    }

    private static int dfs(int index, int h) {
        if (index == 0) {
            return h == 0 ? 1 : 0;
        }

        if (h < 0 || h > height[index]) {
            return 0;
        }

        if (arr[index] != -1 && arr[index] != h) {
            return 0;
        }

        if (dp[index][h] != -1) {
            return dp[index][h];
        }

        int res = 0;

        res += dfs(index - 1, h);
        res %= MOD;
        res += dfs(index - 1, h - 1);
        res %= MOD;
        res += dfs(index - 1, h + 1);
        res %= MOD;

        dp[index][h] = res;
        return dp[index][h];
    }
}
