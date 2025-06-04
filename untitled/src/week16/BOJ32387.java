package week16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ32387 {

    static int n, q;
    static int[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        tree = new int[4 * n];
        init(1, n, 1);

        int[] arr = new int[n + 1];
        int count = 0;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(bf.readLine());
            int type = Integer.parseInt(st.nextToken());
            int port = Integer.parseInt(st.nextToken());

            count++;

            if (type == 1) {
                int index = query(1, n, 1, port, n);
                if (index == Integer.MAX_VALUE) {
                    sb.append(-1).append('\n');
                } else {
                    update(1, n, 1, index, 1);
                    arr[index] = count;
                    sb.append(index).append('\n');
                }
            } else if (type == 2) {
                if (arr[port] != 0) {
                    sb.append(arr[port]).append('\n');
                    arr[port] = 0;
                    update(1, n, 1, port, 0);
                } else {
                    sb.append(-1).append('\n');
                }
            }
        }

        System.out.println(sb);
    }

    private static void update (int start, int end, int node, int index, int value) {
        if (index < start || end < index) {
            return;
        }


        if (start == end) {
            tree[node] = (value == 0) ? start : Integer.MAX_VALUE;
            return;
        }

        int mid = (start + end) / 2;
        update(start, mid, node * 2, index, value);
        update(mid + 1, end, node * 2 + 1, index, value);

        tree[node] = Math.min(tree[node * 2], tree[node * 2 + 1]);
    }

    private static int query (int start, int end, int node, int left, int right) {
        // 범위 밖
        if (right < start || end < left) {
            return Integer.MAX_VALUE;
        }

        // 범위 안
        if (left <= start && end <= right) {
            return tree[node];
        }

        // 부분 범위 안
        int mid = (start + end) / 2;
        return Math.min(
                query(start, mid, node * 2, left, right),
                query(mid + 1, end, node * 2 + 1, left, right)
        );
    }

    private static int init(int start, int end, int node) {
        if (start == end) {
            tree[node] = start;
            return tree[node];
        }

        int mid = (start + end) / 2;
        tree[node] = Math.min(
                init(start, mid, node * 2),
                init(mid + 1, end, node * 2 + 1)
        );

        return tree[node];
    }
}