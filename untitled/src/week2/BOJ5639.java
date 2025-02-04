package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ5639 {
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Tree tree = new Tree();
        while (true) {
            String number = bf.readLine();
            if (number == null || number.equals("")) {
                break;
            }
            tree.insert(Integer.parseInt(number));
        }

        tree.postOrder();
        System.out.println(sb);
    }

    static class Tree {
        Node root;

        Tree () {
            root = null;
        }

        public void insert(int val) {
            root = insertValue(root, val);
        }

        private Node insertValue(Node node, int val) {
            if (node == null) {
                node = new Node(val);
            } else if (val < node.val) {
                node.left = insertValue(node.left, val);
            } else if (val > node.val) {
                node.right = insertValue(node.right, val);
            }

            return node;
        }

        public void postOrder() {
            postOrderValue(root);
        }

        private void postOrderValue(Node node) {
            if (node == null) {
                return;
            }

            postOrderValue(node.left);
            postOrderValue(node.right);
            sb.append(node.val).append('\n');
        }
    }

    static class Node {
        Node left;
        Node right;
        int val;

        Node (int val) {
            this.val = val;
        }
    }
}