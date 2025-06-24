/**
 * 현재 대표가 바로 옆사람 혹은 홀수번째 사람(그래야 나눠진 사람의 수가 각각 짝수임)과 악수를 해야한다.
 * 그래서 2명일때부터 경우의 수를 구하고 그 다음 경우의 수는 이전 경우의 수의 값을 더해서 만들면 됨 => dp
 * 대표가 악수한 사람을 기준으로 양옆에 그룹이 생김 => 이 그룹은 이전에 경우의 수를 참고하면 됨
 * 
 * 점화식 구하기
 * 2명: 1
 * 4명: 0-2일 때 1, 2-0일 때 2
 * 6명: 0-4일 때 2, 2-2일 때 1, 4-0일 때 2===> 5
 * 8명: 0-6->5, 2-4->2, 4-2->2, 6-0->5 ===> 14
 * ...
 * 10000명: 5000개
 * 
 * 고려할점
 * dp 배열은 int로 하니깐 터짐.... 2^31-1을 넘으니깐 당연한거군 ㅋㅋ
 */

package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ1670 {

    private static int MOD = 987654321;
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        if (n == 0) {
            System.out.println(0);
            return;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[2] = 1;
        for (int i = 4; i < dp.length; i+=2) {
            int rest = i - 2;
            long value = 0;
            for (int k = 0; k <= rest; k+=2) {
                value = (value + (dp[k] * dp[rest-k]) % MOD) % MOD;
            }
            dp[i] = value;
        }
        System.out.println(dp[n]);
    }

}
