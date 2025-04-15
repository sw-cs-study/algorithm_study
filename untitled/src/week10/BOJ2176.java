package week10;

import java.io.*;
import java.util.*;

public class BOJ2176 {

    static int n;
    static int m;

    static List<Edge>[] graph;

    static int[] distance;

    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        graph = new List[n + 1];
        for (int i = 0; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(bf.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[node1].add(new Edge(node2, weight));
            graph[node2].add(new Edge(node1, weight));
        }

        distance = new int[n + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);

        dp = new int[n + 1];

        dijkstra(2);

        System.out.println(dp[1]);
    }

    static void dijkstra(int start) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(
                (q1, q2) -> q1.w - q2.w
        );

        queue.add(new Edge(start, 0));
        distance[start] = 0;
        dp[2] = 1;

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (distance[edge.node] != edge.w) {
                continue;
            }

            for (Edge next : graph[edge.node]) {
                if (distance[next.node] > distance[edge.node] + next.w) {
                    distance[next.node] = distance[edge.node] + next.w;

                    queue.add(new Edge(next.node, distance[next.node]));
                }

                if (edge.w > distance[next.node]) {
                    dp[edge.node] += dp[next.node];
                }
            }
        }
    }

    static class Edge {
        int node;
        int w;

        Edge(int node, int w) {
            this.node = node;
            this.w = w;
        }
    }
}