package week26;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2450 {

    static int n;
    static int[] arr;

    static int[] select = new int[3];
    static boolean[] visit = new boolean[3];

    // 입력으로 주어진 배열에서 1, 2, 3 수
    static int[] originCount = new int[3];

    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken()) - 1;
            originCount[arr[i]]++;
        }

        recur(0);
        System.out.println(ans);
    }

    private static void recur(int depth) {
        if (depth == 3) {
            int answer = solve(select);
            ans = Math.min(ans, answer);
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (visit[i]) {
                continue;
            }

            visit[i] = true;
            select[depth] = i;
            recur(depth + 1);
            select[depth] = -1;
            visit[i] = false;
        }
    }

    private static int solve(int[] select) {
        int[][] miss = new int[3][3];
        for (int i = 0; i < originCount[select[0]]; i++) {
            if (arr[i] != select[0]) {
                miss[arr[i]][select[0]]++;
            }
        }

        for (int i = originCount[select[0]]; i < originCount[select[0]] + originCount[select[1]]; i++) {
            if (arr[i] != select[1]) {
                miss[arr[i]][select[1]]++;
            }
        }

        for (int i = originCount[select[0]] + originCount[select[1]]; i < originCount[select[0]] + originCount[select[1]] + originCount[select[2]]; i++) {
            if (arr[i] != select[2]) {
                miss[arr[i]][select[2]]++;
            }
        }

        int answer = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue;
                }
                int changeCnt = Math.min(miss[i][j], miss[j][i]);
                answer += changeCnt;
                miss[i][j] -= changeCnt;
                miss[j][i] -= changeCnt;
            }
        }

        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += miss[i][j];
            }
        }

        if (sum != 0) {
            answer += (sum / 3) * 2;
        }

        return answer;
    }
}