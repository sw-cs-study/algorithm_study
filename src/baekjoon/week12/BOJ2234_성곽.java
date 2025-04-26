package baekjoon.week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs + 비트마스킹
 * 번호를 붙여가며 bfs로 탐색을 한다.
 * 이때 동서남북에 벽이 있는지를 이진수값을 십진수로 바꾼 걸로 줬기 때문에
 * and 연산을 통해서 이동이 가능한지 파악한다.
 * 서 => 0001 => 1
 * 북 => 0010 => 2
 * 동 => 0100 => 4
 * 남 => 1000 => 8
 *
 * 이므로 해당 값과, 벽정보를 and 연산했을때, 그대로 동서남북 값이 나오면 해당 위치는 벽이 있는 것이다.
 * 0이 나오면 벽이 없고 이동이 가능한다는 뜻이 된다.
 *
 * bfs로 각 구역별로 번호가 구해지면, 구역이 몇개인지(1번 조건), 가장 넓은구역(2번조건)을 구할 수 있다.
 * 이 상태에서 다시 bfs를 돌면서 벽을 만나면 벽 넘어의 구역의 크기가 몇인지 확인하여, 현재 구역크기 + 벽넘어 크기를 최대값으로 저장해둔다.
 * 이를 모든 노드를 돌때까지 반복하면서 벽을 부셨을떄 몇인지를 구한다.
 */
public class BOJ2234_성곽 {

	//4방향 탐색 - 서북동남
	private static int[] dx = {0, -1, 0, 1};
	private static int[] dy = {-1, 0, 1, 0};

	//서북동남 별 비트연산을 위한 수.
	private static int[] bitValue = {1,2,4,8};

	//탐색을 위한 노드
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int M;//가로
	private static int N;//세로
	private static int oneResult;//가장 넓은 방의 크기.
	private static int result;//하나의 벽을 제거해서 얻을 수 있는 가장 큰 넓이.
	private static int[][] graph;//방문처리할 그래프
	private static int[][] wallInfoGraph;//벽 위치를 저장할 그래프.
	private static List<Integer> roomSizeArray;//각 구역별 크기를 저장할 리스트.

	//이동이 가능한지 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//벽 체크 - true면 벽임.
	private static boolean wallCheck(int currentDir, int wallInfo){
		return (currentDir & wallInfo) == currentDir;
	}

	//구역에 번호를 붙일 bfs
	private static int numberingBfs(Node startNode, int num){

		int count = 0;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startNode.x, startNode.y));

		graph[startNode.x][startNode.y] = num;

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();
			count++;

			for(int i = 0; i < 4; i++){

				//이동하려는 곳에 벽이 있으면 패스.
				if(wallCheck(bitValue[i], wallInfoGraph[currentNode.x][currentNode.y])) continue;

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				//이동 불가한 위치거나, 이미 방문한곳(0이 아닌 값)이면 패스
				if(!check(nextX, nextY) || graph[nextX][nextY] != 0) continue;

				graph[nextX][nextY] = num;
				needVisited.add(new Node(nextX, nextY));

			}
		}
		return count;
	}

	//벽을 마주치면 벽넘어를 계산할 bfs
	private static void wallSumBfs(Node startNode, int num, boolean[][] visited){


		visited[startNode.x][startNode.y] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startNode.x, startNode.y));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY)) continue;

				//벽이고 벽넘어가 현재 값과 다르다면,두 값을 합쳐서 기존 값과 비교 후 패스
				if(wallCheck(bitValue[i], wallInfoGraph[currentNode.x][currentNode.y]) && graph[nextX][nextY] != num){
					result = Math.max(result, roomSizeArray.get(num) + roomSizeArray.get(graph[nextX][nextY]));
					continue;
				}
				//벽이 아니고 방문하지 않았으면 계속 탐색.
				if(visited[nextX][nextY] || graph[nextX][nextY] != num) continue;

				needVisited.add(new Node(nextX,nextY));
				visited[nextX][nextY] = true;
			}
		}

	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		result = -1;
		graph = new int[N][M];
		wallInfoGraph = new int[N][M];
		roomSizeArray = new ArrayList<>();
		roomSizeArray.add(-1); //인덱스를 1부터 쓰기 위해 -1을 넣음(그래프에서 아직 탐색안한 노드를 0으로 쓰기 위함.)

		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++){
				wallInfoGraph[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int num = 1;
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				if(graph[i][j] != 0) continue;

				int resultCount = numberingBfs(new Node(i, j), num++);
				roomSizeArray.add(resultCount);
				oneResult = Math.max(oneResult, resultCount);
			}
		}

		boolean[][] visited = new boolean[N][M];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){

				wallSumBfs(new Node(i, j), graph[i][j], visited);
			}
		}

		System.out.println(roomSizeArray.size() - 1);
		System.out.println(oneResult);
		System.out.println(result);
	}
}
