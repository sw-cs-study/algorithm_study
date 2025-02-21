package baekjoon.week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 스택을 이용한 풀이 - 그리디
 */
public class BOJ2374_같은수 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());

		Stack<Integer> stack = new Stack<>();
		int maxValue = 0;
		long result = 0;//연산 횟수.

		for (int i = 0; i < n; i++){
			int input = Integer.parseInt(br.readLine());

			maxValue = Math.max(maxValue, input); // 스택에 있는 값보다 작은 값이 들어와 서 끝났을때, 남은 스택 처리를 위해, 최대 값 저장.

			//스택이 비어있지 않고, 마지막 값이 새로 들어온 값보다 작으면 내부 값을, 새로 들어온 값으로 만들어야 함.
			if(!stack.isEmpty() && stack.peek() < input){

				//연산 횟수 구하기 - 수가 크기 때문에 biginteger 이용
				long count = 0;

				//스택 돌면서, 새로 들어온 값과 동일하게 만들기
				//이 과정에서 이전에 있던 값들은 스택에서 빠짐 - 어차피 새로들어온 값으로 맞췄기 때문에, 이후 연산에서는 새로 들어온 값을 바꾸면
				//다 바뀌는 것이므로 더이상 스택에 넣어서 연산할 필요 없음
				//스택이 비어있지 않을떄까지 반복.
				while(!stack.isEmpty()){

					int popValue = stack.pop();
					int peekValue = stack.isEmpty() ? input : stack.peek(); //스택이 비었으면 새로 들어오는 값, 비어있지 않으면 다음 값.

					count += peekValue - popValue;

				}

				result += count;
			}

			//새로 들어온 값을 스택에 넣기.
			stack.add(input);

		}

		//스택에 남은 값 처리.
		while(!stack.isEmpty()){

			int popValue = stack.pop();
			int peekValue = stack.isEmpty() ? maxValue : stack.peek(); //스택이 비었으면 새로 들어오는 값, 비어있지 않으면 다음 값.

			result += peekValue - popValue;
		}

		System.out.println(result);

	}
}
