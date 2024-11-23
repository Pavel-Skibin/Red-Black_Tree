package org.nahap.tree.binarytree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeAlgorithmsTest {

    private static class TestTreeNode<T> implements BinaryTree.TreeNode<T> {
        private final T value;
        private final BinaryTree.TreeNode<T> left;
        private final BinaryTree.TreeNode<T> right;

        public TestTreeNode(T value, BinaryTree.TreeNode<T> left, BinaryTree.TreeNode<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public BinaryTree.TreeNode<T> getLeft() {
            return left;
        }

        @Override
        public BinaryTree.TreeNode<T> getRight() {
            return right;
        }
    }

    private BinaryTree.TreeNode<Integer> createTestTree() {
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
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.preOrderVisit(tree, visitor);

        assertEquals(List.of(1, 2, 4, 5, 3, 6, 7), visited);
    }

    @Test
    void testInOrderVisit() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.inOrderVisit(tree, visitor);

        assertEquals(List.of(4, 2, 5, 1, 6, 3, 7), visited);
    }

    @Test
    void testPostOrderVisit() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.postOrderVisit(tree, visitor);

        assertEquals(List.of(4, 5, 2, 6, 7, 3, 1), visited);
    }

    @Test
    void testByLevelVisit() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> visited = new ArrayList<>();
        BinaryTreeAlgorithms.Visitor<Integer> visitor = (value, level) -> visited.add(value);

        BinaryTreeAlgorithms.byLevelVisit(tree, visitor);

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), visited);
    }

    @Test
    void testPreOrderValues() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> result = new ArrayList<>();
        BinaryTreeAlgorithms.preOrderValues(tree).forEach(result::add);

        assertEquals(List.of(1, 2, 4, 5, 3, 6, 7), result);
    }

    @Test
    void testInOrderValues() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> result = new ArrayList<>();
        BinaryTreeAlgorithms.inOrderValues(tree).forEach(result::add);

        assertEquals(List.of(4, 2, 5, 1, 6, 3, 7), result);
    }

    @Test
    void testPostOrderValues() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> result = new ArrayList<>();
        BinaryTreeAlgorithms.postOrderValues(tree).forEach(result::add);

        assertEquals(List.of(4, 5, 2, 6, 7, 3, 1), result);
    }

    @Test
    void testByLevelValues() {
        BinaryTree.TreeNode<Integer> tree = createTestTree();
        List<Integer> result = new ArrayList<>();
        BinaryTreeAlgorithms.byLevelValues(tree).forEach(result::add);

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), result);
    }
}
