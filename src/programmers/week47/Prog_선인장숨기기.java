package programmers.week47;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

/**
 * 아이디어(참고함.)
 * 2차원 슬라이딩 윈도우
 *
 * 슬라이딩 윈도우를 이요해서 우선 행1개씩 잡고, 줄임.
 * 각 구간별 최소값을 만들었으면, 이제 열 하나씩 잡고 슬라이딩 윈도우를 돌리면,
 * 해당 구간의 최소값을 구할 수 있게 됨.
 *
 */

public class Prog_선인장숨기기 {

	private final static int INF = 500_001;

	//하나의 행 또는 열을 받아서 슬라이딩 윈도우를 돌리고, 만들어진 최소 배열을 반환하는 메서드
	//k는 전달받은 배열에 따라 w,h일수 있음.
	private static int[] getMinSliding(int[] arrays, int k){

		Deque<Integer> deque = new ArrayDeque<>();

		int[] tempArray = new int[arrays.length - k + 1];

		int count = 0;
		for(int i = 0 ; i < arrays.length; i++){

			//큐가 비어있지 않고, 다음 값이 현재 저장되어있는 값들보다 크면 전부 빼기.
			while(!deque.isEmpty() && arrays[deque.peekLast()] >= arrays[i]){
				deque.pollLast();
			}

			//현재 값 넣기.
			deque.addLast(i);

			//맨 앞의 값을 빼서, 윈도우를 벗어났는지 확인
			if(deque.peekFirst() <= i - k){
				deque.pollFirst();
			}

			if(i >= k - 1) tempArray[i - k + 1] = arrays[deque.peekFirst()];
		}

		return tempArray;
	}

	public int[] solution(int m, int n, int h, int w, int[][] drops) {

		int[] answer = new int[2];
		int maxValue = -1;

		int[][] maps = new int[m][n];
		for(int i = 0; i < m; i++){
			Arrays.fill(maps[i], INF);
		}

		//비가 내리는 위치 선청.
		for(int i = 0; i < drops.length; i++){
			maps[drops[i][0]][drops[i][1]] = i + 1;
		}

		//슬라이딩 윈도우로 구한 최소값 저장.
		int[][] windowMap = new int[m - h + 1][n - w + 1];

		int[][] rowMap = new int[m][n - w + 1];

		//한 행마다 돌면서 윈도우 구하고,저장
		for(int i = 0; i < m; i++){

			int[] temp = getMinSliding(maps[i], w);
			rowMap[i] = temp;
		}

		//행이 완료되었으면 열을 진행
		int[] cols = new int[m];
		for(int i = 0; i < n - w + 1; i++){

			for(int j = 0; j < m; j++){
				cols[j] = rowMap[j][i];
			}
			//열 하나 만들어지면 윈도우 생성.
			int[] temp = getMinSliding(cols, h);

			//완료되면 기존윈도우 맵에 업데이트
			for(int j = 0; j < temp.length; j++){
				windowMap[j][i] = temp[j];
			}
		}

		//최종 배열에서 최대 값을 찾아서 업데이트 - 선인장이 가능한 비를 늦게 맞아야 함.
		for(int i = 0; i < m - h + 1; i++){
			for(int j = 0; j < n - w + 1; j++){

				if(maxValue < windowMap[i][j]){
					maxValue = windowMap[i][j];
					answer[0] = i;
					answer[1] = j;
				}
			}
		}


		return answer;
	}

	public static void main(String[] args){

		Prog_선인장숨기기 p = new Prog_선인장숨기기();

		int m1 = 4;
		int n1 =5;
		int h1 = 2;
		int w1 = 2;
		int[][] drops1 = {{0, 0}, {3, 1}, {1, 3}, {2, 4}, {1, 1}, {2, 2}, {2, 3}, {0, 4}};

		System.out.println(Arrays.toString(p.solution(m1, n1, h1, w1, drops1)));
	}
}
