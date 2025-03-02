package baekjoon.week6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * DP
 * 바텀업으로 해볼라 했는데 안풀림 ㅋㅋ
 * 전형적인 탑다운 문제인듯.
 *
 * 각 발의 위치와, 이동한 횟수를 저장해두고, 탐색중에 같은 경우를 보게 되면 해당 값을 반환하면 됨.
 *
 * (추가)
 * 두발이 같은 곳에 있으면 안되는 조건을 고민했는데, 생각해보면 이전에 있던 발로 누르는 것이 더 적은힘이 들기 때문에
 * 더 힘이 적게 드는 것을 선택하게 하면 됨.
 */

public class BOJ2342_DanceDanceRevolution {


	private static List<Integer> inputArray;//입력받은 위치
	private static int[][][] dp;//dp배열

	//힘 변환
	private static int convertPower(int prevPosition, int currentPosition){

		if(prevPosition == 0) return 2;

		int gap = Math.abs(currentPosition - prevPosition);

		if(gap == 0) return 1;
		if(gap == 1 || gap == 3) return 3;
		if(gap == 2) return 4;

		return -1;

	}

	//탐색할 dfs 메서드
	private static int dfs(int leftPosition, int rightPosition, int count){

		//이동횟수를 넘어갔으면 탐색불가로 0반환
		if(count == inputArray.size()) return 0;

		//목적지에 도달하지 않았지만, 해당위치를 이전에 방문한 적이 있다면 해당 값 반환
		if(dp[leftPosition][rightPosition][count] != -1) return dp[leftPosition][rightPosition][count];

		//입력으로 주어진 값으로 이동하는데, 왼발 오른발 중 더 적은 값으로 이동 가능한것으로 이동.
		dp[leftPosition][rightPosition][count] =
			Math.min(
				//왼발을 옮기는 경우
				dfs(inputArray.get(count), rightPosition, count + 1) + convertPower(leftPosition, inputArray.get(count)),
				//오른발을 옮기는 경우.
				dfs(leftPosition, inputArray.get(count), count + 1) + convertPower(rightPosition, inputArray.get(count))
			);

		return dp[leftPosition][rightPosition][count];
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		inputArray = new ArrayList<>();

		int input;
		while((input = Integer.parseInt(st.nextToken())) != 0){
			inputArray.add(input);
		}

		//왼발위치, 오른발 위치, 이동 횟수.
		dp = new int[5][5][inputArray.size() + 1];

		//맨처음 위치는 이동을 안했기 때문에 점수가 0, 아직 도달 한적 없는 것과 구분을 위해 초기값을 -1로 표기
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				for(int k = 0; k <= inputArray.size(); k++){
					dp[i][j][k] = -1;
				}
			}
		}

		System.out.println(dfs(0,0,0));

	}
}
