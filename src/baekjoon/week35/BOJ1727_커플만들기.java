package baekjoon.week35;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디, dp
 * 우선 남자 여자 배열을 정렬해서 매칭한다.
 * 두 수가 같다면 정렬하고 각 인덱스 끼리 매핑하면 최소가 된다,
 * 하지만 두 수가 다르다면 고민을 해야 한다.
 * dp배열을 만들어서 어떤경우가 좋은지 고려해야 한다.
 * dp[n][m] 형식으로 만들면 남자가 n, 여자가 m일때 나올수 있는 최적의 값이 저장되는 것이다.
 * 두 수가 같다면 고민할 것 없지만, 다른 경우는 두가지로 나눠야 한다.
 * 예를 들면 여자 수가 더 많다고 했을때, 추가 된 여자를 선택하는 경우와 선택하지 않는 경우로 나눠서 풀이한다.
 */
public class BOJ1727_커플만들기 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] manArray = new int[N + 1];
		int[] womanArray = new int[M + 1];

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			manArray[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		for (int j = 1; j <= M; j++) {
			womanArray[j] = Integer.parseInt(st.nextToken());

		}

		//두 배열 정렬
		Arrays.sort(manArray, 1, manArray.length);
		Arrays.sort(womanArray, 1, womanArray.length);



		int[][] dp = new int[N + 1][M + 1];


		for(int i = 1; i <= N; i++){
			for(int j = 1; j <= M; j++){

				//두 사람의 수가 같을때 -> 각 인덱스별 차이의 합이 최소임.
				if(i == j){
					dp[i][j] = dp[i - 1][j - 1] + Math.abs(manArray[i] - womanArray[j]);
				}

				//남자가 더 많을떄 - 추가된 인원을 선택하는 경우와 하지 않는 경우 중 최소값으로 업데이트
				//추가된 인원을 선택한다 했을때, 최소가 되게 하려면 여자쪽의 마지막값과 매핑해야 함.
				else if(i > j){
					dp[i][j] = Math.min(
						dp[i - 1][j], // 남자쪽인원 선택안하는 경우
						dp[i - 1][j - 1] + Math.abs(manArray[i] - womanArray[j]) //남자쪽 신규 인원을 선택해서 계산하는 경우,
					);
				}

				//여자가 더 많을떄 - 추가된 인원을 선택하는 경우와 하지 않는 경우 중 최소값으로 업데이트
				else{
					dp[i][j] = Math.min(
						dp[i][j - 1],
						dp[i - 1][j - 1] + Math.abs(manArray[i] - womanArray[j])
					);
				}

			}
		}

		System.out.println(dp[N][M]);

	}

}