package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.answer_fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.AnswerDetailsActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer.OwenAnserListAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.my_interface.Communication;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;
import com.qixiu.intelligentcommunity.receiver.BaseReceiverFactory;
import com.qixiu.intelligentcommunity.receiver.ReceiverFactory;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class OwenAnswerFragment extends BaseFragment implements FragmentInterface, View.OnClickListener, XRecyclerView.LoadingListener, OwenAnserListAdapter.OnAnserRefresh, Communication {
    //布局样式一样，切换数据就行
    RadioButton rb_owner_whole_answer, rb_owner_my_answer;
    XRecyclerView recyclerview_owen_anserall;
    SwipeRefreshLayout swiprefresh_anser_all;
    String url = ConstantUrl.answerListAllUrl;
    private int pageNo = 1, pageSize = 10;
    private OwenAnserListAdapter adapter;
    private ImageView imageView_nothing;
    private ReceiverFactory mInstance;
    private OwenAnswerListBean owenAnswerListBean;
    private BroadcastReceiver receiver;

    @Override
    protected void onInitData() {
        adapter = new OwenAnserListAdapter();
//        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_owen_anserall.setLayoutManager(linearLayoutManager);
        recyclerview_owen_anserall.setAdapter(adapter);
        adapter.setRefreshListenner(this);
        //设置加载事件
        recyclerview_owen_anserall.setPullRefreshEnabled(false);
//        recyclerview_owen_anserall.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        recyclerview_owen_anserall.setLoadingListener(this);
        recyclerview_owen_anserall.addItemDecoration(new SpaceItemsDecoration(10));

        mInstance = BaseReceiverFactory.getInstance();
        IntentFilter intentFilter = new IntentFilter(IntentDataKeyConstant.NOTIFY_ANSWERCIRCLE_RELEASESUCCESS_ACTION);
        mInstance.buildReceiver(getActivity().getApplicationContext(), intentFilter, this);
        swiprefresh_anser_all.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                refresh();
            }
        });
        refresh();
        adapter.setOnItemClickListener(new OnRecyclerItemListener<OwenAnswerListBean.OBean.ListBean>() {
            @Override
            public void onItemClick(View v, OwenAnswerListBean.OBean.ListBean data) {
                Intent intent = new Intent(getContext(), AnswerDetailsActivity.class);
                intent.putExtra("id", data.getId());
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
            }
        });
        IntentFilter fileter = new IntentFilter(ConstantString.broadCastOwnAnser);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent data) {
                if (data != null) {
                    for (int i = 0; i < owenAnswerListBean.getO().getList().size(); i++) {
                        if (data.getStringExtra("id").equals(owenAnswerListBean.getO().getList().get(i).getId() + "")) {
                            adapter.getDatas().remove(owenAnswerListBean.getO().getList().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        getContext().registerReceiver(receiver, fileter);
    }

    private void refresh() {
        OkHttpUtils.post().url(url)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                swiprefresh_anser_all.setRefreshing(false);
                recyclerview_owen_anserall.loadMoreComplete();
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    owenAnswerListBean = GetGson.init().fromJson(s, OwenAnswerListBean.class);
                    if (pageNo == 1) {
                        adapter.refreshData(null);
                        adapter.refreshData(owenAnswerListBean.getO().getList());
                    } else {
                        adapter.addDatas(owenAnswerListBean.getO().getList());
                        if (owenAnswerListBean.getO().getList().size() == 0) {
                            ToastUtil.showToast(getContext(), "没有更多了");
                        }
                    }
                    if (adapter.getDatas().size() == 0) {
                        imageView_nothing.setVisibility(View.VISIBLE);
                    } else {
                        imageView_nothing.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    imageView_nothing.setVisibility(View.VISIBLE);
                }
                swiprefresh_anser_all.setRefreshing(false);
                recyclerview_owen_anserall.loadMoreComplete();
            }
        });
    }

    @Override
    protected void onInitView(View view) {
        imageView_nothing = (ImageView) view.findViewById(R.id.imageView_nothing);
        swiprefresh_anser_all = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh_anser_all);
        recyclerview_owen_anserall = (XRecyclerView) view.findViewById(R.id.recyclerview_owen_anserall);
        rb_owner_whole_answer = (RadioButton) view.findViewById(R.id.rb_owner_whole_answer);
        rb_owner_my_answer = (RadioButton) view.findViewById(R.id.rb_owner_my_answer);
        initClick();
    }

    @Override
    public void onDestroyView() {
        if (mInstance != null) {
            mInstance.destroyBuildReceiver(getActivity().getApplicationContext());
        }
        super.onDestroyView();
    }

    private void initClick() {
        rb_owner_whole_answer.setOnClickListener(this);
        rb_owner_my_answer.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_owen_answer;
    }

    @Override
    public void moveToPosition(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_owner_whole_answer:
                //改变参数
                url = ConstantUrl.answerListAllUrl;
                pageNo = 1;
                refresh();
                break;
            case R.id.rb_owner_my_answer:
                url = ConstantUrl.answerListMineUrl;
                pageNo = 1;
                refresh();
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        refresh();
        Log.e("sss", "加载成功");
    }

    @Override
    public void refresh(OwenAnswerListBean.OBean.ListBean mData) {
        adapter.getDatas().remove(mData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startCommunicate(Object... content) {
        Intent intent = null;
        for (int i = 0; i < content.length; i++) {
            if (content[i] instanceof Intent) {
                intent = (Intent) content[i];
            }
        }
        if (intent == null) {
            return;
        }
        if (IntentDataKeyConstant.NOTIFY_ANSWERCIRCLE_RELEASESUCCESS_ACTION.equals(intent.getAction())) {
            pageNo = 1;
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }
}
