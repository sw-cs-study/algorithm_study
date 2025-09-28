package baekjoon.week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * DP
 * dp[i][j] => i번째 열의 높이가 j일때 i번째 열까지의 가능한 제단 경우의 수
 */

public class BOJ5626_제단 {

	private final static int MOD = 1000000007;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		int[][] dp = new int[N][(N / 2) + 2]; // 제단은 양쪽 끝을 제외하고 가운데로 갈수록 높아짐, 최대 높이는 가운데 지점에서 나타남(N/2), +2는 인덱스 초과 방지를 위한 안전마진
		int[] inputValue = new int[N];

		//각 열의 높이를 모를때,  가능한 높이는 min(i , (N - 1) - i)
		//높이를 올릴때, 양쪽끝은 제외기 때문에, i위치의 높이가 h가 되려면, 왼쪽과 오른쪽으로 최소 h만큼의 거리가 있어야 함.

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			inputValue[i] = Integer.parseInt(st.nextToken());

			//가능한 높이 공식을 이용하여, 불가능한 경우를 거름
			if(inputValue[i] > Math.min(i, (N - 1) - i)){
				System.out.println(0);
				return;
			}
		}


		//dp로 값 구하기
		//dp[i][j] => i번째 열의 높이가 j일때 i번째 열까지의 가능한 제단 경우의 수
		dp[0][0] = 1; //초기값
		for(int i = 1; i < N; i++){

			//높이를 모르는 경우(입력을 -1로 준 경우) -> 0 ~ min(i, (N - 1) - i) 범위의 값이 가능함.
			if(inputValue[i] == -1){

				//가능한 높이.
				int height = Math.min(i, (N - 1) - i);

				for(int j = 0; j <= height; j++){

					//인접 높이와 차이가 1이하로 나야 함.
					for(int k = -1; k <= 1; k++){
						int adjH = j + k;

						if(adjH < 0) continue;

						dp[i][j] = (dp[i][j] + dp[i - 1][adjH]) % MOD;
					}
				}
			}
			//높이를 아는 경우(입력으로 준 경우) - 이전위치에서 올 수 있는 높이들을 찾아야 함 -> 올 수 있는 높이는 최대 1차이까지 날 수 있음.
			else{
				for(int k = -1; k <= 1; k++){
					int adjH = inputValue[i] + k; //인접 높이 구하기.

					//인접높이가 -1인 경우는 볼 필요 없음
					if (adjH < 0) continue;
					dp[i][inputValue[i]] = (dp[i][inputValue[i]] + dp[i - 1][adjH]) % MOD;
				}
			}

		}

		System.out.println(dp[N - 1][0]);
	}
}
