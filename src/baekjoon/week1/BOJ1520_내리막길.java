package baekjoon.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs+dp
 * 최대 배열의 크기가 500*500 이므로 매번 새로 경로 탐색을 하면 시간초과 발생
 * 이미 탐색했던 경로의 수를 저장하고, 다른 경로에서 해당 위치까지 오면, 저장된 경로를 반환하도록 한다.
 *
 * (주의)
 * 초기에는 이전에 이동한 방향을 저장해서, 왔던길을 다시 가는 경우를 방지하려고 했는데, 이전값보다 작은 위치로만 이동 가능하기 때문에,
 * 굳이 따로 파악하지 않아도, 왔던 길로 돌아가지는 않음.
 *
 * (1차시도 - 40퍼 메모리 초과.)
 * 1차 시도에서는, 목적지에 도달 할 수 없는 경로를 체크하지 않아서
 * 이전에 탐색결과, 목적지에 도달할 수 없음에도 재귀를 통해 탐색을 하여 메모리 초과가 남,
 *
 * (최종)
 * dp 배열의 초기값을 -1로 두고, -1이면 아직 탐색 안한 상태, 0이면 목적지에 도달 할 수 없는 상태로 두어,
 * 다음 탐색시에 도달 불가 경로를 재 탐색하지 않도록 함.
 *
 */
public class BOJ1520_내리막길 {

	//4방향 탐색 - 상하좌우
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	//좌표 탐색을 위한 객체
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//주어지는 배열 정보
	private static int m; //세로
	private static int n;//가로

	private static int[][] maps; //내리막길 정보가 있는 배열.
	private static int[][] dp; //경로 가지수 저장.

	//배열 벗어났는지 유무 확인 메서드
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < m &&
			nextY >= 0 && nextY < n;
	}

	//dfs 탐색 메서드
	private static int dfs(Node currentNode){

		//우측 최 하단(m - 1, n - 1) 이라면 1을 반환
		if(currentNode.x == m -1 && currentNode.y == n - 1) return 1;

		//이전에 탐색한 곳이라면, 저장된 값 반환.
		if(dp[currentNode.x][currentNode.y] > 0) return dp[currentNode.x][currentNode.y];

		dp[currentNode.x][currentNode.y] = 0;
		//4방항 탐색 수행
		for(int i = 0; i < 4; i++){

			//다음 위치 좌표 구하기.
			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			//배열을 벗어난 좌표이거나, 내리막길이거나 이전에 탐색했을때, 목표에 도달이 불가능하다면 패스.
			if(!check(nextX, nextY)
				|| maps[currentNode.x][currentNode.y] <= maps[nextX][nextY]
				|| dp[nextX][nextY] == 0) continue;

			//벗어나지 않았다면 재귀 호출을 통해 다음위치로 이동. - 반환 된 값은 현재 위치에 더해야 함
			dp[currentNode.x][currentNode.y] += dfs(new Node(nextX, nextY));
		}

		return dp[currentNode.x][currentNode.y];
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		maps = new int[m][n];
		dp = new int[m][n];

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				maps[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = -1; //탐색 유무 확인을 위해 기본값을 -1로 처리.
			}
		}

		System.out.println(dfs(new Node(0,0)));
	}
}
