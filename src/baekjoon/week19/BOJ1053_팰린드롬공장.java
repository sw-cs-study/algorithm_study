package baekjoon.week19;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어
 * dp
 * 참고함 : https://velog.io/@thohohong/BOJ-1053-%ED%8C%B0%EB%A6%B0%EB%93%9C%EB%A1%AC-%EA%B3%B5%EC%9E%A5
 *
 * 문자열 길이를 이용해서 디피 배열을 만든다.
 * dp[i][j] => i번째부터 j번째문자열까지 팰린드롬으로 만들수 있는 최소 연산수를 저장한다.
 * 이때 중요한것은 문자열이 어떤 팰린드롬 형태인지는 알필요 없고, 중복되는 연산이 있다는 점이다.
 * 원하는거는 팰린드롬으로 만드는데 드는 연산수 이므로, 삽입 삭제, 교환등의 연산을 했냐 안했냐만 보면 된다.
 * 또한 삽입과 삭제는 같은 연산으로 생각해야 한다는 점이다.
 * 나타나는 팰린드롬의 가지수를 구하는 등 문자열의 형태가 중요하면 다르겠지만 여기서는 연산횟수이므로 결론적으로는 같다.
 * 예를 들면 abaa라는 문자열이 있을때, 삽입을 통해 aabaa로 팰린드롬을 만드는 연산수와, 삭제를 통해서 aba로 만드는 연산수는 같기 때문에,
 * 같은 것으로 보고 처리해야 한다.
 *
 * 추가로 주의할 점은 4번연산이다.
 * 4번연산은 시작시점에 바꾸는 것이 좋다.
 * 시작시점에 안바꾸면 1,2,3연산이 일어나서 4번이 의미가 없어진다.
 * 1,2,3연산이 처리된 후에는 4번은 의미가 없다.
 */


public class BOJ1053_팰린드롬공장 {

	private static char[] originStr;
	private static int[][] dp;


	//문자 교환(4번 연산.)
	private static void swap(int source, int target){

		char tempChar = originStr[source];
		originStr[source] = originStr[target];
		originStr[target] = tempChar;

	}

	//주어진 문자로 팰린드롬 연산수 구하기.
	private static int solve(){

		dp = new int[originStr.length][originStr.length];

		//초기값 설정
		//길이가 1,2인것들은 정해져있음,
		//1인 경우에는 그 자체로 팰린드롬이므로 0이고, 길이가 2인 경우는 두 문자가 같으면 0, 아닌 경우 연산을 1번만 하면 되기 때문에 1로 초기홯.ㅁ
		for(int i = 0; i < originStr.length - 1; i++){

			//두 문자가 다르면 1, 같으면 0
			dp[i][i + 1] = originStr[i] != originStr[i + 1] ? 1 : 0;
		}

		//반복문돌면서 점화식 수행.
		for(int size = 2; size < originStr.length; size++){
			for(int i = 0; i < originStr.length - size; i++){

				/**
				 * 점화식
				 * 오른쪽 삭제, 왼쪽삽입은 dp[x][y - 1]
				 * 왼쪽 삭제, 오른쪽 삽입은 dp[x + 1][y]
				 * 교환은 dp[x + 1][y - 1];
				 */

				//i번째에서 i+size번째까지 팰린드롬으로 만드는데 드는 최소 비용 저장.
				//오른쪽삭제 or 왼쪽삽입 / 왼쪽 삭제, 오른쪽 삽입 중에서 더 작은 쪽을 고름(연산을 한번 해야 하는 것이므로 +1 함.)
				dp[i][i + size] = Math.min(dp[i + 1][i + size] + 1,dp[i][i + size - 1] + 1);

				//교환은 확인이 필요함.
				if (originStr[i] == originStr[i + size]){
					//같은 경우는 교환 필요 없음.
					dp[i][i + size] = Math.min(dp[i + 1][i + size - 1], dp[i][i + size]);
 				}
				else {
					dp[i][i + size] = Math.min(dp[i + 1][i + size - 1] + 1, dp[i][i + size]);
				}

			}
		}

		return dp[0][originStr.length -  1];
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		originStr = br.readLine().toCharArray();

		int result = solve(); // 원래의 문자열로 팰린드롬 연산 수 구하기.


		//문자를 교환(4번연산)후에 팰린드롬 구하기.
		for(int i = 0; i < originStr.length - 1; i++){
			for(int j = i + 1; j < originStr.length; j++){

				//교환할 문자가 같으면패스
				if (originStr[i] == originStr[j]) continue;

				swap(i,j); //문자교환
				result = Math.min(result, solve() + 1); //최소 연산수로 업데이트.
				swap(i,j); //문자열 원복
			}
		}

		System.out.println(result);

	}
}
