package baekjoon.week27;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어(참고함.)
 * 수학
 * 두 수의 질량비가 주어진다.
 * 구하고자 하는 것은 전체 질량이므로, 우선은 상대적인 질량을 구해야 한다.
 * 즉, 1,2,3의 질량비를 1:2 = 3:2, 2:3 = 4:5 이런식으로 주어졌다고 하면,
 * 1:2의 질량비를 통한 상대 비율을 구하고, 2:3을 구하면서 1을 업데이트 하여,
 * 모든 수 대비, 각 수의 질량비율을 구해서 저장해둔다.
 *
 * 두 수를 비교하고, 최대공배수를 구해서 곱할때는 연결된 모든 질량에 최대공약수를 구해야한다.
 * 결국 비율은 기약분수로 나타내야 하기때문이다.
 * 예를 들면 1:2 = 3:2, 2:3 = 4:5 => 최종적인 비율이 12, 8, 10 이와 같이 나왔다면,
 * 이를 기약분수로 만들기 위해 최대공약수인 2로 나누게 되면, 6,4,5이 되고 이는 곧 전체 대비 각 수의 질량 비중으로 해당 값을 반환하면 된다.
 * (추가)
 * 연결관계를 다 연결한 후에 하려고 했는데, 그렇게 되면 p,q 값을 리스트에 저장해둬야 함.
 * 입력받는대로 계산하는게 나음,
 * 어차피 totalRatios에 누적이 되어있기 때문에 이전에 반영이 안된 값은 한번에 반영할 수 있음.
 *
 */

public class BOJ1033_칵테일 {

	private static int N;//질량 수.
	private static List<Integer>[] graph;//연관된 질량을 업데이트 하기 위해서 트리구조로 구성함.
	private static int[] totalRatios;//전체 질량 대비, 각 질량별 비율 저장.
	private static boolean[] visited;//방문처리 => 이미 처리된 질량을 다시 계산하지 않기 위해(양방향 그래프로 표현하면 자기 자신을 다시 업데이트 할 수도 있음.)


	//gcd(최대공약수 구하기.)
	private static int gcd(int a, int b){

		int maxValue = Math.max(a,b); // 큰수
		int minValue = Math.min(a,b); // 작은수.

		int temp = maxValue % minValue;

		//두수를 나눈 나머지가 0이면, b가 a의 공약수임,
		if(temp == 0) return minValue;

		//나머지가 0이 아니라면 재귀호출
		return gcd(minValue, temp);

	}

	//비율 정보 구하기.
	private static void calculate(int a, int b, int p, int q){

		visited = new boolean[N];
		int ratioA = totalRatios[a];
		int ratioB = totalRatios[b];

		//a의 비율을 구하기 위해서 b * p(a의 비율)을 tempA에 곱해준다.
		updateRatio(a, ratioB * p);
		//b의 비율을 구하기 위해서 a * p(a의 비율)을 tempB에 곱해준다.
		updateRatio(b , ratioA * q);

		graph[a].add(b);
		graph[b].add(a);
	}

	//재귀호출하면서 연관되는 모든 비율 업데이트. - target은 업데이트 할 대상, num은 곱해줘야 할 비율.
	private static void updateRatio(int target, int num){

		totalRatios[target] *= num;
		visited[target] = true;

		for(int node : graph[target]){

			if(visited[node]) continue;

			updateRatio(node, num);
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		graph = new List[N];
		totalRatios = new int[N];
		for(int i = 0; i < N; i++){
			graph[i] = new ArrayList<>();
			totalRatios[i] = 1;
		}

		for(int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());

			// 6/4 같은 경우, 기약분수로 만들어서 처리하기 위함.
			int gcdValue = gcd(p,q);
			calculate(a, b, p / gcdValue, q / gcdValue);
		}

		// 기약분수로 만들어서 질량 출력을 위해, 최대공약수를 구해야 함.
		int gcd = gcd(totalRatios[0], totalRatios[1]);

		for(int i = 1; i < N; i++){
			gcd = gcd(gcd, totalRatios[i]);

		}

		//구한 최대공약수로 모든 값을 나누고 출력
		StringBuilder result = new StringBuilder();

		for(int i = 0; i < N; i++){
			result.append(totalRatios[i] / gcd);

			if(i == N - 1) continue;
			result.append(" ");
		}

		System.out.println(result);

	}
}
