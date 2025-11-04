package week30;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ17114 {
    static int M, N, O, P, Q, R, S, T, U, V, W;
    static int[][][][][][][][][][][] arr;
    static boolean[][][][][][][][][][][] visit;
    static int[][] d = new int[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0},

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0},

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1}
    };

    static Queue<Node> queue;
    static int greenTomatoCnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(bf.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        O = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        U = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        queue = new ArrayDeque<>();
        arr = new int[W][V][U][T][S][R][Q][P][O][N][M];
        visit = new boolean[W][V][U][T][S][R][Q][P][O][N][M];
        for (int w = 0; w < W; w++) {
            for (int v = 0; v < V; v++) {
                for (int u = 0; u < U; u++) {
                    for (int t = 0; t < T; t++) {
                        for (int s = 0; s < S; s++) {
                            for (int r = 0; r < R; r++) {
                                for (int q = 0; q < Q; q++) {
                                    for (int p = 0; p < P; p++) {
                                        for (int o = 0; o < O; o++) {
                                            for (int n = 0; n < N; n++) {
                                                st = new StringTokenizer(bf.readLine());
                                                for (int m = 0; m < M; m++) {
                                                    arr[w][v][u][t][s][r][q][p][o][n][m] = Integer.parseInt(st.nextToken());
                                                    if (arr[w][v][u][t][s][r][q][p][o][n][m] == 1) {
                                                        queue.add(new Node(w, v, u, t, s, r, q, p, o, n, m));
                                                        visit[w][v][u][t][s][r][q][p][o][n][m] = true;
                                                    } else if (arr[w][v][u][t][s][r][q][p][o][n][m] == 0) {
                                                        greenTomatoCnt++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int answer = bfs();

        System.out.println(greenTomatoCnt == 0 ? answer : -1);
    }

    private static int bfs() {
        int days = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node node = queue.poll();

                for (int[] direction : d) {
                    Node next = new Node();
                    next.w = node.w + direction[0];
                    next.v = node.v + direction[1];
                    next.u = node.u + direction[2];
                    next.t = node.t + direction[3];
                    next.s = node.s + direction[4];
                    next.r = node.r + direction[5];
                    next.q = node.q + direction[6];
                    next.p = node.p + direction[7];
                    next.o = node.o + direction[8];
                    next.n = node.n + direction[9];
                    next.m = node.m + direction[10];

                    // 범위를 벗어난 경우
                    if (OOB(next)) {
                        continue;
                    }

                    // 빈 칸인 경우
                    if (arr[next.w][next.v][next.u][next.t][next.s][next.r][next.q][next.p][next.o][next.n][next.m] == -1) {
                        continue;
                    }

                    // 이미 처리한 칸인 경우
                    if (visit[next.w][next.v][next.u][next.t][next.s][next.r][next.q][next.p][next.o][next.n][next.m]) {
                        continue;
                    }

                    greenTomatoCnt--;
                    visit[next.w][next.v][next.u][next.t][next.s][next.r][next.q][next.p][next.o][next.n][next.m] = true;
                    queue.add(next);
                }
            }

            days++;
        }

        return days - 1;
    }

    public static boolean OOB(Node node) {
        return node.w < 0 || node.v < 0 || node.u < 0 || node.t < 0
                || node.s < 0 || node.r < 0 || node.q < 0 || node.p < 0
                || node.o < 0 || node.n < 0 || node.m < 0
                || node.w >= W || node.v >= V || node.u >= U || node.t >= T
                || node.s >= S || node.r >= R || node.q >= Q || node.p >= P
                || node.o >= O || node.n >= N || node.m >= M;
    }

    private static class Node {
        int w, v, u, t, s, r, q, p, o, n, m;

        public Node() {
        }

        public Node(int w, int v, int u, int t, int s, int r, int q, int p, int o, int n, int m) {
            this.w = w;
            this.v = v;
            this.u = u;
            this.t = t;
            this.s = s;
            this.r = r;
            this.q = q;
            this.p = p;
            this.o = o;
            this.n = n;
            this.m = m;
        }
    }
}
