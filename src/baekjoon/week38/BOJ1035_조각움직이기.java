package baekjoon.week38;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 아이디어.
 * 완탐
 * 주어진 조각들이 위치할 수 있는 모든 경우를 다 탐색한다.
 * 이때 주의할 점은, 중복된 격자 상태가 나오지 않게 하기 위해서 방문체크를 해야 하는데,
 * 격자판의 방문체크를 위해 5*5배열을 25비트열로 표기를 하는 식으로 한다.
 *
 * 1. 주저진 격자판의 조각위치를 리스트로 만듦
 * 2. 조합을 이용해서 각 조각의 위치를 하나씩 옮겨봄.
 * 3. 조각을 옮겼을때 격자판의 상태를 비트문자열로 만들어서, 추후 탐색시 중복이 발생하지 않도록 함.
 *
 * (수정)
 * 방문을 set으로만 하면 문제가 됨.
 * 문제에서 요구하는거는 최소 이동거리이므로, Map으로 해서 ,value에 이동기를 함께 저장하여 최소값일때만 업데이트 하도록 한다.
 *
 */

public class BOJ1035_조각움직이기 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x,y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int result;
	private static int totalPieceCount;
	private static Map<Integer, Integer> visited;
	private static List<Integer> pieceList;
	private static char[][] maps;

	//격자판으로 비트열 만들고
	private static int mapBinary(){
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){

				if(maps[i][j] == '*'){
					temp.append(1);
				}
				else{
					temp.append(0);
				}
			}
		}

		return Integer.parseInt(temp.toString(), 2);
	}

	//완탐
	private static void recursive(int pieceIdx, int moveCount, Node pieceNode){

		if(bfs(pieceNode)){
			result = Math.min(result, moveCount);
		}

		if(pieceIdx >= pieceList.size()) return;

		//이전에 있는 값보다 더 크면 더 볼필요 없음.
		if(moveCount >= result) return;



		//조각 이동 없이 호출하는경우
		recursive(pieceIdx + 1, moveCount,  pieceNode);


		//조각 위치.
		int currentIdx = pieceList.get(pieceIdx);
		int currentX = currentIdx / 5;
		int currentY = currentIdx % 5;


		//조각을 25칸중 빈칸으로 이동시키는 경우.
		for(int j = 0; j < 25; j++){

			int nextX = j / 5;
			int nextY = j % 5;

			//현재 위치가 아니고 이미 조각이 있는 위치이면 패스.
			if (maps[nextX][nextY] == '*') continue;

			//조객 배치
			maps[currentX][currentY] = '.';
			maps[nextX][nextY] = '*';

			//문자열로 만들기.
			int mapInt = mapBinary();

			int nextMoveCount = moveCount + Math.abs(currentX - nextX) + Math.abs(currentY - nextY);

			//방문한적 없으면 방문처리하고 재귀호출
			if(!visited.containsKey(mapInt) || visited.get(mapInt) >= nextMoveCount){
				visited.put(mapInt, nextMoveCount);
				recursive(pieceIdx + 1, nextMoveCount, new Node(nextX, nextY));
			}
			maps[currentX][currentY] = '*';
			maps[nextX][nextY] = '.';

		}



	}

	//격자 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < 5 &&
			nextY >= 0 && nextY < 5;
	}

	//bfs탐색 - true면 모든 노드가 이어짐.
	private static boolean bfs(Node startNode){

		Queue<Node> needVisited = new LinkedList<>();
		needVisited.add(new Node(startNode.x, startNode.y));

		boolean[][] vs = new boolean[5][5];
		vs[startNode.x][startNode.y] = true;

		int tempCount = 0;

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			tempCount++;

			for(int i = 0; i < 4; i++){
				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY) || vs[nextX][nextY] || maps[nextX][nextY] != '*') continue;

				needVisited.add(new Node(nextX, nextY));
				vs[nextX][nextY] = true;

			}
		}

		return tempCount == totalPieceCount;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder tempBinary = new StringBuilder();
		visited = new HashMap<>();
		totalPieceCount = 0;
		result = Integer.MAX_VALUE;
		maps = new char[5][5];
		pieceList = new ArrayList<>();

		for(int i = 0; i < 5; i++){
			String temp = br.readLine();
			for(int j = 0; j < 5; j++){
				char c = temp.charAt(j);

				maps[i][j] = c;

				if(c == '*'){
					totalPieceCount++;
					tempBinary.append("1");
					pieceList.add(i * 5 + j);
				}
				else{
					tempBinary.append("0");
				}
			}
		}

		// visited.put(Integer.parseInt(tempBinary.toString(), 2), 0);
		recursive(0, 0, new Node(pieceList.get(0) / 5, pieceList.get(0) % 5));
		System.out.println(result);





	}
}
