package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2437 {
    static int n;   // 저울추의 개수
    static int[] arr;   // 저울추 무게

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        arr = new int[n + 1];
        st = new StringTokenizer(bf.readLine());
        for (int i = 1; i < n + 1; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 연속구간이 아닌 곳 찾기
        // 현재 [1 ~ 3] 측정 가능, 새로운 추(2) 추가 후 측정할 수 있는 무게 => [1 ~ 3] + [3(1 + 2) ~ 5(3 + 2)] = [1 ~ 5]
        // 이 때 7이 들어오면 [1 ~ 5] + [8(1 + 7) ~ 12(5 + 7)] => 6, 7 구간이 끊겼고 이는 측정할 수 없음
        Arrays.sort(arr);
        int answer = 1;
        for (int weight : arr) {
            if (answer < weight) {
                break;
            }
            answer += weight;
        }

        System.out.println(answer);
    }
}
