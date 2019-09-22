package com.qixiu.intelligentcommunity.mvp.view.activity.mine.messagelist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.home.MessageListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.MessageListAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.homepage.MessageListHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MessageListActivity extends BaseActivity implements XRecyclerView.LoadingListener, MessageListHolder.OnDataRefresh {
    //type 消息类型（1.系统消息 2.业主圈 3问答 4求助 5.邻里圈 6.授权） status:1.查看 2.未查看
    private RelativeLayout relativeLayout_titile_messagelist;
    private int pageNo = 1, pageSize = 20;
    private XRecyclerView recyclerview_messagelist;
    private SwipeRefreshLayout swiprefresh_messagelist;
    MessageListAdapter adapter;
    //空白页
    private ImageView imageView_back_messagelist;

    @Override
    protected void onInitData() {
        adapter = new MessageListAdapter();
        recyclerview_messagelist.setAdapter(adapter);
        getdata();
    }

    @Override
    protected void onInitView() {
        swiprefresh_messagelist = (SwipeRefreshLayout) findViewById(R.id.swiprefresh_messagelist);
        imageView_back_messagelist = (ImageView) findViewById(R.id.imageView_back_messagelist);
        recyclerview_messagelist = (XRecyclerView) findViewById(R.id.recyclerview_messagelist);
        relativeLayout_titile_messagelist = (RelativeLayout) findViewById(R.id.relativeLayout_titile_messagelist);
        initTitle();
        swiprefresh_messagelist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getdata();
            }
        });
        recyclerview_messagelist.setPullRefreshEnabled(false);
        recyclerview_messagelist.setLoadingListener(this);
        recyclerview_messagelist.addItemDecoration(new SpaceItemsDecoration(10));
        recyclerview_messagelist.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setTitle("消息列表");
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout_titile_messagelist.addView(title.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_message_list;
    }

    public void getdata() {
        OkHttpUtils.post().url(ConstantUrl.messageListUrl)
                .addParams("suid", Preference.get(ConstantString.USERID, ""))
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                swiprefresh_messagelist.setRefreshing(false);
                recyclerview_messagelist.loadMoreComplete();
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    MessageListBean messageListBean = GetGson.init().fromJson(s, MessageListBean.class);
                    if (pageNo == 1) {
                        adapter.refreshData(messageListBean.getO().getList());
                    } else {
                        adapter.addDatas(messageListBean.getO().getList());
                        if (messageListBean.getO().getList().size() == 0) {
                            ToastUtil.showToast(MessageListActivity.this, "没有更多了");
                        }
                    }
                    if (adapter.getDatas().size() == 0) {
                        imageView_back_messagelist.setVisibility(View.VISIBLE);
                    } else {
                        imageView_back_messagelist.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                }
                swiprefresh_messagelist.setRefreshing(false);
                recyclerview_messagelist.loadMoreComplete();
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getdata();
    }

    @Override
    public void refreshData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if (Preference.get(ConstantString.IS_FROM_DELETE, "").equals("YES")) {
                Preference.clearFlag(ConstantString.IS_FROM_DELETE);
                pageNo = 1;
                getdata();
            }
        } catch (Exception e) {
        }
    }
}
