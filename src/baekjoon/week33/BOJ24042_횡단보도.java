package baekjoon.week33;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 다익스트라 응용
 */

public class BOJ24042_횡단보도 {

	private final static long INF = 210_000_000_000L;

	//노드
	private static class Node{
		int node;
		long weight;

		public Node(int node, long weight){
			this.node = node;
			this.weight = weight;
		}
	}

	private static int N;//노드수
	private static int M;//횡단보도 주기.
	private static List<Node>[] graph;//그래프

	//다익스트라.
	private static long dijkstra(){

		PriorityQueue<Node> needVisited = new PriorityQueue<>((node1, node2) -> {
			return Long.compare(node1.weight, node2.weight);
		});

		long[] distances = new long[N + 1];
		Arrays.fill(distances, INF);


		needVisited.add(new Node(1, 0));
		distances[1] = 0;

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(currentNode.weight > distances[currentNode.node]) continue;

			for(Node nextNode : graph[currentNode.node]){

				long nextDistance;

				//현재시간으로 위치한 주기를 구하고, 해당 주기를 이용해서 다음 위치의 가중치를 구함.
				//현재 가중치가 다음 가중치보다 크다면, 다음 노드의 가중치는 다음 노드로 넘어가는 횡단보도가 켜지는 시간 + 1임(건너가는 1초의 시간.)
				if(nextNode.weight >= currentNode.weight){
					nextDistance = nextNode.weight + 1;
				}
				//현재 가중치가 다음가중치보다 크면, 한 주기를 돌아서 와야 함.
				else{
					//모듈러 연산을 이용해서 다음 가중치 구해주기.
					long temp = ((long)Math.ceil(((double)currentNode.weight - nextNode.weight) / M)) * M;
					nextDistance = temp + nextNode.weight + 1;
				}

				//새로 구한 다음 노드의 가중치가, 이전에 저장한 값보다 작으면 업데이트 및 노드 추가.
				if(nextDistance < distances[nextNode.node]){
					distances[nextNode.node] = nextDistance;
					needVisited.add(new Node(nextNode.node, nextDistance));
				}
			}

		}

		return distances[N];
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

			graph[a].add(new Node(b, i));
			graph[b].add(new Node(a, i));
		}

		System.out.println(dijkstra());
	}
}
