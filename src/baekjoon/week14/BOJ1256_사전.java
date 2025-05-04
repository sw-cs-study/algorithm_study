package baekjoon.week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 총 나올 수 있는 문자열의 순서는 N*M*2
 * 행 => n, 열 => m 각 칸에 들어가는 수는 a를 n개, z를 m 개 썼을때의 만들수 있는 경우의 수를 저장함.
 *
 * 참고함...
 */
public class BOJ1256_사전 {

	private final static int INF = 1_000_000_001;

	private static int N;
	private static int M;
	private static int K;

	private static int[][] dp;//dp 배열

	private static StringBuilder result;//최종 문자열 출력


	//dp 배열 초기 구성 - 문자를 n,m개 썼을때 몇개의 경우의 수가 만들어지는지,
	private static void init(){
		for(int i = 0; i <= N; i++) {
			for(int j = 0; j <= M; j++){

				//둘중에 하나만 쓰는 경우는 1개의 문자열만 만들수 있음.
				if(i == 0 || j == 0) dp[i][j] = 1;

				if(i > 0 && j > 0) dp[i][j] = Math.min(dp[i - 1][j] + dp[i][j - 1], INF);
			}
		}
	}

	//재귀돌면서 문자열 구성
	private static void setStr(int countA, int countZ, int targetCount){

		//a가 0이면 z만 추가.
		if(countA == 0){
			for(int i = 0; i < countZ; i++){
				result.append("z");
			}
			return;
		}

		//z가 0이면 a만 추가.
		if(countZ == 0){
			for(int i = 0; i < countA; i++){
				result.append("a");
			}
			return;
		}

		//a를 하나뺐을때 나올수 있는 최대 경우의 수보다 목표번째수가 더 크면 앞에 z를 붙임.
		if(dp[countA - 1][countZ] < targetCount){
			result.append("z");
			//z를 하나 두었기 때문에 z를 하나 안뒀을때의 경우를 확인
			setStr(countA, countZ - 1, targetCount - dp[countA - 1][countZ]);
		}
		//a를 하나 뺐을때 나올 수 있는 최대 경우의 수보다 목표번째수가 더 크면 앞에 a를 붙임.
		else{
			result.append("a");
			setStr(countA - 1, countZ, targetCount);
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		dp = new int[N + 1][M + 1];
		result = new StringBuilder();
		init();

		//dp의 (N,M)에 누적된 값이 K보다 작으면 패스(구할 수 없음)
		if(dp[N][M] < K){
			System.out.println(-1);
			return;
		}

		setStr(N,M,K);
		System.out.println(result);

	}
}
