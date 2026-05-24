package baekjoon.week43;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 분리집합 + 해시.
 */
public class BOJ16402_제국 {

	private static int N;//왕국의 수.

	private static int M;//전쟁결과 수.

	private static Map<String, Integer> nodeMap;//각 노드의 번호를 저장할 맵
	private static Map<Integer, String> numMap; //번호별 노드 이름 저장 - 최종 결과 출력시,

	private static int[] parents;//노드의 부모를 저장(번호를 저장함.)


	//경로 압축으로 최대 배열 구하기.
	private static int findParent(int node){

		if(parents[node] == node) return node;

		return parents[node] = findParent(parents[node]);
	}

	//두 노드 합치기 - node1쪽으로 합침.
	private static void union(int node1, int node2){

		int parent1 = findParent(node1);
		int parent2 = findParent(node2);

		parents[parent2] = parent1;


	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		nodeMap = new HashMap<>();
		numMap = new HashMap<>();
		parents = new int[N + 1];
		for(int i = 1; i <= N; i++){
			parents[i] = i;
		}

		for(int i = 0; i < N; i++){

			String name = br.readLine();

			nodeMap.put(name, i + 1);
			numMap.put(i + 1, name);

		}
		for(int i = 0; i < M; i++){

			String[] temp = br.readLine().split(",");
			String name1 = temp[0];
			String name2 = temp[1];

			//1이면 name1이, 2면 name2가 이김.
			int w = Integer.parseInt(temp[2]);

			if(w == 1){
				//name1이 2에 속국이라면,
				if(findParent(nodeMap.get(name1)) == nodeMap.get(name2)){

					//name1이 종주국, 즉 부모를 자기 자신으로 넣어야 함.
					parents[nodeMap.get(name1)] = nodeMap.get(name1);
					//부모가 name2인 모든 노드가 전부 name1의 속국이 되어야 함.
					for(int j = 1; j <= N; j++){

						//부모가 name2가 아니면 패스.
						if(findParent(j) != nodeMap.get(name2)) continue;
						union(nodeMap.get(name1), j);
					}
				}
				//속해있지 않으면 그냥 합치면 됨.
				else{
					union(nodeMap.get(name1), nodeMap.get(name2));
				}
			}
			else {
				//name2이 1에 속국이라면,
				if(findParent(nodeMap.get(name2)) == nodeMap.get(name1)){

					//name2이 종주국, 즉 부모를 자기 자신으로 넣어야 함.
					parents[nodeMap.get(name2)] = nodeMap.get(name2);
					//부모가 name2인 모든 노드가 전부 name1의 속국이 되어야 함.
					for(int j = 1; j <= N; j++){

						//부모가 name1가 아니면 패스.
						if(findParent(j) != nodeMap.get(name1)) continue;
						union(nodeMap.get(name2), j);
					}
				}
				//속해있지 않으면 그냥 합치면 됨.
				else{
					union(nodeMap.get(name2), nodeMap.get(name1));
				}
			}
		}


		//최종적으로 부모 배열을 돌면서 자기 자신인 것만 추출.
		List<String> tempList = new ArrayList<>();
		StringBuilder result = new StringBuilder();
		for(int i = 1; i <= N; i++){
			if(findParent(i) != i) continue;

			tempList.add(numMap.get(i));
		}

		tempList.sort(Comparator.naturalOrder());

		for(String temp : tempList){
			result.append(temp).append("\n");
		}

		System.out.println(tempList.size());
		System.out.println(result);

	}
}
