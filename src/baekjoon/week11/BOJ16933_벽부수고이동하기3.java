package baekjoon.week11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs
 * 낮과 밤을 구분해야 하면, 밤인데 벽이면 가만히 있으면 되고, 벽이 아니면 이동한다.
 * 낮인경우에는 벽을 부술 수 있기 때문에 벽을 부수는데, 지금까지 부순 벽의 개수가 K초과라면 이동 불가하다.
 */

public class BOJ16933_벽부수고이동하기3 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y, dis, wall, day; //0 : 낮, 1 :  밤

		public Node(int x, int y, int dis, int wall, int day){
			this.x = x;
			this.y = y;
			this.dis = dis;
			this.wall = wall;
			this.day = day;
		}
	}

	private static int N;
	private static int M;
	private static int K;


	private static char[][] maps;

	//낮밤 값을 바꾸는 토글.
	private static int dayToggle(int day){
		return day == 0 ? 1 : 0;
	}

	//격자판 넘어가는지 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	private static int bfs(){

		//0이면 낮, 1이면 밤.
		boolean[][][][] visited = new boolean[N][M][K + 1][2];
		visited[0][0][0][0] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(0,0,1,0,0));

		while (!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//목적지에 도달하면 반환.
			if (currentNode.x == N - 1 && currentNode.y == M - 1) return currentNode.dis;

			for (int i = 0; i < 4; i++){


				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				//격자 판을 벗어나면 패스
				if (!check(nextX, nextY)) continue;

				//이동위치가 벽인경우
				if(maps[nextX][nextY] == '1'){

					//낮이고 벽을 부술수 있으면 벽부수고 이동함.
					if (currentNode.day == 0 && currentNode.wall < K && !visited[nextX][nextY][currentNode.wall + 1][dayToggle(currentNode.day)]){
						needVisited.add(new Node(nextX, nextY,currentNode.dis + 1, currentNode.wall + 1, dayToggle(currentNode.day)));
						visited[nextX][nextY][currentNode.wall + 1][dayToggle(currentNode.day)] = true;
					}
					//밤이면 현재 위치에서 대기해야 됨.
					else if (currentNode.day == 1 && currentNode.wall < K && !visited[currentNode.x][currentNode.y][currentNode.wall][dayToggle(currentNode.day)]){
						needVisited.add(new Node(currentNode.x, currentNode.y, currentNode.dis + 1, currentNode.wall, dayToggle(currentNode.day)));
						visited[currentNode.x][currentNode.y][currentNode.wall][dayToggle(currentNode.day)] = true;
					}
				}
				//벽이 아닌 경우.
				else {

					if(visited[nextX][nextY][currentNode.wall][dayToggle(currentNode.day)]) continue;

					needVisited.add(new Node(nextX, nextY, currentNode.dis + 1, currentNode.wall, dayToggle(currentNode.day)));
					visited[nextX][nextY][currentNode.wall][dayToggle(currentNode.day)] = true;
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		maps = new char[N][M];
		for(int i = 0; i < N; i++){
			maps[i] = br.readLine().toCharArray();
		}

		System.out.println(bfs());

	}
}
