/*
 * 사전
 * a : n개, z : m개 => 1 <= n,m <= 100, 1 <= k <= 10^9
 * k개 다세면 시간초과 (10초 > 2초), 하지만 int로 가능(2^31-1 = 2*10^9)
 * 1. a와 z로 만들수 있는 수의 갯수 = (n+m)Cn => (n+m)! / n! / m! => k가 이거보다 크면 -1
 * 2. a와 z의 갯수에서 나올수 있는 갯수를 구해서 k에서 빼면서 남은 k로 필요한 구간만 구하기
 * -> k <= ((n-1)+m)C(n-1)면, a + (나머지)
 * -> k > ((n-1)+m)C(n-1)면, z + (나머지)
 * 고려할 점
 * -> n과 m중에 하나가0이 되는 경우
 * -> 팩토리얼 값이 최대 200! -> 10^2^100 * 10^9 -> 10^209 > 2^64라서 BigInteger써야함
 * -> -> 128 * 10^6 / 70 > 10 ^ 6 => 1000000자리까지 만들수 있음(10^1000000)
 * 
 * 
 * 다르게 풀기(바텀업) 
 * dp[i][j] = iCj 
 * iCj + iC(j+1) = (i+1)Cj 로 채움, 위의 식에서 나오는 것처럼 
 * ( A, Z가 들어갈 수 있는 자리 중에 A 자리를 고르는 방법) = ( 처음에 A 놓고 나머지에서 A자리를 고르는 방법 ) + ( 처음에 A 외의 문자 놓고 나머지에서 A자리를 고르는 방법 )
 * -> (n+m)C(n) = (n-1+m)C(n-1) + (n+m-1)C(n) -> iCj = (i-1)C(j-1) + (i-1)C(j)
 * dp테이블 최댓값은 k+1임 -> k 넘으면 k + 1로 하기
 * 
 */


package week14;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1256 {
    static int n, m, k;
    static int[][] dp;
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        dp = new int[n+m+1][n+1];
        
        for (int i = 0; i < n + m + 1; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < n + m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                if (dp[i][j] > 1_000_000_000) dp[i][j] = 1_000_000_001;
            }
        }

        if (dp[n+m][n] < k) {
            System.out.println(-1);
            return;
        }

        StringBuilder answer = new StringBuilder();
        while (k > 0 && m > 0 && n > 0) {
            if (k <= dp[n+m-1][n-1]) {
                answer.append("a");
                n--;
            } else {
                answer.append("z");
                k -= dp[n+m-1][n-1];
                m--;
            }
        }
        if (n > 0) for (int i = 0; i < n; i++) answer.append("a");
        else if (m > 0) for (int i = 0; i < m; i++) answer.append("z");
        System.out.println(answer);
        return;
    }
    
}
