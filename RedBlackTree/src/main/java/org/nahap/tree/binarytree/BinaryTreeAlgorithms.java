package org.nahap.tree.binarytree;

import java.util.LinkedList;
import java.util.Queue;


public class BinaryTreeAlgorithms {

    @FunctionalInterface
    public interface Visitor<T> {
        /**
         * "Посещение" значения
         *
         * @param value Значение, которое "посещаем"
         * @param level Уровень дерева/поддерева, на котором находится данное значение
         */
        void visit(T value, int level);
    }


    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в прямом/NLR порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor  Посетитель
     */
    public static <T> void preOrderVisit(BTree.TreeNode<T> treeNode, Visitor<T> visitor) {

        class Inner {
            void preOrderVisit(BTree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                visitor.visit(node.getValue(), level);
                preOrderVisit(node.getLeft(), visitor, level + 1);
                preOrderVisit(node.getRight(), visitor, level + 1);
            }
        }

        new Inner().preOrderVisit(treeNode, visitor, 0);
    }


    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в симметричном/поперечном/центрированном/LNR порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor  Посетитель
     */
    public static <T> void inOrderVisit(BTree.TreeNode<T> treeNode, Visitor<T> visitor) {

        class Inner {
            void inOrderVisit(BTree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                inOrderVisit(node.getLeft(), visitor, level + 1);
                visitor.visit(node.getValue(), level);
                inOrderVisit(node.getRight(), visitor, level + 1);
            }
        }

        new Inner().inOrderVisit(treeNode, visitor, 0);
    }



    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в обратном/LRN порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor  Посетитель
     */
    public static <T> void postOrderVisit(BTree.TreeNode<T> treeNode, Visitor<T> visitor) {

        class Inner {
            void postOrderVisit(BTree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                postOrderVisit(node.getLeft(), visitor, level + 1);
                postOrderVisit(node.getRight(), visitor, level + 1);
                visitor.visit(node.getValue(), level);
            }
        }

        new Inner().postOrderVisit(treeNode, visitor, 0);
    }



    /**
     * Класс для хранения узла дерева вместе с его уровнем, нужен для метода
     * {@link #byLevelVisit(BTree.TreeNode, Visitor)}
     *
     * @param <T>
     */
    private static class QueueItem<T> {
        public BTree.TreeNode<T> node;
        public int level;

        public QueueItem(BTree.TreeNode<T> node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * Обход поддерева с вершиной в данном узле "посетителем" по уровням (обход в ширину)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor  Посетитель
     */
    public static <T> void byLevelVisit(BTree.TreeNode<T> treeNode, Visitor<T> visitor) {
        Queue<QueueItem<T>> queue = new LinkedList<>();
        queue.add(new QueueItem<>(treeNode, 0));
        while (!queue.isEmpty()) {
            QueueItem<T> item = queue.poll();
            if (item.node.getLeft() != null) {
                queue.add(new QueueItem<>(item.node.getLeft(), item.level + 1));
            }
            if (item.node.getRight() != null) {
                queue.add(new QueueItem<>(item.node.getRight(), item.level + 1));
            }
            visitor.visit(item.node.getValue(), item.level);
        }
    }

}
