package baekjoon.week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs응용
 * 격자형 그래프로 주어졌기 때문에 bfs탐색하듯 탐색하면 된다.
 * 이때 주의할 점은, 쓰레기가 있는 칸을 밟은 수와, 주변에 쓰레기가 있는 칸을 밟은 수를 저장할 객체를 만들어야 한다.
 * 노드를 탐색할때, pq를 이용해서 쓰레기가 있는 칸을 밟은수가 적은 수 순서대로, 같은경우는 ㅅ주변에 쓰레기가 있는 칸을 밟은 수가 적은 순서대로 탐색을 하게 한다.
 */
public class BOJ1445_일요일아침의데이트 {

	//4방향 탐색
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y, trash, sideTrash;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, int trash, int sideTrash){
			this.x = x;
			this.y = y;
			this.trash = trash;
			this.sideTrash = sideTrash;
		}
	}

	private static int N;//세로
	private static int M;//가로
	private static char[][] graph;//그래프

	private static boolean[][] visited;//방문처리

	private static Node startNode;//시작위치


	//격자판 이동 확인
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//근처에 쓰레기가 있는지 확인
	private static boolean sideCheck(int currentX, int currentY){

		for (int i = 0; i < 4; i++){

			int nextX = currentX + dx[i];
			int nextY = currentY + dy[i];

			if(!check(nextX, nextY)) continue;

			if(graph[nextX][nextY] == 'g') return true;

		}

		return false;
	}

	//다익스트라.
	private static Node bfs(){

		PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) -> {

			if(node1.trash == node2.trash) return node1.sideTrash - node2.sideTrash;

			return node1.trash - node2.trash;
		});
		pq.add(new Node(startNode.x, startNode.y, 0, 0));

		visited[startNode.x][startNode.y] = true;

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			// System.out.println(currentNode.x + " " + currentNode.y + " " + currentNode.trash + " " + currentNode.sideTrash);

			if(graph[currentNode.x][currentNode.y] == 'F') return currentNode;

			for (int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY) || visited[nextX][nextY]) continue;

				int nextTrash = currentNode.trash;
				int nextSideTrash = currentNode.sideTrash;

				if(graph[nextX][nextY] == 'g') nextTrash++;
				if(graph[nextX][nextY] == '.' && sideCheck(nextX, nextY)) nextSideTrash++;


				pq.add(new Node(nextX, nextY, nextTrash, nextSideTrash));

				visited[nextX][nextY] = true;
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new char[N][M];
		visited = new boolean[N][M];

		for (int i = 0; i < N; i++){
			graph[i] = br.readLine().toCharArray();

			for (int j = 0; j < M; j++){

				if(graph[i][j] == 'S') startNode = new Node(i, j);

			}
		}

		Node bfs = bfs();
		System.out.println(bfs.trash + " " + bfs.sideTrash);

	}

}


