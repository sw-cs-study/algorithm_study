package baekjoon.week30;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 노가다
 */
public class BOJ17114_하이퍼토마토 {

	private final static int[] dM = {-1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dN = {0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dO = {0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dP = {0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dQ = {0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dR = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0};
	private final static int[] dT = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0};
	private final static int[] dU = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0};
	private final static int[] dV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0};
	private final static int[] dW = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1};



	private static int m, n, o, p, q, r, s, t, u, v, w;
	private static int[][][][][][][][][][][] graph;


	//이동 가능한지 체크 - 격자판을 벗어나면 안되고, 안익은 토마토로 가야 함.
	private static boolean visitedCheck(int[] nextArray){
		return nextArray[0] >= 0 && nextArray[0] < m &&
			nextArray[1] >= 0 && nextArray[1] < n &&
			nextArray[2] >= 0 && nextArray[2] < o &&
			nextArray[3] >= 0 && nextArray[3] < p &&
			nextArray[4] >= 0 && nextArray[4] < q &&
			nextArray[5] >= 0 && nextArray[5] < r &&
			nextArray[6] >= 0 && nextArray[6] < s &&
			nextArray[7] >= 0 && nextArray[7] < t &&
			nextArray[8] >= 0 && nextArray[8] < u &&
			nextArray[9] >= 0 && nextArray[9] < v &&
			nextArray[10] >= 0 && nextArray[10] < w &&
			graph[nextArray[0]][nextArray[1]][nextArray[2]][nextArray[3]][nextArray[4]][nextArray[5]][nextArray[6]][nextArray[7]][nextArray[8]][nextArray[9]][nextArray[10]] == 0;
	}


	//bfs
	private static int bfs(Queue<int[]> needVisited){

		int result = 0;

		while(!needVisited.isEmpty()){

			int[] currentNode = needVisited.poll();

			result = Math.max(result, currentNode[11]);

			for(int i = 0; i < 22; i++){
				int nextM = currentNode[0] + dM[i];
				int nextN = currentNode[1] + dN[i];
				int nextO = currentNode[2] + dO[i];
				int nextP = currentNode[3] + dP[i];
				int nextQ = currentNode[4] + dQ[i];
				int nextR = currentNode[5] + dR[i];
				int nextS = currentNode[6] + dS[i];
				int nextT = currentNode[7] + dT[i];
				int nextU = currentNode[8] + dU[i];
				int nextV = currentNode[9] + dV[i];
				int nextW = currentNode[10] + dW[i];


				//방문이 불가능하면 패스.
				if(!visitedCheck(new int[]{nextM, nextN, nextO, nextP, nextQ, nextR, nextS, nextT, nextU, nextV, nextW})) continue;


				needVisited.add(new int[]{
					nextM, nextN, nextO, nextP, nextQ, nextR, nextS, nextT, nextU, nextV, nextW, currentNode[11] + 1
				});

				graph[nextM][nextN][nextO][nextP][nextQ][nextR][nextS][nextT][nextU][nextV][nextW] = 1;

			}
		}

		return result;

	}

	// true 면 모든 토마토가 익음, false면 안익음.
	private static boolean check(){

		//m, n, o, p, q, r, s, t, u, v, w;
		for(int tempW = 0; tempW< w; tempW++){
			for(int tempV = 0; tempV < v; tempV++){
				for(int tempU = 0; tempU < u; tempU++){
					for(int tempT = 0; tempT < t; tempT++){
						for(int tempS = 0; tempS < s; tempS++){
							for(int tempR = 0; tempR < r; tempR++){
								for(int tempQ = 0; tempQ < q; tempQ++){
									for(int tempP = 0; tempP < p; tempP++){
										for(int tempO = 0; tempO < o; tempO++){
											for(int tempN = 0; tempN < n; tempN++){
												for(int tempM = 0; tempM < m; tempM++){
													//0이면 안익은 토마토가 남아 있음.
													if(graph[tempM][tempN][tempO][tempP][tempQ][tempR][tempS][tempT][tempU][tempV][tempW] == 0) return false;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}




	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());


		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		o = Integer.parseInt(st.nextToken());
		p = Integer.parseInt(st.nextToken());
		q = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		u = Integer.parseInt(st.nextToken());
		v = Integer.parseInt(st.nextToken());
		w = Integer.parseInt(st.nextToken());


		graph = new int[m][n][o][p][q][r][s][t][u][v][w];

		Queue<int[]> needVisited = new ArrayDeque<>();


		int zeroCount = 0; //시작시점에 모든 토마토가 익어있는지 확인용.

		//m, n, o, p, q, r, s, t, u, v, w;
		for(int tempW = 0; tempW< w; tempW++){
			for(int tempV = 0; tempV < v; tempV++){
				for(int tempU = 0; tempU < u; tempU++){
					for(int tempT = 0; tempT < t; tempT++){
						for(int tempS = 0; tempS < s; tempS++){
							for(int tempR = 0; tempR < r; tempR++){
								for(int tempQ = 0; tempQ < q; tempQ++){
									for(int tempP = 0; tempP < p; tempP++){
										for(int tempO = 0; tempO < o; tempO++){
											for(int tempN = 0; tempN < n; tempN++){

												st = new StringTokenizer(br.readLine());

												for(int tempM = 0; tempM < m; tempM++){

													int tempValue = Integer.parseInt(st.nextToken());

													if(tempValue == 0) zeroCount++;

													graph[tempM][tempN][tempO][tempP][tempQ][tempR][tempS][tempT][tempU][tempV][tempW] = tempValue;

													if(tempValue == 1){
														needVisited.add(new int[]{
															tempM, tempN, tempO, tempP, tempQ, tempR, tempS, tempT, tempU, tempV, tempW, 0
														});
													}

												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}


		int time = 0;

		//시작시점에 전부 익어있지 않으면,bfs 탐색시작.
		if(zeroCount > 0){

			time = bfs(needVisited);


			//bfs 탐색후, 모든 토마토가 익었으면, 결과값 저장, 아니면 -1 반환.
			if(!check()) time = -1;
		}

		System.out.println(time);

	}
}
