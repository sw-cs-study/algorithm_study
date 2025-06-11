package week19;

/**
 * 3^4 = 81 => 100 따라서 3 ^ 16은 대충 10^8, 그럼 16자리만 dfs를 수행해도 이미 시간복잡도를 초과함 => 다른 방법이 필요함
 * 특정 구간의 펠린드롬 만드는 최소 연산 수를 구하기, 그럼 4번이 남는데 앞의 특정 구간을 모두 연산하는 데 50 * 50 / 2 = 1250이므로 널널함. 그래서 2개를 교환한 케이스를 모두 만들어서 특정 구간 연산을 하면됨
 * 그럼 50C2 = 50 * 49 / 2! = 1225
 * 이제 문제는 특정 구간을 펠린드롬으로 만들 때 최소 연산을 가지고 점화식을 세우는 일임
 * 삽입하는 경우를 생각해봤는데, 현재 문자열에서 해당 자리에 반대에 위치한 알파벳을 삽입해야 펠린드롬을 만들수 있다고 치면, 반대에 위치한 알파벳을 삭제하는 것도 똑같이 펠린드롬을 만들수 있다. 
 * 그래서 똑같은 경우로 생각하고 빼도 무관하다.
 * 따라서, dp[i][j] = min(dp[i+1][j] + 1, dp[i][j-1] + 1, dp[i+1][j-1] + (s[i] == s[j] ? 0 : 1))
 */

import java.io.*;

public class BOJ1053 {
    private static char[] arr;
    private static int n;
    private static int[][] dp;
    public static void main (String[] args) throws IOException {
        String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        n = line.length();
        arr = line.toCharArray();

        int answer = 200_000_000;
        // 원본
        dp = new int[n][n];

        for (int k = 1; k < n; k++) {
            for (int i = 0; i < n; i++) {
                int j = i + k;
                if (j >= n) continue;
                dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j-1] + 1);
                dp[i][j] = Math.min(dp[i][j], (k < 2 ? 0 : dp[i+1][j-1]) + (arr[i] == arr[j] ? 0 : 1));
            }
        }
        if (dp[0][n-1] < answer) {
            answer = dp[0][n-1];
        }
        // 교환
        for (char[] caseArr : combination()) {
            dp = new int[n][n];

            for (int k = 1; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    int j = i + k;
                    if (j >= n) continue;
                    dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j-1] + 1);
                    dp[i][j] = Math.min(dp[i][j], (k < 2 ? 0 : dp[i+1][j-1]) + (caseArr[i] == caseArr[j] ? 0 : 1));
                }
            }
            if (dp[0][n-1] + 1 < answer) {
                answer = dp[0][n-1] + 1;
            }
        }
        System.out.println(answer);
    }

    private static char[][] combination () {
        char[][] ret = new char[coefficient(n)][n];
        int caseIdx = 0;
        for (int i = 0; i < n-1; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    ret[caseIdx][k] = arr[k];
                }
                char tmp = ret[caseIdx][i];
                ret[caseIdx][i] = ret[caseIdx][j];
                ret[caseIdx][j] = tmp;
                caseIdx++;
            }
        }
        
        return ret;
    }

    private static int coefficient (int v) {
        return v * (v-1) / 2;
    }
}
