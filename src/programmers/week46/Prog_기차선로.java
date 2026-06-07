package programmers.week46;

import java.util.Arrays;

/**
 * 아이디어
 * 백트레킹.
 *
 * 기본적으로 완탐으로 모든 경우를 다 탐색한다.
 * 1,1 위치에서 이동을 하는데, 철도 별로 이동 가능한 방향이 있기 때문에 철도별 방향을 저장해둔다.
 * 또한 이동후에 둘수 있는 철도는 정해져있기 때문에 2차원 배열을 이용해서 철도 별로 둘수 있는 다음 철도 정보를 저장한다.
 */
public class Prog_기차선로 {

	//상: 0, 하: 1, 좌: 2, 우: 3
	private static int[] dx = {-1, 1, 0, 0};
	private static int[] dy = {0, 0, -1, 1};

	//현재 진행 방향으로 가게 되면 놓을 수 있는 레일
	private static int[][] dirNextRail = {
		{2,3,6,7}, // 상 방향으로 갈때,
		{2,3,4,5}, // 하 방향으로 갈때.
		{1,3,5,6}, // 좌 방향으로 갈때,
		{1,3,4,7} // 우 밯얗으로 갈때.
	};

	private static int n;
	private static int m;
	private static int[][] maps;
	private static int answer;
	private static int[][] visited;

	//격자판을 벗어나거나, 둘수 없는 곳인지 확인.
	private static boolean checkMap(int nextX, int nextY){

		return nextX >= 0 && nextX < n &&
			nextY >= 0 && nextY < m &&
			maps[nextX][nextY] != -1;
	}

	//다음 방향
	private static int getNextDir(int rail, int dir){

		int tempDir = dir;

		switch(rail){
			//1,2,3번 레일은 기존 방향 그대로임.
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				tempDir = dir == 3 ? 0 : 2;
				break;
			case 5:
				tempDir = dir == 2 ? 0 : 3;
				break;
			case 6:
				tempDir = dir == 0 ? 3 : 1;
				break;
			case 7:
				tempDir = dir == 3 ? 1 : 2;
				break;
		}

		return tempDir;
	}


	//레일과 기차 방향에 따라 레일 배치 가능한지 확인 - 기존에 이미 배치된 레일을 지나갈수 있는지 확인을 위함.
	private static boolean checkRailDir(int rail, int prevDir){

		switch(rail){
			case 1:
				return prevDir == 2 || prevDir == 3;
			case 2:
				return prevDir == 0 || prevDir == 1;
			case 3: // 모든 방향 가능.
				return true;
			case 4:
				return prevDir == 3 || prevDir == 1;
			case 5:
				return prevDir == 1 || prevDir == 2;
			case 6:
				return prevDir == 0 || prevDir == 2;
			case 7:
				return prevDir == 3 || prevDir == 0;
			default: //현재 문제에서는 발생하지 않는 케이스지만 문법상 추가.
				return false;
		}
	}

	//모든 레일을 지나쳤는지
	private static boolean allRailCheck(){

		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){

				//3번 레일이면 2번 밟아야 함.
				if(maps[i][j] == 3 && visited[i][j] != 2) return false;

				//나머지레일이면 1번만 밞으면 됨.
				if(maps[i][j] >= 1 && maps[i][j] <= 7 && visited[i][j] < 1) return false;
			}
		}

		return true;
	}

	//dfs
	private static void dfs(int currentX, int currentY, int dir){

		//이동 불가(격자를 넘어갔거나, 사물이 있음)
		if(!checkMap(currentX, currentY)) return;

		//목표위치에 도달했으면 확인
		if(currentX == n - 1 && currentY == m - 1){


			// for(int i = 0; i < n; i++){
			// 	System.out.println(Arrays.toString(visited[i]));
			// }
			//
			// System.out.println("-----------------------------");
			// for(int i = 0; i < n; i++){
			// 	System.out.println(Arrays.toString(maps[i]));
			// }
			//
			// System.out.println("=============================");

			//모든 레일을 다 지나쳤는지.


			if(allRailCheck() && checkRailDir(maps[currentX][currentY], dir)) answer++;
			return;
		}

		//위에 안걸렸으면 방문처리.
		visited[currentX][currentY]++;

		int nextX = 0;
		int nextY = 0;
		int nextDir = dir;

		//현재 위치에 레일이 있는 경우,
		if(maps[currentX][currentY] != 0){

			//현재 방향과 레일을 비교해서 이동이 가능한지 확인.
			if(checkRailDir(maps[currentX][currentY], dir)){
				//다음 방향.
				nextDir = getNextDir(maps[currentX][currentY], dir);

				nextX = currentX + dx[nextDir];
				nextY = currentY + dy[nextDir];

				dfs(nextX, nextY, nextDir);
			}
		}

		//현재 위치에 레일이 없는 경우,
		else if(maps[currentX][currentY] == 0){

			//가능한 경우를 전부 사용
			for(int nextRail : dirNextRail[dir]){

				maps[currentX][currentY] = nextRail;

				//다음 방향.
				nextDir = getNextDir(nextRail, dir);

				nextX = currentX + dx[nextDir];
				nextY = currentY + dy[nextDir];

				dfs(nextX, nextY, nextDir);

			}

			//다 끝나면 해당 칸은 다시 빈칸으로 두어서 다음 탐색 진행하도록 함.
			maps[currentX][currentY] = 0;
		}

		//재귀호출 끝나면 방문처리 취소 - 다음 경로 확인을 위해,
		visited[currentX][currentY]--;

	}


	public int solution(int[][] grid) {

		n = grid.length;
		m = grid[0].length;
		maps = grid;
		visited = new int[n][m]; //방문횟수 누적(십자가 모양은 방문회수가 2회여야 함.)
		answer = 0; // 최종 정답.


		visited[n - 1][m - 1] = 1; // 마지막 레일은 1로 처리해둠.
		//시작 방향은 1번 레일임. 방향은 오른쪽.
		dfs(0, 0 ,3);

		return answer;
	}

	public static void main(String[] args){
		Prog_기차선로 p = new Prog_기차선로();

		int[][] grid1 = {{1, 0, -1}, {0, 0, 7}, {0, 0, 2}};
		int[][] grid2 = {{1, 0, 0, 0, 0, -1, -1}, {-1, 0, 0, 1, 0, 0, 1}};
		int[][] grid3 = {{1, 0, 0, 0, 0}, {0, 0, 3, 0, 2}, {0, 0, 0, 0, 2}};
		int[][] grid4 = {{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}};
		int[][] grid5 = {{1, 7}, {0, 2}};
		int[][] grid6 = {{1, -1, 0, 0}, {-1, 0, 0, 0}, {0, 0, 0, -1}, {0, 0, -1, 1}};

		System.out.println(p.solution(grid1));
		System.out.println(p.solution(grid2));
		System.out.println(p.solution(grid3));
		System.out.println(p.solution(grid4));
		System.out.println(p.solution(grid5));
		System.out.println(p.solution(grid6));


	}
}
