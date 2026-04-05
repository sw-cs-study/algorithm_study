package baekjoon.week42;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 벨만포드 응용.
 * 양의 사이클을 찾는 문제
 * 참고함.
 */
public class BOJ1219_오민식의고민 {

	private static class Edge{
		int a, b, weight;
		public Edge(int a, int b, int weight){
			this.a = a;
			this.b = b;
			this.weight = weight;
		}
	}

	private static int N;
	private static int start;
	private static int finish;
	private static int M;

	private static Edge[] graph;

	private static int[] money;


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		start = Integer.parseInt(st.nextToken());
		finish = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		money = new int[N];
		graph = new Edge[M];


		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			Edge edge = new Edge(a, b, weight);
			graph[i] = edge;
		}



		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			money[i] = Integer.parseInt(st.nextToken());
		}

		long[] dis = new long[N];
		Arrays.fill(dis, Long.MIN_VALUE);

		//시작도시의 수익 초기값 넣기
		dis[start] = money[start];


		//양의 사이클을 검출함.
		//출발도시가 양의 사이클에 들어가면 무한하게 돌아서 이익이 무한대로 발산함 -> 도착지를 MAX로 표기해서 최종 결과를 GEE로 표기.
		for(int i = 0; i < 2*N; i++){

			for(int j = 0; j < M; j++){

				Edge edge = graph[j];

				//해당 출발값이 초기값(MIN)이라면 이동 불가.
				if(dis[edge.a] == Long.MIN_VALUE) continue;

				//해당 출발값이 최대값(MAX)라면, 도착노드도 최대값 - 출발이 최대라는건 이미 양의 사이클에서 돈을 무한대로 벌수 있음, 즉 다음으로 이동할 노드도 무한대임
				else if(dis[edge.a] == Long.MAX_VALUE){
					dis[edge.b] = Long.MAX_VALUE;
				}
				//최대 수익을 구하는 것이므로, 이전 값보다 새로 업데이트 할 값이 수익을 더 크게 만들면 업데이트
				else if(dis[edge.b] < dis[edge.a] - edge.weight + money[edge.b]){
					dis[edge.b] = dis[edge.a] - edge.weight + money[edge.b];

					//업데이트가 총 N번반복되었다면, 양의 사이클이 발생했다는 것,
					//노드 수 -1 만큼 돌린 후 1번 더 돌렸는데 변경이 있었다면 사이클이 생겨서 계속 커진다는 뜻.(시작노드는 가중치를 0으로 두고 시작하기 때메 제외)
					if(i >= N - 1){
						dis[edge.b] = Long.MAX_VALUE;
					}
				}
			}
		}

		//도착지가 MAX면 무한대라는 뜻,
		if(dis[finish] == Long.MAX_VALUE){
			System.out.println("Gee");

		}
		//도착지가 초기 설정인 음의 무한대이면, 도착이 불가능하다는 뜻.
		else if(dis[finish] == Long.MIN_VALUE){
			System.out.println("gg");
		}
		//나머지 경우는 그냥 저장된 값을 출력하면 됨.
		else{
			System.out.println(dis[finish]);
		}




	}
}
