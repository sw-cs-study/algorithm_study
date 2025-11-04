package week28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ3687 {

    static String[] min = {"", "", "1", "7", "4", "2", "6", "8", "10", "18", "22"};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        StringBuilder answer = new StringBuilder();
        while (T-- > 0) {
            int n = Integer.parseInt(bf.readLine());

            StringBuilder big = new StringBuilder();
            int tmp = n;
            if (tmp % 2 == 1) {
                tmp -= 3;   // n이 2 이상이기 때문에 홀수라면 3을 무조건 뺄 수 있음
                big.append('7');
                while (tmp > 0) {
                    tmp -= 2;
                    big.append('1');
                }
            } else {
                while (tmp > 0) {
                    tmp -= 2;
                    big.append('1');
                }
            }

            StringBuilder small = new StringBuilder();
            tmp = n;
            if (tmp < 11) {
                small.append(min[tmp]);
            } else {
                int a = tmp / 7;
                int b = tmp % 7;
                switch (b) {
                    case 0:
                        small.append("8".repeat(a));
                        break;
                    case 1:
                        small.append("10");
                        small.append("8".repeat(a - 1));
                        break;
                    case 2:
                        small.append("1");
                        small.append("8".repeat(a));
                        break;
                    case 3:
                        small.append("200");
                        small.append("8".repeat(a - 2));
                        break;
                    case 4:
                        small.append("20");
                        small.append("8".repeat(a - 1));
                        break;
                    case 5:
                        small.append("2");
                        small.append("8".repeat(a));
                        break;
                    case 6:
                        small.append("6");
                        small.append("8".repeat(a));
                        break;
                }
            }

            answer.append(small).append(" ").append(big).append('\n');
        }
        System.out.println(answer);
    }
}
