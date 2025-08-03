package baekjoon.week22;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 트리
 *
 * 1에서부터 리프 노드까지 내려가면서, 해당 서브트리의 간선중 최소 값을 찾아낸다.
 * 부모까지의 간선과, 리프노드 간선을 비교한다.
 */


public class BOJ12784_인하니카공화국 {

	//최소 가중치를 구하기 위한 초기 최대값
	private final static int INF = Integer.MAX_VALUE;

	private static class Node{
		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	//섬과 다리의 수
	private static int N;
	private static int M;

	//노드 정보
	private static List<Node>[] graph;

	//dfs탐색 - 부모 노드와 연결된 간선의 가중치와, 자식노드들 간 연결된 가중치의 합중 작은 값을 반환함.
	private static int dfs(int currentNode, int prevNode, int parentWeight){

		//현재 하위 노드의 가중치의 합을 구함- 하위노드 연결간선의 합과, 부모노드 중에 뭐가 더 작은지 구하기 위함.
		int totalChildWeight = 0;

		for(Node nextNode : graph[currentNode]){

			//부모 노드라면 패스.
			if (nextNode.node == prevNode) continue;

			totalChildWeight += dfs(nextNode.node, currentNode, nextNode.weight);

		}

		//간선은 최소 1이므로, totalChildWeight가 0이라면 리프노드라는 뜻으로, 부모와 연결된 간선을 반환하면 됨..
		if (totalChildWeight == 0) return parentWeight;

		//totalChildWeight가 0보다 크면,부모와 비교하여 더 작은 간선을 반환함.
		return Math.min(parentWeight, totalChildWeight);
	}

	public static void main(String[] main) throws Exception{


		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(st.nextToken());

		StringBuilder sb = new StringBuilder();

		for (int testCase = 1; testCase <= T; testCase++){
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			graph = new List[N + 1];

			for (int i = 1; i <= N; i++){
				graph[i] = new ArrayList<>();
			}

			for (int i = 0; i < M; i++){
				st = new StringTokenizer(br.readLine());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());

				graph[a].add(new Node(b, weight));
				graph[b].add(new Node(a, weight));
			}

			int result = 0;
			result += dfs(1, -1, INF);

			//반례 추가 -> 섬이 하나만 있는 경우.
			if (result == INF) result = 0;

			sb.append(result).append("\n");

		}

		System.out.println(sb.toString());

	}
}



