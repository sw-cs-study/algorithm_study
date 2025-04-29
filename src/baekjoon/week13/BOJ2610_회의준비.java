package baekjoon.week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 플로이드 워셜 + bfs
 *
 * 문제에서 구해야 하는 것은 위원회 그룹 수와 각 위원회에서 특정 값을 루트로 하여 모든 노드까지 최단거리를 구했을때,
 * 해당 최단거리가 최소가 되는 루트를 구하는 것이다.
 * (사실 이 문제는 쓰레기다, M 값 범위를 안줬음 머저리 놈들)
 *
 * 우선 각 그룹수를 구하기 위해서 bfs를 돌면서 노드를 그룹핑 한다(배열에 표기 - N + M)
 * 다음으로 노드간 최단 거리를 구하기 위해서 플로이드 워셜을 이용해 배열을 구성한다(시간복잡도 N^3으로 10^6)
 * 플로이드 워셜로 구한 배열을 돌면서 그룹별 최소 값과 이에 해당하는 대표 노드를 저장한다(N^N으로 10^2 * 10^2 = 10^4)
 *
 * 최종 시간복잡도는 2N + N^3 + N^2 => N^3 => 10^6이다.
 *
 * (추가)
 * 플로이드 워셜만 구하면 연결정보를 알기 떄문에 굳이 bfs를 탐색할 필요가 없다.
 */

public class BOJ2610_회의준비 {

	private final static int INF = 1000;

	private static int N;//노드수
	private static int M;//간선수
	private static int[][] dp;//최단거리 저장할 배열(플로이드 워셜)
	private static int[] groupMapping;//노드별 그룹 정보를 가지고 있을 배열
	private static List<int[]> groupInfo;//그룹별 최단거리 및 대표 노드 저장 배열.


	//플로이드 워셜
	private static void floyd(){

		for (int k = 1; k <= N; k++){
			for (int i = 1; i <= N; i++){
				for (int j = 1; j <= N; j++){

					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		dp = new int[N + 1][N + 1];
		groupMapping = new int[N + 1];
		groupInfo = new ArrayList<>();
		groupInfo.add(new int[]{-1,-1});//인덱스를 1번부터 사용하기 위해.

		//플로이드 워셜 초기화
		for (int i = 1; i <= N; i++){
			for (int j = 1; j <= N; j++){

				if(i == j) continue;
				dp[i][j] = INF;
			}
		}

		for (int i = 0; i < M ; i++) {
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			dp[a][b] = 1;
			dp[b][a] = 1;
		}

		//플로이드 워셜 수행
		floyd();


		//반복문 돌면서 각 노드의 그룹과 최단거리 구하기
		int idx = 0;

		for(int i = 1; i <= N; i++){

			int maxPath = 0;

			if(groupMapping[i] == 0) {
				idx++;
				groupMapping[i] = idx;
			}

			for(int j = 1; j <= N; j++){

				if(dp[i][j] == INF || i == j) continue;
				//그룹정보 저장.
				if( groupMapping[j] == 0) groupMapping[j] = idx;

				maxPath = Math.max(maxPath, dp[i][j]);

			}

			//인덱스보다 groupinfo 크기가 더 작으면 새로 생성해야 함.
			if(idx > groupInfo.size() - 1){
				groupInfo.add(new int[]{-1,INF});
			}

			//0번에 대표노드, 1번에 최단 경로,
			int[] info = groupInfo.get(groupMapping[i]);

			if (info[1] > maxPath) {
				info[0] = i;
				info[1] = maxPath;
			}
		}

		groupInfo.sort((node1, node2) -> {
			return node1[0] - node2[0];
		});

		StringBuilder result = new StringBuilder();
		result.append(groupInfo.size() - 1).append("\n");
		for(int i = 1; i < groupInfo.size(); i++){
			result.append(groupInfo.get(i)[0]);

			if(i == groupInfo.size() - 1) continue;

			result.append("\n");
		}

		System.out.println(result);
	}
}
