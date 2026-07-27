package programmers.week49;

import java.util.Arrays;

public class Prog_제곱개수배열 {

	private static int n;
	private static int[] nums;//숫자 배열(arr)
	private static long[][] idx;//각 숫자를 brr로 만들었을때의 인덱스 구간.
	private static long[] sum;//구간의 합을 쉽게 구하기 위한 누적합 배열.
	private static long[] countSum;//숫자 개수 누적

	//초기화 메서드
	private static void init(int[] arr){

		n = arr.length;
		nums = arr;
		idx = new long[n][2];
		sum = new long[n];
		countSum = new long[n];

		//idx 배열구성.
		long startIdx = 0;
		for(int i = 0; i < n; i++){
			idx[i][0] = startIdx;
			idx[i][1] = startIdx + nums[i] - 1;

			startIdx = idx[i][1] + 1;
		}

		//sum 배열구성 - 누적합 배열 구성.
		sum[0] = (long)nums[0] * (long)nums[0];
		countSum[0] = nums[0];
		for(int i = 1; i < n; i++){
			sum[i] = ((long)nums[i] * (long)nums[i]) + sum[i - 1];

			countSum[i] = nums[i] + countSum[i - 1];
		}

	}
	//이진탐색으로 어떤구간에 속해있는지 찾기.
	private static int binarySearch(long input){

		int start = 0;
		int end = n - 1;

		while(start <= end){

			int mid = (start + end) / 2;

			if(input >= idx[mid][0] && input <= idx[mid][1]){
				return mid;
			}

			else if(input < idx[mid][0]){
				end = mid - 1;
			}
			else {
				start = mid + 1;
			}
		}

		return -1;
	}

	//l,r이 주어졌을떄 부분 배열의 합 구하기.
	private static long partitionSum(long l, long r){

		int containIdxLeft = binarySearch(l - 1);
		int containIdxRight = binarySearch(r - 1);

		//전체구간
		long totalSum = sum[n - 1];


		//제외 구간 - l 기준의 왼쪽 구간 - l의 경우 idx의 범위중 일부는 포함이 안될수도 있기때문에 빼줘야 함.
		//idx의 구간중에서도 일부는 포함이 안되기 때문에 해당 부분도 같이 빼줘야 함.
		long exclusionLeft =
			(containIdxLeft != 0 ? sum[containIdxLeft - 1] : 0) +
				((l - 1) - idx[containIdxLeft][0]) * nums[containIdxLeft];



		//제외 구간 - r 기준의 오른쪽 구간.
		//r또한 해당 구간에서 추가로 빠지는 부분이 있음.
		long exclusionRight =
			(totalSum - sum[containIdxRight]) +
				((idx[containIdxRight][1] - (r - 1)) * nums[containIdxRight]);




		return totalSum - exclusionLeft - exclusionRight;
	}

	public long[] solution(int[] arr, long l, long r) {
		long[] answer = {0, 0};

		init(arr);

		//K : l~r의 합.
		answer[0] = partitionSum(l, r);

		//C : 길이가 r - l + 1인 brr의 부분 배열중 합이 K인 수.
		//단순 for문으로 하면 10^10이므로 투포인터를 이용해서 중복되는 구간은 패스함.
		//숫자가 배열에 반복되기 때문에 구간단위로 패스 가능.

		//brr의 인덱스
		long leftPoint = 0;
		long rightPoint = r - l;

		long window = partitionSum(1, r - l + 1);

		long brrLen = idx[n - 1][1] + 1;

		//brr이 arr 인덱스로 했을떄 어떤 구간인지 -> 0부터 시작하는 인덱스
		int leftIdx = binarySearch(leftPoint);
		int rightIdx = binarySearch(rightPoint);

		//첫 윈도우 확인
		if(window == answer[0]) answer[1]++;

		while(rightPoint < brrLen - 1){

			if(idx[rightIdx][1] == rightPoint) rightIdx++;

			//각 포인터가 현재 블록에서 밀수 있는 칸 수(연속되는 수가 안바뀌는 위치까지.)
			long distLeft = idx[leftIdx][1] - leftPoint + 1; //left는 마지막칸을 빼도 됨.
			long distRight = idx[rightIdx][1] - rightPoint; //right는 빼는게 아니라 추가라 벗어나면 안됨.

			long move = Math.min(distLeft, distRight);

			//해당 구간의 증가분량.
			long add = (long) nums[rightIdx] - nums[leftIdx];

			if (add == 0) {
				if (window == answer[0]) answer[1] += move;
			} else {
				long diff = answer[0] - window;
				if (diff % add == 0) {
					long q = diff / add;
					if (q >= 1 && q <= move) answer[1]++;
				}
			}

			window += move * add;
			leftPoint += move;
			rightPoint += move;


			if(leftPoint > idx[leftIdx][1]) leftIdx++;
		}


		return answer;
	}

	public static void main(String[] args){

		Prog_제곱개수배열 p = new Prog_제곱개수배열();

		int[] arr1 = {3, 2, 3, 1, 1};
		int l1 = 5;
		int r1 = 7;
		System.out.println(Arrays.toString(p.solution(arr1, l1, r1)));

		int[] arr2 = {2, 2, 2};
		int l2 = 2;
		int r2 = 2;
		System.out.println(Arrays.toString(p.solution(arr2, l2, r2)));

		int[] arr3 = {8, 8, 6, 5, 2, 9, 8, 4, 3, 10};
		int l3 = 25;
		int r3 = 27;
		System.out.println(Arrays.toString(p.solution(arr3, l3, r3)));

		int[] arr4 = {70195, 25471, 7389, 58187, 18454, 90532, 97667, 17148, 91636, 2810};
		int l4 = 126058;
		int r4 = 462933;
		System.out.println(Arrays.toString(p.solution(arr4, l4, r4)));

		int[] arr5 = {16952, 70276, 16771, 37992, 87549, 54906, 36718, 20478, 57088, 27916, 51509, 83422, 51707, 18807, 80859, 2673, 37734, 93380};
		int l5 = 149845;
		int r5 = 228204;
		System.out.println(Arrays.toString(p.solution(arr5, l5, r5)));

		int[] arr6 = {49134, 86806, 94548, 88849, 95022, 28334, 16637, 79487, 23773, 7314, 47370, 50269, 36573, 9415, 44674, 28096};
		int l6 = 61242;
		int r6 = 88535;
		System.out.println(Arrays.toString(p.solution(arr6, l6, r6)));

	}
}
