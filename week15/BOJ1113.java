/*
 * 수영장 만들기
 * bfs가 딱 떠오름
 * 둘러사인 공간을 측정하고 안을 채우려고 했으나, 어떤 공간을 둘러싼 벽의 크기도 다 달라서 어려움
 * 벽 높이가 1~9까지 밖에 안되므로 각 높이에 맞춰 bfs를 돌리고 물을 하나씩 채우도록 만들기 => 시간 복잡도 : O(10n^2) -> 10 * 2500 * 2500 -> 62500000
 * 가장 테두리로 물이 넘치는 경우는 특정높이로 bfs를 돌다가 가장 바깥 테두리에 닿으면 해당 
 * -> 굳이 안에 있는 애들로 bfs를 돌려야할까?? => 겉 테두리를 돌면서 해당 특정 높이에 해당하면 bfs로 이어진 낮은 숫자들를 특정 높이로 올리기 -> 그러고 안에 있는 높이들 중에 특정 높이보다 작으면 답에 더하기
 */

package week15;

import java.io.*;
import java.util.*;

public class BOJ1113 {
    private static int n, m, answer;
    private static int[][] board;
    private static Point[] borders;
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][m];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j) - '0';
            }
        }
        borders = new Point[2 * n + 2 * m - 4];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            borders[idx] = new Point(i, 0);
            idx++;
            borders[idx] = new Point(i, m-1);
            idx++;
        }
        for (int j = 1; j < m-1; j++) {
            borders[idx] = new Point(0, j);
            idx++;
            borders[idx] = new Point(n-1, j);
            idx++;
        }
        answer = 0;

        

        for (int height = 1; height < 10; height++) {
            // 물 새는 곳 제거
            bfs(height);

            // 물 높이 채우기
            for (int i = 1; i < n-1; i++) {
                for (int j = 1; j < m-1; j++) {
                    if (board[i][j] < height) {
                        answer++;
                        board[i][j]++;
                    }
                }
            }
        }

        System.out.println(answer);
        

    }

    private static void bfs(int height) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];
        for (Point p : borders) {
            int x = p.x;
            int y = p.y;
            if (board[x][y] >= height) continue;
            queue.offer(new int[]{x, y});
            visited[x][y] = true;
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0];
            int cy = cur[1];
            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m || visited[nx][ny]) continue;
                if (board[nx][ny] >= height) continue;
                board[nx][ny] = height;
                queue.offer(new int[]{nx, ny});
                visited[nx][ny] = true;
            }
        }
    }

    private static class Point {
        public int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
