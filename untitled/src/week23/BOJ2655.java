package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ2655 {

    static int n;
    static Block[] blocks;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());    // 벽돌의 수

        blocks = new Block[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int bottom = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            blocks[i] = new Block(bottom, height, weight, i + 1);
        }

        // 밑면 넓이 내림차순
        Arrays.sort(blocks, (b1, b2) -> Integer.compare(b2.bottom, b1.bottom));

        // dp[i] : i번째 블럭을 가장 위에 놓았을 때의 최대 높이
        int[] dp = new int[n];

        // parent[i] : i번째 블럭의 바로 밑 블럭
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        // 가장 밑 블럭부터 쌓아보기 시작 (정렬을 했음)
        for (int i = 0; i < n; i++) {
            dp[i] = blocks[i].height;

            for (int j = 0; j < i; j++) {
                // i번째 블럭 밑에 j번째 블럭이 올 수 있는지 체크
                if (blocks[j].bottom > blocks[i].bottom && blocks[j].weight > blocks[i].weight) {
                    // 더 높은 탑이 쌓이는지 체크
                    if (dp[j] + blocks[i].height > dp[i]) {
                        dp[i] = dp[j] + blocks[i].height;
                        parent[i] = j;
                    }
                }
            }
        }

        // 최대 높이 쌓을 수 있는 탑의 가장 윗 인덱스
        int max = -1;
        int maxIndex = -1;
        for (int i = 0; i < n; i++) {
            if (dp[i] > max) {
                max = dp[i];
                maxIndex = i;
            }
        }

        // 탑의 윗 번호부터 바닥 번호까지 저장
        List<Integer> answer = new ArrayList<>();
        while (maxIndex != -1) {
            answer.add(blocks[maxIndex].index);
            maxIndex = parent[maxIndex];
        }

        // 정답 출력
        StringBuilder sb = new StringBuilder();
        sb.append(answer.size()).append('\n');
        for (Integer index : answer) {
            sb.append(index).append('\n');
        }
        System.out.println(sb);
    }

    private static class Block {
        int bottom;
        int height;
        int weight;
        int index;

        public Block(int bottom, int height, int weight, int index) {
            this.bottom = bottom;
            this.height = height;
            this.weight = weight;
            this.index = index;
        }
    }
}
