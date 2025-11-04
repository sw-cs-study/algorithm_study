package week27;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ2632 {

    static int pizzaSize;
    static int m, n;

    static int[] a, b;
    static List<Integer> aPrefixSum, bPrefixSum;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        pizzaSize = Integer.parseInt(bf.readLine());

        st = new StringTokenizer(bf.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        a = new int[m];
        b = new int[n];

        for (int i = 0; i < m; i++) {
            a[i] = Integer.parseInt(bf.readLine());
        }

        for (int i = 0; i < n; i++) {
            b[i] = Integer.parseInt(bf.readLine());
        }

        aPrefixSum = calcPrefixSum(a);
        bPrefixSum = calcPrefixSum(b);

        Collections.sort(aPrefixSum);
        Collections.sort(bPrefixSum);

        long answer = 0;
        for (Integer aVal : aPrefixSum) {
            int target = pizzaSize - aVal;
            int lower = lowerBound(target);
            int upper = upperBound(target);

            answer += (upper - lower);
        }

        System.out.println(answer);
    }

    private static int lowerBound(int target) {
        int left = -1;
        int right = bPrefixSum.size();

        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (!(bPrefixSum.get(mid) >= target)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return right;
    }

    private static int upperBound(int target) {
        int left = -1;
        int right = bPrefixSum.size();

        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (!(bPrefixSum.get(mid) > target)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return right;
    }

    private static List<Integer> calcPrefixSum(int[] a) {
        int length = a.length;
        int[] tmp = new int[2 * length];
        for (int i = 0; i < 2 * length; i++) {
            tmp[i] = a[i % length];
        }

        int[] prefixSum = new int[2 * length + 1];
        for (int i = 1; i < 2 * length + 1; i++) {
            prefixSum[i] = prefixSum[i - 1] + tmp[i - 1];
        }

        List<Integer> sum = new ArrayList<>();
        sum.add(0);

        for (int i = 1; i < length; i++) {
            for (int j = 0; j < length; j++) {
                sum.add(prefixSum[j + i] - prefixSum[j]);
            }
        }

        sum.add(prefixSum[length] - prefixSum[0]);
        return sum;
    }
}