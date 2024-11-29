package org.nahap.tree.bstree;

import org.junit.jupiter.api.Test;
import org.nahap.tree.binarytree.BinaryTree;

import static org.junit.jupiter.api.Assertions.*;

class BSTreeAlgorithmsTest {

    static class TestTreeNode<T> implements BinaryTree.TreeNode<T> {
        private final T value;
        private final TestTreeNode<T> left;
        private final TestTreeNode<T> right;

        TestTreeNode(T value, TestTreeNode<T> left, TestTreeNode<T> right) {
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


    private final TestTreeNode<Integer> sampleTree =
            new TestTreeNode<>(10,
                    new TestTreeNode<>(5,
                            new TestTreeNode<>(2, null, null),
                            new TestTreeNode<>(8, null, null)),
                    new TestTreeNode<>(15,
                            new TestTreeNode<>(12, null, null),
                            new TestTreeNode<>(20, null, null))

                    //         10
                    //        /  \
                    //       5    15
                    //      / \   / \
                    //     2   8 12  20

            );

    @Test
    void testGetNode() {
        assertEquals(10, BSTreeAlgorithms.getNode(sampleTree, 10).getValue());
        assertEquals(5, BSTreeAlgorithms.getNode(sampleTree, 5).getValue());
        assertNull(BSTreeAlgorithms.getNode(sampleTree, 25));
    }

    @Test
    void testGetMinNode() {
        assertEquals(2, BSTreeAlgorithms.getMinNode(sampleTree).getValue());
        assertEquals(8, BSTreeAlgorithms.getMinNode(sampleTree.getLeft().getRight()).getValue());
    }

    @Test
    void testGetMaxNode() {
        assertEquals(20, BSTreeAlgorithms.getMaxNode(sampleTree).getValue());
        assertEquals(12, BSTreeAlgorithms.getMaxNode(sampleTree.getRight().getLeft()).getValue());
    }

    @Test
    void testGetFloorNode() {
        assertEquals(10, BSTreeAlgorithms.getFloorNode(sampleTree, 10).getValue());
        assertEquals(8, BSTreeAlgorithms.getFloorNode(sampleTree, 9).getValue());
        assertNull(BSTreeAlgorithms.getFloorNode(sampleTree, 1));
    }

    @Test
    void testGetCeilingNode() {
        assertEquals(10, BSTreeAlgorithms.getCeilingNode(sampleTree, 10).getValue());
        assertEquals(12, BSTreeAlgorithms.getCeilingNode(sampleTree, 11).getValue());
        assertNull(BSTreeAlgorithms.getCeilingNode(sampleTree, 25));
    }


    private final TestTreeNode<Integer> asymmetricTree =
            new TestTreeNode<>(10,
                    new TestTreeNode<>(5,
                            new TestTreeNode<>(3,
                                    new TestTreeNode<>(1, null, null),
                                    null),
                            new TestTreeNode<>(8, null, null)),
                    new TestTreeNode<>(15,
                            null,
                            new TestTreeNode<>(20, null, null))
                    //         10
                    //        /  \
                    //       5    15
                    //      / \      \
                    //     3   8      20
                    //    /
                    //   1
            );

    @Test
    void testGetNodeAsymmetric() {
        assertEquals(10, BSTreeAlgorithms.getNode(asymmetricTree, 10).getValue());
        assertEquals(5, BSTreeAlgorithms.getNode(asymmetricTree, 5).getValue());
        assertEquals(3, BSTreeAlgorithms.getNode(asymmetricTree, 3).getValue());
        assertEquals(1, BSTreeAlgorithms.getNode(asymmetricTree, 1).getValue());
        assertNull(BSTreeAlgorithms.getNode(asymmetricTree, 25));
    }

    @Test
    void testGetMinNodeAsymmetric() {
        assertEquals(1, BSTreeAlgorithms.getMinNode(asymmetricTree).getValue());
        assertEquals(8, BSTreeAlgorithms.getMinNode(asymmetricTree.getLeft().getRight()).getValue());
    }

    @Test
    void testGetMaxNodeAsymmetric() {
        assertEquals(20, BSTreeAlgorithms.getMaxNode(asymmetricTree).getValue());
        assertEquals(8, BSTreeAlgorithms.getMaxNode(asymmetricTree.getLeft().getRight()).getValue());
    }

    @Test
    void testGetFloorNodeAsymmetric() {
        assertEquals(10, BSTreeAlgorithms.getFloorNode(asymmetricTree, 10).getValue());
        assertEquals(8, BSTreeAlgorithms.getFloorNode(asymmetricTree, 9).getValue());
        assertEquals(5, BSTreeAlgorithms.getFloorNode(asymmetricTree, 6).getValue());
        assertEquals(3, BSTreeAlgorithms.getFloorNode(asymmetricTree, 3).getValue());
        assertNull(BSTreeAlgorithms.getFloorNode(asymmetricTree, 0));
    }

    @Test
    void testGetCeilingNodeAsymmetric() {
        assertEquals(10, BSTreeAlgorithms.getCeilingNode(asymmetricTree, 10).getValue());
        assertEquals(15, BSTreeAlgorithms.getCeilingNode(asymmetricTree, 11).getValue());
        assertEquals(20, BSTreeAlgorithms.getCeilingNode(asymmetricTree, 16).getValue());
        assertEquals(1, BSTreeAlgorithms.getCeilingNode(asymmetricTree, 1).getValue());
        assertNull(BSTreeAlgorithms.getCeilingNode(asymmetricTree, 21));
    }

}
