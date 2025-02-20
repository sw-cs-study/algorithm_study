package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BOJ2374 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        long answer = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(bf.readLine());

            if (!stack.isEmpty() && stack.peek() < num) {
                // ex) 스택: [5, 2, 1], num: 3
                // 2, 1을 3으로 만드는 연산
                answer += (num - stack.pop());

                // 스택 [5]로 만들기
                // [5, 2, 1] 1 pop() -> 1을 3으로 만드는 연산
                // [5, 2] 2 pop() -> 2를 3으로 만드는 연산 (1을 3으로 만드는 연산에 포함됨)
                while (!stack.isEmpty() && stack.peek() < num) {
                    stack.pop();
                }
            }

            stack.push(num);
        }

        // ex) [5, 2, 1]
        while (stack.size() != 1) {
            int num = stack.pop();
            answer += (stack.peek() - num);
        }

        System.out.println(answer);
    }
}