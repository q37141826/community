package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.owner_fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenCircleAllBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenAllAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.my_interface.web.FragmentRefreshInterface;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/6/30.  业主圈全部
 */

public class OwenFragmentAll extends BaseFragment implements XRecyclerView.LoadingListener, OwenOwenAllAdapter.MyRefreshListener, FragmentRefreshInterface {
    private SwipeRefreshLayout swiprefresh_owe_all;
    private XRecyclerView recyclerview_owen_all;
    private int pageNo = 1, pageSize = 10;
    private OwenOwenAllAdapter adapter;
    private ImageView imageView_nothing;

    //全局刷新还是局部刷新
    private int type = 1;
    private int currentposition;
    List<OwenCircleAllBean.OBean.ListBean> list = new ArrayList<>();

    @Override
    protected void onInitData() {
        adapter = new OwenOwenAllAdapter();
        recyclerview_owen_all.setAdapter(adapter);
        swiprefresh_owe_all.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                type = 1;
                currentposition = 0;
                pageSize = 10;
                getData();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerItemListener<OwenCircleAllBean.OBean.ListBean>() {
            @Override
            public void onItemClick(View v, OwenCircleAllBean.OBean.ListBean data) {
                //// TODO: 2017/7/2 0002 跳转到业主圈详情界面 
            }
        });
        adapter.setRefreshListener(this);
        getData();
    }


    @Override
    protected void onInitView(View view) {
        imageView_nothing = (ImageView) view.findViewById(R.id.imageView_nothing);
        recyclerview_owen_all = (XRecyclerView) view.findViewById(R.id.recyclerview_owen_all);
        swiprefresh_owe_all = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh_owe_all);

        //设置加载事件
        recyclerview_owen_all.setPullRefreshEnabled(false);
//        recyclerview_owen_all.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        recyclerview_owen_all.setLoadingListener(this);
        recyclerview_owen_all.addItemDecoration(new SpaceItemsDecoration(1));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_owen_all.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_owen_all;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
//        //如果是改变了什么东西而刷新的话，那么从这条开始加载
//        if (pageNo == 1 && adapter.getDatas().size() > 10) {
//            pageNo = adapter.getDatas().size() / 10;
//        }
        pageNo++;
        getData();
        Log.e("sss", "加载起作用了");
    }

    public void getData() {
        OkHttpUtils.post().url(ConstantUrl.owenAllUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                swiprefresh_owe_all.setRefreshing(false);
                recyclerview_owen_all.loadMoreComplete();
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    OwenCircleAllBean bean = GetGson.init().fromJson(s, OwenCircleAllBean.class);
                    if (pageNo == 1) {
                        adapter.refreshData(bean.getO().getList());
                        pageSize = 10;
                    } else {
                        adapter.addDatas(bean.getO().getList());
                        if (bean.getO().getList().size() == 0) {
                            ToastUtil.showToast(getContext(), "没有更多了");
                        }
                    }
                    //没有数据的时候显示空白页
                    if (adapter.getDatas().size() == 0) {
                        imageView_nothing.setVisibility(View.VISIBLE);
                    } else {
                        imageView_nothing.setVisibility(View.GONE);
                    }
                    //最后一条莫名其妙的会多出一个，还不知道为什么，把它移除掉
                    if (adapter.getDatas().get(adapter.getDatas().size() - 1).getId() == adapter.getDatas().get(adapter.getDatas().size() - 2).getId()) {
                        adapter.getDatas().remove(adapter.getDatas().get(adapter.getDatas().size() - 1));
                        adapter.notifyDataSetChanged();
                        Log.e("sssss", "postion1" + adapter.getDatas().get(adapter.getDatas().size() - 1) + "---" + "position2" + (adapter.getDatas().size() - 1) + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swiprefresh_owe_all.setRefreshing(false);
                recyclerview_owen_all.loadMoreComplete();
            }
        });
    }

    @Override
    public void OnRefresh(OwenCircleAllBean.OBean.ListBean mdata, int position) {
//        //如果不请求同样条数的数据，刷新会出现位置改变的现象
//        pageNo = 1;
//        pageSize = position + 12;
//        getData();
//        currentposition = position;
//        list.clear();
        adapter.getDatas().get(position).setComment(mdata.getComment());
        adapter.getDatas().get(position).setPpuid(mdata.getPpuid());
        adapter.getDatas().get(position).setZan(mdata.getZan());
        adapter.getDatas().get(position).setZan_name(mdata.getZan_name());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnDeleteItem(OwenCircleAllBean.OBean.ListBean mdata) {
        adapter.getDatas().remove(mdata);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fragmentRefresh() {
        pageNo = 1;
        pageSize = 10;
        getData();
    }
}
