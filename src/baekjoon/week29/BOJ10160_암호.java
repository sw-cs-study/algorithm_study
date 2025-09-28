package baekjoon.week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 */

public class BOJ10160_암호 {

	private final static int MOD = 1000000009;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		//인덱스는 문자열의 길이를 의미함.
		long[] dp = new long[N + 1];
		dp[0] = 1;

		//각 문자열 길이 별로 나타날 수 있는 문자의 수는 이전 문자열 * K개임

		//4이하의 경우에는 특정패턴이 발생하지 않음.
		for(int i = 1; i <= 4; i++){
			dp[i] = (dp[i - 1] * K) % MOD;
		}

		//5이상부터는 특정 패턴이 나타남.
		for(int i = 5; i <= N; i++){
			dp[i] = (dp[i - 1] * K) % MOD;//기본적으로 문자열의 수
			//나타나는 패턴 만큼 빼줘야 되는 수.(현재 문자열 길이에서 패턴 길이인 5만큼 뺀 문자열 수 * 2를 함)
			//현재 문자열 길이가 7이라면, 5를 뺀 dp[2]에는 길이가 2일때 안전한 문자열의 수가 나오는데, 여기에 패턴 두종류가 붙으면 제외해야 하는 수가 됨.
			dp[i] = (dp[i] - dp[i - 5] * 2 % MOD + MOD) % MOD;

			//7이상일떄는 빼면 안되는 것을 뺀 경우가 있어서 더해야줘야 한다.
			//x x A B A B C B C 문자열 길이 9일떄 이런 케이스가 있다고 해보자.
			//문자를 뺄때, ABABC, ABCBC 두 패턴에 대해서 문자를 제외할 것이다.
			//그런데 잘 생각해보면 ABCBC패턴을 생각하면, 앞서서 ABABC 패턴으로 제외되어서 나올 수 없는 문자열이된다.
			//즉, 나올 수 없는 문자열을 제거 했기 때문에, XXAB라는 문자열 수만큼 더해줘야 함.

			if(i >= 7){
				dp[i] = (dp[i] + dp[i - 7]) % MOD;
			}
		}

		System.out.println(dp[N]);

	}
}
