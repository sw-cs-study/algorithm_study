/**
 * dp[i]: i번째 벽돌을 제일 밑의 벽돌로 쌓았을 때, 최대 높이.
 */

package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ2655 {

    private static class brick {
        int id, width, height, weight;
        brick(int id, int width, int height, int weight) {
            this.id = id;
            this.width = width;
            this.height = height;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        brick[] bricks = new brick[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int width = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            bricks[i] = new brick(i+1, width, height, weight);

        }

        // 넓이를 기준으로 오름차순 정렬
        Arrays.sort(bricks, (brick a, brick b)->a.width - b.width);
        // dp 배열 선언
        int[] dp = new int[n];
        // 벽돌 추적할 최댓값
        int maxHeight = 0;

        for (int i = 0; i < n; i++) {
            // 현재 벽돌을 제일 밑에 둔 탑의 최대 높이 구하기
            // dp배열에서 i 앞을 탐색하며 가장 밑에 벽돌이 현재(i) 벽돌보다 가벼울 경우 현재 값 갱신 -> dp 배열에서의 순서가 넓이의 순서만 보장하고 있어 무게의 순서는 보장하지 않기 때문에 dp배열 앞의 모든 경우를 체크해야함.
            int curWeight = bricks[i].weight;
            int curHeight = bricks[i].height;
            dp[i] = curHeight;
            for (int j = 0; j < i; j++) {
                if (curWeight < bricks[j].weight) continue;
                int comparator = dp[j]+curHeight;
                if (comparator > dp[i]) {
                    dp[i] = comparator;
                }
            }
            // 높이 최댓값 갱신
            if (dp[i] > maxHeight) {
                maxHeight = dp[i];
            }
        }
        // 최대 높이를 가진 탑에서 밑에 쌓은 벽돌을 제거하면 추적하기
        ArrayList<Integer> track = new ArrayList<>();
        // dp 배열 뒤부터 
        for (int i = n-1; i > -1; i--) {
            if (maxHeight == dp[i]) {
                maxHeight -= bricks[i].height;
                track.add(bricks[i].id);
                if (maxHeight == 0) break;
            }
        }
        // 출력 포맷 맞추기
        StringBuilder answer = new StringBuilder();
        answer.append(track.size()+"\n");
        for (int i = track.size()-1; i > -1; i--) {
            answer.append(track.get(i) + "\n");

        }
        System.out.println(answer);
    }
}