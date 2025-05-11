package baekjoon.week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs
 * 빈칸을 0으로 생각하고, 매번 해당 칸의 위치를 노드, 해당 칸이 이동할 수 있는 인접한 4방향을 다음 노드로 생각해서 처리한다.
 * 3*3 격자판의 상태도 같이 넘겨야한다.
 * 격자판의 수가 많지 않기 때문에 빈칸을 0으로 보고, (1,1)부터 3,3까지 순차적으로 이어 붙여서 문자열로 만들면 격자판의 상태값을 저장하여,
 * 중복 방문하는 경우를 방지 할 수 있다.
 */
public class BOJ1525_퍼즐 {

	//4방향 탐색
	private final static int[] dx = {-1, 1 ,0 ,0};
	private final static int[] dy = {0, 0, -1, 1};

	//노드
	private static class Node{
		int x, y, count;
		String status;

		public Node(int x, int y, int count, String status) {
			this.x = x;
			this.y = y;
			this.count = count;
			this.status = status;
		}
	}

	private static int[][] graph;//그래프


	//목표상태인지 체크
	private static boolean targetCheck(String currentStatus){
		return currentStatus.equals("123456780");
	}

	//다음 상태 값 구하기
	private static String getNextStatus(String currentStatus, int nextX, int nextY){

		//격자판으로 복원
		int[][] tempGraph = new int[3][3];

		int targetX = 0;
		int targetY = 0;

		int idx = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				tempGraph[i][j] = Character.getNumericValue(currentStatus.charAt(idx++));

				if(tempGraph[i][j] == 0){
					targetX = i;
					targetY = j;
				}
			}
		}

		//다음 상태 구하기.
		tempGraph[targetX][targetY] = tempGraph[nextX][nextY];
		tempGraph[nextX][nextY] = 0;

		//문자열 상태로 만들기.
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				result.append(tempGraph[i][j]);
			}
		}


		return result.toString();
	}

	//격자판을 넘어가진 않는지 검증.
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < 3 &&
			nextY >= 0 && nextY < 3;
	}

	//bfs
	private static int bfs(Node startNode){

		HashSet<String> visited = new HashSet<>();
		visited.add(startNode.status);

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		while (!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(targetCheck(currentNode.status)) return currentNode.count;

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY)) continue;

				String nextStatus = getNextStatus(currentNode.status, nextX,nextY);

				//방문했던 상태이면 패스
				if(visited.contains(nextStatus)) continue;

				visited.add(nextStatus);
				needVisited.add(new Node(nextX, nextY, currentNode.count + 1, nextStatus));
			}
		}

		return -1;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		graph = new int[3][3];

		int startX = 0;
		int startY = 0;
		for(int i = 0; i < 3; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++){

				graph[i][j] = Integer.parseInt(st.nextToken());

				if(graph[i][j] == 0){
					startX = i;
					startY = j;
				}

			}
		}



		//초기 노드 구성
		StringBuilder initStatus = new StringBuilder();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++) {
				initStatus.append(graph[i][j]);
			}
		}
		Node startNode = new Node(startX, startY, 0, initStatus.toString());

		System.out.println(bfs(startNode));

	}
}
