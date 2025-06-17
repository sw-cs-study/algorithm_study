/**
 * 특정 높이까지 최대로 그림을 배치하는 dp 배열을 만들거임(냅색)
 * 냅색의 요소중에 (배낭의 용량)=(그림의 최대높이), (각 물건의 무게)=(판매가능 그림의 최소 세로 길이), (물건의 가치)=(그림의 가치)
 * 여기서 냅색의 기본적인 방법과 다른 점은 판매가능 그림의 최소 세로 길이가 정해져 있고, 현재 그림의 높이에서 최소 길이를 뺀 높이를, 이전까지 그림으로 최대한 많이 판 금액과, 
 * -> 이전까지 그림으로 현재 높이까지 최대한 많이 판 금액 을 비교해서 배열에 채움
 * 그리고 냅색의 기본은 현재 물건의 무게부터 배낭의 용량까지 모든 값을 체크하고 조건에 맞으면 갱신하는데, 
 * 근데 그림의 갯수가 300,000개 높이가 20,000,000개, 한 그림의 최대 가치는 1000임. 그래서 그냥 dp 배열을 만들면 6*10^12라서 1초라는 시간제한에 맞출 수 없음
 * 가치의 최대합은 int 형이라 괜춘
 * 줄이기
 * -> 300,000개는 줄일 수가 없음.
 * -> 냅색 알고리즘은 무게에 대해 모두다 체크하면서 갱신함 -> 여기를 줄여보자 -> (현재 그림 높이 - s)이하인 값들을 탐색해서 현재 위치의 값을 갱신해야함 -> 이렇게 탐색하는 과정을 줄여야함
 * ->-> 현재 값이 적힌 행에서 특정 값 이하중에 최대값을 뽑아내야함 -> 세그먼트 트리..? -> log로 해결됨
 * 
 * 세그먼트 트리
 * -> 구간 별로 특정 조건(여기선 최댓값)을 뽑아서 노드에 저장한 이진 트리임. (문제와 같게) 0번 노드부터 전체 구간, 2번은 왼쪽 반, 3번은 왼쪽 반, ... 식으로 감.
 * -> 노드 정보는 배열에 노드의 각 번호와 같은 인덱스에 저장함. 초기화는 구간, 노드 번호를 담은 재귀 형태로 리프 노드까지 타고 들어감.
 * -> 추출: 노드의 구간, 노드 번호를 가지고 해당 구간을 노드의 구간으로 나눠서 구현
 * -> 갱신: 노드의 구간, 노드 번호를 가지고 해당 리프 노드가 해당 되는 구간을 재귀 형태로 갱신함.
 */

package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2515 {

    private static class SegmentTree {
        private int leafNodes;
        private int[] tree;
        SegmentTree(int maxHeight) {
            int leafNodes = 1;
            while (leafNodes <= (maxHeight+1)){
                leafNodes <<= 1;
            }
            this.leafNodes = leafNodes;
            int treeSize = 2 * leafNodes - 1;
            this.tree = new int[treeSize];
        }
        

        public int update(int targetHeight, int valueCost) {
            int left = 0;
            int right = this.leafNodes-1;
            int node = 0;

            int leftNode = node * 2 + 1;
            int rightNode = node * 2 + 2;
            int mid = (left + right) / 2;
            int ret;
            int compare;
            if (targetHeight <= mid) {
                ret = update(targetHeight, valueCost, left, mid, leftNode);
                compare = this.tree[rightNode];
            } else {
                ret = update(targetHeight, valueCost, mid+1, right, rightNode);
                compare = this.tree[leftNode];
            }
            if (ret < compare) {
                ret = compare;
            } 
            this.tree[node] = ret;
            return ret;
        }

        public int update(int targetHeight, int valueCost, int left, int right, int node) {
            if (left == right) {
                if (this.tree[node] > valueCost) return this.tree[node];
                this.tree[node] = valueCost;
                return valueCost;
            }

            int leftNode = node * 2 + 1;
            int rightNode = node * 2 + 2;
            int mid = (left + right) / 2;
            int ret;
            int compare;
            if (targetHeight <= mid) {
                ret = update(targetHeight, valueCost, left, mid, leftNode);
                compare = this.tree[rightNode];
            } else {
                ret = update(targetHeight, valueCost, mid+1, right, rightNode);
                compare = this.tree[leftNode];
            }
            if (ret > compare) {
                this.tree[node] = ret;
            } else {
                this.tree[node] = compare;
            }
            return this.tree[node];
        }

        public int getCost(int targetHeight) {
            
            int left = 0;
            int right = this.leafNodes-1;
            int node = 0;

            int leftNode = node * 2 + 1;
            int rightNode = node * 2 + 2;
            int mid = (left + right) / 2;
            int ret = 0;
            if (targetHeight == mid) {
                return this.tree[leftNode];
            } else if (targetHeight < mid) {
                return getCost(targetHeight, left, mid, leftNode);
            } else {
                int compare = this.tree[leftNode];
                ret = getCost(targetHeight, mid+1, right, rightNode);
                if (ret < compare) {
                    return compare;
                }
                return ret;
            }
        }

        public int getCost(int targetHeight, int left, int right, int node) {
            // 0부터 right 까지라서 left와 타깃을 비교할 필요는 없음
            if (left == right) {
                return this.tree[node];
            }

            int leftNode = node * 2 + 1;
            int rightNode = node * 2 + 2;
            int mid = (left + right) / 2;
            int ret = 0;
            if (targetHeight == mid) {
                return this.tree[leftNode];
            } else if (targetHeight < mid) {
                return getCost(targetHeight, left, mid, leftNode);
            } else {
                int compare = this.tree[leftNode];
                ret = getCost(targetHeight, mid+1, right, rightNode);
                if (ret < compare) {
                    return compare;
                }
                return ret;
            }
        }

        int peek() {
            return this.tree[0];
        }
        
    }

    private static class Picture {
        int height;
        int cost;
        Picture(int h, int c) {
            this.height = h;
            this.cost = c;
        }
    }
    
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        Picture[] pictures = new Picture[n];
        int maxHeight = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pictures[i] = new Picture(h, c);
            if (h > maxHeight) {
                maxHeight = h;
            }
            
        }
        Arrays.sort(pictures, (Picture a, Picture b)->a.height - b.height);
        SegmentTree tree = new SegmentTree(maxHeight);
        // 연산 시작
        for (int i = 0; i < n; i++) {
            Picture pic = pictures[i];
            int maxCost = tree.getCost(pic.height-s);
            tree.update(pic.height, maxCost + pic.cost);
        }
        System.out.println(tree.peek());
    }

    

}
