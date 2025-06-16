package week19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ14867 {

    static int a, b, c, d;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        a = Integer.parseInt(st.nextToken());   // 물통 A 용량
        b = Integer.parseInt(st.nextToken());   // 물통 B 용량
        c = Integer.parseInt(st.nextToken());   // 물통 A에 남겨야 하는 용량
        d = Integer.parseInt(st.nextToken());   // 물통 B에 남겨야 하는 용량

        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(0, 0, 0));

        Map<Long, Boolean> visited = new HashMap<>();
        visited.put(encode(0, 0), true);

        while (!queue.isEmpty()) {
            Node now = queue.poll();
            if (now.a == c && now.b == d) {
                return now.step;
            }

            List<Node> next = new ArrayList<>();
            // A 채우기
            next.add(new Node(a, now.b));

            // B 채우기
            next.add(new Node(now.a, b));

            // A 비우기
            next.add(new Node(0, now.b));

            // B 비우기
            next.add(new Node(now.a, 0));

            // A -> B
            int moveAmount = Math.min(now.a, b - now.b);
            next.add(new Node(now.a - moveAmount, now.b + moveAmount));

            // B -> A
            moveAmount = Math.min(a - now.a, now.b);
            next.add(new Node(now.a + moveAmount, now.b - moveAmount));

            for (Node n : next) {
                Long key = encode(n.a, n.b);
                if (visited.containsKey(key)) {
                    continue;
                }

                visited.put(key, true);
                queue.add(new Node(n.a, n.b, now.step + 1));
            }
        }

        return -1;
    }

    // 한 물통의 용량 최대값은 100,000
    // 2^17 = 131,072
    // 하위 17비트는 b를 표현, 상위 나머지는 a를 표현
    private static long encode(int a, int b) {
        return ((long) a << 17) | b;
    }

    private static class Node {
        int a;
        int b;
        int step;

        public Node(int a, int b, int step) {
            this.a = a;
            this.b = b;
            this.step = step;
        }

        public Node(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
