package programmers.week45;

import java.util.Arrays;

/**
 * 아이디어
 * 구현
 * 매번 공을 4방향의 벽으로 원쿠션 했을때의 거리를 구하면 된다.
 */

public class Prog_당구게임 {

	//두 점 간의 거리 구하기 - 구해야 하는 값이 거리의 제곱이라 루트 씌울 필요 없음.
	private static int distance(int startX, int startY, int endX, int endY){

		return (int) Math.pow(startX - endX, 2) + (int) Math.pow(startY - endY, 2);
	}

	public int[] solution(int m, int n, int startX, int startY, int[][] balls) {

		int[] answer = new int[balls.length];

		//거리 계산시에는 x축 또는 y축으로 반전시켜서 직선의 거리를 구하면 됨.
		for(int i = 0; i < balls.length; i++){
			int minValue = Integer.MAX_VALUE;
			int targetX = balls[i][0];
			int targetY = balls[i][1];


			//왼쪽 벽
			if(!(startY == targetY && startX > targetX)){
				minValue = Math.min(minValue, distance(startX, startY, targetX*(-1), targetY));
			}

			//오른쪽 벽
			if(!(startY == targetY && startX < targetX)){
				minValue = Math.min(minValue,distance(startX, startY, (m - targetX) + m, targetY));
			}

			//위쪽 벽
			if(!(startX == targetX && startY < targetY)){
				minValue = Math.min(minValue, distance(startX, startY, targetX, (n - targetY) + n));
			}

			//아래 쪽 벽
			if(!(startX == targetX && startY > targetY)){
				minValue = Math.min(minValue, distance(startX, startY, targetX, targetY * (-1)));
			}

			answer[i] = minValue;
		}

		return answer;

	}

	public static void main(String[] args){

		Prog_당구게임 p = new Prog_당구게임();
		int m = 10;
		int n = 10;
		int startX = 3;
		int startY = 7;
		int[][] balls = {{7, 7}, {2, 7}, {7, 3}};

		System.out.println(Arrays.toString(p.solution(m,n, startX, startY, balls)));


	}
}
