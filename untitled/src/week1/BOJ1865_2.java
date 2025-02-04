package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1865_2 {
    static int n, m, w;
    static List<int[]> edges;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(bf.readLine());
        while (tc-- > 0) {
            st = new StringTokenizer(bf.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            edges = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                edges.add(new int[]{a, b, cost});
                edges.add(new int[]{b, a, cost});
            }

            for (int i = 0; i < w; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                edges.add(new int[]{a, b, -cost});
            }

            sb.append(bellmanForm(1) ? "YES" : "NO").append('\n');
        }

        System.out.println(sb);
    }

    private static boolean bellmanForm(int start) {
        long[] dist = new long[n + 1];
        dist[start] = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < edges.size(); j++) {
                int from = edges.get(j)[0];
                int to = edges.get(j)[1];
                int cost = edges.get(j)[2];

                if (dist[to] > dist[from] + cost) {
                    dist[to] = dist[from] + cost;

                    if (i == n - 1) {
                        return true;
                    }
                }

            }
        }

        return false;
    }
}
