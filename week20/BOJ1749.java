/**
 * dp와 누적합이다
 * 200 * 200 행렬에서 현재까지 최대의 부분행렬을 합을 저장하는 dp 배열을 만든다.
 * 여기서 (i 행 j 열까지의 부분행렬의 합 중 최대값) = (i-1 행 j열까지의 부분행렬의 합 중 최댓값), (i 행 j-1열까지의 부분행렬의 합 중 최댓값), 
 * 
 * 고려할 점
 * => 하나의 행으로만, 또는 하나의 열로만 이루어진 부분행렬이 존재할 경우 행과 열이 2개 이상의 부분행렬과는 계산이 다름 => 고로 따로 계산하기, 그럼 각 dp[i][j] 계산시 3개의 값을 저장함
 * 
 * ...
 * 최대인 구간을 정하는 방법은 (i', j') (i, j) (i'<=i, j'<=j)구간마다 정해야함
 * 특정 i,j를 정했을 때, i',j'를 하나씩 옮겨가며 다 찾으면 200*200*200*200으로 터짐
 * => 카데인 알고로즘 적용
 * 열값을 기준으로 왼쪽 열을 고정하고 오른쪽 열을 늘려가는 방식으로 모든 부분행렬 중 최댓값을 구할거임
 * 모든 행에 대해서 누적합 값을 가지고 해당행을 오른쪽 끝 열로 가지는 부분 행렬의 합의 최댓값을 구함 => 행에 대한 누적합 값이 1차원 배열로 나오므로 여기서 연속된 구간 중 최댓값이 부분행렬의 최댓값임
 * => 이 값들을 비교해서 최댓값을 출력하면 됨
 * 이렇게 구하면 O(n^3)으로 연산됨
 * 
 */

package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1749 {
    static int MIN_VALUE = -400_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] board = new int[n][m];
        // O(n^2)
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // O(n^2)
        int[][] prefixSums = new int[n][m]; // 행이 누적합 배열임
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j == 0) {
                    prefixSums[i][j] = board[i][j];
                    continue;
                }
                prefixSums[i][j] = board[i][j] + prefixSums[i][j-1];
            }
        }
        int maxMetrixSum = MIN_VALUE;
        for (int leftColumn = 0; leftColumn < m; leftColumn++) {
            // 초기 column 값 만들어야 함. 초기값을 만들면서 column배열 갱신해야함
            for (int rightColumn = leftColumn; rightColumn < m; rightColumn++) {
                int ret = Kadane(prefixSums, leftColumn, rightColumn);
                if (ret > maxMetrixSum) {
                    maxMetrixSum = ret;
                }
                System.out.println(ret);
            }
            
        }
        System.out.println(maxMetrixSum);

    }

    /**
     * 1차원 배열의 값을 갱신하고, 연속된 구간합의 최댓값을 리턴
     * @param arr
     * @return
     */
    private static int Kadane(int[][] prefixSum, int leftColumn, int rightColumn) {
        int maxSum = MIN_VALUE;
        int curSum = 0;
        
        // 최대 구간합 구하기
        for (int i = 0; i < prefixSum.length; i++) {
            int value = prefixSum[i][rightColumn];
            if (leftColumn > 0) {
                value -= prefixSum[i][leftColumn - 1];
            }
            curSum = Math.max(value, curSum + value);
            if (maxSum < curSum) {
                maxSum = curSum;
            }
        }

        return maxSum;  
    }


}
