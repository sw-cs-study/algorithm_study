package baekjoon.week7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * dfs
 * X가 있는경우와 없는 경우로 나눠서 생각할 수 있다.
 * 먼저 X가 등수 비교 목록에 없으면, X는 어떤 등수든 될수 있다.
 *
 * X가 등수 비교 목록에 있으면, 계산을 해야 한다.
 * 이를 위해서 자신보다 작은 쪽으로 가는 간선만 있는 그래프와, 자신보다 큰 쪽으로 가는 그래프를 구분해야 한다.
 * 우선 X가 최대로 얻을 수 있는 등수를 찾기 위해서, 자신보다 작은 노드 쪽으로 이동하는 그래프를 탐색하면서 자신보다 작은게 몇개인지 확인한다.
 * 다음으로는 X가 얻을 수 있는 제일 낮은 등수를 찾기 위해서, 자신보다 큰 노드 쪽으로 이동하는 그래프를 탐색해본다.
 *
 * 명확하게 자신의 위에 있는 것과, 자신의 아래 있는 것을 구했으면, 두개 모두에 속하지 않는 노드의 개수를 구한다.
 * 두개 모두에 속하지 않는 노드개수만큼 자신의 위에 있는 개수에 더한 등수에 + 1한 것이 X가 얻을 수 있는 제일 높은 등수이다.(제일 낮은 등수는 반대로 생각하면 된다.)
 */
public class BOJ17616_등수찾기 {

	private static int N;//학생수
	private static int M;//비교 등수 수
	private static int X;//목표 학생

	// 0: X보다 확실하게 등수가 높은 수, 1: X보다 확실하게 등수가 작은 수,
	private static int[] countList;

	//0 : 큰쪽으로 가는 그래프, 1: 작은 쪽으로 가는 그래프
	private static List<Integer>[][] graph;

	//방문처리
	private static boolean[] visited;

	//dfs 탐색을 돌면서 개수 찾기 - 단방향이고 순환이 발생할수가 없기 때문에 방문처리는 필요 없음.
	private static void dfs(int currentNode, int type){

		//현재 노드가 X가 아니면 증가
		if (currentNode != X){
			countList[type]++;
		}

		for (int nextNode : graph[type][currentNode]){

			if(visited[nextNode]) continue;

			visited[nextNode] = true;
			dfs(nextNode, type);

		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		countList = new int[2];
		graph = new List[2][N + 1];

		for (int i = 1; i <= N; i++){
			graph[0][i] = new ArrayList<>();
			graph[1][i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int higher = Integer.parseInt(st.nextToken());
			int lower = Integer.parseInt(st.nextToken());

			graph[0][lower].add(higher);
			graph[1][higher].add(lower);
		}

		//높은쪽 낮은쪽 dfs두번 돌려서 구함.
		visited = new boolean[N + 1];
		for(int type = 0; type < 2; type++){
			visited[X] = true;
			dfs(X, type);
		}

		//전체 노드수에서 각각구한 수를 뺀 나머지는 확인이 안되는 수(X제외 해야 함.)
		int unknownRankCount = N - (countList[0] + countList[1]) - 1;

		//출력 : 얻을 수 있는 최대 랭크, 얻을 수 있는 최저 랭크 순으로 출력.
		System.out.println(
			(N - (countList[1] + unknownRankCount))  //얻을 수 있는 최대 랭크
			+ " "
			+ (countList[0] + unknownRankCount + 1) //얻을 수 있는 최저 랭크
		);
	}
}
