package programmers.week46;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * 아이디어
 * 구현
 *
 * A - B => 결과1 , B - C => 결과1 이라면 C는 A의 후보가 된다.
 * 숫자야구에서 결과는 수의 분포를 말한다.
 * 위의 케이스라면 C는 A가 될수도 있지만, 아닐수도 있기 떄문에,
 * 아래 문제에서도 나올수 있는 모든 경우를 리스트에 담은 후, 한번 submit 함수를 호출한 결과를 가지고,
 * 리스트 상에 후보가 되는 것들만 남기고 나머지는 전부 버린다.
 *
 */

public class Prog_숫자야구 {


	//리스트 안에 모든 케이스 담기.
	private static void setAllCase(List<Integer> caseList){

		//숫자가 겹치안도록 해서 리스트에 담기.
		for(int i = 1; i <= 9; i++){
			for(int j = 1; j <= 9; j++){
				if(i == j) continue;
				for(int x = 1; x <= 9; x++){
					if(i == x || j == x) continue;
					for(int y = 1; y <= 9; y++){

						if(i == y || j == y || x == y) continue;

						caseList.add(i * 1000 + j * 100 + x * 10 + y);
					}
				}
			}
		}
	}

	//리스트 안에 있는 모든 숫자를 야구 결과에 따라 자르기.
	private static List<Integer> getCandidate(List<Integer> caseList, int num, int strike, int ball) {

		List<Integer> tempList = new ArrayList<>();
		Set<Character> checkSet = new HashSet<>();
		String checkStr = String.valueOf(num);
		checkSet.add(checkStr.charAt(0));
		checkSet.add(checkStr.charAt(1));
		checkSet.add(checkStr.charAt(2));
		checkSet.add(checkStr.charAt(3));

		//반복문 돌면서 체크
		int tempStrike = 0;
		int tempBall = 0;

		for(int temp : caseList){

			String str = String.valueOf(temp);

			tempStrike = 0;
			tempBall = 0;

			for(int i = 0; i < 4; i++){

				char chr = str.charAt(i);

				//숫자와 자리가 같으면 스트라이크 추가.
				if(checkStr.charAt(i) == chr){
					tempStrike++;
				}

				//숫자가 포함되어있으면 볼 추가.
				else if(checkSet.contains(chr)){
					tempBall++;
				}

			}

			//두 조건이 같으면 후보임.
			if(strike == tempStrike && ball == tempBall){
				tempList.add(temp);
			}

		}

		return tempList;
	}

	public int solution(int n, Function<Integer, String> submit) {

		List<Integer> caseList = new ArrayList<>();
		setAllCase(caseList);

		//케이스가 1개 남으면 종료.
		while(caseList.size() > 1){

			int num = caseList.get(0);

			String result = submit.apply(num);

			int strike = Character.getNumericValue(result.charAt(0));
			int ball = Character.getNumericValue(result.charAt(3));

			caseList = getCandidate(caseList, num, strike, ball);
		}


		return caseList.get(0);
	}

}
