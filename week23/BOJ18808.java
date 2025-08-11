package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ18808 {
    private static int n, m, k;
    private static int[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new int[n][m];
        for (int t = 0; t < k; t++) {
            // 스티커 받기
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int[][] sticker = new int[r][c];
            for (int i = 0; i < r; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < c; j++) {
                    sticker[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            // 스티커 조건에 맞게 넣고, 안되면 4번까지 시계 방향으로 돌리기
            boolean done = false;
            
            for (int rotate = 0; rotate < 4; rotate++) {
                
                // 빈공간
                Point p = findBlank(new Point(0, 0), sticker);
                while (!done && p.x < n) {
                    // 스티커 채울 수 있는지 검사 + 되면 채우고 break
                    if (checkBlankWithSticker(p, sticker)) {
                        fillSticker(p, sticker);
                        done = true;
                        break;
                    }
                    p = moveRight(p);
                }
                if (done) break;
                // 돌리기
                sticker = rotateSticker(sticker);
            }

        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 1) answer++;
            }
        }
        System.out.println(answer);
    }

    private static class Point {
        int x, y; 
        Point (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    // 왼쪽 위부터 오른쪽에서 아래 순서로 현재 board 위치에 스티커 좌상단이 들어갈 수 있는지 확인
    private static Point findBlank(Point p, int[][] sticker) {
        for (int i = p.x; i < n; i++) {
            for (int j = p.y; j < m; j++) {
                if (board[i][j] == 0) return new Point(i, j);
                if (board[i][j] == 1 && sticker[0][0] == 0) return new Point(i, j);
            }
        }

        return new Point(0 ,0);
    }

    private static Point moveRight(Point p) {
        if (p.y + 1 == m) return new Point(p.x+1, 0);
        return new Point(p.x, p.y+1);
    }

    // 해당 위치부터 현재 스티커 채우는게 가능한지 체크
    private static boolean checkBlankWithSticker(Point p, int[][] sticker) {
        int h = sticker.length, w = sticker[0].length;
        int r, c;
        for (int i = 0; i < h; i++) {
            r = p.x + i;
            if (r < 0 || r >= n) return false;
            for (int j = 0; j < w; j++) {
                c = p.y + j;
                if (c < 0 || c >= m) return false;
                if (sticker[i][j] == 1 && board[r][c] == 1) return false; 
            }
        }
        return true;
    }

    // 시계 방향으로 90도 돌리기
    private static int[][] rotateSticker(int[][] sticker) {
        int h = sticker.length, w = sticker[0].length;
        int[][] ret = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ret[i][j] = sticker[h-1-j][i];
            }
        }
        return ret;
    }

    // 채우기
    private static void fillSticker(Point p, int[][] sticker) {
        int h = sticker.length, w = sticker[0].length;
        int r, c;
        for (int i = 0; i < h; i++) {
            r = p.x + i;
            for (int j = 0; j < w; j++) {
                c = p.y + j;
                if (sticker[i][j] == 1) board[r][c] = 1;
            }
        }
    }
}
