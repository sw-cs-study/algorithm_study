package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2878 {

    static int m, n;    // 사탕 개수, 친구 수
    static int[] want;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        want = new int[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            int w = Integer.parseInt(bf.readLine());
            want[i] = w;
            sum += w;
        }

        sum -= m;

        Arrays.sort(want);
        long answer = 0;
        for (int i = 0; i < n; i++) {
            long tmp = Math.min(want[i], (sum / (n - i)));
            answer += ((tmp * tmp) % (long) Math.pow(2, 64));
            sum -= tmp;
        }

        System.out.println(answer % (long) Math.pow(2, 64));
    }
}
