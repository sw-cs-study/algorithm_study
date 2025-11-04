package week30;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ23290 {
    private static final int N = 5;

    // 물고기 방향 (1-based), 왼쪽부터 시계방향
    private static final int[] fishDy = {0, 0, -1, -1, -1, 0, 1, 1, 1};
    private static final int[] fishDx = {0, -1, -1, 0, 1, 1, 1, 0, -1};

    // 상어 방향 (1-based), 상 좌 하 우
    private static final int[] sharkDy = {0, -1, 0, 1, 0};
    private static final int[] sharkDx = {0, 0, -1, 0, 1};

    static List<Integer>[][] map;
    static int[][] smell;

    static int sy, sx;  // 상어 위치
    static int M, S;

    // 백트래킹용
    static int maxCnt;
    static int[] pick;
    static int[] sharkMove;
    static boolean[][] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());   // 물고기 수
        S = Integer.parseInt(st.nextToken());   // 마법 연습 횟수

        map = new List[N][N];
        smell = new int[N][N];  // 물고기 냄새, 0이 아닌 경우 냄새
        for (int i = 1; i < N; i++) {
            for(int j = 1; j < N; j++){
                map[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            map[y][x].add(d);
        }

        st = new StringTokenizer(br.readLine());
        sy = Integer.parseInt(st.nextToken());
        sx = Integer.parseInt(st.nextToken());

        while(S-- > 0) {
            play();
        }

        int fishCount = 0;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                fishCount += map[i][j].size();
            }
        }

        System.out.println(fishCount);
    }

    private static void play() {
        // 물고기 복사
        List<Integer>[][] original = new List[N][N];
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                original[i][j] = new ArrayList<>(map[i][j]);
            }
        }

        // 물고기 이동
        List<Integer>[][] moved = new List[N][N];
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                moved[i][j] = new ArrayList<>();
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                for (int d : map[i][j]) {
                    int dir = d;
                    boolean movedFlag = false;
                    for (int k = 0; k < 8; k++) {
                        int ny = i + fishDy[dir];
                        int nx = j + fishDx[dir];

                        if (!OOB(ny, nx)    // 격자 안
                                && !(ny == sy && nx == sx)  // 상어 위치 X
                                && smell[ny][nx] == 0)  // 물고기 냄새 X
                        {
                            moved[ny][nx].add(dir);
                            movedFlag = true;
                            break;
                        }
                        dir = (dir == 1 ? 8 : dir - 1);
                    }

                    // 이동하지 못한 물고기
                    if(!movedFlag){
                        moved[i][j].add(d);
                    }
                }
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                map[i][j].clear();
                map[i][j].addAll(moved[i][j]);
            }
        }

        // 상어 이동 (백트래킹)
        maxCnt = -1;
        pick = new int[3];
        sharkMove = new int[3];
        visit = new boolean[N][N];
        dfs(0, sy, sx, 0);

        // 선택된 이동 적용
        for(int dir : sharkMove){
            sy += sharkDy[dir];
            sx += sharkDx[dir];
            if(!map[sy][sx].isEmpty()){
                map[sy][sx].clear();
                smell[sy][sx] = 3;
            }
        }
        // 냄새 감소
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (smell[i][j] > 0) {
                    smell[i][j]--;
                }
            }
        }

        // 복제 마법 적용
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                map[i][j].addAll(original[i][j]);
            }
        }
    }

    private static boolean OOB(int y, int x) {
        return y < 1 || y >= N || x < 1 || x >= N;
    }

    private static void dfs(int depth, int y, int x, int cnt){
        if (depth == 3) {
            if (cnt > maxCnt) {
                maxCnt = cnt;
                System.arraycopy(pick, 0, sharkMove, 0, 3);
            }
            return;
        }

        for (int d = 1; d < 5; d++) {
            int ny = y + sharkDy[d];
            int nx = x + sharkDx[d];

            if (OOB(ny, nx)) {
                continue;
            }

            pick[depth] = d;

            if(!visit[ny][nx]){
                visit[ny][nx] = true;
                dfs(depth + 1, ny, nx, cnt + map[ny][nx].size());
                visit[ny][nx] = false;
            } else {
                dfs(depth + 1, ny, nx, cnt);
            }
        }
    }
}
