package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ15483 {
    static String source;
    static String target;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        source = bf.readLine();
        target = bf.readLine();

        dp = new int[source.length()][target.length()];
        for (int i = 0; i < source.length(); i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(recur(source.length() - 1, target.length() - 1));
    }

    // source를 기준으로만 생각
    private static int recur(int i, int j) {
        // 한쪽 탐색 완료 시 다른쪽 개수만큼 삽입 또는 삭제가 필요
        if (i == -1) {
            return j + 1;
        }

        if (j == -1) {
            return i + 1;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        // 이미 같은 경우 탐색 위치만 변경
        if (source.charAt(i) == target.charAt(j)) {
            dp[i][j] = recur(i - 1, j - 1);
            return dp[i][j];
        }

        int insert = recur(i, j - 1);
        int delete = recur(i - 1, j);
        int replace = recur(i - 1, j - 1);

        // 3가지 작업 중 적은 연산을 지닌 작업을 선택 + 1
        dp[i][j] = Math.min(insert, Math.min(delete, replace)) + 1;
        return dp[i][j];
    }
}