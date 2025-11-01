package baekjoon.week30;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 빡구현
 */
public class BOJ23290_마법사상어와복제 {

	//방향 - ←(1), ↖(2), ↑(3), ↗(4), →(5), ↘(6), ↓(7), ↙(8)
	private final static int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1};
	private final static int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1};
	//상어의 방향.
	private final static int[] sdx = {0, -1, 0, 1, 0};
	private final static int[] sdy = {0, 0, -1, 0, 1};


	//객체
	private static class Node{
		int x, y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}

	}

	private static int M;//물고기 수
	private static int S;//마법 연습 수.
	private static Node sharkLoc;//상어위치

	private static String maxMove; //상어가 특정위치에서 가장 물고기를 많이 먹을 수 있는 위치.
	private static int maxFish;//상어가 특정 위치에서 3칸 이동했을때, 먹을 수 있는 물고기의 최대 수.

	//맵 상태 - 물고기 표시(방향별로 표기해서, 같은 방향의 물고기를 묶어 반복을 줄임 - 안에 값은 물고기의 수.)
	private static int[][][] maps;
	//냄새 체크 - 삭제 되는 시점의 반복횟수를 저장.
	private static int[][] smellMaps;

	//상어의 이동시에 방문체크용(방문 했던 곳의 물고기 수는 반영안함.)
	private static boolean[][] visited;

	//복제마법 수행 - 복제 대상 저장.
	private static int[][][] replicaSave(){

		//기존 배열을 그대로 복사.
		int[][][] copyMap = new int[5][5][9];
		for(int i  = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				for(int d = 1; d <= 8; d++){
					copyMap[i][j][d] = maps[i][j][d];
				}
			}
		}

		return copyMap;
	}

	//복제마법 수행 - 저장한 대상 추가.
	private static void replicaExecute(int[][][] copyMap){

		for(int i  = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				for(int d = 1; d <= 8; d++){
					maps[i][j][d] += copyMap[i][j][d];
				}
			}
		}

	}
	//물고기의 이동 가능 위치확인.
	private static boolean fishCheck(int nextX, int nextY, int cycle){
		return nextX >= 1 && nextX <= 4 &&
			nextY >= 1 && nextY <= 4 && //격자를 넘어가면 안됨.
			!(sharkLoc.x == nextX && sharkLoc.y == nextY) && //해당 위치에 상어가 없어야 함.
			(smellMaps[nextX][nextY] == 0 || smellMaps[nextX][nextY] + 3 <= cycle); //냄새가 남아있으면 안됨.
	}

	//상어의 이동가능 위치 확인.
	private static boolean sharkCheck(int nextX, int nextY){
		return nextX >= 1 && nextX <= 4 &&
			nextY >= 1 && nextY <= 4;
	}

	//방향 회전
	private static int rotateDir(int dir){

		dir = dir - 1;

		if(dir == 0) dir = 8;

		return dir;
	}

	//물고기 이동. - 모든 물고기가 한칸 이동.(모든 물고기가 한번에 이동해야 함.)
	private static void fishMove(int cycle){

		int[][][] copyMap = new int[5][5][9];

		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				for(int dir = 1; dir <= 8; dir++){

					//해당 위치의 해당 방향을 가진 물고기가 없으면 패스.
					if(maps[i][j][dir] == 0) continue;

					//각 방향별 물고기들이 이동이 가능한지 확인 - 총 7번 회전.
					int nextDir = dir;
					boolean flag = false;
					for(int count = 0; count <= 7; count++){
						if(count != 0) nextDir = rotateDir(nextDir);

						int nextX = i + dx[nextDir];
						int nextY = j + dy[nextDir];

						//이동 가능한 위치인지 확인.
						if(!fishCheck(nextX, nextY, cycle)) continue;

						//이동이 가능하면 해당 위치로 이동하기 위해 복제본에 저장.
						copyMap[nextX][nextY][nextDir] += maps[i][j][dir];
						maps[i][j][dir] = 0;
						flag = true;
						break;
					}

					//flag업데이트가 안되면 갈 수 있는 위치가 없다는 뜻.
					if(!flag){
						copyMap[i][j][dir] += maps[i][j][dir];
						maps[i][j][dir] = 0;
					}
				}
			}
		}



		//복제본에 있는 값을 원본 배열로 옮김
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 4; j++){
				for(int dir = 1; dir <= 8; dir++){
					maps[i][j][dir] += copyMap[i][j][dir];
				}
			}
		}
	}

	//상어의 이동 - 재귀 호출을 이용해서 뎁스가 3일때까지 반복, 이 과정에서 최대 값 찾아내기.
	private static void sharkMove(Node currentNode, int fishCount, String moveDir, int moveCount){

		//3칸 이동이 끝났으면 종료
		if(moveCount == 3){
			//이전에 저장된 물고기 수와 비교해서 더 크면 업데이트.
			if(fishCount > maxFish){
				maxFish = fishCount;
				maxMove = moveDir;
			}
			//같은 경우에는 사전순으로 앞선 것을 선택.
			else if(fishCount == maxFish && (moveDir.compareTo(maxMove) < 0)){
				maxMove = moveDir;
			}
			return;
		}

		for(int i = 1; i <= 4; i++){
			int nextX = currentNode.x + sdx[i];
			int nextY = currentNode.y + sdy[i];

			if(!sharkCheck(nextX, nextY)) continue;

			int nextFishCount = fishCount;

			//방문을 하지 않은 곳이라면 물고기 카운팅.
			if(!visited[nextX][nextY]){
				visited[nextX][nextY] = true;
				for(int dir = 1; dir <= 8; dir++){
					nextFishCount += maps[nextX][nextY][dir];
				}
				sharkMove(new Node(nextX, nextY), nextFishCount, moveDir + i, moveCount + 1);
				visited[nextX][nextY] = false;
			}
			//방문을 한 곳이라면 그냥 재귀호출만 함.
			else{
				sharkMove(new Node(nextX, nextY), nextFishCount, moveDir + i, moveCount + 1);
			}
		}
	}

	//상어가 이동할 방향을 순서대로 확인했으면,해당 방향에 있는 물고기를 제외하고 냄새에 추가 및 상어 위치 업데이트.
	private static void fishDelete(int cycle){

		int nextX = sharkLoc.x;
		int nextY = sharkLoc.y;

		for(int i = 0; i < 3; i++){
			int moveDir = Character.getNumericValue(maxMove.charAt(i));

			nextX = nextX + sdx[moveDir];
			nextY = nextY + sdy[moveDir];

			//해당위치에 있는 물고기 전부 삭제.
			boolean flag = false;
			for(int dir = 1; dir <= 8; dir++){

				if(maps[nextX][nextY][dir] != 0) flag = true;

				maps[nextX][nextY][dir] = 0;
			}
			//삭제 처리된 물고기가 있었으면 냄새를 남겨야 함.
			if(flag){
				smellMaps[nextX][nextY] = cycle;
			}
		}

		//상어 위치 업데이트.
		sharkLoc = new Node(nextX, nextY);
	}

	//최종 물고기 수 출력
	private static int totalFish(){

		int totalCount = 0;
		for(int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				for (int dir = 1; dir <= 8; dir++) {

					totalCount += maps[i][j][dir];

				}
			}
		}

		return totalCount;
	}


	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		maps = new int[5][5][9];
		smellMaps = new int[5][5];

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());

			maps[x][y][d]++;

		}

		st = new StringTokenizer(br.readLine());
		sharkLoc = new Node(
			Integer.parseInt(st.nextToken()),
			Integer.parseInt(st.nextToken())
		);

		visited = new boolean[5][5];


		for(int cycle = 1; cycle <= S; cycle++){

			//복제 시작
			int[][][] copyMap = replicaSave();

			//물고기의 이동.
			fishMove(cycle);

			//상어 이동 전 필요 변수 초기화
			maxMove = "999";
			maxFish = Integer.MIN_VALUE;


			//상어의 이동
			sharkMove(new Node(sharkLoc.x, sharkLoc.y), 0, "", 0);

			//상어 이동경로의 물고기 삭제.
			fishDelete(cycle);

			//복제 종료.
			replicaExecute(copyMap);
		}

		System.out.println(totalFish());
	}
}
