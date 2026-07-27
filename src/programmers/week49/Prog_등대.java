package programmers.week49;

import java.util.ArrayList;
import java.util.List;

/**
 * 아이디어
 * dfs + dp
 */
public class Prog_등대 {

	private static List<Integer>[] graph;//그래프.
	private static int[][] dp;//dp 배열

	//dfs
	private static void dfs(int prevNode, int currentNode){

		//현재 노드정보 추가.
		dp[currentNode][0] = 0; //등대를 안켜는 경우.
		dp[currentNode][1] = 1; //등대를 켜는 경우.

		for(int nextNode : graph[currentNode]){

			//이미 방문한 이전 노드라면 패스.
			if(nextNode == prevNode) continue;

			//자식 노드부터 재귀호출하여, 리프노드까지 이동, 반환하면서 현재 위치 확인.
			//현재노드의 등대 켠 개수는 누적되어야 하기 때문에 자식노드부터 확인.
			dfs(currentNode, nextNode);

			//현재 위치에 등대를 안켜면 연결된 노드는 등대를 무조건 켜야 함.
			dp[currentNode][0] += dp[nextNode][1];

			//현재 위치에 등대를 켜면, 연결된 노드는 등대를 켤수도 안켤수도 있는데,
			//문제에서 구하고자 하는 값은 켠 등대의 최소 값이므로, 둘중 작은값으로 업데이트 해야 함.
			dp[currentNode][1] += Math.min(dp[nextNode][0], dp[nextNode][1]);
		}

	}

	//초기 값 구성.
	private static void init(int n, int[][] lighthouse){

		graph = new List[n + 1];
		dp = new int[n + 1][2];

		for(int i = 1; i <= n; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < lighthouse.length; i++){

			int[] node = lighthouse[i];

			graph[node[0]].add(node[1]);
			graph[node[1]].add(node[0]);

		}
	}


	public int solution(int n, int[][] lighthouse) {
		int answer = 0;

		init(n,lighthouse);
		dfs(-1, 1);

		return Math.min(dp[1][0], dp[1][1]);
	}


	public static void main(String[] args){

		Prog_등대 p = new Prog_등대();

		int n1 = 8;
		int[][] lighthouse1 = {{1, 2}, {1, 3}, {1, 4}, {1, 5}, {5, 6}, {5, 7}, {5, 8}};
		System.out.println(p.solution(n1, lighthouse1));


		int n2 = 10;
		int[][] lighthouse2 = {{4, 1}, {5, 1}, {5, 6}, {7, 6}, {1, 2}, {1, 3}, {6, 8}, {2, 9}, {9, 10}};
		System.out.println(p.solution(n2, lighthouse2));

	}
}
