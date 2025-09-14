package week26;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class BOJ16986 {

    static int n, k;
    static int[][] matchUp;
    static int[] gh, mh;
    static Set<Integer> jw = new HashSet<>();
    static int[] wins = new int[3];
    static int ghIndex = 0;
    static int mhIndex = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());   // 가위바위보 손동작 수
        k = Integer.parseInt(st.nextToken());   // 우승을 위해 필요한 승수

        // 가위바위보 상성
        // [i][j] = 2 => i가 승
        // [i][j] = 1 => 비김
        // [i][j] = 0 => i가 짐
        matchUp = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                matchUp[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 경희
        gh = new int[20];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 20; i++) {
            gh[i] = Integer.parseInt(st.nextToken());
        }

        // 민호
        mh = new int[20];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 20; i++) {
            mh[i] = Integer.parseInt(st.nextToken());
        }

        boolean result = recur(0, 1, 2);
        System.out.println(result ? 1 : 0);
    }

    // a랑 b랑 경기, c는 경기x
    private static boolean recur(int a, int b, int c) {
        // 지우가 승리 조건 달성한 경우
        if (wins[0] == k) {
            return true;
        }

        // 경희, 민호가 승리 조건 달성한 경우
        if (wins[1] == k || wins[2] == k) {
            return false;
        }

        // 지우가 경기에 참여하는 경우
        if (a == 0 || b == 0) {
            int opponent = (a == 0) ? b : a;

            // 경희인데 이미 턴을 다 사용한 경우
            if (opponent == 1 && ghIndex >= 20) {
                return false;
            }

            // 민호인데 이미 턴을 사용한 경우
            if (opponent == 2 && mhIndex >= 20) {
                return false;
            }

            for (int i = 1; i <= n; i++) {
                // 지우가 이미 사용한 손동작인 경우
                if (jw.contains(i)) {
                    continue;
                }

                int opponentMove = opponent == 1 ? gh[ghIndex] : mh[mhIndex];
                int aPose = (a == 0 ? i : opponentMove);
                int bPose = (b == 0 ? i : opponentMove);

                int winner = fight(a, b, aPose, bPose, Math.max(a, b));
                int loser = (winner == a ? b : a);

                jw.add(i);
                wins[winner]++;
                if (opponent == 1) {
                    ghIndex++;
                } else {
                    mhIndex++;
                }

                boolean result = recur(winner, c, loser);

                jw.remove(i);
                wins[winner]--;
                if (opponent == 1) {
                    ghIndex--;
                } else {
                    mhIndex--;
                }

                if (result) {
                    return true;
                }
            }
            return false;
        } else {    // 지우가 경기에 참여하지 않는 경우
            if (ghIndex >= 20 || mhIndex >= 20) {
                return false;
            }

            int aPose = (a == 1 ? gh[ghIndex] : mh[mhIndex]);
            int bPose = (b == 1 ? gh[ghIndex] : mh[mhIndex]);

            int winner = fight(a, b, aPose, bPose, Math.max(a, b));
            int loser = (winner == a ? b : a);

            wins[winner]++;
            ghIndex++;
            mhIndex++;

            boolean result = recur(winner, c, loser);

            wins[winner]--;
            ghIndex--;
            mhIndex--;

            return result;
        }
    }

    // 가위바위보
    private static int fight(int a, int b, int i, int j, int tieWinner) {
        int result = matchUp[i - 1][j - 1];

        if (result == 2) {  // a 승
            return a;
        } else if (result == 0) {   // b 승
            return b;
        } else {    // 비김
            return tieWinner;
        }
    }
}
