package week28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOJ20191 {

    static Map<Character, List<Integer>> idxMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        String t = bf.readLine();

        // t에 속한 문자들의 위치 저장
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            idxMap.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
        }

        // s에 속한 문자가 t에 없는 경우 -1 출력 후 종료
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (idxMap.containsKey(c)) {
                continue;
            }

            System.out.println(-1);
            return;
        }

        int cur = 0;
        int n = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            List<Integer> list = idxMap.get(c);
            int idx = lower(list, cur);
            if (idx == list.size()) {
                n++;
                cur = list.get(0) + 1;
            } else {
                cur = list.get(idx) + 1;
            }
        }

        System.out.println(n);
    }

    private static int lower(List<Integer> list, int target) {
        int left = -1;
        int right = list.size();
        while (left + 1 < right) {
            int mid = (left + right) / 2;

            if (!(list.get(mid) >= target)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return right;
    }
}
