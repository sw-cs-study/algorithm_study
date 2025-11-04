import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1033 {

    static int n;
    static List<Edge>[] graph;

    static long[] amount;
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        long mul = 1;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long p = Long.parseLong(st.nextToken());
            long q = Long.parseLong(st.nextToken());

            long gcd = gcd(p, q);
            p /= gcd;
            q /= gcd;

            graph[a].add(new Edge(b, p, q));
            graph[b].add(new Edge(a, q, p));
            mul *= p;
            mul *= q;
        }

        amount = new long[n];
        visit = new boolean[n];
        amount[0] = mul;
        dfs(0);

        long all = amount[0];
        for (int i = 1; i < n; i++) {
            all = gcd(all, amount[i]);
        }
        for (int i = 0; i < n; i++) {
            amount[i] = amount[i] / all;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(amount[i]).append(' ');
        }
        System.out.println(sb);
    }

    private static void dfs(int node) {
        visit[node] = true;
        for (Edge edge : graph[node]) {
            if (visit[edge.to]) {
                continue;
            }

            amount[edge.to] = amount[node] * edge.q / edge.p;
            dfs(edge.to);
        }
    }

    private static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private static class Edge {
        int to;
        long p;
        long q;

        public Edge(int to, long p, long q) {
            this.to = to;
            this.p = p;
            this.q = q;
        }
    }
}