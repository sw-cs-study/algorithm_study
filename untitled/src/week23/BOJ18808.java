package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ18808 {

    static int n, m, k;
    static int[][] paper;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        paper = new int[n][m];
        while (k-- > 0) {
            st = new StringTokenizer(bf.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            int[][] sticker = new int[y][x];
            for (int i = 0; i < y; i++) {
                st = new StringTokenizer(bf.readLine());
                for (int j = 0; j < x; j++) {
                    sticker[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            attempt(sticker);
        }

        System.out.println(getCount(paper));
    }

    private static int getCount(int[][] sticker) {
        int count = 0;
        for (int[] row : sticker) {
            for (int value : row) {
                if (value == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    private static void attempt(int[][] sticker) {
        // 총 4번 반복 (90도 돌리기)
        for (int i = 0; i < 4; i++) {
            int y = sticker.length; // 세로 길이
            int x = sticker[0].length;  // 가로 길이

            for (int j = 0; j < n - y + 1; j++) {
                for (int l = 0; l < m - x + 1; l++) {
                    if (attach(sticker, j, l)) {
                        return;
                    }
                }
            }

            sticker = rotate90ClockWise(sticker);
        }
    }

    private static int[][] rotate90ClockWise(int[][] sticker) {
        int y = sticker.length; // 세로 길이
        int x = sticker[0].length;  // 가로 갈이

        int[][] rotated = new int[x][y];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                rotated[j][y - 1 - i] = sticker[i][j];
            }
        }

        return rotated;
    }

    private static boolean attach(int[][] sticker, int sy, int sx) {
        int y = sticker.length;
        int x = sticker[0].length;

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                // 이미 붙어있는 경우
                if (paper[sy + i][sx + j] == 1 && sticker[i][j] == 1) {
                    return false;
                }
            }
        }

        // 스티커 붙이기
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (sticker[i][j] == 1) {
                    paper[sy + i][sx + j] = 1;
                }
            }
        }

        return true;
    }
}
