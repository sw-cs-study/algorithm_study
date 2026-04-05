package baekjoon.week42;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

/**
 * 아이디어
 *
 * 롤링 해시(라빈 카프 알고리즘.)
 *
 * 해시를 위해 31을 기준으로 잡음,
 * 소문자 개수가 0 ~ 25, 26개라 27로 잡하도 되긴 함.
 * 자바에서 표준처럼 쓰이는 31로 처리.
 * 문자열을 수로 변환했을때 안겹치기만 하면됨.
 */

public class BOJ10840_구간성분 {



	private static long[] hashValue;//각 문자열 값 저장(31을 기준으로 하고, a이면 31^0, b이면 31^1....)


	//초기 해시값들 저장.
	private static void init(){

		hashValue[0] = 1;

		for(int i = 1; i < 26; i++){
			hashValue[i] = hashValue[i - 1] * 31;
		}
	}

	//문자열 해싱 처리 -> 숫자로 표기.
	private static long hashing(String str, int len){


		int[] count = new int[26];//각 문자가 몇개 나왔는지 확인.
		//해당 문자에서 나온 개수 세기.
		for(int i = 0; i < len; i++){
			char chr = str.charAt(i);
			count[chr - 'a']++;
		}

		//나온 문자수와, 각 문자에 대응되는 값을 곱해서 더하여, 최종적인 수를 만듦.
		long resultHash = 0;
		for(int i = 0; i < 26; i++){
			resultHash += count[i] * hashValue[i];
		}


		return resultHash;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String temp1 = br.readLine();
		String temp2 = br.readLine();


		hashValue = new long[26];
		init();

		//두 문자열 길이를 확인해서 항상 shortStr변수에 더 짧은 변수가 오도록 함 - 구간 성분의 길이는 같아야 해서, 작은 쪽을 기준으로 잡음.
		String shortStr = temp1.length() <= temp2.length() ? temp1 : temp2;
		String longStr = temp1.length() > temp2.length() ? temp1 : temp2;

		//짧은 문자를 기준으로 탐색할 서브 문자열의 길이를 정함.
		//구한 문자열은 set에 넣고, 다 구했으면 두 set을 비교하면서 한개라도 속해있는 것이 나오면 해당 구간성분이 가장긴 문자로 바로 반환함.

		HashSet<Long> shortStrHash = new HashSet<>();
		HashSet<Long> longStrHash = new HashSet<>();


		int result = 0;


		loop:
		for(int len = shortStr.length(); len > 0; len--){

			shortStrHash = new HashSet<>();
			longStrHash = new HashSet<>();

			//긴 문자열에서 주어진 길이로 나올 수 있는 모든 문자열
			long currentHash = hashing(longStr, len); // 시작 값
			longStrHash.add(currentHash);

			for(int i = 1; i <= longStr.length() - len; i++){

				//이전칸은 빼고, 다음칸은 추가.
				currentHash = currentHash - hashValue[longStr.charAt(i - 1) - 'a'] + hashValue[longStr.charAt(i + len - 1) - 'a'];

				//해시에 없으면 추가.
				if(longStrHash.contains(currentHash)) continue;

				longStrHash.add(currentHash);

			}


			//짧은 문자열에서 주어진 길이로 나올 수 있는 모든 문자열.
			currentHash = hashing(shortStr, len); //시작 값.
			shortStrHash.add(currentHash);
			for(int i = 1; i <= shortStr.length() - len; i++){

				//이전칸은 빼고, 다음칸은 추가.
				currentHash = currentHash - hashValue[shortStr.charAt(i - 1) - 'a'] + hashValue[shortStr.charAt(i + len - 1) - 'a'];

				//해시에 없으면 추가.
				if(shortStrHash.contains(currentHash)) continue;

				shortStrHash.add(currentHash);

			}


			//해시 비교
			for(long temp : longStrHash){

				//속해있지 않으면 패스.
				if(!shortStrHash.contains(temp)) continue;

				//하나라도 있다면 현재 구간이 최대 구간임.
				result = len;
				break loop;
			}

		}

		System.out.println(result);


	}
}


