package baekjoon.week39;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 위상정렬
 *
 * 노드갸 문자열이므로, map을 이용해서 진입차수를 계산한다.
 *
 * 1. 구매안한 아이템을 찾음 -> 진입차수가 0인것을 찾음.
 * 2. 1번에서 찾은 아이템중 사전순으로 구매함 -> 연결관계 저장시에 정렬해서 저장.
 *
 * (추가)
 * 현재 구매 가능한 아이템 목록 확인 -> 전부 구매
 * 위의 로직을 반복하기 때문에, 각 노드의 레벨을 저장해야 함.
 * pq에 넣어서 처리하기 위해서, 진입차수가 된 시점을 저장하여, 시점순으로 정렬을 할 수 있도록 해야 함.
 * 별도의 객체를 만들고 각 노드의 레벨을 저장해서, 다음 노드를 추가할떄 레벨을 +1하는 식으로 구현함.
 * pq는 레벨을 먼저 보고, 노드 값을 보는 식으로 정렬기준을 잡음.
 */
public class BOJ23059_리그오브레게노 {

	private static class Node{
		int level;
		String node;

		public Node(int level, String node){
			this.level = level;
			this.node = node;
		}
	}


	private static int N;
	private static Map<String, Integer> inDegree;
	private static Map<String, List<String>> graph;
	private static int totalNodeCount;


	//위상정렬 -> 구매한 아이템리스트를 문자열로 만들어서 반환.
	private static String topologySort(){

		StringBuilder result = new StringBuilder();

		PriorityQueue<Node> needVisited = new PriorityQueue<>((node1, node2) -> {

			if(node1.level == node2.level){
				return node1.node.compareTo(node2.node);
			}

			return node1.level - node2.level;
		});

		// Set<String> visited = new HashSet<>();

		//진입차수가 0인 것들을 뽑아서 큐에 넣기.
		for(Map.Entry<String,Integer> node : inDegree.entrySet()){

			if(node.getValue() > 0) continue;

			needVisited.add(new Node(0, node.getKey()));
		}


		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			result.append(currentNode.node).append("\n");
			totalNodeCount++;


			for(String nextNode : graph.get(currentNode.node)){

				inDegree.put(nextNode, inDegree.get(nextNode) - 1);
				// if(visited.contains(nextNode) || inDegree.get(nextNode) != 0) continue;
				if(inDegree.get(nextNode) != 0) continue;

				// visited.add(nextNode);
				needVisited.add(new Node(currentNode.level + 1, nextNode));
			}
		}

		return result.toString();
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		inDegree = new HashMap<>();
		graph = new HashMap<>();

		for(int i = 0; i < N; i++){

			st = new StringTokenizer(br.readLine());

			String nodeA = st.nextToken();
			String nodeB = st.nextToken();

			//진입차수 저장.
			inDegree.put(nodeA, inDegree.getOrDefault(nodeA, 0));
			inDegree.put(nodeB, inDegree.getOrDefault(nodeB, 0) + 1);

			//그래프 구성.
			if(!graph.containsKey(nodeA)){
				graph.put(nodeA, new ArrayList<>());
			}
			graph.get(nodeA).add(nodeB);

			if(!graph.containsKey(nodeB)){
				graph.put(nodeB, new ArrayList<>());
			}
		}

		String resultString = topologySort();

		if(resultString.isEmpty() || totalNodeCount != inDegree.size()){
			System.out.println(-1);
		}
		else {
			System.out.println(resultString);
		}
	}
}
