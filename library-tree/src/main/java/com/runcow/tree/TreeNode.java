package com.runcow.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author runcow
 * 2019/2/26
 */
public class TreeNode<T> {
    private TreeNode<T> parent;
    private boolean selected = false;
    private boolean selectable = true;
    private List<TreeNode<T>> children = null;
    private T value;
    private boolean expanded = false;
    private boolean active = false;
    private int level;

    public TreeNode() {
    }

    public TreeNode(T value) {
        this.value = value;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<T> child){
        if (children == null){
            children = new ArrayList<>();
        }
        child.setParent(TreeNode.this);
        child.setLevel(getLevel() + 1);
        children.add(child);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
