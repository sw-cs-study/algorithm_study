package programmers.week52;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 아이디어
 * 트리 + 구현.
 */
public class Prog_홀짝트리 {

	private static class Node{
		int node, edgeCount;
		public Node (int node){
			this.node = node;
			this.edgeCount = 0;
		}

		public void addEdgeCount(){
			edgeCount++;
		}
	}
	private static Map<Integer, Node> nodeLists;//노드 리스트.
	private static Map<Integer,List<Integer>> graph;//그래프
	private static Map<Integer, Integer> visited;//방문처리.

	//bfs - bfs로 탐색하면서 하나의 트리 구조를 탐색하고, 홀짝노드, 역홀짝노드 수를 셈.
	//return {홀짝노드 수, 역홀짝노드 수.}
	private static int[] bfs(int startNode){

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		visited.put(startNode, 1);

		int originCount = 0; //홀짝노드
		int reverseCount = 0; //역홀짝노드 수.

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			//홀짝노드 수 구하기.
			Node temp = nodeLists.get(currentNode);
			//둘다 짝수거나 홀수 이면 홀짝노드 아니면 역홀짝,
			int t = (temp.node % 2 == temp.edgeCount % 2) ?
				originCount++ : reverseCount++;

			for(int nextNode : graph.get(currentNode)){

				if(visited.containsKey(nextNode)) continue;

				visited.put(nextNode, 1);
				needVisited.add(nextNode);
			}

		}

		return new int[]{originCount, reverseCount};
	}

	//초기 값 설정
	private static void init(int[] nodes, int[][] edges){

		nodeLists = new HashMap<>();
		graph = new HashMap<>();
		visited = new HashMap<>();

		for(int node : nodes){
			nodeLists.put(node, new Node(node));
			graph.put(node, new ArrayList<>());
		}

		for(int[] edge : edges){

			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);

			nodeLists.get(edge[0]).addEdgeCount();
			nodeLists.get(edge[1]).addEdgeCount();
		}

	}

	public int[] solution(int[] nodes, int[][] edges) {
		int[] answer = new int[2]; // 홀짝, 역홀짝,

		init(nodes, edges);
		for(int node : nodes){

			if(visited.containsKey(node)) continue;

			int[] tempArrays = bfs(node);

			//홀짝노드가 1개이면, 홀짝 가능 / 역홀짝 노드가 1개이면, 역홀짝 가능.
			//둘다 1이면 둘다 가능.
			if(tempArrays[0] == 1 && tempArrays[1] == 1) {
				answer[0]++;
				answer[1]++;
			}
			else if(tempArrays[0] == 1) {
				answer[0]++;
			}
			else if(tempArrays[1] == 1){
				answer[1]++;
			}
		}


		return answer;
	}


	public static void main(String[] args){

		Prog_홀짝트리 p = new Prog_홀짝트리();

		int[] nodes1 = {11, 9, 3, 2, 4, 6};
		int[][] edges1 = {{9, 11}, {2, 3}, {6, 3}, {3, 4}};
		System.out.println(Arrays.toString(p.solution(nodes1, edges1)));

		int[] nodes2 = {9, 15, 14, 7, 6, 1, 2, 4, 5, 11, 8, 10};
		int[][] edges2 = {{5, 14}, {1, 4}, {9, 11}, {2, 15}, {2, 5}, {9, 7}, {8, 1}, {6, 4}};
		System.out.println(Arrays.toString(p.solution(nodes2, edges2)));
	}
}
