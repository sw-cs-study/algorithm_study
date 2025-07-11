package baekjoon.week21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 	아이디어
 * 	누적합 + dp
 *
 * 	구간을 나누면서 중복되는 부분이 있고, 구간의 합을 구해야 하기 때문에 미리 누적합을 사용해서 계산한다.
 */

public class BOJ2228_구간나누기 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[] sumArray = new int[N + 1];

		/**
		 * 행은 구간의 수, 열은 구간의 끝의 인덱스
		 * M개의 구간의 합을 구하기 위해서는 M-1개의 구간의 합이 필요하다. 즉 메모이제이션으로 이전 구간의 수의 합부터 구하면서 와야한다.
		 */
		int[][] dp = new int[M + 1][N + 1];

		//구간 합을 빠르게 구하기 위해서 누적합을 구해둠.
		for (int i = 1; i <= N; i++){
			int inputValue = Integer.parseInt(br.readLine());
			sumArray[i] = sumArray[i - 1] + inputValue;
		}

		//최대값을 구하기 위해서, 기존 디피 배열을 가장 작은수로 초기화.
		for(int i = 1; i <= M; i++){
			Arrays.fill(dp[i], (-1) * 32767 * 101);
		}

		//초기값 - 구간이 1개이고, 1번째 인덱스라면 수가 1개이므로 나올 수 있는 경우가 단 1개임.
		dp[1][1] = sumArray[1];

		for (int i = 1; i <= M; i++){
			for (int j = 2; j <= N; j++){

				dp[i][j] = dp[i][j - 1];

				int minK = i == 1 ? -1 : 0;

				//인접하면 안되기 때문에 2칸 띄우고 탐색.
				for (int k = j - 2; k >= minK; k--){

					if (k < 0) dp[i][j] = Math.max(dp[i][j], sumArray[j]);
					else dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + (sumArray[j] - sumArray[k + 1]));
				}
			}
		}

		int result = Integer.MIN_VALUE;

		for(int i = 1; i <= N; i++){
			result = Math.max(result, dp[M][i]);
		}

		System.out.println(result);
	}
}
