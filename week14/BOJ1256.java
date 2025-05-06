/*
 * 사전
 * a : n개, z : m개 => 1 <= n,m <= 100, 1 <= k <= 10^9
 * k개 다세면 시간초과 (10초 > 2초), 하지만 int로 가능(2^31-1 = 2*10^9)
 * 1. a와 z로 만들수 있는 수의 갯수 = (n+m)Cn => (n+m)! / n! / m! => k가 이거보다 크면 -1
 * 2. a와 z의 갯수에서 나올수 있는 갯수를 구해서 k에서 빼면서 남은 k로 필요한 구간만 구하기
 * -> k <= ((n-1)+m)C(n-1)면, a + (나머지)
 * -> k > ((n-1)+m)C(n-1)면, z + (나머지)
 * 고려할 점
 * -> n과 m중에 하나가0이 되는 경우
 * -> 팩토리얼 값이 최대 200! -> 10^2^100 * 10^9 -> 10^209 > 2^64라서 BigInteger써야함
 * -> -> 128 * 10^6 / 70 > 10 ^ 6 => 1000000자리까지 만들수 있음(10^1000000)
 */


package week14;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class BOJ1256 {
    static int n, m;
    static BigInteger k;
    static BigInteger[] factorialMemo;
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = new BigInteger(st.nextToken());
        factorialMemo = new BigInteger[n+m+1]; // 0으로 초기화
        factorialMemo[0] = new BigInteger("1");
        
        if ((factorial(m+n).divide(factorial(n).multiply(factorial(m)))).compareTo(k) == -1) {
            System.out.println(-1);
            return;
        }
        String answer = "";
        while (k.compareTo(new BigInteger("0")) == 1 && n > 0 && m > 0) {
            BigInteger norm = factorial(n-1+m).divide(factorial(n-1).multiply(factorial(m)));
            if (k.compareTo(norm) <= 0) { // k <= norm
                answer += "a"; 
                n--;
            }
            else { // k > norm
                answer += "z";
                m--;
                k = k.subtract(norm);
            }
        }
        if (n > 0) for (int i = 0; i < n; i++) answer += "a";
        else if (m > 0) for (int i = 0; i < m; i++) answer += "z";
        
        System.out.println(answer);
        return;
    }

    public static BigInteger factorial(int v) {
        if (factorialMemo[v] != null) return factorialMemo[v];
        factorialMemo[v] = factorial(v-1).multiply(new BigInteger(Integer.toString(v)));
        return factorialMemo[v];   
    }

    
}
