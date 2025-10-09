package baekjoon.week28;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 아이디어
 * 그리디
 * 주어진 수에 필요한 성냥의 개수를 나열해보면 다음과 같다.
 * 2개 => 1/ 3개 => 7, 4개 => 4/ 5개 => 2,3,5 / 6개 => 0,6,9 / 7개 => 8
 * 여기서 최대와 최소를 구해야 하는데,
 * 우선 최대의 경우에는 1과7로만 만들면 된다.
 * 가장 높은자리의 수가 큰것보다, 최대자리수가 큰것이 더 크다
 * 즉, 999보다 1111이 낫다는 것이다.
 * 이를 위해서는 최소의 성냥을 사용해야 최대의 자리수를 만들수 있는데, 그게 1,7이다.
 * 짝수의 경우에는 전부 1로 채우면 되고, 홀수의 경우에는 맨앞에 7을 두고 나머지를 1로 두는 것이 최대가 된다.
 *
 * 최소의 경우에는 성냥을 최대로 많이 써야 하는데, 우선은 8이하와 9이상으로 나눠야 한다.
 * 성냥이 8개 이하라면, 숫자 하나만 써도 되기 때문에 해당 수를 선택하면 된다.(8의 경우에는 10)
 * 9이상이라면, 두개로 나눠지게 되는데, 최대한 자리수를 작게 만들기 위해서 개수가 많이 필요한 수를 선택하는 전략으로 간다.
 * 초기에는 각 성냥개비로 만들수 있는 최소 수를 저장해두고, 구해나간다.
 * 최소의 경우에는 9이상일때부터 모든 경우를 다 해봐야 한다.
 * 나올수 있는 성냥 수는 2~7이므로 9 - 2, 9 - 3이런식으로 모든 경우를 비교하면서 최소 값을 업데이트 한다.
 * 이 과정을 거치면, 2개짜리 + 7개짜리 / 7개짜리 + 2개짜리, 이런식으로 성냥의 수는 같아도 수의 위치에 따른 크기도 파악이 가능하다.
 * 이 경우는 성냥이 9 ~ 100깨까지만 보면 되고, 각 경우마다 2~7 개만 판단하면 되기 때문에 시간 초과가 날 일은 없다.
 */

public class BOJ3687_성냥개비 {

	private static long[] dp;//각 성냥개수로 만들수 있는 최소수 저장해두기. - 최소값 구할때 필요함.
	private final static int[] numArray = {1,7,4,2,0,8};//1자리 수의 각 성냥별 최소 수(인덱스 - 2해서 사용.)

	//최대 값 구하기 - 최대 50자리도 가능하기 떄문에(성냥 100개) String으로 계산.
	private static String getMaxValue(int number){
		StringBuilder result = new StringBuilder();


		//짝수인 경우.
		if (number % 2 == 0){

			for(int i = 0; i < number / 2; i++){
				result.append("1");
			}
		}

		//홀수인 경우.
		else {
			result.append("7");

			for(int i = 0; i < (number - 3) / 2; i++){
				result.append("1");
			}

		}
		return result.toString();
	}

	//최소값 구하기 - 최소 값의 경우에는 미리 배열에 저장해둠.
	private static void initMinValue(){

		dp = new long[101];
		Arrays.fill(dp , Long.MAX_VALUE); //최소값을 구해야 하기 때문에 최대값을 저장함.

		dp[2] = 1;
		dp[3] = 7;
		dp[4] = 4;
		dp[5] = 2;
		dp[6] = 6;
		dp[7] = 8;
		dp[8] = 10;

		for(int i = 9; i <= 100; i++){
			for(int j = 2; j <= 7; j++){

				//나올 수 있는 수를 뽑아봄.
				String temp = String.valueOf(dp[i - j]) + String.valueOf(numArray[j - 2]);
				dp[i] = Math.min(dp[i], Long.parseLong(temp));
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int testCase = Integer.parseInt(br.readLine());

		initMinValue();

		StringBuilder result = new StringBuilder();
		for(int i = 0; i < testCase; i++){

			int inputValue = Integer.parseInt(br.readLine());

			result.append(dp[inputValue]).append(" ").append(getMaxValue(inputValue));

			if (i == testCase - 1) continue;

			result.append("\n");

		}

		System.out.println(result.toString());

	}
}
