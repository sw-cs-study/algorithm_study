package programmers.week45;

/**
 * 아이디어
 * 이분탐색
 * 구하고자 하는 숙련도를 이분탐색을 이용해서 구하고,
 * 해당 숙련도로 퍼즐을 풀었을때, 제한시간안에 들어오는 지 확인하는 식으로 구한다.
 * diffs의 길이를 M, 나올수 있는 숙련도를 N으로 하면, 시간복잡도는 약 M * logN => 최악의 케이스를 산정하면 6*10^6
 */
public class Prog_퍼즐게임챌린지 {


	//주어진 난이도로 문제를 풀었을떄 제한시간안에 들어오는지 확인.
	private static boolean limitCheck(int targetLevel, int[] diffs, int[] times, long limit){


		long totalTime = 0;

		for(int i = 0; i < diffs.length; i++){

			int currentDiff = diffs[i];
			int currentTime = times[i];

			//(난이도) <= (숙련도)
			if(currentDiff <= targetLevel){

				//해당 케이스는 그냥 바로 문제 풀 수 있음.
				totalTime += currentTime;
			}

			//(난이도) > (숙련도)
			else{
				//틀릴때마다 (이전시간) + (현재 시간)이 걸림.
				totalTime += (long)(times[i - 1] + currentTime) * (long)(currentDiff - targetLevel) + (long)currentTime;
			}
		}

		return totalTime <= limit;
	}

	//이분탐색
	private static int binarySearch(int start, int end, int[] diffs, int[] times, long limit){

		int result = -1;
		int mid = 0;
		while(start <= end){
			mid = (start + end) / 2;

			//시간안에 들어온다면, 저장하고 숙련도를 더 줄여봄.
			if(limitCheck(mid, diffs, times, limit)){
				result = mid;
				end = mid - 1;
			}
			//시간안에 못들어오면 숙련도를 더 늘려봐야 함.
			else{
				start = mid + 1;
			}
		}

		return result;
	}



	public int solution(int[] diffs, int[] times, long limit) {

		return binarySearch(1, 100000, diffs, times, limit);
	}


	public static void main(String[] args){

		int[] diffs1 = {1, 5, 3};
		int[] times1 = {2, 4, 7};
		long limit1 = 30L;

		int[] diffs2 = {1, 4, 4, 2};
		int[] times2 = {6, 3, 8, 2};
		long limit2 = 59L;

		int[] diffs3 = {1, 328, 467, 209, 54};
		int[] times3 = {2, 7, 1, 4, 3};
		long limit3 = 1723L;

		int[] diffs4 = {1, 99999, 100000, 99995};
		int[] times4 = {9999, 9001, 9999, 9001};
		long limit4 = 3456789012L;


		Prog_퍼즐게임챌린지 p = new Prog_퍼즐게임챌린지();

		System.out.println(p.solution(diffs1, times1, limit1));
		System.out.println(p.solution(diffs2, times2, limit2));
		System.out.println(p.solution(diffs3, times3, limit3));
		System.out.println(p.solution(diffs4, times4, limit4));


	}
}
