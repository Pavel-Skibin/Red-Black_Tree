package org.nahap.tree.rbtree;

import org.nahap.tree.binarytree.BTree;
import org.nahap.tree.bstree.BSTree;
import org.nahap.tree.bstree.BSTreeUtils;

public class RedBlackTree<T extends Comparable<? super T>> implements BSTree<T> {

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    public class RBTreeNode implements BTree.TreeNode<T> {

        public T value;
        public boolean color;
        public RBTreeNode left;
        public RBTreeNode right;
        public RBTreeNode parent;

        public RBTreeNode(T value, boolean color, RBTreeNode left, RBTreeNode right, RBTreeNode parent) {
            this.value = value;
            this.color = color;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public RBTreeNode(T value, RBTreeNode parent) {
            this(value, BLACK, null, null, parent);
        }

        public RBTreeNode(T value) {
            this(value, null);
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

    private RBTreeNode root = null;
    private int size = 0;

    @Override
    public BTree.TreeNode<T> getRoot() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T put(T value) {
        if (root == null) {
            setRoot(new RBTreeNode(value));
            size++;
            return null;
        }

        RBTreeNode node = root;
        while (true) {
            int comparison = value.compareTo(node.value);
            if (comparison == 0) {
                T oldValue = node.value;
                node.value = value;
                return oldValue;
            }

            if (comparison < 0) {
                if (node.left == null) {
                    setLeft(node, new RBTreeNode(value));
                    size++;
                    fixAfterInsertion(node.left);
                    return null;
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    setRight(node, new RBTreeNode(value));
                    size++;
                    fixAfterInsertion(node.right);
                    return null;
                }
                node = node.right;
            }
        }
    }

    @Override
    public T remove(T value) {
        RBTreeNode node = (RBTreeNode) findNode(value);
        if (node == null) {
            return null;
        }

        T oldValue = node.value;
        if (node.left != null && node.right != null) {
            RBTreeNode successor = (RBTreeNode) BSTreeUtils.findMinNode(node.right);
            node.value = successor.value;
            node = successor;
        }

        RBTreeNode child = (node.left != null) ? node.left : node.right;
        if (child != null) {
            if (node == root) {
                setRoot(child);
                root.color = BLACK;
            } else if (node.parent.left == node) {
                setLeft(node.parent, child);
            } else {
                setRight(node.parent, child);
            }

            if (node.color == BLACK) {
                fixAfterDeletion(child);
            }
        } else if (node == root) {
            root = null;
        } else {
            if (node.color == BLACK) {
                fixAfterDeletion(node);
            }
            removeFromParent(node);
        }

        size--;
        return oldValue;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private void fixAfterInsertion(RBTreeNode node) {
        node.color = RED;

        while (node != null && node != root && node.parent.color == RED) {
            if (node.parent == leftOf(node.parent.parent)) {
                RBTreeNode uncle = rightOf(node.parent.parent);
                if (uncle != null && uncle.color == RED) {
                    setColor(node.parent, BLACK);
                    setColor(uncle, BLACK);
                    setColor(node.parent.parent, RED);
                    node = node.parent.parent;
                } else {
                    if (node == rightOf(node.parent)) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    setColor(node.parent, BLACK);
                    setColor(node.parent.parent, RED);
                    rightRotate(node.parent.parent);
                }
            } else {
                RBTreeNode uncle = leftOf(node.parent.parent);
                if (uncle != null && uncle.color == RED) {
                    setColor(node.parent, BLACK);
                    setColor(uncle, BLACK);
                    setColor(node.parent.parent, RED);
                    node = node.parent.parent;
                } else {
                    if (node == leftOf(node.parent)) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    setColor(node.parent, BLACK);
                    setColor(node.parent.parent, RED);
                    leftRotate(node.parent.parent);
                }
            }
        }

        setColor(root, BLACK);
    }

    private void fixAfterDeletion(RBTreeNode node) {
        while (node != root && isBlack(node)) {
            if (node == leftOf(parentOf(node))) {
                RBTreeNode sibling = rightOf(parentOf(node));
                if (isRed(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(node), RED);
                    leftRotate(parentOf(node));
                    sibling = rightOf(parentOf(node));
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, RED);
                    node = parentOf(node);
                } else {
                    if (isBlack(rightOf(sibling))) {
                        setColor(leftOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rightRotate(sibling);
                        sibling = rightOf(parentOf(node));
                    }
                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(rightOf(sibling), BLACK);
                    leftRotate(parentOf(node));
                    node = root;
                }
            } else {
                RBTreeNode sibling = leftOf(parentOf(node));
                if (isRed(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(node), RED);
                    rightRotate(parentOf(node));
                    sibling = leftOf(parentOf(node));
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, RED);
                    node = parentOf(node);
                } else {
                    if (isBlack(leftOf(sibling))) {
                        setColor(rightOf(sibling), BLACK);
                        setColor(sibling, RED);
                        leftRotate(sibling);
                        sibling = leftOf(parentOf(node));
                    }
                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(leftOf(sibling), BLACK);
                    rightRotate(parentOf(node));
                    node = root;
                }
            }
        }
        setColor(node, BLACK);
    }

    private void leftRotate(RBTreeNode node) {
        if (node.right == null) return;

        RBTreeNode right = node.right;
        setRight(node, right.left);
        if (node.parent == null) {
            setRoot(right);
        } else if (node.parent.left == node) {
            setLeft(node.parent, right);
        } else {
            setRight(node.parent, right);
        }
        setLeft(right, node);
    }

    private void rightRotate(RBTreeNode node) {
        if (node.left == null) return;

        RBTreeNode left = node.left;
        setLeft(node, left.right);
        if (node.parent == null) {
            setRoot(left);
        } else if (node.parent.left == node) {
            setLeft(node.parent, left);
        } else {
            setRight(node.parent, left);
        }
        setRight(left, node);
    }

    private void removeFromParent(RBTreeNode node) {
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = null;
            } else if (node.parent.right == node) {
                node.parent.right = null;
            }
            node.parent = null;
        }
    }

    private boolean colorOf(RBTreeNode node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isRed(RBTreeNode node) {
        return colorOf(node) == RED;
    }

    private boolean isBlack(RBTreeNode node) {
        return colorOf(node) == BLACK;
    }

    private void setColor(RBTreeNode node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    private void setLeft(RBTreeNode node, RBTreeNode left) {
        if (node != null) {
            node.left = left;
            if (left != null) {
                left.parent = node;
            }
        }
    }

    private void setRight(RBTreeNode node, RBTreeNode right) {
        if (node != null) {
            node.right = right;
            if (right != null) {
                right.parent = node;
            }
        }
    }

    private void setRoot(RBTreeNode node) {
        root = node;
        if (node != null) {
            node.parent = null;
        }
    }

    private RBTreeNode parentOf(RBTreeNode node) {
        return node == null ? null : node.parent;
    }

    private RBTreeNode grandparentOf(RBTreeNode node) {
        return (node == null || node.parent == null) ? null : node.parent.parent;
    }

    private RBTreeNode siblingOf(RBTreeNode node) {
        return (node == null || node.parent == null)
                ? null
                : (node == node.parent.left)
                ? node.parent.right : node.parent.left;
    }

    private RBTreeNode leftOf(RBTreeNode node) {
        return node == null ? null : node.left;
    }

    private RBTreeNode rightOf(RBTreeNode node) {
        return node == null ? null : node.right;
    }

    private int getHeight(RBTreeNode node) {
        return (node == null) ? -1 : Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public int getHeight() {
        return getHeight(root);
    }
}
