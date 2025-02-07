package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ11049 {
    static int n;
    static Matrix[] matrices;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        matrices = new Matrix[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            matrices[i] = new Matrix(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(recur(0, n - 1));
    }

    // start 행렬부터 end 행렬까지 곱셉의 최소값
    private static int recur(int start, int end) {
        // 같은 행렬의 경우 곱할 필요가 없음
        if (start == end) {
            return 0;
        }

        // 이미 계산을 한 경우
        if (dp[start][end] != -1) {
            return dp[start][end];
        }

        // 최소값을 구해야하므로 최대값으로 설정
        int min = Integer.MAX_VALUE;

        // 분할정복?
        for (int i = start; i < end; i++) {
            // 앞 부분 (start ~ i), 뒤 부분(i + 1, end)은 알아서 계산이 된다고 치고, 앞 부분과 뒤 부분 행렬곱셈을 해야함.
            // 앞 부분의 크키는 (start의 r, i의 c) 뒤 부분의 크키는 (i + 1의 r, end의 c)
            // 곱셈 연산 수는: start의 r * i의 c * end의 c가 됨.
            int cost = recur(start, i) + recur(i + 1, end) + (matrices[start].r * matrices[i].c * matrices[end].c);
            min = Math.min(min, cost);
        }

        dp[start][end] = min;
        return min;
    }

    private static class Matrix {
        int r;
        int c;

        public Matrix(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
