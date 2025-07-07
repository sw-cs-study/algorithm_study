package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2228 {

    private static final int MIN = -1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());   // 구간 개수

        int[] arr = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            arr[i] = Integer.parseInt(bf.readLine());
        }

        int[][] local = new int[k + 1][n + 1];
        int[][] global = new int[k + 1][n + 1];

        for (int i = 0; i < k + 1; i++) {
            Arrays.fill(local[i], MIN);
            Arrays.fill(global[i], MIN);
        }

        local[1][1] = arr[1];
        global[1][1] = arr[1];
        for (int i = 2; i < n + 1; i++) {
            local[1][i] = Math.max(arr[i], local[1][i - 1] + arr[i]);
            global[1][i] = Math.max(global[1][i - 1], local[1][i]);
        }

        for (int i = 2; i < k + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                // k번째 구간을 j에서 시작
                int startNew = (j >= 2) ? global[i - 1][j - 2] + arr[j] : MIN;

                // k번째 구간의 끝을 j - 1에서 j로 확장
                int extend = local[i][j - 1] + arr[j];

                local[i][j] = Math.max(startNew, extend);

                // max(j을 포함하지 않는 경우, j를 포함하는 경우)
                global[i][j] = Math.max(global[i][j - 1], local[i][j]);
            }
        }

        System.out.println(global[k][n]);
    }
}
