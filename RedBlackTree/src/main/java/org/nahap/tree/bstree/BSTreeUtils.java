package org.nahap.tree.bstree;


import org.nahap.tree.binarytree.BTree;

public class BSTreeUtils {

    public static <T extends Comparable<? super T>> BTree.TreeNode<T> findNode(BTree.TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = node.getValue().compareTo(value);
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            return findNode(node.getLeft(), value);
        } else {
            return findNode(node.getRight(), value);
        }
    }


    public static <T extends Comparable<? super T>> BTree.TreeNode<T> findMinNode(BTree.TreeNode<T> node) {
        return (node == null || node.getLeft() == null) ? node : findMinNode(node.getLeft());
    }


    public static <T extends Comparable<? super T>> BTree.TreeNode<T> findMaxNode(BTree.TreeNode<T> node) {
        return (node == null || node.getRight() == null) ? node : findMaxNode(node.getRight());
    }


    public static <T extends Comparable<? super T>> BTree.TreeNode<T> findFloorNode(BTree.TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.getValue());
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return findFloorNode(node.getLeft(), value);
        } else {
            BTree.TreeNode<T> res = findFloorNode(node.getRight(), value);
            return (res != null) ? res : node;
        }
    }

    public static <T extends Comparable<? super T>> BTree.TreeNode<T> findCeilingNode(BTree.TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.getValue());
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            return findCeilingNode(node.getRight(), value);
        } else {
            BTree.TreeNode<T> res = findCeilingNode(node.getLeft(), value);
            return (res != null) ? res : node;
        }
    }
}
