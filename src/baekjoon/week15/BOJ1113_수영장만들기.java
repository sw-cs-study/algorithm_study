package baekjoon.week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs + 구현
 * bfs를 이용해서 가장 크기가 낮은 것들부터 탐색한다
 * 선택한 크기를 기준으로 자신과 같으면 탐색을 이어가서 웅덩이의 크기를 찾고, 자신보다 높이가 높은 칸을 만나면 해당값을 이전에 저장된 값과 비교해서,
 * 하나의 웅덩이에서 물이 넘치지 않는 선에서 최대로 담을 수 있는 높이를 구한다.
 *
 * 이렇게 물이 넘치지 않고, 웅덩이가 형성되었으면 해당 좌표들에 물을 넣어서 최대로 담을수 있는 높이까지 채운다.
 * 이때, 탐색중에 가장자리까지 연결이 된다면 물이 흘러내린다는 뜻이므로, 가장자리라면 물을 담지 않도록 표시를 남겨야 한다.
 *
 * (시간복잡도 계산)
 * 각 격자판마다 1~9까지 체크하면서 bfs 시작좌표를 찾아야 한다 => N*M*9
 * 격자 판이므로 BFS를 돈다면 모든 칸을 다 탐색한다 => N*M
 * 두 경우의 수를 곱한다 => (N*M)^2*9 => 50^4 * 9 => 대략 10^7 * 6.25,
 * 1억이 좀 안되는 수치로 연산 1억번을 1초라고 했을떄, 문제에서 주어진 2초내로 연산이 가능한다.
 */
public class BOJ1113_수영장만들기 {

	//4방향 탐색 배열
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	//탐색할 노드
	private static class Node{

		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int N;//가로
	private static int M;//세로
	private static int result; //총 담을 수 있는 수영장의 물의 양
	private static int[][] graph;//격자 판.

	//격자판을 벗어나는지 체크
	private static boolean check(int nextX, int nextY){

		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < N;
	}

	//가장자리인지 파악 -> 가장자리와 연결되면 물 담을수 없음.
	private static boolean sideCheck(int nextX, int nextY){

		return nextX == 0 || nextX == N - 1 || nextY == 0 || nextY == M - 1;
	}

	//bfs 탐색
	private static void bfs(int startX, int startY, int targetNum){

		boolean[][] visited = new boolean[N][M];
		visited[startX][startY] = true;

		int minHeight = 10;
		List<Node> poolWater = new ArrayList<>(); //채워야 하는 웅덩이 위치.

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startX, startY));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			poolWater.add(new Node(currentNode.x, currentNode.y));

			for(int dir = 0; dir < 4; dir++){

				int nextX = currentNode.x + dx[dir];
				int nextY = currentNode.y + dy[dir];

				//방문했던 노드라면 패스
				if(visited[nextX][nextY]) continue;

				//다음 노드의 크기가 더 작으면 채울수 없음 - 그대로 종료
				if(graph[nextX][nextY] < targetNum) return;

				//다음 노드의 크기가 더 크다면 크기 업데이트 하고 패스
				if(graph[nextX][nextY] > targetNum){
					minHeight = Math.min(minHeight, graph[nextX][nextY]);
					continue;
				}

				//다음 노드의 크기가 같은데 가장자리라면 더 탐색할 필요 없음.
				if(sideCheck(nextX, nextY)) return;

				needVisited.add(new Node(nextX, nextY));
				visited[nextX][nextY] = true;
			}
		}


		//웅덩이에 물 채우기
		fillWater(poolWater, minHeight - targetNum);
	}

	//웅덩이들에 물 채우기.
	private static void fillWater(List<Node> poolList, int waterAmount){


		for(Node pool : poolList){
			graph[pool.x][pool.y] += waterAmount;
			result += waterAmount;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new int[N][M];
		for(int i = 0; i < N; i++){
			String str = br.readLine();
			for(int j = 0; j < M; j++){
				graph[i][j] = Character.getNumericValue(str.charAt(j));
			}
		}

		result = 0;
		for(int k = 1; k <= 9; k++){
			for(int i = 0; i < N; i++){
				for(int j = 0; j < M; j++){

					//가장 자리이면 패스
					if(sideCheck(i, j)) continue;

					if(graph[i][j] == k) bfs(i, j, k);
				}
			}
		}



		System.out.println(result);
	}
}
