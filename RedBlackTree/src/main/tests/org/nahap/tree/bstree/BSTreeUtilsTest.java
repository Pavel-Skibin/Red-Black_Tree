package org.nahap.tree.bstree;

import org.junit.jupiter.api.Test;
import org.nahap.tree.binarytree.BTree;

import static org.junit.jupiter.api.Assertions.*;

class BSTreeUtilsTest {

    static class TestTreeNode<T> implements BTree.TreeNode<T> {
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
        public BTree.TreeNode<T> getLeft() {
            return left;
        }

        @Override
        public BTree.TreeNode<T> getRight() {
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
    void testFindNode() {
        assertEquals(10, BSTreeUtils.findNode(sampleTree, 10).getValue());
        assertEquals(5, BSTreeUtils.findNode(sampleTree, 5).getValue());
        assertNull(BSTreeUtils.findNode(sampleTree, 25));
    }

    @Test
    void testFindMinNode() {
        assertEquals(2, BSTreeUtils.findMinNode(sampleTree).getValue());
        assertEquals(8, BSTreeUtils.findMinNode(sampleTree.getLeft().getRight()).getValue());
    }

    @Test
    void testFindMaxNode() {
        assertEquals(20, BSTreeUtils.findMaxNode(sampleTree).getValue());
        assertEquals(12, BSTreeUtils.findMaxNode(sampleTree.getRight().getLeft()).getValue());
    }

    @Test
    void testFindFloorNode() {
        assertEquals(10, BSTreeUtils.findFloorNode(sampleTree, 10).getValue());
        assertEquals(8, BSTreeUtils.findFloorNode(sampleTree, 9).getValue());
        assertNull(BSTreeUtils.findFloorNode(sampleTree, 1));
    }

    @Test
    void testFindCeilingNode() {
        assertEquals(10, BSTreeUtils.findCeilingNode(sampleTree, 10).getValue());
        assertEquals(12, BSTreeUtils.findCeilingNode(sampleTree, 11).getValue());
        assertNull(BSTreeUtils.findCeilingNode(sampleTree, 25));
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
    void testFindNodeAsymmetric() {
        assertEquals(10, BSTreeUtils.findNode(asymmetricTree, 10).getValue());
        assertEquals(5, BSTreeUtils.findNode(asymmetricTree, 5).getValue());
        assertEquals(3, BSTreeUtils.findNode(asymmetricTree, 3).getValue());
        assertEquals(1, BSTreeUtils.findNode(asymmetricTree, 1).getValue());
        assertNull(BSTreeUtils.findNode(asymmetricTree, 25));
    }

    @Test
    void testFindMinNodeAsymmetric() {
        assertEquals(1, BSTreeUtils.findMinNode(asymmetricTree).getValue());
        assertEquals(8, BSTreeUtils.findMinNode(asymmetricTree.getLeft().getRight()).getValue());
    }

    @Test
    void testFindMaxNodeAsymmetric() {
        assertEquals(20, BSTreeUtils.findMaxNode(asymmetricTree).getValue());
        assertEquals(8, BSTreeUtils.findMaxNode(asymmetricTree.getLeft().getRight()).getValue());
    }

    @Test
    void testFindFloorNodeAsymmetric() {
        assertEquals(10, BSTreeUtils.findFloorNode(asymmetricTree, 10).getValue());
        assertEquals(8, BSTreeUtils.findFloorNode(asymmetricTree, 9).getValue());
        assertEquals(5, BSTreeUtils.findFloorNode(asymmetricTree, 6).getValue());
        assertEquals(3, BSTreeUtils.findFloorNode(asymmetricTree, 3).getValue());
        assertNull(BSTreeUtils.findFloorNode(asymmetricTree, 0));
    }

    @Test
    void testFindCeilingNodeAsymmetric() {
        assertEquals(10, BSTreeUtils.findCeilingNode(asymmetricTree, 10).getValue());
        assertEquals(15, BSTreeUtils.findCeilingNode(asymmetricTree, 11).getValue());
        assertEquals(20, BSTreeUtils.findCeilingNode(asymmetricTree, 16).getValue());
        assertEquals(1, BSTreeUtils.findCeilingNode(asymmetricTree, 1).getValue());
        assertNull(BSTreeUtils.findCeilingNode(asymmetricTree, 21));
    }

}
