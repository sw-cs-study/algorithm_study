package week14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시간 제한 1초
 * 1 <= n <= 1,000,000
 * 1 <= k <= 10,000
 * 1 <= d <= 1,000,000,000
 *
 * => 시간복잡도 O(N) or O(NlogN)
 * 1. imos
 * - 간격이 있는 경우에는 어떻게 할 수 있는지 모르겠음
 *
 * 2. 이분 탐색
 * - 필요한 도토리 수를 이분 탐색을 통해 구한다. => log(1,000,000,000)
 * - 최대 도토리 가능 수 => 최대 상자(1,000,000) * 최대 규칙 수(10,000)
 * => 규칙이 10,000개 들어오고 모든 규칙이 1 1,000,000 1(1번 상자부터 1,000,000상자까지 1개 간격으로)
 * => 주어지는 도토리 최대 수보다 크다.
 *
 * - 이분 탐색을 시작하고 초기 비교값이 되는 500,000,000만큼 도토리가 필요해 탐색하면 5초다...
 *
 * 3번 생각 후 다시 돌아옴
 * => 탐색을 하나씩 X => (B - A) / C + 1
 * => 마지막 도토리가 들어가는 상자의 번호를 알기 어려움.
 * => 이분탐색을 상자로 진행
 * - 선택된 상자까지 도토리 담아본 결과를 result
 * if result < d: left = mid
 * if result >= d: right = mid
 *
 * => let's go
 *
 * 3. 분할정복
 * - 나눌만한 기준을 못찾음
 *
 *
 * 틀린 이유
 * - count 타입 잘못 설정...
 */
public class BOJ15732 {

    static int n;   // 상자의 개수
    static int k;   // 규칙의 개수
    static int d;   // 도토리의 개수

    static int[] A; // A[i]: i번째 규칙의 시작 상자
    static int[] B; // B[i]: i번째 규칙의 끝 상자
    static int[] C; // C[i]: i번째 규칙의 간격

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        A = new int[k];
        B = new int[k];
        C = new int[k];

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(bf.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(bs());
    }

    private static int bs() {
        int left = 0;
        int right = n;

        while (left + 1 < right) {
            int mid = (left + right) >> 1;
            if (check(mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return right;
    }

    private static boolean check(int box) {
        long count = 0;
        for (int i = 0; i < k; i++) {
            if (box < A[i]) {   // 셀 수 없는 경우
                continue;
            }

            count += (Math.min(box, B[i]) - A[i]) / C[i] + 1;
        }

        return count < d;
    }
}
