package week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16118 {

    static int n, m;    // 나무 그루터기, 오솔길
    static List<Edge>[] graph;

    static int[] dist; // 여우용
    static int[][] dist2; // 늑대용

    private static final int MAX = 2_002_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        graph = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken()) * 2;

            graph[a].add(new Edge(b, c));
            graph[b].add(new Edge(a, c));
        }

        // 거리 배열 초기화
        dist = new int[n + 1];
        dist2 = new int[2][n + 1];

        Arrays.fill(dist, MAX);
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dist2[i], MAX);
        }

        // 여우 다익스트라
        dijkstra(1);

        // 늑대 다익스트라
        dijkstra2(1);

        int count = 0;
        for (int i = 2; i < n + 1; i++) {
            if (dist[i] < Math.min(dist2[0][i], dist2[1][i])) {
                count++;
            }
        }

        System.out.println(count);
    }

    // 늑대
    private static void dijkstra2(int start) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        pq.add(new Edge(start, 0, 0));

        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if (dist2[now.state][now.node] < now.cost) {
                continue;
            }

            for (Edge next : graph[now.node]) {
                int nextCost = now.state == 0 ? now.cost + (next.cost / 2) : now.cost + (next.cost * 2);
                int nextState = (now.state + 1) % 2;

                if (dist2[nextState][next.node] <= nextCost) {
                    continue;
                }

                dist2[nextState][next.node] = nextCost;
                pq.add(new Edge(next.node, nextCost, nextState));
            }
        }
    }



    // 여우
    private static void dijkstra(int start) {
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.cost)
        );

        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if (dist[now.node] < now.cost) {
                continue;
            }

            for (Edge next : graph[now.node]) {
                if (dist[next.node] <= dist[now.node] + next.cost) {
                    continue;
                }

                dist[next.node] = dist[now.node] + next.cost;
                pq.add(new Edge(next.node, dist[next.node]));
            }
        }
    }

    private static class Edge {
        int node;
        int cost;
        int state; // 0: run, 1: walk

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        public Edge(int node, int cost, int state) {
            this.node = node;
            this.cost = cost;
            this.state = state;
        }
    }
}
