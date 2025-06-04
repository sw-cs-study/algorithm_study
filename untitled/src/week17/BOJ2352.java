package week17;

import java.util.*;
import java.io.*;

class BOJ2352 {

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
        lis[0] = arr[0];
        int length = 1;

        for (int i = 1; i < n; i++) {
            if (lis[length - 1] < arr[i]) {
                lis[length++] = arr[i];
            } else {
                int left = -1;
                int right = length;
                while (left + 1 < right) {
                    int mid = (left + right) / 2;
                    if (lis[mid] >= arr[i]) {
                        right = mid;
                    } else {
                        left = mid;
                    }
                }

                lis[right] = arr[i];
            }
        }

        // System.out.println(Arrays.toString(lis));
        System.out.println(length);
    }
}