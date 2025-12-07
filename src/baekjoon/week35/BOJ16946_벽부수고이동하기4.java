package baekjoon.week35;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs
 * 모든 벽마다 bfs를 돌면 시간터짐
 * 따라서 미리 0인 그룹을 bfs로 구해두고, 1에서 4방향을 탐색하는 식으로 하면 N^2으로 처리가능.
 */
public class BOJ16946_벽부수고이동하기4 {

	//4방향 탐색
	private static int[] dx = {-1, 1, 0, 0};
	private static int[] dy = {0, 0, -1, 1};


	//노드
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int N;//행
	private static int M;//열
	private static int[][] graph;//그래프
	private static int[][] result;//결과
	private static Map<Integer, Integer> mapping;//그래프 그룹별 개수 매핑

	//격자 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//bfs로 갯수 구하기.
	private static void bfs(Node startNode, int count){

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		graph[startNode.x][startNode.y] = count;
		mapping.put(count, 1);

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY) || graph[nextX][nextY] != 0) continue;

				needVisited.add(new Node(nextX, nextY));
				graph[nextX][nextY] = count;
				mapping.put(count, mapping.get(count) + 1);
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new int[N][M];
		result = new int[N][M];
		mapping = new HashMap<>();

		for(int i = 0; i < N; i++){
			String temp = br.readLine();
			for(int j = 0; j < M; j++){
				graph[i][j] = Character.getNumericValue(temp.charAt(j));
				result[i][j] = graph[i][j];
			}
		}

		//0인 것 카운트.
		int count = 2;
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){

				if(graph[i][j] != 0) continue;

				bfs(new Node(i, j), count);
				count++;
			}
		}

		//벽인부분에서 확인하기.
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){

				if(graph[i][j] != 1) continue;

				Set<Integer> visited = new HashSet<>();
				for(int dir = 0; dir < 4; dir++){
					int nextX = i + dx[dir];
					int nextY = j + dy[dir];

					if(!check(nextX, nextY) || graph[nextX][nextY] == 1 || visited.contains(graph[nextX][nextY])) continue;


					result[i][j] += mapping.get(graph[nextX][nextY]);
					visited.add(graph[nextX][nextY]);

				}
			}
		}



		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				sb.append(result[i][j] % 10);
			}
			sb.append("\n");
		}
		System.out.println(sb);

	}
}
