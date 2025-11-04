package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ10160 {
    private static final int MOD = 1_000_000_009;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // dp[i] = 길이 i의 문자열 중 암호가 될 수 있는 문자열의 수
        long[] dp = new long[n + 1];
        dp[0] = 1;

        for (int i = 1; i < n + 1; i++) {
            long total = (k * dp[i - 1]) % MOD; // 가장 뒤에 올 수 있는 알파벳 수 * 이전 문자열 수. 즉, 만들 수 있는 총 가지 수

            if (i >= 5) {
                total = total - (2 * dp[i - 5]) % MOD;  // ABCB_, ABAB_ 마지막에 C가 오는 경우를 빼줌.
            }
            if (i >= 7) {
                total = (total + dp[i - 7]) % MOD;  // ABABCBC의 경우 앞에서부터 ABABC는 이미 이전에 빼준 값임. 중복으로 뺀 값이니까 다시 더해줌.
            }

            // i가 작을 떄 음수 가능성 존재
            dp[i] = (total + MOD) % MOD;
        }

        System.out.println(dp[n]);
    }
}