package baekjoon.week34;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 위상정렬
 */
public class BOJ21276_계보복원가호석 {


	private static int N;//사람수.
	private static int M;
	private static String[] manInfo;//사람 정보가 담긴 배열 -> 사전순 나열을 위해 정렬 필요.(자식을 사전순으로 뽑을때 필요.)
	private static Map<String, Integer> mapping;//사람정보와 인덱스를 매핑한 정보 - 1;
	private static StringBuilder result;
	private static int ancestor;

	//진입차수.
	private static int[] inDegree;

	//그래프.
	private static List<Integer>[] graph;

	//자식 정보.
	private static List<Integer>[] childInfo;

	//위상정렬
	private static void topologySort(){

		Queue<Integer> needVisited = new ArrayDeque<>();
		childInfo = new List[N];

		for(int i = 0; i < N; i++){

			childInfo[i] = new ArrayList<>();

			if(inDegree[i] != 0) continue;

			needVisited.add(i);
		}

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(int nextNode : graph[currentNode]){

				inDegree[nextNode]--;

				//진입차수가 0이 아니면 패스.
				if(inDegree[nextNode] != 0) continue;

				needVisited.add(nextNode);
				//자식정보 추가.
				childInfo[currentNode].add(nextNode);
			}
		}


	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		manInfo = new String[N];
		mapping = new HashMap<>();

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			manInfo[i] = st.nextToken();
		}

		Arrays.sort(manInfo);
		for(int i = 0; i < N; i++){
			mapping.put(manInfo[i], i);
		}

		inDegree = new int[N];
		graph = new List[N];
		for(int i = 0; i < N; i++){
			graph[i] = new ArrayList<>();
		}

		M = Integer.parseInt(br.readLine());
		for(int i = 1; i <= M; i++){
			st = new StringTokenizer(br.readLine());

			int a = mapping.get(st.nextToken());
			int b = mapping.get(st.nextToken());

			inDegree[a]++;
			graph[b].add(a);
		}

		result = new StringBuilder();
		ancestor = 0;

		StringBuilder ancestorString = new StringBuilder();
		for(int i = 0; i < N; i++){
			if(inDegree[i] != 0) continue;

			ancestor++;

			ancestorString.append(manInfo[i]).append(" ");
		}

		topologySort();

		result.append(ancestor).append("\n").append(ancestorString.toString()).append("\n");

		//자식 정보 추가.
		for(int i = 0; i < N; i++){
			//본인 자식수 자식이름 나열
			result.append(manInfo[i]).append(" ").append(childInfo[i].size()).append(" ");

			StringBuilder childStr = new StringBuilder();
			childInfo[i].sort(Comparator.naturalOrder());
			for(int childNum : childInfo[i]){
				childStr.append(manInfo[childNum]).append(" ");
			}

			result.append(childStr.toString()).append("\n");
		}


		System.out.println(result.toString());
	}
}
