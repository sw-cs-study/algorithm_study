package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2560 {

    // 1. 태어난 이후 a일째 되는 날 성체가 된다.
    // 1-1. 성체가 되자마자 첫 개체를 만든다.
    // 1-2. 이후 하루가 지날 때마다 새로운 개체를 하나씩 만들어낸다.
    // 2. 태어난 이후로 b일째 되는 순간부터는 새로운 개체를 더 이상 만들지 않는다.
    // 3. 태어난 이후로 d일째 되는 순간 죽는다.
    // 4. N일째 되는 날 살아있는 개체 수를 1000으로 나눈 나머지를 출력하시오.
    static int a, b, d, N;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        int[] cnt = new int[N + 1]; // i일 일때 살아있는 개체 수
        for (int i = 0; i < a; i++) {
            cnt[i] = 1; // a일전까지는 처음에 넣었던 1마리
        }

        for (int i = a; i < N + 1; i++) {
            cnt[i] = (cnt[i - 1] + cnt[i - a]) % 1000;
            if (i >= b) {
                cnt[i] = (cnt[i] - cnt[i - b] + 1000) % 1000;
            }
        }

        if (N >= d) {   // d일전에 있는 개체는 모두 죽으므로
            System.out.println((cnt[N] - cnt[N - d] + 1000) % 1000);
        } else {
            System.out.println(cnt[N] % 1000);
        }
    }
}
