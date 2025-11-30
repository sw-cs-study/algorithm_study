package baekjoon.week34;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 이분탐색 + 투 포인터.
 */
public class BOJ15823_카드팩구매하기 {


	private static int N;//카드수
	private static int M;//카드 팩 수.
	private static int result;
	private static int[] cards;//카드 종류
	private static int[] cardCheck;//하나의 팩에서 이미 뽑은 카드인지 확인하기 위한 배열 - 매번 초기화 하면 낭비라, 카드 팩번호로 구분.


	//이분탐색 -> 구하고자 하는 것은 카드 팩에 담길수 있는 최대 카드수이므로 upper bound를 사용해서 상한선을 구한다.
	private static void upperBound(int start, int end){

		int mid;
		while(start <= end){

			mid = (start + end) / 2;

			//true 면 가능하다는 뜻으로, 해당 값을 저장하고, 카드 수를 늘려보기.
			if(cardPack(mid)){
				result = Math.max(result, mid);
				start = mid + 1;
			}
			else{
				end = mid - 1;
			}
		}
	}

	//투포인터로 카드팩 구성해보기 -> 파라미터로 이분탐색을 통해서 구한 카드팩내 카드수 전달.
	private static boolean cardPack(int targetCardNum){

		//만들어진 카드팩 개수.
		int packCount = 0;
		int currentPack = 0;// 투포인터로 카드 팩 구성하면서 중복 종류 제거시에 사용.
		cardCheck = new int[500001]; //카드

		int start = 1;
		for( ;start < cards.length; start++){

			int left = start;
			int right = start;

			currentPack++;

			cardCheck[cards[left]] = currentPack;//처음 선택한 카드 현재 팩번호로 체크 표시

			//반복문 돌면서 투포인터로 팩 구성
			while(true){

				//현재까지 구성된 수로 팩이 구성되었으면 카드팩 개수 증가시키고 종료
				if((right - left + 1) == targetCardNum){
					packCount++;
					break;
				}

				//왼쪽 값이 오른쪽 포인터를 넘어가거나(같은 종류 카드가 나오면 왼쪽 값을 하나 증가시키는데, 이러면 넘어가는 경우 발생)
				//오른쪽 값이 카드 배열을 넘어가면 종료.
				if(left > right || right + 1 == cards.length) break;

				//중복 카드가 있으면 왼쪽 값 줄이고 탐색이어서 진행.
				if(cardCheck[cards[right + 1]] == currentPack){
					cardCheck[cards[left]] = 0;
					left++;
					continue;
				}

				//위의 조건에 걸리지 않았으면 오른쪽 카드 뽑기.
				cardCheck[cards[right + 1]] = currentPack;
				right++;
			}

			//다음 팩 탐색을 위해, start값을 right로 함 -> for문의 반복문에서 +1 될 예정.
			start = right;

		}

		//모든 카드를 탐색했을때, 팩의 수가 M이면 성공, 아니면 실패.
		//카드 팩은 정확히 M개여야 하지만, 구성 불가한 경우는 문제에 없기 때문에 아래와 같이 구성하여,
		//목표 카드팩수와 같거나 크면 카드팩당 카드수를 늘려서 구하도록 한다.
		return packCount >= M;

	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		cards = new int[N + 1];

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			cards[i] = Integer.parseInt(st.nextToken());
		}

		// 번호는 50만까지 가능.
		result = 0;

		upperBound(1, N / M);

		System.out.println(result);
	}
}
