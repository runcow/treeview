package com.runcow.treeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runcow.tree.BaseTreeAdapter;
import com.runcow.tree.TreeNode;

/**
 * @author runcow
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private int nodeMargin, arrowMargin, textMargin;
    private BaseTreeAdapter<OrgBean> adapter;
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_PROVINCE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initView();
        initData();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new BaseTreeAdapter<OrgBean>(this) {

            @Override
            protected int provideItemLayoutId(int viewType) {
                switch (viewType) {
                    case VIEW_TYPE_PROVINCE:
                        return R.layout.item_organization_province;
                    case VIEW_TYPE_NORMAL:
                        return R.layout.item_organization;
                    default:
                        return R.layout.item_organization;
                }

            }

            @Override
            protected void setData(View itemView, TreeNode<OrgBean> node, int viewType) {
                switch (viewType) {
                    case VIEW_TYPE_PROVINCE:
                        TextView tvProvince = itemView.findViewById(R.id.tvProvince);
                        ImageView ivArrowProvince = itemView.findViewById(R.id.ivArrowProvince);
                        tvProvince.setText(node.getValue().getName() + "(expend=" + node.isExpanded() + ",select=" + node.isSelected() + ")");
                        if (node.isActive()) {
                            ivArrowProvince.setImageResource(R.drawable.icon_organization_up);
                        } else {
                            ivArrowProvince.setImageResource(R.drawable.icon_organization_down);
                        }
                        if (node.isSelected()) {
                            tvProvince.setTextColor(getResources().getColor(R.color.colorAccent));
                            tvProvince.setSelected(true);
                        } else {
                            tvProvince.setTextColor(getResources().getColor(R.color.colorNormal));
                            tvProvince.setSelected(false);
                        }
                        //手动计算paddingLeft的值 实现分层效果
                        int leftP = node.getLevel() * getNodeMargin() + getArrowMargin();
                        itemView.setPadding(leftP, 0, getTextMargin(), 0);
                        break;
                    case VIEW_TYPE_NORMAL:
                        TextView tvName = itemView.findViewById(R.id.tvName);
                        ImageView ivArrow = itemView.findViewById(R.id.ivArrow);
                        tvName.setText(node.getValue().getName() + "(expend=" + node.isExpanded() + ",select=" + node.isSelected() + ")");
                        if (node.isActive()) {
                            ivArrow.setImageResource(R.drawable.icon_organization_up);
                        } else {
                            ivArrow.setImageResource(R.drawable.icon_organization_down);
                        }
                        if (node.isSelected()) {
                            tvName.setTextColor(getResources().getColor(R.color.colorAccent));
                            tvName.setSelected(true);
                        } else {
                            tvName.setTextColor(getResources().getColor(R.color.colorNormal));
                            tvName.setSelected(false);
                        }
                        //手动计算paddingLeft的值 实现分层效果
                        int left = node.getLevel() * getNodeMargin() + getArrowMargin();
                        itemView.setPadding(left, 0, getTextMargin(), 0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            protected int getItemViewType(TreeNode<OrgBean> node) {
                if (node.getValue().isProvince()) {
                    return VIEW_TYPE_PROVINCE;
                }
                return VIEW_TYPE_NORMAL;
            }
        });
    }

    protected void initData() {

        TreeNode<OrgBean> china = new TreeNode<>(new OrgBean(0, "中国"));
        TreeNode<OrgBean> hebei = new TreeNode<>(new OrgBean(0, "河北省"));
        TreeNode<OrgBean> jilin = new TreeNode<>(new OrgBean(0, "吉林省"));
        TreeNode<OrgBean> liaoning = new TreeNode<>(new OrgBean(0, "辽宁省"));
        TreeNode<OrgBean> zhejiang = new TreeNode<>(new OrgBean(0, "浙江省"));
        TreeNode<OrgBean> xx = new TreeNode<>(new OrgBean(0, "这个省名字很长这个省名字很长这个省名字很长"));
        hebei.getValue().setProvince(true);
        jilin.getValue().setProvince(true);
        liaoning.getValue().setProvince(true);
        zhejiang.getValue().setProvince(true);
        xx.getValue().setProvince(true);
        china.addChild(hebei);
        china.addChild(jilin);
        china.addChild(liaoning);
        china.addChild(zhejiang);
        china.addChild(xx);

        TreeNode<OrgBean> shijiazhuang = new TreeNode<>(new OrgBean(0, "石家庄市"));
        TreeNode<OrgBean> tangshan = new TreeNode<>(new OrgBean(0, "唐山市"));
        TreeNode<OrgBean> qinhuangdao = new TreeNode<>(new OrgBean(0, "秦皇岛市"));
        hebei.addChild(shijiazhuang);
        hebei.addChild(tangshan);
        hebei.addChild(qinhuangdao);

        TreeNode<OrgBean> shenyang = new TreeNode<>(new OrgBean(0, "沈阳市"));
        TreeNode<OrgBean> dalian = new TreeNode<>(new OrgBean(0, "大连市"));
        TreeNode<OrgBean> anshan = new TreeNode<>(new OrgBean(0, "鞍山市"));
        liaoning.addChild(shenyang);
        liaoning.addChild(dalian);
        liaoning.addChild(anshan);

        TreeNode<OrgBean> changchun = new TreeNode<>(new OrgBean(0, "长春市"));
        TreeNode<OrgBean> jilinshi = new TreeNode<>(new OrgBean(0, "吉林市"));
        TreeNode<OrgBean> siping = new TreeNode<>(new OrgBean(0, "四平市"));
        jilin.addChild(changchun);
        jilin.addChild(jilinshi);
        jilin.addChild(siping);

        TreeNode<OrgBean> hangzhou = new TreeNode<>(new OrgBean(0, "杭州市"));
        TreeNode<OrgBean> ningbo = new TreeNode<>(new OrgBean(0, "宁波市"));
        TreeNode<OrgBean> shaoxing = new TreeNode<>(new OrgBean(0, "绍兴市"));
        TreeNode<OrgBean> jinhua = new TreeNode<>(new OrgBean(0, "金华市"));
        TreeNode<OrgBean> quzhou = new TreeNode<>(new OrgBean(0, "衢州市"));
        zhejiang.addChild(hangzhou);
        zhejiang.addChild(ningbo);
        zhejiang.addChild(shaoxing);
        zhejiang.addChild(jinhua);
        zhejiang.addChild(quzhou);

        TreeNode<OrgBean> kaihua = new TreeNode<>(new OrgBean(0, "开化县"));
        TreeNode<OrgBean> changshan = new TreeNode<>(new OrgBean(0, "常山县"));
        TreeNode<OrgBean> longyou = new TreeNode<>(new OrgBean(0, "龙游县"));
        quzhou.addChild(kaihua);
        quzhou.addChild(changshan);
        quzhou.addChild(longyou);

        TreeNode<OrgBean> huabu = new TreeNode<>(new OrgBean(0, "华埠镇"));
        TreeNode<OrgBean> majin = new TreeNode<>(new OrgBean(0, "马金镇"));
        kaihua.addChild(huabu);
        kaihua.addChild(majin);

        TreeNode<OrgBean> mmcun = new TreeNode<>(new OrgBean(0, "某某村"));
        huabu.addChild(mmcun);

        china.setExpanded(true);
        adapter.setRoot(china);
        adapter.setAsyncMode(true);
        adapter.setOnNodeClickListener(new BaseTreeAdapter.OnNodeClickListener<OrgBean>() {
            @Override
            public void onNodeClick(TreeNode<OrgBean> node) {
                showLoading();
            }
        });
        adapter.setOnLoadFinishListener(new BaseTreeAdapter.OnLoadFinishListener() {
            @Override
            public void onLoadFinish() {
                //recyclerView.postDelayed(new Runnable() {
                //    @Override
                //    public void run() {
                //        hideLoading();
                //    }
                //},1000);
                hideLoading();
            }
        });
        adapter.update();
    }

    private void showLoading() {

    }

    private void hideLoading() {

    }

    public int getNodeMargin() {
        if (nodeMargin == 0) {
            nodeMargin = dp2px(18);
        }
        return nodeMargin;
    }

    public int getArrowMargin() {
        if (arrowMargin == 0) {
            arrowMargin = dp2px(15);
        }
        return arrowMargin;
    }

    public int getTextMargin() {
        if (textMargin == 0) {
            textMargin = dp2px(10);
        }
        return textMargin;
    }

    public int dp2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
