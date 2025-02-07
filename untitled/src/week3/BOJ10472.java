package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class BOJ10472 {
    static int arr;
    static boolean[] visit;
    static int[] dy = {0, -1, 1, 0, 0};
    static int[] dx = {0, 0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int p = Integer.parseInt(bf.readLine());
        while (p-- > 0) {
            arr = 0;
            for (int i = 0; i < 3; i++) {
                String row = bf.readLine();
                for (int j = 0; j < 3; j++) {
                    // 검은색: 1, 흰색: 0
                    if (row.charAt(j) == '*') {
                        int point = (i * 3) + j;
                        arr |= (1 << point);
                    }
                }
            }

            visit = new boolean[1 << 9];
            sb.append(bfs()).append('\n');
        }
        System.out.println(sb);
    }

    private static int bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        visit[0] = true;

        int click = 0;
        A: while (!queue.isEmpty()) {
            // 레벨 별 탐색을 위한 size
            int size = queue.size();
            while (size-- > 0) {
                int now = queue.poll();
                if (now == arr) {
                    break A;
                }

                // 9칸 탐색
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int next = getNext(now, i, j);

                        // 방문했는지 판별
                        if (!visit[next]) {
                            visit[next] = true;
                            queue.add(next);
                        }
                    }
                }
            }
            click++;
        }

        return click;
    }

    private static int getNext(int now, int i, int j) {
        int next = now;
        for (int k = 0; k < 5; k++) {
            int ny = i + dy[k];
            int nx = j + dx[k];

            if (OOB(ny, nx)) {
                continue;
            }

            int point = (ny * 3) + nx;
            // 흑 -> 백
            if ((next & (1 << point)) > 0) {
                next &= ~(1 << point);
            } else {
                // 백 -> 흑
                next |= (1 << point);
            }
        }
        return next;
    }

    private static boolean OOB(int y, int x) {
        return y < 0 || y >= 3 || x < 0 || x >= 3;
    }
}
