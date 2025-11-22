package baekjoon.week33;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 각 숫자별 인덱스를 저장하고, 정렬후에 앞으로 가장 많이 이동한 칸의 개수가 정답.
 * 뒤로 이동하는 것은 한번의 연산에서 몇번이든 가능하지만, 앞으로 이동하는 것은 한번의 회전에서 한번씩만 가능함.
 * (주의)
 * Map으로 해결하려고 하면 중복값이 있을때 문제가 발생.
 */

public class BOJ1377_버블소트 {

	private static class Node{
		int originIndex, value;

		public Node(int originIndex, int value){
			this.originIndex = originIndex;
			this.value = value;
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());


		Node[] numArrays = new Node[N];
		for(int i = 0; i < N; i++){
			int num = Integer.parseInt(br.readLine());
			numArrays[i] = new Node(i, num);
		}

		Arrays.sort(numArrays, (node1, node2) -> {
			return node1.value - node2.value;
		});

		int maxValue = 0;
		for(int i = 0; i < N; i++){

			int originIndex = numArrays[i].originIndex;

			maxValue = Math.max(maxValue, originIndex - i);
		}

		//정렬완료 후 반복문 인자가 한번더 증가함.
		System.out.println(maxValue + 1);

	}
}
