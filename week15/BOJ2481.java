/*
 * 해밍 경로
 * 각 수를 10진수로 변환 시켜 차이가 2^p 꼴이면 해밍 경로의 조건에 맞음
 * 1번 이진수 코드에서 다른 점으로 이동하는 해밍 경로를 찾아야함 -> bfs -> 특정 노드에서 이동할 때 해밍 거리가 1인 곳만 체크 (최대 30)
 * -> 각 자리수를 하나씩 바꿔서 이동, 최대 30가지 나옴 -> map 으로 100_000개 저장해서 바로 이동하기 -> 시간 복잡도 = 30 * 100_000
 * 경로는 트리로 저장해서 출력하자 => LinkedList로 -> 근데 저장하는 것보단 바로 저장해서 출력하는 게 좋을듯?
 * 
 * (메모리초과 이유)
 * 마지막에 지나간 루트 구할 때, 재귀로 만듬, 재귀가 최대 100_000개 진행되는데 여기서 메모리 초과인듯? -> 재귀 없이 for문으로 수정하니깐 통과함
 */

package week15;

import java.io.*;
import java.util.*;

public class BOJ2481 {
    private static int n, k, m, start;
    private static HashMap<Integer, Integer> map = new HashMap<>();
    private static int[] parent; // 100_000 * 4byte


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            int number = Integer.parseInt(br.readLine(), 2);
            map.put(number, i+1);
            if (i == 0) start = number;
        }

        parent = new int[n + 1];
        // bfs
        bfs(start);
        // 목적지에 따른 경로 정보
        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            String arrival = br.readLine();
            StringBuilder answer = new StringBuilder(arrival);
            int p = parent[Integer.parseInt(arrival)];
            while (true) {
                if (p == 1) {
                    answer.insert(0, " ").insert(0, "1");
                    break;
                }
                if (p == 0) {
                    answer = new StringBuilder("-1");
                    break;
                }
                answer.insert(0, " ").insert(0, Integer.toString(p));
                p = parent[p];
            }
            System.out.println(answer);
        }


    }


    private static void bfs(int start) {
        LinkedList<Integer> queue = new LinkedList();

        queue.offer(start);
        
        while (!queue.isEmpty()) {
            int code = queue.poll();
            int index = map.get(code);
            // 해밍 거리 1인 수를 구하고 map에 저장되었는지 체크하고 진행
            for (int i = 0; i < k; i++) {
                int nextCode = code ^ (1 << i);
                if (!map.containsKey(nextCode)) continue;
                int nextIndex = map.get(nextCode);
                if (parent[nextIndex] != 0) continue;
                queue.offer(nextCode);
                // 경로 저장
                parent[nextIndex] = index;
            }
        }

    }

    
}
