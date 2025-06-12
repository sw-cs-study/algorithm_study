/**
 * 오름차순 정렬하고 오른쪽(양수)는 0이 아닌 그 다음 큰 양수와 곱하기, 왼쪽(음수)는 가장 작은 수를 그 다음으로 작은 음수와 곱함, 혼자 남으면 최대한 0이랑 곱함
 * 고려할 점
 * 1. 양수끼리 곱할 때 1을 곱하는건 피해야함
 * 2. 양수끼리 더할 때 before 값을 경우에 맞게 처리해야함 -> 이 경우엔 idx가 middle에 도달했을 때 남아잇는 before 값을 처리안했었음
 */

package week19;

import java.io.*;
import java.util.*;

public class BOJ1744 {
    private static int n;
    private static int[] numbers;
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(numbers);
        // 양수가 시작되기 전의 인덱스값 찾기
        int middle = -1;
        for (int i = 0; i < n; i++) {
            if (numbers[i] > 0) break;
            middle = i;
        }
        int answer = 0;
        // 음수부터 0
        for (int i = 1; i <= middle; i+=2) {
            if (numbers[i] == 0) break; // 앞으로 쭉 0이라 더 계산할 필요가 없음
            answer += numbers[i-1] * numbers[i];
        }
        // 끝에 하나 남음
        if (middle % 2 == 0) {
            answer += numbers[middle];
        }
        // 양수
        int idx = n-1;
        int before = 0;
        while (true) {
            if (idx == middle) {
                answer += before;
                break;
            }
            if (numbers[idx] == 1) {
                answer += before;
                answer += (idx - middle);
                break;
            } else if (before > 0) {
                answer += before * numbers[idx];
                before = 0;
            } else {
                before = numbers[idx];
            }
            idx--;
        }
        System.out.println(answer);
    }
}
