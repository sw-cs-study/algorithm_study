package baekjoon.week24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어(참고함.)
 * DP
 *
 * 특정 위치에 알파벳을 배치할때는 앞에 A가 몇개인지 B가 몇개인지 확인하면 된다.
 * AB가 있고, 3번째에 알파벳을 배치한다고 했을때,
 * 앞에 A가 1개, B가 1개 이므로, A를 배치하면 순서쌍이 0개, B를 배치하면 순서쌍이 1개 추가 되는 것이다.
 * AAB라고 하면, 앞에 A가 2개, B가 1개이므로 A를 배치하면 0개, B를 배치하면 순서쌍이 2개, C를 추가하면 순서쌍이 3개 추가된다.
 * 즉, A를 배치할때는 0, B를 배치할떄는 앞선 A의 개수만큼 순서쌍 추가, C를 배치하면 (A개수) + (B개수) 만큼 추가가 된다.
 * 따라서, 신규값을 추가할떄, 이전의 값을 저장해두고 활용하기 때문에 메모이제이션을 사용해야 하고, 이 유형은 DP가 된다.
 *
 * (추가)
 * 처음에는 특정 문자열 N번쨰까지 ABC의 개수를 dp[N][0],dp[N][1],dp[N][2],dp[N][3]에 각각 저장하려고 했는데,
 * 이렇게 되면 특정 문자열까지 나올수 있는 a,b,c의 개수가 한가지로 고정되기 때문에 모든 경우를 탐색할 수는 없다.
 */

public class BOJ12969_ABC {

	private static int N;
	private static int K;
	private static boolean[][][][] dp;
	private static char[] result;


	public static boolean dfs(int len, int a, int b, int pair){

		//종료 조건 - 문자열길이가 N이고, 순서쌍이 K를 만족하면 종료
		//길이가 N에 도달안하면 이어서 추가해봐야 함.
		if(N == len){
			if(pair == K) return true;
			return false;
		}

		//이전에 탐색한 문자면 패스
		if(dp[len][a][b][pair]) return false;

		//해당 문자를 탐색하기 전에 방문 처리 - 재귀 호출했을때 중복을 탐색하는 것을 막기 위함.
		dp[len][a][b][pair] = true;

		//A를 선택하는 경우.
		result[len + 1] = 'A';//문자열을 해당 길이위치에 저장.
		//true가 반환되면, 해당 경우로 만들수 있다는 뜻으로, 재귀를 종료
		if (dfs(len + 1, a + 1, b, pair)) return true;

		//B를 선택하는 경우.
		result[len + 1] = 'B';//문자열을 해당 길이위치에 저장.
		if(dfs(len + 1, a, b + 1, pair + a)) return true;

		//C를 선택하는 경우.
		result[len + 1] = 'C';//문자열을 해당 길이위치에 저장.
		if(dfs(len + 1, a, b, pair + a + b)) return true;


		return false;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		//문자열 길이, a의 수, b의 수, 순서쌍의 개수. - 방문 처리 배열로 생각.
		//문자열의 길이가 같고, a,b의 수가 같으며 순서쌍의 개수가 동일하면 같은 문자로 생각.
		//C의 개수는 저장할 필요가 없음, C보다 큰 알파벳은 없기 떄문에 C는 순서쌍을 계산할때 사용되지 않음.
		dp = new boolean[N + 1][N + 1][N + 1][N * (N-1)/2];

		result = new char[N + 1];
		StringBuilder sb = new StringBuilder();

		//true가 반환되면 만족하는 무자열이 잇다는 뜻.
		if (dfs(0,0,0,0)){

			for(int i = 1; i <= N; i++){
				sb.append(result[i]);
			}
			System.out.println(sb);

		}
		//false면 만족하는 문자열이 없음.
		else{
			System.out.println(-1);
		}
	}
}
