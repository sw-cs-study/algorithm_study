package baekjoon.week4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs
 * 연결된 그래프의 간선 하나를 제거해서, 두개의 그래프가 되었음
 * 각 그래프 별로 bfs를 돌며, 1번 그래프, 2번 그래프를 구분함.
 * 1,2번 그래프에서 아무 노드나 하나씩 뽑아서 반환.
 *
 * (추가)
 * bfs 말고도, 그래프의 최상위 루트를 찾는 유니온 파인드도 괜찮을 듯 하다.
 * 유니온 파인드로 각 그래프 별 최상위 루트를 찾아서 반환하면 된다.
 *
 * 소요시간 : 15분
 */
public class BOJ17352_여러분의다리가되어드리겠습니다 {

	private static int N;//노드수

	private static int[] visited;//방문처리
	private static List<Integer>[] graph;//그래프

	//bfs
	private static void bfs(int startNode, int number){

		visited[startNode] = number;

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		while(!needVisited.isEmpty()) {

			int currentNode = needVisited.poll();

			for (int nextNode : graph[currentNode]) {

				if (visited[nextNode] != 0)
					continue;

				visited[nextNode] = number;
				needVisited.add(nextNode);
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());

		graph = new List[N + 1];
		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		visited = new int[N + 1];

		for(int i = 0; i < N - 2; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			graph[a].add(b);
			graph[b].add(a);
		}

		int graphNumber = 0;

		int[] result = new int[2];

		for(int i = 1; i <= N; i++){

			if(visited[i] != 0) continue;

			bfs(i, graphNumber + 1);
			result[graphNumber++] = i;

		}

		System.out.println(result[0] + " " + result[1]);
	}
}
