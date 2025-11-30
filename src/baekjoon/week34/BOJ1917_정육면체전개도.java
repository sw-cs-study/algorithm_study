package baekjoon.week34;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현
 *
 * dfs를 이용해서 주사위를 굴려보고, 수가 중복되면 잘못된것.
 */
public class BOJ1917_정육면체전개도 {
	//위, 오, 아래, 왼
	private final static int[] dx = {-1, 0, 1, 0};//4방향
	private final static int[] dy = {0, 1, 0, -1};


	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}


	private static boolean isDice;
	//다이스 구분 - 앞: 1, 뒤: 6, 왼: 2, 위: 3, 오: 4, 밑: 5
	private static boolean[] diceCheck;
	private static int[] diceType;

	//다이스 전개도.
	private static int[][] diceMap;

	//격자 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < 6 &&
			nextY >= 0 && nextY < 6 &&
			diceMap[nextX][nextY] == 1;
	}

	//dfs로 탐색
	private static void dfs(Node currentNode){


		for(int i = 0; i < 4; i++){
			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			if(!check(nextX, nextY)) continue;


			//이미 탐색한 수가 나오면 종료
			if(diceCheck[diceType[i]]) {
				isDice = false;
				return;
			};

			diceMap[nextX][nextY] = 0;
			diceCheck[diceType[i]] = true;
			changeDice(i);
			dfs(new Node(nextX, nextY));
			changeDice((i + 2) % 4);

		}

	}

	//다이스 숫자 위치 변경. 위0, 오1, 밑2, 왼3, 앞4, 뒤5
	private static void changeDice(int dir){

		switch(dir){
			case 0: // 위로
				diceType = new int[] {diceType[5], diceType[1], diceType[4], diceType[3], diceType[0], diceType[2]};
				break;
			case 1: // 오른쪽
				diceType = new int[]{diceType[0], diceType[5], diceType[2], diceType[4], diceType[1], diceType[3]};
				break;
			case 2: // 아래
				diceType = new int[]{diceType[4], diceType[1], diceType[5], diceType[3], diceType[2], diceType[0]};
				break;
			case 3: // 왼쪽
				diceType = new int[]{diceType[0], diceType[4], diceType[2], diceType[5], diceType[3], diceType[1]};
				break;
		}
	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		StringBuilder result = new StringBuilder();
		for(int testcase = 0; testcase < 3; testcase++){

			diceMap = new int[6][6];
			isDice = true;


			//초기 다이스 형태.
			diceType = new int[]{2,3,4,5,1,6}; //위 : 0, 오1, 밑2, 왼3, 앞4, 뒤5
			int startX = -1;
			int startY = -1;
			for(int i = 0; i < 6; i++){
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < 6; j++){

					diceMap[i][j] = Integer.parseInt(st.nextToken());

					if(diceMap[i][j] == 1 && startX == -1 && startY == -1){
						startX = i;
						startY = j;
					}
				}
			}

			diceMap[startX][startY] = 0;
			diceCheck = new boolean[7];
			diceCheck[1] = true;
			dfs(new Node(startX,startY));

			if(isDice){
				result.append("yes");
			}
			else{
				result.append("no");
			}
			result.append("\n");

		}

		System.out.println(result.toString());

	}
}
