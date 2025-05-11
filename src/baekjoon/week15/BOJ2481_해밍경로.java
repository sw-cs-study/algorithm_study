package baekjoon.week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어 - 참고함
 * bfs
 * 주어진 비트열은 10만개라 이를 조합으로 풀면 시간초과가 남
 * 단순 조합도 아니고, 순서에 따라 해밍경로가 될수도 있고 안될수도 있기 때문에 더 많은 경우의 수가 나오게 됨.
 * 그러기 때문에 비트열 길이가 30인것을 활용함.
 * 한비트씩 밀면서 기존의 값과 xor연산을 하면, 1비트만 다른 값을 구할 수 있음.
 * 타겟으로 선택한 값이 value 이면, 연산했을떄 1비트만 다른 값은 value ^ (1<<i) - i는 0부터 K-1이다.
 * value ^ value ^ x = 1비트 차이나는 것 -> value^value = 0
 * 0 ^ x = x => x는 1<<i가 되면 됨.
 *
 * (못 푼 이유)
 * 아이디어를 떠올리지 못함.
 * 주어진 비트열을 이용해서 모든 조합을 만들면 시간 터지는 것은 아는데, 이 외의 방법이 생각 나지 않음.
 */
public class BOJ2481_해밍경로 {

	private static int N;//N - 이진코드 수.
	private static int K;// K - 비트열 수.
	private static int M;//M - 질의 개수

	private static Map<Integer, Integer> mapping;//방문처리 할 맵 -> 값은 같지만 위치가 다른것이 있을 수 있기 때문에 값과 인덱스를 같이 저장해야 함.
	private static int[] visited;//경로 저장할 배열(초기 값 -1)

	//bfs로 해밍거리가 1인 노드 찾기.
	private static void bfs(int startNode){

		visited[1] = 0;

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();


			//다음 노드 구하기
			for(int i = 0; i < K; i++){

				int nextNode = currentNode ^ (1 << i);

				//맵에 없으면 주어진 이진코드가 아님.
				if(!mapping.containsKey(nextNode)) continue;

				//이전에 탐색한 노드인지 확인을 위해, Map에서 인덱스 꺼내서 확인
				int idx = mapping.get(nextNode);

				//방문했던 값이면 패스.
				if(visited[idx] != -1) continue;

				//방문하지 않았으면 - 이전 경로를 저장하고 다음 탐색
				visited[idx] = mapping.get(currentNode);
				needVisited.add(nextNode);
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		mapping = new HashMap<>();
		visited = new int[N + 1];
		Arrays.fill(visited, -1);

		int startNode = 0;
		for (int i = 1; i <= N; i++){
			int binaryInt = Integer.parseInt(br.readLine(), 2);
			mapping.put(binaryInt, i);

			if(i == 1) startNode = binaryInt;
		}

		bfs(startNode);

		M = Integer.parseInt(br.readLine());
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < M; i++){

			StringBuilder subResult = new StringBuilder();
			int targetIndex = Integer.parseInt(br.readLine());

			if(visited[targetIndex] == -1) result.append(-1);

			while(visited[targetIndex] != -1){

				subResult.insert(0,targetIndex + " ");

				targetIndex = visited[targetIndex];

				if(targetIndex == 0) break;

			}

			result.append(subResult).append("\n");
		}

		System.out.println(result);

	}


}
