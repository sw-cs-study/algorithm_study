package baekjoon.week4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 다익스트라
 * 다익스트라인데, 최소가 되는 경로를 매번 업데이트 하면서 최종 경로를 저장해둬야 하는 문제
 *
 * MST와 헷갈릴수도 있는데 MST는 하나의 노드에서 특정 위치까지 최단거리가 구해지는 것이 아닌,
 * 모든 노드를 사이클 없이 연결하면서 간선 가중치의 최소합이 되게 하는 것이므로, 문제와는 다르다.
 */
public class BOJ2211_네트워크복구 {

	private final static int INF = 10001;

	//연결한 노드 추적을 위해 탐색할때마다 연결한 리스트를 매번 업데이트 한다.
	//특정 노드를 업데이트 할때는 리스트에 저장된 노드번호를 보면서, 해당 노드들이 업데이트 하려고 하는 노드에 연결되어있다면 전부 연결을 날려야 한다.
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
	private static int[] visited;//노드와 노드를 연결한 간선을 저장하는 배열.

	private static void dijkstra(){

		int[] distances = new int[N + 1];
		for(int i = 1; i <= N; i++){
			distances[i] = INF;
		}
		distances[1] = 0;

		PriorityQueue<Node> needVisited = new PriorityQueue<>((o1, o2) -> {

			if(o1.weight == o2.weight) return o1.node - o2.node;
			return o1.weight - o2.weight;
		});

		needVisited.add(new Node(1, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//새로 꺼낸 가중치가 기존에 저장된 값보다 크다면 볼 필요 없음.
			if(distances[currentNode.node] < currentNode.weight) continue;

			for(Node nextNode : graph[currentNode.node]){

				//해당 노드를 선택했을때, 다음 노드에 저장될 거리 구하기.
				int nextDistance = distances[currentNode.node] + nextNode.weight;

				//새로 구한 거리보다 기존에 저장된 거리가 더 작다면 패스.
				if(nextDistance > distances[nextNode.node]) continue;

				//다음 거리 업데이트
				distances[nextNode.node] = nextDistance;

				//다음탐색을 위한 큐에 넣기
				needVisited.add(nextNode);

				//간선 출력을 위해 간선 미리 저장.
				visited[nextNode.node] = currentNode.node;

			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		visited = new int[N + 1];

		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			graph[a].add(new Node(b, c));
			graph[b].add(new Node(a, c));
		}

		dijkstra();

		int resultCount = 0;
		StringBuilder result = new StringBuilder();
		for(int i = 2; i <= N; i++) {

			if(visited[i] == 0) continue;
			resultCount++;
			result.append(visited[i]).append(" ").append(i);

			if(i == N) continue;
			result.append("\n");
		}

		result.insert(0, resultCount + "\n");
		System.out.println(result);

	}
}
