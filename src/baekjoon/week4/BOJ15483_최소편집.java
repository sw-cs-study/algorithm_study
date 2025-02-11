package baekjoon.week4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 편집 알고리즘의 대표 유형으로 LCS 개념을 알아야 함.
 *
 * 참고 : https://velog.io/@qwerty1434/%EB%B0%B1%EC%A4%80-15483%EB%B2%88-%EC%B5%9C%EC%86%8C-%ED%8E%B8%EC%A7%91
 */

public class BOJ15483_최소편집 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String startStr = br.readLine();
		String targetStr = br.readLine();


		//dp구성
		//dp[i][j]의 의미는 문자열1번의 i까지를 문자열2번의 j까지의 문자열로 만드는데 필요한 편집 횟수
		int[][] dp = new int[startStr.length() + 1][targetStr.length() + 1];

		//초기값 : 공백 문자열로 j번째까지의 문자열을 만드는데 드는 비용은 j이다(추가만 하면 되니까)
		for(int i = 0; i <= targetStr.length(); i++){
			dp[0][i] = i;
		}

		//초기 값 : i번째까지의 문자열로 공백문자열을 만드는데 드는 비용은 전부 삭제하는 비용임.
		for(int i = 0; i <= startStr.length(); i++){
			dp[i][0] = i;
		}

		//dp 2차원 배열
		for(int i = 1; i <= startStr.length(); i++){
			for(int j = 1; j <= targetStr.length(); j++){

				//1. 마지막 문자열이 같다면,변경할것이 없기 떄문에 이전단계까지 만드는데 드는 횟수를 가져옴
				if(startStr.charAt(i - 1) == targetStr.charAt(j - 1)){
					dp[i][j] = dp[i - 1][j - 1];
				}

				else {
					//문자열이 다르면 아래의 경우중에 더 작은 쪽으로 업데이트 하면됨.
					//삽입 : dp[i][j - 1] + 1;
					//삭제 : dp[i - 1][j] + 1;
					//교체 : dp[i - 1][j - 1] + 1;
					dp[i][j] = Math.min(dp[i][j - 1] + 1, Math.min(dp[i - 1][j] + 1, dp[i - 1][j - 1] + 1));
				}
			}
		}

		System.out.println(dp[startStr.length()][targetStr.length()]);
	}
}
