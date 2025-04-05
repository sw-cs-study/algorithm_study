package baekjoon.week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 트리.
 * 주어진 정보로 트리를 구성한다.
 */
public class BOJ14725_개미굴 {

	private static class Node{
		Map<String,Node> child;

		public Node(){
			child = new HashMap<>();
		}
	}

	private static StringBuilder result;

	//dfs 돌면서 stringbuilder에 저장.
	private static void dfs(Node currentNode, String value){

		//현재 노드의 정보를 정렬
		List<String> nodeStrList = new ArrayList<>(currentNode.child.keySet());
		//정렬
		Collections.sort(nodeStrList);

		//반복문 돌면서 재귀 호출.
		for(String nextNodeStr : nodeStrList){

			//누적된 노드 값을 추가 - 앞에 --를 추가.
			result.append(value).append(nextNodeStr).append("\n");
			//다음 노드 탐색 - 탐색할때는 --를 반복해서 붙인 문자를 넘긴다.
			dfs(currentNode.child.get(nextNodeStr),value + "--");
		}
	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int N = Integer.parseInt(br.readLine());

		Node root = new Node();

		result = new StringBuilder();

		Node currentNode = root;
		for (int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			currentNode = root;
			int count = Integer.parseInt(st.nextToken());
			for (int j = 0; j < count; j++){
				String node = st.nextToken();

				//자식 정보에 없으면 추가.
				if(!currentNode.child.containsKey(node)){
					currentNode.child.put(node,new Node());
				}
				//현재노드를 다음 노드로 변경
				currentNode = currentNode.child.get(node);
			}
		}

		dfs(root, "");

		System.out.println(result);
	}
}
