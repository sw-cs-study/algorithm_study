package week15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 메모리 제한
 * java: 256MB
 * 1024 * 1024 * 256 byte = 67,108,864
 *
 * String => 헤더(12) + hash(4) + char[] 참조(4) + char[] 헤더(12) + char[] 값(1 x 9, 9, compact string)
 * => 41
 *
 * 맵에 등장할 수 있는 패턴 수 => 9! => 362,880
 *
 * 41 * 362,880 = 14,878,080
 */
public class BOJ1525 {

    private static final String end = "123456780";
    private static final int n = 3;
    private static Map<String, Integer> visit;  // <맵 : 이동횟수>
    private static int[] dy = {-1, 1, 0, 0};
    private static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < n; j++) {
                sb.append(st.nextToken());
            }
        }

        visit = new HashMap<>();
        System.out.println(bfs(sb.toString()));
    }

    private static int bfs(String start) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        visit.put(start, 0);

        while (!queue.isEmpty()) {
            String now = queue.poll();

            // 정답에 도달한 경우
            if (now.equals(end)) {
                return visit.get(now);
            }

            // 현재 0이 위치한 인덱스 추출
            int index = now.indexOf("0");

            // 0 인덱스 기반으로 (y, x) 값 계산
            int y = index / n;
            int x = index % n;

            // 0 위치에서 4방 탐색을 통해 다음 0의 위치를 정함
            for (int i = 0; i < 4; i++) {
                int ny = y + dy[i];
                int nx = x + dx[i];

                // 범위를 벗어난 경우
                if (OOB(ny, nx)) {
                    continue;
                }

                // 다음 0의 위치 계산
                int nextZeroPosition = ny * n + nx;
                char tmp = now.charAt(nextZeroPosition);

                // 다음 맵 계산
                String next = now.replace(tmp, 'a');
                next = next.replace('0', tmp);
                next = next.replace('a', '0');

                // 이미 탐색한 경우
                if (visit.containsKey(next)) {
                    continue;
                }

                // 탐색하지 않은 경우
                queue.add(next);
                visit.put(next, visit.get(now) + 1);
            }
        }

        return -1;
    }

    private static boolean OOB(int y, int x) {
        return y >= n || y < 0 || x >= n || x < 0;
    }
}
