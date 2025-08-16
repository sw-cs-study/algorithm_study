package week24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ12969 {

    static int n, k;

    static boolean[][][][] dp;
    static char[] answer;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 해당 케이스를 진행했을 때 실패한 경우 마킹용 (재탐색 방지)
        dp = new boolean[n + 1][n + 1][n + 1][k + 1];
        answer = new char[n];
        System.out.println(recur(0, 0, 0, 0) ? new String(answer) : -1);
    }

    private static boolean recur(int len, int a, int b, int pairCount) {
        // 타겟 문자열 길이까지 온 경우 쌍의 수를 확인하기
        if (len == n) {
            return pairCount == k;
        }

        // 타겟 문자열 길이까지 가기 전 쌍의 수가 초과한 경우엔 문자열 생성 실패
        if (pairCount > k) {
            return false;
        }

        // len 길이의 문자열을 A는 a개, B는 b개를 이용하고 쌍은 pairCount개가 존재하는 케이스
        // 이전에 실패한 경우
        if (dp[len][a][b][pairCount]) {
            return false;
        }

        // A 붙이기
        // A보다 아스키 코드 상 낮은 문자는 없으므로 pairCount 유지
        answer[len] = 'A';
        if (recur(len + 1, a + 1, b, pairCount)) {
            return true;
        }

        // B 붙이기
        // B보다 아스키 코드 상 낮은 문자는 A 이므로 A 개수(a)만큼 증가
        answer[len] = 'B';
        if (recur(len + 1, a, b + 1, pairCount + a)) {
            return true;
        }

        // C 붙이기
        // C보다 아스키 코드 상 낮은 문자는 A, B 이므로 A, B 개수(a + b)만큼 증가
        answer[len] = 'C';
        if (recur(len + 1, a, b, pairCount + a + b)) {
            return true;
        }

        // 여기까지 오면 실패
        dp[len][a][b][pairCount] = true;
        return false;
    }
}
