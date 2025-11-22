package baekjoon.week33;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 다익스트라 + 이분탐색
 * 다익스트라로 탐색을 하는데, 이동 가능한 간선 가중치의 상한을 이분탐색으로 구해서 처리한다.
 */
public class BOJ20183_골목대장효석_효율성2 {

	//간선정보
	private static class Edge{
		int node;
		long weight;
		public Edge(int node, long weight){
			this.node = node;
			this.weight = weight;
		}
	}

	private static int N;//노드 수
	private static int M;//간선수
	private static int startNode;//시작 노드
	private static int endNode;//도착 노드
	private static long money;//가진 돈.
	private static int result;
	private static List<List<Edge>> graph;//그래프.


	//이분탐색
	private static void binarySearch(int start, int end){

		while(start <= end){



			int mid = (start + end) / 2;
			// System.out.println(mid);

			long totalMoney = dijkstra(mid);

			//간선 상한을 줄이게 되면, 비싼간선을 사용하지 못해, 우회해야 해서, 더 많은 간선을 거쳐가야 할 수 있음.
			//예를 들면 상한이 10이면 5 -> 10 으로 한번에 15의 비용으로 갈수 있지만, 만약 이 수치를 줄이게 되면, 5 -> 3 -> 4 -> 3 이런식으로 우회해서 전체 코스트가 증가할 수 있음.
			if(totalMoney > money){
				start = mid + 1;
			}
			else {
				result = mid;
				end = mid -1;
			}
		}
	}

	//다익스트라
	private static long dijkstra(int target){

		PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingLong(edge -> edge.weight));
		long[] distances = new long[N];
		Arrays.fill(distances, 500_000_000_000_001L);

		pq.add(new Edge(startNode, 0));
		distances[startNode] = 0;

		while(!pq.isEmpty()){

			Edge currentEdge = pq.poll();

			if(distances[currentEdge.node] < currentEdge.weight) continue;

			for(Edge nextEdge : graph.get(currentEdge.node)){

				//정해진 간선 상한선 보다 크면 패스.
				if(nextEdge.weight > target) continue;

				long nextDistance = distances[currentEdge.node] + nextEdge.weight;

				if(distances[nextEdge.node] <= nextDistance) continue;

				distances[nextEdge.node] = nextDistance;
				pq.add(new Edge(nextEdge.node, nextDistance));
			}
		}

		// System.out.println(Arrays.toString(distances));
		return distances[endNode];
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		startNode = Integer.parseInt(st.nextToken()) - 1;
		endNode = Integer.parseInt(st.nextToken()) - 1;
		money = Long.parseLong(st.nextToken());

		graph = new ArrayList<List<Edge>>();
		for(int i = 0; i < N; i++){
			graph.add(new ArrayList<>());
		}

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			long c = Long.parseLong(st.nextToken());

			graph.get(a).add(new Edge(b, c));
			graph.get(b).add(new Edge(a, c));
		}


		result = Integer.MAX_VALUE;
		int start = 1;//골목별 최소 수금액.
		int end = 1_000_000_000; // 골목별 최대 수금액.
		binarySearch(start, end);

		//MAX_VALUE면 이동 불가능.
		System.out.println(result == Integer.MAX_VALUE ? -1 : result);


	}
}
