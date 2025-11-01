package baekjoon.week6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP + 누적합.
 */

public class BOJ32519_대회전략 {

	// private final static int M_INF = Integer.MIN_VALUE;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());

		//문제 정보 입력 받기.
		int N = Integer.parseInt(st.nextToken()); //서브 태스크 수.
		int T = Integer.parseInt(st.nextToken()); // 제한시간.

		int totalTask = N * 3;

		//각 문제는 독립적일 수 없고, 이전에 문제를 풀어야만 해당 문제를 풀 수 있음.
		//문제별 배점 - 누적합 구해두기.
		int[][] scores = new int[3][N + 1];

		//문제별 점수. - 누적합 구해두기.
		int[][] times = new int[3][N + 1];

		for (int i = 0; i < 3; i++){
			st = new StringTokenizer(br.readLine());
			for (int j= 1; j <= N; j++){
				scores[i][j] = scores[i][j - 1] + Integer.parseInt(st.nextToken());
				// scores[N * i] = scores[totalTask - 1] + Integer.parseInt(st.nextToken());

			}
		}

		//문제별 걸리는 시간 누적합 구해두기.
		for (int i = 0; i < 3; i++){
			st = new StringTokenizer(br.readLine());
			for (int j= 1; j <= N; j++){

				times[i][j] = times[i][j - 1] + Integer.parseInt(st.nextToken());
			}
		}


		long result = 0;

		//투포인터 사용
		for(int firstPoint = 0; firstPoint <= N; firstPoint++){

			int secondPoint = 0;
			int thirdPoint = N;

			//음수면 패스
			if(scores[0][firstPoint] < 0) continue;

			while(secondPoint <= N && thirdPoint >= 0){

				if(firstPoint == 3 && secondPoint == 5 && thirdPoint == 1){

					System.out.println("time : " + times[0][firstPoint] + " " +  times[1][secondPoint] + " " + times[2][thirdPoint]);
					System.out.println("score : " + scores[0][firstPoint] + " " + scores[1][secondPoint] + " " + scores[2][thirdPoint]);
					System.out.println("test1111");
				}

				//총 시간이 T를 넘지 않아야 함.
				//시간이 T를 넘으면 thirdPoint를 감소시킴.
				if(times[0][firstPoint] + times[1][secondPoint] + times[2][thirdPoint] > T){
					thirdPoint--;
				}

				//시간이 T보다 작거나 같으면  secondPoint를 늘려봄
				else{

					result = Math.max(
						result,
						scores[0][firstPoint] + scores[1][secondPoint] + scores[2][thirdPoint]
						);

					secondPoint++;
				}
			}
		}


		System.out.println(result);
	}
}
