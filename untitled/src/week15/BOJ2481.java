package week15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 코드가 같은 경우가 없음
public class BOJ2481 {

    static int n, k;
    static Map<Integer, Integer> valueOrderMap;
    static int[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());   // 코드 수
        k = Integer.parseInt(st.nextToken());   // 각 코드의 비트 수

        int start = 0;
        valueOrderMap = new HashMap<>();
        for (int i = 1; i < n + 1; i++) {
            int value = Integer.parseInt(bf.readLine(), 2);
            if (i == 1) {
                start = value;
            }
            valueOrderMap.put(value, i);
        }

        bfs(start);

        StringBuilder sb = new StringBuilder();
        int m = Integer.parseInt(bf.readLine());   // 질의 수
        for (int i = 0; i < m; i++) {
            // 1번 코드와 j번 코드 사이의 해밍 경로
            int j = Integer.parseInt(bf.readLine());

            if (visit[j] == -1) {
                sb.append(-1).append('\n');
                continue;
            }

            List<Integer> path = new ArrayList<>();
            path.add(j);

            boolean flag = false;
            while (j != 1) {
                path.add(visit[j]);
                j = visit[j];
            }

            if (flag) {
                sb.append(-1).append(" ");
            } else {
                for (int l = path.size() - 1; l > -1; l--) {
                    sb.append(path.get(l)).append(" ");
                }
                sb.append('\n');
            }
        }

        System.out.println(sb);

    }

    private static void bfs(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        visit = new int[n + 1];
        Arrays.fill(visit, -1);

        queue.add(start);
        int order = valueOrderMap.get(start);
        visit[order] = order;

        while (!queue.isEmpty()) {
            int nowValue = queue.poll();
            int nowOrder = valueOrderMap.get(nowValue);

            for (int i = 0; i < k; i++) {
                int nextValue = nowValue ^ (1 << i); // 한 비트 바꾸기

                // 한 비트 바꾼 수가 있는 경우
                if (valueOrderMap.containsKey(nextValue)) {
                    int nextOrder = valueOrderMap.get(nextValue);

                    // 이미 방문한 경우
                    if (visit[nextOrder] != -1) {
                        continue;
                    }

                    queue.add(nextValue);
                    visit[nextOrder] = nowOrder;
                }
            }
        }
    }
}
