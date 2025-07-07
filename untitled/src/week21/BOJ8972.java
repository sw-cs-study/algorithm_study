package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ8972 {

    static int r, c;
    static char[][] map;

    static int[] dy = {0, 1, 1, 1, 0, 0, 0, -1, -1, -1};
    static int[] dx = {0, -1, 0, 1, -1, 0, 1, -1, 0, 1};

    static Node jongsu;
    static List<Node> robotList;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        /**
         * . : 빈 칸
         * R : 미친 아두이노
         * I : 종수
         */
        robotList = new ArrayList<>();
        map = new char[r][c];
        for (int i = 0; i < r; i++) {
            String row = bf.readLine();
            for (int j = 0; j < c; j++) {
                map[i][j] = row.charAt(j);
                if (map[i][j] == 'I') {
                    jongsu = new Node(i, j);
                } else if (map[i][j] == 'R') {
                    robotList.add(new Node(i, j));
                }
            }
        }

        String command = bf.readLine();
        for (int i = 0; i < command.length(); i++) {
            boolean flag = move(Character.getNumericValue(command.charAt(i)));
            if (!flag) {
                System.out.println("kraj " + (i + 1));
                return;
            }
        }

        // 정답 세팅
        char[][] answer = new char[r][c];
        for (char[] row : answer) {
            Arrays.fill(row, '.');
        }

        // 종수
        answer[jongsu.y][jongsu.x] = 'I';

        // 아두이노
        for (Node robot : robotList) {
            answer[robot.y][robot.x] = 'R';
        }

        // 정답 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(answer[i][j]);
            }
            sb.append('\n');
        }

        System.out.println(sb);
    }

    private static boolean move(int dir) {
        // 범위 벗어나는 입력은 들어오지 않음
        jongsu.y += dy[dir];
        jongsu.x += dx[dir];

        for (Node robot : robotList) {
            if (jongsu.y == robot.y && jongsu.x == robot.x) {
                return false;
            }
        }

        // 미친 아두이노 차례
        int[][] count = new int[r][c];
        for (Node robot : robotList) {
            // 다음 위치
            int ny = robot.y + Integer.signum(jongsu.y - robot.y);
            int nx = robot.x + Integer.signum(jongsu.x - robot.x);

            // 해당 위치 로봇 수 카운트
            count[ny][nx]++;

            // 로봇 위치 변경
            robot.y = ny;
            robot.x = nx;
        }

        // 로봇 움직인 후 상태 관리
        // 종수랑 겹치는지, 한 개만 남았는지
        List<Node> nextRobotList = new ArrayList<>();
        for (Node robot : robotList) {
            if (robot.y == jongsu.y && robot.x == jongsu.x) {
                return false;
            }

            if (count[robot.y][robot.x] == 1) {
                nextRobotList.add(robot);
            }
        }

        robotList = nextRobotList;
        return true;
    }

    private static class Node {
        int y;
        int x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
