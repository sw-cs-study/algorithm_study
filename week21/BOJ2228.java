/**
 * i번째 수까지 사용해서 j개의 구간을 만들려면
 * i번째 수를 사용하지 않고 j개의 구간을 만들때와 i번째 수를 사용해서 j개의 구간을 만들때가 있음.
 * 1. i번째 수를 사용하지 않고 j개의 구간을 만들때는 
 * => i-1번째 수까지 사용해서 j개의 구간을 만들때와 i-1번째 수를 사용하지 않고 j개의 구간을 만드는 경우가 있음
 * 2. i번째 수를 사용해서 j개의 구간을 만들때는
 * => i-1번째 수를 사용하지 않고 j-1개의 구간을 만들때에 i번째 수를 더할 때랑, 
 * => i-1번째 수를 사용해서 j개의 구간을 만들때에 i번째 수를 더할 때가 있음.
 * 
 * i번째 수를 사용하는 배열은 contain, 사용하지 않는 배열은 notContain으로 하고 식으로 표현하면
 * 1. notContain[i][j] = Math.max(contain[i-1][j], notContain[i-1][j]);
 * 2. contain[i][j] = Math.max(notContain[i-1][j-1] + number, contain[i-1][j] + number)
 * 마지막에 둘중에 큰값을 출력하면 됨
 * 
 * 고려할점
 * i번째 수를 가지고 만들 수 있는 최대 구간 갯수는 (i+1)/2 이하임
 * 음수가 있어서 무조건 0으로 초기화 하면 안됨
 * => i번째 수를 사용해서 0개의 구간을 만들때 최댓값은 0으로 해야함 => 2번 연산에서 오류가 없음
 * => i번째 수에서 j개의 구간을 만들고 최댓값을 정하지 않은 모든 부분은 MIN_VALUE 처리 => 존재하지 않는 값이기 때문에 연산시 음수보다 작은 값으로 만들어야함
 */
package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2228 {
    private static int MIN_VALUE = -32768000;
    public static void main (String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] contain = new int[n+1][m+1];
        int[][] notContain = new int[n+1][m+1];
        for (int i = 0; i < n+1; i++) {
            for (int j = 1; j < m+1; j++) {
                contain[i][j] = MIN_VALUE;
                notContain[i][j] = MIN_VALUE;
            }
            
        }
        for (int i = 1; i < n+1; i++) {
            int number = Integer.parseInt(br.readLine());
            for (int j = 1; j <= (i + 1) / 2; j++) {
                if (j > m) break;
                notContain[i][j] = Math.max(contain[i-1][j], notContain[i-1][j]);
                contain[i][j] = Math.max(notContain[i-1][j-1] + number, contain[i-1][j] + number);
            }
        }
        System.out.println(Math.max(notContain[n][m], contain[n][m]));
    }
}
