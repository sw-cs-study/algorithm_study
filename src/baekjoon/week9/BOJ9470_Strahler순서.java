package baekjoon.week9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 위상정렬
 *
 * 특정 노드의 순서를 구할때는
 */
public class BOJ9470_Strahler순서 {

	private static int K;//테스트 케이스 번호.
	private static int M;//노드 수
	private static int P;//간선 수
	private static List<Integer>[] graph;//그래프
	private static int[] inDegree;//진입 차수.
	private static int[][] orderValue;//각 노드별 순서값

	//위상 정렬로 각 노드별 순서값채우기.
	private static void topologySort(){

		//진입 차수가 0인 값들을 큐에 넣음
		Queue<Integer> needVisited = new ArrayDeque<>();
		for(int i = 1; i <= M; i++){
			if(inDegree[i] != 0) continue;

			orderValue[0][i] = 1; //strahler 순서를 1로 만듦.
			orderValue[1][i] = 1;
			needVisited.add(i);
		}

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(int nextNode : graph[currentNode]){

				//orderValue 에 저장된 값이 새로 온 값보다 크면 업데이트ㅡ
				//같으면 + 1
				//크면 패스.
				if(orderValue[0][nextNode] < orderValue[0][currentNode]){
					orderValue[0][nextNode] = orderValue[0][currentNode];
					orderValue[1][nextNode] = 1;
				}
				else if(orderValue[0][nextNode] == orderValue[0][currentNode]){
					orderValue[1][nextNode]++;
				}

				inDegree[nextNode]--;
				if(inDegree[nextNode] != 0) continue;

				orderValue[0][nextNode] += orderValue[1][nextNode] >= 2 ? 1 : 0;
				needVisited.add(nextNode);


			}
		}



	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		StringBuilder result = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for(int testCase  = 1; testCase <= T; testCase++){

			st = new StringTokenizer(br.readLine());

			K = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			P = Integer.parseInt(st.nextToken());

			//그래프 초기화.
			graph = new List[M + 1];
			for(int i = 1; i <= M; i++){
				graph[i] = new ArrayList<>();
			}

			//진입 차수 그래프 초기화
			inDegree = new int[M + 1];
			orderValue = new int[2][M + 1];

			//간선
			for(int i = 0; i < P; i++){

				st = new StringTokenizer(br.readLine());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				graph[a].add(b);
				inDegree[b]++;
			}

			topologySort();

			int resultOrder = 0;
			for(int i = 1; i <= M; i++){

				resultOrder = Math.max(
					resultOrder,
					orderValue[0][i]
				);
			}

			result.append(K).append(" ").append(resultOrder).append("\n");
		}

		System.out.println(result);


	}
}

