package programmers.week51;

import java.util.Arrays;

/**
 * 아이디어
 * 그래프 구현
 *
 * 각 모양은 진입, 진출차수 수로 판단이 가능한다.
 * 생성점 : 진입차수0, 진출차수2 이상 -> 각 그래프 모형에 연결
 * 도넛 : 진입차수1, 진출차수1
 * 막대 : 진입차수1, 진출차수0
 * 8자 : 진입차수2, 진출차수2
 */

public class Prog_도넛과막대그래프 {

	//각 노드별 진입, 진출 차수 저장.
	private static class Node{
		int indegree, outdegree;
		boolean isVisited; //100만개중에 쓰는 노드만 체크.

		public Node(int indegree, int outdegree){
			this.indegree = indegree;
			this.outdegree = outdegree;
			this.isVisited = false;
		}
	}

	public int[] solution(int[][] edges) {
		int[] answer = new int[4];

		//노드 수는 최대 100만
		Node[] degreeArray = new  Node[1_000_001];
		for(int i = 1; i < degreeArray.length; i++){
			degreeArray[i] = new Node(0,0);
		}

		for(int[] edge : edges){
			degreeArray[edge[0]].outdegree++;
			degreeArray[edge[1]].indegree++;

			degreeArray[edge[0]].isVisited = true;
			degreeArray[edge[1]].isVisited = true;
		}

		int totalGraph = 0;
		int stickGraph = 0;
		int eightGraph = 0;

		for(int i = 1; i < degreeArray.length; i++){

			Node currentNode = degreeArray[i];

			//실제 사용하는 노드가 아니면 패스.
			if(!currentNode.isVisited) continue;

			//생성점 - 진입은 0, 진출은 최소 2
			if(currentNode.indegree == 0 && currentNode.outdegree >= 2){
				totalGraph = currentNode.outdegree;
				answer[0] = i;
			}

			//막대 - 진출차수가 없음.
			if(currentNode.outdegree == 0){
				stickGraph++;
			}

			//8자 - 진입2이상, 진출2(진입이 기본이 2인데, 생성점에서 연결한 점이 붙어있을 수 있음.)
			if(currentNode.indegree >=2 && currentNode.outdegree == 2){
				eightGraph++;
			}
		}

		answer[1] = totalGraph - (stickGraph + eightGraph);
		answer[2] = stickGraph;
		answer[3] = eightGraph;


		return answer;
	}

	public static void main(String[] args){

		Prog_도넛과막대그래프 p = new Prog_도넛과막대그래프();

		int[][] edges1 = {{2, 3}, {4, 3}, {1, 1}, {2, 1}};
		System.out.println(Arrays.toString(p.solution(edges1)));

		int[][] edges2 = {{4, 11}, {1, 12}, {8, 3}, {12, 7}, {4, 2}, {7, 11}, {4, 8}, {9, 6}, {10, 11}, {6, 10}, {3, 5}, {11, 1}, {5, 3}, {11, 9}, {3, 8}};
		System.out.println(Arrays.toString(p.solution(edges2)));


	}
}
