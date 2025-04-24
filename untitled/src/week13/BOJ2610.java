package week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * union-find
 * floyd warshall
 * - 가지 못하는 곳은 inf
 *
 * 시간복잡도
 * union-find 100
 * floyd warshall => (100 * 100 * 100)
 */
public class BOJ2610 {

    static final int MAX = 100_000_000;

    static int n;   // 참석자 수
    static int[] parent;
    static int[][] arr;

    static List<Integer>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());

        parent = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            parent[i] = i;
        }

        arr = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == j) {
                    continue;
                }
                arr[i][j] = MAX;
            }
        }

        int m = Integer.parseInt(bf.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[a][b] = 1;
            arr[b][a] = 1;

            union(a, b);
        }

        // floyd warshall
        for (int k = 1; k < n + 1; k++) {
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    arr[i][j] = Math.min(arr[i][j], arr[i][k] + arr[k][j]);
                }
            }
        }

        // 참가자 한 명씩 탐색
        // <해당 참가자가 속한 루트 노드 : {의사전달시간, 해당 참가자}>
        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 1; i < n + 1; i++) {
            int p = find(i);

            int max = Integer.MIN_VALUE;
            for (int j = 1; j < n + 1; j++) {
                if (arr[i][j] == MAX) { // 연결되지 않은 부분
                    continue;
                }

                // 최대 의사전달시간
                max = Math.max(max, arr[i][j]);
            }

            if (map.containsKey(p)) {
                // 저장된 의사전달시간 > 현재 구한 의사전달시간
                if (map.get(p)[0] > max) {
                    map.put(p, new int[]{max, i});
                }
            } else {
                map.put(p, new int[]{max, i});
            }
        }

        System.out.println(map.size());
        List<Integer> answer = new ArrayList<>();
        for (int key : map.keySet()) {
            answer.add(map.get(key)[1]);
        }

        Collections.sort(answer);
        for (int ans : answer) {
            System.out.println(ans);
        }
    }

    private static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa == pb) {
            return;
        }

        if (pa < pb) {
            parent[pb] = pa;
        } else {
            parent[pa] = pb;
        }
    }

    private static int find(int a) {
        if (parent[a] != a) {
            parent[a] = find(parent[a]);
        }

        return parent[a];
    }
}
