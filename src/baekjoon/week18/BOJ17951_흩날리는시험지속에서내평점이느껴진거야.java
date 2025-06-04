package baekjoon.week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
* 아이디어
* 이분탐색
* 받을수 있는 최대 점수를 이분탐색으로 뽑는다.
* 이분탐색을 통해서 후보점수를 뽑으면, 문제에서 주어진 그룹의 개수만큼 그룹을 만들때, 후보점수 이상으로 값이 나오는지 확인해야 한다.
* (뽑은 점수는 각 그룹에서 나올수 있는 최소 점수이므로, 해당 점수이상이어야 한다.)
* 이때 중요한 점은 문제에서 "현재 순서 그대로"라고 주었기 떄문에, 그룹을 앞에서 부터 쪼개보면 된다.
* 후보점수를 뽑았으면, 앞에서 부터 후보점수 이상이 될떄까지 더해보고, 후보점수를 넘으면 해당 위치까지를 첫번째 그룹,
* 그다음으로 두번째 그룹을 만들어나간다.
* 만약에 후보점수를 넘는 그룹이, 문제에서 주어진 그룹의 수에 미치지 못했다면 점수의 크기를 줄어봐야 하고, 그룹의 수를 넘는다면 점수를 키워봐야 한다.
* */
public class BOJ17951_흩날리는시험지속에서내평점이느껴진거야 {

	private static int N; //문제 수
	private static int K; //그룹수
	private static int[] numArray;

	//그룹 만들어 보기 - true : 그룹수를 넘지 않았기 떄문에, 수를 키워봐야 함... false : 그룹수와 같거나, 더 많은 것이므로 수를 키워야 함..
	private static boolean groupCheck(int targetValue){

		int groupCount = 0;
		int tempSum = 0;
		for (int i = 0; i < N; i++){
			tempSum += numArray[i];

			if (tempSum >= targetValue){
				groupCount++;
				tempSum = 0;
			}
		}

		return groupCount < K;
	}

	//이진 탐색.
	private static int binarySearch(int left, int right){

		while(left <= right){
			int mid = (left + right) / 2;

			if (groupCheck(mid)) right = mid - 1;
			else left = mid + 1;
		}

		return right;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		numArray = new int[N];

		st = new StringTokenizer(br.readLine());

		int minValue = 21;
		int maxValue = 0;

		for(int i = 0; i < N; i++){

			int inputValue = Integer.parseInt(st.nextToken());

			minValue = Math.min(minValue, inputValue);
			maxValue += inputValue;

			numArray[i] = inputValue;
		}

		System.out.println(binarySearch(minValue, maxValue));
	}
}
