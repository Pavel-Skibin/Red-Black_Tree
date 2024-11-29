package org.nahap.tree.binarytree;

import java.awt.Color;
import java.util.Iterator;

/**
 * Интерфейс для двоичного дерева с реализацияей по умолчанию различных обходов
 * дерева
 *
 * @param <T>
 */
public interface BinaryTree<T>  {

    /**
     * Интерфейс для описания узла двоичного дерева
     *
     * @param <T>
     */
    interface TreeNode<T> {

        /**
         * @return Значение в узле дерева
         */
        T getValue();

        /**
         * @return Левое поддерево
         */
        default TreeNode<T> getLeft() {
            return null;
        }

        /**
         * @return Правое поддерево
         */
        default TreeNode<T> getRight() {
            return null;
        }


    }

    /**
     * @return Корень (вершина) дерева
     */
    TreeNode<T> getRoot();


}
