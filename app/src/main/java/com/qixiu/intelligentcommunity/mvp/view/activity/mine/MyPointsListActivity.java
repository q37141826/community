package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.mine.PointsDetailsBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.PointsAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MyPointsListActivity extends NewTitleActivity implements OKHttpUIUpdataListener<BaseBean>, XRecyclerView.LoadingListener {
    private PointsAdapter adapter;
    private ImageView imageView_nothing;
    private OKHttpRequestModel okHttpRequestModel;
    private SwipeRefreshLayout swip_refresh_points;
    private XRecyclerView recyclerview_points;
    private int pageNo = 1, pageSize = 10;

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (data instanceof PointsDetailsBean) {
            PointsDetailsBean bean = (PointsDetailsBean) data;
            if (pageNo == 1) {
                adapter.refreshData(bean.getO().getList());
            } else {
                adapter.addDatas(bean.getO().getList());
            }
        }
        if (adapter.getDatas().size() == 0) {
            imageView_nothing.setVisibility(View.VISIBLE);
        } else {
            imageView_nothing.setVisibility(View.GONE);
        }
        if (adapter.getDatas().size() == 0 && pageNo != 1) {
            ToastUtil.toast("没有更多了");
        }
        swip_refresh_points.setRefreshing(false);
        recyclerview_points.loadMoreComplete();
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        swip_refresh_points.setRefreshing(false);
        recyclerview_points.loadMoreComplete();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        swip_refresh_points.setRefreshing(false);
        recyclerview_points.loadMoreComplete();
    }


    @Override
    protected void onInitData() {
        CommonUtils.setXrecyclerView(recyclerview_points,this,this,false,1,null);
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okHttpRequestModel = new OKHttpRequestModel(this);
        adapter = new PointsAdapter();
        recyclerview_points.setAdapter(adapter);
        getdata();
        mTitleView.setTitle("积分明细");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pointes_list;
    }

    @Override
    protected void onInitViewNew() {
        imageView_nothing = (ImageView) findViewById(R.id.imageView_nothing);
        swip_refresh_points = (SwipeRefreshLayout) findViewById(R.id.swip_refresh_points);
        recyclerview_points = (XRecyclerView) findViewById(R.id.recyclerview_points);
        swip_refresh_points.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getdata();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void getdata() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", Preference.get(ConstantString.USERID, ""));
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        PointsDetailsBean bean = new PointsDetailsBean();
        okHttpRequestModel.okhHttpPost(ConstantUrl.myPointsListUrl, map, bean);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getdata();
    }
}
