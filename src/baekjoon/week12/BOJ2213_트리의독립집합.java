package baekjoon.week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 트리 + dfs + dp
 *
 * 문제에서 말하는 독립집합을 구하기 위해서는 1-2-3과 같은 트리가 있다고 했을때,
 * 1번을 선택하면 2번을 선택안하는 식으로 하나씩 탐색하면서 구하면 된다.
 * 이때 각 노드별로 선택했을떄의 가중치, 선택하지 않았을떄의 가중치를 구해야 한다.
 *
 * 주의할점은, 이전에 탐색했던 노드를 탐색하지 않게, 방문배열처리를 하던가, prev 노드를 두는 것이다.
 *
 */
public class BOJ2213_트리의독립집합 {


	private static int N;//노드 수
	private static int[] weight; //가중치 정보 배열
	private static List<Integer>[] graph;//그래프
	private static List<Integer> path;//경로 출력을 위한 리스트
	private static int[][] dp;//방문 노드의 누적 가중치 저장을 위한 노드.

	//경로 찾기
	private static void trace(int currentNode, int prevNode, int select){

		//현재노드를 선택하지 않은 경우
		if (select == 0){
			for (int nextNode : graph[currentNode]){

				if (nextNode == prevNode) continue;

				//다음노드에 저장된 가중치를 확인해서 다음노드를 선택하는 경우와 선택하지 않는 경우 중 더 큰 쪽을 선택.
				if (dp[nextNode][0] > dp[nextNode][1]){
					trace(nextNode, currentNode, 0);
				}
				else {
					trace(nextNode, currentNode, 1);
				}
			}
		}
		//현재노드를 선택한 경우
		else {
			path.add(currentNode);
			for (int nextNode : graph[currentNode]){

				if (nextNode == prevNode) continue;

				//다음 노드는 선택 불가.
				trace(nextNode, currentNode, 0);
			}
		}
	}

	//select가 1이면 해당정점을 선택함. 0이면 선택하지 않음.
	private static int dfs(int currentNode, int prevNode, int select){

		//이미 탐색한 정점이면 그대로 반환
		if (dp[currentNode][select] != 0) return dp[currentNode][select];

		//현재 정점을 선택하지 않는 경우 - 다음 정점을 선택할수도 있고 선택하지 않을수도 있음.
		int weightValue = 0;
		if (select == 0){
			for (int nextNode : graph[currentNode]){
				if (nextNode == prevNode) continue;

				int selectValue = dfs(nextNode, currentNode, 1); //간선을 선택하는 경우,
				int nonSelectValue = dfs(nextNode, currentNode, 0); //간선을 선택하지 않는 경우.

				//현재 노드의 가중치에는 둘중 더 큰값으로 누적함.
				weightValue += Math.max(selectValue, nonSelectValue);
			}
		}
		//현재 정점을 선택하는 경우 - 다음 정점을 선택할 수 없음.
		else {
			weightValue = weight[currentNode];
			for (int nextNode : graph[currentNode]){
				if (nextNode == prevNode) continue;
				weightValue += dfs(nextNode, currentNode, 0);
			}
		}

		return dp[currentNode][select] = weightValue;
	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		weight = new int[N + 1];
		graph = new List[N + 1];
		path = new ArrayList<>();
		dp = new int[N + 1][2];

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			weight[i] = Integer.parseInt(st.nextToken());

			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			graph[a].add(b);
			graph[b].add(a);
		}

		int selectRoot = dfs(1, -1, 1); //루트를 선택한 경우,
		int nonSelectRoot = dfs(1, -1, 0); //루트를 선택하지 않은 경우.

		int totalValue = 0;
		//경로 구하기.
		if(selectRoot > nonSelectRoot){
			totalValue = selectRoot;
			trace(1, -1, 1);
		}
		else {
			totalValue = nonSelectRoot;
			trace(1, -1, 0);
		}

		Collections.sort(path);

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < path.size(); i++){
			result.append(path.get(i));

			if(i == path.size() - 1) continue;

			result.append(" ");
		}

		System.out.println(totalValue);
		System.out.println(result);
	}
}
