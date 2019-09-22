package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2018/7/20.
 */

public class XrecyclerViewUtil {

    public static void setXrecyclerView(XRecyclerView recyclerView, XRecyclerView.LoadingListener listenner, Context context, boolean IS_USE_REFRESH, int declorLineHeight, RecyclerView.LayoutManager manager) {
        recyclerView.setPullRefreshEnabled(IS_USE_REFRESH);
        if (listenner != null) {
            recyclerView.setLoadingListener(listenner);
        }
        recyclerView.addItemDecoration(new SpaceItemsDecoration(declorLineHeight));
        if (manager == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(manager);
        }
    }

    public static void stopXrecyclerRefreshLoading(XRecyclerView recyclerView){
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }



    public static void refreshFictiousData(RecyclerBaseAdapter adapter){
        adapter.refreshData(creatDatas());
    }

    public static void loadMoreFictiousData(RecyclerBaseAdapter adapter){
        adapter.addDatas(creatDatas());
    }

    private static List creatDatas(){
        List<Object> datas=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            datas.add("1");
        }
        return datas;
    }

}
