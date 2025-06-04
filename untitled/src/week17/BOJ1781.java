package week17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1781 {

    static int n;
    static PriorityQueue<Problem> candidate;
    static PriorityQueue<Problem> select;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(bf.readLine());
        candidate = new PriorityQueue<>(
                (p1, p2) -> {
                    if (p1.deadLine == p2.deadLine) {
                        return Integer.compare(p2.cupRamen, p1.cupRamen);
                    }

                    return Integer.compare(p1.deadLine, p2.deadLine);
                }
        );

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int deadLine = Integer.parseInt(st.nextToken());
            int cupRamen = Integer.parseInt(st.nextToken());

            candidate.add(new Problem(deadLine, cupRamen));
        }

        select = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.cupRamen, p2.cupRamen)
        );

        int answer = 0;
        while (!candidate.isEmpty()) {
            Problem now = candidate.poll();
            if (select.size() < now.deadLine) {
                select.add(now);
                answer += now.cupRamen;
            } else {
                Problem minP = select.peek();
                if (minP.cupRamen < now.cupRamen) {
                    select.poll();
                    answer -= minP.cupRamen;

                    select.add(now);
                    answer += now.cupRamen;
                }
            }
        }

        System.out.println(answer);
    }

    private static class Problem {
        int deadLine;
        int cupRamen;

        public Problem(int deadLine, int cupRamen) {
            this.deadLine = deadLine;
            this.cupRamen = cupRamen;
        }
    }
}
