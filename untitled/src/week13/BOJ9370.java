package week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 다익스트라
 *
 * 1. s에서 다익스트라
 * 2. g에서 다익스트라
 * 3. h에서 다익스트라
 *
 * 목적지 후보 중 아래와 같은 조건 중 하나를 만족하는 노드를 오름차순으로 출력한다.
 * 조건1. 1[g] + 2[h] + 3[x] = 1[x]
 * 조건2. 1[h] + 3[g] + 2[x] = 1[x]
 *
 * 시간복잡도 (시간제한 3초)
 * 다익스트라 3번
 * (50,000 * log2,000) * 3 => 50,000 * 11 * 3 =>  1,650,000
 *
 * 메모리 초과 이유
 * - dist[next.node], dist[now.node] + next.cost 비교하는 부분
 * - 비교조건을 <= 이 아닌 <로 하여 중복 탐색이 이루어져 메모리 초과가 났음
 */

public class BOJ9370 {

    static final int MAX = 5000 * 1000;

    static int n, m, t; // 교차로 수, 도로 수, 목적지 후보 수
    static int s, g, h; // 예술가들의 출발지

    static List<Edge>[] graph;
    static int[] candidate;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(bf.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(bf.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(bf.readLine());
            s = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            graph = new List[n + 1];
            for (int i = 1; i < n + 1; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                graph[a].add(new Edge(b, d));
                graph[b].add(new Edge(a, d));
            }

            candidate = new int[t];
            for (int i = 0; i < t; i++) {
                candidate[i] = Integer.parseInt(bf.readLine());
            }

            int[] distS = dijkstra(s);
            int[] distG = dijkstra(g);
            int[] distH = dijkstra(h);

            List<Integer> answerList = new ArrayList<>();
            for (int c : candidate) {
                if (distS[c] == distS[g] + distG[h] + distH[c] || distS[c] == distS[h] + distH[g] + distG[c]) {
                    answerList.add(c);
                }
            }

            Collections.sort(answerList);
            for (int answer : answerList) {
                sb.append(answer).append(" ");
            }
            sb.append('\n');
        }

        System.out.println(sb);
    }

    private static int[] dijkstra(int start) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, MAX);
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
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

        return dist;
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
