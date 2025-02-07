package org.nahap.tree.binarytree;

public interface BTree<T>  {


    interface TreeNode<T> {

        T getValue();

        default TreeNode<T> getLeft() {
            return null;
        }

        default TreeNode<T> getRight() {
            return null;
        }


    }

    TreeNode<T> getRoot();


}
