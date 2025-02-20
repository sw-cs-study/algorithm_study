package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

// 스터디원과 visit 배열 크기에 대해 이야기 나누어보기
public class BOJ12851 {
    
    static int n, k;    // 수빈 위치, 동생 위치
    static int[] visit;
    static int min = Integer.MAX_VALUE;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());    
        k = Integer.parseInt(st.nextToken());

        visit = new int[150001];

        bfs(n);
        System.out.println(min);
        System.out.println(cnt);
    }

    private static void bfs(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        visit[start] = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int now = queue.poll();

                if (now == k) {
                    min = visit[now];
                    cnt++;
                }

                // queue 이니까 k에 도달한 이후 시간은 볼 필요가 없음
                if (visit[now] > min) {
                    return;
                }

                int next;
                for (int i = 0; i < 3; i++) {
                    if (i == 0) {
                        next = now - 1;
                    } else if (i == 1) {
                        next = now + 1;
                    } else {
                        next = now * 2;
                    }

                    if (next < 0 || next > 150000) {
                        continue;
                    }

                    if (visit[next] == 0 || visit[next] == visit[now] + 1) {
                        queue.add(next);
                        visit[next] = visit[now] + 1;
                    }
                }
            }
        }
    }
}
