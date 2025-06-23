package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1749 {

    static int n, m;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());   // 행
        m = Integer.parseInt(st.nextToken());   // 열

        arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = Integer.MIN_VALUE;
        for (int top = 0; top < n; top++) {
            int[] rowSum = new int[m];

            for (int bottom = top; bottom < n; bottom++) {
                for (int i = 0; i < m; i++) {
                    rowSum[i] += arr[bottom][i];
                }

                max = Math.max(max, kadane(rowSum));
            }
        }

        System.out.println(max);
    }

    private static int kadane(int[] arr) {
        int max = arr[0];
        int sum = arr[0];

        for (int i = 1; i < n; i++) {
            // 현재 값을 포함하는게 최대값인지 아닌지 판단
            sum = Math.max(arr[i], sum + arr[i]);

            // 최대값 경신
            max = Math.max(max, sum);
        }

        return max;
    }
}
