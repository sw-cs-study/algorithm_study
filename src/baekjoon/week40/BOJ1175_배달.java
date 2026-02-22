package baekjoon.week40;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs 응용
 *
 * 배달 즉, 목적지는 총 2개가 있다.
 * 따라서 bfs 탐색을 해서, 두개의 목적지를 가장빨리 전부 선택했을때가 최단거리가 된다.
 * 이때 주의할 점은, 각 위치에 방향정보도 저장해야 한다는 점이다.
 * 한번 방문했다고 다시 방문 할수 없게 하면 목적지 두곳 전부 방문이 어려울수 있다.
 * 하지만 같은 방향으로 진입한 위치라면 다시 볼필요가 없다(그러면 최단거리가 될 수 없음)
 * 이런 방식으로 bfs를 구현하면 최단거리를 구현할 수 있다.
 *
 * (추가)
 * 시작점에서 단순 bfs 탐색을 하면 안된다.
 * 첫번째로 1번 도착지에 갔다 2번 도착지로 이동할때는 갔던 곳을 다시 가도 된다.
 * 두번째로는 bfs로 해서 도착한 경우는 출발지로 가장 가까운 도착지 -> 먼 도착지가 되는데,
 * 이 경우는 예외가 존재한다.
 * 멀이 있는걸 먼저 도착했을때 훨씬 빠를 수 있다는 것이다.
 * 만약 시작점과 같은 열, 다른 행에 도착지가 연속으로 있다면,
 * 같은 방향으로 이동이 불가능하기 때문에 멀리 돌아가서 더 걸리는 경우가 생길수도 있다.
 *
 * (수정)
 * 굳이 케이스를 나눠서 할 필요는 없다.
 * 한번의 bfs로 처리가 가능한데, 방문처리를 할때 방향만 하는 것이 아니라,
 * 그동안 어떤 방을 거쳐서 왔는지도 추가해서 4차원 배열로 구분하면 된다;.
 * 이부분에서 비트마스킹을 사용해도 되지만 굳이 그럴필요 없이 4가지 상태로 표현을 했다.
 * 0 -> 도착지 한곳도 방문안함.
 * 1 -> 1번 도착지를 방문한 상태임
 * 2 -> 2번 도착지를 방문한 상태임
 * 3 -> 도착지를 모두 방문한 상태임
 *
 */
public class BOJ1175_배달 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	//노드
	private static class Node{
		int x, y, prevDir, time, targetCount, totalDis;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, int prevDir, int time, int targetCount){
			this.x = x;
			this.y = y;
			this.prevDir = prevDir; //이전 방향
			this.time = time; //이동 거리(시간)
			this.targetCount = targetCount;

		}
	}


	private static int N;//세로
	private static int M;//가로

	private static List<Node> finishNode; // 도착지 노드들.

	private static char[][] graph;//그래프.

	//격자 그래프를 벗어나지 않았는지 검사 메서드
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//bfs
	private static int bfs(Node startNode){

		// 방향과 해당 방향으로 갔을때C 방문여부 체크(0 : 방문안함, 1 : c1 방문, )
		boolean[][][][] visited = new boolean[N][M][4][3];
		Queue<Node> needVisited = new ArrayDeque<>();

		for(int i = 0; i < 4; i++){
			visited[startNode.x][startNode.y][i][0] = true;
		}

		needVisited.add(new Node(startNode.x, startNode.y, -1, 0, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(currentNode.targetCount == 3) return currentNode.time;


			for(int i = 0; i < 4; i++){

				//이전 방향과 같으면 패스.
				if(currentNode.prevDir == i) continue;

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				//다음 방향으로 이동가능한지 확인
				if(!check(nextX, nextY) || visited[nextX][nextY][i][currentNode.targetCount] || graph[nextX][nextY] == '#') continue;

				visited[nextX][nextY][i][currentNode.targetCount] = true;

				int nextTargetCount = currentNode.targetCount;
				//1번 도착지를 방문한적 없는데, 다음위치가 1인 경우.
				if(finishNode.get(0).x == nextX && finishNode.get(0).y == nextY && currentNode.targetCount != 1) nextTargetCount += 1;
				else if(finishNode.get(1).x == nextX && finishNode.get(1).y == nextY && currentNode.targetCount != 2) nextTargetCount += 2;

				needVisited.add(new Node(
					nextX,
					nextY,
					i,
					currentNode.time + 1,
					nextTargetCount
					)
				);
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
		finishNode = new ArrayList<>();
		for(int i = 0; i < N; i++){
			graph[i] = br.readLine().toCharArray();

			for(int j = 0; j < M; j++){

				if(graph[i][j] == 'S'){
					startNode = new Node(i, j);
					graph[i][j] = '.'; //시작지점도 .으로 바꿔서 탐색 문제 없게 하기
				}
				else if(graph[i][j] == 'C'){
					finishNode.add(new Node(i,j));
				}

			}
		}

		System.out.println(bfs(startNode));


	}
}
