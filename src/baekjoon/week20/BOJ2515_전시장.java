package baekjoon.week20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 이분탐색 + dp
 * 우선 주어진 액자를 높이순대로 오름차순 정렬한다.
 * 정렬하는 이유는 최대로 고객들이 모든 작품을 볼 수 있는 후보군은 앞에 있을수록 작은 경우이다.
 * 정렬후에는 액자하나씩 확인하면서 해당 액자를 놓을수 있는지, 또 놓았을때 최대가 되는지 확인하면 된다
 * 최대의 액자를 구하기 위해서는 우선 놓으려고 한 위치 앞에 최대한 많은 액자가 있으면 된다.(물론 조건을 만족하는)
 * 따라서 특정 액자를 놓을수 있는 위치를 확인할때는 상한선 즉 upper bound로 구해야 한다.
 * 이분탐색을 이용하는 이유는 액자의 수가 30만이라서, 특정 액자를 골랐을때, 어느 위치에 와야 하는지 구하는 연산의 수를 줄이기 위해서이다.
 * 만약 upper bound로 구한 액자를 놓을 수 있는 인덱스 위치가 현재 위치라면 둘 수 없다는 뜻이므로 패스 하고 다음 액자를 보면 된다.
 */
public class BOJ2515_전시장 {

	private static class Node{
		int height, price;

		public Node(int height, int price){
			this.height = height;
			this.price = price;
		}
	}


	private static int N;
	private static int S;

	private static Node[] nodes;

	//이분탐색 메서드
	private static int upperBound(int start, int end, int target){

		while (start < end){
			int mid = (start + end) / 2;

			if(nodes[mid].height <= target) start = mid + 1;
			else end = mid;
		}

		return end;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		nodes = new Node[N];

		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			nodes[i] = new Node(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
		}

		Arrays.sort(nodes, (node1, node2) -> {
			return node1.height - node2.height;
		});

		// 0이면 선택x, 1이면 선택
		int[][] dp = new int[N][2];

		//초기값
		dp[0][1] = nodes[0].price;

		for(int i = 1; i < N; i++){

			int targetIdx = upperBound(0, i, nodes[i].height - S);

			//이전에 둔 것들과 비교해야 함.
			dp[i][1] = nodes[i].price + Math.max(

				targetIdx == 0 ? 0 : dp[targetIdx - 1][0],
				targetIdx == 0 ? 0 : dp[targetIdx - 1][1]
			);

			//그림을 놓지 않으면 이전 값 그대로
			dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);

		}

		System.out.println(Math.max(dp[N - 1][0], dp[N - 1][1]));

	}
}
