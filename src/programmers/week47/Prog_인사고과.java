package programmers.week47;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 아이디어
 * 그리디
 *
 * 근무태도를 기준으로 내림차순, 동료평가를 기준으로 오름차순하게 되면, 동료평가 하나로만 판단해서 인센티브 대상이 아닌 인원을 거를 수 있음.
 *
 * (추가)
 * 타켓의 점수로만 비교해서 확인하는 식으로 했는데, 이렇게 되면 합계 점수는 같지만, 완호가 인센티브에서 제외되는 경우를 파악할 수 없음.
 */
public class Prog_인사고과 {

	private static class Node{

		int a, b, idx;

		public Node(int a, int b, int idx){
			this.a = a;
			this.b = b;
			this.idx = idx;
		}

	}

	public int solution(int[][] scores) {
		int answer = 0;

		int targetIdx = 0;
		int targetSum = scores[0][0] + scores[0][1];
		int maxValue = -1;

		List<Node> scoreList = new ArrayList<>();
		for(int i = 0; i < scores.length; i++){
			scoreList.add(new Node(scores[i][0], scores[i][1], i));
		}

		Collections.sort(scoreList, (o1, o2) -> {

			if(o1.a == o2.a){
				return o1.b - o2.b;
			}

			return o2.a - o1.a;
		});


		List<Integer> candidates = new ArrayList<>();

		//반복문 돌면서 후보자 리스트 만들기 - 인센티브 제외자는 제거.
		maxValue = scoreList.get(0).b; // 초기값은 첫번째 값 동료평가 점수 넣기.
		candidates.add(scoreList.get(0).a + scoreList.get(0).b);
		for(int i = 1; i < scoreList.size(); i++){

			//이전의 동료평가 값중 최대값(maxValue)보다 크거나 같아야 인센티브 받을 수 있음.
			if(maxValue > scoreList.get(i).b) {

				//완호가 포함되어있으면 바로 종료.
				if(scoreList.get(i).idx == targetIdx){
					return -1;
				}

				continue;
			}

			maxValue = Math.max(maxValue, scoreList.get(i).b);
			candidates.add(scoreList.get(i).a+ scoreList.get(i).b);
		}

		//후보자 군 정렬
		candidates.sort(Comparator.reverseOrder());

		boolean flag = false;
		for(int i = 0; i < candidates.size(); i++){

			//완호의 점수가 나올때까지 더하기.
			if(targetSum == candidates.get(i)) break;

			answer++;
		}


		return answer + 1;
	}

	public static void main(String[] args){

		Prog_인사고과 p = new Prog_인사고과();

		int[][] scores = {{2,2},{1,4},{3,2},{3,2},{2,1}};

		System.out.println(p.solution(scores));
	}
}
