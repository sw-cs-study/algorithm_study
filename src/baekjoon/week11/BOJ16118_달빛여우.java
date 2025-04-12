package baekjoon.week11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/**
 * 아이디어
 * 다익스트라
 * 달빛 여우와 달빛 늑대는 1번에서 출발한다.
 * 최단거리 값 기준으로 달빛 여우 < 달빛 늑대인 노드를 찾아야 하고
 * 둘은 이동가능한 최단거리로 이동한다, 즉 다익스트라를 이용해서 각 노드별 값을 저장해주면 된다.
 * 각 노드별 2차원 배열을 이용해서 한번은 달빛 여우의 최단거리를 업데이트, 한번은 달빛 늑대로 업데이트 해주면 된다.
 * 최종적으로 모든 노드를 돌면서 저장된 최단거리 값이 달빛 여우 < 달빛 늑대인 노드 수를 세어주면 된다.
 *
 * 이때 중요한 것은 절반으로 나누는 것이 아닌 역으로 곱하는 것이다.
 * 주어지는 간선의 가중치가 달빛 늑대가 달빛 여우의 두배로 달렸을때의 가중치라고 생각하면, 달빛 여우는 주어진 가중치의 2배로 연산해주고,
 * 달빛 늑대가 달빛 여우의 절반의 속도로 걸어가는 것은 4배로 연산해주면 된다.
 *
 * 역으로 곱하는 이유는 나누게 되면 소수점이 나오기 때문에 계산이 불가능해진다.
 *
 * (추가)
 * 단순하게 가중치에 수를 곱해서 처리하는 방식은 모든 경우를 파악 불가.
 * 달빛 늑대가 1번 예제를 기준으로 했을때 단순 다익스트라로는 1 -> 4로 가는 경우 최단 거리는 1-> 2->4이다.
 * 하지만 실제로 최단거리는 1 -> 3 -> 2 -> 4이다.
 * 즉 중간거리까지는 최단거리가 아닐 수 있지만, 해당 경로로 가는게 최종적으로는 최단 거리일 수 있다(가중치가 큰 구간을 달려나가면 훨씬 이득이기 때문)
 * 따라서 여우를 계산하는 노드와 늑대를 계산하는 노드는 별도로 분리해야 하며,
 * 늑대는 각 지점별로, 달려서 온 경우와 걸어서 온 경우 두가지로 나눠서 처리해야 한다.
 */
public class BOJ16118_달빛여우 {

	private final static int INF = 2_000_000_000;

	//탐색을 위한 노드 - 달빛 여우 노드와 달빛 늑대 노드를 같이 사용
	//달빛 늑대는 스피드가 1, 달빛 여우는 스피드가 2, 달빛늑대가 걸을때는 스피드가 4
	//이동한 위치의 가중치를 구하기 위해서는 스피드만큼 곱해준다.
	private static class Node{
		int node, weight, speedType;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
			this.speedType = -1;
		}

		public Node(int node, int weight, int speedType){
			this.node = node;
			this.weight = weight;
			this.speedType = speedType;
		}
	}

	private static int N;//노드수
	private static int M;//간선 수
	private static List<Node>[] graph;//그래프
	private static int[][] wolfDistances;//최단거리 저장 배열 - 거리 값은 최대 21억미만이므로 int면 충분함.
	private static int[] foxDistances;

	//스피드(실제 가중치에 곱해져야 하는 값.) 반환 - 뛰어온 경우(0)와 걸어온 경우(1) 타입값을 받아서 처리,
	private static int getSpeed(int speedType){
		return speedType == 0 ? 1 : 4;
	}

	//늑대 속도 변경 : 달림 -> 걷기 반복
	private static int changeSpeed(int speedType){
		return speedType == 1 ? 0 : 1;
	}

	private static void wolfDijkstra(){


		PriorityQueue<Node> pq = new PriorityQueue<>(
			(o1, o2) -> {
				return o1.weight - o2.weight;
			}
		);
		pq.add(new Node(1,0,1));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > wolfDistances[currentNode.speedType][currentNode.node]) continue;


			for(Node nextNode : graph[currentNode.node]){

				int nextSpeedType = changeSpeed(currentNode.speedType);
				int nextDistance = 0;

				if(wolfDistances[currentNode.speedType][currentNode.node] == INF){
					nextDistance = nextNode.weight * getSpeed(nextSpeedType);
				}
				else{
					nextDistance = wolfDistances[currentNode.speedType][currentNode.node]
						+ nextNode.weight * getSpeed(nextSpeedType);
				}


				if(wolfDistances[nextSpeedType][nextNode.node] <= nextDistance) continue;

				pq.add(new Node(nextNode.node, nextDistance, nextSpeedType));
				wolfDistances[nextSpeedType][nextNode.node] = nextDistance;
			}
		}

	}

	//다익스트라, type : 0이면 늑대, 1면 여우
	private static void foxDijkstra(){

		foxDistances[1] = 0;

		PriorityQueue<Node> pq = new PriorityQueue<>(
			(o1, o2) -> {
				return o1.weight - o2.weight;
			}
		);

		//여우는 속도가 고정이라 스피드 타입 값 사용필요 없음.
		pq.add(new Node(1,0,-1));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > foxDistances[currentNode.node]) continue;

			for(Node nextNode : graph[currentNode.node]){
				int nextDistance = foxDistances[currentNode.node] + nextNode.weight * 2;

				if(nextDistance >= foxDistances[nextNode.node]) continue;

				pq.add(new Node(nextNode.node,nextDistance, -1));
				foxDistances[nextNode.node] = nextDistance;
			}
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];

		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			graph[a].add(new Node(b,w));
			graph[b].add(new Node(a,w));
		}

		foxDistances = new int[N + 1];
		wolfDistances = new int[2][N + 1]; // 0 : 뛰어옴, 1: 걸어옴.
		//최단거리 측정을 위해 최대값으로 초기화.
		for(int i = 1; i <= N; i++){
			foxDistances[i] = INF;
			wolfDistances[0][i] = INF;
			wolfDistances[1][i] = INF;
		}

		//여우와 늑대에 대해서 각각 다익스트라 수행.
		foxDijkstra();
		wolfDijkstra();

		int result = 0;
		//1은 시작점이므로 패스.
		for(int i = 2; i <= N; i++){

			if(foxDistances[i] >= Math.min(wolfDistances[0][i], wolfDistances[1][i])) continue;

			result++;
		}

		System.out.println(result);
	}
}


