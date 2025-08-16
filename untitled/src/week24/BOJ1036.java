package week24;

import java.io.*;
import java.util.*;

/**
 * BOJ 1036 - 36진수 최대 합 (BigInteger 없이)
 * 아이디어: 각 자리(36^p)에서의 계수를 int 배열에 누적 후 한 번에 캐리 처리.
 * - total[p] : 원래 합의 p자리 누적
 * - gain[d][p] : 문자 d(0..35)를 Z(=35)로 바꿀 때 p자리 이득 누적
 * 정렬 전 gain은 캐리 정규화하고, 이득 큰 순서로 K개를 total에 더한 뒤 total을 정규화하여 출력.
 */
public class BOJ1036 {

    static final int RADIX = 36;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        String[] s = new String[n];
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            s[i] = br.readLine();
            maxLen = Math.max(maxLen, s[i].length());
        }

        int k = Integer.parseInt(br.readLine().trim());

        // 캐리 여유를 넉넉히 확보
        int LEN = maxLen + 60;

        int[] total = new int[LEN];
        int[][] gain = new int[36][LEN];

        // 1) 자리 누적: total, gain
        for (String str : s) {
            int L = str.length();
            for (int p = 0; p < L; p++) {
                char ch = str.charAt(L - 1 - p);
                int v = val(ch);
                total[p] += v;               // 원래 값
                gain[v][p] += (35 - v);      // v->Z 교체 이득
            }
        }

        // 2) gain들을 정규화 (캐리 처리) 후, 내림차순 정렬
        for (int d = 0; d < 36; d++) normalize(gain[d]);
        // 이득 배열들을 리스트에 담아 정렬 (내림차순)
        List<int[]> gains = new ArrayList<>(36);
        for (int d = 0; d < 36; d++) gains.add(gain[d]);
        gains.sort((a, b) -> -compareDesc(a, b)); // 큰 이득 우선

        // 3) 상위 K개의 양(+)의 이득만 total에 더함
        for (int i = 0; i < k; i++) {
            if (isZero(gains.get(i))) break; // 0 이득부터는 의미 없음
            addInPlace(total, gains.get(i));
        }

        // 4) total 정규화 후 36진수 대문자로 출력
        normalize(total);
        System.out.println(toString36(total));
    }

    /* ====== 36진 유틸 ====== */

    static int val(char c) {
        if ('0' <= c && c <= '9') return c - '0';
        return 10 + (c - 'A');
    }

    static char digit(int v) {
        if (v < 10) return (char) ('0' + v);
        return (char) ('A' + (v - 10));
    }

    // a += b (동일 길이 가정, b 길이만큼 더함)
    static void addInPlace(int[] a, int[] b) {
        int m = Math.min(a.length, b.length);
        for (int i = 0; i < m; i++) a[i] += b[i];
    }

    // 캐리 정규화
    static void normalize(int[] a) {
        int carry = 0;
        for (int i = 0; i < a.length; i++) {
            int cur = a[i] + carry;
            a[i] = cur % RADIX;
            carry = cur / RADIX;
        }
    }

    // 정규화된 a가 0인지
    static boolean isZero(int[] a) {
        for (int v : a) if (v != 0) return false;
        return true;
    }

    // 내림차순 비교용(정규화 가정): a > b 이면 양수
    static int compareDesc(int[] a, int[] b) {
        int i = a.length - 1, j = b.length - 1;
        while (i >= 0 && a[i] == 0) i--;
        while (j >= 0 && b[j] == 0) j--;
        if (i != j) return Integer.compare(i, j); // 더 높은 자리 가진 쪽이 큼
        while (i >= 0) {
            int av = a[i];
            int bv = b[i];
            if (av != bv) return Integer.compare(av, bv);
            i--;
        }
        return 0;
    }

    // 정규화된 배열을 36진 문자열로
    static String toString36(int[] a) {
        int i = a.length - 1;
        while (i > 0 && a[i] == 0) i--;
        StringBuilder sb = new StringBuilder(i + 1);
        for (; i >= 0; i--) {
            sb.append(digit(a[i]));
        }
        return sb.toString();
    }
}

