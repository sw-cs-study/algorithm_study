package baekjoon.week32;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 참고함 : https://tech.kakao.com/posts/354
 *
 * 인접한 색이 다른 두 격자는 초기상태와 같은 상태라는 것을 알면 쉽게 해결이 된다.
 * 그외의 격자는 어떤 색이어도 된다.
 * 예를 들면 BW라는 상태가 있다고 가정해보자.
 * 해당 격자의 초기 상태는 BW라는 것을 증명해보면,
 * BB => WW 불가능
 * WW => BB 불가능
 * WB => WW or BB 불가능.
 * 즉 인접한 격자와 색이 다르다면, 해당 격자들은 해당 상태가 초기라고 봐야 한다.
 *
 * 따라서 반복문을 돌면서, 인접한 격자가 서로 다른 색을 가지는 격자의 수를 구하고,
 * 전체 개수에서 서로 다른 색을 가진 격자의 수를 뺀 개수에 *2를 한다.
 * (인접 격자가 같은 색을 가진다면, B,W 둘다 되기 떄문에 *2를 해준다.)
 */
public class BOJ15999_뒤집기 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private final static int MOD = 1_000_000_007;

	private static int N;
	private static int M;

	private static char[][] maps;

	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		maps = new char[N][M];

		for(int i = 0; i < N; i++){
			maps[i] = br.readLine().toCharArray();
		}


		int adjCount = 0;//인접격자와 색이 다른 격자의 수
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				for(int dir = 0; dir < 4; dir++){

					int nextX = i + dx[dir];
					int nextY = j + dy[dir];

					if(!check(nextX, nextY) || maps[i][j] == maps[nextX][nextY]) continue;

					adjCount++;
					break;
				}
			}
		}

		int result = 1;
		//2^(N*M - adjCount)해야하지만 MOD 연산을 해줘야 하기 떄문에 반복문 돌면서 2 곱할때마다 모드연산 처리함.
		for(int i = 0; i < N*M - adjCount; i++){
			result = (result * 2) % MOD;
		}

		System.out.println(result);
	}
}
