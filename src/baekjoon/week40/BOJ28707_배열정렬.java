package baekjoon.week40;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 다익스트라.
 *
 * 아이디어만 참고함.
 * 배열 상태를 노드로 보고, 다음 노드는 주어진 숫자 변환조건에 따라 배열을 변환시킨 배열이다.
 * bfs로도 생각할 수 있지만, 한번 방문했다고 해서 그것이 최소가 되지 않는다,
 * cost가 있기 때문에 다익스트라로 생각하고 진행한다.
 * 이때 문제는 방문노드에 대한 코스트를 업데이트해야 하는데, 노드가 배열이므로 이를 표현하는 것이 문제이다.
 * 배열을 문자열로 만들어서 해시에 저장하는 식으로 처리한다.
 * 배열의 길이가 최대 8이므로 매번 배열에서 수를 교환하고 문자열로 만들어도 큰 부하가 걸리지 않는다ㅣ.
 */
public class BOJ28707_배열정렬 {

	private final static int INF = 1_000_000;

	private static class Node{
		String nodeStr;
		int[] nodeArray;
		int count;

		public Node(String nodeStr, int[] nodeArray, int count) {
			this.nodeStr = nodeStr;
			this.nodeArray = nodeArray;
			this.count = count;
		}
	}

	private static class Edge{
		int a, b, weight;
		public Edge(int a, int b, int weight){
			this.a = a;
			this.b = b;
			this.weight = weight;
		}
	}


	private static int N;//배열의 길이.
	private static int[] numArray;//배열
	private static int M;//조작의 개수
	private static Edge[] manipulations; //조작 리스트.


	//숫자 교체 메서드
	private static void numChange(int a, int b, int[] numArray){

		int temp = numArray[a - 1];
		numArray[a - 1] = numArray[b - 1];
		numArray[b - 1] = temp;

	}

	//배열 -> 문자열로 만들기.
	private static String arrayCombine(int[] numArray){
		StringBuilder temp = new StringBuilder();

		for(int num : numArray){
			temp.append(num);
		}

		return temp.toString();
	}

	//다익스트라.
	private static int dijkstra(Node startNode, String target){

		Map<String, Integer> visited = new HashMap<>();
		visited.put(startNode.nodeStr, 0);

		PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) -> {

			return node1.count - node2.count;
		});

		pq.add(startNode);

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.count > visited.get(currentNode.nodeStr)) continue;

			for(int i = 0; i < M; i++){

				Edge edge = manipulations[i];

				int[] nextNodeArray = Arrays.copyOf(currentNode.nodeArray, currentNode.nodeArray.length);
				numChange(edge.a, edge.b, nextNodeArray);

				String nextNodeStr = arrayCombine(nextNodeArray);

				int nextCost = currentNode.count + edge.weight;

				if(visited.getOrDefault(nextNodeStr, INF) <= nextCost) continue;

				visited.put(nextNodeStr, nextCost);
				pq.add(new Node(nextNodeStr, nextNodeArray, nextCost));

			}
		}

		return visited.getOrDefault(target, -1);

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());
		numArray = new int[N];
		st = new StringTokenizer(br.readLine());

		for(int i = 0; i < N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		M = Integer.parseInt(br.readLine());
		manipulations = new Edge[M];
		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			manipulations[i] = new Edge(a, b, cost);
		}

		int[] targetArray = Arrays.copyOf(numArray, numArray.length);
		Arrays.sort(targetArray);
		String target = arrayCombine(targetArray);

		Node startNode = new Node(
			arrayCombine(numArray),
			numArray,
			0
		);
		System.out.println(dijkstra(startNode, target));

	}
}
