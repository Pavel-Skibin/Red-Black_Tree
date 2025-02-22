package org.nahap.tree.binarytree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BTreeAlgorithmsTest {

    private static class TestTreeNode<T> implements BTree.TreeNode<T> {
        private final T value;
        private final BTree.TreeNode<T> left;
        private final BTree.TreeNode<T> right;

        public TestTreeNode(T value, BTree.TreeNode<T> left, BTree.TreeNode<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public BTree.TreeNode<T> getLeft() {
            return left;
        }

        @Override
        public BTree.TreeNode<T> getRight() {
            return right;
        }
    }

    private BTree.TreeNode<Integer> createTestTree() {
        // Дерево:
        //        1
        //      /   \
        //     2     3
        //    / \   / \
        //   4   5 6   7
        return new TestTreeNode<>(1,
                new TestTreeNode<>(2,
                        new TestTreeNode<>(4, null, null),
                        new TestTreeNode<>(5, null, null)),
                new TestTreeNode<>(3,
                        new TestTreeNode<>(6, null, null),
                        new TestTreeNode<>(7, null, null)));
    }

    @Test
    void testPreOrderVisit() {
        BTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.preOrderVisit(tree, visitor);

        assertEquals(List.of(1, 2, 4, 5, 3, 6, 7), visited);
    }

    @Test
    void testInOrderVisit() {
        BTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.inOrderVisit(tree, visitor);

        assertEquals(List.of(4, 2, 5, 1, 6, 3, 7), visited);
    }

    @Test
    void testPostOrderVisit() {
        BTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.postOrderVisit(tree, visitor);

        assertEquals(List.of(4, 5, 2, 6, 7, 3, 1), visited);
    }

    @Test
    void testByLevelVisit() {
        BTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.byLevelVisit(tree, visitor);

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), visited);
    }

    private BTree.TreeNode<Integer> createAsymmetricTree() {
        // Дерево:
        //        10
        //      /    \
        //     5      15
        //      \        \
        //       8       20
        return new TestTreeNode<>(10,
                new TestTreeNode<>(5,
                        null,
                        new TestTreeNode<>(8, null, null)),
                new TestTreeNode<>(15,
                        null,
                        new TestTreeNode<>(20, null, null)));
    }

    @Test
    void testPreOrderTraversalOnAsymmetricTree() {
        BTree.TreeNode<Integer> tree = createAsymmetricTree();

        List<Integer> preOrder = new ArrayList<>();
        BinaryTreeAlgorithms.preOrderVisit(tree, (value, level) -> preOrder.add(value));

        assertEquals(List.of(10, 5, 8, 15, 20), preOrder);
    }


    @Test
    void testInOrderTraversalOnAsymmetricTree() {
        BTree.TreeNode<Integer> tree = createAsymmetricTree();

        List<Integer> inOrder = new ArrayList<>();
        BinaryTreeAlgorithms.inOrderVisit(tree, (value, level) -> inOrder.add(value));

        assertEquals(List.of(5, 8, 10, 15, 20), inOrder);
    }

    @Test
    void testPostOrderTraversalOnAsymmetricTree() {
        BTree.TreeNode<Integer> tree = createAsymmetricTree();

        List<Integer> postOrder = new ArrayList<>();
        BinaryTreeAlgorithms.postOrderVisit(tree, (value, level) -> postOrder.add(value));

        assertEquals(List.of(8, 5, 20, 15, 10), postOrder);
    }

    @Test
    void testByLevelTraversalOnAsymmetricTree() {
        BTree.TreeNode<Integer> tree = createAsymmetricTree();

        List<Integer> byLevel = new ArrayList<>();
        BinaryTreeAlgorithms.byLevelVisit(tree, (value, level) -> byLevel.add(value));

        assertEquals(List.of(10, 5, 15, 8, 20), byLevel);
    }


}
