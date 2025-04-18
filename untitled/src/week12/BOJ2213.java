package week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ2213 {

    static int n;   // 정점 수
    static List<Integer>[] graph;
    static int[] count; // 가중치
    static int[][] dp;

    static boolean[] visit; // 독립집합 포함 여부

    static List<Integer> path = new ArrayList<>();  // 독립집합

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        count = new int[n + 1];
        graph = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        st = new StringTokenizer(bf.readLine());
        for (int i = 1; i < n + 1; i++) {
            count[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        dp = new int[2][n + 1];
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 시작노드를 임의로 선택, 해당 노드를 선택한 경우와 선택하지 않은 경우를 모두 탐색
        System.out.println(Math.max(recur(1, -1, 0), recur(1, -1, 1)));

        // 독립집합 포함 여부
        visit = new boolean[n + 1];
        getPath(1, 0);
        Collections.sort(path);

        StringBuilder sb = new StringBuilder();
        for (int p : path) {
            sb.append(p).append(" ");
        }
        System.out.println(sb);
    }

    private static void getPath(int now, int prev) {
        // now를 포함하는 경우가 더 큰 경우 && 이전 노드가 독립집합에 포함되지 않은 경우 => 현재 노드가 독립집합에 포함
        if (dp[1][now] > dp[0][now] && !visit[prev]) {
            visit[now] = true;
            path.add(now);
        }

        for (int next : graph[now]) {
            if (next == prev) {
                continue;
            }

            getPath(next, now);
        }
    }

    private static int recur(int now, int prev, int onOff) {
        if (dp[onOff][now] != -1) {
            return dp[onOff][now];
        }

        dp[onOff][now] = onOff == 0 ? 0 : count[now];

        for (int next : graph[now]) {
            if (next == prev) {
                continue;
            }

            if (onOff == 0) {   // 현재 노드를 선택하지 않은 경우, 다음 노드는 선택 또는 선택 X
                dp[onOff][now] += Math.max(recur(next, now, 0), recur(next, now, 1));
            } else {    // 현재 노드를 선택한 경우, 다음 노드는 선택 X
                dp[onOff][now] += recur(next, now, 0);
            }
        }

        return dp[onOff][now];
    }
}
