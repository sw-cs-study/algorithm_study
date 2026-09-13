package programmers.week52;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prog_주사위고르기 {


	private static int result; //승리 횟수.
	private static int resultBit; //승리 할 수 있을때의 조합.
	private static Map<Integer, Integer> visited;//조합 방문 처리,
	//완전 탐색.
	private static void dfs(int[][] dice, int currentBit, int count, int idx){


		// n/2개를 다 골랐으면 승리 횟수 계산.
		if(count == dice.length / 2){

			//중복 비트면 필요 없음
			if(visited.containsKey(currentBit)) return;

			Map<Integer, Integer> tempA = winInfo(dice, currentBit);
			Map<Integer, Integer> tempB = winInfo(dice, (~currentBit) & ((1 << dice.length) - 1));

			//두 합계 분포 비교.
			int[] temp = compareSum(currentBit, (~currentBit) & ((1 << dice.length) - 1), tempA, tempB);

			if(temp[0] > result){
				result = temp[0];
				resultBit = temp[1];
			}
			return;
		}

		for(int i = idx; i < dice.length; i++){

			int nextBit = currentBit | (1 << i);

			dfs(dice, nextBit, count + 1, i + 1);
		}
	}

	//각 합계 분포 구하기.
	private static Map<Integer, Integer> winInfo(int[][] dice, int currentBit){

		int tempN = dice.length;

		visited.put(currentBit, 1); //방문처리.

		Map<Integer, Integer> sumMap = new HashMap<>();//합계 분포

		//주사위 선택 리스트 만들기.
		List<Integer> indexList = new ArrayList<>();
		for(int i = 0; i < tempN; i++){

			if((currentBit & (1 << i)) == 0) continue;
			indexList.add(i);
		}

		recursiveSum(dice, 0,  indexList,0, sumMap); //A

		return sumMap;

	}

	//합계 구하기 - type이 1이면 비트가 1인 것들만 확인, 0이면 0인것들만 확인.
	private static void recursiveSum(int[][] dice, int tempSum, List<Integer> indexList, int count, Map<Integer, Integer> map){

		//5개 다 골랐으면 map에 저장.
		if(count == indexList.size()){
			map.put(tempSum, map.getOrDefault(tempSum, 0) + 1);
			return;
		}

		for(int i : dice[indexList.get(count)]){
			int nextSum = tempSum + i;
			recursiveSum(dice, nextSum, indexList, count + 1, map);
		}
	}

	//두 승리 분포 비교 후,이긴쪽 승리수와 해당 비트 반환
	//return[0] => 승리수, return[1] => 비트
	private static int[] compareSum(int bitA, int bitB, Map<Integer, Integer> mapA, Map<Integer, Integer> mapB){

		int sumA = 0, sumB = 0;

		for(int keyA : mapA.keySet()){
			for(int keyB : mapB.keySet()){

				if(keyA == keyB) continue;

				if(keyA > keyB){
					sumA += mapA.get(keyA) * mapB.get(keyB);
				}
				else if(keyA < keyB){
					sumB += mapA.get(keyA) * mapB.get(keyB);
				}
			}
		}

		return new int[]{
			sumA >= sumB ? sumA : sumB,
			sumA >= sumB ? bitA : bitB
		};
	}


	public int[] solution(int[][] dice) {
		List<Integer> answer = new ArrayList<>();
		visited = new HashMap<>();
		result = -1;
		resultBit = 0;

		dfs(dice, 0, 0, 0);

		for(int i = 0; i < dice.length; i++){

			if((resultBit & (1 << i)) == 0) continue;
			answer.add(i + 1);
		}


		return answer.stream().mapToInt(Integer::intValue).toArray();
	}

	public static void main(String[] args){



		Prog_주사위고르기 p = new Prog_주사위고르기();

		int[][] dice1 = {{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}};
		System.out.println(Arrays.toString(p.solution(dice1)));

		int[][] dice2 = {{1, 2, 3, 4, 5, 6}, {2, 2, 4, 4, 6, 6}};
		System.out.println(Arrays.toString(p.solution(dice2)));

		int[][] dice3 = {{40, 41, 42, 43, 44, 45}, {43, 43, 42, 42, 41, 41}, {1, 1, 80, 80, 80, 80}, {70, 70, 1, 1, 70, 70}};
		System.out.println(Arrays.toString(p.solution(dice3)));

	}
}
