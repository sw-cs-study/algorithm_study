package baekjoon.week22;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 단순 그래프 탐색
 * 트리이므로 그냥 탐색하면 된다.
 *
 * (추가)
 * 반복해서 bfs하는 것만으로도 충분하긴 하지만, lca를 이용해서 최적화 하는 방법을 도입해본다.
 * 우선 lca를 이용해서 각 노드 부모노드 정보와, 루트로 부터의 거리를 구한다.
 * 해당 정보를 토대로 두 노드간의 최소 공통부모를 구하고, 루트 - a노드, 루트 - b 노드를 구하고, 여기에서 루트 - 최소 공통부모 거리를 두번 뺴주면 된다.
 */
public class BOJ1240_노드사이의거리 {

	private static class Node{
		int a, b, weight;

		public Node(int a, int b, int weight){
			this.a = a;
			this.b = b;
			this.weight = weight;
		}
	}

	private static int N;
	private static int M;
	private static List<Node>[] graph;

	private static int H;//트리의 높이
	private static int[] depth;//트리의 깊이 - 최소 공통부모를 찾기 위해서, 두 노드의 높이를 같게 맞출때 사용.
	private static int[] distance; //루트노드부터 각 노드간의 거리를 저장함.
	private static int[][] dp;

	//lca 계산을 위한 트리높이, 및 거리 초기화 - 파라미터에는 prevNode를 넣어서 이미 탐색한 부모노드를 탐색하지 않도록 함.
	private static void init(int currentNode, int height, int prevNode){

		depth[currentNode] = height; //현재노드의 높이 넣기.

		for(Node nextNode : graph[currentNode]){

			//이전에 탐색한 노드이면 패스
			if (nextNode.b == prevNode) continue;

			distance[nextNode.b] = distance[currentNode] + nextNode.weight;//해당 노드까지의 거리 저장.
			dp[nextNode.b][0] = currentNode;//부모노드 정보를 저장함.
			init(nextNode.b, height + 1, currentNode);//재귀호출.

		}


	}

	//dp배열 구성 -> 행은 노드, 열은 해당 노드에서 몇번째 부모인지, 예를 들면 0이면 바로 위, 1이면 그 위, 이런식으로 부모정보들을 다 저장.
	//부모 정보를 전부 저장해야, 추후에 두 노드 간 높이를 맞춘후에 최소 공통부모를 찾을 수 있음.
	private static void parentSetting(){

		for (int i = 1; i < H; i++){
			for(int j = 1; j <= N; j++){
				dp[j][i] = dp[dp[j][i - 1]][i - 1];
			}
		}
	}

	//LCA를 이용해서 최소공통부모 찾기. - 두 노드의 높이가 같아질때까지 부모를 탐색, 높이가 같고 두 값이 같으면 해당 노드가 공통부모
	private static int lca(int nodeA, int nodeB){

		//두 노드의 현재 높이를 가져옴
		int depthA = depth[nodeA];
		int depthB = depth[nodeB];

		//계산하기 편하게, nodeA를 높이가 더 큰 쪽(깊이가 더 깊은)으로 만듦
		if(depthA < depthB){
			int temp = nodeA;
			nodeA = nodeB;
			nodeB = temp;
		}

		//두 노드의 깊이 차이 - 이 차이만큼 node 노드가 올라가야 함.
		for(int i = H - 1; i >= 0; i--){

			//2^i만큼 점프가 불가능하면 하나 줄여봄.
			if((int)Math.pow(2, i) > depth[nodeA] - depth[nodeB]) continue;

			nodeA = dp[nodeA][i];
		}

		//두 노드가 같아졌다면 둘중 하나를 반환
		if(nodeA == nodeB) return nodeA;

		//깊이가 깊은 노드를 위로 올려봤음에도 공통부모를 못찾았으면, 둘다 높이를 올려봐야 함.
		for(int i = H - 1; i >= 0; i--){
			if(dp[nodeA][i] != dp[nodeB][i]){
				nodeA = dp[nodeA][i];
				nodeB = dp[nodeB][i];
			}

		}

		return dp[nodeA][0];
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		H = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;//트리 높이 초기화 => log2(N);
		depth = new int[N + 1];
		distance = new int[N + 1];
		dp = new int[N + 1][H];

		for(int i = 1; i <= N ; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());
			int nodeWeight = Integer.parseInt(st.nextToken());

			graph[nodeA].add(new Node(nodeA, nodeB, nodeWeight));
			graph[nodeB].add(new Node(nodeB, nodeA, nodeWeight));
		}

		init(1,1, 0);
		parentSetting();

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int tempA = Integer.parseInt(st.nextToken());
			int tempB = Integer.parseInt(st.nextToken());

			//두 노드의 공통 높이 구하기.
			int commonNode = lca(tempA, tempB);

			//루트에서 각 노드 거리의 합 - (루트에서 공통 노드까지의 거리* 2)
			int dis = distance[tempA] + distance[tempB] - (distance[commonNode] * 2);
			result.append(dis);

			if(i == M - 1) continue;
			result.append("\n");
		}

		System.out.println(result);
	}
}

