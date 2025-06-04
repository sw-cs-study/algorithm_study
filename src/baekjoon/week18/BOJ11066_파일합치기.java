package baekjoon.week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 해당 문제는 결국 모든 경우를 다 해봐야 한다.
 * 모든 경우를 다 확인할때, 일부 경우는 이전에 계산했던 경우를 다시 계산할 필요가 없다.
 * 그러기 떄문에 dp를 이용해서 연산 수를 줄여본다.
 * 예를 들면 c1,c2,c3,c4,c5가 있을떄, c2와 (c3,c4,c5)를 계산하고자 할때, 이전에 계산한 ,c3,c4,c5에서 나온 값을 저장해두면,
 * 다시 계산할 필요가 없다는 거다.
 *
 * 문제해결은 결국 1번째부터 마지막번쨰 파일까지 합쳤을때 최소비용을 구하는 문제이고,
 * 합치는 방법은 여러가지이다.
* 1~5번을 합친다고 했을때, (1,2) + (3,4,5) 일수도 있고, (1,2,3) + (4,5)가 이득일 수도 있다.
 * 즉, start부터 end까지 계산한다고 했을떄, 중간에 어디까지 먼저 계산하는지에 따라 달라진다는 것이다.
 * 이를 2차원 배열에 표기해서 행은 start, 열은 end로 표기해서 start - end 까지 계산한 최소값을 저장하는 식으로 구해나간다.
 * 이때 mid 값도 하나씩 증가시켜나가면서 해당 칸들을 채워나가면 된다.
 */
public class BOJ11066_파일합치기 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		StringBuilder result = new StringBuilder();
		for(int testCase = 0; testCase < T; testCase++){

			int K = Integer.parseInt(br.readLine());
			int[] numArray = new int[K + 1]; //누적합으로 만들어줌 => start-end 비용 계산시에 전체를 더한 값이 필요함.
			int[][] dp = new int[K + 1][K + 1];

			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= K; i++) {
				numArray[i] = numArray[i - 1] + Integer.parseInt(st.nextToken());
			}


			//dp배열 채우기
			//합칠떄 파일의 수가 1개 차이나는 것 부터 처리해야 함.
			for(int len = 1; len <= K; len++){
				for(int start = 1; start <= K - len; start++){

					int end = start + len;

					//최소값을 구하기 위해 최대 값을 채워넣음
					dp[start][end] = Integer.MAX_VALUE;

					//분리지점을 구함.
					for (int mid = start; mid < end; mid++){

						//기존에 저장된 값과, 새로 계산한 값중 더 작은 값으로 업데이트.
						dp[start][end] = Math.min(
							dp[start][end],
							dp[start][mid] + dp[mid + 1][end] + (numArray[end] - numArray[start - 1])
						);
					}
				}
			}
			result.append(dp[1][K]).append("\n");

		}

		System.out.println(result);

	}
}
