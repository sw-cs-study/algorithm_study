package baekjoon.week37;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 다익스트라 응용
 * 가중치를 계산해서 구해야 하고, 다익스트라를 두번(집 -> 목적지, 목적지 -> 고려대) 돌려야 하는 문제로,
 * 다익스트라를 응용한 문제.
 * 가중치가 음수가 나올수 있어서, 기존 다익스트라 처럼 다음 노드 가중치가 더 작다고 해서 탐색을 안하면 안됨.
 * 조건을 보게 되면, 올라갈때는 기존 높이보다 높은것만, 내려갈때는 낮은것만 탐색하게 되어있기 때문에 루프를 계속 돌지는 않음.
 *
 * (추가)
 * 문제를 잘못이해함.
 * 노드를 하나 거칠때마다 가치를 구하고 누적하는게 아니라, 목적지에 도달하면, 도달할때까지 이동한 거리를 이용해서 구하는 것,
 * 즉, 각 노드에는 거리만 누적하면 됨.
 * 가치가 최대가 되기 위해서는 거리가 최소가 되면 됨.
 */
public class BOJ16681_등산 {

	private final static long INF = 100_000_000_000L;
	private final static long MINUS_INF = -10_000_000_000_000L;

	//노드
	private static class Node{
		int node;
		long weight;
		public Node(int node, long weight){
			this.node = node;
			this.weight = weight;
		}
	}


	private static int N;//노드수
	private static int M;//간선수
	private static int D;//거리 비례 체력 소모량
	private static int E;//높이 비례 성취 획득량

	private static long result;

	private static int[] heights;//각 지점의 높이 정보.

	private static List<Node>[] graph;//그래프

	//이동가능한지 체크
	private static boolean check(int current, int next){
		return next > current;
	}


	//다익스트라 메서드
	private static long[] dijkstra(int startNode){

		long[] distances = new long[N + 1];
		Arrays.fill(distances, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) -> {

			if(node1.weight == node2.weight) return node1.node - node2.node;

			return Long.compare(node1.weight, node2.weight);
		});

		distances[startNode] = 0;
		pq.offer(new Node(startNode, 0));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(distances[currentNode.node] < currentNode.weight) continue;

			for(Node nextNode : graph[currentNode.node]){

				//다음 노드 높이에 따라 이동할지 말지 선택.
				if(!check(heights[currentNode.node], heights[nextNode.node])) continue;

				long nextDistance = currentNode.weight + nextNode.weight;

				//다음 높이 후보가 이전에 저장된 것 보다 크면 패스.
				if(nextDistance >= distances[nextNode.node]) continue;

				distances[nextNode.node] = nextDistance;
				pq.offer(new Node(nextNode.node, nextDistance));
			}
		}

		return distances;
	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		heights = new int[N + 1];
		graph = new List[N + 1];

		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			heights[i] = Integer.parseInt(st.nextToken());
		}

		for(int i = 1; i <= M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int n = Integer.parseInt(st.nextToken());

			graph[a].add(new Node(b, n));
			graph[b].add(new Node(a, n));
		}

		result = MINUS_INF;
		//집 출발
		long[] homeStart = dijkstra(1);

		//고려대 출발
		long[] univStart = dijkstra(N);


		//두 정보를 가지고 판단 - 한쪽이라도 INF이면 패스.
		boolean flag = false;
		for(int i = 2; i < N; i++){

			if(homeStart[i] == INF || univStart[i] == INF) continue;

			long energy = homeStart[i] + univStart[i];

			result = Math.max(result, ((long) heights[i] * E) - (energy * D));
			flag = true;
		}

		if(!flag) System.out.println("Impossible");
		else System.out.println(result);
	}
}
