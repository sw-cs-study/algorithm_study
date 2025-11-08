package baekjoon.week31;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 구현 + 백트래킹
 * 스도쿠임,
 */
public class BOJ4574_스도미노쿠 {

	//도미노를 놓기 위한 방향 - 좌상에서 우하로 이동하기 떄문에 오른쪽과 아래만 보면 됨.
	private final static int[] dx = {1, 0};
	private final static int[] dy = {0, 1};



	private static int N; //채워져 있는 도미노의 개수.
	private static int[][] maps;//스도쿠 배열.
	private static boolean[][] dominoVisited; // 도미노 방문처리.

	//해당 위치에 도미노를 놓을 수 잇는지 확인.
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < 9 &&
			nextY >= 0 && nextY < 9;
	}

	//스도쿠 검증 - 검증은 스토쿠를 놓은 부분이 영향을 주는 곳만 확인하면 됨.
	/*
	각 행에는 1부터 9까지 숫자가 하나씩 있어야 한다.
	각 열에는 1부터 9까지 숫자가 하나씩 있어야 한다.
	3×3크기의 정사각형에는 1부터 9가지 숫자가 하나씩 있어야 한다.
	 */
	private static boolean puzzleCheck(int x, int y, int value){

		//각 행 확인.

		boolean[] visited = new boolean[10];
		visited[value] = true;
		for (int i = 0; i < 9; i++) {

			if(maps[x][i] == 0) continue;
			if(visited[maps[x][i]]) return false;

			visited[maps[x][i]] = true;
		}



		//각 열 확인.
		visited = new boolean[10];
		visited[value] = true;
		for (int i = 0; i < 9; i++) {

			if(maps[i][y] == 0) continue;
			if(visited[maps[i][y]]) return false;

			visited[maps[i][y]] = true;
		}


		//각 3*3 영역 확인.
		int startX = (x / 3) * 3;
		int startY = (y / 3) * 3;

		visited = new boolean[10];
		visited[value] = true;
		for(int i = startX; i < startX + 3; i++){
			for(int j = startY; j < startY + 3; j++){

				if(maps[i][j] == 0) continue;
				if(visited[maps[i][j]]) return false;

				visited[maps[i][j]] = true;
			}
		}

		return true;
	}

	//재귀 호출하면서 스도쿠 채우기 - true면 모든 스도쿠를 채움
	private static boolean dfs(int currentNode){

		//모든 노드 배치가 끝나면 종료.
		if(currentNode == 81) return true;


		int row = currentNode / 9;
		int col = currentNode % 9;

		//해당 위치에 넣을 수 없다면 다음수로 이동.
		if(maps[row][col] != 0) return dfs(currentNode + 1);

		//첫번째 블록에 넣을 값.
		for(int i = 1; i <= 9; i++){

			//해당위치에 해당 수를 놓을 수 없다면, 패스
			if(!puzzleCheck(row, col, i)) continue;

			maps[row][col] = i;


			//두번째 수 놓기.
			for(int dir = 0; dir < 2; dir++){

				int nextX = row + dx[dir];
				int nextY = col + dy[dir];

				//둘 수 없는 곳이면 패스 .
				if(!check(nextX, nextY) || maps[nextX][nextY] != 0) continue;

				//수 확인.
				for(int j = 1; j <= 9; j++){

					//스도쿠 규칙에 맞지 않거나, 사용한 도미노라면 패스.
					if(!puzzleCheck(nextX, nextY, j) || dominoVisited[i][j] || i == j) continue;

					maps[nextX][nextY] = j;
					dominoVisited[i][j] = true;
					dominoVisited[j][i] = true;
					if(dfs(currentNode + 1)) return true;
					dominoVisited[i][j] = false;
					dominoVisited[j][i] = false;

					maps[nextX][nextY] = 0;

				}
			}

			maps[row][col] = 0; //다음 탐색을 위한 원복.
		}

		return false;
	}

	//스도쿠 결과 출력
	private static String puzzleResult(){
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				sb.append(maps[i][j]);
			}

			if(i == 8) continue;
			sb.append("\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = 1;
		StringBuilder result = new StringBuilder();
		while((N = Integer.parseInt(br.readLine())) != 0){

			if(testCase != 1) result.append("\n");

			maps = new int[9][9];
			dominoVisited = new boolean[10][10];
			//초기 도미노 블럭 위치.
			for(int i = 0; i < N; i++){
				st = new StringTokenizer(br.readLine());

				int U = Integer.parseInt(st.nextToken());
				String LU = st.nextToken();
				int V = Integer.parseInt(st.nextToken());
				String LV = st.nextToken();

				int firstA = LU.charAt(0) - 'A';
				int firstB= Character.getNumericValue(LU.charAt(1)) - 1;
				int secondA = LV.charAt(0) - 'A';
				int secondB= Character.getNumericValue(LV.charAt(1)) - 1;

				maps[firstA][firstB] = U;
				maps[secondA][secondB] = V;
				dominoVisited[U][V] = true;
				dominoVisited[V][U] = true;


			}

			//초기 숫자 위치.
			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= 9; i++){
				String tempInput = st.nextToken();
				maps[tempInput.charAt(0) - 'A'][Character.getNumericValue(tempInput.charAt(1)) - 1] = i;
			}

			dfs(0);

			result.append("Puzzle ").append(testCase).append("\n")
				.append(puzzleResult());
			testCase++;

		}

		System.out.println(result.toString());
	}
}
