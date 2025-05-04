package baekjoon.week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 이분탐색
 * 몇번째 상자를 선택해야 되는지를 이분탐색을 고르게 한다.
 * 고른 후에는 해당 상자까지 몇개의 도토리가 담겼는지 반복문을 돌면서 체크한다.
 * (시작) (끝) (간격) 이 주어지는데, 이 사이에 몇개의 상자에 도토리를 담을 수 있는지는 ((끝) - (시작)) % (간격) + 1로 계산하면 된다.
 * 주어지는 규칙의 개수는 10^5이므로 이분탐색으로 하나의 상자를 고르게 될때마다 10^5를 반복하는 것이다.
 * 최종 시간복잡도를 계산하면 10^5 * log10^6 => 약 10^6 * 1.8이 나오게 된다.
 *
 * (주의 사항)
 * 규칙에서 상자사이에 몇개의 상자가 나오는지 계산하면 최악의 케이스에는 10^6 * 10^5 => 10^11 이 나온다
 * 따라서 해당 개수를 셀때는 long으로 처리해야 한다.
 */

public class BOJ15732_도토리숨기기 {

	//규칙 정보를 담을 노드
	private static class Node{
		int a, b, c;

		public Node(int a, int b, int c){
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	private static int N;//상자의 수
	private static int K;//규칙의 수
	private static int D;//도토리

	//규칙 정보를 담을 리스트
	private static List<Node> ruleList;

	//특정 상자까지의 담을수 있는 도토리 개수
	private static long getCount(int boxNum){

		long count = 0;
		for(Node node : ruleList){


			//주어진 박스가 규칙의 최소 박스보다 작으면 볼 필요 없음.
			if(node.a > boxNum) continue;

			//주어진 박스가 규칙의 최대 박스보다 크거나 같으면 최대 박스까지만 보면됨.
			if(node.b <= boxNum){
				count += ((node.b - node.a) / node.c) + 1;
				continue;
			}

			count += ((boxNum - node.a) / node.c) + 1;
		}

		return count;
	}

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		ruleList = new ArrayList<>();

		for(int i = 0; i < K; i++){
			st = new StringTokenizer(br.readLine());

			ruleList.add(new Node(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			));
		}

		int start = 1;
		int end = N;

		int result = 0;

		while(start <= end){

			int mid = (start + end) / 2;

			long tempCount = getCount(mid);
			//해당 박스까지 도토리가 주어진 도토리보다 더 많으면 박스 위치를 더 줄여봐야 됨.
			if(tempCount < D){
				start = mid + 1;
			}
			else {
				end = mid - 1;
			}
		}

		System.out.println(start);



	}
}
