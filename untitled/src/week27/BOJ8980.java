package week27;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ8980 {

    static int n, c, m;
    static Box[] boxList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());   // 마을 수
        c = Integer.parseInt(st.nextToken());   // 트럭 용량
        m = Integer.parseInt(br.readLine());    // 박스 정보 수

        boxList = new Box[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int amount = Integer.parseInt(st.nextToken());

            boxList[i] = new Box(from, to, amount);
        }

        // 트럭 용량 한정적 -> 먼저 내릴 수 있는 짐을 실어야함.
        Arrays.sort(boxList, (b1, b2) -> {
            if (b1.to != b2.to) {
                return Integer.compare(b1.to, b2.to);
            }
            return Integer.compare(b1.from, b2.from);
        });

        int[] remain = new int[n + 1];
        Arrays.fill(remain, c);

        int deliver = 0;
        for (Box box : boxList) {
            // 구간에서 최대로 실을 수 있는 박스 양
            int min = Integer.MAX_VALUE;
            for (int i = box.from; i < box.to; i++) {
                min = Math.min(min, remain[i]);
            }

            // 트럭에 최대로 실을 수 있는 박스 양
            int load = Math.min(min, box.amount);
            if (load == 0) {
                continue;
            }

            for (int i = box.from; i < box.to; i++) {
                remain[i] -= load;
            }

            deliver += load;
        }

        System.out.println(deliver);
    }

    private static class Box {
        int from;
        int to;
        int amount;

        public Box(int from, int to, int amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }
    }
}