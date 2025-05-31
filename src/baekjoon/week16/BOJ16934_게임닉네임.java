package baekjoon.week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 트라이
 *
 * 참고 : https://wjdtn7823.tistory.com/55, https://velog.io/@ddongh1122/%EB%B0%B1%EC%A4%80-16934%EB%B2%88-%EA%B2%8C%EC%9E%84-%EB%8B%89%EB%84%A4%EC%9E%84
 */
public class BOJ16934_게임닉네임 {

	private static class Node{
		Map<Character, Node> child  = new HashMap<>();
		int isEnd = 0;
	}

	private static class Trie {
		Node root = new Node();

		public String insert(String in){
			Node node = root;
			StringBuilder result = new StringBuilder();

			boolean endFlag = false;
			for(int i = 0; i < in.length(); i++){

				//자식노드가 존재하면 문자열 빌더에 추가.
				if (node.child.get(in.charAt(i)) != null){
					node = node.child.get(in.charAt(i));
					result.append(in.charAt(i));
				}

				//자식노드가 없으면 그래프 만들고 문자열은 추가 안함.
				else{
					Node nextNode = new Node();
					node.child.put(in.charAt(i), nextNode);
					node = nextNode;


					if(!endFlag){
						result.append(in.charAt(i));
						endFlag = true;
					}
				}
			}

			if(node.isEnd == 0){
				node.isEnd = 1;
				return result.toString();
			}
			else {
				node.isEnd++;
				return result.append(node.isEnd).toString();
			}
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		Trie trie = new Trie();
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < N; i++){
			String str = br.readLine();

			result.append(trie.insert(str));

			if(i == N - 1) continue;
			result.append("\n");
		}

		System.out.println(result.toString());
	}
}
