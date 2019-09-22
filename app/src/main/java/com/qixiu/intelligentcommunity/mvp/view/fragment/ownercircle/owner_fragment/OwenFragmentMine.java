package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.owner_fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenMineBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.EditDynamicActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_owen.OwenOwenMineAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.my_interface.web.FragmentRefreshInterface;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class OwenFragmentMine extends BaseFragment implements XRecyclerView.LoadingListener , FragmentRefreshInterface {
    private SwipeRefreshLayout swip_owen_owen_mine;
    private XRecyclerView recyclerview_owen_owen_mine;
    private OwenOwenMineAdapter adapter;
    private int pageNo = 1, pageSize = 10;

    private LinearLayout linearlayout_photoselect_owen_mine_main;
    private ImageView iv_photoselect_owen_mine_main;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            pageNo = 1;
            refresh();
        }
    }

    @Override
    protected void onInitData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_owen_owen_mine.setLayoutManager(linearLayoutManager);
        adapter = new OwenOwenMineAdapter();
        recyclerview_owen_owen_mine.setAdapter(adapter);

        //设置加载事件
        recyclerview_owen_owen_mine.setPullRefreshEnabled(false);
//        recyclerview_owen_owen_mine.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        recyclerview_owen_owen_mine.setLoadingListener(this);
        recyclerview_owen_owen_mine.addItemDecoration(new SpaceItemsDecoration(10));

        swip_owen_owen_mine.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                refresh();
            }
        });
        iv_photoselect_owen_mine_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startIntent(getContext(), EditDynamicActivity.class);
            }
        });

    }


    private void refresh() {
        OkHttpUtils.post().url(ConstantUrl.owenOwenMineUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize + "")
                .build().execute(new StringCallback() {
                                     @Override
                                     public void onError(Call call, Exception e, int i) {
                                         swip_owen_owen_mine.setRefreshing(false);
                                         recyclerview_owen_owen_mine.loadMoreComplete();
                                     }

                                     @Override
                                     public void onResponse(String s, int i) {
                                         try {
                                             OwenOwenMineBean owenOwenMineBean = GetGson.init().fromJson(s, OwenOwenMineBean.class);
                                             List<OwenOwenMineBean.OBean.ListBean.DataBeanX.DataBean> list = new ArrayList<>();
                                             if (owenOwenMineBean.getO() != null && owenOwenMineBean.getO().getList().size() != 0) {
                                                 for (int j = 0; j < owenOwenMineBean.getO().getList().size(); j++) {
                                                     if (owenOwenMineBean.getO().getList().get(j).getData().size() != 0) {
                                                         for (int k = 0; k < owenOwenMineBean.getO().getList().get(j).getData().size(); k++) {
                                                             owenOwenMineBean.getO().getList().get(j).getData().get(k).getData().get(0).setIS_FiRST(true);
                                                             list.addAll(owenOwenMineBean.getO().getList().get(j).getData().get(k).getData());
                                                         }
                                                     }
                                                 }
                                             }
                                             if (pageNo == 1) {
                                                 if (list.size() == 0) {
                                                     List<OwenOwenMineBean.OBean.ListBean.DataBeanX.DataBean> dataBeens = new ArrayList<>();
                                                     OwenOwenMineBean.OBean.ListBean.DataBeanX.DataBean dataBean = new OwenOwenMineBean.OBean.ListBean.DataBeanX.DataBean();
                                                     dataBean.setAddtime("-1");
                                                     dataBeens.add(dataBean);
                                                     adapter.refreshData(dataBeens);
                                                 } else {
                                                     adapter.refreshData(list);
                                                 }
                                             } else {
                                                 adapter.addDatas(list);
                                                 if (list.size() == 0) {
                                                     ToastUtil.showToast(getContext(), "没有更多了");
                                                 }
                                             }
                                             if (adapter.getDatas().size() == 0) {
                                                 linearlayout_photoselect_owen_mine_main.setVisibility(View.VISIBLE);
                                             } else {
                                                 linearlayout_photoselect_owen_mine_main.setVisibility(View.GONE);
                                             }
                                         } catch (Exception e) {
                                             ToastUtil.showToast(getContext(), GetGson.parseBaseBean(s).getM());
                                         }
                                         swip_owen_owen_mine.setRefreshing(false);
                                         recyclerview_owen_owen_mine.loadMoreComplete();
                                     }
                                 }

        );


    }

    @Override
    protected void onInitView(View view) {
        iv_photoselect_owen_mine_main = (ImageView) view.findViewById(R.id.iv_photoselect_owen_mine_main);
        linearlayout_photoselect_owen_mine_main = (LinearLayout) view.findViewById(R.id.linearlayout_photoselect_owen_mine_main);
        swip_owen_owen_mine = (SwipeRefreshLayout) view.findViewById(R.id.swip_owen_owen_mine);
        recyclerview_owen_owen_mine = (XRecyclerView) view.findViewById(R.id.recyclerview_owen_owen_mine);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_owen_mine;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        refresh();
    }

    @Override
    public void fragmentRefresh() {
        pageNo=1;
        pageSize=10;
        refresh();
    }
}
