package baekjoon.week26;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 완탐
 *
 * 구해야 하는 것은 지우가 모든 경기에서 손동작을 다르게 냈을때, 승수를 채울수 있는지 이다.
 * 모든 경우를 다 확인해야 한다.
 * 경기 순서는 정해져 있고, 각 경기마다 지우가 이기는 경우, 지는 경우 두가지 케이스를 나눠서 재귀호출을 하고, 3명중 정해진 승수를 최초로 달성하는 사람이 있는지 확인한다.
 * 경기 순서의 경우에는, ABC라고 했을때, A-B가 먼저 경기를 시작하고, 다음 경기부터는 이전경기에서 이긴사람과, 이전경기에 참여하지 않았던 사람이 참여하는 방식이다.
 * 20라운드의 경기마다 어떤 것을 내는지 정보가 있기 떄문에, 재귀 호출시에는 현재 몇번째 경기를 치루고 있는지 확인이 필요하다.
 *
 * (주의)
 * 주의할점은 문제에서 주어지는 라운드별 내는 손동작이,
 * 해당 라운드에서 낸다의 개념이 아닌, 앞으로20번의 경기를 할때, 내는 손동작이라는 것이다.
 * 즉, 경희가 1,3,5라운드에서 경기를 했다고 하면, 1,3,5번째 값을 가지고 하는것이 아니라,
 * 1,2,3번에 해당하는 값을 각각 봐야 하는 것이다.
 * 각 라운드 별이 아닌, 경기를 하게 되면 내는 손동작을 순서대로 나열한 것으로 봐야 한다.
 */

public class BOJ16986_인싸들의가위바위보 {

	private static int N;//손동작 수
	private static int K;//승수
	private static int[][] relation;//손동작간의 관계

	private static int[][] statusInfo;//경희(0번로우), 민호(1번 로우)의 20라운드 간 정보

	private static int[] winInfo;//3명의 승수 정보.
	private static int[] roundInfo;//3명이 각각 현재 어떤것을 내야 하는지 라운드 정보.

	private static boolean[] visited;//지우가 냈던 손동작 들

	//승수 채웠는지 확인
	private static int winCheck(){

		for(int i = 0; i < 3; i++){

			if(winInfo[i] == K) return i;
		}

		return -1;
	}

	//참여 안한 사람 구하기.
	private static int getNextPlayer(int win, int lose){
		return 3 - win - lose;
	}

	// 두 플레이어 중 승자 구하기.
	private static int getWinner(int player1, int type1, int player2, int type2){

		if(relation[type1 - 1][type2 - 1] == 0) return player2;
		else if(relation[type1 - 1][type2 - 1] == 2) return player1;
		//비긴 경우에는 두 플레이어 중 큰쪽(플레이어는 0,1,2로 구분)
		else return Math.max(player1, player2);
	}


	//재귀 호출 - player1, type은 이번에 낼것을 말함(지우는 타입값이 -1)
	private static boolean recursive(int round, int player1, int player2){

		//승수 계산해서 다 채운 사람이 있으면 확인 -
		int finalWinner = winCheck();
		if(finalWinner != -1) return finalWinner == 0;

		//20라운드를 넘었으면 종료
		if(round >= 20) return false;

		//계산하기 편하도록 지우는 무조건 player1 변수에 배치.
		if(player2 == 0){
			int temp = player1;
			player1 = player2;
			player2 = temp;
		}

		if(player1 == 0){
			//지우가 낼수 있는 가지수
			for(int i = 1; i <= N; i++){

				//이전에 냈던 것이라면 패스.
				if(visited[i - 1]) continue;

				//상대방과 다르다면 두 플레이어 중에 승리한 사람 구하기.
				int winner = getWinner(player1, i, player2, statusInfo[player2 - 1][roundInfo[player2]]);
				winInfo[winner]++;
				visited[i - 1] = true;
				roundInfo[player1]++;
				roundInfo[player2]++;
				//다음 라운드 탐색하도록 함 - 반환값이 true이면 지우가 이기는 경우가 존재한다는 뜻으면 종료 처리.
				if(recursive(round + 1,winner, getNextPlayer(player1, player2))) return true;
				visited[i - 1] = false;
				winInfo[winner]--;
				roundInfo[player1]--;
				roundInfo[player2]--;
			}
		}
		//플레이어 중에 지우가 없는 경우 - 그냥 해당 라운드에 낸 값 비교 하면 됨.
		else{

			int winner = getWinner(player1, statusInfo[player1 - 1][roundInfo[player1]], player2, statusInfo[player2 - 1][roundInfo[player2]]);
			winInfo[winner]++;
			roundInfo[player1]++;
			roundInfo[player2]++;
			if(recursive(round + 1, winner, getNextPlayer(player1, player2))) return true;
			winInfo[winner]--;
			roundInfo[player1]--;
			roundInfo[player2]--;
		}

		return false;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		relation = new int[N][N];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			for(int j = 0; j < N; j++){
				relation[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		statusInfo = new int[2][20];
		winInfo = new int[3];

		for(int i = 0; i < 2; i++){
			st = new StringTokenizer(br.readLine());

			for(int j = 0; j < 20; j++){
				statusInfo[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		visited = new boolean[N];
		roundInfo = new int[3];

		//시작경기는 무조건 0,1 둘이 함.
		boolean result = recursive(0, 0, 1);

		System.out.println(result ? 1 : 0);

	}
}
