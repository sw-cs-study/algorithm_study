package baekjoon.week2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 트리
 * 각 위치 별로 칭찬수를 저장해두고,
 * 루트부터 자식노드로 내려가면서, 현재값은 자식노드에 더하는 식으로 한번만 트리를 탐색하도록 한다.
 */

public class BOJ14267_회사문화1 {


	private static int n;
	private static int m;
	private static int[] nodeInfo;
	private static List<Integer>[] graph;
	private static int[] scoreList;

	//bfs로 탐색하면서 하위노드로 내려가면서 저장.
	private static void bfs(){

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(1);

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(int nextNode : graph[currentNode]){

				scoreList[nextNode] += scoreList[currentNode];
				needVisited.add(nextNode);
			}

		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		nodeInfo = new int[n + 1];
		scoreList = new int[n + 1];

		st = new StringTokenizer(br.readLine());

		for(int i = 1; i <= n; i++){
			nodeInfo[i] = Integer.parseInt(st.nextToken());
		}

		//자식노드를 탐색하도록 트리 초기화
		graph = new List[n + 1];
		for (int i = 1; i <= n; i++) {
			graph[i] = new ArrayList<>();
		}

		//트리 값 넣기 - 1번은 사장으로 볼 필요 없음.
		for(int i = 2; i <= n; i++){
			graph[nodeInfo[i]].add(i);
		}

		//점수저장
		for(int a = 0; a < m; a++){
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			scoreList[i] += w;
		}

		bfs();

		StringBuilder result = new StringBuilder();
		for(int i = 1; i <= n; i++){
			result.append(scoreList[i]);

			if(i == n) continue;
			result.append(" ");
		}
		System.out.println(result);
	}
}
