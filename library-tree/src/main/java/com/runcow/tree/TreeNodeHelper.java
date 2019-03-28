package com.runcow.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author runcow
 * 2019/2/26
 */
public class TreeNodeHelper {

    public static <T> List<TreeNode<T>> depthFirstSearch(TreeNode<T> node){
        return depthFirstSearch(new ArrayList<TreeNode<T>>(),node);
    }

    private static <T> List<TreeNode<T>> depthFirstSearch(List<TreeNode<T>> list,TreeNode<T> node){
        if (node.isExpanded()){
            list.add(node);
        }
        if (node.getChildren() != null){
            for (TreeNode<T> n : node.getChildren()){
                depthFirstSearch(list,n);
            }
        }
        return list;
    }

    public static <T> void expendNode(TreeNode<T> node,boolean expend) {
        if (node != null && node.getChildren() != null){
            for (TreeNode<T> t:node.getChildren()) {
                t.setExpanded(expend);
            }
        }
    }


    public static <T> TreeNode<T> getRoot(TreeNode<T> node) {
        TreeNode<T> root = node;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    public static void main(String[] args) {
    }
}
