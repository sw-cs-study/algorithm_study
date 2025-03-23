package baekjoon.week9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 개빡구현
 *
 * 문제에서 주어진대로 구현하면 된다.
 * 낚시를 하는 격자판은 2차원 배열로 구현하고, 상어의 경우 가지고 있는 정보를 담기 위해 객체로 만들어 관리한다.
 * 격자판의 상어가 여러마리 배치 되는 경우에는 크기가 가장 큰 상어가 나머지를 잡아 먹는다고 했기 때문에,
 * 상어 이동후에 해당 칸에 다른 상어가 있는지 확인하고, 크기가 큰 상어만 남겨두어야 한다.
 * 낚시왕은 오른쪽으로 계속이동하다 더이상 이동이 불가능하면 멈춘다
 *
 */
public class BOJ17143_낚시왕 {

	//방향 - 상,좌,하,우(벽에 부딪칠경우, 방향전환을 해야 해서, 해당 방향에서 +2 하고 %2 하면 전환된 방향이 나오도록 배치함.)
	private final static int[] dx = {-1, 0, 1, 0};
	private final static int[] dy = {0, -1, 0, 1};

	//상어 객체
	private static class Shark{
		int num, x, y, size, dir, speed;//상어번호, 상어 위치와 크기, 바라보는 방향, 속력(초당 몇칸을 가는지.).

		public Shark(int num, int x, int y, int speed, int dir, int size){
			this.num = num;
			this.x = x;
			this.y = y;
			this.size = size;
			this.dir = dir;
			this.speed = speed;
		}

		//방향 전환
		public void changeDir(){
			this.dir = (this.dir + 2) % 4;
		}

		@Override
		public String toString() {
			return "Shark{" +
				"dir=" + dir +
				", num=" + num +
				", x=" + x +
				", y=" + y +
				", size=" + size +
				", speed=" + speed +
				'}';
		}
	}

	private static int R;//격자판 세로
	private static int C;//격자판 가로.
	private static int M;//상어의 수.
	private static int[][] graph;//격자판 - 격자판에는 상어인덱스가 위치.

	private static Shark[] sharkArray;//상어 정보


	//초기에 방향을 내가 정한 방식으로 변경하는 메서드
	//1 -> 0, 2-> 2, 3 -> 3, 4 -> 1
	private static int dirChange(int inputDir){

		int changeDir = -1;

		switch (inputDir){
			case 1:
				changeDir = 0;
				break;
			case 2:
				changeDir = 2;
				break;
			case 3:
				changeDir = 3;
				break;
			case 4:
				changeDir = 1;
				break;
			default:
				break;
		}

		return changeDir;
	}

	//낚시왕의 위치에서 해당 열중, 바닥과 가장 가까운 상어 잡기.
	private static int catchShark(int fisherManLoc){

		int sharkSize = 0;

		for(int row = 1; row <= R; row++){

			if(graph[row][fisherManLoc] == 0) continue;

			sharkSize = sharkArray[graph[row][fisherManLoc]].size;
			sharkArray[graph[row][fisherManLoc]] = null;
			graph[row][fisherManLoc] = 0;

			break;
		}



		return sharkSize;
	}

	//상어가 격자판을 벗어났는지 체크하는 메서드.
	private static boolean check(int nextX, int nextY){
		return nextX >= 1 && nextX <= R &&
			nextY >= 1 && nextY <= C;

	}

	//상어 이동하는 메서드.
	private static void moveShark(){

		//모든 상어들이 이동을 해야 함.
		for (Shark shark : sharkArray){

			//잡아 먹혀서 없는 상어면 패스.
			if(shark == null) continue;

			//그래프에서 상어표시를 제거하고 시작
			graph[shark.x][shark.y] = 0;

			for (int i = 0; i < shark.speed; i++){

				int nextX = shark.x + dx[shark.dir];
				int nextY = shark.y + dy[shark.dir];


				//이동이 불가능한 위치라면 방향을 바꾸고 다시 계산해야 함.
				if (!check(nextX, nextY)){
					shark.changeDir();
					nextX = shark.x + dx[shark.dir];
					nextY = shark.y + dy[shark.dir];
				}

				//상어 위치 업데이트.
				shark.x = nextX;
				shark.y = nextY;
			}
		}
	}

	//상어 배치 - 상어 배치시에 충돌이 있으면, 큰 상어만 남기고 나머지는 삭제함.
	private static void settingShark(){

		for (Shark currentShark : sharkArray){

			if(currentShark == null) continue;

			if (graph[currentShark.x][currentShark.y] == 0){
				graph[currentShark.x][currentShark.y] = currentShark.num;
			}
			//이미 상어가 있다면 사이즈가 큰 것만 남겨두고 삭제.
			else{
				//해당 위치에 있는 상어 크기가 더 크다면,현재 상어를 삭제.(두 상어가 같은 크기인 경우는 없음.)
				if (currentShark.size <= sharkArray[graph[currentShark.x][currentShark.y]].size){
					sharkArray[currentShark.num] = null;
				}
				//현재 상어가 더 크면 현재 상어로 업데이트.
				else {
					sharkArray[graph[currentShark.x][currentShark.y]] = null;
					graph[currentShark.x][currentShark.y] = currentShark.num;
				}
			}
		}

	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new int[R + 1][C + 1];
		sharkArray = new Shark[M + 1];

		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());

			Shark shark = new Shark(
				i, //상어번호
				Integer.parseInt(st.nextToken()),//행
				Integer.parseInt(st.nextToken()),//열
				Integer.parseInt(st.nextToken()), //속력
				dirChange(Integer.parseInt(st.nextToken())), //방향
				Integer.parseInt(st.nextToken())  // 크기.
			);

			sharkArray[i] = shark;
			graph[shark.x][shark.y] = i;
		}

		int totalSharkSize = 0;

		for (int fisherManLoc = 1; fisherManLoc <=C; fisherManLoc++){

			/*1. 상어 잡기.*/
			totalSharkSize += catchShark(fisherManLoc);

			/*2. 상어 이동*/
			moveShark();//상어 이동

			settingShark();//상어 배치

		}

		System.out.println(totalSharkSize);
	}
}
