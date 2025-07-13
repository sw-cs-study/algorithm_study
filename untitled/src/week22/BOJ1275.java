package week22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 점 갱신, 구간 쿼리 형태의 세그먼트 트리
public class BOJ1275 {

    static int n, q;
    static int[] arr;
    static long[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());   // 수의 개수
        q = Integer.parseInt(st.nextToken());   // 턴의 개수

        arr = new int[n + 1];
        st = new StringTokenizer(bf.readLine());
        for (int i = 1; i < n + 1; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        tree = new long[4 * n];
        init(1, n, 1);

        StringBuilder sb = new StringBuilder();
        while (q-- > 0) {
            st = new StringTokenizer(bf.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            sb.append(query(1, n, 1, Math.min(x, y), Math.max(x, y))).append('\n');

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            update(1, n, 1, a, b);
        }

        System.out.println(sb);
    }

    /**
     *
     * @param start
     * @param end
     * @param index
     * @param targetIndex 바꾸고자하는 위치(인덱스)
     * @param targetValue 바꾸고자하는 값
     */
    private static void update(int start, int end, int index, int targetIndex, int targetValue) {
        // 범위 밖
        if (targetIndex < start || targetIndex > end) {
            return;
        }

        // 범위 안

        if (start == end) {
            tree[index] = targetValue;
            return;
        }

        int mid = (start + end) / 2;
        update(start, mid, index * 2, targetIndex, targetValue);
        update(mid + 1, end, index * 2 + 1, targetIndex, targetValue);

        tree[index] = tree[index * 2] + tree[index * 2 + 1];
    }

    /**
     *
     * @param start tree[index]가 담당하는 구간의 시작 인덱스
     * @param end tree[index]가 담당하는 구간의 끝 인덱스
     * @param index 현재 노드의 인덱스
     * @param left 탐색 구간의 시작 인덱스
     * @param right 탐색 구간의 끝 인덱스
     * @return arr[left] ~ arr[right] 구간의 합
     */
    private static long query(int start, int end, int index, int left, int right) {
        // 범위 밖
        if (right < start || end < left) {
            return 0;
        }

        // 범위 안
        if (left <= start && end <= right) {
            return tree[index];
        }

        // 부분적으로 속한 경우
        int mid = (start + end) / 2;
        return query(start, mid, index * 2, left, right) + query(mid + 1, end, index * 2 + 1, left, right);
    }

    private static long init(int start, int end, int index) {
        if (start == end) {
            return tree[index] = arr[start];
        }

        int mid = (start + end) / 2;
        return tree[index] = init(start, mid, index * 2) + init(mid + 1, end, index * 2 + 1);
    }
}
