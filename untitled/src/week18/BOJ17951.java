package week18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ17951 {

    static int n, k;    // 시험지 개수, 그룹 수
    static int[] arr;   // 맞은 문제 수

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new int[n];
        st = new StringTokenizer(bf.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(bs());
    }

    private static int bs() {
        int left = 0;  // 맞은 문제 수가 0까지 가능
        int right = 1000000 * 20;   // n의 최대 범위 * 시험지 당 최대 20문제

        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(mid) < k) {
                right = mid;
            } else {
                left = mid;
            }
        }

        return left;
    }

    // 그룹의 수를 리턴한다.
    private static int check(int target) {
        int groupCount = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            if (sum >= target) {
                groupCount++;
                sum = 0;
            }
        }

//        // 마지막 그룹은 target보다 적을 수 있다.
//        // sum이 0이라면 마지막 그룹까지 target보다 큰 경우이므로 앞서 카운팅이 된다.
//        if (sum != 0) {
//            groupCount++;
//        }

        return groupCount;
    }
}
