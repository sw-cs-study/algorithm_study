package baekjoon.week27;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.*;

/**
 * 아이디어
 * 원형 누적합 + 조합
 * 두 피자에서 나올 수 있는 모든 조합을 구해서 반환한다.
 * 피자는 연속된 크기로 줄수 있기 때문에 누적합을 이용해서 줄 수 있는 모든 크기를 구해둬야 하는데,
 * 여기서 주의할 점은 원형이라는 점이다
 * 원형이기 때문에 배열의 크기를 피자배열의 2배 만큼 키워서 누적합을 구하면 나올 수 있는 모든 경우의 수를 구할 수 있다.
 * 각 피자에서 나올 수 있는 수들의 조합을 구하면 2M * 2N => 4 * 10^6 으로 1초내로 처리가 가능하다.
 *
 * 각 피자에서 나온 경우의 수를 구했으면, 한 피자에서 나올 수 있는 조합의 수를 map에 저장한다.
 * key는 피자의 크기가 될 것이고, 만약 이전에 구한 값이 있으면 경우의 수를 구해야 하기 때문에 +1해서 저장해둔다.
 *
 * 두번째 조합을 구하면서 고객이 요청한 크기에 맞는 경우의 수를 구하고, 두번째 피자 조각에 없으면 기존에 저장해둔 피자 크기와 조합하여 반환한다.
 *
 */

public class BOJ2632_피자판매 {


	private static int targetSize;//고객이 요구한 사이즈.
	private static int m;//m - 첫번째 피자의 조각 수
	private static int n;//n - 두번째 피자의 조각 수

	private static int[] mPizza;//첫번째 피자의 누적합
	private static int[] nPizza;//두번째 피자의 누적합.
	private static Map<Integer, Integer> pizzaMap;//피자 크기 경우의 수


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		//고객이 원하는 크기 입력
		targetSize = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine());

		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		mPizza = new int[m * 2 + 1];
		nPizza = new int[n * 2 + 1];

		//첫번째 피자 입력
		for(int i = 1; i <= m; i++){
			mPizza[i] = mPizza[i + m] = Integer.parseInt(br.readLine());
		}

		//두번째 피자 입력.
		for(int i = 1; i <= n; i++){
			nPizza[i] = nPizza[i + n] = Integer.parseInt(br.readLine());
		}



		//첫번째 피자 누적합 계산.
		for(int i = 1; i < mPizza.length; i++){
			mPizza[i] += mPizza[i - 1];
		}
		//두번쨰 피자 누적합 계산.
		for(int i = 1; i < nPizza.length; i++){
			nPizza[i] += nPizza[i - 1];
		}

		//첫번째 피자에서 나올 수 있는 모든 경우를 저장.
		pizzaMap = new HashMap<>();
		int result = 0;

		//전체 크기는 먼저 구함 - 아래 반복문에서 전체 크기까지 구하도록 설계하면 중복이 발생
		//따라서 아래 반복문에서 조합을 구할떄는 전체크기 - 1 길이까지만 구하도록 함.
		if(mPizza[m] == targetSize) result++;
		//타켓 사이즈보다 작으면 맵에 넣어서 두번째 피자랑 조합해봄.
		if(mPizza[m] < targetSize) pizzaMap.put(mPizza[m], 1);

		//시작점,
		for(int i = 1; i <= m; i++){
			//연속된 피자의 길이
			for(int j = i; j < i + m - 1; j++){

				int tempSize = mPizza[j] - mPizza[i - 1];
				//해당 사이즈가 고객이 원한 크기면 경우의수에 추가.
				if(tempSize >= targetSize){
					if(tempSize == targetSize) result++;
					//해당 사이즈가 고객이 원한 크기보다 크면 패스.
					continue;
				}
				pizzaMap.put(tempSize, pizzaMap.getOrDefault(tempSize, 0) + 1);
			}
		}

		if(nPizza[n] == targetSize) result++;
		//타켓 사이즈보다 작으면 첫번쨰 피자와 조합.
		if(nPizza[n] < targetSize) result += pizzaMap.getOrDefault(targetSize - nPizza[n], 0);

		//두번째 피자.
		for(int i = 1; i <= n; i++){
			for(int j = i; j < i + n - 1; j++){

				int tempSize = nPizza[j] - nPizza[i - 1];

				//해당 사이즈가 고객이 원한 크기면 경우의수에 추가.
				if(tempSize >= targetSize){
					if(tempSize == targetSize) result++;
					//해당 사이즈가 고객이 원한 크기보다 크면 패스.
					continue;
				}

				//첫번째 피자와 조합해서 원하는 사이즈가 있으면 반환하여 누적
				result += pizzaMap.getOrDefault(targetSize - tempSize, 0);
			}
		}

		System.out.println(result);
	}
}
