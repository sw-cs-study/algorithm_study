package week18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ11066 {

    static int k;
    static int[] files;
    static int[][] dp;
    static int[] prefixSum;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(bf.readLine());
        while (T-- > 0) {
            k = Integer.parseInt(bf.readLine());
            files = new int[k + 1];
            prefixSum = new int[k + 1];
            st = new StringTokenizer(bf.readLine());
            for (int i = 1; i < k + 1; i++) {
                files[i] = Integer.parseInt(st.nextToken());
                prefixSum[i] = prefixSum[i - 1] + files[i];
            }

            dp = new int[k + 1][k + 1];
            for (int i = 0; i < k + 1; i++) {
                Arrays.fill(dp[i], -1);
            }

            sb.append(recur(1, k)).append('\n');
        }

        System.out.println(sb);
    }

    private static int recur(int left, int right) {
        // 같은 페이지인 경우
        if (left == right) {
            return 0;
        }

        // 이미 계산된 경우
        if (dp[left][right] != -1) {
            return dp[left][right];
        }

        // left ~ i ~ right
        int ret = Integer.MAX_VALUE;
        for (int i = left; i < right; i++) {
            // left 묶음 만들 때 비용 + right 묶음 만들 때 비용 + 2개 묶음 합칠 때 비용
            ret = Math.min(ret, recur(left, i) + recur(i + 1, right) + prefixSum[right] - prefixSum[left - 1]);
        }

        dp[left][right] = ret;
        return dp[left][right];
    }
}
