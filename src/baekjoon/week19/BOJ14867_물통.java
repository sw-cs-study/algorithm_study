package baekjoon.week19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs
 *
 * 각 물통 상황에서 할수 있는 방법은 총 4가지이다.
 * 물통 두개의 상황을 하나의 노드로 생각하면 인접노드는 각 경우마다 4가지가 나올 수 있다.
 * 모든 경우를 탐색하고, bfs로 탐색을 했을때, 특정 상태에 가장 먼저 도달하면 해당 값이 최소가 된다.
 *
 * (추가)
 * 데이터가 10^5 * 10^5라며 배열로 하면 터짐.
 * 해시맵을 이용(해시셋보다 빠름.)
 */
public class BOJ14867_물통 {

	//물통의 상태을 나타낼 노드
	private static class Node{
		int stateA, stateB, count;

		public Node(int stateA, int stateB){
			this.stateA = stateA;
			this.stateB = stateB;
		}

		public Node(int stateA, int stateB, int count){
			this.stateA = stateA;
			this.stateB = stateB;
			this.count = count;
		}
	}

	private static int sizeA;//a물통크기
	private static int sizeB;//b물통크기

	private static int targetA;//a물통 목표 상태
	private static int targetB;//b물통 목표 상태.

	private static String visitedStr(Node node){
		return node.stateA + "_" + node.stateB;
	}


	//다음 노드 상태 구하기.
	// type,a,b => 타입이 1이고, bottle이 0이면 a물통에 채우는 것,
	// 타입이 2이고 bottle이 1이면 b의 물을 버림.
	// 타입이 3이고 bottle이 0이면 a -> b, 1이면 b -> a
	private static Node getNextNode(Node currentNode, int type, int bottle){

		int tempStatA = currentNode.stateA;
		int tempStatB = currentNode.stateB;

		//물통에 물채우기
		if (type == 1){

			//a물통
			if(bottle == 0){

				//가득차있으면 패스.
				if(tempStatA == sizeA) return null;

				tempStatA = sizeA;
			}
			else{

				if(tempStatB == sizeB) return null;

				tempStatB = sizeB;

			}

		}
		//물통에 물 버리기
		else if (type == 2){

			if(bottle == 0){

				//물이 없으면 패스.
				if(tempStatA == 0) return null;

				tempStatA = 0;
			}
			else{

				if(tempStatB == 0) return null;

				tempStatB = 0;

			}
		}
		//다른 물통으로 물 옮기기.
		else if (type == 3){

			//a -> b
			if(bottle == 0){

				//b가 가득차있거나, a가 비어있으면 패스.
				if(tempStatB == sizeB || tempStatA == 0) return null;

				//b에 남은량
				int remainB = sizeB - tempStatB;

				//b에 채울수 있는 만큼 채우기
				tempStatB += Math.min(remainB, tempStatA);

				//A빼기
				tempStatA -= Math.min(remainB, tempStatA);
			}

			//b -> a
			else{

				//a가 가득차있거나 b가 비어있으면 패스.
				if (tempStatA == sizeA || tempStatB == 0) return null;

				int remainA = sizeA - tempStatA;

				tempStatA += Math.min(remainA, tempStatB);

				tempStatB -= Math.min(remainA, tempStatB);

			}

		}

		return new Node(tempStatA, tempStatB);
	}


	//bfs
	private static int bfs(){

		HashMap<String, Integer> visited = new HashMap<>();
		visited.put("0_0", 1);


		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(0, 0, 1));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//목표수치면 반환
			if(currentNode.stateA == targetA && currentNode.stateB == targetB){
				return currentNode.count - 1;
			}

			//총 6가지가 나올 수 있음.
			for(int type = 1; type <= 3; type++){
				//각 명령어중에서 무엇을 실행할지(ex. F이면 a를 채울지, b를 채울지)
				for(int i = 0; i < 2; i++){
					Node nextNode = getNextNode(currentNode, type, i);

					//null이면 수행 불가 - 이미 가득 차있는데, 채우는 경우나, 없는데 버리는 경우.
					if(nextNode == null) continue;

					//방문했었다면 패스
					String stateStr = visitedStr(nextNode);
					if (visited.containsKey(stateStr)) continue;

					visited.put(stateStr, 1);
					needVisited.add(new Node(nextNode.stateA, nextNode.stateB, currentNode.count + 1));
				}
			}
		}


		return -1;
	}

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		sizeA = Integer.parseInt(st.nextToken());
		sizeB = Integer.parseInt(st.nextToken());
		targetA = Integer.parseInt(st.nextToken());
		targetB = Integer.parseInt(st.nextToken());

		System.out.println(bfs());

	}
}
