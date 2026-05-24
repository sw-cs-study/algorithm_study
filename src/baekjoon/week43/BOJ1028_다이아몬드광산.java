package baekjoon.week43;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어 누적합.
 */
public class BOJ1028_다이아몬드광산 {

	//왼쪽 아래, 오른쪽 아래, 왼쪽위, 오른쪽 위.
	private final static int[] dx = {1, 1,  -1, -1};
	private final static int[] dy = {-1, 1, -1, 1};

	private static int R;
	private static int C;
	private static char[][] maps;
	private static int[][][] dp;

	//방문가능한지 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < R &&
			nextY >= 0 && nextY < C;

	}

	//현재 위치에서 입력 받은 방향으로 갈 수 있는 최대길이 구하기.
	private static int getMaxSize(int currentX, int currentY, int dir){

		int size = 0;
		int count = 1;

		while(true){
			int nextX = currentX + dx[dir] * count;
			int nextY = currentY + dy[dir] * count;


			//이동이 불가능하거나, 0이면 종료.
			if(!check(nextX, nextY) || maps[nextX][nextY] == '0') break;

			size++;
			count++;
		}

		return size;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		maps = new char[R][C];
		dp = new int[R][C][4];

		for(int i = 0; i < R; i++){
			maps[i] = br.readLine().toCharArray();
		}

		//길이가 1이면 0,0의 값이 답임.
		if(R == 1 && C == 1){
			System.out.println(maps[0][0]);
			return;
		}

		//1이 하나라도 있는지 탐색.
		boolean flag = false;
		loop:
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++) {
				if(maps[i][j] == '0') continue;

				flag = true;
				break loop;

			}
		}
		int maxSize = 0; //나올 수 있는 다이아 한변의 최대 길이.
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){

				if(maps[i][j] != '1') continue;

				//현재위치에서 4방향으로 갈수 있는 최대 값 계산하기.
				for(int dir = 0; dir < 4; dir++){
					dp[i][j][dir] = getMaxSize(i, j ,dir);
				}

				//현재 위치에서 위 대각선으로 갈수 있는 두개의 길이가 이전에 구한 최대 길이보다 길면 진행.
				//최대 길이의 다이아를 구하는 것이므로, maxSize보다 작으면 볼 필요 없음.
				if(dp[i][j][2] <= maxSize || dp[i][j][3] <= maxSize) continue;

				//한변의 길이는 두개중에 짧은 걸 선택해야 완전한 다이아가 됨.
				int len = Math.min(dp[i][j][2], dp[i][j][3]);

				//가능한 한변의 길이에서 줄여가며 체크
				//현재 위치에서 한변의 길이를 이용해서 반대쪽 꼭지점을 찾고, 위 대각선의 길이들을 체크함
				for(int tmp = len; tmp > maxSize; tmp--){

					//반대쪽 꼭지점이 없는 칸이면 패스.
					if(i - (2 * tmp) < 0) continue;

					//아래 대각선 둘다 현재 지정한 길이(tmp)와 같거나 그보다 크면 다이아 가능하다는 뜻.
					//다이아가 가능하면 max갱신 후, 현재 반복문 종료(최대 길이구하는거라 줄여서 볼 필요가 없음)
					if(maps[i - (2 * tmp)][j] == '0' || dp[i - (2 * tmp)][j][0] < tmp || dp[i - (2 * tmp)][j][1] < tmp) continue;

					maxSize = tmp;
					break;
				}

			}

		}

		if(maxSize > 0){
			System.out.println(maxSize + 1);
		}else{
			System.out.println(flag ? 1 : 0);
		}

	}
}
