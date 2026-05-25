package programmers.week45;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * 아이디어
 * 빡 구현
 * bfs를 이용해서 연결된 앱을 찾고, 해당 하는 앱들의 좌표를 전부 구해서,
 * 한번에 1칸을 동시에 이동하도록 처리함.
 *
 * (주의)
 * 크기가 2*2 이상인 앱은 한칸이라도 밖으로 나가면 반대쪽으로 이동,
 * 즉, 이동이 완료된 후, 한칸이라도 격자 밖으로 나가서 반대쪽으로 온 노드들을 찾아서 한번더 처리하는 로직이 필요함.
 *
 */

public class Prog_카카오앱정리 {

	//방향 - 1은 오른쪽, 2는 아래쪽, 3은 왼쪽, 4는 위쪽 방향
	private final static int[] dx = {0, 0, 1, 0, -1};
	private final static int[] dy = {0, 1, 0, -1, 0};

	//노드
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//가로 세로.
	private static int n;
	private static int m;


	//이동할 노드 정보 구하기.
	private static Set<Integer> getMoveNode(int[][] board, Set<Integer> idSet, int dir){

		Set<Integer> nodeSet = new HashSet<>(idSet);
		Queue<Integer> needVisited = new ArrayDeque<>(idSet);

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			//모든 위치를 다 확인해야 함.
			for(int i = 0; i < n; i++){
				for(int j = 0; j < m; j++){

					//현재 탐색할 노드 번호가 아니면 패스
					if(board[i][j] != currentNode) continue;

					//주어진 방향으로 한칸 이동 - 왼쪽이나 상단 이동으로 음수 나올 것을 고려해서 격자 최대길이를 더하고, mod 연산함.
					int nextX = (i + dx[dir] + n) % n;
					int nextY = (j + dy[dir] + m) % m;

					//해당 위치의 값이 이미 체크한 값이거나 0이면 패스
					if(board[nextX][nextY] == 0 || nodeSet.contains(board[nextX][nextY])) continue;

					nodeSet.add(board[nextX][nextY]);
					needVisited.add(board[nextX][nextY]);
				}
			}
		}

		return nodeSet;
	}


	//노드 이동 처리.
	private static int[][] updateMoveNode(int[][] board, Set<Integer> nodeSet, int dir){

		//임시 배열을 하나 만듦
		int[][] tmp = new int[n][m];

		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){

				if(board[i][j] == 0) continue;

				if(nodeSet.contains(board[i][j])){
					int nextX = (i + dx[dir] + n) % n;
					int nextY = (j + dy[dir] + m) % m;

					tmp[nextX][nextY] = board[i][j];
				}
				else{
					tmp[i][j] = board[i][j];
				}


			}
		}

		return tmp;

	}


	//이동 후 잘린 노드를 찾는 메서드
	private static Set<Integer> getCuttingNode(int[][] board, int dir){

		Set<Integer> nodeSet = new HashSet<>();

		//오른쪽이나 왼쪽인 경우.
		if(dir == 1 || dir == 3){
			for(int i = 0; i < n; i++){

				int leftId = board[i][0];
				int rightId = board[i][m - 1];

				//양 끝값이 다르면 패스.
				if((leftId == 0 || rightId == 0) || leftId != rightId) continue;

				//이전에 이미 추가한 값이면 패스.
				if(nodeSet.contains(leftId)) continue;

				//모든 컬럼을 다 확인해서 하나라도 leftId가 아닌게 있어야 추가.
				//만약 모든 컬럼이 전부 leftId이면 잘린게 아님.
				boolean flag = false;
				for(int j = 0; j < m; j++){

					if(board[i][j] == leftId) continue;

					flag = true;
					break;

				}

				//모든 컬럼 확인했을때 잘린블럭이 아니라면 패스.
				if(!flag) continue;

				nodeSet.add(leftId);

			}
		}
		else if(dir == 2 || dir == 4){
			for(int i = 0; i < m; i++){

				int upId = board[0][i];
				int downId = board[n - 1][i];

				if((upId == 0 || downId == 0) || upId != downId) continue;

				if(nodeSet.contains(upId)) continue;

				//모든 로우을 다 확인해서 하나라도 upId가 아닌게 있어야 추가.
				//만약 모든 로우이 전부 upId이면 잘린게 아님.
				boolean flag = false;
				for(int j = 0; j < n; j++){

					if(board[j][i] == upId) continue;

					flag = true;
					break;

				}

				//모든 로우 확인했을때 잘린블럭이 아니라면 패스.
				if(!flag) continue;

				nodeSet.add(upId);

			}
		}


		return nodeSet;
	}


	public int[][] solution(int[][] board, int[][] commands) {

		n = board.length;
		m = board[0].length;

		for(int[] command : commands){

			int id = command[0];
			int dir = command[1];

			Set<Integer> tmp = new HashSet<>();
			tmp.add(id);

			Set<Integer> moveNodeSet = getMoveNode(board, tmp, dir);

			board = updateMoveNode(board, moveNodeSet, dir);

			//잘린 블럭 처리.
			while(true){
				Set<Integer> cuttingNode = getCuttingNode(board, dir);
				if(cuttingNode.isEmpty()) break;

				Set<Integer> moveCuttingNode = getMoveNode(board, cuttingNode, dir);
				board = updateMoveNode(board, moveCuttingNode, dir);
			}

		}

		return board;
	}

	public static void main(String[] args){

		Prog_카카오앱정리 p = new Prog_카카오앱정리();

		int[][] board1 = {
			{0, 2, 2, 0, 0, 0, 0, 0},
			{0, 2, 2, 0, 0, 4, 4, 0},
			{0, 3, 3, 3, 1, 4, 4, 0},
			{0, 3, 3, 3, 0, 0, 0, 0},
			{0, 3, 3, 3, 5, 5, 6, 0},
			{0, 0, 0, 0, 5, 5, 0, 0}
		};
		int[][] command1 = {{3, 1}, {3, 1}};
		int[][] result1 = {
			{0, 0, 2, 2, 0, 0, 0, 0},
			{4, 4, 2, 2, 0, 0, 0, 0},
			{4, 4, 0, 3, 3, 3, 1, 0},
			{0, 0, 0, 3, 3, 3, 0, 0},
			{6, 0, 0, 3, 3, 3, 5, 5},
			{0, 0, 0, 0, 0, 0, 5, 5}
		};

		int[][] board2 = {{0, 9, 1, 1, 6, 0, 0, 0}, {2, 2, 1, 1, 0, 0, 0, 0}, {2, 2, 3, 4, 4, 4, 0, 0}, {5, 0, 0, 4, 4, 4, 7, 0}, {0, 0, 0, 4, 4, 4, 8, 8}, {0, 0, 0, 0, 0, 0, 8, 8}};
		int[][] command2 = {{2, 1}, {3, 1}, {9, 2}, {4, 1}};
		int[][] result2 = {
			{8, 8, 0, 1, 1, 6, 0, 0},
			{8, 8, 0, 1, 1, 0, 0, 0},
			{4, 4, 4, 9, 3, 0, 0, 0},
			{4, 4, 4, 7, 2, 2, 0, 0},
			{4, 4, 4, 0, 2, 2, 0, 0},
			{0, 5, 0, 0, 0, 0, 0, 0}
		};


		int[][] board3 = {
			{1, 1, 0},
			{1, 1, 0}
		};
		int[][] command3 = {{1, 4}, {1, 3}, {1, 2}};
		int[][] result3 = {
			{0, 1, 1},
			{0, 1, 1}
		};

		int[][] tmp = p.solution(board3, command3);
		for(int i = 0; i < n; i++){
			System.out.println(Arrays.toString(tmp[i]));
		}

		//1은 오른쪽, 2는 아래쪽, 3은 왼쪽, 4는 위쪽 방향
	}
}
