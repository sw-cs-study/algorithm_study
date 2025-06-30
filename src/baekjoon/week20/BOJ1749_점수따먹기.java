package baekjoon.week20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 2차원 누적합 + 브루트 포스
 *
 * 각 칸에는 해당칸까지 모든 값의 합이 저장된다.
 *  1 2 3
 *  4 5 6
 *  7 8 9
 *
 *  이러한 행렬이 있다면, (1,2) 위치에는 1+2+3+4+5+6 한 값이 저장된다.
 *  해당 값을 구하기 위해서는 이전에 누적된 값을 확인해야 한다.
 *
 *  (2,2)의 값을 구하고 싶으면 우선 (2,2)위치의 값에 (2,1)에 누적된 값 + (1,2)에 누적된 값을 더해줘야,
 *  (2,2)를 포함한 모든 범위를 커버할수 있다.
 *  여기에 중복되는 값을 빼줘야 하는데, (2,1)과 (1,2)에 누적된 값은 (1,1)에 저장된 값의 공간만큼 중복이 된다.
 *  따라서 누적값을 구하기 위해서는 (1,1)도 뺴주어야 한다.
 *
 *  이렇게 모든 칸에 대한 누적합이 구해지면, 나올 수 있는 모든 경우에 대해서 반복문 돌면서 구해보고, 그중 최대 값을 구하면 된다.
 */
public class BOJ1749_점수따먹기 {

	private static int N;
	private static int M;

	private static int[][] numArray;
	private static int[][] sumArray;

	//누적합 구하기.
	private static void initSum(){

		for (int i = 1; i <= N; i++){
			for(int j = 1; j <= M; j++){
				sumArray[i][j] = numArray[i][j] + sumArray[i-1][j] + sumArray[i][j - 1] - sumArray[i - 1][j - 1];
			}
		}
	}

	//모든 경우 보면서 최대 값 구하기.
	private static int getMaxValue(){

		int result = (-1) * 10000 * 200 * 200;

		for (int i = N; i >= 1; i--){
			for (int j = M; j >= 1; j--){

				for(int rowlen = j; rowlen >= 1; rowlen--){ //N(N + 1)/2
					for(int collen = i; collen >= 1; collen--){ // M(M + 1)/2

						result = Math.max(
							result,
							sumArray[i][j] - sumArray[i - collen][j] - sumArray[i][j - rowlen] + sumArray[i - collen][j - rowlen]
						);
					}
				}

			}
		}



		return result;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		numArray = new int[N + 1][M + 1];
		sumArray = new int[N + 1][M + 1];

		for (int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= M; j++){
				numArray[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		initSum();
		System.out.println(getMaxValue());


	}
}
