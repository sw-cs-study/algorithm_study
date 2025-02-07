package baekjoon.week3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ11049_행렬곱셈순서 {

	private final static int INF = Integer.MAX_VALUE;

	private static class Node{
		int row,col;

		public Node(int row, int col){
			this.row = row;
			this.col = col;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int N = Integer.parseInt(br.readLine());

		Node[] infoArray = new Node[N + 1];
		int[][] dp = new int[N + 1][N + 1];

		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			infoArray[i] = new Node(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
		}

		//dp 초기화 - 인접한 행렬과 연산했을때의 수는 미리 구해둠. (2*3 , 3*2 => 2*3*2) - 행렬두개로는 계산순서가 있을 수 없음.
		for(int i = 1; i < N; i++){
			dp[i][i + 1] = infoArray[i].row * infoArray[i].col * infoArray[i + 1].col;
		}

		//길이는 특정 행렬을 몇개 거쳐갔는지를 나타냄, 즉 길이가 2면 1번 행렬에서 3번행렬까지 계산한 수를 나타내고, 3이라면 1번~4번까지의 계산한 수를 나타냄
		//따라서 길이가 2인것, 3인것 순서대로 구해가야 함.(길이가 1인 경우, 즉 인접한 것과의 계산은 초기 연산시에 미리 계산을 해둠.)
		for(int len = 2; len < N; len++){
			//시작위치는 전체 행렬 갯수 - 몇개를 거쳐갈지를 나타내는 거리가 된다.
			for(int start = 1; start <= N - len; start++){

				//목적지
				int end = start + len;

				//최소값을 구하기 위해, start에서 end까지 연산했을때의 최소값 저장 배열에 최대값으로 초기화를 함.
				dp[start][end] = INF;

				//반복문을 돌면서 start에서 end까지 연산할떄, 중간중간에 연산하는 모든 행렬을 다 봐야 함.
				//즉 1~5라면, 1 2 (345) 이런 중간중간 값을 다 따져봐야 함.
				for(int mid = start; mid < end; mid++){

					//dp[start][end]에 최종적으로 가장 작은 값을 저장한다.
					dp[start][end] = Math.min(
						dp[start][end],
						dp[start][mid] + dp[mid + 1][end] + (infoArray[start].row * infoArray[mid].col * infoArray[end].col)
					);
				}

			}
		}

		System.out.println(dp[1][N]);
	}
}
