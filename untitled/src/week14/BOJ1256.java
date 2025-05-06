package week14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * N개의 a와 M개의 z로 이루어진 문자열
 * 알파벳 순서대로 수록
 * k번쨰 문자열 찾기
 *
 * 1 <= n, m <= 100
 * 1 <= k <- 1,000,000,000 (십억)
 *
 * 시간제한 2초
 *
 * 1. 탑다운
 * 예제 입력 4, 타켓은 47번째
 * (a) 10C6 => 210, a로 시작하는 문자열은 총 210개, 즉 a로 시작해야 47번째 문자열을 만들 수 있음
 * a(a) 9C5 => 126
 * aa(a) 8C4 => 70
 * aaa(a) 7C3 => 35, aaa(a)로 시작하면 35번째까지밖에 못함. 즉, aaa(z)로 시작해야함.
 * 그리고 aaa(a)가 35까지이니까 aaa(z)는 36번부터임. 타켓을 47 - 35 = 12로 변경
 * aaaz(a) 6C3 => 20, (12 < 20)
 * aaaza(a) 5C2 => 10, (12 > 10), 12 - 10 = 2 타겟 변경
 * aaazaz(a) 4C2 => 6
 * aaazaza(a) 3C1 => 3
 * aaazazaa(a) 2C0 => 1 (2 > 1) 2 - 1 = 1 타겟 변경
 * aaazazaaz(a) 1C0 => 1
 * aaazazaaza(z) => z 1개 남음
 *
 * => 처음에 비교하기 위해 199C99를 해야함. 너무나 수가 큼. 바텁업으로 변경
 *
 * 2. 바텁업
 * dp[i][j]: i개 중에서 j개를 고르는 경우의 수
 * dp[n + m + 1][n + 1]: (a개수 + z개수)C(a개수)
 * k의 최대인 10억을 넘어가면 10억으로 초기화(탑다운에서 문제였던 큰 수를 제어할 수 있음)
 * dp 배열 초기화 완료 후 dp[n + m][n]이 k보다 작다면 -1
 * 문자열 만들기
 * -> 탑다운에서 생각했던 방법으로 진행
 */

public class BOJ1256 {

    static int n, m, k;
    static int[][] dp;  // dp[i][j] = i개 중에서 j개를 고르는 경우의 수

    private static final int MAX = 1_000_000_001;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dp = new int[n + m + 1][n + 1];

        // iC0 = 1
        for (int i = 0; i < n + m + 1; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < n + m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                if (dp[i][j] >= MAX) {   // k번째를 벗어난 경우
                    dp[i][j] = MAX;
                }
            }
        }

        if (k > dp[n + m][n]) {
            System.out.println(-1);
            return;
        }

        // 문자열 조합
        StringBuilder sb = new StringBuilder();
        int aCount = n;
        int zCount = m;

        int totalCount = n + m;
        while (totalCount-- > 0) {
            if (aCount == 0) {
                sb.append("z");
                zCount--;
            } else if (zCount == 0) {
                sb.append("a");
                aCount--;
            } else {
                // 현재 자리를 제외하고 올 수 있는 경우의 수를 확인. 따라서 -1 추가.
                int count = dp[aCount + zCount - 1][aCount - 1];

                if (count >= k) {
                    sb.append("a");
                    aCount--;
                } else {
                    sb.append("z");
                    k -= count;
                    zCount--;
                }
            }
        }

        System.out.println(sb);
    }
}
