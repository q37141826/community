package com.qixiu.intelligentcommunity.mvp.view.activity.home.get_goods;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.GetGoodsRecordBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.GetGoodsRecordAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class GetGoodsRecordActivity extends NewTitleActivity implements OKHttpUIUpdataListener, XRecyclerView.LoadingListener {
    private SwipeRefreshLayout swip_refresh_getGoods_record;
    private XRecyclerView recyclerView_getgoods_record;
    private GetGoodsRecordAdapter adapter;
    private int pageNo = 1, pageSize = 10;
    private OKHttpRequestModel okmodl;
    private ImageView imageView_nothing;

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(Object data, int i) {
        if (data instanceof GetGoodsRecordBean) {
            GetGoodsRecordBean bean = (GetGoodsRecordBean) data;
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
        swip_refresh_getGoods_record.setRefreshing(false);
        recyclerView_getgoods_record.loadMoreComplete();
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        swip_refresh_getGoods_record.setRefreshing(false);
        recyclerView_getgoods_record.loadMoreComplete();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        swip_refresh_getGoods_record.setRefreshing(false);
        recyclerView_getgoods_record.loadMoreComplete();
    }

    @Override
    protected void onInitViewNew() {
        mTitleView.setTitle("记录");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView_nothing = (ImageView) findViewById(R.id.imageView_nothing);
        adapter = new GetGoodsRecordAdapter();
        swip_refresh_getGoods_record = (SwipeRefreshLayout) findViewById(R.id.swip_refresh_getGoods_record);
        recyclerView_getgoods_record = (XRecyclerView) findViewById(R.id.recyclerView_getgoods_record);
        swip_refresh_getGoods_record.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getdata();
            }
        });
        okmodl = new OKHttpRequestModel(this);
        CommonUtils.setXrecyclerView(recyclerView_getgoods_record, this, this, false, 1, null);
    }

    @Override
    protected void onInitData() {
        getdata();
        recyclerView_getgoods_record.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemListener<GetGoodsRecordBean.OBean.ListBean>() {
            @Override
            public void onItemClick(View v, GetGoodsRecordBean.OBean.ListBean data) {
                Intent intent = new Intent(GetGoodsRecordActivity.this, GetGoodsDetailsActivity.class);
                intent.putExtra(ConstantString.USERID, data.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_get_goods_record;
    }

    public void getdata() {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        map.put("uid", Preference.get(ConstantString.USERID, ""));
        GetGoodsRecordBean bean = new GetGoodsRecordBean();
        okmodl.okhHttpPost(ConstantUrl.getGoodsRecordUrl, map, bean);

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
