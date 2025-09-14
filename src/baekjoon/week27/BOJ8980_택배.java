package baekjoon.week27;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어(참고 https://steady-coding.tistory.com/58)
 * 그리디
 * 전달할 마을 번호가 작은 것먼저 배송하는 것이 좋다 -> 그래야 더 많이 배송가능(가까워야 내리고 더 실어서 갈 수 있음.)
 *
 * 각 마을마다 최대로 배송 가능한 값들을 우선 계산한다.
 * 정렬된 값(도착 마을번호 작은순 -> 출발지 작은 순)들을 확인하면서 각 마을별 최대 배송가능 값을 업데이트 함.
 * 만약 1,2,30 이라면 [1,2) 위치의 마을들의 최대 배송 가능한 값들에서 30을 빼줌
 * 뺐을때 음수라면 0미만이 되지 않는 수치까지 빼주고 그 값들을 배송가능한 수치로 누적해줌.
 *
 */

public class BOJ8980_택배 {

	private static class Node{
		int start, end, box;

		public Node(int start, int end, int box){
			this.start = start;
			this.end = end;
			this.box = box;
		}
	}


	private static int N;
	private static int C;

	private static Node[] nodeInfo;//마을 정보 배열

	private static int[] limits;//각 마을별 배송가능한 택배한도 저장.

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		int M = Integer.parseInt(br.readLine());

		nodeInfo = new Node[M];
		limits = new int[N];
		Arrays.fill(limits,C);

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int box = Integer.parseInt(st.nextToken());

			nodeInfo[i] = new Node(start - 1, end - 1, box);
		}

		//종료지점이 빠른것 -> 시작지점이 빠른것.
		Arrays.sort(nodeInfo, (node1, node2) -> {

			if(node1.end == node2.end) return node1.start - node2.start;

			return node1.end - node2.end;
		});


		int result = 0;
		for(int i = 0; i < M; i++){

			Node node = nodeInfo[i];
			//구간에서 가능한 최대(해당 구간의 허용가능한 택배용량중 최소값)치에 맞춰서 빼주기.

			int min = C; //C보다 큰 값은 없기 때문에 C로 초기화.
			for(int s = node.start; s < node.end; s++){
				min = Math.min(limits[s], min);
			}

			//구한 최소값과 현재 선택한 구간에서 전송가능한 박스값을 비교
			min = Math.min(node.box, min);

			result += min;
			for(int s = node.start; s < node.end; s++){
				limits[s] -= min;
			}
		}

		System.out.println(result);

	}
}
