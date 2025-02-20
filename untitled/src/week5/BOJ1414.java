package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BOJ1414 {
    static int n;   // 컴퓨터 수
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());

        PriorityQueue<Edge> queue = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.cost)
        );

        int has = 0;
        for (int i = 0; i < n; i++) {
            String row = bf.readLine();
            for (int j = 0; j < n; j++) {
                char costChar = row.charAt(j);
                if (costChar == '0') {  // 연결 X
                    continue;
                }

                // 연결 O
                int length = 0;
                if (Character.isLowerCase(costChar)) {
                    // a는 1, z는 26
                    length = costChar - 'a' + 1;
                    queue.add(new Edge(i, j, length));
                } else if (Character.isUpperCase(costChar)) {
                    // A는 27 Z는 52
                    length = costChar - 'A' + 27;
                    queue.add(new Edge(i, j, length));
                }
                has += length;
            }
        }

        // mst
        parent = new int[53];
        for (int i = 0; i < 53; i++) {
            parent[i] = i;
        }

        int answer = 0;
        int count = 0;
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (find(edge.dst) == find(edge.src)) {
                continue;
            }

            union(edge.dst, edge.src);
            answer += edge.cost;
            count++;

            // 연결 완료
            if (count == n - 1) {
                break;
            }
        }

        System.out.println(count == n - 1 ? (has - answer) : -1);
    }

    private static int find(int n) {
        if (parent[n] != n) {
            parent[n] = find(parent[n]);
        }

        return parent[n];
    }

    private static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa < pb) {
            parent[pb] = pa;
        } else {
            parent[pa] = pb;
        }
    }

    private static class Edge {
        int src;
        int dst;
        int cost;

        public Edge(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }
    }
}