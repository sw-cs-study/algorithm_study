package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ17370 {

    static int n;
    static int[][] arr;
    static boolean[][] visit;
    static int answer;

    // 위(0), 왼 대각 위(1), 오른 대각 위(2), 아래(3), 왼 대각 아래(4), 오른 대각 아래(5)
    static int[][] nextVector = new int[][]{
            {1, 2},
            {4, 0},
            {0, 5},
            {4, 5},
            {3, 1},
            {3, 2}
    };

    static int[] dy = {-1, -1, -1, 1, 1, 1};
    static int[] dx = {0, -1, 1, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());

        arr = new int[100][100];
        visit = new boolean[100][100];
        visit[51][50] = true;

        answer = 0;
        recur(50, 50, 0, 0);

        System.out.println(answer);
    }

    private static void recur(int y, int x, int dir, int cnt) {
        if (cnt == n) { // 더 갈 수 없음
            if (visit[y][x]) {
                answer++;
            }
            return;
        }

        if (visit[y][x]) {
            return;
        }

        visit[y][x] = true;

        for (int i = 0; i < 2; i++) {
            int nd = nextVector[dir][i];
            int ny = y + dy[nd];
            int nx = x + dx[nd];

            recur(ny, nx, nd, cnt + 1);
        }

        visit[y][x] = false;
    }
}
