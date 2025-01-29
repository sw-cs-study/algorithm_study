package baekjoon.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 벨만포드
 */

public class BOJ1865_웜홀 {

	private final static int INF = Integer.MAX_VALUE;

	private static class Node{
		int node, edge;

		public Node(int node, int edge){
			this.node = node;
			this.edge = edge;
		}
	}

	private static int N;
	private static int M;
	private static int W;

	private static int[] distance;
	private static List<List<Node>> graph;

	//벨만 포드 알고리즘
	private static boolean bellmanFord(int startNode) {

		boolean isChange = false;
		distance[startNode] = 0;

		//노드  - 1번만큼 반복.
		for(int i = 1; i < N ; i++) {
			isChange = false;
			for (int j = 1; j <= N; j++) {
				for (Node nextNode : graph.get(j)) {

					//해당 위치의 가중치가 무한대이거나, 기존 노드에 저장된 가중치 값이 신규 계산값보다 더 작거나 같다면 패스.
					if (distance[j] == INF || distance[nextNode.node] <= distance[j] + nextNode.edge)
						continue;

					distance[nextNode.node] = distance[j] + nextNode.edge;
					isChange = true;
				}
			}

			//중간에 가중치에 변화가 없다면 음수 사이클이 없는 것이므로 더 볼 것 없이 종료.
			if(!isChange) return isChange;
		}

		//노드 수 - 1 번 만큼 반복했으면, 1번더 반복해서 음수 사이클이 발생하는지 확인.
		for(int i = 1; i <= N ; i++){
			for(Node nextNode : graph.get(i)){
				if (distance[i] == INF || distance[nextNode.node] <= distance[i] + nextNode.edge)
					continue;

				return true; //가중치 변동이 생기면 음수 사이클이 발생한 것으로 간주.
			}
		}

		return false;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		StringBuilder result = new StringBuilder();

		int tc = Integer.parseInt(br.readLine());

		for(int testCase = 0; testCase < tc; testCase++){

			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			graph = new ArrayList<>();
			for(int i = 0; i <= N; i++){
				graph.add(new ArrayList<>());
			}

			//도로
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int S = Integer.parseInt(st.nextToken());
				int E = Integer.parseInt(st.nextToken());
				int T = Integer.parseInt(st.nextToken());

				graph.get(S).add(new Node(E,T));
				graph.get(E).add(new Node(S,T));
			}

			//웜홀
			for (int i = 0; i < W; i++) {
				st = new StringTokenizer(br.readLine());
				int S = Integer.parseInt(st.nextToken());
				int E = Integer.parseInt(st.nextToken());
				int T = Integer.parseInt(st.nextToken());

				graph.get(S).add(new Node(E, T * -1));
			}


			//최단거리 배열 구성 - 노드 - 1개만큼 구성.
			//노드수 - 1번까지는 최단거리를 구하면서 변할 수 있음(최단거리는 모드)
			//각 노드를 시작점으로 해서 벨만포드 수행.

			boolean check = false;
			distance = new int[N + 1];
			for(int i = 1; i <= N ; i++){

				Arrays.fill(distance, INF);

				//수행결과 음수 가중치가 있다면 YES
				if(bellmanFord(i)){
					result.append("YES").append("\n");
					check = true;
					break;
				}
			}

			if(!check){
				result.append("NO").append("\n");
			}
		}

		System.out.println(result);

	}
}
