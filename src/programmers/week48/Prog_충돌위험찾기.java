	package programmers.week48;

	import java.util.HashMap;
	import java.util.Map;

	/**
	 * 아이디어
	 * 빡구현.
	 */

	public class Prog_충돌위험찾기 {

		//상, 하, 좌, 우
		private static int[] dx = {-1, 1, 0, 0};
		private static int[] dy = {0, 0, -1, 1};

		private static int r;
		private static int c;
		private static int result;
		private static Map[][] maps;

		private static void init(){

			result = 0; // 누적 위 상황

			// 1<= r, c <= 100  / 좌표는 1,1 부터 사용.
			r = 101;
			c = 101;
			maps = new Map[r][c];

			for(int i = 1; i < r; i++){
				for(int j = 1; j < c; j++){
					maps[i][j] = new HashMap<Integer, Integer>(); // 시간 : 충돌횟수.
				}
			}
		}

		//각 로봇이 특정 포인트에서 특정 포인트로 이동
		//이동하면서 maps내의 해시를 업데이트, 해당 시간에 해당위치를 방문한적 없음
		private static void moveRobot(int[][] points, int[] route){

			int currentIdx = route[0];
			int currentX = points[currentIdx - 1][0];
			int currentY = points[currentIdx - 1][1];

			int currentTime = 0;

			for(int i = 1; i < route.length; i++){
				int targetIdx = route[i];
				int targetX = points[targetIdx - 1][0];
				int targetY = points[targetIdx - 1][1];

				//목적지에 도달할떄까지 반복,
				while(currentX != targetX || currentY != targetY){

					Map<Integer,Integer> tempMap = maps[currentX][currentY];

					//현재시간 추가 - 이미 있으면 +1로 업데이터, 있는데 값이 0이면 위험상황 카운트로 증가.
					tempMap.put(currentTime, tempMap.getOrDefault(currentTime, 0) + 1);

					if(tempMap.get(currentTime) == 2){
						result++;
					}
					//이동 - 행을 먼저 타겟의 행과 맞추고, 열을 움직임.
					if(currentX != targetX){
						currentX = currentX + (currentX - targetX < 0 ? 1 : -1);
					}
					//행이 같으면 열을 움직임.
					else{
						currentY = currentY + (currentY - targetY< 0 ? 1 : -1);
					}
					currentTime++;
				}

				//최종 목적지 일때 값 누적 필요.
				if(i == route.length - 1){
					Map<Integer,Integer> tempMap = maps[targetX][targetY];

					//현재시간 추가 - 이미 있으면 +1로 업데이터, 있는데 값이 0이면 위험상황 카운트로 증가.
					tempMap.put(currentTime, tempMap.getOrDefault(currentTime, 0) + 1);

					if(tempMap.get(currentTime) == 2){
						result++;
					}
				}
			}
		}

		//로직
		private static void logic(int[][] points, int[][] routes){

			//로봇 별로 처리.
			for(int[] route : routes){
				moveRobot(points, route);
			}

		}

		public int solution(int[][] points, int[][] routes) {
			init();
			logic(points, routes);

			return result;
		}

		public static void main(String[] args){

			Prog_충돌위험찾기 p = new Prog_충돌위험찾기();

			int[][] points1 = {{3, 2}, {6, 4}, {4, 7}, {1, 4}};
			int[][] routes1 = {{4, 2}, {1, 3}, {2, 4}};
			System.out.println(p.solution(points1, routes1));

			int[][] points2 = {{3, 2}, {6, 4}, {4, 7}, {1, 4}};
			int[][] routes2 = {{4, 2}, {1, 3}, {4, 2}, {4, 3}};
			System.out.println(p.solution(points2, routes2));

			int[][] points3 = {{2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}};
			int[][] routes3 = {{2, 3, 4, 5}, {1, 3, 4, 5}};
			System.out.println(p.solution(points3, routes3));
		}
	}
