package baekjoon.week21;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어
 * dp
 *
 * 모든 경우를 매번 구하는 것은 비효율적이며, 정상의 수가 총 1만이라서 시간 초과가 발생한다.
 * 1~6이 있다고 가정햇을때, 3번까지 악수하는 경우의 수를 구할떄는 이전에 구해둔 1,2가 악수하는 경우의 수를 구해두고 여기에 추가만 해주면 된다.
 * 즉, 메모이제이션을 이용해서 매번 모든 상태를 다시 구하는것이 아니라, 중복되는 상태는 기록해두었다가 더해주기만 하면 된다.
 * 또한 중요한 점은 교차되면 안되는데, 이부분은 전체 정상의 수가 짝수이므로, 연결한 두수의 차가 홀수라면, 교차 되지 않는다.
 * 두 수의 차가 짝수가 되면, 악수한 두 정상 사이에는 홀수개의 정상이 남아서 교차하지 않고서는 악수가 불가능해진다.
 *
 */
public class BOJ1670_정상회담2 {

	private final static long MOD = 987654321;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		long[] dp = new long[N + 1];
		dp[0] = 1; //기본값 1(곱할떄, 0이면 0이 되기 떄문에 이를 방지하기 위해서 1로 둠.)
		dp[2] = 1; //점 두개로 만들수 있는 경우의 수 1

		//i는 짝수개로 늘어남 -> dp[i]는 i개 점으로 만들 수 있는 경우의 수.
		for (int i = 4; i <= N; i+=2){
			for (int j = 0; j <= i - 2; j+=2){
				dp[i] += dp[j] * dp[i - j - 2];
				dp[i] %= MOD;
			}
		}

		System.out.println(dp[N]);
	}
}
