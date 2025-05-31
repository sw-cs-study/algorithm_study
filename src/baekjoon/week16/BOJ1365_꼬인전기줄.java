package baekjoon.week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * LIS
 *
 * 전기줄이 꼬이지 않으려면, 반대쪽 전봇대가 증가하는 수열의 형태로 존재해야 한다.
 * 즉, 주어진 연결된 반대쪽 전봇대의 최대 증가하는 수열의 길이를 구하고, 전체에서 빼주면 몇개의 전선을 잘라야 하는지 답이 나오게 된다.
 * 이때 N이 10^5이므로 단순 dp로 해결하게 되면 10^10으로 시관 초과가 날수 있기 떄문에,
 * 이전에 자신보다 작은 전봇대 수중, 가장 긴 값을 구하기 위해 이분탐색을 활용한다.
 */
public class BOJ1365_꼬인전기줄 {

	private static int binarySearch(int[] dp, int left, int right, int target){

		while(left < right){
			int mid = (left + right) / 2;

			if(dp[mid] > target) right = mid;
			else left = mid + 1;
		}
		return right;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		int[] numArray = new int[N];

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		//길이를 저장할 dp 배열
		int[] dp = new int[N + 1];
		int max = 0;
		int idx = 0;

		for(int i = 0; i < N; i++){


			if(numArray[i] > dp[max]){
				max += 1;
				dp[max] = numArray[i];
				continue;
			}

			idx = binarySearch(dp, 0, max, numArray[i]);
			dp[idx] = numArray[i];
		}
		System.out.println(N - max);
	}
}
