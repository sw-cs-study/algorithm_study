import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1467 {

    static int n, m;
    static List<Integer>[] graph;
    static int[] good;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        graph = new List[n + 1];
        for (int i = 0; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        st = new StringTokenizer(bf.readLine());
        for (int i = 1; i < n + 1; i++) {
            int boss = Integer.parseInt(st.nextToken());
            if (boss != -1) {
                graph[boss].add(i);
            }
        }

        good = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int goodBoss = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            good[goodBoss] += weight;
        }

        recur(1);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n + 1; i++) {
            sb.append(good[i]).append(' ');
        }
        System.out.println(sb);
    }

    private static void recur(int person) {
        for (int next : graph[person]) {
            good[next] += good[person];
            recur(next);
        }
    }
}