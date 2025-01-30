package baekjoon.week2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs+구현
 * 매초 동작을 수행,
 * 치즈의 경우, 4방향을 확인해서, 2변 이상이 공기와 접촉시에, 리스트에 넣고 모아두었다가 한번에 제거
 * 치즈에 갇혀있는 내부 공간인지 외부공간인지 구분을 위해 maps정보에 외부 공기는 -1로 표기.
 * 0,0(해당 부분은 항상 치즈가 없음.) 부터 bfs 탐색을 통해서 연결되어있으면 외부 공간으로 표기하도록 함.
 *
 */
public class BOJ2638_치즈 {

	private static final int[] dx = {-1, 1, 0, 0};
	private static final int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int N;
	private static int M;

	private static int[][] maps; //외부 공기는 -1로 표기.
	private static boolean[][] visited; //외부 공기 처리 bfs 방문처리 배열.

	//4방향 탐색시에 칸을 벋어났는지 여부 체크 메서드
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//치즈 4방향 탐색해서 확인하는 메서드 - true : 녹일 치즈, false 그대로 냅둘 치즈
	private static boolean cheeseCheck(int cheeseX, int cheeseY){

		int count = 0;
		for(int i = 0; i < 4; i++){
			int nextX = cheeseX + dx[i];
			int nextY = cheeseY + dy[i];

			//벗어났거나 외부 공기가 아니면 패스
			if(!check(nextX,nextY) || maps[nextX][nextY] != -1) continue;

			count++;

			if(count >= 2) return true;//
		}

		return false;
	}

	//치즈 리스트를 받아서 치즈 삭제 처리하는 메서드.
	private static void deleteCheese(List<Node> cheeseList){

		for(Node node : cheeseList){
			//0으로 만들어, 다음단계인 외부 공기 체크 메서드가 처리하도록 함.
			maps[node.x][node.y] = 0;
		}
	}

	//bfs를 통해서 외부 공기 여부를 체크하고, 외부공기와 연결되었으면 외부공기로 업데이트 하는 메서드
	private static void updateOutAir(){
		visited = new boolean[N][M];
		visited[0][0] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(0,0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			for(int i = 0; i < 4; i++){
				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				//칸을 벗어났거나, 방문했거나, 치즈면 패스.
				if(!check(nextX, nextY) || visited[nextX][nextY] || maps[nextX][nextY] == 1) continue;

				//내부 공기라면 외부공기로 업데이트
				if(maps[nextX][nextY] == 0) maps[nextX][nextY] = -1;

				visited[nextX][nextY] = true;
				needVisited.add(new Node(nextX, nextY));
			}
		}
	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		int initCheese = 0; //초기 치즈 갯수 계산.
		maps = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				maps[i][j] = Integer.parseInt(st.nextToken());

				if(maps[i][j] == 1) initCheese++;
			}
		}



		//초기 외부 공기 업데이트
		maps[0][0] = -1;// 시작점 업데이트.
		updateOutAir();

		int time = 0;
		List<Node> cheeseList;//삭제할 치즈 리스트.
		while (true) {
			time++; //시간 증가.

			//치즈 확인
			cheeseList = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if(maps[i][j] != 1 || !cheeseCheck(i, j)) continue;

					cheeseList.add(new Node(i,j));
				}
			}

			//삭제 될 치즈 개수 저장
			initCheese -= cheeseList.size();
			//치즈 삭제.
			deleteCheese(cheeseList);
			//모든 치즈가 삭제되었는지 확인
			if(initCheese <= 0) break;

			//외부 공기 업데이트.
			updateOutAir();

		}

		System.out.println(time);



	}
}
