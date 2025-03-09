package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ21278 {

    static int n, m;    // 건물의 개수, 도로의 개수
    static int[][] arr; // 도로 그래프, arr(i, j) -> i에서 j로 갈 때 드는 비용, inf면 못감

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(arr[i], 1_000);
            arr[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr[a][b] = 1;
            arr[b][a] = 1;
        }

        for (int k = 1; k < n + 1; k++) {
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    if (arr[i][k] + arr[k][j] < arr[i][j]) {
                        arr[i][j] = arr[i][k] + arr[k][j];
                    }
                }
            }
        }

        int answerTime = Integer.MAX_VALUE;
        int chickenA = 0;
        int chickenB = 0;
        for (int i = 1; i < n + 1; i++) {
            for (int j = i + 1; j < n + 1; j++) {
                int time = 0;
                for (int k = 1; k < n + 1; k++) {
                    time += (Math.min(arr[i][k], arr[j][k]) * 2);
                }

                if (time < answerTime) {
                    answerTime = time;
                    chickenA = i;
                    chickenB = j;
                }
            }
        }

        System.out.println(chickenA + " " + chickenB + " " + answerTime);
    }
}
