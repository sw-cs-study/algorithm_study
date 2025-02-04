package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1520 {
    static int m, n;
    static int[][] arr;
    static int[][] cnt;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        arr = new int[m][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        cnt = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cnt[i], -1);
        }

        System.out.println(start(0, 0));
    }

    private static int start(int y, int x) {
        if (y == m - 1 && x == n - 1) {
            return 1;
        }

        if (cnt[y][x] != -1) {
            return cnt[y][x];
        }

        int ret = 0;
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (OOB(ny, nx)) {
                continue;
            }

            if (arr[y][x] > arr[ny][nx]) {
                ret += start(ny, nx);
            }
        }

        cnt[y][x] = ret;
        return cnt[y][x];
    }

    private static boolean OOB(int y, int x) {
        return y >= m || y < 0 || x >= n || x < 0;
    }
}