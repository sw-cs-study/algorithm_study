package week22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// 두 섬을 연결하는 다리를 최소한의 개수로 만들어 모든 섬 간의 왕래가 가능하도록 만들었다.
// => 사이클이 없다?

// 다리가 1개뿐인 섬
// => 리프 노드?
public class BOJ12784 {

    static int n, m;
    static List<Edge>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(bf.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(bf.readLine());
            n = Integer.parseInt(st.nextToken());   // 섬의 수
            m = Integer.parseInt(st.nextToken());   // 다리의 수

            graph = new List[n + 1];
            for (int i = 1; i < n + 1; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                graph[a].add(new Edge(b, c));
                graph[b].add(new Edge(a, c));
            }

            sb.append(dfs(1, 0)).append('\n');
        }

        System.out.println(sb);
    }

    private static int dfs(int cur, int parent) {
        // 리프 노드
        if (parent != 0 && graph[cur].size() == 1) {
            return Integer.MAX_VALUE;
        }

        int ret = 0;
        for (Edge next : graph[cur]) {
            if (next.node == parent) {  // 역류 방지
                continue;
            }

            // 현재 노드에서 막기 vs 밑에서 막기
            ret += Math.min(next.cost, dfs(next.node, cur));
        }

        return ret;
    }

    private static class Edge {
        int node;
        int cost;

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }
}
