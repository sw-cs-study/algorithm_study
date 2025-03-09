package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ17616 {
    static int n, m, x; // 학생 수, 등수 확인 질문 수, 타겟 학생
    static List<Integer>[] highGraph;
    static List<Integer>[] lowGraph;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        // 그래프 만들기
        highGraph = new List[n + 1]; // 높은 등수용
        lowGraph = new List[n + 1]; // 낮은 등수용
        for (int i = 0; i < n + 1; i++) {
            highGraph[i] = new ArrayList<>();
            lowGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            highGraph[b].add(a);
            lowGraph[a].add(b);
        }

        // x 보다 높은 점수인 학생들을 탐색 -> 가능한 가장 높은 등수
        int highRank = bfs(x, highGraph);

        // x 보다 낮은 점수인 학생을 탐색 -> 가능한 가장 낮은 등수
        int lowRank = n - (bfs(x, lowGraph) - 1); // x인 자기 자신은 제외해야함.
        System.out.println(highRank + " " + lowRank);
    }

    private static int bfs(int start, List<Integer>[] graph) {
        boolean[] visit = new boolean[n + 1];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        visit[start] = true;

        int cnt = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            cnt++;

            for (int next : graph[node]) {
                if (visit[next]) {
                    continue;
                }

                visit[next] = true;
                queue.add(next);
            }
        }

        return cnt;
    }
}