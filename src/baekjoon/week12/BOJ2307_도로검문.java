package baekjoon.week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 다익스트라.
 *
 * 지연시간을 계산하기 위해서 초기 정상적인 상태에서의 최단거리를 구한다.
 * 도로 차단후 지연시간을 위해서, 간선을 하나씩 끊어가며 확인한다.
 * (V+E)logV * E = 60000 * 5*10^3 => 3 * 10 * 10^7
 *
 * 모든 간선이 최단거리를 구하는데 영향을 주는 것은 아니기 때문에,
 * 최단거리를 구하면서 해당 경로를 저장한다.
 * 저장된 경로들만 제거하면서 다익스트라로 탐색하게 되면 훨씬 효율적이다.
 *
 * 대략 시간 복잡도를 계산했을때,
 * 우선 노드(V) : 1000, 간선(E) : 5000
 * 다익스트라의 시간복잡도는 (V+E)logV이다.
 * 여기에 간선을 하나씩 끊어봐야 하는데, 모든 간선을 다 끊을 필요는 없고,
 * 최단경로에 사용된 간선만 끊으면 된다.
 * 간선은 5000개라고 하지만, 모든 노드를 다 연결하는데에는 , 음수 가중치가 없기 때문에 V-1개를 거치는 경우가 최대이다.
 * 즉 (V-1) * ((V+E)logV)의 시간 복잡도를 가지게 되고,
 * 10^3 * (7*10^3)*log10^3 => 약 10^7 * 7의 시간복잡도로 1억이 좀 안되는 수치이다.
 *
 */
public class BOJ2307_도로검문 {

	private final static int INF = 100_000_001;

	//노드객체
	private static class Node{
		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	private static int N;//노드수
	private static int M;//간선수

	private static List<Node>[] graph;//그래프
	private static int[] visited;//간선체크 배열

	//다익스트라
	private static int dijkstra(){
		//시작노드는 1

		int[] distances = new int[N + 1];
		Arrays.fill(distances, INF);

		distances[1] = 0;

		PriorityQueue<Node> pq = new PriorityQueue<>(
			(node1, node2) -> {
				return node1.weight - node2.weight;
		});

		pq.add(new Node(1, 0));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > distances[currentNode.node]) continue;

			for(Node nextNode : graph[currentNode.node]){

				int nextDistance = distances[currentNode.node] + nextNode.weight;

				if(nextDistance >= distances[nextNode.node]) continue;

				pq.add(new Node(nextNode.node, nextDistance));
				distances[nextNode.node] = nextDistance;
				visited[nextNode.node] = currentNode.node;
			}
		}


		return distances[N];
	}

	//다익스트라 - 도로차단했을떄,
	private static int policeDijkstra(int nodeA, int nodeB){
		//시작노드는 1

		int[] distances = new int[N + 1];
		Arrays.fill(distances, INF);

		distances[1] = 0;

		PriorityQueue<Node> pq = new PriorityQueue<>(
			(node1, node2) -> {
				return node1.weight - node2.weight;
			});

		pq.add(new Node(1, 0));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > distances[currentNode.node]) continue;

			for(Node nextNode : graph[currentNode.node]){

				//차단된 도로면 패스
				if(nodeB == currentNode.node && nodeA == nextNode.node) continue;

				int nextDistance = distances[currentNode.node] + nextNode.weight;

				if(nextDistance >= distances[nextNode.node]) continue;

				pq.add(new Node(nextNode.node, nextDistance));
				distances[nextNode.node] = nextDistance;
			}
		}


		return distances[N];
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		for(int i = 1; i <= N; i++){
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

		visited = new int[N + 1];

		int minDistance = dijkstra(); //도둑이 정상적인 도로상황에서 탈출 할 수 있는 최단거리.
		int maxDistance = -1; //도로를 차단했을때 탈출가능한 최대시간.

		int index = N;
		while(index > 0){

			maxDistance = Math.max(maxDistance, policeDijkstra(index, visited[index]));

			index = visited[index];
		}


		if(maxDistance == INF)
			System.out.println(-1);
		else
			System.out.println(maxDistance - minDistance);


	}

}
