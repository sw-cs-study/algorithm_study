package baekjoon.week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 아이디어
 * 자료구조 + 구혀
 *
 * 단순 구현문제
 * 이때 주의할 점은 N과 Q의 크기가 크기 때문에 연산이 NlogN이하로 나오게 해야 한다.
 *  pq를 이용하여 처리한다.
 * 포트에 충전기를 꽂을때, 이미 꽂혀 있다면 남은 것들중에 최소 전력인것을 출력해야 하기 때문에 pq를 이용하도록 한다.
 * 꽂혀있는 것을 뺄때는 포트 정보가 담긴 자료구조에서 업데이트 후에, 꽂혀있지 않은 정보를 담은 pq에 넣도록 처리한다.
 *
 *
 * (추가)
 * 문제 요건을 배열과 pq로만 해결하려고 하니, "최소 전력 이상의 포트 중 가장 전력이 작은 포트" 이부분에서 탐색에 시간이 오래걸린다.
 * pq에서 poll을 하면서 처음으로 요청한 최소 전력을 넘는 값을 찾아야 하고, 찾기 전까지 출력한 값들은 모아뒀다가 다시 pq에 넿게 되는데,
 * 이 과정에서 많은 연산을 소모하게 된다.
 * treeset을 사용하면 특정 값보다 작은 개체들의 값을 반환하는 메서드 등이 있고 이를 활용하면 훨씬 빠르게 계산할 수 있다.
 */
public class BOJ32387_충전하기 {

	private static class Node{

		int idx, activeCount;// 꽂은 포트의 번호와, 몇번째 행동에서 꽂힌 것인지

		public Node(int idx, int activeCount){
			this.idx = idx;
			this.activeCount = activeCount;
		}

	}


	private static int N;//포트 수
	private static int Q;//행동의 수
	private static Node[] portStatus;//전체 포트의 상태가 담긴
	private static TreeSet<Integer> ts;//미사용 포트 정보가 담긴 pq

	//전력
	//행동에 따른 결과 반환 - count : 몇번째 행동인지, type : 행동의 타입, minValue : 최소 전력
	private static int excute(int count,int type, int minValue){

		int returnValue = -1;
		//1번 행동 - 포트에 꽂는 행동.
		if (type == 1){

			//해당 포트에 꽂혀있는 것이 없으면 그대로 꽂기
			if (portStatus[minValue] == null){
				portStatus[minValue] = new Node(minValue, count);
				ts.remove(minValue);
				returnValue = minValue;

			}
			//해당 포트에 꽂혀있다면 다음 꽂을 위치 찾기.
			else{

				Integer portValue = ts.ceiling(minValue);

				//null이 아니면 꽂기.
				if (portValue != null){
					portStatus[portValue] = new Node(minValue,count);//포트에 꽂기.
					ts.remove(portValue);
					returnValue = portValue;
				}
			}

			return returnValue;
		}
		//2번 행동 - 꽂힌 것을 뽑는 행동.
		else {

			//꽂혀있지 않으면 -1을 반환
			if (portStatus[minValue] == null) return -1;

			//해당 위치에 꽂혀있다면, pq에 넣고 몇번째에서 꽂힌 값인지 반환.
			returnValue = portStatus[minValue].activeCount; //반환할 값
			ts.add(minValue); // 충전기를 뺐기 때문에 다음 탐색시에 사용하도록 추가.
			portStatus[minValue] = null; //빈 포트로 표기.

			return returnValue;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		portStatus = new Node[N + 1];

		ts = new TreeSet<>();

		for(int i = 1; i <= N; i++){
			ts.add(i);
		}

		StringBuilder result = new StringBuilder();
		for (int i = 1; i <= Q; i++){
			st = new StringTokenizer(br.readLine());

			result.append(excute(
				i,
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			)).append(" ");

			if(i == Q) continue;

			result.append("\n");

		}

		System.out.println(result);
	}
}
