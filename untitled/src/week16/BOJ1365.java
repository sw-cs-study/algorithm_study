package week16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1365 {

    static int n;
    static int[] arr;
    static int[] lis;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());

        arr = new int[n];
        st = new StringTokenizer(bf.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        lis = new int[n];
        int size = 0;

        lis[size] = arr[0];
        size++;

        for (int i = 1; i < n; i++) {
            if (lis[size - 1] < arr[i]) {
                lis[size] = arr[i];
                size++;
            } else {
                int index = bs(-1, size - 1, arr[i]);
                lis[index] = arr[i];
            }
        }

        System.out.println(n - size);
    }

    private static int bs(int left, int right, int target) {
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (lis[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }
}
