package baekjoon.week41;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 이분탐색
 */
public class BOJ1508_레이스 {

	private static int N;//트랙길이
	private static int M;//심판
	private static int K;//심판 배치 수
	private static int[] judgements;//심판 배치 위치.

	//그리디를 이용해서 파악.
	private static String check(int target){

		//심판 배치 수
		int count = 0;
		//심판 배치 문자열
		StringBuilder sb = new StringBuilder();

		//첫번째 위치에 배치함 -> 심판 간격을 크게 하려면 첫번째 위치에 배치하는게 이득
		count = 1;
		sb.append("1");

		int prevLoc = judgements[0];

		//두번째 위치부터 반복하면서, 주어진 거리(target)보다 작지 않으면 배치하는 식으로 반복
		for(int i = 1; i < K; i++){

			//이미 배치를 다 했으면 0을 붙임.
			if(count == M){
				sb.append("0");
				continue;
			}

			//조건에 맞으면 추가함.
			if(judgements[i] - prevLoc >= target){
				sb.append("1");
				count++;
				prevLoc = judgements[i];
				continue;
			}

			//조건에 안맞으면 배치를 안하고 넘어감.
			sb.append("0");
		}

		//최종적으로 심판 배치수가 M이면 배치 문자열을 반환.
		if(count == M) return sb.toString();

		return "";
	}

	//이분 탐색을 통해서 찾기.
	private static String binarySearch(int start, int end){

		String result = "";

		while(start <= end){

			int mid = (start + end) / 2;

			String temp = check(mid);

			//심판을 배치해봤을때, 빈문자열이 나온다면, 주어진 거리로는 전원배치가 불가능하다는 것으로, 거리를 줄여봐야 함.
			if(temp.equals("")){
				end = mid - 1;
			}
			//주어진 최소 거리(mid)보다 더 크다면 더 키워도 됨.
			else {

				result = temp;
				start = mid + 1;
			}
		}

		return result;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		judgements = new int[K];

		for(int i = 0; i < K; i++){
			judgements[i] = Integer.parseInt(st.nextToken());
		}

		System.out.println(binarySearch(1, N));

	}
}
