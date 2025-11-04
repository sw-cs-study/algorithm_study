package week30;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ4902 {
    static final int INF = 1_000_000_007;
    static final int MAX_N = 400;        // 문제 범위에 맞춰 충분히 크게
    static final int MAX_W = 800;        // 열 최대: 2*n - 1

    static int[][] arr = new int[MAX_N + 2][MAX_W + 2];
    static int[][] prefix = new int[MAX_N + 2][MAX_W + 2];

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder out = new StringBuilder();

        int num = 0;
        while (true) {
            st = new StringTokenizer(bf.readLine());
            int size = Integer.parseInt(st.nextToken());
            if (size == 0) {
                break;
            }

            for (int i = 1; i <= size; i++) {
                prefix[i][0] = 0; // 안전하게 0으로 초기화
                int width = 2 * i - 1;
                for (int j = 1; j <= width; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    prefix[i][j] = arr[i][j] + prefix[i][j - 1];
                }
            }

            int max = -INF;

            // 정삼각형
            for (int i = 1; i <= size; i++) {
                for (int j = 1; j <= 2 * i - 1; j += 2) {
                    int cur = 0;
                    for (int k = 0; k < size - i + 1; k++) {
                        cur += prefix[i + k][j + 2 * k] - prefix[i + k][j - 1];
                        if (cur > max) max = cur;
                    }
                }
            }

            // 역삼각형
            for (int i = size; i >= 1; i--) {
                for (int j = 2; j <= 2 * i - 1; j += 2) {
                    int cur = 0;
                    int limit = Math.min(j / 2, i - j / 2);
                    for (int k = 0; k < limit; k++) {
                        cur += prefix[i - k][j] - prefix[i - k][j - 2 * k - 1];
                        if (cur > max) max = cur;
                    }
                }
            }

            out.append(++num).append(". ").append(max).append('\n');
        }

        System.out.print(out);
    }
}
