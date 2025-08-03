package baekjoon.week23;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp + 그래프
 *
 * (최초 아이디어)
 * bfs를 이용해서 매번 모든 노드를 이동시키는 방법 -> 70프로 시간초과
 *
 * (개선안)
 * 특정 위치를 밟았을때, 최종적으로 어떤 위치로 가는지는 정해져있기 때문에, 한번 탐색을 했으면 끝까지 가지 않고,
 * 각 위치가 최종적으로 어디로가는지만 저장
 * 예를 들면 2,2를 밟으면 4,4 까지 이동하는게 최선 -> 2,2에 4,4를 저장했다가 2,2에 오게 되면 끝까지 탐색하지 않고 4,4에 값을 추가하는식으로 변경.
 */
public class BOJ16957_체스판위의공 {

	//8방향 -
	private static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
	private static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
	//노드
	private static class Node{
		int x, y, count;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, int count){
			this.x = x;
			this.y = y;
			this.count = count;
		}

	}

	//가로 세로
	private static int R;
	private static int C;

	//그래프
	private static int[][] graph;

	//체스공 저장.
	private static Node[][] dp;

	//격자판 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < R &&
			nextY >= 0 && nextY < C;
	}

	//주변 8방향 체크 - 가장 작은 값이 적은 위치로 이동.
	private static Node valueCheck(int currentX, int currentY){

		int targetValue = graph[currentX][currentY];


		int nextX = -1;
		int nextY = -1;
		int minValue = targetValue;

		for(int i = 0; i < 8; i++){

			int tempNextX = currentX + dx[i];
			int tempNextY = currentY + dy[i];

			if(!check(tempNextX, tempNextY)) continue;

			if(minValue <= graph[tempNextX][tempNextY]) continue;

			minValue = graph[tempNextX][tempNextY];
			nextX = tempNextX;
			nextY = tempNextY;
		}

		return new Node(nextX, nextY);
	}

	//bfs 탐색
	private static Node dfs(Node currentNode){

		//해당 위치에 값이 있으면 그대로 반환
		Node tempNode = dp[currentNode.x][currentNode.y];
		if (tempNode.x != -1 && tempNode.y != -1) return tempNode;

		//위에서 종료되지 않았으면, 아직 탐색해야 함.
		Node nextTempNode = valueCheck(currentNode.x, currentNode.y); //다음 이동 위치구하기.

		//다음이동위치가 없으면 종료
		if (nextTempNode.x == -1 && nextTempNode.y == -1) {
			tempNode.x = currentNode.x;
			tempNode.y = currentNode.y;
			return tempNode;
		}

		//다음 이동위치가 있으면 이동하고 업데이트
		Node nextNode = dfs(new Node(nextTempNode.x, nextTempNode.y));
		tempNode.x = nextNode.x;
		tempNode.y = nextNode.y;
		return tempNode;

	}

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		graph = new int[R][C];
		dp = new Node[R][C];

		for (int i = 0; i < R; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < C; j++){
				graph[i][j] = Integer.parseInt(st.nextToken());

				dp[i][j] = new Node(-1, -1, 0);
			}
		}


		for (int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				Node node = dfs(new Node(i, j));

				dp[node.x][node.y].count++;
			}
		}
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				result.append(dp[i][j].count).append(" ");
			}
			result.append("\n");
		}

		System.out.println(result);
	}
}
