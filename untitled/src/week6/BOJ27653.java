package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ27653 {

    static int n;   // 정점 수
    static List<Integer>[] graph;   // 그래프 (트리)
    static int[] goals; // 목표 가중치
    static long answer = 0;  // 정답, 목표 가중치가 1과 1억이 번갈아있는 경우엔 int를 벗어남
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());

        goals = new int[n + 1];
        st = new StringTokenizer(bf.readLine());
        for (int i = 1; i < n + 1; i++) {
            goals[i] = Integer.parseInt(st.nextToken());
        }

        graph = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        // 1에서 시작
        // 시작 노드의 목표 가중치를 도달하기 위한 연산 수
        answer += goals[1];
        visit = new boolean[n + 1];
        dfs(1);

        System.out.println(answer);
    }

    // 현재 노드의 목표 가중치 < 다음 노드의 목표 가중치인 경우
    // answer에 2노드의 목표 가중치의 차를 더해준다.
    // ex) 5 - 3 - 6 일 때
    // 5에서 시작 (answer = 5),
    // (5와 3 비교 -> 넘어감, 5를 만들 때 3은 이미 만들어짐)
    // (3와 6 비교 -> answer += (6 - 3), 3 이후의 노드에는 5를 만들 때 연산이 미치지 못함.
    private static void dfs(int now) {
        visit[now] = true;

        for (int next : graph[now]) {
            if (visit[next]) {
                continue;
            }

            if (goals[now] < goals[next]) {
                answer += (goals[next] - goals[now]);
            }

            dfs(next);
        }
    }
}