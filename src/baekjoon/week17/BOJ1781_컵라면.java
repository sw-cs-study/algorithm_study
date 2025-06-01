package baekjoon.week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 우선순위 큐 + 그리디를 이용한 구현
 * 최대로 많은 컵라면을 받을 수 있는 경우를 생각해보면,
 * 데드라인이 짧고, 컵라면을 많이 주는 순서대로 일을 처리하면 된다
 * 데드라인이 짧은 것을 먼저 처리해야 최대한 많은 문제를 처리할 수 있고, 그중에서도 보상이 큰 것을 먼저 처리해야 이득이다.
 * 배열을 이용해서 데드라인이 짧고, 컵라면이 큰 순으로 정렬후에 하나씩 문제를 해결한다.
 * 이때 pq에 선택한 정보를 넣어둬야 pq길이를 통해서 몇개까지의 문제를 해결했는지 파악하여, 데드라인을 계산한다.
 * 추가로 만약 선택한 문제의 데드라인이 이전에 해결한 문제 수(pq길이)보다 더 작다면, 이전에 해결한 문제중에 컵라면을 가장적게 주는 것과 비교하여,
 * 현재 뽑은 것이 더 많은 컵라면을 준다면, 해당 컵라면으로 업데이트 하도록 한다.
 */
public class BOJ1781_컵라면 {

	//문제 노드
	private static class Node{
		int deadline, cupNoodle;
		public Node(int deadline, int cupNoodle) {
			this.deadline = deadline;
			this.cupNoodle = cupNoodle;
		}
	}

	private static int N;//문제 수
	private static Node[] probArray;//문제 배열
	private static PriorityQueue<Node> solvedPQ;//해결한 문제 pg

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		probArray = new Node[N];
		solvedPQ = new PriorityQueue<>((node1, node2) -> {

			if(node1.cupNoodle == node2.cupNoodle) return node1.deadline - node2.deadline;

			return node1.cupNoodle - node2.cupNoodle;
		});

		for (int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			probArray[i] = new Node(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
		}

		Arrays.sort(probArray, (node1, node2) -> {

			if (node1.deadline == node2.deadline) return node2.cupNoodle - node1.cupNoodle;

			return node1.deadline - node2.deadline;
		});

		int result = 0;
		//반복문 돌면서 데드라인이 작은것 부터 큐에 넣기.
		for (Node prob : probArray){

			//현재 뽑은 문제의 데드라인이 pq의 길이보다 크면 해당 문제를 풀 수 있음.(모든 문제를 푸는데 걸리는 단위시간은 1이므로 길이로 보면 됨.)
			if(prob.deadline > solvedPQ.size()){
				solvedPQ.add(prob);
				result += prob.cupNoodle;
			}

			//현재 뽑은 문제의 데드라인이 pq의 길이와 같거나 작다면 해당문제를 풀수 없음.
			//이전에 푼 문제들 중에서 컵라면을 가장 적게 주는 것을 뽑고, 현재 뽑은 것과 비교해서 큰걸로 업데이트함.
			else if(solvedPQ.peek().cupNoodle < prob.cupNoodle){

				Node prevSolve = solvedPQ.poll();

				result -= prevSolve.cupNoodle;
				result += prob.cupNoodle;

				solvedPQ.add(prob);
			}

		}

		System.out.println(result);

	}
}
