package baekjoon.week2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 아이디어
 * 이진트리 구현
 * 전위 순회 결과를 가지고 이진트리를 구성 후, 다시 후위 순회 하도록 한다.
 */
public class BOJ5639_이진검색트리 {

	//트리의 노드 구성
	private static class Node {
		int value;
		Node left, right;

		public Node(int value) {
			this.value = value;
		}
	}

	//최종 결과 출력
	private static StringBuilder result;

	//노드를 추가할 트리 위치 탐색
	private static void searchNode(Node currentNode, Node inputNode){

		//현재 값보다 작으면 왼쪽으로 탐색
		if (currentNode.value > inputNode.value) {

			//왼쪽에 값이 없으면 추가.
			if(currentNode.left == null){
				currentNode.left = inputNode;
				return;
			}
			searchNode(currentNode.left, inputNode);
		}
		// 현재값보다 크면 오른쪽으로 탐색.
		else {

			//오른쪽에 값이 없으면 추가.
			if(currentNode.right == null){
				currentNode.right = inputNode;
				return;
			}
			searchNode(currentNode.right, inputNode);
		}
	}

	//후위 순회
	private static void postOrder(Node currentNode){

		if(currentNode == null) return;

		postOrder(currentNode.left);
		postOrder(currentNode.right);
		result.append(currentNode.value).append("\n");
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String input = null;
		Node root = null; //루트 노드.
		while(true){

			input = br.readLine();
			if(input == null || input.isEmpty()) break;
			//새로운 노드 추가.
			Node node = new Node(Integer.parseInt(input));

			//루트가 아직 입력 전이면 루트에 추가.
			if(root == null){
				root = node;
				continue;
			}

			searchNode(root, node);
		}

		result = new StringBuilder();

		postOrder(root);

		System.out.println(result);

	}

}
