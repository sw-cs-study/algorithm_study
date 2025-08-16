package week24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1800 {

    // 입력
    static int n, p, k;
    static List<Node>[] graph;

    // non-입력
    static int maxCost = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());   // 학생 수
        p = Integer.parseInt(st.nextToken());   // 이어질 수 있는 쌍의 수
        k = Integer.parseInt(st.nextToken());   // 공짜 연결 수

        graph = new List[n + 1];    // 학생이 1부터 번호가 부여됨에 따라 인덱스 1부터 사용
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(bf.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, cost));
            graph[to].add(new Node(from, cost));

            maxCost = Math.max(maxCost, cost);
        }

        int right = binarySearch();
        System.out.println(check(right) ? right : -1);
    }

    // 최적화 문제 -> 결정 문제
    private static int binarySearch() {
        int left = -1;
        int right = maxCost;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        return right;
    }

    // 1번 학생부터 n번 학생까지 연결하는 간선의 수가 k개 이하라면 true 반환, 아니라면 false
    private static boolean check(int x) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, 1001);
        dist[1] = 0;    // 1번 학생은 항상 인터넷과 연결되어있다.

        Deque<Integer> dq = new ArrayDeque<>();
        dq.addFirst(1);

        while (!dq.isEmpty()) {
            int now = dq.pollFirst();
            if (dist[now] > k) {
                continue;
            }
            for (Node next : graph[now]) {
                int to = next.to;
                int nextCost = next.cost > x ? 1 : 0;
                if (dist[now] + nextCost < dist[to]) {
                    dist[to] = dist[now] + nextCost;
                    if (nextCost == 0) {
                        dq.addFirst(to);
                    } else {
                        dq.addLast(to);
                    }
                }
            }
        }

        return dist[n] <= k;
    }

    private static class Node {
        int to;
        int cost;

        public Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
