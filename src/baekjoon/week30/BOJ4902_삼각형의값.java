package baekjoon.week30;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 누적합.
 * 삼각형을 배열로 표현하고, 삼각형의 합을 구할때는 누적합을 이용한다.
 * 정방향 삼각형과 역방향 삼각형을 나눠 구하면 된다.
 *
 * 삼각형을 구할때는 꼭지점을 찾고, 해당 꼭지점으로 부터 삼각형을 구성하면 되는데,
 * 삼각형을 그려보면, 각 층은 작은 삼각형 홀수개가 만들어지고, 이중 홀수번을 선택하면 정방향을 만들 수 있다.
 */
public class BOJ4902_삼각형의값 {

	private static int[][] triArray; //삼각형 저장용
	private static int[][] sumArray; //누적합 용.


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		StringBuilder result = new StringBuilder();
		int testCase = 1;



		while(true){

			st = new StringTokenizer(br.readLine());

			int n = Integer.parseInt(st.nextToken());

			if(n == 0) break;

			//누적합 계산 편하게 인덱스는 1부터 사용.
			triArray = new int[n + 1][2 * n];
			sumArray = new int[n + 1][2 * n];

			//누적합 및 삼각형 정보 구성.
			for(int i = 1; i <= n; i++){
				for(int j = 1; j <= 2 * i- 1; j++){
					triArray[i][j] = Integer.parseInt(st.nextToken());
					sumArray[i][j] += triArray[i][j] + sumArray[i][j - 1];
				}
			}

			int maxValue = Integer.MIN_VALUE;

			//정삼각형 윗꼭지를 기준으로 전부 구해봄.
			for(int i = 1; i <= n; i++){
				for(int j = 1; j <= 2 * i - 1; j += 2){

					int tempTriSum = 0;
					//i,j를 꼭지점으로 한다면, 최대 정삼각형의 크기는 n - i임
					for(int k = 0; k <= n - i; k++){
						tempTriSum += sumArray[i + k][j + (2 * k)] - sumArray[i + k][j - 1];
						maxValue = Math.max(maxValue, tempTriSum);
					}
				}
			}

			//역삼각형 윗꼭지를 기준으로 전부 구해봄.
			for(int i = n; i >= 1; i--){
				for(int j = 2; j <= 2 * i - 1; j += 2){

					int tempTriSum = 0;

					//i,j를 꼭지점으로 한다면 최대 역삼각형의 크기는 min(j / 2, i - j/2)
					for(int k = 0; k < Math.min(j / 2, i - (j / 2)); k++){
						tempTriSum += sumArray[i - k][j] - sumArray[i - k][j - (2 * k) - 1];
						maxValue = Math.max(maxValue, tempTriSum);
					}
				}
			}

			result.append(testCase).append(". ").append(maxValue).append("\n");
			testCase++;
		}

		System.out.println(result);
	}
}
