package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ17265 {
    static int n;
    static char[][] arr;

    static int[] dy = {1, 0};
    static int[] dx = {0, 1};

    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        arr = new char[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = st.nextToken().charAt(0);
            }
        }

        solve(0, 0, Character.getNumericValue(arr[0][0]));
        System.out.println(max + " " + min);
    }

    private static void solve(int y, int x, int val) {
        if (y == n - 1 && x == n - 1) {
            max = Math.max(max, val);
            min = Math.min(min, val);
            return;
        }

        for (int i = 0; i < 2; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (OOB(ny, nx)) {
                continue;
            }

            // 다음 칸이 숫자이면 현재 칸은 연산자
            if (Character.isDigit(arr[ny][nx])) {
                int next = Character.getNumericValue(arr[ny][nx]);
                solve(ny, nx, calc(val, next, arr[y][x]));
            } else {
                solve(ny, nx, val);
            }
        }
    }

    private static int calc(int v1, int v2, char op) {
        if (op == '+') {
            return v1 + v2;
        } else if (op == '-') {
            return v1 - v2;
        } else {
            return v1 * v2;
        }
    }

    private static boolean OOB(int y, int x) {
        return y < 0 || y >= n || x < 0 || x >= n;
    }
}
