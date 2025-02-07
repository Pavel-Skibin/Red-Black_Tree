package org.nahap.tree.rbtree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    void testPutSingleElement() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        assertNotNull(tree.getRoot());
        assertEquals(10, tree.getRoot().getValue());
        assertEquals(1, tree.size());
    }

    @Test
    void testPutMultipleElements() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        tree.put(20);
        tree.put(5);

        assertEquals(3, tree.size());
        assertEquals(10, tree.getRoot().getValue());
        assertNotNull(tree.getRoot().getLeft());
        assertNotNull(tree.getRoot().getRight());
    }



    @Test
    void testRedBlackPropertiesAfterInsert() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        tree.put(20);
        tree.put(5);
        tree.put(15);
        tree.put(25);
        tree.put(1);
        tree.put(8);
        tree.put(18);
        tree.put(30);


        assertFalse(((RedBlackTree<Integer>.RBTreeNode) tree.getRoot()).color);


        assertTrue(isValidRedBlackTree(tree));


        //                  10 (BLACK)
        //               /             \
        //          5 (BLACK)       20 (BLACK)
        //        /      \         /        \
        //   1 (RED)   8 (RED)  15 (BLACK) 25 (BLACK)
        //                             \           \
        //                          18 (RED)    30 (RED)
        //

    }


    @Test
    void testRemoveElement() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        tree.put(20);
        tree.put(5);

        assertEquals(3, tree.size());
        Integer removed = tree.remove(20);
        assertEquals(20, removed);
        assertEquals(2, tree.size());
        assertNull(tree.getRoot().getRight());
    }

    @Test
    void testRemoveRoot() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        tree.put(20);
        tree.put(5);

        assertEquals(3, tree.size());
        Integer removed = tree.remove(10);
        assertEquals(10, removed);
        assertEquals(2, tree.size());
    }

    @Test
    void testClearTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        tree.put(20);
        tree.put(5);

        assertEquals(3, tree.size());
        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test
    void testTreeHeight() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(10);
        tree.put(20);
        tree.put(5);
        tree.put(15);

        assertEquals(2, tree.getHeight()); // Проверяем высоту
    }

    // Проверка корректности свойств красно-черного дерева
    private boolean isValidRedBlackTree(RedBlackTree<Integer> tree) {
        return validateNode((RedBlackTree<Integer>.RBTreeNode) tree.getRoot());
    }

    private boolean validateNode(RedBlackTree<Integer>.RBTreeNode node) {
        if (node == null) {
            return true;
        }

        // У красного узла не должно быть красных потомков
        if (node.color == RedBlackTree.RED) {
            if ((node.left != null && node.left.color == RedBlackTree.RED) ||
                    (node.right != null && node.right.color == RedBlackTree.RED)) {
                return false;
            }
        }

        // Рекурсивно проверяем левое и правое поддерево
        return validateNode(node.left) && validateNode(node.right);
    }
}
