package baekjoon.week28;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 아이디어
 * 이분탐색 +  dp
 * 참고함 : https://chb2005.tistory.com/95
 */
public class BOJ20191_줄임말 {

	//이분탐색 - T문자가 여러개 있을때는 이분탐색을 통해서 찾음.
	private static int binarySearch(int targetIndex, int left, int right, List<Integer> indexList){

		while(left < right){

			int mid = (left + right) / 2;

			if(indexList.get(mid) <= targetIndex){
				left = mid + 1;
			}
			else {
				right = mid;
			}
		}

		return indexList.get(left);

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String S = br.readLine();
		String T = br.readLine();

		//T의 문자열에서 각 알파벳이 나오는 위치정보를 담을 리스트.
		List<Integer>[] locationList = new ArrayList[26];
		for(int i = 0; i < T.length(); i++){

			char c = T.charAt(i);

			if(locationList[c - 'a'] == null){
				locationList[c - 'a'] = new ArrayList<>();
			}

			locationList[c - 'a'].add(i);
		}

		int result = 1; //문자열을 몇번 반복하는지.
		int currentIndex = -1;//현재 몇번째 인덱스에 있는지.

		//S 문자열 개수만큼 반복.
		for(int i = 0; i < S.length(); i++){
			char c = S.charAt(i);
			List<Integer> tempList = locationList[c - 'a'];

			//T에 나와야 하는 S의 문자가 없으면 만들수 없기 떄문에 종료
			if(tempList == null) {
				result = -1;
				break;
			}

			//해당 문자열의 위치중, 가장 마지막으로 나온 위치가, 현재 인덱스보다 작거나 같다면, 문자열 반복이 필요함.
			if(tempList.get(tempList.size() - 1) <= currentIndex){

				result++; //문자열 반복횟수 증가.
				currentIndex = tempList.get(0);//현재인덱스는 해당 문자열이 나오는 가장 작은 인덱스 위치로 업데이트.
			}
			//해당 문자열의 위치중, 가장 마지막으로 나온 위치가, 현재 인덱스보다 크다면 현재 문자열로도 가능하기 때문에,
			//현재 인덱스보다 크면서 가장 작은 위치를 이분탐색으로 찾아야 함.
			else{
				currentIndex = binarySearch(currentIndex, 0, tempList.size() - 1, tempList);
			}
		}

		System.out.println(result);

	}
}
