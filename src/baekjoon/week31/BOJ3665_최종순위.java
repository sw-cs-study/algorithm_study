package baekjoon.week31;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 위상정렬 응용
 *
 * 우선 주어진 경우를 가지고 우열을 가린 후, 진입 차수를 구해줘야 한다.
 * 또한 예외 케이스가 2개가 존재하는데 , 이를 걸러야 한다.
 * 1. 순서모순 => 진입차수가 0인것이 하나도 없다면, 사이클이 생긴다는 뜻으로 순서에 모순이 생긴다는 뜻이다.
 * 2. 우열x => 진입차수가 0인것이 여러개라면 서로 우열을 가릴 수가 없다.
 *
 * 우선 팀간 작년 정보를 토대로 진입차수를 구해주고, 바뀐 정보를 토대로 재 구성해준다.
 */
public class BOJ3665_최종순위 {


	private static int N;//팀 수
	private static int M;//등수가 바뀐 쌍의 수.
	private static boolean[][] edgeInfo;//팀간 우열 정보
	private static int[] inDegree;//진입차수

	//위상정렬
	private static String topologySort(){

		Queue<Integer> needVisited = new ArrayDeque<>();
		for(int i = 1; i <= N; i++){

			if(inDegree[i] != 0) continue;

			needVisited.add(i);
		}

		StringBuilder orderInfo = new StringBuilder();
		int count = 0;

		while(!needVisited.isEmpty()){

			//예외 케이스 처리.
			if(needVisited.size() > 1) return "?";

			int currentNode = needVisited.poll();

			orderInfo.append(currentNode).append(" ");
			count++;

			for(int i = 1; i <= N; i++){

				//자기 자신이거나,자신보다 순위가 높으면 패스.
				if(currentNode == i || !edgeInfo[currentNode][i]) continue;

				inDegree[i]--;
				if(inDegree[i] != 0) continue;
				needVisited.add(i);
			}


		}
		if(count != N) return "IMPOSSIBLE";

		return orderInfo.toString();
	}

	//바뀐 순서에 따라 재정렬 - 진입차수 변경 및 팀간 우열정보 수정.
	private static void reSort(int a, int b){

		//작년에 a팀이 b팀을 앞선 경우 - 우열정보 변경 및 진입차수를 바꿔줘야 함.
		if(edgeInfo[a][b]){
			edgeInfo[a][b] = false;
			edgeInfo[b][a] = true;
			inDegree[a]++;
			inDegree[b]--;
		}
		//작년에 b팀이 a팀을 앞선 경우.
		else{
			edgeInfo[a][b] = true;
			edgeInfo[b][a] = false;
			inDegree[a]--;
			inDegree[b]++;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int t = Integer.parseInt(br.readLine());
		StringBuilder result = new StringBuilder();

		for(int testCase = 0; testCase < t; testCase++){
			N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());

			//true면, 해당 팀보다 순위가 높음.
			edgeInfo = new boolean[N + 1][N + 1];
			inDegree = new int[N + 1];
			for(int i = 1; i <= N; i++){
				int currentTeam = Integer.parseInt(st.nextToken());
				inDegree[currentTeam] = i - 1;//자기 자신은 제외함.

				//순위에 따라 표기 - currentTeam을 기준으로 타 팀보다 앞서는 지 확인.
				for(int j = 1; j <= N; j++){

					//자기 자신이거나, currentTeam 보다 j팀이 앞서있다면 패스.
					if(j == currentTeam || edgeInfo[j][currentTeam]) continue;

					edgeInfo[currentTeam][j] = true;
				}
			}
			//등수가 바뀐 수
			M = Integer.parseInt(br.readLine());
			for(int i = 0; i < M; i++){
				st = new StringTokenizer(br.readLine());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				reSort(a,b); //바뀐 등수 정보를 보고 우열 재정렬
			}

			result.append(topologySort()).append("\n");
		}


		System.out.println(result);
	}
}
