package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ21606 {

    static int n;   // 정점 수
    static char[] inout; // 1: 실내, 0: 실외
    static List<Integer>[] graph;
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        inout = new char[n + 1]; // 인덱스 1부터 의미가 있음

        String tmp = bf.readLine();
        for (int i = 0; i < n; i++) {
            inout[i + 1] = tmp.charAt(i);
        }

        graph = new List[n + 1];    // 인덱스 1부터 의미가 있음
        for (int i = 0; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        int answer = 0;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            graph[b].add(a);

            if (inout[a] == '1' && inout[b] == '1') {
                answer += 2;    // 실내 - 실내, 실외를 기준으로 dfs를 돌기 때문에 (실내 - 실내) 케이스를 여기서 고려해줌
            }
        }

        visit = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            if (inout[i] == '0' && !visit[i]) {
                int insideCnt = dfs(i);
                answer += (insideCnt * (insideCnt - 1));    // N개 중 2개를 뽑는 경우의 수
            }
        }

        System.out.println(answer);
    }

    private static int dfs(int node) {
        visit[node] = true;
        int insideCnt = 0;

        for (int next : graph[node]) {
            if (visit[next]) {
                continue;
            }

            if (inout[next] == '1') {   // 인전한 정점이 실내인 경우
                insideCnt++;
            } else if (inout[next] == '0'){    // 인전합 정점이 실외인 경우, 실외에서 실외는 갈 수 있음.
                insideCnt += dfs(next);
            }
        }

        return insideCnt;
    }
}
