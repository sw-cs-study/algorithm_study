package baekjoon.week39;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 *  이분탐색
 *
 *  n개의 돌중에서 m개를 제거하고 하나씩 확인하는 방법으로 하면 시간이 오래걸린다.
 *  따라서 사고를 다르게 해야 하는데, 구하고자 하는 점프가능한 최소거리를 정해두고,
 *  해당 거리만 뛸 수 있을때 n-m+1개(도착지도 포함해야 함.)의 돌을 모두 밟을 수 있는지를 보는 것이다.
 *
 *  예를 들면 1 3 7 8 9라고 하고, 밟아야 하는 돌의 총 개수가 4개라고 해보자.
 *  최소거리를 2으로 잡고 계산한다면 3, 7, 9까지만 가능하다
 *  이런 경우에는 최소거리를 좀 더 줄여야지만 모든 돌섬을 밟을 수 있게 되는 것이다.
 *
 *  반대의 경우도 있다.
 *  1 3 7 10 13이고, 밟아야 하는 돌의 총 개수가 2인 경우로 가정해보자.
 *  최소거리를 2로 잡게 되면, 3, 7, 10, 13 4개가 되는데,
 *  밟아야 하는 돌의 개수 2를 넘엇기 떄문에 밟지 못하는 돌이 있어도 된다는 뜻으로,
 *  최소 거리를 좀 더 늘려도 된다.
 *
 *  위의 케이스대로 이분탐색을 통해 구해주면 된다.
 *
 */

public class BOJ6209_제자리멀리뛰기 {

	private static int d;
	private static int n;
	private static int m;
	private static List<Integer> islands;

	//구한 최소거리로 점프를 시도했을떄, 몇개의 섬을 거치는 지 확인
	private static boolean jumpCheck(int targetDistance){

		int currentLoc = 0;
		int count = 0; //주어진 최소거리로 이동가능한 섬의 수.

		for(int island : islands){

			//최소거리만큼 이동했을떄 다음 섬 위치보다 크면 최소거리가 더 작아야 한다는 뜻.
			if(currentLoc + targetDistance > island) continue;

			currentLoc = island;
			count++;
		}

		//최종적으로 개수가 (총 섬개수(탈출구 포함.) - m) 보다 크면, 최소거리를 늘려서 섬을 줄여도 됨.
		if(count >= islands.size() - m) return true;

		//섬 개수 - m 보다 작다면 불가능하다는 뜻으로 최소거리를 줄여야 함.
		return false;
	}

	//이분탐색 수행.
	private static int binarySearch(int start, int end){


		int temp = 0; // 최대값 저장.

		while(start <= end){

			int mid = (start + end) / 2;

			if(jumpCheck(mid)){
				temp = mid;
				start = mid + 1;
			}
			else{
				end = mid - 1;
			}
		}

		return temp;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		d = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		islands = new ArrayList<>();

		for(int i = 0; i < n; i++){
			islands.add(Integer.parseInt(br.readLine()));
		}
		//마지막 탈출구도 넣어줌.
		islands.add(d);

		//오름차순으로 정렬
		islands.sort(Comparator.naturalOrder());

		//최소거리의 최대값 출력
		System.out.println(binarySearch(0, d));

	}
}
