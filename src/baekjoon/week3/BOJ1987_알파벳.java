package baekjoon.week3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs
 * dfs를 이용해서 최대 몇칸까지 이동이 가능한지 찾는 문제.
 *
 * (추가)
 * 인덱스를 이용한 배열 엑세스보다 비트연산이 훨씬 빠름...
 * 비트연산을 고려하긴 했는데 별차이 없을 거라고 생각해서 배열로 방문체크를 했지만, 1등코드와 10배이상 차이남.
 */
public class BOJ1987_알파벳 {


	//4방향 탐색
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int R;//세로
	private static int C;//가로

	private static int maxCount;//최대 칸수


	private static char[][] maps;//맵

	//격자판 범위 체크.
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < R &&
			nextY >= 0 && nextY < C;
	}
	//dfs 탐색
	private static void dfs(Node currentNode, boolean[] visited, int count){

		//최대 칸수 업데이트.
		maxCount = Math.max(maxCount, count);

		for(int i = 0; i < 4; i++){

			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			//격자판을 벗어났거나, 이전에 방문했던 알파벳이라면 패스.
			if(!check(nextX, nextY) || visited[maps[nextX][nextY] - 65]) continue;

			visited[maps[nextX][nextY] - 65] = true;//방문처리.

			dfs(new Node(nextX, nextY), visited, count + 1); //다음 노드 탐색 진행.


			visited[maps[nextX][nextY] - 65] = false;//다음 탐색을 위해 방문처리 원복
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		maxCount = Integer.MIN_VALUE;

		maps = new char[R][C];

		for(int i = 0; i < R; i++){
			String temp = br.readLine();
			for(int j = 0; j < C; j++){
				maps[i][j] = temp.charAt(j);
			}
		}

		boolean[] visited = new boolean[26];
		visited[maps[0][0] - 65] = true;

		dfs(new Node(0, 0), visited, 1);

		System.out.println(maxCount);


	}
}
