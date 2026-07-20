package programmers.week48;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 아이디어
 * 우선순위 큐 2개와 map으로 해결한다.
 *
 *
 * I 숫자	큐에 주어진 숫자를 삽입합니다.
 * D 1	큐에서 최댓값을 삭제합니다.
 * D -1	큐에서 최솟값을 삭제합니다.
 *
 *
 *
 */

public class Prog_이중우선순위큐 {

	public static class Node{
		int idx, node;

		public Node(int idx, int node){
			this.idx = idx;
			this.node = node;
		}
	}

	public int[] solution(String[] operations) {
		int[] answer = {0,0};

		//삭제처리를 체크할 map
		Map<Integer, Node> delMap = new HashMap<>();

		//최대 힙
		PriorityQueue<Node> maxPq = new PriorityQueue<>((node1, node2) -> {

			if(node1.node == node2.node) return node1.idx - node2.idx;

			return node2.node - node1.node;
		});

		//최소 힙
		PriorityQueue<Node> minPq = new PriorityQueue<>((node1, node2) -> {

			if(node1.node == node2.node) return node1.idx - node2.idx;

			return node1.node - node2.node;
		});

		for(int i = 0; i < operations.length; i++){

			String[] operation = operations[i].split(" ");

			String command = operation[0];
			int num = Integer.parseInt(operation[1]);

			//큐에 숫자 삽입
			if(command.equals("I")){
				Node temp = new Node(i,num);
				delMap.put(i, temp);

				maxPq.add(temp);
				minPq.add(temp);
			}
			else {
				//최댓값 삭제.
				if(num == 1){

					while(!maxPq.isEmpty()){

						Node currentNode = maxPq.poll();

						//map에 있으면 삭제
						if(delMap.containsKey(currentNode.idx)){
							delMap.remove(currentNode.idx);
							break;
						}
					}

				}
				//최소값 삭제.
				else{

					while(!minPq.isEmpty()){

						Node currentNode = minPq.poll();

						//map에 있으면 삭제
						if(delMap.containsKey(currentNode.idx)){
							delMap.remove(currentNode.idx);
							break;
						}
					}
				}
			}
		}
		// 유효하지 않은(삭제된) 노드 제거
		while(!maxPq.isEmpty() && !delMap.containsKey(maxPq.peek().idx)) maxPq.poll();
		while(!minPq.isEmpty() && !delMap.containsKey(minPq.peek().idx)) minPq.poll();

		if(!maxPq.isEmpty() && !minPq.isEmpty()){
			answer[0] = maxPq.poll().node;
			answer[1] = minPq.poll().node;
		}


		return answer;
	}

	public static void main(String[] args){
		Prog_이중우선순위큐 p = new Prog_이중우선순위큐();


		String[] operations1 = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};
		System.out.println(Arrays.toString(p.solution(operations1)));


		String[] operations2 = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
		System.out.println(Arrays.toString(p.solution(operations2)));



	}
}
