package baekjoon.week8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * dfs + dp
 */

public class BOJ21606_아침산책 {

	private static int N;//노드 수
	private static char[] inoutInfo; //실내, 실외 여부(1 : 실내, 0 : 실외)
	private static List<Integer>[] graph;//그래프
	private static int[] dp;//경로 수 저장 dp 배열

	private static int inCount;//만난 실내 개수.

	//dfs - 실외해서 dfs 탐색
	private static void dfs(int currentNode, int startNode){

		//실내이면 종료하고 방문처리.
		if(inoutInfo[currentNode] == '1'){
			inCount++;
			return;
		}

		for(int nextNode : graph[currentNode]){

			//방문했던 노드이거나 시작 노드이면 패스.
			if(dp[nextNode] != -1) continue;
			if(inoutInfo[nextNode] == '0') dp[nextNode] = 0;
			dfs(nextNode, startNode);
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());
		inoutInfo = new char[N];

		inoutInfo = br.readLine().toCharArray();

		graph = new List[N];
		for (int i = 0; i < N; i++){
			graph[i] = new ArrayList<>();
		}

		int result = 0;
		for (int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			//두 노드가 실내이고 인접하면 경로 2개 더하기.
			if (inoutInfo[a - 1] == '1' && inoutInfo[b - 1] == '1') result += 2;

			graph[a - 1].add(b - 1);
			graph[b - 1].add(a - 1);
		}

		dp = new int[N];
		Arrays.fill(dp, -1); //방문하지 않았으면 -1(실외노드 중에 실내노드로 갈수 없어 0이 나오는 경우도 있을수 있음.)


		for (int i = 0; i < N; i++){

			if (inoutInfo[i] == '1' || dp[i] != -1) continue;

			inCount = 0;
			dp[i] = 0;
			dfs(i, i);
			if(inCount < 2) continue;

			result +=  inCount * (inCount - 1);
		}

		System.out.println(result);
	}
}
