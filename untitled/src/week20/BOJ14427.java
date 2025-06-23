package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ14427 {

    static int n;
    static int[] arr;
    static Node[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        arr = new int[n + 1];
        st = new StringTokenizer(bf.readLine());
        for (int i = 1; i < n + 1; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        tree = new Node[4 * n];
        init(1, n, 1);

        StringBuilder sb = new StringBuilder();
        int m = Integer.parseInt(bf.readLine());
        while (m-- > 0) {
            st = new StringTokenizer(bf.readLine());
            int command = Integer.parseInt(st.nextToken());
            if (command == 1) {
                int index = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                update(1, n, 1, index, value);
            } else if (command == 2) {
                sb.append(query(1, n, 1, 1, n).index).append('\n');
            }
        }

        System.out.println(sb);
    }

    private static Node query(int start, int end, int node, int left, int right) {
        if (right < start || end < left) {
            return new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        return combine(
                query(start, mid, node * 2, left, right),
                query(mid + 1, end, node * 2 + 1, left, right)
        );
    }

    private static Node update(int start, int end, int node, int index, int value) {
        if (index < start || index > end) {
            return tree[node];
        }

        if (start == end) {
            return tree[node] = new Node(value, index);
        }

        int mid = (start + end) / 2;
        Node left = update(start, mid, node * 2, index, value);
        Node right = update(mid + 1, end, node * 2 + 1, index, value);

        tree[node] = combine(left, right);

        return tree[node];
    }

    private static Node init(int start, int end, int node) {
        if (start == end) {
            return tree[node] = new Node(arr[start], start);
        }

        int mid = (start + end) / 2;
        Node left = init(start, mid, node * 2);
        Node right = init(mid + 1, end, node * 2 + 1);

        tree[node] = combine(left, right);

        return tree[node];
    }

    private static Node combine(Node left, Node right) {
        if (left.value < right.value) {
            return left;
        } else if (left.value > right.value) {
            return right;
        } else {
            return (left.index < right.index) ? left : right;
        }
    }

    private static class Node {
        int value;
        int index;

        public Node(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
}
