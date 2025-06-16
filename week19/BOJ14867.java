/**
 * F, E, M 3가지 연산을 2개의 물통에 적용한다면 각 연산수가 6개다 => 여기서 6^x(작업수) < 2*10^8, 6^4 = 1296 이므로 작업수가 8회에서 9회만 넘어가도 시간초과임
 * 그래서 memoization해야함 [현재쵯수][a 물통 상태][b 물통 상태]로 저장함 => a 물통 상태 * b 물통 상태만 해도 10^10이라 터짐 => 메모리도 터짐 
 * 일단 bfs ㄱㄱ 
 * 나올 수 있는 경우는 (0, 0), (a, 0), (0, b), (a', 0), (0, b') 각 연산별로 나오는 경우의 수를 계산해보기
 * 방문은 set 자료형으로, string보단 long 으로
 * 
 */


package week19;

import java.io.*;
import java.util.*;

public class BOJ14867 {
    private static int volumeA, volumeB, targetA, targetB;
    private static HashSet<Long> visited;

    static class State {
        int A = 0;
        int B = 0;
        State (int a, int b) {
            this.A = a;
            this.B = b;
        }
    }

    private static long makeKey(int a, int b) {
        return ((long) a << 17) | b;
    }

    private static int bfs() {
        Queue<State> queue = new LinkedList<>();
        visited = new HashSet<>();
        queue.add(new State(0, 0));
        visited.add(makeKey(0, 0));
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                State cur = queue.poll();
                // answer
                if (cur.A == targetA && cur.B == targetB) {
                    return count;
                }
                // fill
                // a
                long key;
                key = makeKey(volumeA, cur.B);
                if (!visited.contains(key)) {
                    queue.add(new State(volumeA, cur.B));
                    visited.add(key);
                }
                // b
                key = makeKey(cur.A, volumeB);
                if (!visited.contains(key)) {
                    queue.add(new State(cur.A, volumeB));
                    visited.add(key);
                }
                // empty
                // a
                key = makeKey(0, cur.B);
                if (!visited.contains(key)) {
                    queue.add(new State(0, cur.B));
                    visited.add(key);
                }
                // b
                key = makeKey(cur.A, 0);
                if (!visited.contains(key)) {
                    queue.add(new State(cur.A, 0));
                    visited.add(key);
                }

                // move
                // a -> b
                int nextB = cur.B + cur.A;
                int nextA = Math.max(0, nextB - volumeB);
                nextB = Math.min(nextB, volumeB);
                key = makeKey(nextA, nextB);
                if (!visited.contains(key)) {
                    queue.add(new State(nextA, nextB));
                    visited.add(key);
                }
                // b <- a
                nextA = cur.A + cur.B;
                nextB = Math.max(0, nextA - volumeA);
                nextA = Math.min(nextA, volumeA);
                key = makeKey(nextA, nextB);
                if (!visited.contains(key)) {
                    queue.add(new State(nextA, nextB));
                    visited.add(key);
                }
                
            }
            count++;
        }
        return -1;
    }
    
    public static void main (String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        volumeA = Integer.parseInt(st.nextToken());
        volumeB = Integer.parseInt(st.nextToken());
        targetA = Integer.parseInt(st.nextToken());
        targetB = Integer.parseInt(st.nextToken());

        System.out.println(bfs());
        
    }  

    

    
}
