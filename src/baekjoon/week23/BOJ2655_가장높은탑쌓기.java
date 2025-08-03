package baekjoon.week23;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 밑면을 기준으로 정렬한 이후, dp로 문제를 풀면된다.
 * 어려운 부분은 위에서부터 벽돌 변호를 출력해야 하는 부분인데, 이부분은 스택을 이용한다.
 * 높이가 같은 벽돌이 없기 때문에, 최대치를 구한 후에, 정렬된 배열을 역순으로 돌면서, 어떤 벽돌을 선택했는지 확인하고,
 * 해당 벽돌의 높이를 최대치에서 뺴주는 것을 반복한다.
 */
public class BOJ2655_가장높은탑쌓기 {

	public static class Node{
		int num, width, height, weight; // 밑면, 높이, 무게

		public Node(int num, int width, int height, int weight){
			this.num = num;
			this.width = width;
			this.height = height;
			this.weight = weight;
		}

	}

	public static void main(String[] args)throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		Node[] blockArray = new Node[N];

		for (int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			blockArray[i] = new Node(
				i + 1,
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
		}

		//밑면 넓이순으로 내림차순 정렬.
		Arrays.sort(blockArray, (o1, o2) -> {
			return o2.width - o1.width;
		});

		int[] dp = new int[N];

		int max = Integer.MIN_VALUE; //역순으로 정렬하기 위해, 최대 높이를 저장 해두기.

		for(int i = 0; i < N; i++){

			dp[i] = blockArray[i].height;
			for(int j = 0; j < i; j++){
				//현재 무게보다 이전무게가 더 가벼우면 패스.
				if(blockArray[i].weight > blockArray[j].weight) continue;

				dp[i] = Math.max(dp[i], dp[j] + blockArray[i].height);
			}

			max = Math.max(max, dp[i]);
		}
		//역순으로 정렬
		StringBuilder result = new StringBuilder();

		int count = 0;
		for(int idx = N - 1; idx >= 0; idx--){

			//최대값이랑 다르면 패스.
			if(dp[idx] != max) continue;

			max -= blockArray[idx].height;
			result.append(blockArray[idx].num).append("\n");
			count++;
		}

		System.out.println(count);
		System.out.println(result);


	}
}
