package baekjoon.week19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 아이디어
 * 그리디
 *
 * 음수까지 있는 상태에서 최대의 수가 나오는 경우는 아래와 같다.
 *
 * 1. 2 이상의 수끼리는 곱하는게 이득
 * 2. 큰수 끼리 곱하는게 이득
 * 3. 음수는 음수끼리 곱함.
 * 4. 가장 작은 음수는 0과 곱합.
 *
 * 대략적으로 위의 조건을 따른다.
 *
 * 우선 큰수부터 하나씩 찾아간다.
 * 이를 위해서 배열을 정렬하고, 스택에 넣는다.
 * 스택에 이미 수가 들어가 있고, 다음으로 뽑은 수가 2이상인 양수라면 두 수를 곱해서 최종 결과에 더해준다.
 * 0이라면 0의 개수를 세어둔다.
 * 음수라면 양수의 경우와 마찬가지로, 스택에 들어가있는 수가 음수라면 두 수를 곱해준다.
 *
 * 하나의 배열로는 안되고, 두개의 배열을 사용한다.
 * 작은순으로 나열한 배열,
 * 큰 순서로 나열한 배열.
 *
 * 정렬한 배열을 만들기전에는 0과 1의 개수를 세어준다.
 * 0은 음수가 있을때 지우는 용도로 사용되고, 1은 곱한것보다 더했을때 이득이므로 최종결과에 더해준다.
 *
 * (수정)
 * 굳이 2개의 배열을 사용할 필요는 없다.
 * 하나의 배열과 하나의 스택으로도 처리 가능함.
 * 내림차순으로 먼저 정렬한다
 * 1. 양수인경우
 * => 스택이 비었으면 스택에 넣고. 스택이 비어있지 않으면 꺼내서 곱한 후에 더한다.
 * 2. 음수인 경우
 * => 음수가 나오면, 이전에 스택에 양수가 들어있을수도 있으니, 모든 값을 꺼내서 전부 결과에 더해준다.
 * => 음수는 현재 값을 선택하고 남은 수의 개수를 확인해야 한다.
 *    2-1) 남은 수의 개수가 짝수
 *    		=> 정렬했기 때문에 남은 수는 전부 음수이며, 현재 수가 음수중에 제일 큰수로, 0이 있으면 패스, 0이 없으면 결과에 더해준다.
 *    	    => 남은 수들은 뒤에서부터 순차적으로 곱하고 더해준다.
 *    2-2) 남은 수의 개수가 홀수
 *         => 본인의 수를 포함하면 짝수이므로, 모든 수가 두개씩 짝지어진다.
 *         => 인덱스 맨 끝부터 순회하며, 처리한다.
 */
public class BOJ1744_수묶기 {

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int result = 0;
		int zeroCount = 0;

		List<Integer> numList = new ArrayList<>(); //양수

		for (int i = 0; i < N; i++) {
			int inputValue = Integer.parseInt(br.readLine());

			if (inputValue == 0) zeroCount++; //0은 나중에 음수를 제거하는 용도로 쓰일 수 있음.
			else if (inputValue == 1) result++; //1은 결과에 그냥 더함.
			else numList.add(inputValue);
		}

		numList.sort((num1, num2) -> num2 - num1);

		Stack<Integer> numStack = new Stack<>();
		int idx = 0;


		//양수 먼저 처리.
		while (idx < numList.size() && numList.get(idx) > 0){

			int num = numList.get(idx++);

			//스택이 비어있으면 넣기.
			if (numStack.isEmpty()){
				numStack.add(num);
				continue;
			}

			//스택이 비어있지 않으면 꺼내서 곱하고 더하기.
			result += numStack.pop() * num;

		}

		//스택에 남은 값을 더함.
		while (!numStack.isEmpty()){
			result += numStack.pop();
		}

		//음수처리

		int remain = numList.size() - idx; //남은 개수
		// 남은 수가 짝수이면 뒤에서 부터 곱함.
		if (remain % 2 == 0) {

			for (int i = numList.size() - 1; i >= idx; i--) {

				int num = numList.get(i);

				//스택이 비어있으면 넣기.
				if (numStack.isEmpty()) {
					numStack.add(num);
					continue;
				}

				//스택이 비어있지 않으면 꺼내서 곱하고 더하기.
				result += numStack.pop() * num;
			}
		}

		//홀수 이면 - idx 번째 수 처리 필요.
		else{
			if (zeroCount == 0) result += numList.get(idx);
			for(int i = numList.size() - 1; i > idx; i--){
				int num = numList.get(i);

				//스택이 비어있으면 넣기.
				if (numStack.isEmpty()) {
					numStack.add(num);
					continue;
				}

				//스택이 비어있지 않으면 꺼내서 곱하고 더하기.
				result += numStack.pop() * num;
			}
		}

		System.out.println(result);
	}
}
