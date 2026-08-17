package programmers.week50;

import java.util.Arrays;

/**
 * 아이디어
 * dp
 * dp[i][j] => i는 특정 시점, j는 온도로 해서 특정시점의 특정 온도일때 가능한 최소 소비전력을 구하면 됨.
 */

public class Prog_에어컨 {

	private final static int INF = 100 * 1000 + 1; //소비 전력의 최대 값은 한번 상승당 100이고, 최대 1000분까지 존재.

	public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
		int answer = INF;

		//온도를 인덱스로 쓰기 위해서 +10처리.
		int startTemp = t1 + 10;
		int endTemp = t2 + 10;
		int outTemp = temperature + 10;

		//dp배열 - 온도는 -10 ~ 40이므로, 배열인덱스로 표시하기 위해 +10을 함.
		int[][] dp = new int[onboard.length][51];

		//초기값 설정
		for(int i = 0; i < onboard.length; i++){
			Arrays.fill(dp[i], INF);
		}

		dp[0][outTemp] = 0; //시작시점의 온도는 실외온도이고 이때 소비 전력은 0임.
		//온도를 올리거나 내리면, 시간상으로 1분뒤의 값이 변화하기 때문에 i는 onboard 길이 - 1까지만 봄
		for(int i = 0; i < onboard.length - 1; i++){
			for(int j = 0; j < 51; j++){

				//승객이 탑승한 시간인데, 온도가 적정온도가 아닌 케이는 볼 필요 없음.
				if(onboard[i] == 1 && (j < startTemp || j > endTemp)) continue;


				//1. 에어컨을 켜는 케이스 - 희망온도는 어떤 온도든 될 수 있기 때문에 에어컨을 킬때는 3케이스를 매번 다 해봐야 함.
				//1-1. 실내온도 == 희망온도
				dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + b);
				//1-2. 실내온도 > 희망온도 => 이 경우는 a만큼 소모하여 온도를 내려야 함.(단, 온도가 1도 이상이야 함.)
				if(j >= 1) dp[i + 1][j - 1] = Math.min(dp[i + 1][j - 1], dp[i][j] + a);
				//1-2. 실내온도 < 희망온도 => 이 경우는 a만큼 소모하여 온도를 올려야 함.(단 온도가 50도 미만이야 함.)
				if(j < 50) dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + a);


				//2. 에어컨을 켜지 않는 케이스
				//2-1. 실외온도 == 실내온도 - 온도가 그대로 유지됨.
				if(outTemp == j) dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j]);
				//2-2. 실외온도 > 실내온도 => 1도씩 상승
				else if (outTemp > j) dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j]);
				//2-3. 실외온도 < 실내온도 => 1도씩 하강.
				else dp[i + 1][j - 1] = dp[i + 1][j - 1] = Math.min(dp[i + 1][j - 1], dp[i][j]);

			}
		}

		//onboard의 마지막 배열중 최소가 되는 값을 선청

		for(int i = 0; i < 51; i++){

			//위의 dp 구성 배열에서는 마지막 시간에 사용자가 탄 케이스 확인이 안되었기 때문에 확인필요.
			if(onboard[onboard.length - 1] == 1 && (i < startTemp || i > endTemp)) continue;

			answer = Math.min(answer, dp[onboard.length - 1][i]);
		}

		return answer;
	}

	public static void main(String[] args){

		Prog_에어컨 p = new Prog_에어컨();

		int temperature1 = 28;
		int t1_1 = 18;
		int t2_1 = 26;
		int a1 = 10;
		int b1 = 8;
		int[] onboard1 = {0, 0, 1, 1, 1, 1, 1};
		System.out.println(p.solution(temperature1, t1_1, t2_1, a1, b1, onboard1));


		int temperature2 = -10;
		int t1_2 = -5;
		int t2_2 = 5;
		int a2 = 5;
		int b2 = 1;
		int[] onboard2 = {0, 0, 0, 0, 0, 1, 0};
		System.out.println(p.solution(temperature2, t1_2, t2_2, a2, b2, onboard2));


		int temperature3 = 11;
		int t1_3 = 8;
		int t2_3 = 10;
		int a3 = 10;
		int b3 = 1;
		int[] onboard3 = {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1};
		System.out.println(p.solution(temperature3, t1_3, t2_3, a3, b3, onboard3));


		int temperature4 = 11;
		int t1_4 = 8;
		int t2_4 = 10;
		int a4 = 10;
		int b4 = 100;
		int[] onboard4 = {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1};
		System.out.println(p.solution(temperature4, t1_4, t2_4, a4, b4, onboard4));
	}
}
