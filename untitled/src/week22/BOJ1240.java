package week22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1240 {

    static int n, m;
    static List<Node>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        graph = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        StringBuilder sb = new StringBuilder();
        while (m-- > 0) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(bfs(a, b)).append('\n');
        }

        System.out.println(sb);
    }

    private static int bfs(int a, int b) {
        boolean[] visit = new boolean[n + 1];
        Queue<Node> queue = new ArrayDeque<>();

        queue.add(new Node(a, 0));
        visit[a] = true;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (cur.node == b) {
                return cur.dist;
            }

            for (Node next : graph[cur.node]) {
                if (visit[next.node]) {
                    continue;
                }

                queue.add(new Node(next.node, cur.dist + next.dist));
                visit[next.node] = true;
            }
        }

        return -1;
    }

    private static class Node {
        int node;
        int dist;

        public Node(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}
