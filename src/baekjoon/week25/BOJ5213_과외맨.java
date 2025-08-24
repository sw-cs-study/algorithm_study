package baekjoon.week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + 구현
 *
 * 타일을 2차원 배열에 나타내고, dfs 탐색을 진행,
 * 타일의 값을 나타내는 배열과, 타일의 번호를 표기 할 배열을 만들고,
 * 이동시에 타일 번호가 같은 면 그대로 이동, 타일 번호가 다르면 숫자가 같은지 확인.
 * 목적지에 도달하면 역으로 반환하면서 경로를 반환.
 *
 * (추가)
 * 오른쪽, 왼쪽, 아래만 고려했는데, 이동가능한 유일한 경로가 아래에서 위로 가야되는 경우도 있을 수 있음.
 * 즉, 4방향을 다 탐색해야 하고 방문처리도 해줘야 함.
 *
 * (수정)
 * dfs로 탐색하면 지수시간이 걸려서 시간초과(2%)에 걸린다.
 * 최단거리를 찾기 위해서는 bfs를 사용해서 구해줘야 한다.
 *
 * (추가)
 * 현재는 한칸한칸을 하나의 노드처럼 탐색하는데,
 * 하나의 타일에서 나올 수 있는 칸을 한번에 큐에 넣어야 한다.
 * 안그러면 최단경로로 가지 않음.
 */

public class BOJ5213_과외맨 {

	//4방향 - 하, 우,상, 좌
	private static int[] dx = {0, 1, 0, -1};
	private static int[] dy = {1, 0, -1, 0};

	//배열 탐색을 위한 노드
	private static class Node{
		int x,y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static class Tile{
		int num;
		Node left, right;
		public Tile(int num, int leftX, int leftY, int rightX, int rightY){
			this.num = num;
			this.left = new Node(leftX, leftY);
			this.right = new Node(rightX, rightY);
		}

	}

	private static int N;//보드 개수.

	private static int R;//가로
	private static int C;//세로.
	private static int totalTile;//총 타일 개수

	private static int[][] graph;//그래프.
	private static int[][] tileNumGraph;//타일 번호 저장.

	private static int[] prevNumArray;//각 타일의 이전 경로 저장을 위한 배열
	private static Tile[] tileArrays;


	//그래프와, 타일번호 저장 그래프 둘다 채우는 메서드
	//tileNum값은 0부터, tileNumGraph에 저장할때는 1부터.
	private static void initGraph(int tileNum, int a, int b, int shift){


		//tileNum / N 으로 행 번호를 구하고, 행 번호가 짝수이면 N개의 타일 배치
		//행번호가 홀수이면 N - 1개의 타일을 배치(열 좌표를 +1하고 배정해야 함.)
		//열 위치의 경우에는 colNum값에 *2를 함(수를 두개씩 배치하기 떄문에.)

		int rowNum = (tileNum + shift) / N;
		int colNum = (tileNum + shift) % N;

		if (rowNum % 2 == 0){

			graph[rowNum][colNum * 2] = a;
			graph[rowNum][colNum * 2 + 1] = b;

			tileNumGraph[rowNum][colNum * 2] = tileNum + 1;
			tileNumGraph[rowNum][colNum * 2 + 1] = tileNum + 1;

			tileArrays[tileNum + 1] = new Tile(
				tileNum + 1,
				rowNum,
				colNum * 2,
				rowNum,
				colNum * 2 + 1
			);
		}
		else {

			graph[rowNum][colNum * 2 + 1] = a;
			graph[rowNum][colNum * 2 + 2] = b;

			tileNumGraph[rowNum][colNum * 2 + 1] = tileNum + 1;
			tileNumGraph[rowNum][colNum * 2 + 2] = tileNum + 1;

			tileArrays[tileNum + 1] = new Tile(
				tileNum + 1,
				rowNum,
				colNum * 2 + 1,
				rowNum,
				colNum * 2 + 2
			);
		}


	}

	//격자 배열을 벗어나지 않는지 확인.
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < R &&
			nextY >= 0 && nextY < C;
	}

	//dfs 탐색 - 마지막 타일에 도착하지 못한다면, 가장 큰 타일로 가야 하기 때문에 모든 경우를 다 봐야 함.
	//방문처리 배열은 따로 필요 없이, 이전 위치에 대한 정보만 가지고 있으면 됨(왼쪽, 오른쪽의 경우에만 겹칠 수 있음.)
	private static int bfs(Node startNode){
		boolean[] visited = new boolean[totalTile + 1];
		visited[1] = true;

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(1);

		prevNumArray[1] = -1;

		int maxTile = -1; //가장 큰 타일 수.

		while(!needVisited.isEmpty()){

			int tileNum = needVisited.poll();
			Tile currentTile = tileArrays[tileNum];


			if (maxTile < currentTile.num){
				maxTile = currentTile.num;
			}

			//목적지에 도달했으면 더 볼 필요 없음.
			if(currentTile.num == totalTile) return totalTile;

			for(int k = 0; k < 2; k++){

				int currentX = k == 0 ? currentTile.left.x : currentTile.right.x;
				int currentY = k == 0 ? currentTile.left.y : currentTile.right.y;

				for(int i = 0; i < 4; i++){
					int nextX = currentX + dx[i];
					int nextY = currentY + dy[i];


					//이동불가, 방문한 공간, 사용하지 않는칸이면 패스
					if(!check(nextX, nextY) || visited[tileNumGraph[nextX][nextY]] || graph[nextX][nextY] == 0) continue;

					//현재 좌표와 다음 값이 같을때만 이동.
					if(graph[currentX][currentY] != graph[nextX][nextY]) continue;

					int nextTileNum = tileNumGraph[nextX][nextY];

					prevNumArray[nextTileNum] = currentTile.num;
					visited[nextTileNum] = true;
					needVisited.add(nextTileNum);
				}
			}

		}
		return maxTile;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		R = N;
		C = N * 2;

		graph = new int[R][C];
		tileNumGraph = new int[R][C];
		totalTile = (N * N) - (N / 2);
		prevNumArray = new int[totalTile + 1];
		tileArrays = new Tile[totalTile + 1];


		StringBuilder result = new StringBuilder();

		//2N - 1개가 지날때마다 보정 값을 +1 씩 해야 함.
		int shift = 0;
		int count = 0;
		for (int i = 0; i < totalTile; i++){

			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			count++;
			initGraph(i, a, b, shift);

			if(count == (2 * N - 1)){
				count = 0;
				shift++;
			}
		}



		int lastTile = bfs(new Node(0,0));
		int resultCount = 0;

		while(lastTile != -1){

			resultCount++;
			result.insert(0, lastTile + " ");

			lastTile = prevNumArray[lastTile];

		}

		System.out.println(resultCount);
		System.out.print(result);

		// for(int i = 0; i < R; i++){
		// 	System.out.println(Arrays.toString(graph[i]));
		// }

	}
}
