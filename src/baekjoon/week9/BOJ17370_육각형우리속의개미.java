package baekjoon.week9;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 완탐
 * 문제에서 주어지는 방향 회전수가 최대 22 이므로, 중간지점에서 시작했을때 갈 수 있는 최대 길이로 대략2차원 배열을 잡는다.
 */

public class BOJ17370_육각형우리속의개미 {

	//육각형처럼 회전하기 위한 회전 좌표 배열. - 아래, 왼아래, 오아래, 위, 왼위, 오위
	private static final int[] dx = {1, 1, 1, -1, -1, -1};
	private static final int[] dy = {0, -1, 1, 0, -1, 1};

	//각 방향으로 이동 했을때는 이동 가능한 방향이 정해져 있음.
	private static final int[][] moveDir = {{1,2},{0,4},{0,5},{4,5},{3,1},{3,2}};

	//노드
	private static class Node{
		int x, y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;

		}
	}

	private static int N;//방향 회전 수.
	private static boolean[][] graph;//그래프.
	private static int result;//총 경우의 수

	//dfs
	private static void dfs(int prevDir, int changeMove, Node currentNode){

		//방문했던 노드이면 종료.
		if(graph[currentNode.x][currentNode.y]){
			//목표 방향 회전수를 채웠으면 카운트 증가.
			if(changeMove == N) result++;
			return;
		}

		//회전수가 N을 넘었으면 종료.
		if(changeMove == N) return;

		graph[currentNode.x][currentNode.y] = true; //방문처리.

		for(int i = 0 ; i < 2; i++){

			int nextX = currentNode.x + dx[moveDir[prevDir][i]];
			int nextY = currentNode.y + dy[moveDir[prevDir][i]];

			dfs(moveDir[prevDir][i], changeMove + 1, new Node(nextX, nextY));

		}
		//다음 탐색을 위해 방문처리 원복.
		graph[currentNode.x][currentNode.y] = false;


	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		graph = new boolean[47][47]; //중간위치는 23,23
		graph[23][23] = true;
		result = 0;

		//시작은 0, 즉 아래로 내려갔다고 가정하고 시작.
		dfs(0, 0, new Node(24,23));

		System.out.println(result);

	}
}
