package baekjoon.week7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 누적합
 */
public class BOJ2437_저울 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int N = Integer.parseInt(br.readLine());
		int[] numArray = new int[N];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(numArray);

		int sum  = 0; //이전추들의 누적합.
		for (int i = 0; i < N; i++) {

			if(sum + 1 < numArray[i]) break;

			sum += numArray[i];

		}

		System.out.println(sum + 1);

	}
}
