package baekjoon.week42;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디.
 */
public class BOJ16238_독수리 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>((node1, node2) -> node2 - node1);

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			pq.add(Integer.parseInt(st.nextToken()));
		}

		int time = 0;
		int result = 0;
		while(!pq.isEmpty()){

			int currentNode = pq.poll() - time;

			if(currentNode <= 0) continue;

			result += currentNode;
			time++;
		}

		System.out.println(result);
	}
}
