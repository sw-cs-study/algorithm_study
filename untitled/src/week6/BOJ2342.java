package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2342 {

    static int size;
    static int[] arr;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        size = st.countTokens();
        arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[5][5][100001];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        System.out.println(recur(0, 0, 0));
    }

    private static int recur(int left, int right, int index) {
        if (arr[index] == 0) {
            // 게임 끝
            return 0;
        }

        if (dp[left][right][index] != -1) {
            return dp[left][right][index];
        }

        int ret = 0;

        int moveLeftAgain = Integer.MAX_VALUE;
        int moveRightAgain = Integer.MAX_VALUE;
        int moveLeft = Integer.MAX_VALUE;
        int moveRight = Integer.MAX_VALUE;


        if (left == arr[index]) {   // 밟아야 하는 칸에 이미 왼발이 있는 경우
            moveLeftAgain = recur(arr[index], right, index + 1) + 1;
        } else if (right == arr[index]) {   // 밟아야 하는 칸에 이미 오른발이 있는 경우
            moveRightAgain = recur(left, arr[index], index + 1) + 1;
        } else {
            // 왼발이 다음 칸으로 이동
            moveLeft = recur(arr[index], right, index + 1) + (left == 0 ? 2 : (isAdjoin(left, arr[index]) ? 3 : 4));

            // 오른발이 다음 칸으로 이동
            moveRight = recur(left, arr[index], index + 1) + (right == 0 ? 2 : (isAdjoin(right, arr[index]) ? 3 : 4));
        }


        // 왼발 이동과 오른발 이동 중 최소의 힘을 선택
        ret += Math.min(moveRightAgain, Math.min(moveLeftAgain, Math.min(moveLeft, moveRight)));

        dp[left][right][index] = ret;

        return ret;
    }

    private static boolean isAdjoin(int now, int next) {
        if (next == 1) {
            if (now == 3) {
                return false;
            }
            return true;
        } else if (next == 2) {
            if (now == 4) {
                return false;
            }
            return true;
        } else if (next == 3) {
            if (now == 1) {
                return false;
            }
            return true;
        } else {
            if (now == 2) {
                return false;
            }
            return true;
        }
    }
}