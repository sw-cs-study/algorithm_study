package baekjoon.week24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 아이디어
 * 그리디 + 구현
 * 뽑은 수를 z로 바꾼다.
 * 수가 가장 커질라면, 자리수가 가장 큰것을 z로 바꿔야 한다.
 * 이를 위해서, 각 문자의 자리수가 큰 것부터 선정한다.
 * 큰 것부터 K개를 선정했으면, 문자열을 돌면서 해당 하는 문자를 Z로 변경하고, 이를 더해준다.
 *
 * (추가)
 * 자리수를 계산할때 단순 수로하면 너무 크기 때문에 모든 문자별로, 문자열 최대 50길이에 맞춰서 수의 자리를 저장하는 배열을 만들고,
 * 증가 폭을 계산한다.
 * 각 자리별로 최대 증가 가능한 값은 36이므로, 36으로 나눈 몫을 그 다음 자리로 올려준다.
 * 나머지는 그대로 현재 자리에 둔다.
 *
 * 이 배열을 이용해서 정렬을 하고,
 */

public class BOJ1036_36진수 {

	//각 36진수 수별 자리 저장.
	private static class Node{

		int num;
		int[] loc; //각 자리수 별 증가량.

		public Node(int num, int[] loc){
			this.num = num;
			this.loc = loc;
		}
	}


	private static int N;//문자열 수.

	private static int K;//고를 숫자 갯수.

	private static String[] originStrArray;//원본 문자열.

	private static Node[] nodes;//각 문자별 노드

	//모든 문자 돌면서 자리수 증가폭 배열 업데이트
	private static void increaseValue(){

		for (String str : originStrArray){

			int len = str.length();
			for (int i = 0; i < len; i++){

				char word = str.charAt(i);

				//A ~ Z
				if (word >= 'A'){
					nodes[word - 'A' + 10].loc[len - i - 1] += 'Z' - word;
				}
				//0 ~ 9일때,
				else {
					int tmpIdx = Character.getNumericValue(word);
					nodes[tmpIdx].loc[len - i - 1] += 35 - tmpIdx;
				}
			}
		}
	}

	//증가량 값들을 확인하면서, 자리수를 올려주는 처리.
	private static void valueUpdate(){

		for (Node node : nodes){

			int[] tempLoc = node.loc;
			for (int i = 0; i < tempLoc.length - 1; i++){

				tempLoc[i + 1] += tempLoc[i] / 36; //몫은 다음 자리수에 올려줌.
				tempLoc[i] = tempLoc[i] % 36; //나머지는 현재 자리에서의 증가폭
			}
		}

	}

	//정렬된 알파벳중 K개를 찾고, 문자열 업데이트
	private static void originStrUpdate(){

		for (int i = 0; i < K; i++){

			char selectChr = nodes[i].num < 10 ?
				String.valueOf(nodes[i].num).charAt(0) :
				String.valueOf((char)(nodes[i].num - 10 + 'A')).charAt(0);

			for (int j = 0; j < N; j++){
				originStrArray[j] = originStrArray[j].replace(selectChr, 'Z');
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//각 문자열 자리수 배열 초기화
		nodes = new Node[36];
		for (int i = 0; i < 36; i++){
			nodes[i] = new Node(i, new int[52]);
		}

		N = Integer.parseInt(br.readLine());

		originStrArray = new String[N];
		for (int i = 0; i < N; i++){
			originStrArray[i] = br.readLine();
		}

		K = Integer.parseInt(br.readLine());

		increaseValue(); // 증가폭 구하기
		valueUpdate(); // 구한 증가폭에서 자리수 올려주기.

		//구한 증가폭으로 정렬 - 내림차순 정렬해야 함.
		Arrays.sort(nodes, (node1, node2) -> {

			//역순으로 돌면서 수가 더크면 반환.
			for (int i = 51; i >= 0; i--){

				if(node2.loc[i] < node1.loc[i]) return -1;

				if(node2.loc[i] > node1.loc[i]) return 1;

			}
			return 0;
		});

		//정렬한 것들중 K개의 알파벳 선별
		//기존 문자열에서 선별한 알파벳들을 전부 Z로 변경
		originStrUpdate();

		//모든 문자열의 합 계산.
		int[] result = new int[52];

		for (String str : originStrArray){

			int len = str.length();

			for (int i = 0; i < len; i++){

				char word = str.charAt(i);

				//A ~ Z
				if (word >= 'A'){
					result[len - i - 1] += word - 'A' + 10;
				}
				//0 ~ 9일때,
				else {
					int tmpIdx = Character.getNumericValue(word);
					result[len - i - 1] += tmpIdx;
				}

			}
		}

		int lastIdx = 0;
		//자리수 업데이트.
		for (int i = 0; i < result.length - 1; i++){

			if(result[i] == 0) continue;

			lastIdx = i;

			result[i + 1] += result[i] / 36; //몫은 다음 자리수에 올려줌.
			result[i] = result[i] % 36; //나머지는 현재 자리에서의 증가폭


			if (result[i + 1] > 0) lastIdx = i + 1;
		}

		//최종 결과 출력
		StringBuilder resultStr = new StringBuilder();

		for (int i = lastIdx; i >= 0; i--) {
			resultStr.append(
				result[i] < 10 ?
					String.valueOf(result[i]).charAt(0) :
					String.valueOf((char)(result[i] - 10 + 'A')).charAt(0)
			);
		}

		System.out.println(resultStr);
	}

}
