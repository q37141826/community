package com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.mine.OnekeyCallBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.myprofile.OnkeyCallAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.mine.OneKeyCallHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class OnekeyCallActivity extends BaseActivity implements OneKeyCallHolder.CallPermission, XRecyclerView.LoadingListener {
    private RelativeLayout relativeLayout_title_onekeycall;
    private XRecyclerView recyclerview_onkeycall;
    private OnkeyCallAdapter adapter;
    private int pageNo = 1, pageSize = 10;
    String permissions[] = {Manifest.permission.CALL_PHONE};
    private ImageView imageView_kongbai_onekeycall;
    private SwipeRefreshLayout swiprefresh_onkey_call;

    @Override
    protected void onInitData() {
        adapter = new OnkeyCallAdapter();
        recyclerview_onkeycall.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_onkeycall.setAdapter(adapter);

        recyclerview_onkeycall.setPullRefreshEnabled(false);
        recyclerview_onkeycall.setLoadingListener(this);
        recyclerview_onkeycall.addItemDecoration(new SpaceItemsDecoration(10));

        getData();
        swiprefresh_onkey_call.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getData();
            }
        });
    }

    @Override
    protected void onInitView() {
        swiprefresh_onkey_call = (SwipeRefreshLayout) findViewById(R.id.swiprefresh_onkey_call);
        imageView_kongbai_onekeycall = (ImageView) findViewById(R.id.imageView_kongbai_onekeycall);
        relativeLayout_title_onekeycall = (RelativeLayout) findViewById(R.id.relativeLayout_title_onekeycall);
        recyclerview_onkeycall = (XRecyclerView) findViewById(R.id.recyclerview_onkeycall);
        initTitle();
    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setTitle("一键呼叫");

        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout_title_onekeycall.addView(title.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_onekey_call;
    }

    public void getData() {
        OkHttpUtils.post().url(ConstantUrl.onekeyCallUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize + "").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        swiprefresh_onkey_call.setRefreshing(false);
                        recyclerview_onkeycall.loadMoreComplete();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        try {
                            OnekeyCallBean bean = GetGson.init().fromJson(s, OnekeyCallBean.class);
                            if (pageNo == 1) {
                                adapter.refreshData(bean.getO().getList());
                            } else {
                                adapter.addDatas(bean.getO().getList());
                                if (bean.getO().getList().size() == 0) {
                                    ToastUtil.showToast(OnekeyCallActivity.this, "没有更多了");
                                }
                            }
                            if (adapter.getDatas().size() == 0) {
                                imageView_kongbai_onekeycall.setVisibility(View.VISIBLE);
                            } else {
                                imageView_kongbai_onekeycall.setVisibility(View.GONE);
                            }
//                            ToastUtil.showToast(OnekeyCallActivity.this, bean.getM());
                        } catch (Exception e) {
                            MessageBean bean = GetGson.parseMessageBean(s);
                            ToastUtil.showToast(OnekeyCallActivity.this, bean.getM());
                        }
                        swiprefresh_onkey_call.setRefreshing(false);
                        recyclerview_onkeycall.loadMoreComplete();
                    }
                });
    }

    String callPhone;

    @Override
    public void call(String phone) {
        callPhone = phone;
        if (!hasPermission(permissions)) {
            hasRequse(1, permissions);
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!hasPermission(permissions)) {
            hasRequse(1, permissions);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getData();
    }
}
