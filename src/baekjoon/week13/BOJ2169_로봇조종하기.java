package baekjoon.week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + dp
 *
 * dfs를 통해서 (1,1) 부터 (N,M) 까지 경로를 탐색하며 가중치를 누적한다.
 * 이때 매번 모든 경로를 탐색하려고 하면, dfs의 시간복잡도는 O(V + E)가 된다.
 * 격자형 그래프에서 V =>  N*M,
 * E => (가장자리 노드의 간선수 2개) + 가운데 노드 간선수 4개 => ((N + M) * 2 - 4) + ((N - 2) * (M - 2))]
 *
 * 따라서 모든 경우를 매번 탐색하면 시간초과가 나게 된다.
 * 한번 목적지까지 탐색을 하면 굳이 다시 탐색할 필요 없이, 값을 저장해뒀다가 반환하는 메모이제이션을 사용한다.
 *
 * (추가)
 * 해당 문제는 3차원으로 해결해야 한다.
 * dp를 이용할때 반드시 3차원으로 처리해야 하는게, 방문했던 노드를 다시 방문하는 것이 불가능하다.
 * 특정 노드로 이동시, 어떤방향에서 들어왔는지에 따라서 목적지까지 도착이 가능할수도, 아닐수도 있다.
 * 방향을 같이 저장하지 않게 되면, 한 방향으로 진입했을떄, 목적지 도달이 안되지만, 다른 방향으로 진입하면 목적지 도달이 가능한 경우에도,
 * 제대로 탐색을 하지 못하게 된다.
 *
 * (추가)
 * dfs로 탐색하려고 하면 N M 크기가 커서 안된다....
 * 바텀업으로 해결해야 한다(혹시 탑다운으로 시초 없이 푼사람 공유좀)
 *
 */
public class BOJ2169_로봇조종하기 {

	private final static int INF = -100_001;

	private static int N;//행(N);
	private static int M;//열(M);

	private static int[][] graph;//격자형 그래프.

	private static int[][][] dp;//가중치를 저장할 배열



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new int[N + 1][M + 1];
		dp = new int[N + 1][M + 1][3];

		//그래프 초기화.
		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= M; j++){
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//최소 값으로 초기화.
		for(int i = 0; i <= N; i++){
			for(int j = 0; j <= M; j++){
				for(int k = 0; k < 3; k++){
					dp[i][j][k] = INF;
				}
			}
		}

		//dp배열 시작점 초기화.
		dp[1][1][0] = dp[1][1][1] = dp[1][1][2] = graph[1][1];

		//0 : 왼쪽에서 오른쪽, 1: 위에서 아래, 2: 오른쪽에서 왼쪽.
		for (int i = 1; i <= N; i++){
			for (int j = 1; j <= M; j++){


				//왼쪽에서 오른쪽으로 오는 경우.(2번 경우는 존재 불가, 방문노드를 다시 방문할 수 없음.)
				if(j > 1){
					dp[i][j][0] = Math.max(dp[i][j - 1][0], dp[i][j - 1][1]) + graph[i][j];
				}

				//위에서 아래로 오는 경우(모든 경우가 다됨, 위로가는 경우는 없기 때문에)
				if(i > 1){
					dp[i][j][1] = Math.max(Math.max(dp[i - 1][j][0], dp[i - 1][j][1]), dp[i - 1][j][2]) + graph[i][j];
				}
			}

			//오른쪽에서 왼쪽으로 오는 경우는 오른쪽끝에서 부터 반복 돌아야 함.
			for (int j = M - 1; j >= 1; j--){

				//왼쪽에서 오른쪽으로 온 경우 제외 다 확인.
				dp[i][j][2] = Math.max(dp[i][j + 1][1], dp[i][j + 1][2]) + graph[i][j];
			}
		}


		//목적지는 오른쪽 하단 끝이므로, 오른쪽에서 왼쪽으로 오는(2) 경우는 없음.
		System.out.println(Math.max(dp[N][M][0],dp[N][M][1]));

	}

}
