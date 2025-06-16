package week19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

// 정답은 항상 2^31 보다 작다. => int
/**
 * 양수를 저장하는 우선순위 큐, 음수를 저장하는 우선순위 큐
 * 1) 양수를 저장한 우선순위 큐의 크기가 2 이상일 때
 * - 2개 수를 꺼내어 곱한다. 단, 2개 중 하나의 수가 0 또는 1 이라면 곱하지 않는다.
 * - 0인 경우엔 0은 우선순위 큐에 두고 다른 수는 정답에 더한다.
 * - 1인 경우엔 정답에 1과 1이 아닌 수를 더한다.
 * 2) 음수를 저장하는 우선순위 큐의 크기가 2 이상일 떄
 * - 2개의 수를 꺼내어 곱한다.
 * - 2개 미만인 경우 양수를 저장한 우선순위 큐에 0이 있는지 확인한 후 있다면 곱한다.
 * - 없다면 정답에 더한다.
 */
public class BOJ1744 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        PriorityQueue<Integer> plus = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> minus = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(bf.readLine());
            if (num >= 0) {
                plus.add(num);
            } else {
                minus.add(num);
            }
        }

        int answer = 0;

        // 양수 2개 곱하기
        while (plus.size() >= 2) {
            int n1 = plus.poll();
            int n2 = plus.poll();

            // 둘 다 0인 경우 탈출
            if (n1 == 0 && n2 == 0) {
                break;
            }

            if (n1 == 1 && n2 != 0) {   // 한쪽이 1인 경우, 두 수를 더하는 것이 더 큼
                answer += (n1 + n2);
            } else if (n2 == 1 && n1 != 0) {    // 한쪽이 1인 경우, 두 수를 더하는 것이 더 큼
                answer += (n1 + n2);
            } else if (n1 == 0 && n2 != 0) {    // 한쪽이 0인 경우, 0이 아닌 수를 더함
                answer += n2;
                plus.add(n1);
            } else if (n2 == 0 && n1 != 0) {    // 한쪽이 0인 경우, 0이 아닌 수를 더함
                answer += n1;
                plus.add(n2);
            } else {    // 두 수 모두 0, 1이 아닌 경우
                answer += (n1 * n2);
            }
        }

        // 음수 2개 곱하기 => 양수
        while (minus.size() >= 2) {
            int n1 = minus.poll();
            int n2 = minus.poll();

            answer += (n1 * n2);
        }

        // 남아있는 음수와 0을 곱하는 과정
        while (!plus.isEmpty() && !minus.isEmpty()) {
            if (plus.peek() != 0) {
                break;
            }

            plus.poll();
            minus.poll();
        }

        // 남아있는 양수를 더하는 과정
        while (!plus.isEmpty()) {
            answer += plus.poll();
        }

        // 남아있는 음수를 더하는 과정
        while (!minus.isEmpty()) {
            answer += minus.poll();
        }

        System.out.println(answer);
    }
}
