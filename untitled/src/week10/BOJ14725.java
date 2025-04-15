package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ14725 {
    static int n;   // 먹이 정보 개수
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(bf.readLine());

        StringTokenizer st;

        Trie trie = new Trie();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            int num = Integer.parseInt(st.nextToken());
            String[] words = new String[num];
            for (int j = 0; j < num; j++) {
                words[j] = st.nextToken();
            }

            trie.add(words);
        }

        trie.print(trie.root, 0);
        System.out.println(sb);
    }

    private static class Trie {
        Node root;

        Trie() {
            root = new Node();
        }

        void add(String[] words) {
            Node node = this.root;
            for (String word : words) {
                node = node.getChildNodes().computeIfAbsent(word, w -> new Node());
            }
        }

        public void print(Node now, int depth) {
            Node node = now;
            List<String> keyList = new ArrayList<>(node.getChildNodes().keySet());
            Collections.sort(keyList);

            for (String key : keyList) {
                for (int i = 0; i < depth; i++) {
                    sb.append('-');
                }
                sb.append(key).append('\n');
                print(node.getChildNodes().get(key), depth + 2);
            }
        }
    }

    private static class Node {
        private Map<String, Node> childNodes = new HashMap<>();

        public Map<String, Node> getChildNodes() {
            return childNodes;
        }
    }
}
