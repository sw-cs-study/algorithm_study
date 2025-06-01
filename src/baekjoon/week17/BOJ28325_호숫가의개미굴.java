package baekjoon.week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디
 * 모든 경우를 다 탐색하게 되면 노드의 수가, 250000이라서 터지게 됨.
 * 따라서 다른 방법을 생각.
 * 하나의 방에 이미 개미가 있다면, 인접한 방에는 개미가 있을 수 없음.
 * 최대로 만드는 방법은 쪽방이 존재하는 방의 경우에는 개미를 배치하지 않는 것임.
 * 이렇게 되면 쪽방에 개미를 배치 할수 있고, 연결된 다른 방에도 개미를 배치할 수 있어서 최대가 될 수 있음.
 *
 * 0번째 노드부터 순차적으로 탐색.
 * 이때 개미를 배치했는지 여부를 확인하기 위해 boolean 배열 확인.(true이면 개미를 배치함.)
 * 원형 큐 형태이므로 마지막 위치에 도달하면 첫번째 위치로 돌아가도록 설정.
 * 이전 위치가 쪽방이 붙어있는 노드(배열상에서 0이 아닌 노드)라면 현재 위치에 개미를 추가.
 *
 * (주의)
 * 원형큐라는 점을 항상 조심해야 함.
 * 또한 노드수에 따라 배치를 어떻게 하느냐에 따라서 3개를 배치할 수도 2개를 배치할 수도 있음.
 *
 */
public class BOJ28325_호숫가의개미굴 {

	private static int N;

	//이전 위치 구하는 메서드(원형큐 형태라)
	private static int getPrevIdx(int currentIdx){
		return ((currentIdx - 1) + N) % N;
	}

	private static int getNextIdx(int currentIdx){
		return (currentIdx + 1) % N;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		long[] numArray = new long[N];
		boolean[] antCheck = new boolean[N];

		long result = 0;

		st = new StringTokenizer(br.readLine());
		int currentIdx = -1;
		for(int i = 0; i < N; i++){
			numArray[i] = Long.parseLong(st.nextToken());

			if (currentIdx == -1 && numArray[i] != 0){
				currentIdx = i;
			}
		}

		if(currentIdx == -1){ currentIdx = 0;}



		for(int i = 0; i < N; i++){

			//현재 위치가 쪽방이면 쪽방수 더하고 패스.
			if(numArray[currentIdx] != 0){
				result += numArray[currentIdx];
				currentIdx = getNextIdx(currentIdx);
				continue;
			}

			//이전 값 확인
			int prevIdx = getPrevIdx(currentIdx);

			//이전 위치가 쪽방이 아니고, 개미가 배치되어있으면 패스.
			if (numArray[prevIdx] == 0 && (antCheck[prevIdx] || antCheck[getNextIdx(currentIdx)])){
				currentIdx = getNextIdx(currentIdx);
				continue;
			}


			//현재위치에 개미 배치
			result += 1;
			antCheck[currentIdx] = true;
			currentIdx = getNextIdx(currentIdx);
		}

		System.out.println(result);

	}
}