package com.qixiu.intelligentcommunity.mvp.view.activity.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;


/**
 * Created by HuiHeZe on 2017/8/25.
 */

public abstract class XrecyclerViewActivity extends NewTitleActivity implements XRecyclerView.LoadingListener {
    private SwipeRefreshLayout swiprefresh_xrecyclerView;
    private XRecyclerView recyclerview_xrecyclerView;
    public MyAdapter adapter;
    public int pageNo = 1, pageSize = 10;
    public RelativeLayout relativeLayout_xrecyclerview_father;
   private ImageView imageView_nothing;
    @Override
    protected void onInitData() {
        newInItData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_xrecyclerview;
    }

    private int dataSizeBeforeLoading=0;

    @Override
    protected void onInitViewNew() {
        adapter = new MyAdapter();
        imageView_nothing= (ImageView) findViewById(R.id.imageView_nothing);
        relativeLayout_xrecyclerview_father= (RelativeLayout) findViewById(R.id.relativeLayout_xrecyclerview_father);
        recyclerview_xrecyclerView = (XRecyclerView) findViewById(R.id.recyclerview_xrecyclerView);
        CommonUtils.setXrecyclerView(recyclerview_xrecyclerView, this,this, false, 1, null);
        recyclerview_xrecyclerView.setAdapter(adapter);
        swiprefresh_xrecyclerView = (SwipeRefreshLayout) findViewById(R.id.swiprefresh_xrecyclerView);
        swiprefresh_xrecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        newInItView();
    }

    @Override
    public void onClick(View v) {
        newOnClick(v);
    }

    public abstract void newOnClick(View v);

    public abstract void newInItView();

    public abstract void newInItData();

    public abstract int getItemLayoutId();

    public abstract void setItemView(View itemView);

    public abstract void getNetData();

    public abstract void myBindHolder(Object obj, int position);


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getNetData();
    }

    public void refresh() {
        pageNo = 1;
        getNetData();
    }


    public class MyAdapter extends RecyclerBaseAdapter {
        @Override
        public int getLayoutId() {
            return getItemLayoutId();
        }

        @Override
        public MyHolder createViewHolder(View itemView, Context context, int viewType) {
            return new MyHolder(itemView, context, this);
        }
    }


    public class MyHolder extends RecyclerBaseHolder<Object> {
        public MyHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
            super(itemView, context, adapter);
            setItemView(itemView);
        }

        @Override
        public void bindHolder(int position) {
            myBindHolder(mData, position);
        }
    }

    public void refreshLoadStop() {
        recyclerview_xrecyclerView.loadMoreComplete();
        swiprefresh_xrecyclerView.setRefreshing(false);
    }


    public void setBackVisblity(){
        if (adapter.getDatas().size() == 0) {
            if (pageNo == 1) {
                imageView_nothing.setVisibility(View.VISIBLE);
            }
        }else {
            //隐藏空白页
            imageView_nothing.setVisibility(View.GONE);
        }
        if(adapter.getDatas().size()==dataSizeBeforeLoading&&pageNo!=1){
            ToastUtil.toast(R.string.nomore_loading);
            dataSizeBeforeLoading=adapter.getDatas().size();
        }
    }
}
