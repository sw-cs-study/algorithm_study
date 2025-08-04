package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ16957 {
    private static int r, c;
    private static int[][] board, countBoard;
    private static int[][] delta = new int[][]{
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        board = new int[r][c];
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        PriorityQueue<Point> queue = new PriorityQueue<>((Point a, Point b)->board[b.x][b.y]-board[a.x][a.y]); // 큰값이 먼저 나오도록
        countBoard = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                countBoard[i][j] = 1;
                queue.add(new Point(i, j));
            }
        }

        while(!queue.isEmpty()) {
            Point cur = queue.poll();
            int nextX = cur.x, nextY = cur.y, value = board[cur.x][cur.y];
            // 8방 중에 가장 작은 곳의 좌표 구하기
            for (int[] d : delta) {
                int tempX = cur.x + d[0], tempY = cur.y + d[1];
                if (!check(tempX, tempY)) continue;
                int tempV = board[tempX][tempY];
                if (tempV < value) {
                    nextX = tempX;
                    nextY = tempY;
                    value = tempV;
                }
            }
            // 이동안하면 건너뜀
            if (nextX == cur.x && nextY == cur.y) continue;
            // 한번에 옮기기
            countBoard[nextX][nextY] += countBoard[cur.x][cur.y];
            countBoard[cur.x][cur.y] = 0;
            queue.add(new Point(nextX, nextY));
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                answer.append(countBoard[i][j]);
                if (j < c - 1) answer.append(" ");
            }
            if (i < r - 1) answer.append("\n");
        }
        System.out.println(answer);
    }

    private static boolean check(int x, int y) {
        if (x < 0 || x >= r) return false;
        if (y < 0 || y >= c) return false;
        return true;
    }

    private static class Point {
        private int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
