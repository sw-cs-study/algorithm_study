package programmers.week46;

import java.util.Arrays;

/**
 * 아이디어
 * 백트래킹
 * 한쪽에 대해서 훔칠수 있는 모든 경우를 계산하고, 나머지 한쪽이 훔치지 않은 것을 훔친다.
 * 즉 A를 기준으로 부분집합을 통해서 물건을 훔치는 경우의 수를 계산
 * 단 A는 n미만이어야 하며, 남은 것을 B가 훔칠때도 B의 흔적이 m을 넘어서면 안됨.
 *
 * (추가)
 * info길이가 40이라, 부분집합 불가 dp로 해결해야 함.
 */

public class Prog_완전범죄 {



	public int solution(int[][] info, int n, int m) {

		int[][] dp = new int[info.length + 1][m]; //배열 안에는 a의 최소 흔적값을 저장.

		//최소값 저장을 위해 최대값 저장.

		for(int i = 0; i <= info.length; i++){
			Arrays.fill(dp[i], 200);
		}

		//초기 값
		dp[0][0] = 0;
		for(int i = 1; i <= info.length; i++){

			int valueA = info[i - 1][0];
			int valueB = info[i - 1][1];

			//각 물건을 훔칠때.
			for(int j = 0; j < m; j++){

				//현재 물건을 A가 훔치는 경우 - b을 기준으로 했기 때문에 바로 이전까지 상품을 훔쳤을때 최소값에 A를 더함.
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + valueA);


				//현재 물건을 B가 훔치는 경우,
				//단 B가 훔칠때는 m을 넘지 않아야 함.
				if(j + valueB < m){
					dp[i][j + valueB] = dp[i - 1][j];
				}

			}
		}

		//dp마지막 행을 돌면서, A의 최소값을 반환
		int answer = Integer.MAX_VALUE;

		for(int i = 0; i < m; i++){

			answer = Math.min(answer, dp[info.length][i]);
		}


		return answer >= n ? -1: answer;
	}



	public static void main(String[] args){

		Prog_완전범죄 p = new Prog_완전범죄();

		int[][] info1 = {{1, 2}, {2, 3}, {2, 1}};
		int n1 = 4;
		int m1 = 4;
		System.out.println(p.solution(info1, n1, m1));

		int[][] info2 = {{1, 2}, {2, 3}, {2, 1}};
		int n2 = 1;
		int m2 = 7;
		System.out.println(p.solution(info2, n2, m2));

		int[][] info3 = {{3, 3}, {3, 3}};
		int n3 = 7;
		int m3 = 1;
		System.out.println(p.solution(info3, n3, m3));

		int[][] info4 = {{3, 3}, {3, 3}};
		int n4 = 6;
		int m4 = 1;
		System.out.println(p.solution(info4, n4, m4));

	}
}
