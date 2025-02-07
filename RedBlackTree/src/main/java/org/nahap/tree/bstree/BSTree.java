package org.nahap.tree.bstree;


import org.nahap.tree.binarytree.BTree;


public interface BSTree<T extends Comparable<? super T>> extends BTree<T> {


    default BTree.TreeNode<T> findNode(T value) {
        return BSTreeUtils.findNode(getRoot(), value);
    }


    default T get(T value) {
        BTree.TreeNode<T> valueNode = findNode(value);
        return (valueNode == null) ? null : valueNode.getValue();
    }

    default boolean contains(T value) {
        return findNode(value) != null;
    }

    T put(T value);


    T remove(T value);

    void clear();

    int size();

    default BTree.TreeNode<T> findMinNode() {
        return BSTreeUtils.findMinNode(getRoot());
    }

    default T getMin() {
        BTree.TreeNode<T> minNode = findMinNode();
        return (minNode == null) ? null : minNode.getValue();
    }

    default BTree.TreeNode<T> findMaxNode() {
        return BSTreeUtils.findMaxNode(getRoot());
    }

    default T getMax() {
        BTree.TreeNode<T> minNode = findMinNode();
        return (minNode == null) ? null : minNode.getValue();
    }

    default BTree.TreeNode<T> findFloorNode(T value) {
        return BSTreeUtils.findFloorNode(getRoot(), value);
    }

    default T getFloor(T value) {
        BTree.TreeNode<T> floorNode = findFloorNode(value);
        return (floorNode == null) ? null : floorNode.getValue();
    }


    default BTree.TreeNode<T> findCeilingNode(T value) {
        return BSTreeUtils.findCeilingNode(getRoot(), value);
    }


    default T getCeiling(T value) {
        BTree.TreeNode<T> ceilingNode = findCeilingNode(value);
        return (ceilingNode == null) ? null : ceilingNode.getValue();
    }
}
