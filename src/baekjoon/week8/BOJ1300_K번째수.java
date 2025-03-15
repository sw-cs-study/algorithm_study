package baekjoon.week8;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어
 * 참고 : https://st-lab.tistory.com/281
 *
 * 이분탐색과 곱셈의 성질을 이용
 * 행렬을 만들어 나열하면, 각 행은 1씩증가 2씩증가 이런식으로 구성된다.
 * 하나씩 비교할 수도 있지만, 그러면 10^5*10^5번 확인해야 하기 때문에 더 빠른 방법을 이용한다
 * 구하고자 하는 수가 x라면 1씩증가하는 구간에서 x까지 나올수 있는 수의 개수는 x/1이다.
 * 예를 들면 구하고자 하는 목표 수가 6이라고 하면, 1씩 증가하는 구간에서는 6개가 나올 수 있다.
 * 주의 할 점은 n의 크기를 넘지 않아야 하는 것이다.
 *
 * 추가로 확인할 점은 구하고자 하는 수 x는 k를 넘을 수 없다.
 * 잘 생각해보면 k의 최대값은 n^2이다.
 * 모든수가 1씩 증가한다고 해도, k번째수와 x값은 같은 경우이므로 x <= k 가 성립한다.
 */
public class BOJ1300_K번째수 {

	private static int N; //N*N 행렬 크기
	private static int K; //목표 K번째 수.

	//k번째보다 큰지 작은지 결정 -
	private static int check(int N, int K, int mid){


		int sameCount = 0;//mid와 같은 수가 몇개인지 확인.
		int count = 0; //mid와 같거나 작은 수가 몇개인지 누적

		//각 행에서 나올수 있는 최대 개수 구하기.
		for(int i = 1; i <= N; i++){

			count += Math.min(mid / i, N);

			if(mid % i == 0 && (mid / i) <= N){
				sameCount++;
			}
		}
		//sameCount가 0이라면 행렬에 mid에 해당 하는 수가 없음.
		// if(sameCount == 0) return -1;

		// System.out.println("mid = " + mid);
		// System.out.println("count = "  + count);
		// System.out.println("sameCount = " + sameCount);


		if (K >= (count-sameCount + 1) && K <= count){
			return 0;
		}
		else if (K > count){
			return 1;
		}
		else{
			return -1;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		//수의 범위를 1~k사이에서 이분탐색을 통해 빠르게 줄여나간다.
		int start = 1;
		int end = K;

		int mid = -1;
		while (start <= end){

			mid = (start + end) / 2;

			int checkValue = check(N, K, mid);
			//수를 늘려봐야 함.
			if (checkValue == 1){
				start = mid + 1;
			}
			//0이면 해당수임
			else if (checkValue == 0){
				break;
			}
			//-1수를 줄여봐야 함.
			else {
				end = mid - 1;
			}
		}

		System.out.println(mid);

	}
}
