package baekjoon.week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 *
 * 다익스트라 응용
 */
public class BOJ9370_미확인도착지 {

	private final static int INF = 2_000_001;

	//노드객체
	private static class Node{
		int node, weight, type;
		public Node(int node, int weight, int type){
			this.node = node;
			this.weight = weight;
			this.type = type;
		}
	}

	private static int n;//노드 수
	private static int m;// 간선수
	private static int t;//목적지 후보수

	private static int s;//출발지
	private static int g;//지나간 노드1
	private static int h;//지나간 노드2

	private static List<Node>[] graph;//그래프

	private static List<Integer> candidates;//목적지 후보 리스트

	//다익스트라
	private static int[][] dijkstra(){
		//0 : 필수간선을 안지남, 1: 필수간선을 지남.
		int[][] distance = new int[n + 1][2];
		for(int i = 1; i <=n; i++){
			distance[i][0] = INF;
			distance[i][1] = INF;
		}

		distance[s][0] = 0;

		PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) -> {
			return node1.weight - node2.weight;
		});

		pq.add(new Node(s,0,0));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > distance[currentNode.node][currentNode.type]) continue;



			for(Node nextNode : graph[currentNode.node]){
				int required = currentNode.type;
				int nextDistance = distance[currentNode.node][currentNode.type] + nextNode.weight;

				//다음 간선이 필수간선인경우,
				if((currentNode.node == g && nextNode.node == h) || (currentNode.node == h && nextNode.node == g)){
					required = 1;
				}

				if(nextDistance >= distance[nextNode.node][required]) continue;

				pq.add(new Node(nextNode.node, nextDistance, required));
				distance[nextNode.node][required] = nextDistance;

			}
		}

		return distance;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		StringBuilder result = new StringBuilder();

		for(int testCase = 1; testCase <= T; testCase++){

			st = new StringTokenizer(br.readLine());

			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());

			s = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());

			graph = new List[n + 1];
			for(int i = 1; i <= n; i++){
				graph[i] = new ArrayList<>();
			}

			for(int i = 0; i < m; i++){
				st = new StringTokenizer(br.readLine());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());

				graph[a].add(new Node(b,d, 0));
				graph[b].add(new Node(a,d,0));

			}

			candidates = new ArrayList<>();
			for(int i = 0; i < t; i++){
				candidates.add(Integer.parseInt(br.readLine()));
			}

			Collections.sort(candidates);

			int[][] dijkstraDistance = dijkstra();

			for(int candidate : candidates){

				if(dijkstraDistance[candidate][1] == INF || (dijkstraDistance[candidate][0] < dijkstraDistance[candidate][1])) continue;

				result.append(candidate).append(" ");
			}

			if(testCase == T) continue;

			result.append("\n");

		}

		System.out.println(result);
	}

}
