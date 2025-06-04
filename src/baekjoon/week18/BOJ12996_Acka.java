package baekjoon.week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 3명의 가수가 앨범을 만들수 있는 경우의 수는,
 * {1,1,1}
 * {0,1,1}, {1,0,1}, {1,1,0}
 * {0,0,1}, {0,1,0}, {1,0,0}
 *  => 총 7가지 경우의 수가 나온다.
 *  S번째 곡을 만드는 경우의 수는 S - 1번째에서 나올 수 있는 경우의 수이다.
 *  여기서 중요한 것은 s-1번째의 경우의 수를 구할때, 가수마다 부를 수 있는 곡의 제한이 있기 때문에 이 정보를 확인해야 한다.
 *
 *  참고함 : https://hwk99.tistory.com/186
 */

public class BOJ12996_Acka {

	private final static int MOD = 1_000_000_007;

	//재귀를 돌면서 배열 채우기.
	private static int solve(int[][][][] dp , int S, int a, int b, int c){

		//곡이 음수거나, 각 사람이 부를 수 있는 곡의 수가 -1가 되면 더 진행할 수 없음.
		if (S < 0 || a < 0 || b < 0 || c < 0) return 0;

		//곡이 0이면 초기 값으로, 각 사람의 값이 0,0,0이면 1을 반환함 => 초기 값. 나머지는 0을 반환함.
		if(S == 0){
			return (a == 0 && b == 0 && c == 0) ? 1 : 0;
		}

		//-1이 아닌 값이 있으면 이미 탐색한 것이므로 그대로 반환함.
		if (dp[S][a][b][c] != -1) return dp[S][a][b][c];

		int sum  = 0;//현재 상태의 경우의 수를 구함.

		//나올수 있는 7가지 경우의 수를 구하기 위하 재귀 호출.
		sum = (sum + solve(dp, S - 1, a - 1, b - 1, c - 1)) % MOD; //모든 음악가가 참여하는 경우.
		sum = (sum +solve(dp, S - 1, a, b - 1, c - 1)) % MOD; // b,c가 참여하는 경우
		sum = (sum +solve(dp, S - 1, a - 1, b, c - 1)) % MOD; // a,c 가 참여하는 경우.
		sum = (sum +solve(dp, S - 1, a - 1, b - 1, c)) % MOD; // a,b 가 참여하는 경우
		sum = (sum +solve(dp, S - 1, a - 1, b, c)) % MOD; // a가 참여하는 경우.
		sum = (sum +solve(dp, S - 1, a, b - 1, c)) % MOD; // b가 참여하는 경우
		sum = (sum +solve(dp, S - 1, a, b, c - 1)) % MOD; // c가 참여하는 경우.

		//현재 상태에 저장 및 반환.
		return dp[S][a][b][c] = sum;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int S = Integer.parseInt(st.nextToken());
		int a = Integer.parseInt(st.nextToken()); //dotorya
		int b = Integer.parseInt(st.nextToken()); //kesakiyo
		int c = Integer.parseInt(st.nextToken()); //hongjun7

		int[][][][] dp = new int[S + 1][a + 1][b + 1][c + 1];


		//초기 값은 -1로
		for (int i = 0; i <= S; i++){
			for (int j = 0; j <= a; j++){
				for (int k = 0; k <= b; k++){
					Arrays.fill(dp[i][j][k], -1);
				}
			}
		}


		solve(dp,S, a,b,c);

		System.out.println(dp[S][a][b][c]);
	}
}
