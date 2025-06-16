package week19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ1053 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        String word = bf.readLine();
        int n = word.length();

        // len = 1은 항상 팰린드롬
        int[][] p = palindrome(n, new StringBuilder(word));

        int answer = p[0][n - 1];

        // 4. 서로 다른 문자 교환
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                StringBuilder sb = getNewWord(word, i, j);

                // 바뀐 문자열을 기준으로 팰린드롬 계산
                p = palindrome(n, sb);

                // 4번 연산을 했으니까 + 1
                answer = Math.min(answer, p[0][n - 1] + 1);
            }
        }

        // 정답 출력
        System.out.println(answer);
    }

    private static StringBuilder getNewWord(String word, int i, int j) {
        StringBuilder sb = new StringBuilder(word);

        // i번째 문자
        char c = sb.charAt(i);

        // i번째에 j번째 문자로 교체
        sb.setCharAt(i, sb.charAt(j));

        // j번째에 i번째 문자로 교체
        sb.setCharAt(j, c);

        return sb;
    }

    private static int[][] palindrome(int n, StringBuilder sb) {
        int[][] dp = new int[n][n]; // dp[i][j] : 문자열 i ~ j가 팰린드롬이 되기 위한 최소 수

        // len = 1은 항상 팰린드롬
        for (int len = 2; len < n + 1; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;

                if (sb.charAt(i) == sb.charAt(j)) {
                    // 같은 경우엔 안에 문자열들이 팰린드롬이면 됨
                    dp[i][j] = (len == 2) ? 0 : dp[i + 1][j - 1];
                } else {
                    // 다른 경우: min(특정 위치에 추가, 특정 위치 삭제 또는 교환) + 1
                    dp[i][j] = Math.min(dp[i + 1][j - 1], Math.min(dp[i + 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        return dp;
    }
}
