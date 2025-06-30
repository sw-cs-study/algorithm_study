package baekjoon.week20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 매번 값들을 특정 수로 바꾸게 되면, 다시 정렬해야지만 최소값을 알 수 있게된다.
 * 따라서 세그먼트 트리를 이용해서 연산수를 NlogN으로 줄여야 한다.
 * 리프노트를 제외한 나머지 노드에는, 자식노드 중 최소값의 인덱스를 저장하는 식으로 트리를 구성한다.
 *
 *
 * (참고)
 * 풀이 : https://loosie.tistory.com/673
 * 트리의 노드가 4N인 이유 : https://www.acmicpc.net/board/view/79441
 *
 */
public class BOJ14427_수열과쿼리15 {

	private static int N;
	private static int M;
	private static int[] numArray;
	private static int[] segTree;

	//세그트리.
	private static int init(int start, int end, int node){

		if (start == end) return segTree[node] = end;

		int mid = (start + end) / 2;
		int left = init(start, mid, node * 2);
		int right = init(mid + 1, end, node * 2 + 1);

		return segTree[node] = getIndex(left, right);

	}

	//업데이트 연산 - 수를 업데이트 하면 처음부터 전부 업데이트.
	private static int update(int start, int end, int node, int targetIdx){
		if (start > targetIdx || end < targetIdx) return segTree[node];
		if (start == end) return segTree[node] = start;


		int mid = (start + end) / 2;
		int left = update(start, mid, node * 2,targetIdx);
		int right = update(mid + 1, end, node * 2 + 1, targetIdx);

		return segTree[node] = getIndex(left, right);
	}

	//두 노드에 들어갈 인덱스중에 최소값을 구하는 메서드
	private static int getIndex(int left, int right){

		if(numArray[left] == numArray[right]) return Math.min(left, right);
		else if(numArray[left] < numArray[right]) return left;
		else return right;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		numArray = new int[N + 1];
		segTree = new int[4 * N];

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		StringBuilder result = new StringBuilder();

		init(1, N, 1);
		M = Integer.parseInt(br.readLine());
		//쿼리
		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());
			int operation = Integer.parseInt(st.nextToken());

			//바꾸는 연산
			if (operation == 1){
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				numArray[a] = b;
				update(1, N, 1, a);
			}
			else{
				result.append(segTree[1]).append("\n");
			}
		}

		System.out.println(result);
	}
}
