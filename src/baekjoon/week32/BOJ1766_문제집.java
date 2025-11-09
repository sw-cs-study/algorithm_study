package baekjoon.week32;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 위상정렬
 *
 * 기본적인 위상정렬로 해결을 하되, 3번 조건을 만족시키기 위해서 우선순위큐를 이용하여 쉬운문제를 먼저 풀도록 한다.
 */

public class BOJ1766_문제집 {

	private static int N;//문제수
	private static int M;// 먼저 풀면 좋은 문제수.

	private static int[] inDegree;//진입 차수

	private static List<Integer>[] graph;//연결을 나타내는 그래프.

	//위상 정렬
	private static String topologySort(){

		StringBuilder result = new StringBuilder();
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int i = 1; i <= N; i++){

			if(inDegree[i] != 0) continue;

			pq.add(i);
		}

		while(!pq.isEmpty()){

			int currentNode = pq.poll();

			result.append(currentNode).append(" ");

			for(int nextNode : graph[currentNode]){

				inDegree[nextNode]--;

				if(inDegree[nextNode] != 0) continue;

				pq.add(nextNode);
			}
		}

		return result.toString();
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		inDegree = new int[N + 1];
		graph = new List[N + 1];

		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			inDegree[b]++;
			graph[a].add(b);

		}

		System.out.println(topologySort());
	}
}
