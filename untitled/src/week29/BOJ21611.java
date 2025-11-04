package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// 마법사 상어는 ((N + 1) / 2, (N + 1) / 2)에 존재함. (정중앙에 존재한단 말)
// 실선은 벽, 점섬은 벽이 아님
// 칸에 적혀있는 수는 칸의 번호
// 구슬은 1, 2, 3번 구슬이 있다.
// 블리자드 마법을 시전하려면 방향 d와 거리 s를 정해야함.
// 방향은 4방향(상하좌우)를 정수 1, 2, 3, 4로 표현함
// 상어는 di 방향으로 거리가 si 이하인 모든 칸에 얼음 파편을 던져 그 칸에 있는 구슬을 모두 파괴함
// 구슬이 파괴되면 그 칸은 구슬이 들어있지 않은 빈 칸이 됨
// 얼음 파편은 벽 위로 떨어지기 떄문에 벽은 파괴되지 않음.
public class BOJ21611 {
    static int n;   // 한 변의 길이, 무조건 홀수
    static int m;   // 마법 횟수
    static int[][] arr; // 격자
    static int[] dy = {0, -1, 1, 0, 0}; // 인덱스 1부터 사용
    static int[] dx = {0, 0, 0, -1, 1}; // 인덱스 1부터 사용
    static int sy;  // 상어위치 y값
    static int sx;  // 상어위치 x값
    static Map<Integer, Integer> dirMap = Map.of(1, 3, 2, 4, 3, 2, 4, 1);
    static int[] score = new int[4];    // 폭발 스코어(인덱스 1부터)

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 격자 입력
        arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 상어 위치 7로 초기화
        sy = n / 2;
        sx = n / 2;
        arr[sy][sx] = 7;

        // 마법 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int d = Integer.parseInt(st.nextToken());   // 방향
            int s = Integer.parseInt(st.nextToken());   // 거리

            magic(d, s);
        }

        int answer = score[1] + (score[2] * 2) + (score[3] * 3);
        System.out.println(answer);
    }

    private static void magic(int d, int s) {
        // d 방향으로 거리 s 이하인 모든 칸에 있는 구슬 파괴
        destroy(d, s);

        // 파괴된 곳을 채우기 위한 구슬 이동 (시계 방향으로 채워야함)
        move();

        // 4개 이상 연속하는 구슬은 폭발
        while (boom()) {
            // 폭발이 일어난 경우 빈 칸을 채우기 위해 구슬 이동
            move();
        }

        // 하나의 그룹은 두 개의 구슬 A와 B로 변한다.
        split();
    }

    // 하나의 그룹은 두 개의 구슬 A와 B로 변한다.
    // A: 그룹에 들어있는 구슬의 개수
    // B: 그룹을 이루고 있는 구슬의 번호
    // ex) 1, 1, 1 => A: 3, B: 1
    // ex) 2 => A: 1, B: 2
    private static void split() {
        // 기존 구슬들을 저장하는 리스트
        List<Integer> marbleList = new ArrayList<>();

        int y = sy;
        int x = sx;
        int amount = 1; // 한 방향으로 움직이는 횟수
        int dir = 3;
        int count = 0;
        int prevMarble = arr[sy][sx - 1];

        A: while (x > -1) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < amount; j++) {
                    y += dy[dir];
                    x += dx[dir];

                    if (x == -1) {
                        break A;
                    }

                    // 구슬인 경우
                    if (arr[y][x] != 0) {
                        if (arr[y][x] == prevMarble) {
                            count++;
                        } else {
                            marbleList.add(count);
                            marbleList.add(prevMarble);

                            prevMarble = arr[y][x];
                            count = 1;
                        }
                    } else { // 이전 단계에서 빈 칸을 다 채웠으므로 빈 칸을 만났을 경우 더 이상 탐색하지 않아도 됨. (더 탐색해도 구슬이 없음)
                        marbleList.add(count);
                        marbleList.add(prevMarble);
                        break A;
                    }
                }
                // 한 방향으로 탐색 끝. 방향 전환.
                dir = dirMap.get(dir);
            }
            // 탐색 거리 1 증가
            amount++;
        }

        // 새로운 구슬 배열로 채우기
        pullMarble(marbleList);
    }

    private static void pullMarble(List<Integer> marbleList) {
        int y = sy;
        int x = sx;
        int amount = 1;
        int dir = 3;
        int index = 0;

        A: while (x > -1) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < amount; j++) {
                    y += dy[dir];
                    x += dx[dir];

                    if (x == -1) {
                        break A;
                    }

                    if (index == marbleList.size()) {
                        arr[y][x] = 0;
                    } else {
                        arr[y][x] = marbleList.get(index);
                        index++;
                    }
                }
                // 한 방향으로 탐색 끝. 방향 전환.
                dir = dirMap.get(dir);
            }
            // 탐색 거리 1 증가
            amount++;
        }
    }

    // 4개 이상 연속하는 구슬은 폭발
    // 폭발하면 true
    private static boolean boom() {
        boolean isBoom = false;

        // 기존 구슬들을 저장하는 리스트
        List<Integer> marbleList = new ArrayList<>();

        int y = sy;
        int x = sx;
        int amount = 1; // 한 방향으로 움직이는 횟수
        int dir = 3;


        int count = 0;
        int prevMarble = arr[sy][sx - 1];

        A: while (x > -1) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < amount; j++) {
                    y += dy[dir];
                    x += dx[dir];

                    if (x == -1) {
                        break A;
                    }

                    // 구슬인 경우
                    if (arr[y][x] != 0) {
                        if (arr[y][x] == prevMarble) {
                            count++;
                        } else {
                            if (count >= 4) {
                                // 폭발
                                score[prevMarble] += count;
                                isBoom = true;
                            } else {
                                // 폭발하지 않은 경우, 구슬을 다시 채워야함.
                                for (int k = 0; k < count; k++) {
                                    marbleList.add(prevMarble);
                                }
                            }

                            prevMarble = arr[y][x];
                            count = 1;
                        }
                    } else { // 이전 단계에서 빈 칸을 다 채웠으므로 빈 칸을 만났을 경우 더 이상 탐색하지 않아도 됨. (더 탐색해도 구슬이 없음)
                        if (count >= 4) {
                            score[prevMarble] += count;
                            isBoom = true;
                        } else {
                            for (int k = 0; k < count; k++) {
                                marbleList.add(prevMarble);
                            }
                        }
                        break A;
                    }
                }
                // 한 방향으로 탐색 끝. 방향 전환.
                dir = dirMap.get(dir);
            }
            // 탐색 거리 1 증가
            amount++;
        }

        // 새로운 구슬 배열로 채우기
        pullMarble(marbleList);

        return isBoom;
    }

    // 파괴된 곳을 채우기 위한 구슬 이동 (시계 방향으로 채워야함)
    // 파괴된 곳은 0
    private static void move() {
        List<Integer> marbleList = new ArrayList<>();

        int y = sy;
        int x = sx;
        int amount = 1; // 한 방향으로 움직이는 횟수
        int dir = 3;

        A: while (x > -1) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < amount; j++) {
                    y += dy[dir];
                    x += dx[dir];

                    if (x == -1) {
                        break A;
                    }

                    if (arr[y][x] != 0) {
                        marbleList.add(arr[y][x]);
                        arr[y][x] = 0;
                    }
                }
                // 한 방향으로 탐색 끝. 방향 전환.
                dir = dirMap.get(dir);
            }
            // 탐색 거리 1 증가
            amount++;
        }

        // 새로운 구슬 배열로 채우기
        pullMarble(marbleList);
    }

    // d 방향으로 거리 s 이하인 모든 칸에 있는 구슬 파괴, 파괴된 곳은 0
    private static void destroy(int d, int s) {
        for (int i = 1; i <= s; i++) {
            arr[sy + (dy[d] * i)][sx + (dx[d] * i)] = 0;
        }
    }

    // 테스트용
    private static void printArr() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}