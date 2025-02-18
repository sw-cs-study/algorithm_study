package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 슈퍼컴퓨터가 입력으로 주어진 컴퓨터 중 하나를 의미함...
// 이거를 파악하는데 시간이 오래걸림

// 조건1. 네트워크를 복구한 후에 네트워크에 속한 컴퓨터들끼리 통신이 가능해야함.
// 조건2. 네트워크를 복구한 후 특정 (A, B) 컴퓨터끼리의 통신 비용이 원래 네트워크의 통신보다 커지면 안됨.
// -> MST인줄 알았으나 아님.
// 1 2 3
// 2 3 3
// 1 3 5

// => MST 경우 1 2 3, 2 3 3을 선택하는데 1과 3의 통신비용은 6으로 기존 네트워크인 5보다 비쌈.

// 또한, 문제에서 슈퍼컴퓨터를 고르는 기준에 복구했을 때 최소 비용이 아니라, 특정 컴퓨터에서 다익스트라를 돌려도 문제가 되지 않음.
// 즉, 1번 컴퓨터를 슈퍼 컴퓨터로 선택 후 복구한 네트워크의 비용이 2번 컴퓨터를 슈퍼 컴퓨터로 선택 후 복구한 네트워크의 비용보다 비싸도 문제가 되지 않음.
public class BOJ2211 {
    static int n, m;
    static List<Edge>[] graph;
    static int[] parent;

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

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 양방향, 모든 통신은 완전쌍방향 방식으로 이루어지기 때문에, 한 회선으로 연결된 두 컴퓨터는 어느 방향으로도 통신할 수 있다.
            graph[a].add(new Edge(b, c));
            graph[b].add(new Edge(a, c));
        }

        // 다익스트라
        int[] prev = solve(1);

        StringBuilder sb = new StringBuilder();
        sb.append(n - 1).append('\n');
        for (int i = 2; i < n + 1; i++) {
            sb.append(i).append(" ").append(prev[i]).append('\n');
        }
        System.out.println(sb.toString());
    }

    private static int[] solve(int start) {
        // 최소 거리 저장 배열
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // 최단 거리 업데이트 시 어느 노드로부터 업데이트가 되었는지 저장하기 위한 배열
        parent = new int[n + 1];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (Edge next : graph[node]) {
                int cost = next.cost + dist[node];

                if (cost < dist[next.node]) {
                    dist[next.node] = cost;
                    parent[next.node] = node;
                    queue.add(next.node);
                }
            }
        }

        return parent;
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
