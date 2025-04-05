package baekjoon.week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어.
 * 합리적인 이동경로란, S에서 E로 가는 거리를 dis(S,E)라고 했을때,
 * a에서 b로 이동한다고 했을때, dis(a, e) > dis(b, e) 인 경우에만 이동한다는 뜻.
 * 역으로 목적지에서 최단거리를 계산해두고, 재귀호출 하면서 경로를 세어나감.
 */

public class BOJ2176_합리적인이동경로 {

	private static final int INF = 1_000_000_001;

	private static class Node{
		int node, weight;
		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}


	private static int N; //정점 수
	private static int M; //간선 수
	private static int result; //경로 수
	private static List<Node>[] graph;//그래프
	private static int[] distances;//최단거리 배열.
	private static int[] dp;//dp - 이전에 탐색한 경로를 저장해서 탐색시간을 줄임

	//다익스트라 - 목적지 노드에서 각 노드로 가는 최단거리 구하기.
	private static void dijkstra(int startNode) {
		distances = new int[N + 1];
		Arrays.fill(distances, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>(
			(node1, node2) -> node1.weight - node2.weight
		);

		pq.add(new Node(startNode, 0));
		distances[startNode] = 0;

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > distances[currentNode.node]) continue;

			for(Node nextNode : graph[currentNode.node]) {

				int nextDistance = currentNode.weight + nextNode.weight;

				if (nextDistance > distances[nextNode.node])
					continue;

				distances[nextNode.node] = nextDistance;
				pq.add(new Node(nextNode.node, nextDistance));
			}
		}
	}

	//dfs로 탐색하면서 경로 구하기
	//distances[currentNode] > distances[nextNode] 인 경우가, 합리적인 경로이다(목적지까지의 거리가 줄어들기 때문)
	private static int dfs(int currentNode){

		//목적지 노드에 도달하면 1반환해서 개수세기
		if(currentNode == 2) return 1;

		//이전에 이미 탐색했던 노드이면 수를 반환
		if(dp[currentNode] != -1) return dp[currentNode];

		dp[currentNode] = 0;
		for(Node node : graph[currentNode]){

			int nextNode = node.node;

			//현재 거리 값보다, 다음 노드의 거리 값이 작아야 이동가능
			if(distances[currentNode] <= distances[nextNode]) continue;


			dp[currentNode] += dfs(nextNode);

		}

		return dp[currentNode];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		for(int i = 0; i <= N; i++){
			graph[i] = new ArrayList<>();
		}


		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			graph[a].add(new Node(b, weight));
			graph[b].add(new Node(a, weight));
		}

		//문제에서 나와있는 것 처럼 목적지는 2이므로,
		dijkstra(2);


		//-1이면 탐색 안함
		dp = new int[N + 1];
		Arrays.fill(dp, -1);

		//문제에서 출발지를 1로 줌.
		result = dfs(1);

		System.out.println(result);
	}
}
