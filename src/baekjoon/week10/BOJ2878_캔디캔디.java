package baekjoon.week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디
 * 친구들이 받고 싶어하는 사탕의 개수의 합은 항상 M(가지고 있는 사탕수)넘는다.
 * 친구들의 분노수치를 최소로 하기 위해서는 최대한 균등해지게 분배한다.
 * 모든 친구들이 받지 못한 사탕의 수를 최대한 비슷하게 만들어야 제곱을 해서 더했을때 수가 작아진다.
 *
 * (추가)
 * 위의 방법으로 1로 만든다는 아이디어를 쓰면 몇개만 해봐도 반례가 존재한다.
 * 1로 만드는 것보다 좀 더 큰수로 만드는게 더 작은케이스가 존재한다.
 * 아래의 글을 참조해서 로직을 수정했다.
 * 우선 주어진 사탕 수와, 친구들이 원하는 사탕수의 합을 빼서, 나눠줄수 없는 사탕수가 몇ㄱ
 *
 * 참고 : https://maivve.tistory.com/152
 */

public class BOJ2878_캔디캔디 {

	private static final long REMAIN_VALUE = (long)Math.pow(2, 64);

	public static void main (String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		int[] candyArray = new int[N];
		long sum = 0;

		for (int i = 0; i < N; i++){
			candyArray[i] = Integer.parseInt(br.readLine());
			sum += candyArray[i];
		}

		//정렬 - 원하는 사탕개수가 적은 쪽부터 나눠주기 위해.
		Arrays.sort(candyArray);

		//부족한 사탕의 수
		long lackValue = sum - M;

		//큰수부터 줄여나감.

		long result = 0;

		for (int i = 0; i < N; i++){

			long remainCount = Math.min(candyArray[i], lackValue / (N - i));
			lackValue -= remainCount;

			result += (remainCount * remainCount) % REMAIN_VALUE;
			// result %= REMAIN_VALUE;

		}




		System.out.println(result % REMAIN_VALUE);
	}
}
