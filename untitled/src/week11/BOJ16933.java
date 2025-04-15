package week11;

import java.io.*;
import java.util.*;

public class BOJ16933 {

    static int n;
    static int m;
    static int k;

    static int[][] arr;
    static boolean[][][] visited;

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            String row = bf.readLine();
            for (int j = 0; j < m; j++) {
                arr[i][j] = Character.getNumericValue(row.charAt(j));
            }
        }

        visited = new boolean[k + 1][n][m];
        System.out.println(bfs(0, 0));
    }

    static int bfs(int y, int x) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(new Point(y, x, 0, 1, 0));
        visited[0][y][x] = true;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (point.y == n - 1 && point.x == m - 1) {
                return point.cnt;
            }

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (!(0 <= ny && ny < n && 0 <= nx && nx < m)) {
                    continue;
                }

                if (arr[ny][nx] == 0) {
                    if (!visited[point.k][ny][nx]) {
                        visited[point.k][ny][nx] = true;
                        queue.add(new Point(ny, nx, point.k, point.cnt + 1, point.day == 0 ? 1 : 0));
                    }
                } else {
                    if (point.k < k) {  // 벽 부수기 가능
                        if (point.day == 0) {   // 낮
                            if (!visited[point.k + 1][ny][nx]) {
                                visited[point.k + 1][ny][nx] = true;
                                queue.add(new Point(ny, nx, point.k + 1, point.cnt + 1, 1));
                            }
                        } else {    // 밤
                            queue.add(new Point(point.y, point.x, point.k, point.cnt + 1, 0));
                        }
                    }
                }
            }
        }

        return -1;
    }

    static class Point {
        int y;
        int x;
        int k;
        int cnt;
        int day;

        Point(int y, int x, int k, int cnt, int day) {
            this.y = y;
            this.x = x;
            this.k = k;
            this.cnt = cnt;
            this.day = day;
        }
    }
}