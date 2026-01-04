package baekjoon.week37;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그래프 탐색.
 *
 * 각 원의 관계를 구해서 원을 노드로 생각하고 연결관계를 만들어준다.
 * 가령 A가 B의 원 내부에 있다면, A <-> B 관계가 성립된다.
 * A와 B가 서로 외부에서 만나지 않는다면, A <-> C(좌표평면), C <-> B 관계가 성립되는 것이다.
 * 주의할 점은 두원 A,B가 임의의 원 D안에 존재한다면 좌표 평면이 아닌 D가 되는것이다.
 * 이렇게 하게 되면, 원안에 한번만 들어갈 수 있다는 제약은, 노드간 방문처리로 해결할 수 있고,
 * 목적지 까지 dfs 탐색을 통해서 해결할 수 있게 된다.
 *
 * (수정)
 * 그래프를 구성하려고 하면 모든 원간의 관계를 구성해야 하는데,
 * N이 20만이라 불가능하다.
 * 그래프 구축에 다른 방법을 사용해야 해서 아래 블로그를 참고함.
 * https://gyummodiary.tistory.com/293
 *
 * 원의 중심의 경우에는 x축에만 있는 것을 이용해서, 원점기준으로 양 끝점을 pq에 넣고,
 * 하나씩 꺼내면서, 부모 관계를 연결해주는 식으로 처리함.
 *
 * (주의)
 * 문제에서 임의의 두점을 선택했을때,내접 외접등 교점이 발생하지 않는다고 줬기 때문에,
 * 겹치는 경우는 고려하지 않아도 됨(pq, 스택 풀이 모두 교점이 생기는 경우가 있으면 계산이 불가능해짐.)
 */

public class BOJ22948_원이동하기2 {

	//노드 객체 - bfs 탐색시 사용.
	private static class Node{
		int node;
		int count; // 몇개의 원을 방문했는지,
		String history;

		public Node(int node, int count, String history){
			this.node = node;
			this.count = count;
			this.history = history;
		}

	}

	//그래프 구성을 위한 객체,
	private static class Point{
		int num, x; //원의 번호와 원의 양끝 x 좌표.

		public Point(int num, int x){
			this.num = num;
			this.x = x;
		}
	}

	private static int N;//원의 개수.
	private static Node result; // 결과 정보.
	private static List<Integer>[] graph;//그래프.

	private static PriorityQueue<Point> pq;//그래프 구성을 위한 pq

	//그래프 구성.
	private static void initGraph(int currentParent){
		//pq에서 값 하나 꺼냄.
		Point currentPoint = pq.poll();

		//이전 부모가 있다면, 꺼낸 값과 부모를 연결함.
		if(currentParent != -1) {
			graph[currentParent].add(currentPoint.num);
			graph[currentPoint.num].add(currentParent);
		}

		//현재 꺼낸 점과 같은 원 번호를 가진 점이 나올때까지(반대쪽 끝 점)반복
		//같은 번호가 아니라면 현재 노드를 부모로 하고 재귀 호출
		//같은 번호라면 해당 원 안에 다른 원이 더이상 없다는 뜻으로 해당 원을 부모로 하는 계산은 끝내도 됨.
		while(currentPoint.num != pq.peek().num){
			initGraph(currentPoint.num);
		}

		//반복이 끝나면, 처리한 점은 제거,
		pq.poll();
	}

	//bfs
	private static void bfs(int start, int end){

		boolean[] visited = new boolean[N + 1];
		visited[start] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(start, 1, String.valueOf(start)));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(currentNode.node == end){
				result = currentNode;
			}

			for(int nextNode : graph[currentNode.node]){

				if(visited[nextNode]) continue;

				visited[nextNode] = true;
				needVisited.add(new Node(nextNode, currentNode.count + 1, currentNode.history + " " + nextNode));
			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		graph = new List[N + 1];
		for(int i = 0; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		pq = new PriorityQueue<>((point1, point2) -> {
			return point1.x - point2.x;
		});

		//원 외부의 좌표 평면을 위해 추가(좌표 평면은 번호가 0)
		//x좌표가 -100만까지 가능하고 반지름이 1만으로, 그 범위를 벗어나도록 극단적인 값으로 추가.
		pq.add(new Point(0, -10_000_000));
		pq.add(new Point(0, 10_000_000));

		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			int num = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());

			pq.add(new Point(num, x - r));
			pq.add(new Point(num, x + r));
		}

		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		initGraph(-1);
		bfs(start, end);

		System.out.println(result.count);
		System.out.println(result.history);

	}

}
