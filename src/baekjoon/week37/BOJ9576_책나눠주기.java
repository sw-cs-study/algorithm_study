package baekjoon.week37;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디, 정렬
 *
 * [a,b] 구간이 주어졌을때, 구간의 끝값인 b로 정렬한 후에,
 * 해당하는 구간에서 가장 작은 값부터 나눠주는 방식으로 해결한다.
 * 이렇게 되면 자연스럽게 겹치는 영역이 가장 작은 값을 먼저 주는 식으로 처리된다.
 *
 * 이미 준 값이라면 그 다음 작은 값을 선택해서 주는 방식으로 해결한다.
 *
 */

public class BOJ9576_책나눠주기 {

	private static class Node{
		int a,b;

		public Node(int a, int b){
			this.a = a;
			this.b = b;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		StringBuilder result = new StringBuilder();
		for(int testCase = 1; testCase <= T; testCase++){

			st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			boolean[] books = new boolean[N + 1];
			List<Node> ranges = new ArrayList<>();

			int count = 0;

			for(int i = 0; i < M; i++){
				st = new StringTokenizer(br.readLine());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				ranges.add(new Node(a, b));
			}

			//b를 기준으로 오름차순 정렬
			ranges.sort((node1, node2) -> {
				return node1.b - node2.b;
			});


			//하나씩 돌면서 해당 범위에서 가장 작은 값 주기.
			for(Node node : ranges){

				int start = node.a;
				int end = node.b;

				while(start <= end){

					if(books[start]){
						start++;
						continue;
					}

					books[start] = true;
					count++;
					break;
				}
			}

			result.append(count).append("\n");
		}

		System.out.println(result.toString());

	}
}
