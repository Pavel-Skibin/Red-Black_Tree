package org.nahap.tree.rbtree;


public class RBTreeGraphvizConverter {
    public static <T extends Comparable<? super T>> String convertToDot(RedBlackTree<T> tree) {
        StringBuilder dot = new StringBuilder();
        dot.append("graph RedBlackTree {\n");
        dot.append("    node [shape=circle];\n");

        if (tree.getRoot() != null) {
            appendNode(dot, (RedBlackTree<T>.RBTreeNode) tree.getRoot());
        }

        dot.append("}");
        return dot.toString();
    }

    private static <T extends Comparable<? super T>> void appendNode(StringBuilder dot, RedBlackTree<T>.RBTreeNode node) {
        if (node == null) return;


        String color = node.color == RedBlackTree.RED ? "red" : "black";
        String fontColor = node.color == RedBlackTree.RED ? "black" : "white";

        dot.append(String.format("    \"%s\" [color=%s, style=filled, fontcolor=%s];\n",
                node.value, color, fontColor));

        if (node.left != null) {
            dot.append(String.format("    \"%s\" -- \"%s\";\n", node.value, node.left.value));
            appendNode(dot, node.left);
        }

        if (node.right != null) {
            dot.append(String.format("    \"%s\" -- \"%s\";\n", node.value, node.right.value));
            appendNode(dot, node.right);
        }
    }


}