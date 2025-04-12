package baekjoon.week11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현
 * 문제에서 말한대로 구현하면 된다.
 *
 * (주의)
 * 문제에서 N일동안 주어지는 수열은 각각 0,1,2의 개수임.
 * 또한 매일 매일 모든 벌레의 크기를 확인해가면 최악의 경우 3억번의 반복을 해야함.
 * 잘 생각해보면 사이드의 벌레들을 N일까지 다 키운 후에 나머지 벌레들을 성장시켜도 같아진다.
 */

public class BOJ10836_여왕벌 {

	//왼쪽, 왼쪽위, 위 방향을 확인하는 객체
	private static int[] dx = {0, -1, -1, 0};
	private static int[] dy = {-1, -1, 0, 1};


	private static int M;//격자판 크기
	private static int N;//날짜 수
	private static int[][] graph;//격자판


	//벽면에 붙은 애벌레의 성장.
	private static void growSideLarva(int[] infoArray){

		int currentX = M - 1;
		int currentY = 0;
		int dir = 2; //방향

		for(int i = 0; i < infoArray.length; i++){

			graph[currentX][currentY] += infoArray[i];

			if(currentX == 0 && dir == 2) dir++;

			currentX += dx[dir];
			currentY += dy[dir];
		}
	}


	//나머지 애벌레의 성장 - 위에 있는게 최대
	private static void growGeneralLarva(){

		for(int i = 1; i < M; i++){
			for(int j = 1; j < M; j++){

				graph[i][j] += graph[0][j];
			}
		}
	}

	//출력
	private static void printResult(){

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < M; i++){
			for(int j = 0; j < M; j++){
				result.append(graph[i][j] + 1);

				if(j == M - 1) continue;

				result.append(" ");
			}
			result.append("\n");
		}

		System.out.println(result);

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		graph = new int[M][M];


		int[] infoArray = new int[(M * 2) - 1];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());


			int idx = Integer.parseInt(st.nextToken());

			//입력으로 주어지는 왼쪽, 맨위 애벌레들의 성장크기를 리스트로 만듦
			for(int num = 1; num < 3; num++){

				int count = Integer.parseInt(st.nextToken());

				for(int k = 0; k < count; k++){
					infoArray[idx++] += num;
				}
			}

		}
		//왼쪽, 맨위 애벌레들 성장.
		growSideLarva(infoArray);

		//나머지 애벌레들의 성장.
		growGeneralLarva();

		printResult();

	}
}
