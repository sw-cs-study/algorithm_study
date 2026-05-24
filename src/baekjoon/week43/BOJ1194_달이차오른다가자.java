package baekjoon.week43;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs + 비트 마스킹.
 */
public class BOJ1194_달이차오른다가자 {

	private final static int[] dx = {-1, 1 ,0 ,0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y, count, key;

		public Node(int x, int y, int count, int key){
			this.x = x;
			this.y = y;
			this.count = count;
			this.key = key;
		}
	}

	private static int N;//세로
	private static int M;//가로

	private static char[][] graph;//그래프

	//이동 가능 여부 확인.
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//해당 알파벳 대소문자에 따라 비트를 몇 만큼 이동 시켜야 하는지 구하는 메서드.
	private static int getMoveBit(char alpha){

		//대문자도 소문자도 아니면 0 반환.
		if((alpha < 'a' || alpha > 'z') && (alpha < 'A' || alpha > 'Z')) return 0;

		//소문자면 97을 뺌, 대문자면 65를 뻄.
		return 1 << (Character.isLowerCase(alpha) ? alpha - 97 : alpha - 65);
	}

	//bfs
	private static int bfs(Node startNode){

		boolean[][][] visited = new boolean[N][M][64];
		visited[startNode.x][startNode.y][0] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startNode.x, startNode.y, 0, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(graph[currentNode.x][currentNode.y] == '1') return currentNode.count;

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				//이동이 불가하거나 벽이거나 이미 이동했던 곳이면 이동 불가.
				if(!check(nextX, nextY) || graph[nextX][nextY] == '#' || visited[nextX][nextY][currentNode.key]) continue;

				int nextKey = currentNode.key;

				//다음 위치가 열쇠면 열쇠를 계산
				if(graph[nextX][nextY] >= 'a' && graph[nextX][nextY] <= 'z'){

					nextKey |= getMoveBit(graph[nextX][nextY]);
				}
				//문이라면, 주어진 열쇠로 열 수 있는지 확인 - and 연산시에 0이면 못여는 것.
				else if(graph[nextX][nextY] >= 'A' && graph[nextX][nextY] <= 'Z' && (nextKey & getMoveBit(graph[nextX][nextY])) == 0) continue;

				//위의 경우를 제외한 경우는 전부 이동가능.
				visited[nextX][nextY][nextKey] = true;
				needVisited.add(new Node(nextX,nextY, currentNode.count + 1, nextKey));
			}
		}


		return -1;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new char[N][M];

		Node startNode = null;

		for(int i = 0; i < N; i++){
			graph[i] = br.readLine().toCharArray();

			for(int j = 0; j < M; j++){

				if(graph[i][j] != '0') continue;

				startNode = new Node(i, j, 0, 0);
			}
		}

		System.out.println(bfs(startNode));

	}
}
