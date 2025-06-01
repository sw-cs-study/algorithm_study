package baekjoon.week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * LIS
 * BOJ1365와 동일한 문제
 * 연결선이 겹치지 않게 할 수 있는 최대 개수를 구하는 문제
 * 1365문제와 동일하게 dp를 활용하여 N^2로 해결하게 되면 4만개이므로, 시간초과가 나게된다.
 * 따라서 binary search를 이용한 풀이로 nlogn으로 연산을 줄여야 한다.
 * dp 배열의 인덱스는 LIS의 길이가 되고, 안에 값은 해당 길이가 될 수 있는 값이 된다.
 * 하나씩 값을 선택하고, 이전에 저장된 값들중에서 선택한 값을 넘지 않는 최대의 위치를 찾아서 수를 교체 해준다.
 *
 */
public class BOJ2352_반도체설계 {

	private static int N;
	private static int[] numArray;
	private static List<Integer> dp;

	//이진탐색을 통해서, 수를 배치할 위치를 찾음.
	private static int binarySearch(int left, int right, int target){

		while (left < right){
			int mid = (left + right) / 2;

			//dp배열의 값이, 타켓 값보다 크면 범위를 더 줄여봐야 함.
			if(dp.get(mid) > target) right = mid;

			//타겟값보다 작거나 같다면 범위를 늘려봐야 함.
			else left = mid + 1;
		}

		//상한선을 구하는 것 이므로 right를 반환(target을 넘지않으면서 가장 가까운 위치중, 가장 나중에 있는 것을 구함.)
		return right;
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		numArray = new int[N];
		dp = new ArrayList<>();

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}


		//초기 값
		dp.add(numArray[0]);


		//탐색하면서 dp배열 채우기.
		for (int i = 1; i < N; i++){

			//dp 배열의 마지막 값과 비교해서, 현재값이 더 크면 볼 필요 없이 추가함.
			if (dp.get(dp.size() - 1) < numArray[i]){
				dp.add(numArray[i]);
				continue;
			}

			//이전에 저장된값중 배치할 위치 찾기.
			int idx = binarySearch(0, dp.size() - 1, numArray[i]);
			dp.set(idx, numArray[i]);
		}


		System.out.println(dp.size());
	}
}
