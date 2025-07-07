package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 교차 X
// 1번 기준, 악수 가능 상대는 2i ( 0 <= 2i <= n)
// 1번이 2i와 악수하면 악수선?을 기준으로 왼쪽 그룹과 오른쪽 그룹이 나누어짐.
// 2 그룹은 독립적 => 곱으로 표현
public class BOJ1670 {

    private static final int MOD = 987_654_321;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        int pair = n / 2;

        // 문제에서 987654321로 나눈 나머지이므로 long
        // 987654320 * 987654320 => int 초과
        long[] dp = new long[pair + 1];

        // 0쌍인 경우엔 1가지
        dp[0] = 1;

        for (int i = 1; i < pair + 1; i++) {
            long sum = 0;

            // 1번이 2j와 악수
            for (int j = 0; j < i; j++) {
                // 왼쪽 그룹 * 오른쪽 그룹
                sum = (sum + (dp[j] * dp[i - 1 - j])) % MOD;
            }

            dp[i] = sum;
        }

        System.out.println(dp[pair]);
    }
}
