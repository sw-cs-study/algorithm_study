package baekjoon.week24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어(참고함)
 * 다익스트라 + 이분탐색.
 *
 * 1부터 N번까지 갈수 있는 간선중에, 가중치가 큰것부터 K개를 제외한 후, 남은 값들 중 최대값이, 최소가 되는 경우를 구하는 것이다.
 * 처음에는 그래프 탐색을 통해서, 모든 경로의 가중치 값을 배열로 만들고, 이를 정렬해서 큰것부터 K개를 제외하려고 했다.
 * bfs를 이용해서 1에서 N에 도달가능한 모든 경로에 대한 가중치 배열을 만들고 이를 정렬하면, 시간초과가 날 것이라고 생각했다.
 *
 * (참고한 아이디어)
 * 참고 아이디어에서는, 구하고자 하는 비용을 이분탐색을 통해서 구하고, 이에 맞춰서 반복적으로 그래프 탐색을 통해, 가능한지 불가능한지 판단 하도록 했다.
 * 결정문제가 되면, 이분탐색을 통해서 지정한 비용보다 크면 가중치 1, 같거나 작으면 가중치를 0으로 두고,
 * 최종적으로 이치의 합이 K이하 이면, 가능하다는 뜻으로, 최소 비용을 구하기 위해, 이분탐색으로 더 줄여서 진행한다.
 */
public class BOJ1800_인터넷설치 {

	private final static int INF = Integer.MAX_VALUE;

	//노드
	private static class Node{
		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	private static int N;//노드수
	private static int P;//케이선의 개수.
	private static int K;//공짜 제공 케이블 수

	private static List<Node>[] graph;//그래프.

	//이분 탐색 - 최종적으로 내야하는 비용 반환, end 값은 입력으로 주어지는 간선가중치 중 최대 값.
	private static int binarySearch(int end){
		int answer = -1;

		int start = 0;

		while(start <= end){
			int mid = (start + end) / 2;

			//K개 이하면 가능하다는 뜻으로, 더 줄여서 최소비용을 구해봐야 함.
			if(bfs(mid)){
				answer = mid;//구한 값이 최소일수도 있기 때문에 저장해둠.
				end = mid - 1;
			}
			else {
				start = mid + 1;
			}
		}

		return answer;
	}

	//다익스트라 방식을 통해서, 주어진 비용보다 큰 비용의 케이블이 K개 이하면 true, K개 이상이면 값을 더 키워야 하므로 false;
	private static boolean bfs(int targetCharge){

		//주어진 타겟 비용보다 더 크면 가중치 1로 해서, 거리구하기.
		//최소 거리만 구하면 됨, 최단 가중치가 K보다 큰지 아닌지 판단하면 됨.
		//최단 가중치로 계산했을때 K를 넘는지 아닌지 판단해야, 해당 비용으로는 아예 불가능하다는 것을 알 수 있음.
		int[] counts = new int[N + 1];

		//최단 가중치를 구하기 위해서 최소값으로 채움.
		Arrays.fill(counts, INF);

		counts[1] = 0;

		//우선순위 큐로 다익스트라 탐색진행.
		PriorityQueue<Node> needVisited = new PriorityQueue<>(
			Comparator.comparingInt(node -> node.weight)
		);
		needVisited.add(new Node(1, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if (counts[currentNode.node] < currentNode.weight) continue;

			for (Node nextNode : graph[currentNode.node]){

				//다음 간선의 가중치가 targetCharge 보다 크면 1, 아니면 0으로 해서 K개를 넘는지 안넘는지 체크.
				int nextCount = counts[currentNode.node] + (nextNode.weight > targetCharge ? 1 : 0);

				if (counts[nextNode.node] <= nextCount) continue;

				counts[nextNode.node] = nextCount;
				needVisited.add(new Node(nextNode.node, nextCount));
			}
		}

		return counts[N] <= K;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		for (int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		int end = 0;

		for (int i = 0; i < P; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			graph[a].add(new Node(b, weight));
			graph[b].add(new Node(a, weight));

			end = Math.max(end, weight);
		}

		System.out.println(binarySearch(end));
	}
}
