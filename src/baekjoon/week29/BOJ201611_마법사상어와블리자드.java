package baekjoon.week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현.
 * 주어진대로 단계별로 동작하도록 구현하면 된다.
 * 기능의 경우에는 메서드로 만들어서 로직의 흐름이 잘 보이게 하면 좋다.
 * 특히 토네이도 모양의 배열에 번호를 붙어야 하는데, 번호별 좌표를 구해서 미리 배열에 저장해두면 쉽게 풀이가 가능해진다.
 *
 * 주의
 * 문제의 주석에서 배열을 순서대로 탐색한다는 것은, 토네이도 모양으로 탐색한다는 것.
 *
 * 총 시간 복잡도.
 * 1. 블리자드 => O(N / 2) => O(N)
 * 2. 구슬이동 => O(N^2) + O(N^2) => O(N^2)
 * 3. 폭발 => O(N^2) + O(N^2) => O(N^2)
 * 4. 구슬 변화 => O(N^2) + O(N^2) => O(N^2)
 *
 * 구슬이동과 폭발은 여러번 반복함 => 실제로는 구슬이동과 폭발의 최악의 경우의수가 동시에 나올 순 없긴 하지만, 계산하면
 * 매 폭발마다 4개의 그룹이 1개씩 있어서 연속으로 터지는 경우로 가정
 * N은 최대 50으로 잡고 전체 칸수는 2500 => 2.5 * 10^3
 * 매 폭발마다 4개씩 날아가면, 625번 폭발가능함(상어 위치등은 빼고 계산하기 편하게 칸 전부 포함.)
 * O(N) + O(N^2) + 625*O(N^2) + 625*O(N^2) + O(N^2)
 * => 5 * 10^3 + 5*10 + 3.125 * 10^6 => 1초 미만 수행 가능.
 */

public class BOJ201611_마법사상어와블리자드 {

	//4방향 => 인덱스 1부터 사용, 상하좌우
	private final static int[] dx = {0, -1, 1, 0, 0};
	private final static int[] dy = {0, 0, 0, -1, 1};



	//각 칸 좌표 정보를 나타낼 객체.
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}

	}

	private static int N;//격자판 크기.
	private static int M;//블리자드 시전 횟수.
	private static int[][] maps;//격자 판.

	private static Node initLoc;//상어의 초기 위치

	private static int[] explosionCount;//각 구슬별 폭발개수 누적 => 인덱스 1부터 사용

	private static Node[] mapNumber;//각 좌표별 번호를 담은 배열.

	//격자판을 넘어가는지 확인
	private static boolean check(int nextX, int nextY){
		return nextX >= 1 && nextX <= N &&
			nextY >= 1 && nextY <= N;
	}


	//블리자드 마법기능 수행(메서드) - 방향(d), 거리(s)를 입력으로 받음.
	//시간복잡도 - 상어 위치로부터 한쪽방향으로만 날리기 때문에 N / 2
	//O(N / 2) => O(N)
	private static void executeBlizzard(int d, int s){

		//거리 s 만큼 이동하면서 전부 0으로 만듦.
		for(int i = 1; i <= s; i++){

			int nextX = initLoc.x + dx[d] * i;
			int nextY = initLoc.y + dy[d] * i;

			if (!check(nextX, nextY)) return;

			maps[nextX][nextY] = 0;

		}

	}

	//구슬이동 수행(메서드)
	//시간복잡도 => [0이 아닌 칸을 큐에 넣음] - O(N^2) +  [큐를 돌면서 처리] - O(N^2) => O(N^2)
	//최악의 경우는 1번칸이 제거되어 모든 구슬을 한칸씩 땡기는 경우.
	private static void moveBead(){

		//1번위치부터 0이 아닌 칸을 큐에 넣음.
		Queue<Integer> updateQueue = new ArrayDeque<>();
		for(int i = 1; i <= (N * N) - 1; i++){

			if(maps[mapNumber[i].x][mapNumber[i].y] == 0) continue;

			updateQueue.add(i);
		}


		int idx = 1;
		while(!updateQueue.isEmpty()){

			Node currentNode = mapNumber[updateQueue.poll()];
			int updateValue = maps[currentNode.x][currentNode.y];
			maps[currentNode.x][currentNode.y] = 0;

			Node target = mapNumber[idx++];

			maps[target.x][target.y] = updateValue;
		}
	}

	//구슬 폭발(메서드) - 연속구슬이 4개 이상이면 폭발하고, 폭발한 개수를 누적. 반환값으로 폭발처리 여부.(false: 폭발안함.)
	//시간복잡도 => [전체 배열을 순회하면 체크] - O(N ^ 2) + [배열 순회시, 모두가 그룹크기 4이상에 속해 있어서,큐에 넣기 위해 역으로 한번더 탐색] - O(N ^ 2)
	// => O(N ^ 2)
	private static boolean explosionBead(){

		Queue<Integer> explosionQueue = new ArrayDeque<>();

		//배열을 순서대로 돌면서, 연속구슬이 4개이상이면, 해당 좌표들을 큐에 넣음
		int count = 1;//터트릴 구슬의 수.
		int currentType = maps[mapNumber[1].x][mapNumber[1].y];//연속되는 구슬의 번호.
		for(int i = 2; i <= (N * N) - 1; i++){

			Node currentNode = mapNumber[i];
			int nextType = maps[currentNode.x][currentNode.y];

			//두 구슬 타입이 같으면 누적.
			if(currentType == nextType){
				count++;
			}
			//두 구슬 타입이 다르면 누적 갯수 확인 필요.
			else {
				//누적된 구슬이 4개 이상이라면 폭발가능하기 때문에 큐에 담음.
				if(count >= 4){

					//현재 위치부터 개수만큼 역으로 배열을 순회하며 좌표 넣음
					for(int tmp = i - 1; tmp > i - 1 - count; tmp--){
						explosionQueue.add(tmp);
					}
				}
				//4개 미만이면 누적구슬카운트 초기화진행.
				count = 1;
			}

			currentType = nextType;
		}

		if(explosionQueue.isEmpty()) return false;

		//큐에 있는 구슬들을 터트리면서 처리.
		while(!explosionQueue.isEmpty()){

			Node currentNode = mapNumber[explosionQueue.poll()];

			//터트리기전에 해당 값을 누적
			explosionCount[maps[currentNode.x][currentNode.y]]++;

			maps[currentNode.x][currentNode.y] = 0;
		}

		return true;
	}

	//구슬의 변화(메서드) - 연속하는 구슬을 그룹으로 보고 각 그룹은 두개의 구슬로 변화하며 그룹순서대로 1번위치부터 넣어줌.
	//시간 복잡도 - [모든 배열을 한번씩 순회] - O(N ^ 2) + [큐에 있는 것을 탐색하며 배열에 추가, 배열개수 넘어가면 버림] - O(N^2)
	// => O(N ^ 2)
	private static void changeBead(){

		Queue<Integer> updateQueue = new ArrayDeque<>();

		//순서대로 그룹을 확인하고 큐에 넣어 처리.
		int count = 1;//그룹 수.
		int currentType = maps[mapNumber[1].x][mapNumber[1].y];//연속되는 구슬의 번호.
		for (int i = 2; i <= (N * N) - 1; i++){
			Node currentNode = mapNumber[i];
			int nextType = maps[currentNode.x][currentNode.y];

			//두 타입이 같은 경우에는 그룹수를 누적하고 패스.
			if(currentType == nextType){
				count++;
			}
			//두 타입이 달라지만, 이전 그룹의 정보를 큐에 넣고 초기화.
			else{
				updateQueue.add(count);
				updateQueue.add(currentType);
				count = 1;
				currentType = nextType;
			}
		}


		//큐에 넣은대로 배열을 업데이트.
		int idx = 1;
		while(!updateQueue.isEmpty()) {

			int currentValue = updateQueue.poll();

			Node currentNode = mapNumber[idx++];

			//배열 칸을 넘어가면 그냥 버림.
			if(idx > (N * N) - 1) break;

			maps[currentNode.x][currentNode.y] = currentValue;
		}
	}

	//디버깅 용
	private static void printMap(){
		for(int i = 1; i <= N; i++){
			for(int j = 1; j <= N; j++){
				System.out.print(maps[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("++++++++++++++++++++++++");
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		maps = new int[N + 1][N + 1];
		explosionCount = new int[4];
		mapNumber = new Node[N * N + 1];

		initLoc = new Node((N + 1) / 2, (N + 1) / 2);

		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= N; j++){
				maps[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//각 좌표별 번호를 저장해둠.

		int dir = 0;
		int currentX = initLoc.x;
		int currentY = initLoc.y;

		int moveCount = 0; // 같은 방향으로 움직인 칸수.
		int changeCount = 1; // 방향전환에 필요한 칸수.
		int checkCount = 0; // 방향 전환을 한 횟수 저장.(2회가 되면 방향전환에 필요한 이동칸수를 증가시킴.)

		//회전하는 방향
		int[] rotateDir = {3, 2, 4, 1};

		//방향은 상어의 초기 위치부터 3 -> 2 -> 4 -> 1 순으로 변경되며
		//각 방향으로 1칸이동 2번, 2칸이동 2번, 3칸이동 3번 이런식으로 이동칸수가 늘어나게 된다.
		for(int i = 1; i <= (N * N) - 1; i++){

			currentX = currentX + dx[rotateDir[dir]];
			currentY = currentY + dy[rotateDir[dir]];

			mapNumber[i] = new Node(currentX, currentY);

			moveCount++;

			//같은 방향으로 움직인 칸수가, 방향 전환에 필요한 칸수만큼 움직였으면 방향 전환.
			if(moveCount == changeCount){
				dir = (dir + 1) % 4;
				checkCount++;
				moveCount = 0;
			}

			//방향 전환을 2회 하면, 이동칸수를 증가시킴
			if(checkCount == 2){
				changeCount++;
				checkCount = 0;
			}

		}


		//블리자드 시전
		for(int i = 0; i < M; i++){

			st = new StringTokenizer(br.readLine());

			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());

			//1.블리자드 마법 실행
			executeBlizzard(d, s);

			do{
				//2.구슬 이동 - 블리자드 마법 실행 후 한번은 무조건 실행.
				moveBead();
			} while(explosionBead());//3.구슬 폭발 -> 구슬 폭발 여부에 따라서 구슬이동 반복.

			//4. 구슬 변화
			changeBead();

		}

		//1×(폭발한 1번 구슬의 개수) + 2×(폭발한 2번 구슬의 개수) + 3×(폭발한 3번 구슬의 개수)
		System.out.println(explosionCount[1] + explosionCount[2] * 2 + explosionCount[3] * 3);
	}
}
