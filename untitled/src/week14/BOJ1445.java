package week14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1445 {

    static int n, m;
    static char[][] arr;

    static int sy = 0;
    static int sx = 0;
    static int fy = 0;
    static int fx = 0;

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new char[n][m];

        for (int i = 0; i < n; i++) {
            String row = bf.readLine();
            for (int j = 0; j < m; j++) {
                arr[i][j] = row.charAt(j);
                if (arr[i][j] == 'S') {
                    sy = i;
                    sx = j;
                } else if (arr[i][j] == 'F') {
                    fy = i;
                    fx = j;
                }
            }
        }

        Node node = dijkstra();
        System.out.println(node.trashCount + " " + node.besideCount);
    }

    private static Node dijkstra() {
        int[][] trash = new int[n][m];
        int[][] beside = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(trash[i], Integer.MAX_VALUE);
            Arrays.fill(trash[i], Integer.MAX_VALUE);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(
                (n1, n2) -> {
                    if (n1.trashCount == n2.trashCount) {
                        return Integer.compare(n1.besideCount, n2.besideCount);
                    }

                    return Integer.compare(n1.trashCount, n2.trashCount);
                }
        );

        pq.add(new Node(sy, sx, 0, 0));
        trash[sy][sx] = 0;
        beside[sy][sx] = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (now.y == fy && now.x == fx) {
                return now;
            }

            for (int i = 0; i < 4; i++) {
                int ny = now.y + dy[i];
                int nx = now.x + dx[i];

                if (OOB(ny, nx)) {
                    continue;
                }

                int nextTrashCount = now.trashCount;
                int nextBesideCount = now.besideCount;

                if (arr[ny][nx] == 'g') {
                    nextTrashCount++;
                } else if (arr[ny][nx] == '.') {
                    boolean isBeside = false;
                    for (int j = 0; j < 4; j++) {
                        int nny = ny + dy[j];
                        int nnx = nx + dx[j];

                        if (OOB(nny, nnx)) {
                            continue;
                        }

                        if (arr[nny][nnx] == 'g') {
                            isBeside = true;
                            break;
                        }
                    }

                    if (isBeside) {
                        nextBesideCount++;
                    }
                }

                if (trash[ny][nx] < nextTrashCount) {
                    continue;
                }

                if (trash[ny][nx] == nextTrashCount && beside[ny][nx] <= nextBesideCount) {
                    continue;
                }

                trash[ny][nx] = nextTrashCount;
                beside[ny][nx] = nextBesideCount;

                pq.add(new Node(ny, nx, nextTrashCount, nextBesideCount));
            }
        }

        return null;
    }

    private static boolean OOB(int y, int x) {
        return y >= n || y < 0 || x >= m || x < 0;
    }

    private static class Node {
        int y;
        int x;
        int trashCount;
        int besideCount;

        public Node(int y, int x, int trashCount, int besideCount) {
            this.y = y;
            this.x = x;
            this.trashCount = trashCount;
            this.besideCount = besideCount;
        }
    }
}