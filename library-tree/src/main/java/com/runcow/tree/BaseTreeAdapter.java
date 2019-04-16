package com.runcow.tree;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author runcow
 * 2019/3/1 15:43
 */
public abstract class BaseTreeAdapter<T> extends RecyclerView.Adapter<BaseTreeAdapter.ViewHolder<T>>{

    private LayoutInflater layoutInflater;
    private Context context;
    private List<TreeNode<T>> mData = new ArrayList<>();
    private TreeNode<T> root,selectedNode;
    private OnNodeClickListener<T> onNodeClickListener;

    public BaseTreeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public TreeNode<T> getSelectedNode() {
        return selectedNode;
    }

    public List<TreeNode<T>> getData() {
        return mData;
    }

    public void setOnNodeClickListener(OnNodeClickListener<T> onNodeClickListener) {
        this.onNodeClickListener = onNodeClickListener;
    }

    public void setSelectedNode(TreeNode<T> selectedNode) {
        if (this.selectedNode != null){
            this.selectedNode.setSelected(false);
            notifyItemChanged(mData.indexOf(this.selectedNode));
        }
        this.selectedNode = selectedNode;
        this.selectedNode.setSelected(true);
        this.selectedNode.setActive(!this.selectedNode.isActive());
        notifyItemChanged(mData.indexOf(this.selectedNode));
    }

    public void setRoot(TreeNode<T> root) {
        if (this.root != null){
            mData.clear();
        }
        this.root = root;
        if (selectedNode == null){
            selectedNode = root;
        }
        mData.addAll(TreeNodeHelper.depthFirstSearch(root));
        notifyDataSetChanged();
    }

    public void update(TreeNode<T> node){
        int position = mData.indexOf(node);
        if (node.getChildren() != null){
            boolean active = node.isActive();
            if (!active){
                //collapse
                if (node.getChildren() != null){
                    List<TreeNode<T>> diff = new ArrayList<>();
                    for (TreeNode<T> c:node.getChildren()){
                        diff.addAll(TreeNodeHelper.depthFirstSearch(c));
                    }
                    for (TreeNode<T> n : diff){
                        n.setActive(false);
                    }
                    mData.removeAll(diff);
                    notifyItemRangeRemoved(position+1,diff.size());
                }
            } else if (node.getChildren() != null && node.getChildren().size() > 0){
                for (TreeNode<T> c : node.getChildren()){
                    c.setExpanded(true);
                }
                mData.addAll(position+1,node.getChildren());
                notifyItemRangeInserted(position+1,node.getChildren().size());
            }
        }
    }


    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<>(layoutInflater.inflate(provideItemLayoutId(viewType),parent,false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        holder.setData(mData.get(position),getItemViewType(position),position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mData.get(position));
    }

    protected int getItemViewType(TreeNode<T> node){
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract int provideItemLayoutId(int viewType);

    protected abstract void setData(View itemView,TreeNode<T> node,int viewType);

    static class ViewHolder<T> extends RecyclerView.ViewHolder {

        BaseTreeAdapter<T> adapter;
        TreeNode<T> node;
        int position;
        ViewHolder(View itemView, final BaseTreeAdapter<T> baseTreeAdapter) {
            super(itemView);
            adapter = baseTreeAdapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.onNodeClickListener != null){
                        if (!adapter.onNodeClickListener.consumeNodeClick(node)){
                            adapter.setSelectedNode(node);
                            adapter.update(node);
                        }
                    } else {
                        adapter.setSelectedNode(node);
                        adapter.update(node);
                    }
                }
            });
        }

        void setData(TreeNode<T> node,int viewType,int position){
            this.node = node;
            this.position = position;
            adapter.setData(itemView,node,viewType);
        }

    }

    public interface OnNodeClickListener<T>{
        boolean consumeNodeClick(TreeNode<T> node);
    }

}
