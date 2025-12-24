package baekjoon.week36;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 아이디어
 *
 * 위상정렬
 * (주의)
 * 서로 다른 레시피에서 같은 물약이 나오는 케이스를 조심해야 한다.
 * 선행되는 물약수만큼 진입차수를 늘려버리면 만들수 없게 된다.
 */
public class BOJ20119_클레어와물약 {


	private static int N; //물약 종류수
	private static int M; //레시피 개수.
	private static int[] inDegree; //진입 차수.
	private static int[] drugInfo;//약 정보.
	private static List<Integer>[] graph;//그래프 - 물약 선행 구조.
	private static List<Integer> results;//만들수 있는 물약 리스트
	private static Set<Integer> saveDrug;//보유 물약
	private static Queue<Integer> needVisited;

	//위상정렬
	private static void topologySort(){

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(int nextNode : graph[currentNode]){

				inDegree[nextNode]--;

				int nextDrug = drugInfo[nextNode];

				//진입 차수가 0이 아니거나, 이미 보유하고 있는 물약이면 탐색할 필요 없음.
				if(inDegree[nextNode] != 0 || saveDrug.contains(nextDrug)) continue;


				results.add(nextDrug);
				saveDrug.add(nextDrug);
				needVisited.add(nextDrug);
			}
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		inDegree = new int[M + 1];
		drugInfo = new int[M + 1];
		saveDrug = new HashSet<>();
		needVisited = new ArrayDeque<>();
		results = new ArrayList<>();

		graph = new List[N + 1];
		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 1; i <= M; i++){
			st = new StringTokenizer(br.readLine());

			int k = Integer.parseInt(st.nextToken());

			//물약 레시피.
			List<Integer> temp = new ArrayList<>();
			for(int j = 0; j < k; j++){
				int x = Integer.parseInt(st.nextToken());
				temp.add(x);
			}
			//만들어지는 물약.
			int r = Integer.parseInt(st.nextToken());

			for(int drug : temp){
				inDegree[i]++;
				drugInfo[i] = r; //레시피 인덱스에 물약정보를 저장해서, 서로 다른 레시피가 같은 물약을 만들어내는 경우를 방지.

				graph[drug].add(i);
			}
		}

		int L = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < L; i++){
			int drug = Integer.parseInt(st.nextToken());
			saveDrug.add(drug);
			needVisited.add(drug);
			results.add(drug);
		}


		topologySort();


		results.sort(Comparator.naturalOrder());
		StringBuilder sb = new StringBuilder();

		for(int result : results){
			sb.append(result).append(" ");
		}

		System.out.println(results.size());
		System.out.println(sb.toString());
	}
}
