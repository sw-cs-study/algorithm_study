/**
 * 특정 높이까지 최대로 그림을 배치하는 dp 배열을 만들거임(냅색)
 * 냅색의 요소중에 (배낭의 용량)=(그림의 최대높이), (각 물건의 무게)=(판매가능 그림의 최소 세로 길이), (물건의 가치)=(그림의 가치)
 * 여기서 냅색의 기본적인 방법과 다른 점은 판매가능 그림의 최소 세로 길이가 정해져 있고, 현재 그림의 높이에서 최소 길이를 뺀 높이를, 이전까지 그림으로 최대한 많이 판 금액과, 
 * -> 이전까지 그림으로 현재 높이까지 최대한 많이 판 금액 을 비교해서 배열에 채움
 * 
 * 생각해보면 높이 순으로 정렬해서 각 높이별로 가지는 가치를 저장한뒤(중복제거)
 * 각 가치를 일차원 dp 배열로 작은 높이부터 최댓값을 가지도록 채워나감 => 이전 높이에서의 최댓값을 계속 가지고 가면서 비교해서 갱신
 */

package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2515 {

    
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int[] costs = new int[20_000_001];
        int[] dp = new int[20_000_001];
        int maxHeight = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if (costs[h] < c) {
                costs[h] = c;
            }
            if (maxHeight < h) {
                maxHeight = h;
            }
        }
        for (int i = s; i < maxHeight+1; i++) {
            dp[i] = Math.max(costs[i] + dp[i-s], dp[i-1]);
        }

        System.out.println(dp[maxHeight]);
    }

    

}
