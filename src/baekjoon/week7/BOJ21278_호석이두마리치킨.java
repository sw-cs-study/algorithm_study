package baekjoon.week7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 플로이드 워셜
 * 어떤 곳에 치킨집을 설치 했을때, 최단거리가 나오는지는 모든 경우를 다 해보는 수 밖에 없다(그리디처럼 풀기 어려움.)
 * 그래프에서 특정 지점에서 다른 지점까지의 최단거리를 전부 구한 후에, 조합을 따져야 하기 때문에,
 * 플로이드 워셜을 이용해서 모든 경우의 수를 먼저 다 구해본다.
 *
 * 노드 수가 적었다면 재귀를 이용한 완탐도 가능하지만, 해당 문제는 노드가 100개라 플로이드 워셜이 적합할 듯.
 *
 * (주의)
 * 해당 문제에서 출력해야 하는 거리는 "왕복거리"임을 주의해야 한다.
 *
 * (수정)
 * - 97에서 틀림 => 총 거리(가중치의 합)의 초기 값을 1000으로 너무 작게 잡음,
 *                만약 편향된 트리이고 노드가 최대 개수인 100개라면, 각 노드가 특정 치킨집까지 가는 거리의 합이 충분히 1000을 넘는다.
 */

public class BOJ21278_호석이두마리치킨 {

	private final static int INF = 100000;

	private static int N;//건물 개수
	private static int M;//도로 개수
	private static int[][] floyd;//플로이드 워설로 탐색하면서 최단 경로를 저장할 배열.

	private static int firstChicken;
	private static int secondChicken;
	private static int minDistance;

	//두개의 치킨집 위치 조합을 받아서 확인 : 더 최소 값이 존재하면 업데이트.
	private static void distanceCheck(int firstLoc, int secondLoc){

		int totalDistance = 0;

		//각 노드 별로 두 치킨집중 좀 더 최단거리인 곳의 값으로 누적.
		for(int currentLoc = 1; currentLoc <= N; currentLoc++){
			totalDistance += Math.min(
				floyd[currentLoc][firstLoc],
				floyd[currentLoc][secondLoc]
			);
		}

		if(totalDistance >= minDistance) return;

		minDistance = totalDistance;
		firstChicken = firstLoc;
		secondChicken = secondLoc;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		floyd = new int[N + 1][N + 1];

		//플로이드 워셜 배열 초기화.
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if(i == j) continue;
				floyd[i][j] = INF;
			}
		}

		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			floyd[a][b] = 1;
			floyd[b][a] = 1;
		}

		for(int k = 1; k <= N; k++){
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {

					//거쳐가는 비용이 무한대이면 의미 없음.
					if(floyd[i][k] == INF || floyd[k][j] == INF) continue;

					floyd[i][j] = Math.min(floyd[i][k] + floyd[k][j], floyd[i][j]);
				}
			}
		}

		firstChicken = -1;
		secondChicken = -1;
		minDistance = INF;

		//치킨집 두개 선정.
		for (int first = 1; first <= N; first++) {
			for (int second = first + 1; second <= N; second++) {
				distanceCheck(first, second);
			}
		}


		System.out.println(firstChicken + " " + secondChicken + " " + (minDistance * 2));

	}
}
