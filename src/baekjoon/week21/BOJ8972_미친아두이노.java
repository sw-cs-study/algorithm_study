package baekjoon.week21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현
 *
 * [최종적으로 구해야 할 부분]
 * 1. 중간에 죽지 않았다면, 종수의 아두이노 이동이 완료된 후의 보드판 상태
 * 2. 중간에 죽었다면, 몇번째에서 죽엇는지 출력.
 *
 * [구현해야 하는 부분]
 * 1. 종수이의 아두이노 움직임(제자리에 있는 것 까지 9방향)
 * 2. 미친 아두이노의 이동(맨해튼 거리를 이용.)
 * 3. 미친 아두이노가 같은 칸에 있을때 터지는 부분.
 * 4. 종수이 and 미친 아두이노가 움직였을때 격자판을 벗어나는지 확인하는 부분.
 * 5. 종수이의 아두이노가 미친 아두이노를 만났는지 여부.
 * 6. 미친 아두이노의 이동을 위한 맨해튼 거리 측정 기능.
 *
 *
 */
public class BOJ8972_미친아두이노 {

	//9방향, 인덱스 1부터 사용.
	private final static int[] dx = {-1, 1, 1, 1, 0, 0, 0, -1, -1, -1};
	private final static int[] dy = {-1, -1, 0, 1, -1, 0, 1, -1, 0, 1};

	//아두이노의 상태를 나타낼 객체
	private static class Arduino{
		int x, y;

		public Arduino(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//격자판 크기
	private static int R;
	private static int C;

	private static char[][] maps;//격자판.
	private static int[][] visited;

	private static Queue<Arduino> crazyArduinos;

	//종현이의 아두이노 - 미친 아두이도가 종현이 아두이노 위치쪽으로 이동하기 때문에.
	private static Arduino targetArduino;

	//아두이노가 격자판을 벗어났는지 확인하는 메서드
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < R &&
			nextY >= 0 && nextY < C;
	}

	//미친 아두이노 이동을 위한 맨해튼 거리 측정.
	private static int distance(int nextX, int nextY, int targetX, int targetY){
		return Math.abs(nextX - targetX) + Math.abs(nextY - targetY);
	}

	//종수의 아두이노가 움직임 - 종수의 위치는 하나이므로 maps에 표기할 필요는 없음.
	private static boolean targetMove(int command){

		int nextX = targetArduino.x + dx[command];
		int nextY = targetArduino.y + dy[command];

		//이동할 위치에 미친 아두이노가 있으면 종료.
		if (maps[nextX][nextY] == 'R') return false;

		targetArduino.x = nextX;
		targetArduino.y = nextY;

		//중간에 종료되지 않았으면 true 반환.
		return true;

	}

	//미친 아두이노가 움직이는 메서드 - return이 false 라면, 죽음, return이 true라면 계속 진행.
	//중요한 점은 모든 미친 아두이노가 동시에 움직여야 함.
	private static boolean crazyMove(){

		//방문처리.
		visited = new int[R][C];

		//미친 아두이노를 하나씩 돌면서 확인.
		while (!crazyArduinos.isEmpty()){

			Arduino crazyArduino = crazyArduinos.poll();

			//이동은 총 9방향으로 가능하고, 9방향중에 종수의 위치와 가장 가까운 위치를 선택.
			int dis = Integer.MAX_VALUE;
			int nextX = 0;
			int nextY = 0;
			for (int i = 1; i <= 9; i++){

				if (i == 5) continue;

				int tempNextX = crazyArduino.x + dx[i];
				int tempNextY = crazyArduino.y + dy[i];

				//격자판을 벗어났다면 실패
				if(!check(tempNextX, tempNextY)) continue;

				//기존에 저장된 거리보다 짧다면 해당 위치를 선택함.
				int nextDistance = distance(tempNextX, tempNextY, targetArduino.x, targetArduino.y);
				if (dis > nextDistance){
					nextX = tempNextX;
					nextY = tempNextY;
					dis = nextDistance;
				}
			}

			//이동할 위치를 골랐으면, 이동

			//1. 이동위치에 종수의 아두이노가 있다면, 종료.
			if (targetArduino.x == nextX && targetArduino.y == nextY) return false;

			maps[crazyArduino.x][crazyArduino.y] = '.';
			visited[nextX][nextY]++;
		}

		return true;
	}

	//미친 아두이도 배치
	private static void updateArduino(){

		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){

				if(visited[i][j] != 1) continue;

				maps[i][j] = 'R';
				crazyArduinos.add(new Arduino(i,j));
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		maps = new char[R][C];

		crazyArduinos = new ArrayDeque<>();

		for (int i = 0; i < R; i++){
			maps[i] = br.readLine().toCharArray();
			for (int j = 0; j < C; j++){

				char chr = maps[i][j];

				if (chr == 'I'){
					maps[i][j] = '.';
					targetArduino = new Arduino(i,j);
				}
				else if(chr == 'R'){
					crazyArduinos.add(new Arduino(i, j));
				}
			}
		}

		String commands = br.readLine();

		for (int i = 0; i < commands.length(); i++){

			int command = Character.getNumericValue(commands.charAt(i));


			if (!targetMove(command)){
				System.out.println("kraj " + (i + 1));
				return;
			}


			if (!crazyMove()){
				System.out.println("kraj " + (i + 1));
				return;
			}

			updateArduino();

		}


		StringBuilder result = new StringBuilder();

		maps[targetArduino.x][targetArduino.y] = 'I';
		for(int i = 0; i < R; i++){

			for (int j = 0; j < C; j++){
				result.append(maps[i][j]);
			}

			result.append("\n");
		}

		System.out.println(result);

	}
}
