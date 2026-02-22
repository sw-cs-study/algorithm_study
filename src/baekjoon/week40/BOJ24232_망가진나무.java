package baekjoon.week40;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 트리 + dp
 *
 * 참고함
 * https://www.acmicpc.net/problem/24232
 */
public class BOJ24232_망가진나무 {

	private static class Edge{
		int node, idx;
		boolean convert; //true 면 전환, false면 전환안함.

		public Edge(int node, int idx, boolean convert){
			this.node = node;
			this.idx = idx;
			this.convert = convert;
		}

	}

	private static int N;//정점 수.

	private static List<Edge>[] graph;//그래프구성

	private static int resultConvert;//최소 간선 전환 수,
	private static boolean[] convertStatus;//노드 1을 기준으로 모든 노드가 연결되게 하는 간선 변환 상태.
	private static boolean[] result;//최종 결과

	//초기 노드 1을 기준으로 모든 노드에 도달하기 위한 변환횟수와 변환상태 파악.
	private static void init(int currentNode, int prevNode, int nodeCount){

		if(nodeCount == N) return;

		for(Edge nextEdge : graph[currentNode]){

			if(prevNode == nextEdge.node) continue;

			convertStatus[nextEdge.idx] = nextEdge.convert;
			resultConvert += nextEdge.convert ? 1 : 0;
			init(nextEdge.node, currentNode, nodeCount + 1);
		}

	}

	//dfs 완탐.
	private static void dfs(int currentNode, int prevNode, int convertCount){

		if(resultConvert > convertCount){
			resultConvert = convertCount;

			result = Arrays.copyOf(convertStatus, convertStatus.length);
		}

		for(Edge edge : graph[currentNode]){

			if(edge.node == prevNode) continue;

			//true이면 간선을 뒤집은 상태라, 다음 노드를 루트로 잡으면 이전에 구한 개수에서 -1 해줘야 함.
			if(edge.convert){

				convertStatus[edge.idx] = false;
				dfs(edge.node, currentNode, convertCount - 1);
				convertStatus[edge.idx] = true;
			}
			//false이면 간선을 뒤집지 않은 상태라, 다음 노드를 루트로 잡으면 현재 노드로 가기 위해 뒤집어야 해서 +1 해줘야 함.
			else{
				convertStatus[edge.idx] = true;
				dfs(edge.node, currentNode, convertCount + 1);
				convertStatus[edge.idx] = false;
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());
		graph = new List[N];
		for(int i = 0; i < N; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;

			graph[a].add(new Edge(b, i, false));
			graph[b].add(new Edge(a, i , true));
		}

		convertStatus = new boolean[N - 1];
		result = new boolean[N - 1];
		init(0, -1, 1);
		dfs(0, -1, 0);

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < N - 1; i++){
			sb.append(result[i] ? 1 : 0);
		}

		System.out.println(sb.toString());
	}
}
