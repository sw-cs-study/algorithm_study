package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ5639_2 {
    static int[] arr = new int[10001];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        int index = 0;
        while (true) {
            String number = bf.readLine();
            if (number == null || number.equals("")) {
                break;
            }
            arr[index++] = Integer.parseInt(number);
        }

        postOrder(0, index - 1);
        System.out.println(sb);
    }

    private static void postOrder(int start, int end) {
        if (start > end) {
            return;
        }

        int mid = start + 1;
        while (mid <= end && arr[mid] < arr[start]) {
            mid++;
        }

        postOrder(start + 1, mid - 1);  // 왼쪽 서브 트리
        postOrder(mid, end);    // 오른쪽 서브 트리
        sb.append(arr[start]).append('\n'); // 루트
    }
}
