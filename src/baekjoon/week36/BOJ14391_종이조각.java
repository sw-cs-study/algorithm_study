package baekjoon.week36;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ14391_종이조각 {


	private static int n; //세로
	private static int m; //가로
	private static int[][] maps;
	private static boolean[][] visited;
	private static int result;//총 합계

	//dfs
	private static void dfs(int idx, int sum){

		if(idx >= n * m){
			result = Math.max(result, sum);
			return;
		}

		int currentX = idx / m;
		int currentY = idx % m;

		if(visited[currentX][currentY]) dfs(idx + 1, sum);
		else{
			int num = maps[currentX][currentY];
			visited[currentX][currentY] = true;
			//해당칸
			dfs(idx + 1, sum + num);

			//세로
			int i;
			int tempNum = num;
			for(i = currentX + 1; i < n; i++){

				//이동 불가능하면 해당방향 종료.
				if(visited[i][currentY]) break;

				visited[i][currentY] = true;
				tempNum = (tempNum * 10) + maps[i][currentY];
				dfs(idx + 1,  sum + tempNum);
			}

			//방문원복
			for(int j = currentX + 1; j < i; j++){
				visited[j][currentY] = false;
			}

			//가로
			tempNum = num;
			for(i = currentY + 1; i < m; i++){

				if(visited[currentX][i]) break;

				visited[currentX][i] = true;
				tempNum = (tempNum * 10) + maps[currentX][i];
				dfs(idx + i - currentY + 1, sum + tempNum);
			}

			//방문 원복.
			for(int j = currentY + 1; j < i; j++){
				visited[currentX][j] = false;
			}

			//현재 위치 원복.
			visited[currentX][currentY] = false;

		}

	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		result = 0;
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		maps = new int[n][m];
		visited = new boolean[n][m];

		for(int i = 0; i < n; i++){
			String temp = br.readLine();
			for(int j = 0; j < m; j++){
				maps[i][j] = Character.getNumericValue(temp.charAt(j));
			}
		}

		dfs(0, 0);
		System.out.println(result);
	}
}
