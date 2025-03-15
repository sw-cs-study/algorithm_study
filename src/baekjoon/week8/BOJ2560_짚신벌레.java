package baekjoon.week8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 날짜를 하나씩 증가시키면서 벌레를 증가, 감소시켜나가면 수가 커서 시간초과이다.
 * 점화식을 이용해서 풀어낸다.
 *
 * i일에 생성된 벌레의 수를 구하려면, (i - 1)일에 생성된 벌레의 수 + (i - a)일의 벌레 수가 된다.
 * a일이 지나면 벌레 하나를 생성하기 때문이다.
 * 여기에 추가로 b일이 지나면 더이상 벌레를 생성하지 못하기 때문에 b일이 지난 벌레수만큼 빼줘야 한다(i - b)
 *
 * 최종적으로 구하고자 하는 날짜는 N이다.
 * 이때 죽은 벌레는 빠졌기 때문에 N일까지 생성되어있는 벌레에서, n-d에 생성된 벌레를 제거해줘야 한다.
 *
 */
public class BOJ2560_짚신벌레 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int a = Integer.parseInt(st.nextToken()); //성체가 되는 날 - 성체가 되면 새로운 개체 생성.
		int b = Integer.parseInt(st.nextToken()); //새로운 개체 생성 불가능한 날짜.
		int d = Integer.parseInt(st.nextToken()); //죽는 날짜.
		int N = Integer.parseInt(st.nextToken()); //목표일자.

		int[] dp = new int[N + 1];
		//태어난 날부터 성체가 되기 전날까지 1로 채우기.
		for(int i = 0; i < a; i++){
			dp[i] = 1;
		}

		//반복문 돌면서 점화식으로 각 날짜에 벌레 채우기.
		for (int currentDay = a; currentDay <= N; currentDay++){

			if (currentDay - b < 0){
				dp[currentDay] = (dp[currentDay - 1] + dp[currentDay - a]) % 1000;
			}
			else {
				dp[currentDay] =
					//빼는 연산이 있으면 음수가 나올 수도 있기 때문에 1000만큼 더해줌.
					(dp[currentDay - 1] + dp[currentDay - a] - dp[currentDay - b] + 1000) % 1000;
			}
		}

		//죽은 벌레 수 뺴주기.
		if (N - d < 0){
			System.out.println(dp[N] % 1000);
		}
		else{
			System.out.println((dp[N] - dp[N - d] + 1000) % 1000);
		}



	}
}
