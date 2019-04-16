# treeview

基于RecyclerView实现的treeview。

# start
[ ![Download](https://api.bintray.com/packages/runcow/AndroidTreeView/treeview/images/download.svg?version=1.1.0) ](https://bintray.com/runcow/AndroidTreeView/treeview/1.1.0/link)

```
dependencies {
    implementation 'com.runcow:treeview:1.1.2'
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
 //set bean behaviors before update
 adapter.setOnNodeClickListener()
 
 ```

