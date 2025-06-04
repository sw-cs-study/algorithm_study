package week16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BOJ16934 {

    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(bf.readLine());

        StringBuilder sb = new StringBuilder();
        Trie trie = new Trie();
        for (int i = 0; i < n; i++) {
            sb.append(trie.insert(bf.readLine())).append('\n');
        }
        System.out.println(sb);
    }

    private static class Node {
        int count;
        Map<Character, Node> childMap = new HashMap<>();

        public Map<Character, Node> getChildMap() {
            return childMap;
        }
    }

    private static class Trie {
        Node root;

        public Trie() {
            this.root = new Node();
        }

        public String insert(String word) {
            Node node = this.root;
            StringBuilder alias = new StringBuilder();
            boolean prefix = false;
            for (int i = 0; i < word.length(); i++) {
                char now = word.charAt(i);
                if (!prefix) {
                    alias.append(now);
                }

                if (!node.childMap.containsKey(now)) {
                    prefix = true;
                }

                node = node.childMap.computeIfAbsent(now, w -> new Node());
            }

            node.count++;

            if (prefix) {
                return alias.toString();
            } else {
                if (node.count != 1) {
                    alias.append(node.count);
                }

                return alias.toString();
            }
        }
    }
}
