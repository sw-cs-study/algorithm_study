package programmers.week51;

import java.util.ArrayList;
import java.util.List;

/**
 * 아이디어
 * 구현
 */

public class Prog_봉인된주문 {


	//ban정보를 숫자로 변환한 리스트(정렬필요)
	private static List<Long> banList;

	//알파벳 -> 숫자로 변환.
	private static long convertNum(String str){

		long returnNum = 0;
		for(int i = 0; i < str.length(); i++){

			char chr = str.charAt(i);

			returnNum += (long) (chr - 'a' + 1) * (long) Math.pow(26, str.length() -  i - 1);
		}


		return returnNum;

	}

	//숫자 -> 문자열로 변환
	//26진수 변환처럼 반복해서 나눗셈하여 처리
	private static String convertStr(long num){

		long tempNum = num;
		long remain = 0;
		StringBuilder returnStr = new StringBuilder();
		while(tempNum > 0){

			tempNum--; // 0~25체계로 맞추기.

			remain = tempNum % 26;
			tempNum = tempNum / 26;

			returnStr.append((char) ('a' + (remain) ));
		}

		return returnStr.reverse().toString();
	}


	public String solution(long n, String[] bans) {
		String answer = "";

		banList = new ArrayList<>();

		for(String ban : bans){

			banList.add(convertNum(ban));
		}

		banList.sort(Long::compare);

		//ban보다 작거나 같으면 +1식
		long tempN = n;
		for(long ban : banList){

			if(tempN < ban) continue;

			tempN++;
		}


		return convertStr(tempN);
	}
	public static void main(String[] args){

		Prog_봉인된주문 p = new Prog_봉인된주문();

		int n1 = 30;
		String[] bans1 = {"d", "e", "bb", "aa", "ae"};
		System.out.println(p.solution(n1, bans1));

		int n2 = 7388;
		String[] bans2 = {"gqk", "kdn", "jxj", "jxi", "fug", "jxg", "ewq", "len", "bhc"};
		System.out.println(p.solution(n2, bans2));
	}


}
