package baekjoon.week28;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + dp
 * 트리레벨 1부터 N까지 하나씩 두면서 경우의 수를 구한다.
 * 각 레벨에 딱맞게 두어야 하며, 색이 3개이므로, 색1개를 두는경우, 2개를 두는 경우, 3개를 두는 경우, 안두고 패스하는 경우가 가능하다.
 * 각 레벨에 색을 두거나 안두는 경우를 구하고, 재귀호출을 통해 다음 상태를 구하도록 한다.
 * 주의할 점은 색상 2개를 선택하려면 K가 2의 배수, 색상 3개를 선택하려면 3의 배수여야 함.(모든 색상의 수는 동일해야 하기 때문에)
 * 추가로 색상을 선택하는데에는 조합이용해야 한다
 */

public class BOJ1234_크리스마스트리 {

	private static long[][][][] dp;//각 레벨에서 rgb가 남아있을때의 경우의 수
	private static long[][] comDp;//nCr을 구할때, 매번 구하면 시초가 날 수 있기 떄문에 dp로 저장해둠.
	private static long[] facDp;

	//dfs
	private static long dfs(int currentN, int r, int g, int b){

		//하나라도 0보다 작으면 0반환해서 계산하지 않도록 함, 0보다 작은거는 선택가능한 개수보다 많이 선택한 것.
		if(r < 0 || g < 0 || b < 0) return 0;

		//현재 선택한 레벨이 0이거나 0보다 작아지면 끝
		if (currentN <= 0) return 1;

		//해당 레벨, 해당 색깔을 전에 구했었다면 그대로 반환
		if(dp[currentN][r][g][b] != 0) return dp[currentN][r][g][b];

		//1가지 색상을 선택하는 경우.
		dp[currentN][r][g][b] += dfs(currentN - 1, r - currentN, g, b);
		dp[currentN][r][g][b] += dfs(currentN - 1, r, g - currentN, b);
		dp[currentN][r][g][b] += dfs(currentN - 1, r, g, b - currentN);

		//2가지 색상을 선택하는 경우 - 2의 배수만 가능,
		if(currentN % 2 == 0){
			//현재 레벨에서 각 색상을 사용할 수 있는 수
			int tempCount = currentN / 2;
			dp[currentN][r][g][b] += dfs(currentN - 1, r - tempCount, g - tempCount, b) * comb(currentN, tempCount);
			dp[currentN][r][g][b] += dfs(currentN - 1, r - tempCount, g, b - tempCount) * comb(currentN, tempCount);
			dp[currentN][r][g][b] += dfs(currentN - 1, r, g - tempCount, b - tempCount) * comb(currentN, tempCount);
		}

		//3가지 색상을 선택하는 경우.
		if(currentN % 3 == 0){
			//현재 레벨에서 각 색상을 사용할 수 있는 수
			int tempCount = currentN / 3;
			dp[currentN][r][g][b] +=
				dfs(currentN - 1, r - tempCount, g - tempCount, b - tempCount)
					* comb(currentN, tempCount)
					* comb(currentN - tempCount, tempCount);
		}

		return dp[currentN][r][g][b];

	}

	//fac
	private static long fac(int num){

		if(num == 0 || num == 1) return 1;

		if(facDp[num] != 0) return facDp[num];

		return num * fac(num - 1);


	}
	//combination - nCr => n! / (r! * (n - r)!)
	private static long comb(int n, int r){

		if(comDp[n][r] != 0) return comDp[n][r];

		return comDp[n][r] = fac(n) / (fac(r) * fac(n - r));
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int g = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());

		dp = new long[N + 1][r + 1][g + 1][b + 1];
		comDp = new long[N + 1][N + 1];
		facDp = new long[N + 1];
		System.out.println(dfs(N,r,g,b));
	}
}
