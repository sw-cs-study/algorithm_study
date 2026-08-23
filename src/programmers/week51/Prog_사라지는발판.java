package programmers.week51;

/**
 * 아이디어
 * dfs
 * dfs로 모든 경우를 해보고, 상대가 지는 경우가 하나라도 있으면 나는 최소 횟수로 이겨야 함.
 * 모든 경우에서 상대가 이긴다면, 나는 최대횟수로 져야 함.
 *
 * 나 : A, 이기는 경우가 있다면 최선을 다해서 이김, 즉 상대가 지는 경우가 있다면 무조건 난 이기는 경우중 최소 횟수로 이기는 쪽으로 감.
 */

public class Prog_사라지는발판 {

	//상하좌우
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x,y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int n;
	private static int m;
	private static int[][] maps;

	//이동가능한지 체크 - 격자 판을 벗어나지 않고, 해당 위치가 1이어야 함.
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < n &&
			nextY >= 0 && nextY < m &&
			maps[nextX][nextY] == 1;
	}

	//type : 0이면 a가 움직일차례, type이 1이면 b가 움직일차례
	//return[0] = 1이면 이김, 0이면 짐, return[1] : 움직인 최소 횟수,
	private static int[] dfs(Node aNode, Node bNode, int type){

		//0이면 a차례,
		Node currentNode = type == 0 ? aNode : bNode;

		//현재 위치에 발판이 없으면 - 동시에 같은 곳을 밟다가 한명이 이동하는 경우.
		if(maps[currentNode.x][currentNode.y] == 0){
			return new int[]{0, 0};
		}

		boolean canWin = false;
		int maxMove = 0; //내가 질수밖에 없을때,
		int minMove = Integer.MAX_VALUE; // 내가 이길수 밖에 없을때,

		boolean moveFlag = false;
		for(int i = 0; i < 4; i++){
			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			if(!check(nextX, nextY)) continue;


			moveFlag = true; //true면 이동할 공간이 있음.

			maps[currentNode.x][currentNode.y] = 0;

			int[] winInfo = null;
			//a가 움직일 차례
			if(type == 0){
				winInfo = dfs(new Node(nextX, nextY), bNode, 1);
			}
			else {
				winInfo = dfs(aNode, new Node(nextX, nextY), 0);
			}

			maps[currentNode.x][currentNode.y] = 1;


			//false가 반환됐다는거는, 현재지점의 내가 이기고 상대가 졌다는 뜻, 즉 난 이길 경우가 있다.
			if(winInfo[0] == 0){
				canWin = true;
				minMove = Math.min(minMove, winInfo[1] + 1); //내가 이길수 있다면 최소로 이겨야 함.
			}
			//지는 경우라면 최대한 늦게 지도록
			else {
				maxMove = Math.max(maxMove, winInfo[1] + 1);
			}
		}

		//주위에 이동할 곳이 없으면 패배.
		if(!moveFlag){
			return new int[]{0, 0};
		}

		//이기는 경우가 있으면 최소 이동.
		if(canWin){
			return new int[]{1, minMove};
		}
		else{
			return new int[]{0, maxMove};
		}
	}

	public int solution(int[][] board, int[] aloc, int[] bloc) {

		maps = board;
		n = board.length;
		m = board[0].length;

		//0 : A플레이어, 1: B플레이어
		int[] result = dfs(new Node(aloc[0], aloc[1]), new Node(bloc[0], bloc[1]), 0);

		return result[1];
	}

	public static void main(String[] args){

		Prog_사라지는발판 p = new Prog_사라지는발판();

		int[][] board1 = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
		int[] aloc1 = {1, 0};
		int[] bloc1 = {1, 2};
		System.out.println(p.solution(board1, aloc1, bloc1));

		int[][] board2 = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
		int[] aloc2 = {1, 0};
		int[] bloc2 = {1, 2};
		System.out.println(p.solution(board2, aloc2, bloc2));

		int[][] board3 = {{1, 1, 1, 1, 1}};
		int[] aloc3 = {0,0};
		int[] bloc3 = {0, 4};
		System.out.println(p.solution(board3, aloc3, bloc3));

		int[][] board4 = {{1}};
		int[] aloc4 = {0,0};
		int[] bloc4 = {0,0};
		System.out.println(p.solution(board4, aloc4, bloc4));
	}
}
