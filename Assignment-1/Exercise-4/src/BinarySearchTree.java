public class BinarySearchTree {
    protected class Node {
        protected int data;
        protected Node left;
        protected Node right;

        protected Node(int x) {
            data = x;
        }
    }

    Node root;

    void insert(int x) {
        root = insert(root, x);
    }

    Node insert(Node v, int x) {
        if(v == null)
            return new Node(x);
        else if (x < v.data)
            v.left = insert(v.left, x);
        else if (x > v.data)
            v.right = insert(v.right, x);
        return v;
    }

    int minHeight(Node v) {
        if(v == null)
            return -1;
        return 1 + Math.min(minHeight(v.left), minHeight(v.right));
    }

    int height(Node v) {
        if(v == null)
            return -1;
        return 1 + Math.max(height(v.left), height(v.right));
    }

    boolean isBalanced() {
        return height(root) - minHeight(root) <= 1;
    }
}
