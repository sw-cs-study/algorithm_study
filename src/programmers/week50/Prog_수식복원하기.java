package programmers.week50;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 아이디어
 *
 * 구현
 *
 * 나올수있는 진법은 2~9이다.
 * 주어진 문제에 있는 수식에 나와있는 숫자를 보고, 가능한 진법 후보를 구한 후,
 * 해당 진법으로 변환해서 계산해본다.
 * 여러개이면 ?, 명확하게 떨어지면 해당 진법으로 계산한 결과를 작성한다.
 */
public class Prog_수식복원하기 {


	private static boolean[] notationCheck;//가능한 진법 후보군 배열
	private static List<String> result;//최종 답안.

	//진법 후보군 탐색.
	private static void findNotation(String[] expressions){


		int maxValue = 0;
		for(String expression : expressions){

			for(int i = 0; i < expression.length(); i++){
				char chr = expression.charAt(i); //각 문자 하나씩 꺼내기.

				//숫자가 아니면 패스.
				if(!Character.isDigit(chr)) continue;

				maxValue = Math.max(maxValue, Character.getNumericValue(chr));

			}
		}

		//maxValue 이하의 수는 전부 불가.
		for(int i = maxValue; i >= 2; i--){
			notationCheck[i] = false;
		}


	}

	//진법 정보를 받아서 숫자 계산.
	private static int changeNotation(int notation, String num){

		num = num.trim();
		if(num.contains("X")) return -1;

		int returnValue = 0;
		for(int i = 0; i < num.length(); i++){

			char chr = num.charAt(i);
			returnValue += Character.getNumericValue(chr) * (int) Math.pow(notation, num.length() - i - 1);

		}

		return returnValue;
	}

	//수식을 받아서 계산하는 메서드
	private static int[] formulaCal(int notation, String formula){

		int[] returnArray = new int[2]; //0 : 왼쪽, 1: 오른쪽.

		//=을 기준으로 split
		String[] split = formula.split("=");

		String[] opSplit;
		int left = 0;
		int right = changeNotation(notation, split[1]);

		//+를 기준으로 split
		if(split[0].contains("+")){
			opSplit = split[0].split("[+]");
			left = changeNotation(notation, opSplit[0]) + changeNotation(notation, opSplit[1]);
		}

		else{
			opSplit = split[0].split("-");
			left = changeNotation(notation, opSplit[0]) - changeNotation(notation, opSplit[1]);
		}

		returnArray[0] = left;
		returnArray[1] = right;

		return returnArray;
	}

	//X 표시된 수식을 찾고 특정 진법이 맞는지 확인.
	private static boolean changeExpressions(int notation, String[] expressions){

		for(int i = 0; i < expressions.length; i++){

			String expression = expressions[i];

			//X가 들어간 수식은 패스.
			if(expression.contains("X")) continue;

			int[] formulaResult = formulaCal(notation, expression);

			if(formulaResult[0] != formulaResult[1]) return false;
		}


		return true;
	}

	//10진법 -> x 진법 변환
	private static String convert(int notation, int num){

		if(num == 0) return "0";

		StringBuilder sb = new StringBuilder();

		while(num > 0){
			sb.append(num % notation);
			num /= notation;
		}


		return sb.reverse().toString();
	}

	//진법 정보를 받아서 특정문자열 값 변환 - X가 포함된 문자열대상으로 가능한 모든 진법으로 계산했을때,
	//다른 결과가 여러개이면 ?, 결과가 하나이면 해당 값.
	private static void changeResult(String[] expressions){


		for(int i = 0; i < expressions.length; i++){

			String temp = expressions[i];

			if(!temp.contains("X")) continue;

			Set<String> flag = new HashSet<>();
			for(int j = 2; j < notationCheck.length; j++){

				if(!notationCheck[j]) continue;

				//각 진법에 대해 계산 돌려보기.
				int[] cals = formulaCal(j , temp);

				flag.add(convert(j, cals[0])); //나올수 있는 경우 다 추가.
			}

			//1보다 크면 중복되는게 여러개
			if(flag.size() > 1) {
				//새로운 값과 이전값이 다르면 ?로 치환.
				result.add(temp.replace("X", "?"));
			}

			//나올수 있는 수가 1개면 해당 수로 저장.
			else{
				//끝까지 다 돌았는데도 값이 모두 동일하다면 해당 값으로 치환.
				result.add(temp.replace("X", String.valueOf(flag.iterator().next())));
			}

		}

	}

	//로직 - 특정진법이 불가능 하면 패스.
	private static void logic(String[] expressions){

		int resultNotation = 0;

		//최종 후보군 구하기.
		for(int i = 2; i < notationCheck.length; i++){

			//후보군에 없으면 패스.
			if(!notationCheck[i]) continue;

			//false 이면 수식중에 불가능한 수식이 있어, 해당 진법 불가능.
			if(!changeExpressions(i, expressions)){
				notationCheck[i] = false;
			}
		}


		//최종 후보군으로 각 수직 검증.
		changeResult(expressions);
	}


	public String[] solution(String[] expressions) {

		notationCheck = new boolean[10]; // 2~9 인덱스만 사용.
		for(int i = 2; i < notationCheck.length; i++){
			notationCheck[i] = true;
		}

		result = new ArrayList<>();


		findNotation(expressions); //후보군 선정.

		logic(expressions);

		return result.toArray(new String[0]);
	}

	public static void main(String[] args){
		 Prog_수식복원하기 p = new Prog_수식복원하기();

		 String[] expressions1 = {"14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"};
		 System.out.println(Arrays.toString(p.solution(expressions1)));

		String[] expressions2 = {"1 + 1 = 2", "1 + 3 = 4", "1 + 5 = X", "1 + 2 = X"};
		System.out.println(Arrays.toString(p.solution(expressions2)));

		String[] expressions3 = {"10 - 2 = X", "30 + 31 = 101", "3 + 3 = X", "33 + 33 = X"};
		System.out.println(Arrays.toString(p.solution(expressions3)));

		String[] expressions4 = {"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "5 - 5 = X"};
		System.out.println(Arrays.toString(p.solution(expressions4)));

		String[] expressions5 = {"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "8 + 4 = X"};
		System.out.println(Arrays.toString(p.solution(expressions5)));

	}
}
