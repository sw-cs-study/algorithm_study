package programmers.week50;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 아이디어
 * 그래프  + 구현
 * 우선 주어진 형태로는 그래프를 다루기 힘들기 때문에 분리를 한다.
 * 각 도시가 노드이고, 추가로 교점을 이중 반복문을 통해 구하여 노드로 만든다.
 * 각 노드 사이에 단속카메라가 있으면, 그중 가장 작은 값으로 간선의 가중치로 만든다.
 * 그렇게 되면 간선이 있는 그래프에서 최대 가중치를 구하는 문제가 된다.
 *
 */
public class Prog_최고속도 {

	//노드 정보
	private static class Node{
		int x, y, limit;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
		//카메라 같은 노드 표기용
		public Node(int x, int y, int limit){
			this.x = x;
			this.y = y;
			this.limit = limit;
		}
	}

	//그래프 노드
	private static class GNode{
		int node, weight;

		public GNode(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	//도로 정보.
	private static class Road{
		int x1, y1, x2, y2, limit;

		public Road(int x1, int y1, int x2, int y2, int limit){
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.limit = limit;
		}

		//도로가 가로방향인지 세로 방향인지 확인 - 인접 간선을 구하기 위해서 도로 위의 점을 정렬해야 하는데, 방향에 따라 달라지기 때문에
		boolean isHorizontal() {
			return this.y1 == this.y2;
		}
	}

	//간선 정보 - 그래프 구성을 위해, 도로위의 노드들을 모아서 간선을 모두 구함.
	private static class Edge{
		int node1, node2, weight;

		public Edge(int node1, int node2, int weight){
			this.node1 = node1;
			this.node2 = node2;
			this.weight = weight;
		}
	}

	//다익스트라 탐색시, 단속 카메라가 없는 구간은 가중치를 무한대로 표기(그래야 경로중 최소 값을 구할 수 있음)
	//limit최대가 100000
	private final static int INF = 1_000_001;

	private static Road[] roads;//길정보.
	private static Map<Long, Integer> nodeDupMap;//중복 노드 체크를 위한 맵
	private static List<Node> nodeList; // 노드정보 - 노드id -> node객체
	private static Map<Long, Integer> cameraLimitMap;// 카메라 정보(중복정보 처리하고 새로 저장.)
	private static List<GNode>[] graph;//그래프 구성.

	//좌표를 유니크한 키로 변환 -> 좌표를 가지고 중복 체크하는 용도
	private static long convert(int x, int y){
		return ((long) x + 1_000_000_000L) * 2_000_000_001L + ((long) y + 1_000_000_000L);
	}


	private static int getId(int x, int y){
		long key = convert(x, y);
		//있으면 반환.
		if(nodeDupMap.containsKey(key)) return nodeDupMap.get(key);

		//없으면 노드 정보 배열에서 만듦.
		int nextId = nodeList.size();
		nodeDupMap.put(key, nextId);

		//해당 좌표가 카메라에 있으면 카메라임.
		nodeList.add(new Node(
			x,
			y,
			cameraLimitMap.getOrDefault(key, INF)
		));

		return nextId;
	}

	//도로 방향 확인 - 특정 도시 좌표가 임의의 도로 위의 점인지 확인하기 위함.
	private static boolean nodeOnRoadCheck(int x, int y, Road r){

		//가로 방향이면, y축 값이 같고, x는 도로 범위여야 함.
		if(r.isHorizontal()) return y == r.y1 && x >= r.x1 && x <= r.x2;
		else return x == r.x1 && y >= r.y1 && y <= r.y2;
	}

	//교점 구하기 - 두 도로 정보를 받아서 나올 수 있는 모든 교점을 구함.
	private static Node intersect(Road a, Road b){

		//같은 방향인 경우, - - 이렇게 연결되는 경우가 있음.
		if(a.isHorizontal() && b.isHorizontal()){

			//y가 같지 않으면 패스.
			if(a.y1 != b.y1) return null;

			//끝점에서 연결되는 케이스인지 확인 - 시작점 x좌표중 큰 것과, 끝점 x좌표중 작은 것이 같으면 끝점에서 만나는 것.
			return Math.max(a.x1, b.x1) == Math.min(a.x2, b.x2) ?
				new Node(
					Math.max(a.x1, b.x1),
					a.y1
				):
				null;
		}
		//세로로 이어지는 경우,
		if(!a.isHorizontal() && !b.isHorizontal()){
			//x가 같지 않으면 패스.
			if(a.x1 != b.x1) return null;

			//끝점에서 연결되는 케이스인지 확인 - 시작점 y좌표중 큰 것과, 끝점 y좌표중 작은 것이 같으면 끝점에서 만나는 것.
			return Math.max(a.y1, b.y1) == Math.min(a.y2, b.y2) ?
				new Node(
					a.x1,
					Math.max(a.y1, b.y1)
				):
				null;
		}

		//그 외의 케이스 - 교차 하는 경우.
		Road horizRoad = a.isHorizontal() ? a : b;
		Road vertRoad = a.isHorizontal() ? b : a;

		//안겹치는 경우는 패스.
		if(vertRoad.x1 < horizRoad.x1 || vertRoad.x1 > horizRoad.x2 || horizRoad.y1 < vertRoad.y1 || horizRoad.y2 > vertRoad.y2) return null;

		return new Node(
			vertRoad.x1,
			horizRoad.y1
		);
	}


	//다익스트라.
	private static int[] dijkstra(int startId){

		int[] distance = new int[nodeList.size()];
		Arrays.fill(distance, -1); // max heap이라 -1로 잡음.

		distance[startId] = INF;

		PriorityQueue<GNode> pq = new PriorityQueue<>((o1, o2) -> {
			return o2.weight - o1.weight;
		});
		pq.add(new GNode(startId, INF));

		while(!pq.isEmpty()){

			GNode currentNode = pq.poll();

			//특정 위치까지갈수 있는 최소 속도 중, 최대값을 구하는 것이므로 현재 탐색 노드의 가중치가 저장된 값보다 더 작다면 볼 필요 없음.
			if(currentNode.weight < distance[currentNode.node]) continue;

			for(GNode nextNode : graph[currentNode.node]){

				int nextWeight = Math.min(currentNode.weight, nextNode.weight); //다음 위치의 가중치(제한속도)중에 작은게 후보임.

				if(distance[nextNode.node] >= nextWeight) continue;

				pq.add(new GNode(
					nextNode.node,
					nextWeight
				));

				distance[nextNode.node] = nextWeight;
			}

		}
		return distance;

	}

	//초기값
	private static void init(){
		nodeDupMap = new HashMap<>();
		nodeList = new ArrayList<>();
		cameraLimitMap = new HashMap<>();
	}

	public int[] solution(int[][] city, int[][] road) {

		int n = city.length;
		int m = road.length;

		init();

		//도로 정보를 다루기 편하게 변환.
		roads = new Road[m];
		for(int i = 0; i < m; i++){
			roads[i] = new Road(
				road[i][0],
				road[i][1],
				road[i][2],
				road[i][3],
				road[i][4]
			);
		}

		//도시정보를 키 값으로 저장.
		int[] cityId = new int[n];
		for(int i = 0; i < n; i++){
			cityId[i] = getId(city[i][0], city[i][1]);
		}

		//카메라 제한 속도 저장 - 중복되는 위치가 있으면 더 작은 값으로 업데이트.
		for(Road r : roads){
			int tempX = (r.x1 + r.x2) / 2;
			int tempY = (r.y1 + r.y2) / 2;

			long key = convert(tempX, tempY);

			//포함되어있지 않거나, 기존 값보다 작을떄만 저장.
			if(cameraLimitMap.containsKey(key) && r.limit > cameraLimitMap.get(key)) continue;

			cameraLimitMap.put(key, r.limit);
		}

		//도로별로 그 위에 놓잉ㄴ 노드들 모으기 - 간선만들기 위함.
		List<List<Integer>> roadPoints = new ArrayList<>();
		for(int i = 0; i < m; i++) roadPoints.add(new ArrayList<>());

		//도로 별로 채우기 - 카메라 추가.
		for(int i = 0; i < m; i++){
			Road r = roads[i];

			//카메라 추가.
			int tempX = (r.x1 + r.x2) / 2;
			int tempY = (r.y1 + r.y2) / 2;

			roadPoints.get(i).add(getId(tempX, tempY));
		}
		//도로 위의 도시 채우기.
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){

				//도로 위의 도시가 아니면 패스.
				if(!nodeOnRoadCheck(city[i][0], city[i][1], roads[j])) continue;

				roadPoints.get(j).add(cityId[i]);
			}
		}

		//교점 정보 구해서 넣기.
		for(int i = 0; i < m; i++){
			for(int j = i + 1; j < m; j++){
				Node node = intersect(roads[i], roads[j]);

				//null이면 교점이 없다는 뜻으로 패스.
				if(node == null) continue;
				int id = getId(node.x, node.y); //getId 메서드 내부에서 해당좌표가 카메라이면, 노드 저장시에 가중치도 같이 저장함.
				roadPoints.get(i).add(id);
				roadPoints.get(j).add(id);
			}
		}


		//그래프 구성.
		graph = new List[nodeList.size()];
		for(int i = 0; i < nodeList.size(); i++){
			graph[i] = new ArrayList<>();
		}


		//도로를 기준으로 모든 노드를 정렬하고 이어봄.
		for(int i = 0 ; i < m; i++){
			Road r = roads[i];

			//리스트에 도로위 노드를 넣고 정렬 - roadPoints에서 교점 구할때 중복이 발생했을수 있어, set으로 중복 제거,
			List<Integer> sortList = new ArrayList<>(new HashSet<>(roadPoints.get(i)));

			//정렬
			sortList.sort((id1, id2) -> {

				//정렬에 필요한 좌표 정보는 노드 리스트에 있기 떄문에 꺼내옴.
				Node node1 = nodeList.get(id1);
				Node node2 = nodeList.get(id2);

				//수평이면  x 값을 기준으로 정렬하면 됨.
				if(r.isHorizontal()) return Integer.compare(node1.x, node2.x);
				//수직이면 y 값을 기준으로 정렬.
				else return Integer.compare(node1.y, node2.y);
			});

			//정렬이 완료되었으면 두개씩 비교해서 처리.
			for(int j = 0; j < sortList.size() - 1; j++){

				int point1 = sortList.get(j);
				int point2 = sortList.get(j + 1);
				int weight = Math.min(nodeList.get(point1).limit, nodeList.get(point2).limit); // 가중치는 둘중 작은 쪽으로.

				//그래프에 추가.
				graph[point1].add(new GNode(point2, weight));
				graph[point2].add(new GNode(point1, weight));

			}
		}

		int[] temp = dijkstra(cityId[0]);
		int[] answer = new int[n - 1];

		//INF인것은 전부 0으로 치환
		for(int i = 1; i < n; i++){
			answer[i - 1] = temp[i] == INF ? 0 : temp[i];

		}
		return answer;
	}

	public static void main(String[] args){

		Prog_최고속도 p = new Prog_최고속도();

		int[][] city1 = {{-1, 3}, {7, 3}, {1, -1}, {-2, 6}};
		int[][] road1 = {{-1, 7, 7, 7, 80}, {-3, 3, 9, 3, 45}, {-2, -4, -2, 6, 60}, {1, -4, 1, 8, 50}, {5, 1, 5, 7, 70}};
		System.out.println(Arrays.toString(p.solution(city1, road1)));

		int[][] city2 = {{3, 5}, {3, 3}, {2, 1}, {9, 1}, {7, -1}};
		int[][] road2 = {{3, -2, 3, 4, 30}, {5, 1, 9, 1, 29}, {3, 4, 3, 8, 99}, {1, 1, 5, 1, 99}, {7, -3, 7, 5, 99}};
		System.out.println(Arrays.toString(p.solution(city2, road2)));

	}
}
