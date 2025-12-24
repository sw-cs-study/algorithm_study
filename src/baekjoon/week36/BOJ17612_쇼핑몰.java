package baekjoon.week36;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 우선순위 큐
 *
 * 우선순위 큐를 두개 사용하여 처리.
 * 계산대에 있는 고객, 나가는 고객을 우선순위 큐로 구현.
 * 나갈때는 출구에서 가까운, 즉 계산대번호가 큰게 시간이 같을 경우에 같기 때문에 두개의 우선순위 큐가 필요함.
 */
public class BOJ17612_쇼핑몰 {

	private static class Node{
		int idx, id, weight;

		public Node(int idx, int id, int weight){
			this.idx = idx;
			this.id = id;
			this.weight = weight;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		long result = 0;

		PriorityQueue<Node> calPQ = new PriorityQueue<>((node1, node2) -> {

			if(node1.weight == node2.weight) return node1.idx - node2.idx;

			return node1.weight - node2.weight;
		});
		PriorityQueue<Node> outPQ = new PriorityQueue<>((node1, node2) -> {
			if(node1.weight == node2.weight) return node2.idx - node1.idx;
			return node1.weight - node2.weight;
		});

		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			int id = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			//i가 K보다 작으면 계산대가 남았다는 뜻.
			if(i <= K){
				calPQ.add(new Node(i, id, weight));
			}
			//K보다 크면 이미 계산대가 꽉참 - 시간상 가장 빠른거 꺼내서, 출력 큐에 넣어야 함.
			else{
				Node poll = calPQ.poll();
				calPQ.add(new Node(poll.idx, id, weight + poll.weight)); //빼고 넣은거라, 뺀 사람의 계산시간을 더해줌.
				outPQ.add(new Node(poll.idx, poll.id, poll.weight));
			}
		}

		//calPQ에서 남은거 전부 꺼내기
		while(!calPQ.isEmpty()){

			Node poll = calPQ.poll();
			outPQ.add(new Node(poll.idx, poll.id, poll.weight));
		}

		//큐 돌면서 계산
		long count = 1;
		while(!outPQ.isEmpty()){
			Node poll = outPQ.poll();

			result += count * (long) poll.id;
			count++;
		}

		System.out.println(result);


	}
}
