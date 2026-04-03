package baekjoon.week41;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 누적합 + 완탐.
 */
public class BOJ33561_임스의땅따먹기 {

	private static int N;//땅 크기
	private static int K;//설계도의 개수.
	private static Integer[] bluePrint;//설계도 -> 내림차순 정렬 후, 누적합 계산.
	private static int[][] graph;//그래프 -> 땅 정보 표기 -> 누적합으로 처리.
	private static int[][] zeroGraph;//그래프 -> 가치 0인 개수 -> 누적합으로 처리.

	private static int maxValue;//최대 가치.


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		graph = new int[N + 1][N + 1];
		zeroGraph = new int[N + 1][N + 1];

		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());

			for(int j = 1; j <= N; j++){
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		K = Integer.parseInt(br.readLine());
		bluePrint = new Integer[K];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < K; i++){
			bluePrint[i] = Integer.parseInt(st.nextToken());
		}

		//정렬
		Arrays.sort(bluePrint, Collections.reverseOrder());

		//설계도 누적합
		for(int i = 1; i < K; i++){
			bluePrint[i] += bluePrint[i - 1];
		}

		// 땅 누적합 계산.
		for(int i = 1; i <= N; i++){
			for(int j = 1; j <= N; j++){
				//가치가 0인 것의 개수 누적.
				if(graph[i][j] == 0){
					zeroGraph[i][j] += zeroGraph[i - 1][j] + zeroGraph[i][j - 1] - zeroGraph[i - 1][j - 1] + 1;
				}
				else{
					zeroGraph[i][j] += zeroGraph[i - 1][j] + zeroGraph[i][j - 1] - zeroGraph[i - 1][j - 1];
				}

				graph[i][j] += graph[i - 1][j] + graph[i][j - 1] - graph[i - 1][j - 1];


			}
		}


		maxValue = 0;

		//정사각형 한변의 길이.
		for(int len = N; len >= 1; len--){
			for(int i = N; i >= len; i--){
				for(int j = N; j >= len; j--){

					int zeroCount = zeroGraph[i][j] - zeroGraph[i - len][j] - zeroGraph[i][j - len] + zeroGraph[i - len][j - len];

					//K보다 크면, 설계도를 다 써도 영역안에 0존재.
					if(zeroCount > K) continue;

					int bluePrintValue = zeroCount > 0 ? bluePrint[zeroCount - 1] : 0;

					maxValue = Math.max(
						maxValue,
						graph[i][j] - graph[i - len][j] - graph[i][j - len] + graph[i - len][j - len] + bluePrintValue
					);
				}
			}
		}

		System.out.println(maxValue);


	}
}
