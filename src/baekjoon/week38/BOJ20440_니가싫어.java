package baekjoon.week38;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 좌표압축
 *
 * 누적합이 가장 먼저 떠오르지만 시간이 21억이라 메모리 초과가 남.
 */
public class BOJ20440_니가싫어 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		//key : 시간, value : 모기 수.(퇴장이면 -1, 입장이면 +1로 유지)
		Map<Integer, Integer> timeMap = new HashMap<>();

		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			int TE = Integer.parseInt(st.nextToken());
			int TX = Integer.parseInt(st.nextToken());

			//입장이면 모기수 +1, 퇴장이면 -1
			timeMap.put(TE, timeMap.getOrDefault(TE, 0) + 1);
			timeMap.put(TX, timeMap.getOrDefault(TX, 0) - 1);
		}

		//시간순으로 오름차순 정렬하고, 반복문으로 돌면서 모기수 합치기
		List<Integer> orderKey = new ArrayList<>(timeMap.keySet());
		orderKey.sort((node1, node2) -> {
			return node1 - node2;
		});

		//구간 최대값 찾기.
		int resultValue = 0;
		int resultStart = 0;
		int resultEnd = 0;

		int temp = 0;
		boolean flag = false; //구간 체크용.
		for(int key : orderKey){

			temp += timeMap.get(key);

			if(temp > resultValue){
				resultValue = temp;
				resultStart = key;
				flag = true; //최대 값이 등장하는 시점에 구간체크
			}
			//이전에 저장된 최대값 보다 작은데 최대값이 등장해서 구간체크가 되고 있는 상태라면 -> 최대값이 나오는 구간이 끝남(퇴장했다는 뜻), 즉 구간저장하고 닫으면 됨.
			else if(temp < resultValue && flag){
				resultEnd = key;
				flag = false;
			}
		}

		System.out.println(resultValue);
		System.out.println(resultStart + " " + resultEnd);

	}
}
