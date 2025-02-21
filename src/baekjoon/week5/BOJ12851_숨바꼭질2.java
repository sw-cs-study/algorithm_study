package baekjoon.week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs
 * 각 위치를 노드로 보고, 다음 노드로 이동가능한 간선 경우를 +-1,*2로 보면, bfs 탐색을 통해 모든 경우를 확인 할 수 있다.
 * 이때 주의할점은, 탐색 과정에서 10만을 넘어가면 해당 노드는 폐기하는것이 좋다.
 * 동생의 위치는 최대 10만인데, 10만이상을 허용하면 의미없는 노드가 큐에 많이 쌓이게 된다.
 * 가령 현재 값이 80,001 인데 여기서 *2를 먼저해서 160,002이 큐에 들어가면 다음 탐색때 매번 불필요하게 160.002에 연산을 하게 된다.
 *
 *
 * (주의)
 * 수를 방문처리할때, 목적지를 방문처리하면, 다른 경로에서 목적지로 올수가 없어 가지수가 1개밖에 나오지 않는다.
 */
public class BOJ12851_숨바꼭질2 {

	private static class Node{
		int value, count;

		public Node(int value, int count){
			this.value = value;
			this.count = count;
		}
	}

	private static int N;//수빈이 위치 - 시작점
	private static int K;//동생위치 - 목적지

	private static int[] visited;//노드 방문처리.
	private static int minTime;//최소 시간 저장.
	private static int caseCount;//가지수 저장.

	//다음 노드 계산.
	private static int getNextNode(int currentNode, int index){

		switch(index){
			case 0:
				return currentNode + 1;
			case 1:
				return currentNode - 1;
			case 2:
				return currentNode * 2;
		}

		return currentNode;
	}

	//다음 탐색이 가능한 노드인지 확인.
	private static boolean check(int nextNode){
		return nextNode <= 100_000 &&
			nextNode >= 0;
	}

	//bfs 탐색.
	private static void bfs(){

		Queue<Node> needVisited = new LinkedList<>();
		needVisited.add(new Node(N,0));

		visited[N] = 0;

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();


			//동생위치에 도달했으면, 이전에 저장된 최소값과 비교.
			if(currentNode.value == K){
				//이전에 저장된 최소값과 같다면 가지수 증가.
				if(minTime == currentNode.count){
					caseCount++;
				}
				//저장된 시간보다 새로구한 시간이 더 작다면 가지수 초기화 하고 업데이트.
				else if(minTime > currentNode.count){
					minTime = currentNode.count;
					caseCount = 1;
				}

				continue;
			}

			//다음 노드 탐색
			for(int i = 0; i < 3; i++){

				int nextValue = getNextNode(currentNode.value, i);

				//조건(10만초과)이 맞지 않거나, 방문했다면 패스.
				if(!check(nextValue) || (visited[nextValue] < currentNode.count + 1 && nextValue != K)) continue;

				visited[nextValue] = currentNode.count + 1;
				needVisited.add(new Node(nextValue, currentNode.count+1));
			}
		}

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		minTime = 100_001; //출발점이 0이고 동생이 10만이고, 1씩 증가하는 최악의 케이스를 가정했을떄도 10만번이면 됨.
		caseCount = 0;

		visited = new int[100_001]; // 방문노드는 10만까지만 보면 됨.
		for(int i = 0; i <= 100_000; i++){
			visited[i] = 100_001;
		}

		bfs();

		System.out.println(minTime);
		System.out.println(caseCount);
	}
}
