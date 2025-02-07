package baekjoon.week3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * 아이디어
 * bfs + 구현
 * 각 보드판의 검/흰 상태를 노드로 생각한다.
 * 각 칸을 눌렀을때, 색이 변한 상태를 다음 노드로 보면, 그래프 처럼 생각할수 있고, bfs를 통해서 모든 경우를 탐색할 수 있다.
 *
 * (추가)
 * 보드판이 중복되지 않도록 체크하기 위해서, 방문체크를 해야 하는데, 2차원 배열 형태면 체크가 어렵다.
 * 3*3이고 검은색 하얀색이 구분되어있기 때문에 이를 0,1의 비트 문자열로 변경해서 처리하도록 한다.
 * 예를 들면,
 * 1 0 0
 * 1 1 0
 * 1 0 0
 * => 100110100 으로 표현을 한다면, 해당 문자열을 set자료구조에 넣어서 중복 체크를 O(1)로 처리가 가능해진다.
 */

public class BOJ10472_십자뒤집기 {

	//2진수 문자열에서 4방향 - 현재, 상, 하.
	private final static int[] disX = {0, -3, 3};
	private final static int[] disY = {1, -1};

	//탐색할 노드.
	private static class Node{
		int count;
		String value;

		public Node(int count, String value){
			this.count = count;
			this.value = value;
		}
	}

	//주어진 배열을 2진수로 만드는 메서드
	private static String arrayToBinary(char[][] maps){

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){

				if(maps[i][j] == '*'){
					result.append("1");
				}
				else{
					result.append("0");
				}
			}
		}

		return result.toString();
	}

	//클릭시, 2진수 문자열에서 색 변환하는 메서드. - index : 누를 칸, binaryStr : 색칠된 지도.
	private static String changeColor(int index, String binaryStr){

		//내부 문자열 변경이 쉽도록 배열로 만듦
		char[] binaryArray = binaryStr.toCharArray();

		//변경할 인덱스는 상하 -> +-3,
		//변경할 인덱스가 0 ~ 8(3*3 배열이므로)을 넘어가지 않으면 변경.
		/**
		 * 예시
		 * 0 1 2
		 * 3 4 5
		 * 6 7 8
		 * => 해당 2차원 배열을 1차원 배열로 내리게 되면, 각 칸의 인덱스는 위와 같음.
		 */
		//현재위치 + 상,하 확인
		for(int i = 0; i < 3; i++){

			int nextIndex = index + disX[i];

			//격자판을 벗어나는 값이라면 패스
			if(nextIndex < 0 || nextIndex > 8) continue;

			//해당칸의 색을 토글
			binaryArray[nextIndex] = binaryArray[nextIndex] == '0' ? '1' : '0';
		}

		//좌우 처리.
		int loopCount = index % 3;

		if(loopCount == 0){
			int nextIndex = index + disY[0];
			binaryArray[nextIndex] = binaryArray[nextIndex] == '0' ? '1' : '0';
		}
		else if(loopCount == 2){
			int nextIndex = index + disY[1];
			binaryArray[nextIndex] = binaryArray[nextIndex] == '0' ? '1' : '0';
		}
		else{
			for(int i = 0; i < 2; i++){
				int nextIndex = index + disY[i];
				binaryArray[nextIndex] = binaryArray[nextIndex] == '0' ? '1' : '0';
			}
		}

		return String.valueOf(binaryArray);
	}

	//bfs 메서드
	private static int bfs(String targetStr){

		String initValue = "000000000";

		Set<String> visited = new HashSet<>();
		visited.add(initValue); //초기 흰색.

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(0, initValue));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//목표하는 상태랑 같다면
			if(currentNode.value.equals(targetStr)) return currentNode.count;

			//총 9개의 칸을 누를 수 있음.
			for(int i = 0; i < 9; i++){

				String nextValue = changeColor(i, currentNode.value);

				if(visited.contains(nextValue)) continue;

				needVisited.add(new Node(currentNode.count + 1, nextValue));
				visited.add(currentNode.value);
			}
		}
		return -1;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int p = Integer.parseInt(br.readLine());
		StringBuilder result = new StringBuilder();

		char[][] maps;
		for(int i = 0; i < p; i++){
			maps = new char[3][3];
			for(int j = 0; j < 3; j++){
				maps[j] = br.readLine().toCharArray();
			}

			result.append(bfs(arrayToBinary(maps))).append("\n");
		}

		System.out.println(result);
	}
}

