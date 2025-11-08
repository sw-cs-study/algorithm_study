package baekjoon.week31;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 세그먼트 트리.
 */

public class BOJ11505_구간곱구하기 {

	private final static int MOD = 1_000_000_007;

	private static int N;
	private static int M;
	private static int K;
	private static long[] numArray;
	private static long[] segTree;

	//세그먼트 트리 초기 구성.
	private static long init(int start, int end, int node){

		//리프노드에 도달함.
		if(start == end) return segTree[node] = numArray[start];

		//왼쪽 오른쪽 자식노드를 채워나감.
		int mid = (start + end) / 2;

		//왼쪽 오른쪽 곱한 값을 해당 노드에 저장함.(MOD 이용)
		//모듈러 연산의 분배법칙 => (A * B) MOD N=> ((A MOD N) * (B MOD N)) % MOD N
		return segTree[node] = (init(start, mid, node * 2) * init(mid + 1, end, node * 2 + 1)) % MOD;
	}

	//수 업데이트
	private static long update(int start, int end, int node, int changeNode, int changeValue){

		//업데이트 하려는 노드가 범위 밖이면 자기 자신 반환해서, 상위 노드 계산시에 사용하도록 함.
		if(changeNode < start || changeNode > end) return segTree[node];

		//해당 위치에 도달하면, 업데이트 - 해당 위치가 아니면 위의 조건식에서 종료될 것 이므로 start==end일때까지 탐색하면 됨.
		if(start == end) return segTree[node] = changeValue;

		int mid = (start + end) / 2;

		return segTree[node] = (update(start, mid, node * 2, changeNode, changeValue) * update(mid + 1, end, node * 2 + 1, changeNode, changeValue)) % MOD;

	}

	//구하고자 하는 범위의 수 - start,end, node는 세그트리 탐색을 위해 필요.
	private static long getScopeMul(int start, int end, int node, int left, int right){

		//범위 밖이라면 곱했을때 영향이 없도록 하기 위해 1을 반환함.
		if(left > end || right < start) return 1;

		//범위 안이라면 해당 노드 값을 반환함.
		if(left <= start && right >= end) return segTree[node];

		//범위안에 완전히 속하지 않고, 일부만 속한다면 더 작은 범위로 쪼개야 함.
		int mid = (start + end) / 2;
		return (getScopeMul(start, mid, node * 2, left, right) * getScopeMul(mid + 1, end, node * 2 + 1, left, right)) % MOD;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		numArray = new long[N + 1];
		segTree = new long[4 * N];

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < N; i++){
			numArray[i + 1] = Integer.parseInt(br.readLine());
		}

		init(1, N, 1);// 세그먼트 트리 초기화.

		for(int i = 0; i < M + K; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			//a == 1이면 수 변경, a == 2이면 구간 곱 구하기.
			if(a == 1){
				numArray[b] = c; // 초기 트리 구성 이후에는 세그트리 노드에 값을 직접 저장하고 수정하기 때문에 따로 필요 없을듯.
				update(1, N, 1, b, c);
			}
			else if(a == 2){
				result.append(getScopeMul(1, N, 1, b, c)).append("\n");
			}
		}

		System.out.println(result);

	}
}
