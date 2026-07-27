package programmers.week49;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * 아이디어
 * bfs + dfs
 */

public class Prog_바이러스파이프 {

	public static class Edge{
		int node, edge;

		public Edge(int node, int edge){
			this.node = node;
			this.edge = edge;
		}
	}

	private static int result;

	private static List<Edge>[] graph;//그래프.

	private static Deque<Integer> virus; //감염체정보.

	//dfs
	private static void dfs(int k, int count){

		//파이프 행동 수 k개 넘으면 종료.
		if(count > k){
			result = Math.max(result, virus.size());
			return;
		}

		for(int i = 1; i <= 3; i++){

			//bfs 실행
			int newVirusCount = bfs(i);

			//재귀호출
			dfs(k, count + 1);

			//추가된 파이러스 제거.
			for(int j = 0; j < newVirusCount; j++){
				virus.pollLast();
			}
		}
	}

	//bfs - return은 몇개를 추가로 감염시켰는지 개수 반환, dfs에서 원복시 해당 개수만큼 poll
	private static int bfs(int type){


		int addVirusCount = 0; //추가되는 바이러스 개수.

		boolean[] visited = new boolean[graph.length];
		for(int temp : virus){
			visited[temp] = true;
		}

		Queue<Integer> needVisited = new ArrayDeque<>(virus);

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(Edge nextEdge : graph[currentNode]){

				if(visited[nextEdge.node] || nextEdge.edge != type) continue;

				addVirusCount++;
				visited[nextEdge.node] = true;
				needVisited.add(nextEdge.node);
				virus.add(nextEdge.node); //다음 파이프 오픈시 탐색을 위해.
			}

		}
		return addVirusCount;
	}


	//초기값
	private static void init(int n, int infection, int[][] edges){

		result = 0;

		graph = new ArrayList[n + 1];
		for(int i = 1; i <= n; i++){
			graph[i] = new ArrayList<>();
		}

		for(int[] edge : edges){
			graph[edge[0]].add(new Edge(edge[1], edge[2]));
			graph[edge[1]].add(new Edge(edge[0], edge[2]));
		}

		virus = new ArrayDeque<>();
		virus.add(infection);
	}

	public int solution(int n, int infection, int[][] edges, int k) {

		init(n, infection, edges);
		dfs(k, 1);

		return result;
	}

	public static void main(String[] args){

		Prog_바이러스파이프 p = new Prog_바이러스파이프();

		int n1 = 10;
		int infection1 = 1;
		int[][] edges1 = {{1, 2, 1}, {1, 3, 1}, {1, 4, 3}, {1, 5, 2}, {5, 6, 1}, {5, 7, 1}, {2, 8, 3}, {2, 9, 2}, {9, 10, 1}};
		int k1 = 2;
		System.out.println(p.solution(n1, infection1, edges1, k1));

		int n2 = 7;
		int infection2 = 6;
		int[][] edges2 = {{1, 2, 3}, {1, 4, 3}, {4, 5, 1}, {5, 6, 1}, {3, 6, 2}, {3, 7, 2}};
		int k2 = 3;
		System.out.println(p.solution(n2, infection2, edges2, k2));

	}
}
