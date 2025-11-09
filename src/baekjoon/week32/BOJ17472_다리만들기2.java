package baekjoon.week32;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs + MST
 * 크루스칼 알고리즘을 이용해서 해결한다.
 * 우선 bfs 탐색을 통해서 각 섬별 정보를 저장한다.
 * 섬 번호, 가장자리 노드들을 저장해두고, 가장자리에서 한 방향으로 쭉 이동했을때 길이와 연결되는 노드 정보를 저장하여,
 * 섬을 노드, 다리를 간선으로 보고 모든 정보를 저장한 후, 크루스칼 알고리즘을 사용해서 처리한다.
 * => 그래프의 정보를 구해서 mst를 푸는 문제로, 그래프만 구성되면 mst를 구하기만 하면 된다.
 */
public class BOJ17472_다리만들기2 {

	//4방향
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	//노드
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//간선
	private static class Edge{
		int node1, node2, weight;

		public Edge(int node1, int node2, int weight){
			this.node1 = node1;
			this.node2 = node2;
			this.weight = weight;
		}
	}

	private static int N;//세로
	private static int M;//가로
	private static int[][] maps;//지도
	private static List<Edge> edges;//간선 정보
	private static int[] parents;//부모 노드 정보.

	private static int totalNode;//총 노드 수.

	private static boolean[][] edgeVisited; // 크루스칼로 간선 구성시 중복노드 제거를 위함.

	//bfs 과정에서 격자판을 벗어나지 않는지 확인하는 메서드
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	//최대 부모 구하기.
	private static int findParent(int node){

		if(node == parents[node]) return node;

		return parents[node] = findParent(parents[node]);
	}

	//union find
	private static boolean union(int node1, int node2){

		int findNode1 = findParent(node1);
		int findNode2 = findParent(node2);

		if(findNode1 < findNode2){
			parents[findNode2] = findNode1;
		}
		else if(findNode2 < findNode1){
			parents[findNode1] = findNode2;
		}

		else return false;

		return true;
	}

	//크루스칼 알고리즘
	private static int kruskal(){

		int count = 0;
		int edgeCount = 0;

		for(Edge edge : edges){

			if(edgeVisited[edge.node1][edge.node2] || edgeVisited[edge.node2][edge.node1]) continue;
			if(!union(edge.node1, edge.node2)) continue; //사이클이 생기면 패스

			edgeCount++;
			count += edge.weight;
			edgeVisited[edge.node1][edge.node2] = true;
			edgeVisited[edge.node2][edge.node1] = true;

			if(edgeCount == totalNode - 1) return count;
		}

		return -1;
	}

	//bfs를 돌면서 섬에 번호 붙이기 - 지도가 1로 표기 되니, 섬번호는 2부터 표기 하는 것으로,
	private static void bfs(Node startNode, int nodeNum){

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		maps[startNode.x][startNode.y] = nodeNum;

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY) || maps[nextX][nextY] != 1) continue;

				needVisited.add(new Node(nextX, nextY));
				maps[nextX][nextY] = nodeNum;
			}
		}
	}

	//가장자리 노드정보를 찾아서, 간선 정보 구성.
	private static void setEdges(int currentX, int currentY, int currentNum){


		for(int dir = 0; dir < 4; dir++){


			//가장자리라 이동 가능하면, 다른 다리를 만날때까지 이동.
			int bridgeCount = 0;
			int count = 1;

			while(true){
				int nextX = currentX + dx[dir] * count;
				int nextY = currentY + dy[dir] * count;

				//이동 가능한지 확인 - 격자판을 벗어나거나, 탐색을 시작한 섬과 번호가 같다면 종료.
				if(!check(nextX, nextY) || maps[nextX][nextY] == currentNum) break;

				//탐색을 시작한 섬과 번호가 다르다면 간선임.
				if(maps[nextX][nextY] != 0 && maps[nextX][nextY] != currentNum){
					if(bridgeCount >= 2){
						edges.add(new Edge(currentNum, maps[nextX][nextY], bridgeCount));
					}
					break;
				}

				bridgeCount++;
				count++;
			}
		}

	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		maps = new  int[N][M];
		edges = new ArrayList<>();

		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < M; j++){
				maps[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//bfs 구성
		int nodeNum = 2;
		for(int i = 0; i < N; i++){
			for (int j = 0; j < M; j++){

				if(maps[i][j] != 1) continue;

				bfs(new Node(i,j), nodeNum++);
			}
		}

		totalNode = nodeNum - 2;


		//다리 구성.
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){

				if(maps[i][j] == 0) continue;

				setEdges(i, j, maps[i][j]);
			}
		}

		//간선 작은 순으로 정렬.
		edges.sort(Comparator.comparingInt(edge -> edge.weight));

		edgeVisited = new boolean[nodeNum][nodeNum]; // 해당 간선을 연결했는지 확인.

		parents = new int[nodeNum];
		for(int i = 2; i < nodeNum; i++){
			parents[i] = i;
		}



		System.out.println(kruskal());
	}
}
