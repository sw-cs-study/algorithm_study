package programmers.week47;

/**
 * 아이디어
 * 완탐 + 그리디
 *
 * 분배노드는 2또는 3이다
 * 그러면 2를 몇층까지 분배하고, 3을 몇층까지 분배할지 모든 경우를 조합해서 확인하면 된다.
 * 이때 2와 3의 순서는 볼필요가 없다
 * 분배도(2^p * 3^p)는 2와 3 순서에는 상관없기 때문에 패스,
 * 예산의 경우를 봐야 하는데,
 * 2를 먼저쓰는게 예산(분배노드 - dist_limit)을 덜 쓰게 된다.
 * 루트 - 자식1 에서 시작한다고 가정해보자,
 * 2를 먼저쓰고 3을 쓰는 경우와, 3을 먼저 쓰고 2를 쓰는 경우를 생각해보자.
 * 전자는 분배노드를 -1 -2 총 3개 즉 예산을 총 3을 쓴다.
 * 후자는 -1 -3 총 4개, 즉 예산을 4를 쓴다.
 * 하지만 리프노드는 6개로 동일하다.
 * 즉 2를 먼저 쓰는게 이득이라는 소리다.
 *
 * (주의할점)
 * 2와 3의 개수조합을 구할때,
 * 2^p * 3^q 범위를 넘는지 체크하는데, 이 수가 10^9이라서,
 * 만약 10억에 3만 곱해도 int 범위를 넘어가서 잘못 계산될 수 있다,
 * 따라서 2^p * 3^q 범위체크할 변수는 long으로 잡아야 한다.
 *
 */
public class Prog_리프노드수최대화 {

	public int solution(int dist_limit, int split_limit) {
		int answer = 1;


		// i : 2를 몇층 쓸지, j : 3을 몇층 쓸지

		long i2 = 1;
		for(int i = 0; i2 <= split_limit; i++){
			long j2 = 1;
			for(int j = 0; i2 * j2 <= split_limit; j++){


				int budget = dist_limit;
				int currentNodeCount = 1;//현재 노드 수,
				int leafCount = 1;

				//i+j 만큼 반복,
				for(int a = 0; a < i + j; a++){

					//2를 먼저 i개만큼 분배 하고 3을 j개 만큼 분배,
					int k = a < i ? 2 : 3;

					//현재 레벨의 노드 수가 예산보다 작거나 같으면, 전부 분배노드로 만들수 있음.
					if(budget >= currentNodeCount){
						budget -= currentNodeCount;
						leafCount += currentNodeCount * k - currentNodeCount;
						currentNodeCount = leafCount;
					}
					//현재 레벨의 노드수가 예산보다 작으면, 다 배치가 불가능 함.
					else {
						leafCount += budget * k - budget;
						break; //예산부족으로 더 탐색 불가.
					}
				}
				//배치 다했으면, 리프노드 수의 최대 값 저장.
				answer = Math.max(answer, leafCount);

				j2 *= 3;
			}
			i2 *= 2;

		}


		return answer;
	}

	public static void main(String[] args){
		Prog_리프노드수최대화 s = new Prog_리프노드수최대화();

		int dist_limit1 = 3;
		int split_limit1 = 6;
		System.out.println(s.solution(dist_limit1, split_limit1));

		int dist_limit2 = 0;
		int split_limit2 = 10;
		System.out.println(s.solution(dist_limit2, split_limit2));

		int dist_limit3 = 3;
		int split_limit3 = 100;
		System.out.println(s.solution(dist_limit3, split_limit3));

		int dist_limit4 = 5;
		int split_limit4 = 16;
		System.out.println(s.solution(dist_limit4, split_limit4));


	}
}
