package week6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 그리디 + dfs
 *
 * 작은 값부터 연산을 한다.
 * 모든 노드중 목표값이 가장 작은 값이 2라면, 해당 노드에 연산을 2번하고, 연결된 노드를 모두 2로 만든다.
 * 다음으로 작은 값을 꺼내고, 해당 값에 연결된 노드중, 이미 목표 값을 달성하지 않은 노드들을 목표값까지 만드는 식을 반복한다.
 * 이때 작은 값은 pq를 사용한다.
 */
public class BOJ27653_최소트리분할 {

	//노드 - 가중치가 작은순으로 정렬하기 위함.
	private static class Node{
		int idx, weight;

		public Node(int idx, int weight){
			this.idx = idx;
			this.weight = weight;
		}
	}


	private static int N;//노드 수
	private static long result; // 총 연산수.
	private static List<Integer>[] graph;//그래프

	private static int[] targetWeight;//목표 가중치
	private static boolean[] visited; // 방문했던 노드는 다시 방문하지 못하도록 함.

	//dfs 탐색
	private static void dfs(int currentNode, int prevNode, int preWeight){

		//이전 상위노드에서 전달된 가중치 만큼 빼고, 그 나머지만큼 연산해야 함(상위노드가 5이고, 현재노드가 3이면,3은 연산할 필요가 없음.)
		if(preWeight < targetWeight[currentNode]){
			result += targetWeight[currentNode] - preWeight;
		}

		for(int nextNode : graph[currentNode]){

			if(nextNode == prevNode) continue;

			dfs(nextNode, currentNode, targetWeight[currentNode]);

		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());

		result = 0;
		graph = new List[N + 1];
		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		targetWeight = new int[N + 1];

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			int tempWeight = Integer.parseInt(st.nextToken());
			targetWeight[i] = tempWeight;
		}


		for(int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());

			graph[nodeA].add(nodeB);
			graph[nodeB].add(nodeA);
		}

		//시작점은 어떤걸로 해도 상관 없을 듯.
		dfs(1, -1, 0);

		System.out.println(result);

	}
}
