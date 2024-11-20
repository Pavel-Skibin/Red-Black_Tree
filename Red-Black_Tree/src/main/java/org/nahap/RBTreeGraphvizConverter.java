package org.nahap;



public class RBTreeGraphvizConverter {
    public static <T extends Comparable<? super T>> String convertToDot(RBTree<T> tree) {
        StringBuilder dot = new StringBuilder();
        dot.append("graph RedBlackTree {\n");
        dot.append("    node [shape=circle];\n");

        if (tree.root != null) {
            appendNode(dot, tree.root);
        }

        dot.append("}");
        return dot.toString();
    }

    private static <T extends Comparable<? super T>> void appendNode(StringBuilder dot, RBTree<T>.RBTreeNode node) {
        if (node == null) return;


        String color = node.color == RBTree.RED ? "red" : "black";
        String fontColor = node.color == RBTree.RED ? "black" : "white";

        dot.append(String.format("    \"%s\" [color=%s, style=filled, fontcolor=%s];\n",
                node.value, color, fontColor));

        // Left child
        if (node.left != null) {
            dot.append(String.format("    \"%s\" -- \"%s\";\n", node.value, node.left.value));
            appendNode(dot, node.left);
        }

        // Right child
        if (node.right != null) {
            dot.append(String.format("    \"%s\" -- \"%s\";\n", node.value, node.right.value));
            appendNode(dot, node.right);
        }
    }


}