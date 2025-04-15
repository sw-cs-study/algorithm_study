package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// B[i]일 때 B[i]보다 작거나 같은 수의 개수 >= k
public class BOJ1300 {
    static int n;
    static long k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());
        k = Long.parseLong(bf.readLine());

        long left = 0;
        long right = (long) n * n; // 오버플로우 방지
        while (left + 1 < right) {
            long mid = (left + right) / 2;

            if (solve(mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        System.out.println(right);
    }

    // mid / i가 i번째 row에서 mid보다 작은 수의 개수가 되는 이유
    // i * j <= mid를 찾고있음
    // j는 구하고자 하는 개수임. 따라서 양변에 i로 나눔.
    // j <= mid / i
    private static boolean solve(long mid) {
        long cnt = 0;
        for (int i = 1; i <= n; i++) {  // 문제에서 주어진 조건에 따라 인덱스는 1부터 시작
            cnt += Math.min(mid / i, n);
        }

        return cnt >= k;
    }
}
