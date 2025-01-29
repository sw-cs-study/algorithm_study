package baekjoon.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs
 * dfs지만, 노드를 탐색하면서 연산을 진행해야 한다.
 * 객체에 이전값과, 연산자를 저장하도록 하고, 이전값과 연산자가 존재하면 계산하도록 한다.
 * 문제에서 숫자 연산자를 번갈아가면서 등장하게 했기 때문에, 숫자->숫자나 연산자->연산자가 나오는 경우는 고려하지 않는다.
 */

public class BOJ17265_나의인생에는수학과함께 {

	//오른쪽과 아래로만 이동.
	private final static int[] dx = {0, 1};
	private final static int[] dy = {1, 0};


	//탐색을 위한 객체
	private static class Node{
		int x, y, value;
		char op;

		public Node(int x, int y, int value){
			this.x = x;
			this.y = y;
			this.value = value;
		}

		public Node nextNode(int x, int y,char nextValue){

			Node nextNode = new Node(x, y, this.value);

			//숫자면 연산해야 함.
			if(Character.isDigit(nextValue)){
				nextNode.op = this.op;
				nextNode.calNode(Character.getNumericValue(nextValue));
			}
			else{
				nextNode.op = nextValue;
			}

			return nextNode;
		}

		//연산자를 가지고 계산
		public void calNode(int nextValue){
			if(this.op == '+'){
				this.value += nextValue;
			}
			else if(this.op == '-'){
				this.value -= nextValue;
			}
			else if(this.op == '*'){
				this.value *= nextValue;
			}
			else{
				throw new RuntimeException("not allowed operator");
			}
		}
	}

	//n크기
	private static int N;

	//맵 정보가 담긴 배열
	private static char[][] maps;

	//목적지의 최대값
	private static int maxValue;
	//목적지의 최소값,
	private static int minValue;

	//배열을 벗어나지 않는지 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < N;
	}

	//dfs 탐색을 할 메서드
	private static void dfs(Node currentNode){

		//목표에 도달하면 최대값과 최소값을 업데이트 함,.
		if(currentNode.x == N - 1 && currentNode.y == N - 1){
			maxValue = Math.max(maxValue, currentNode.value);
			minValue = Math.min(minValue, currentNode.value);
			return;
		}

		for(int i = 0; i < 2; i++){

			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			if(!check(nextX, nextY)) continue;

			dfs(currentNode.nextNode(nextX,nextY, maps[nextX][nextY]));

		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());
		maps = new char[N][N];

		maxValue = Integer.MIN_VALUE;
		minValue = Integer.MAX_VALUE;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				maps[i][j] = st.nextToken().charAt(0);
			}
		}

		dfs(new Node(0,0, Character.getNumericValue(maps[0][0])));

		System.out.println(maxValue + " " + minValue);
	}
}
