package baekjoon.week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * lca
 * 최소 공통 부모를 찾아서 해당 부모를 제거하면 된다.
 * 단순하게 트리로 주면 쉬운 문제지만, 2진수형태로 주어서, 해당 값을 보고 트리를 구성해야 한다.
 * 또한 각 노드별로 2진수 형태에서 몇번째값을 가지는지 저장해야 한다.
 * 각 노드는 두개의 위치를 가진다.(0과 1)
 * lca로 공통 부모를 찾을때는, 트리를 탐색하면서 노드 별 높이를 저장하고, 두 노드간 높이를 동일하게 맞췄을때, 부모가 같다면 해당 부모를 선택,
 * 높이를 동일하게 맞췄지만 부모가 다르다면, 두 노드의 높이를 동일하게 한레벨씩 올리면서 같은 부모를 찾아야 한다.
 *
 * (추가)
 * 노드 수는 0의 개수로 보면 된다.
 *
 * (추가2)
 * 로직에서 부모의 값을 반환하게 했는데,
 * 두 노드 중, 한 노드가 나머지 한 노드의 자식인 경우를 조심해야 한다.
 * 루트노드의 부모를 구분하기 위해 -1로 설정하면서 에러가 발생했음.
 */

public class BOJ2233_사과나무 {

	//각 노드 정보 저장
	private static class Node{
		int i, j;
		int level; //트리의 레벨 정보.
		int parent; //트리의 부모 정보.

		public Node(int i){
			this.i = i;
		}

	}

	private static int N;//정점의 개수
	private static String binaryStr;//이진수 문자열
	private static Node[] nodeinfo;//노드 정보(2진수에서의 위치정보)
	private static int deleteApple1;
	private static int deleteApple2;

	//트리 구성을 위한 메서드 - 트리를 다 구현할 필요 없이, 부모의 정보만 가지고 있으면 됨.
	private static void initTree(int idx, int level, int parent, int currentNode){

		//이진수 길이를 넘어가면 종료
		if(idx == binaryStr.length()) return;

		char chr = binaryStr.charAt(idx);


		//이진수의 값이 0이면 하위 노드로 이동.
		if(chr == '0'){
			nodeinfo[currentNode] = new Node(idx);
			nodeinfo[currentNode].level = level;
			nodeinfo[currentNode].parent = parent;

			initTree(idx + 1, level + 1, currentNode, currentNode + 1);
		}


		//1이면 현재 노드 인덱스를 저장하고 마무리.
		else{
			nodeinfo[parent].j = idx;

			initTree(idx + 1, level - 1, nodeinfo[parent].parent, currentNode);
		}
	}

	//LCA를 구현
	private static int lca(){

		int nodeA = deleteApple1;
		int nodeB = deleteApple2;



		//둘중에 레벨이 큰 노드를 nodeA로 둠.
		if(nodeinfo[nodeA].level < nodeinfo[nodeB].level) {
			int temp = nodeA;
			nodeA = nodeB;
			nodeB = temp;
		}

		//nodeA를 nodeB의 높이와 맞추기 위해서 레벨을 올림.
		while(nodeinfo[nodeA].level > nodeinfo[nodeB].level){

			nodeA = nodeinfo[nodeA].parent;
		}

		//두 노드가 같은 노드라면 볼 필요 없음.
		if (nodeA == nodeB) return nodeA;

		//두 노드의 부모가 같으면 부모를 반환
		if(nodeinfo[nodeA].parent == nodeinfo[nodeB].parent) return nodeinfo[nodeA].parent;

		//두 노드의 부모가 다르면, 같을때까지 레벨을 올림.
		while(nodeinfo[nodeA].parent != nodeinfo[nodeB].parent){
			nodeA = nodeinfo[nodeA].parent;
			nodeB = nodeinfo[nodeB].parent;
		}

		return nodeinfo[nodeA].parent;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		binaryStr = br.readLine();

		nodeinfo = new Node[N + 1];

		st = new StringTokenizer(br.readLine());

		deleteApple1 = Integer.parseInt(st.nextToken()) - 1;
		deleteApple2 = Integer.parseInt(st.nextToken()) - 1;


		initTree(0, 1, -1, 1);

		//삭제할 사과를 찾아서 저장.
		boolean flag1 = false;
		boolean flag2 = false;

		for(int i = 1; i <= N; i++){

			Node node = nodeinfo[i];
			// System.out.println("node => " + i + ", i => " + node.i + ", j => " + node.j);

			if (!flag1 && (deleteApple1 == node.i || deleteApple1 == node.j)){
				deleteApple1 = i;
				flag1 = true;
			}
			if (!flag2 && (deleteApple2 == node.i || deleteApple2 == node.j)){
				deleteApple2 = i;
				flag2 = true;
			}

		}

		// System.out.println("delete1 => " + deleteApple1 + ", delete2 => " + deleteApple2);

		int targetNode = lca();

		System.out.println((nodeinfo[targetNode].i + 1) + " " + (nodeinfo[targetNode].j + 1));
	}
}
