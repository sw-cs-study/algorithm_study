package baekjoon.week35;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디
 * 메모리 교체 알고리즘의 아이디어를 착안해서 해결한다.
 * 멀티탭이 비어있으면 그대로 꽂으면 되고,
 * 멀티탭이 가득 차있으면 뽑을 대상을 선정한다.
 * 뽑는 수를 최소로 해야 하기 때문에, 뽑을 대상을 선정할때는
 * 주어진 사용 순서상, 가장 오랫동안 사용이 되지 않을 것을 찾으면 됨.(가장 오랫동안 사용하지 않는것을 고르면 뽑아야 되는 시점이 나중에 오기 때문에 유리함.)
 *
 */
public class BOJ1700_멀티탭스케줄링 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] deviceInfo = new int[K];

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < K; i++){
			deviceInfo[i] = Integer.parseInt(st.nextToken());
		}

		//멀티탭 정보
		Set<Integer> multiTab = new HashSet<>();
		int result = 0;


		//반복문 돌면서 멀티탭에 꽂기
		for(int i = 0; i < K; i++){

			int currentNum = deviceInfo[i];

			//멀티탭에 있으면 패스
			if(multiTab.contains(currentNum)) continue;

			//꽂을 구멍이 있으면 추가하고 패스.
			if(multiTab.size() < N) {
				multiTab.add(currentNum);
				continue;
			}

			//앞으로 멀티탭의 콘센트가 쓰이는지 확인하기 위한 set - 앞으로 안쓰이면 해당 기기 선택.
			Set<Integer> visited = new HashSet<>();

			//선택한 장비 다음것부터 확인하면서, 현재 멀티탭에서 사용중이면서 앞으로 나올 것 선택.
			int tempSelect = -1;// 가장 오랫동안 사용하지 않을 후보 - 아예 사용하지 않는 기기가 나오면 해당 기기 선택.
			for(int j = i + 1; j < K; j++){

				int nextNum = deviceInfo[j];

				//멀티탭에 없거나, 이미 방문처리 했으면 패스.
				if(!multiTab.contains(nextNum) || visited.contains(nextNum)) continue;

				visited.add(nextNum);
				tempSelect = nextNum;
			}

			//멀티탭에 있는데, 앞으로 사용이 안될 것 찾기.

			for(int multi : multiTab){

				if(visited.contains(multi)) continue;

				tempSelect = multi; //멀티탭에는 있지만, 앞으로 나오지 않는거라면 해당 기기 선택.
				break;

			}
			if(tempSelect != -1) {
				multiTab.remove(tempSelect);
				result++;
			}
			multiTab.add(currentNum);

		}

		System.out.println(result);
	}
}
