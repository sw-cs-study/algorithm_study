package programmers.week44;



import java.util.*;

/*
* 아이디어
* 완탐 -> 스테이지를 각 노드라고 봤을때, 다음 노드로 이동하는 경우는 번들을 사냐 안사냐로 갈리게 됨.
번들을 사면 해당하는 힌트권 개수를 누적하고, 해당 위치에 갔을떄 힌트권수에 해당하는 가격으로 스테이지를 클리어 하는 식으로 처리.
최종 스테이지에 도달하면, 최소 가격을 저장해두고, 다른 탐색시에는 최소가격을 넘어가면 굳이 볼필요 없음.
*/

class Prog_힌트스테이지 {

	private static int n;
	private static int totalCost;

	//idx는 현재 스테이지.
	private static void recursive(int stage, int sumCost,int[] hintArray, int[][] cost, int[][] hint){

		//현재 보유한 힌트권으로 클리어에 필요한 가격 계산 - 보유한 힌트권이 쓸수 있는 최대개수를 넘을수도 있음.
		int tmpCost = sumCost + cost[stage][Math.min(cost[stage].length - 1, hintArray[stage])];

		//현재까지 누적된 코스트가 총 코스트보다 크거나 같으면 종료
		if(tmpCost >= totalCost) return;


		//최종 스테이지를 넘어가면 저장하고 종료.
		if(stage >= n - 1) {
			totalCost = tmpCost;
			return;
		}



		//현재 힌트 번들을 사는 케이스.
		tmpCost += hint[stage][0]; //힌트 번들 가격 추가.
		for(int i = 1; i < hint[0].length; i++){
			hintArray[hint[stage][i] - 1]++;
		}



		recursive(stage + 1, tmpCost, hintArray, cost, hint);

		//추가한 정보 원복.
		tmpCost -= hint[stage][0];
		for(int i = 1; i < hint[0].length; i++){

			hintArray[hint[stage][i] - 1]--;
		}


		//현재 힌트 번들을 안사는 케이스.
		recursive(stage + 1, tmpCost, hintArray, cost, hint);

	}

	public int solution(int[][] cost, int[][] hint) {

		n = cost.length;

		totalCost = Integer.MAX_VALUE;


		recursive(0, 0, new int[n], cost, hint);


		return totalCost;
	}


	public static void main(String[] args){

		int[][] cost1 = {{160, 140, 120, 110, 60}, {290, 270, 260, 120, 10}, {160, 130, 120, 60, 20}, {160, 120, 80, 70, 20}, {110, 70, 60, 30, 20}};
		int[][] hint1 = {{40, 2, 3}, {40, 5, 3}, {20, 5, 4}, {50, 5, 5}};

		int[][] cost2 = {{110, 100, 90, 80, 70, 50, 10}, {170, 160, 150, 140, 130, 110, 30}, {260, 250, 190, 180, 170, 130, 100}, {170, 150, 120, 90, 60, 40, 30}, {220, 140, 110, 100, 70, 60, 50}, {290, 180, 150, 130, 100, 20, 10}, {110, 100, 90, 70, 50, 40, 30}};
		int[][] hint2 = {{40, 3, 4, 3}, {80, 3, 7, 4}, {40, 7, 6, 5}, {40, 7, 5, 5}, {50, 6, 7, 6}, {30, 7, 7, 7}};

		Prog_힌트스테이지 p = new Prog_힌트스테이지();


		System.out.println(p.solution(cost1, hint1));
	}
}