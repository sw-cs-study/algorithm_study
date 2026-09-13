package programmers.week52;

import java.util.function.Function;

/**
 * 아이디어
 * dp ->(백준의 행력곱셈구간이랑 비슷)
 */

public class Prog_보물찾기 {

	public int solution(int[] depth, int money, Function<Integer, Integer> excavate) {


		//구간 인덱스를 1부터 w까지 써서 왼쪽 오른쪽 표현시 인덱스를 벗어나지 않도록.
		int[][] cost = new int[depth.length + 2][depth.length + 2]; //해당 구간에서 나올수 있는 최악의 비용.
		int[][] pick = new int[depth.length + 2][depth.length + 2]; //cost 기반으로, 해당 구간에서 최악의 상황에도 최소비용으로 답을 구할 수 있는 위치.


		//구간 길이
		for(int len = 1; len <= depth.length; len++){

			//구간의 왼쪽 시작위치
			for(int left = 1; left + len - 1 <= depth.length; left++){

				//구간의 오른쪽 시작위치.
				int right = left + len - 1;

				//최적의 값.
				int best = Integer.MAX_VALUE; //각 구간에서 나올수 있는 최악의 비용들 중 최선을 구하기 위함(2를 먼저 파면 7, 3을 먼저파면 9라 하면, 7이 저장되어야 함.)
				int bestLoc = left;//최적의 위치 - 시작은 일단 L로 잡음, 아래 탐색을 통해서 L~R 구간중 하나로 선택이 됨.

				//주어지는 구간중에서 어떤 위치를 먼저 팔지 다 해봄
				for(int i = left; i <= right; i++){

					int d = depth[i - 1]; //i위치를 파는 데 필요한 비용(깊이)

					//i를 팠을때 나올수 있는 시나리오(-1, 0, 1)
					int leftWeight = d + cost[left][i - 1]; // 왼쪽에 있다면?
					int rightWeight = d + cost[i + 1][right];//오른쪽에 있다고 하면?

					//특정위치를 팠을때 답이 왼쪽인경우, 오른쪽인 경우, 현재위치인 경우중 더 작은 케이스를 선택,
					//최악의 케이스를 구함
					int maxCase = Math.max(d, Math.max(leftWeight, rightWeight));

					//해당 구간에서 나온 최악의 케이스들 중 가장 작은 값을 선택함 - 해당 값이 LR구간에서 답을 구할때의 최소값
					if(best > maxCase){
						best = maxCase;
						bestLoc = i; //위치를 저장해둬야, 해당 구간에서의 최소값을 알 수 있음.
					}
				}

				cost[left][right] = best;
				pick[left][right] = bestLoc;

			}
		}


		//dp로 구간을 다 구했으면, 해당 정보를 토대로 이분탐색 진행
		int start = 1, end = depth.length;

		while(true){
			int mid = pick[start][end]; //현재구간에서 최소 비용으로 답을 찾을 수 있는 위치를 선택
			int result = excavate.apply(mid);//해당위치로 돌렸을때 보물 위치 확인.

			//0이면 답이니 반환.
			if(result == 0) return mid;
			//왼쪽이라면,
			else if(result == -1){
				end = mid - 1;
			}
			else{
				start = mid + 1;
			}
		}
	}


	public static void main(String[] args){

		Prog_보물찾기 p = new Prog_보물찾기();

		int[] depth1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int money1 = 55;
		final int treasure1 = 3;
		Function<Integer, Integer> excavate1 = i -> i == treasure1 ? 0 : (i < treasure1 ? 1 : -1);
		System.out.println(p.solution(depth1, money1, excavate1));  // 3

		int[] depth2 = {1, 1, 1, 1, 1};
		int money2 = 3;
		final int treasure2 = 5;
		Function<Integer, Integer> excavate2 = i -> i == treasure2 ? 0 : (i < treasure2 ? 1 : -1);
		System.out.println(p.solution(depth2, money2, excavate2));  // 5

		int[] depth3 = {2, 100, 1, 100, 3, 100, 1};
		int money3 = 200;
		final int treasure3 = 6;
		Function<Integer, Integer> excavate3 = i -> i == treasure3 ? 0 : (i < treasure3 ? 1 : -1);
		System.out.println(p.solution(depth3, money3, excavate3));  // 6

		int[] depth4 = {2, 100, 1, 100, 3, 100, 1};
		int money4 = 200;
		final int treasure4 = 5;
		Function<Integer, Integer> excavate4 = i -> i == treasure4 ? 0 : (i < treasure4 ? 1 : -1);
		System.out.println(p.solution(depth4, money4, excavate4));  // 5

		int[] depth5 = {3, 2, 1, 2, 3, 2, 1, 2};
		int money5 = 8;
		final int treasure5 = 5;
		Function<Integer, Integer> excavate5 = i -> i == treasure5 ? 0 : (i < treasure5 ? 1 : -1);
		System.out.println(p.solution(depth5, money5, excavate5));  // 5

		int[] depth6 = {1, 1000, 1, 1, 1, 10, 15, 1};
		int money6 = 1002;
		final int treasure6 = 2;
		Function<Integer, Integer> excavate6 = i -> i == treasure6 ? 0 : (i < treasure6 ? 1 : -1);
		System.out.println(p.solution(depth6, money6, excavate6));  // 2
	}
}
