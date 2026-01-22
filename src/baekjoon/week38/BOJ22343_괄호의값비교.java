package baekjoon.week38;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 스택
 *
 * 괄호 문제는 스택을 이용하면 쉽게 해결가능
 * 단 해당 문제는 주어진 공식을 이용해서 계산을 해야 하는데,
 * 이 수가 최대 2^150만이 나올 수 있음
 * (문자열의 최대 개수는 3* 10^6인데, 가장 큰 경우가 ((())) 이러한 케이스임
 *  절반인 150만개가 여는 괄호, 절반이 닫는 괄호라면,
 *  2^150만승이 되어서 일반적인 숫자 자료형으로는 표현이 불가능함.
 * )
 *
 * 해당 문제는 잘못된 괄호케이스가 없음.
 *
 *(추가)
 *  여는 괄호의 수에 따라 지수를 결정
 *  예를 들면 (()(())) => 이러한 케이스가 있다면
 *
 *  첫번쨰로 만나는 ()의 경우, 앞에 여는 괄호가 1개 있기 떄문에 2^1
 *  두번째로 만나는 (())이 안의 괄호는 여는 괄호가 2개 있기 떄문에 2^2로 보는 것이다
 *  ()(()) => F[()] + F[(())] 이렇게 계산될 것이고
 *  (()(())) => 2 * (F[()] + F[(())])
 */

public class BOJ22343_괄호의값비교 {


	private static int maxExp; //지수 최대값
	private static int minExp; //지수 최소 값.
	private static int[] expArrays;


	//괄호 계산
	private static void bracketValue(String inputBracket, int type){

		int leftCount = 0;

		for(int i = 0; i < inputBracket.length(); i++){

			char bracket = inputBracket.charAt(i);

			//여는 괄호
			if(bracket == '('){

				leftCount++;
			}
			//닫는 괄호.
			else {
				leftCount--;

				if(inputBracket.charAt(i - 1) != '(') continue;

				//이전 괄호가 여는 괄호라면 해당하는 지수의 인덱스 배열에 추가
				expArrays[leftCount] += type;
			}

		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		StringBuilder result = new StringBuilder();
		expArrays = new int[1_500_100];

		for(int testCase = 1; testCase <= T; testCase++){

			Arrays.fill(expArrays,0);

			String firstBracket = br.readLine();
			String secondBracket = br.readLine();

			//첫번째 괄호문자열의 경우에는 1을 더하고, 두번째 괄호 문자열의 경우에는 -1을 해서,
			//상위 비트, 즉 큰수부터 확인해서 음수면 두번째 수가 더 큰수를 더 많이 가지고 있는 것으로 판단하여 대소 비교
			bracketValue(firstBracket, 1);
			bracketValue(secondBracket, -1);


			//0이 아닌 최상위 비트의 수가 뭐였는지 확인
			int check = 0;

			//캐리가 발생할 수 있기 때문에 인덱스 낮은쪽부터 돌면서 올려줘야 함.
			for(int i = 0; i < expArrays.length - 1; i++){

				//해당위치가 0이면 볼 필요 없음.
				if(expArrays[i] == 0) continue;


				int carry = expArrays[i] / 2;
				expArrays[i] = expArrays[i] % 2;
				expArrays[i + 1] += carry;

				//비트 올림이 완료된 후 현재값이 음수인지 양수인지체크.
				if(expArrays[i] == 0) continue;

				check = expArrays[i];
			}

			if(check == 0){
				result.append("=");
			}
			else if(check > 0){
				result.append(">");
			}
			else{
				result.append("<");
			}

			result.append("\n");
		}

		System.out.println(result);

	}
}
