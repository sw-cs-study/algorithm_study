package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1987 {
    static int r, c; // 세로, 가로
    static char[][] arr; // 보드판
    static boolean[] visit; // 방문체크
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static int answer = 1;  // 가장 적게 지날 수 있는 칸 수는 1

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        arr = new char[r][c];
        for (int i = 0; i < r; i++) {
            String row = bf.readLine();
            for (int j = 0; j < c; j++) {
                arr[i][j] = row.charAt(j);
            }
        }

        visit = new boolean[27];
        visit[arr[0][0] - 'A'] = true;
        recur(0, 0, 1);
        System.out.println(answer);
    }

    // 다음 칸이 중복인 경우 들어가지 않고 처리 (depth -> 1)
    private static void recur(int y, int x, int depth) {
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (OOB(ny, nx) || visit[arr[ny][nx] - 'A']) {  // 범위체크
                answer = Math.max(answer, depth);
                continue;
            }

            visit[arr[ny][nx] - 'A'] = true;  // 방문처리
            recur(ny, nx, depth + 1); // 다음칸 탐색
            visit[arr[ny][nx] - 'A'] = false; // 방문처리 해제
        }
    }

    private static boolean OOB(int y, int x) {
        return y < 0 || y >= r || x < 0 || x >= c;
    }
}
