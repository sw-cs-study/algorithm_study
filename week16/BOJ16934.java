/*
 * 게임 닉네임
 * 트라이로 각 문자에서 끝나는 단어가 있는지(몇개), 접두사가 가능한지(bool)로 구현, 각 입력에 대해서 바로 구현하기
 * 시간 복잡도 : (단어 갯수) * (단어 길이) = O(10n)) < 2초
 * 트라이 넣을 때 체크
 * 1. 들어온 단어를 순회하면서 접두사 만들기
 * 2. 루트 -> 다음 문자 -> 순으로 접두사로 가능한지(이전 단어들의 접두사가 아닌지) 체크, 이전 단어의 접두사면 현재 별칭에 계속 추가, 그렇지 않으면 추가 그만
 * 3. 단어의 끝에 도달했을 때도 접두사로 가능한지 체크해야함
 */

package week16;

import java.io.*;
import java.util.*;

public class BOJ16934 {

    private static int n; 
    private static Trie trie;
    private static StringBuilder answer;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        trie = new Trie();
        answer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            trie.insert(br.readLine());
            answer.append("\n");
        }
        System.out.println(answer);
    }

    private static class Node {
        char c;
        int count;
        HashMap<Character, Node> children;

        Node(char c) {
            this.c = c;
            this.count = 0;
            this.children = new HashMap<>();
        }
        void setEnd() {
            this.count++;
        }
        boolean hasChild(char child) {
            if (this.children.containsKey(child)) return true;
            return false;
        }
        void addChild(char child) {
            children.put(child, new Node(child));
        }
        Node getChild(char child) {
            return this.children.get(child);
        }
        int getCount() {
            return this.count;
        }
    }

    private static class Trie {
        Node root;

        Trie() {
            this.root = new Node(' ');
        }
        void insert(String word) {
            Node cur = root;
            boolean first = true;
            for (char c : word.toCharArray()) {
                // 현재 문자를 포함하는 경우면 이전 문자의 접두사이므로 별칭에 계속 추가
                if (cur.hasChild(c)) {
                    answer.append(c);
                } else {
                    // 현재 문자를 포함하지 않는 경우 
                    // 처음 여기 들어온 경우 별칭에 추가
                    if (first) {
                        answer.append(c);
                        first = false;
                    }
                    // 별칭이 더 이상 별칭에 추가하지 않고, children에 추가
                    cur.addChild(c);
                }
                // 다음 child node 가져오기
                cur = cur.getChild(c);
            }
            // 끝처리해야함
            cur.setEnd();
            if (first && cur.getCount() > 1) {
                // 같으면 별칭에 숫자 추가해야함. 단, 1인 경우는 제외
                answer.append(Integer.toString(cur.getCount()));
            } 
            // 다르면 별칭 그대로 사용
        }
    }
}

