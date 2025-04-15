package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ17143 {

    static int r, c, m; // 세로, 가로, 상어의 수
    static Shark[][] arr;

    // 위, 아래, 오른쪽, 왼쪽
    static int[] dy = {0, -1, 1, 0, 0};
    static int[] dx = {0, 0, 0, 1, -1};

    // 낚시왕이 잡은 상어 크기의 합
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new Shark[r + 1][c + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            arr[r][c] = new Shark(r, c, s, d, z);
        }

        int fisherPos = 1;
        while (fisherPos < c + 1) {
            // 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다. 상어를 잡으면 격자판에서 잡은 상어가 사라진다.
            getShark(fisherPos);

            // 상어가 이동한다.
            move();

            // 낚시왕이 오른쪽으로 한 칸 이동한다.
            fisherPos++;
        }

        System.out.println(answer);
    }

    private static void move() {
        // 맵에 있던 상어들을 리스트에 모으기
        List<Shark> sharkList = new ArrayList<>();
        for (int i = 1; i < r + 1; i++) {
            for (int j = 1; j < c + 1; j++) {
                if (arr[i][j] != null) {
                    sharkList.add(arr[i][j]);
                    arr[i][j] = null;
                }
            }
        }

        // 상어 한 마리씩 이동시키기
        for (Shark shark : sharkList) {
            moveShark(shark);
        }

        // 리스트에 있는 상어들을 맵에 재배치
        for (Shark shark : sharkList) {
            // 비어있는 칸이라면 상어를 넣는다.
            if (arr[shark.r][shark.c] == null) {
                arr[shark.r][shark.c] = shark;
            } else {
                // 해당 칸에 현재 넣으려고 하는 상어보다 작은 상어가 있다면 현재 넣으려고 하는 상어로 바꿔준다.
                if (arr[shark.r][shark.c].z < shark.z) {
                    arr[shark.r][shark.c] = shark;
                }
            }
        }
    }

    private static void moveShark(Shark shark) {
        int leftDistance;

        if (shark.d == 1 || shark.d == 2) {
            leftDistance = shark.s % ((r - 1) * 2);
        } else {
            leftDistance = shark.s % ((c - 1) * 2);
        }

        while (leftDistance > 0) {
            // 다음 칸
            int nr = shark.r + dy[shark.d];
            int nc = shark.c + dx[shark.d];

            // 범위 벗어난 경우
            if (OOB(nr, nc)) {
                // 방향 전환
                shark.d = getNextDir(shark.d);

                // 방향 전환 후 다음 칸
                nr = shark.r + dy[shark.d];
                nc = shark.c + dx[shark.d];
            }

            // 다음 칸으로 이동
            shark.r = nr;
            shark.c = nc;

            // 남은 거리 빼기
            leftDistance--;
        }
    }

    private static int getNextDir(int curDir) {
        if (curDir == 1) {
            return 2;
        } else if (curDir == 2) {
            return 1;
        } else if (curDir == 3) {
            return 4;
        } else {
            return 3;
        }
    }

    private static boolean OOB(int y, int x) {
        return y >= r + 1 || y < 1 || x >= c + 1 || x < 1;
    }

    private static void getShark(int fisherPos) {
        for (int i = 1; i < r + 1; i++) {
            if (arr[i][fisherPos] == null) {
                continue;
            }

            answer += arr[i][fisherPos].z;
            arr[i][fisherPos] = null;
            break;
        }
    }

    private static class Shark {
        int r;  // 세로 좌표
        int c;  // 가로 좌표
        int s;  // 속력
        int d;  // 이동 방향
        int z;  // 크기

        public Shark(int r, int c, int s, int d, int z) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }

}
