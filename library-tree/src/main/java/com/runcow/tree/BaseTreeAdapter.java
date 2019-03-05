package com.runcow.tree;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author runcow
 * @time 2019/3/1 15:43
 */
public abstract class BaseTreeAdapter<T> extends RecyclerView.Adapter<BaseTreeAdapter.ViewHolder<T>>{

    private final static int ASYNC_WHAT = 1;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<TreeNode<T>> mData;
    private TreeNode<T> root,selectedNode;
    private OnNodeClickListener<T> onNodeClickListener;
    private OnLoadFinishListener onLoadFinishListener;
    private ExecutorService singleThreadExecutor;
    private MyHandler mHandler;
    private boolean asyncMode = false;

    public BaseTreeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public TreeNode<T> getSelectedNode() {
        return selectedNode;
    }

    private void setSelectedNode(TreeNode<T> selectedNode) {
        if (this.selectedNode != null){
            this.selectedNode.setSelected(false);
        }
        this.selectedNode = selectedNode;
        this.selectedNode.setSelected(true);
        this.selectedNode.setActive(!this.selectedNode.isActive());
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public boolean isAsyncMode() {
        return asyncMode;
    }

    public void setAsyncMode(boolean asyncMode) {
        this.asyncMode = asyncMode;
    }

    public void update(){
        if (!asyncMode){
            updateSync();
        } else {
            updateAsync();
        }
    }

    private void updateSync(){
        if (selectedNode == null){
            selectedNode = root;
        }
        mData = TreeNodeHelper.depthFirstSearch(root);
        notifyDataSetChanged();
    }

    private void updateAsync(){
        if (selectedNode == null){
            selectedNode = root;
        }
        if (singleThreadExecutor == null){
            singleThreadExecutor = Executors.newSingleThreadExecutor();
            mHandler = new MyHandler(this);
        }
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mData = TreeNodeHelper.depthFirstSearch(root);
                mHandler.sendEmptyMessage(ASYNC_WHAT);
            }
        });
    }

    public void setOnNodeClickListener(OnNodeClickListener<T> onNodeClickListener) {
        this.onNodeClickListener = onNodeClickListener;
    }

    public void setOnLoadFinishListener(OnLoadFinishListener onLoadFinishListener) {
        this.onLoadFinishListener = onLoadFinishListener;
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
                        adapter.onNodeClickListener.onNodeClick(node);
                    }
                    TreeNodeHelper.expend(node);
                    adapter.setSelectedNode(node);
                    adapter.update();
                }
            });
        }

        void setData(TreeNode<T> node,int viewType,int position){
            this.node = node;
            this.position = position;
            adapter.setData(itemView,node,viewType);
        }

    }

    private static class MyHandler extends Handler {

        private WeakReference<BaseTreeAdapter> adapterWeakReference;

        MyHandler(BaseTreeAdapter adapter){
            adapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ASYNC_WHAT){
                final BaseTreeAdapter adapter = adapterWeakReference.get();
                if(adapter != null){
                    adapter.notifyDataSetChanged();
                    if (adapter.onLoadFinishListener != null){
                        adapter.onLoadFinishListener.onLoadFinish();
                    }
                }
            }
        }
    }

    public interface OnNodeClickListener<T>{
        void onNodeClick(TreeNode<T> node);
    }

    public interface OnLoadFinishListener {
        void onLoadFinish();
    }
}
