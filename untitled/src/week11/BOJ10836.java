package week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ10836 {

    static int m, n; // 격자 크기, 날짜 수
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        arr = new int[m][m];

        int[] tmp = new int[2 * m - 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int index = 0;
            for (int j = 0; j < 3; j++) {
                int cnt = Integer.parseInt(st.nextToken());
                for (int k = 0; k < cnt; k++) {
                    tmp[index] += j;
                    index++;
                }
            }
        }

        int index = 0;
        for (int i = m - 1; i > -1; i--) {
            arr[i][0] = tmp[index++];
        }

        for (int i = 1; i < m; i++) {
            arr[0][i] = tmp[index++];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < m; j++) {
                arr[i][j] = Math.max(
                        arr[i - 1][j - 1],
                        Math.max(
                                arr[i - 1][j],
                                arr[i][j - 1]
                        )
                );
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(arr[i][j] + 1).append(" ");
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
