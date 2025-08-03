package baekjoon.week22;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 세그먼트 트리.
 *
 * 구간의 합을 계속해서 구하는 문제로, 세그먼트 트리로 해결가능한 문제.
 * 레드블랙트리를 사용하는 treemap을 사용해도 해결은 가능할 듯?
 * 세그먼트 트리를 저장할 배열의 크기는 2^(h + 1)이면 충분함.
 * h의 경우에는 Logn -> 완전이진트리이므로
 */

public class BOJ1275_커피숍2 {

	private static int N;
	private static int Q;


	private static long[] numArray;
	private static long[] segTree;

	//세그먼트 트리 초기화
	private static long initSegTree(int start, int end, int node){


		if (start == end) return segTree[node] = numArray[start];

		int mid = (start + end) / 2;

		return segTree[node] = initSegTree(start, mid, node * 2) + initSegTree(mid + 1, end, node * 2 + 1);

	}

	//세그먼트 트리 업데이트
	private static void updateSegTree(int start, int end, int node, int targetIndex, long diffValue){

		//목표 인덱스가 start,end 범위 밖이라면 패스.
		if (start > targetIndex || end < targetIndex) return;

		//목표 인덱스가 범위 안이면 업데이트.
		segTree[node] += diffValue;

		//start end가 같아지면 더 탐색할 필요 없음.
		if (start == end) return;

		int mid = (start + end) / 2;

		//왼쪽 오른쪽 서브트리를 나눠서 처리
		updateSegTree(start, mid, node * 2, targetIndex, diffValue);
		updateSegTree(mid + 1, end, node * 2 + 1, targetIndex, diffValue);
	}


	//구간 합 구하기.
	private static long rangeSum(int start, int end, int node, int leftPart, int rightPart){

		// 범위를 벗어나면 패스.
		if (start > rightPart || leftPart > end) return 0;
		//구간안이라면 반환.
		if(leftPart <= start && end <= rightPart) return segTree[node];

		int mid = (start + end) / 2;

		return rangeSum(start, mid, node * 2, leftPart,rightPart) + rangeSum(mid + 1, end, node * 2 + 1, leftPart,rightPart);

	}


	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		int h = (int) Math.ceil(Math.log(N)/Math.log(2));

		numArray = new long[N + 1];
		// segTree = new long[(int) Math.pow(2, h + 1) + 1];
		segTree = new long[4 * N];


		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++){

			numArray[i + 1] = Integer.parseInt(st.nextToken());
		}

		initSegTree(1, N, 1);

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < Q; i++){
			st = new StringTokenizer(br.readLine());

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());


			//구간합.
			result.append(rangeSum(1, N, 1,Math.min(x,y), Math.max(x,y))).append("\n");

			//업데이트
			long diff = b - numArray[a];
			numArray[a] = b;

			updateSegTree(1, N, 1, a,  diff);

		}

		System.out.println(result);

	}
}
