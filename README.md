# treeview

基于RecyclerView实现的treeview。

# start

```
dependencies {
    implementation 'com.runcow:treeview:1.1.0'
}

```

# usage

```
BaseTreeAdapter<T> adapter = new BaseTreeAdapter<T>(Context context){
    @Override
    protected int provideItemLayoutId(int viewType) {
    
    }
    
    @Override
    protected void setData(View itemView, TreeNode<T> node, int viewType) {
    
    }
}

RecyclerView.setAdapter(adapter);
```

```
TreeNode<T> root = new TreeNode<>();

//root.addCihld()...

root.setExpanded(true);

adapter.setRoot(root);
```
 
more:
 
 ```
 
 adapter.setOnNodeClickListener()
 
 adapter.setOnLoadFinishListener()
 
 ```

