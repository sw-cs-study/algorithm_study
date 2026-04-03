package baekjoon.week41;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 완탐 + bfs
 *
 * 모든 경우의 수에 따라 큐브를 배치하고, 배치완료되면 bfs를 돌리는 방식으로 한다.
 * bfs의 경우에는 상하좌우 말고도 윗칸 아랫칸으로 이동하는 경우도 고려해주어야 한다
 *
 * 큐를 뽑는 경우는 총 5개의 큐브를 각 층마다 하나씩 쌓으면 5!, 각 칸별로 돌려주면 4^5이다.
 * 0 0 0 0 0 => 5개의 판이 각각 회전하지 않음.
 * 1 0 0 0 0 => 5개의 판중 1번판이 시계방향으로 90도
 *
 * (주의)
 * 판넬을 쌓는 순서도 있음.
 */

public class BOJ16985_Maaaaaaaaaze {

	private final static int INF = Integer.MAX_VALUE;

	//큐브 탐색 6방향
	private final static int[] dx = {-1, 1, 0, 0, 0, 0};
	private final static int[] dy = {0, 0, -1, 1, 0, 0};
	private final static int[] dz = {0, 0, 0, 0, -1, 1};

	private static class Node{
		int x,y,z, count;

		public Node(int x, int y, int z, int count){
			this.x = x;
			this.y = y;
			this.z = z;
			this.count = count;
		}
	}

	private static int[][][] graph;//각 판 정보.
	private static int[][][] copyGraph; //각 판을 배치한 배열정보.
	private static boolean[] panelVisited;// 판넬 순서 선택시, 중복 선택 방지용.

	private static int result;

	//특정 판 회전
	private static void rotate(int z){
		int[][] panel = copyGraph[z];
		int[][] temp = new int[panel[0].length][panel.length];

		for(int i = 0; i < panel.length; i++){
			for(int j = 0; j < panel[0].length; j++){
				temp[j][panel.length - 1 - i] = panel[i][j];
			}
		}

		copyGraph[z] = temp;

	}

	//배열 복제
	private static void copyArrays(int originNum, int copyNum){

		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				copyGraph[copyNum][i][j] = graph[originNum][i][j];
			}
		}

	}


	//순열을 이용해서, 배치.
	private static void permute(int panelNum){


		//모든 판넬 순서를 배치했으면, 회전하고 bfs 돌려야 함.
		if(panelNum == 5) {
			recursive(0);
			return;
		}


		for(int i = 0; i < 5; i++){
			if(panelVisited[i]) continue;

			panelVisited[i] = true;
			copyArrays(i, panelNum);
			permute(panelNum + 1);
			panelVisited[i] = false;

		}

	}

	//부분집합으로 각 판넬 돌리기.
	private static void recursive(int panelNum){

		//현재 큐브 상태에서 bfs 돌리기.
		int moveCount = bfs();
		if(moveCount != -1 && moveCount < result){
			result = moveCount;
		}

		if(panelNum == 5) return;

		//현재 판넬 회전없이 패스하기.
		// recursive(panelNum + 1);

		for(int i = 0; i < 4; i++){

			//현재 선택된 판넬에서 회전시키기.
			rotate(panelNum);
			recursive(panelNum + 1);
		}


	}

	private static boolean check(int nextX, int nextY, int nextZ){
		return nextX >= 0 && nextX < 5 &&
			nextY >= 0 && nextY < 5 &&
			nextZ >= 0 && nextZ < 5;
	}

	//bfs 탐색.
	private static int bfs(){

		//둘중에 하나라도 0이라면 bfs 필요 없음.
		if(copyGraph[0][0][0] == 0 || copyGraph[4][4][4] == 0) return -1;

		boolean[][][] visited = new boolean[5][5][5];
		visited[0][0][0] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(0,0,0, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(currentNode.x == 4 && currentNode.y == 4 && currentNode.z == 4) return currentNode.count;

			for(int i = 0; i < 6; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];
				int nextZ = currentNode.z + dz[i];

				if(!check(nextX, nextY, nextZ) || visited[nextZ][nextX][nextY] || copyGraph[nextZ][nextX][nextY] == 0) continue;

				visited[nextZ][nextX][nextY] = true;
				needVisited.add(new Node(nextX, nextY, nextZ, currentNode.count + 1));
			}
		}

		return -1;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		result = INF;
		graph = new int[5][5][5];
		panelVisited = new boolean[5];
		copyGraph = new int[5][5][5];

		for(int z = 0; z < 5; z++){
			for(int x = 0; x < 5; x++){
				st = new StringTokenizer(br.readLine());
				for(int y = 0; y < 5; y++){
					graph[z][x][y] = Integer.parseInt(st.nextToken());
				}
			}
		}

		permute(0);
		System.out.println(result == INF ? -1 : result);

	}

}
