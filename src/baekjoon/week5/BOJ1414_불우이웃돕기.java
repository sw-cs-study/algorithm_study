package baekjoon.week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * MST
 *
 * 모든 컴퓨터가 연결되어있어야 하고, 기부할 간선이 최대가 되려면 연결되는 간선의 가중치의 합이 최소가 되어야 한다.
 * 크루스칼 알고리즘은 모든 노드를 연결해야 하며, 그중 가중치가 작은 것들로 찾는다.
 */

public class BOJ1414_불우이웃돕기 {

	//다익스트라 탐색을 위한 노드
	private static class Edge{
		int nodeA, nodeB, weight;

		public Edge(int nodeA, int nodeB, int weight){
			this.nodeA = nodeA;
			this.nodeB = nodeB;
			this.weight = weight;
		}
	}

	private static int N;//노드 수.
	private static int totalWeight;//총 간선 합.
	private static List<Edge> edges;//간선 배열..

	private static int[] parents;//부모정보 저장.

	//부모찾는 메서드
	private static int findParent(int node){

		//저장된 부모가 자신이라면 더 탐색할 필요 없음.
		if(parents[node] == node) return node;

		//아니라면 부모를 반환함.
		return parents[node] = findParent(parents[node]);
	}

	//union
	private static void union(int nodeA, int nodeB){

		int parentA = findParent(nodeA);
		int parentB = findParent(nodeB);

		if (parentA < parentB){
			parents[parentB] = parentA;
		}
		else {
			parents[parentA] = parentB;
		}
	}

	//크루스칼 수행.
	private static int kruskal(){

		edges.sort(Comparator.comparingInt(e -> e.weight)); //간선가중치가 작은 순으로 정렬

		int weightCount = 0;
		int edgeCount = 0;
		for(Edge edge : edges){

			int parentA = findParent(edge.nodeA);
			int parentB = findParent(edge.nodeB);

			//같으면 패스
			if(parentA == parentB) continue;

			union(edge.nodeA, edge.nodeB);

			edgeCount++;
			weightCount += edge.weight;

			if(edgeCount == N - 1) break;

		}

		if(edgeCount != N - 1) return -1;

		return weightCount;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		edges = new ArrayList<>();
		totalWeight = 0;

		for(int i = 1; i <= N; i++){
			String temp = br.readLine();
			for(int j = 1; j <= N; j++){

				char chr = temp.charAt(j - 1);

				//0이면 연결이 안됨.
				if(chr == '0') continue;

				int weight = 0;

				if(Character.isUpperCase(chr)){
					weight = (chr - 'A') + 27;
				}
				else{
					weight = (chr - 'a') + 1;
				}

				edges.add(new Edge(i, j, weight));

				totalWeight += weight;

			}
		}

		parents = new int[N + 1];
		for(int i = 1; i <= N; i++){
			parents[i] = i;
		}

		int resultWeight = kruskal();
		if(resultWeight == -1) {
			System.out.println(-1);
		}
		else{
			System.out.println(totalWeight - resultWeight);
		}

	}
}
