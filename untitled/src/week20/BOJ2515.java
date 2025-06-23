package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2515 {

    static int n, s;
    static Paint[] paints;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());   // 그림 수
        s = Integer.parseInt(st.nextToken());   // 허용되는 그림 간 높이 차

        paints = new Paint[n];  // 그림들
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int height = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            paints[i] = new Paint(height, value);
        }

        Arrays.sort(paints, (p1, p2) -> Integer.compare(p1.height, p2.height));

        int[] dp = new int[n];
        int pointer = 0; 
        int bestPrev = 0;
        dp[0] = paints[0].value;

        for (int i = 1; i < n; i++) {
            while (pointer < i && paints[i].height - paints[pointer].height >= s) {
                bestPrev = Math.max(bestPrev, dp[pointer]);
                pointer++;
            }

            dp[i] = Math.max(dp[i - 1], bestPrev + paints[i].value);
        }

        System.out.println(dp[n - 1]);
    }

    private static class Paint {
        int height;
        int value;

        public Paint(int height, int value) {
            this.height = height;
            this.value = value;
        }
    }
}
