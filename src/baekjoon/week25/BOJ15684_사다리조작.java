package baekjoon.week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 백트래킹
 *
 * 문제에서 주어진대로 사다리를 구성하고, 한줄씩 내려가면서 i -> i로 가는지 확인한다.
 * 이때 문제에서 답이 3보다 크면 -1을 출력하라고 했기 때문에, 3을 넘어가면 종료한다.
 */

public class BOJ15684_사다리조작 {

	private static int N;//세로선의 개수
	private static int M;//가로선의 개수
	private static int H;//위치의 개수.
	private static int total;

	private static int[][] graph;//사다리를 표시할 배열.

	//사다리 배치가 끝난 후에 i -> i 여부를 판단
	private static boolean check(){

		for(int i = 0; i < N; i++){
			int currentX = 0;
			int currentY = i;

			while(currentX < H){

				if(graph[currentX][currentY] == 1){
					currentY++;
				}
				else if(graph[currentX][currentY] == -1){
					currentY--;
				}

				currentX++;
			}

			if(i != currentY) return false;

		}

		return true;
	}

	//사다리를 배치할 dfs - 이차원 좌표를 1차원으로 내려서 계산.
	private static boolean dfs(int idx, int count, int ladderCount){

		//count가 놓을 사다리 개수를 넘으면 종료, 최소개수를 구하는 것이므로 0,1,2,3 개 순으로 놓아봄.
		//목표개수에 도달했을때만 체크.
		if(count == ladderCount) return check();

		for(int i = idx; i < total; i++){

			//사다리를 놓을 좌표 추출.
			int x = i / N;
			int y = i % N;


			//배치할 수 없으면 패스..
			if (y == N - 1 || !(graph[x][y] == 0 && graph[x][y + 1] == 0)) continue;

			//사다리 배치
			graph[x][y] = 1;
			graph[x][y + 1] = -1;

			//배치했을때 i -> i 구조가 나오면 바로 리턴.
			if(dfs(i + 1, count + 1, ladderCount)) return true;

			//사다리 원복.
			graph[x][y] = 0;
			graph[x][y + 1] = 0;


		}

		return false;
	}

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		total = N * H;

		graph = new int[H][N];

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			graph[a - 1][b - 1] = 1;
			graph[a - 1][b] = -1;
		}


		int result = -1;
		for(int i = 0; i <= 3; i++){

			if(!dfs(0,0, i)) continue;

			result = i;
			break;
		}

		System.out.println(result);

	}
}
