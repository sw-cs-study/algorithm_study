package programmers.week48;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * 아이디어
 * 비트마스킹 + bfs
 * (tps 문제와 유사.)
 */

public class Prog_발전소회로복구 {

	private final static int INF = 10_000_000;

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x,y, count;

		public Node(int a, int b, int count){
			this.x = a;
			this.y = b;
			this.count = count;
		}
	}

	private static int r;
	private static int c;
	private static int k; // 판넬수.


	private static int[] needs;//각 판넬별 선탱되어야 하는 정보.
	private static int[][] dist; //[a][b] = a에서 b 까지 거리.
	private static int[][] dp;//각 판넬의 최단 거리 - [i][j] => i는 탐색한 판넬들 비트 정보, j는 마지막으로 탐색한 판넬 번호.
	private static char[][] maps;

	//초기 값 설정
	private static void init(String[] grid, int[][] panels){

		r = grid.length;
		c = grid[0].length();
		k = panels.length;
		maps = new char[r][c];

		for(int i = 0; i < r; i++){
			maps[i] = grid[i].toCharArray();
		}

		needs = new int[k + 1];
		dp = new int[(1 << k)][k + 1];
		dist = new int[k + 1][k + 1];


		for(int i = 0; i < (1<<k); i++){
			Arrays.fill(dp[i], INF);
		}
	}

	//bfs 탐색조건
	private static boolean check(int nextX, int nextY){

		return nextX >= 0 && nextX < r &&
			nextY >= 0 && nextY < c;
	}

	//bfs -> start,target간 거리구하기
	private static int bfs(Node startNode, Node targetNode){
		boolean[][] visited = new boolean[r][c];
		visited[startNode.x][startNode.y] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startNode.x, startNode.y, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(currentNode.x == targetNode.x && currentNode.y == targetNode.y){
				return currentNode.count;
			}

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY) || visited[nextX][nextY] || maps[nextX][nextY] == '#') continue;

				visited[nextX][nextY] = true;
				needVisited.add(new Node(nextX, nextY, currentNode.count + 1));
			}
		}

		return -1;

	}

	//dist 배열 채우기.
	private static void logic(int h, int[][] panels){

		dp[0][1] = 0;
		for(int i = 0; i < (1 << k); i++){

			//시작 판넬.
			for(int j = 1; j <= k; j++){

				//INF 이면 불가.
				if(dp[i][j] == INF) continue;

				//이동하려는 판넬.
				for(int m = 1; m <= k; m++){

					//이미 켠 패널이라면 패스
					if((i & (1 << (m - 1))) != 0) continue;

					//선행조건 확인 - 선행조건을 가지고 있지 않으면 패스.
					if((i & needs[m]) != needs[m]) continue;


					//현재 위치에서 m으로 가는 경우 구하기.
					int nextCost = dist[j][m];
					int nextState = i | (1 << (m - 1));

					dp[nextState][m] = Math.min(dp[nextState][m], dp[i][j] + nextCost);
				}

			}
		}

	}


	public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
		int answer = INF;

		init(grid, panels);

		//선행 패널 정보 - 번호순으로 볼수 있도록(번호는 1부터 사용.)
		for(int i = 0; i < seqs.length; i++){
			int[] seq = seqs[i]; // [0]: 선행 패널

			needs[seq[1]] = needs[seq[1]] | (1 << seq[0] - 1);
		}



		Node elevator = null;
		//엘리베이터 위치 구하기.
		for(int i = 0; i < r; i++){
			for(int j = 0; j < c; j++){

				if(maps[i][j] != '@') continue;

				elevator = new Node(i, j, 0);

			}
		}

		//dist초기 거리 구성
		//i = 0은 엘베, 엘베에서 각 판넬 위치 구하기.
		for(int i = 1; i <= k; i++){
			int[] panel = panels[i - 1];

			int temp = bfs(elevator, new Node(panel[1] - 1,panel[2] - 1,0));
			dist[0][i] = temp;
			dist[i][0] = temp;
		}

		//dist 거리 구성
		for(int i = 1; i <= k; i++){
			for(int j = 1; j <= k; j++){

				if(i == j) continue;

				//두 패널의 층이 다르면, 엘베거리를 이용하면 됨.
				if(panels[i - 1][0] != panels[j - 1][0]){
					dist[i][j] = dist[0][i] + dist[0][j] + Math.abs(panels[i - 1][0] - panels[j - 1][0]);

				}
				//두 패널의 층이 같으면 bfs로 처리.
				else{
					dist[i][j] = bfs(
						new Node(panels[i - 1][1] - 1, panels[i - 1][2] - 1, 0),
						new Node(panels[j - 1][1] - 1, panels[j - 1][2] - 1, 0)
					);
				}

			}
		}

		logic(h,panels);

		for(int i = 0; i < k + 1; i++){
			answer = Math.min(answer, dp[(1 << k) - 1][i]);
		}

		return answer;
	}

	public static void main(String[] args){

		Prog_발전소회로복구 p = new Prog_발전소회로복구();

		int h1 = 3;
		String[] grid1 = {".#.##..", ".#..##.", ".......", "##.###.", ".@.#...", "...#..."};
		int[][] panels1 = {{2, 3, 4}, {2, 5, 6}, {1, 1, 1}, {3, 6, 3}};
		int[][] seqs1 = {{3, 2}, {1, 2}, {4, 1}, {4, 3}};

		System.out.println(p.solution(h1,grid1,panels1, seqs1));

		int h2 = 1;
		String[] grid2 = {"@......", ".######", ".......", "######.", ".......", ".####..", "....#.."};
		int[][] panels2 = {{1, 7, 4}, {1, 3, 5}, {1, 1, 3}};
		int[][] seqs2 = {{1, 3}, {3, 2}};

		System.out.println(p.solution(h2,grid2,panels2, seqs2));

		int h3 = 4;
		String[] grid3 = {"........#", "........#", "@.......#", ".#.#....#", "........#", "#........", "#.#..####", "..#..####", ".....####"};
		int[][] panels3 = {{2, 9, 1}, {2, 1, 8}, {1, 1, 3}, {3, 3, 2}, {1, 2, 8}};
		int[][] seqs3 = {{1, 2}, {2, 3}, {3, 4}, {4, 5}};

		System.out.println(p.solution(h3,grid3,panels3, seqs3));

	}
}
