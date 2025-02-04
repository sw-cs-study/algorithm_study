package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1865 {
    static final int MAX = 100_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(bf.readLine());
        while (tc-- > 0) {
            st = new StringTokenizer(bf.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            int[][] dist = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = (i == j) ? 0 : MAX;
                }
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                int cost = Integer.parseInt(st.nextToken());

                dist[a][b] = Math.min(dist[a][b], cost);
                dist[b][a] = Math.min(dist[b][a], cost);
            }

            for (int i = 0; i < w; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                int cost = Integer.parseInt(st.nextToken());

                dist[a][b] = Math.min(dist[a][b], -cost);
            }

            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }

            boolean isPossible = false;
            for (int i = 0; i < n; i++) {
                if (dist[i][i] < 0) {
                    isPossible = true;
                    break;
                }
            }

            sb.append(isPossible ? "YES" : "NO").append('\n');
        }

        System.out.println(sb);
    }
}
