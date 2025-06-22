/**
 * 정렬된 배열, 인덱스 배열마다 객체로 data를 관리하려고 했는데 값 갱신할때, 재정렬이 필수적이라 아웃.
 * 특정 너비에서 최소값을 가지는 데이터의 인덱스, 같다면 인덱스가 작은 것의 인덱스를 출력함 => 세그먼트 트리
 * 
 */


package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ14427 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        IndexSegmentTree tree = new IndexSegmentTree(arr);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            if (op == 2) {
                answer.append((tree.tree[1]+1) + "\n");
            } else {
                // op == 1
                int index = Integer.parseInt(st.nextToken())-1;
                int value = Integer.parseInt(st.nextToken()); 
                tree.update(index, value);
            }
        }
        System.out.println(answer);
    }

    private static class IndexSegmentTree {
        private int[] tree; // 둘중에 작은값 + 같다면 인덱스가 더 적은 값의 index가 저장됨
        private int[] arr;
        private int size;

        IndexSegmentTree(int[] input) {
            arr = input;
            int n = arr.length;
            size = 1;
            while (size < n) size <<= 1;
            tree = new int[2*size];
            init();
        }

        void init() {
            // 리프노드
            for (int i = 0; i < arr.length; i++) {
                tree[i+size] = i;
            }

            // 나머지 리프노드는 -1로 처리해놓기
            for (int i = size + arr.length; i < size*2; i++) {
                tree[i] = -1;
            }

            // 리프 노드 상위 노드들을 조건에 맞게 업데이트
            for (int i = size - 1; i > 0; i--) {
                tree[i] = minIndex(tree[i*2], tree[i*2+1]);
            }
        }

        int minIndex(int a, int b) {
            if (a == -1) return b;
            if (b == -1) return a;
            if (arr[a] > arr[b]) return b;
            if (arr[a] < arr[b]) return a;
            return Math.min(a, b);
        }

        

        void update(int index, int value) {
            arr[index] = value;
            int treeIndex = index + size;
            tree[treeIndex] = index;
            
            while (treeIndex > 1) {
                treeIndex >>= 1;
                tree[treeIndex] = minIndex(tree[treeIndex*2], tree[treeIndex*2+1]);
            }
        }

    }


}
