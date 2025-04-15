package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ9470 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(bf.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(bf.readLine());
            int k = Integer.parseInt(st.nextToken());   // 테스트케이스 번호
            int m = Integer.parseInt(st.nextToken());   // 노드의 수
            int p = Integer.parseInt(st.nextToken());   // 간선의 수

            int[] indegree = new int[m + 1];    // 노드 입력이 1부터 시작
            List<Integer>[] graph = new List[m + 1];
            for (int i = 0; i < m + 1; i++) {
                graph[i] = new ArrayList<>();
            }

            int[] cnt = new int[m + 1]; // i번 노드에 동일 순서로 진입하는 간선 수
            int[] order = new int[m + 1];   // 순서

            // 간선 입력
            for (int i = 0; i < p; i++) {
                st = new StringTokenizer(bf.readLine());
                int a = Integer.parseInt(st.nextToken());   // 출발 노드
                int b = Integer.parseInt(st.nextToken());   // 도착 노드

                graph[a].add(b);
                indegree[b]++;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 1; i < m + 1; i++) {
                if (indegree[i] == 0) {
                    queue.add(i);
                    order[i] = 1;
                    cnt[i] = 1;
                }
            }

            while (!queue.isEmpty()) {
                int now = queue.poll();

                for (int next : graph[now]) {
                    // next 노드의 순서보다 now 노드의 순서가 큰 경우
                    if (order[now] > order[next]) {
                        order[next] = order[now];

                        // next 노드에 order[now] 순서로 들어오는 간선은 1개이므로
                        cnt[next] = 1;
                    } else if (order[now] == order[next]) {
                        cnt[next]++;
                    }

                    indegree[next]--;

                    // 진입간선이 0인 경우, 자신의 순서를 정해야함
                    if (indegree[next] == 0) {
                        // 노드로 들어오는 강의 순서 중 가장 큰 값을 i라고 했을 때, 들어오는 모든 강 중에서 Strahler 순서가 i인 강이 1개이면 순서는 i, 2개 이상이면 순서는 i+1이다.
                        if (cnt[next] >= 2) {
                            order[next]++;
                        }
                        queue.add(next);
                    }
                }
            }

            sb.append(k).append(" ").append(order[m]).append('\n');
        }

        System.out.println(sb);
    }
}
