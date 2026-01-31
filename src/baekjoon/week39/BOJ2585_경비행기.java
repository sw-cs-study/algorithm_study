package baekjoon.week39;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 이분탐색
 * 최소용량을 이분탐색을 통해서 구하고, bfs를 이용해서 가능한지 탐색하는 방식.
 */

public class BOJ2585_경비행기 {

	private static class Node{
		int x, y, count;
		public Node(int x, int y, int count){
			this.x = x;
			this.y = y;
			this.count = count;
		}
	}

	private static int N;
	private static int K;
	private static Node[] nodes;


	//거리 계산.
	private static int calDis(int currentX, int currentY, int nextX, int nextY){
		double sqrt = Math.sqrt(Math.pow(currentX - nextX, 2) + Math.pow(currentY - nextY, 2));
		return sqrt / 10 == ((int) sqrt) / 10 ? (int) sqrt / 10 : (int) sqrt / 10 + 1;
	}
	//이분탐색.
	private static int binarySearch(int start, int end){

		int mid;
		int result = 0;
		while(start <= end){
			mid = (start + end) / 2;

			//true 이면 주어진 k번안에 목적지 도달이 가능함.
			if(bfs(mid)){
				result = mid;
				end = mid - 1;
			}
			else{
				start = mid + 1;
			}
		}

		return result;
	}

	//bfs
	private static boolean bfs(int fuel){

		boolean[] visited = new boolean[N];

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(0,0, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//현재까지 온 위치에서 목적지까지 날아가 보기.
			if(fuel >= calDis(currentNode.x, currentNode.y, 10000,10000)) return true;

			//착륙횟수가 K이면 더 경유 할 필요 없음.
			if(currentNode.count == K) continue;

			for(int i = 0; i < N; i++){

				//방문했거나, 주어진 연료로 해당 위치까지 못가면 패스.
				if(visited[i] || fuel < calDis(currentNode.x, currentNode.y, nodes[i].x, nodes[i].y)) continue;

				visited[i] = true;
				needVisited.add(new Node(nodes[i].x, nodes[i].y, currentNode.count + 1));
			}
		}

		return false;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		nodes = new Node[N];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			nodes[i] = new Node(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken()),
				0
			);
		}

		System.out.println(binarySearch(0, calDis(0,0, 10000,10000)));

	}
}
