package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.event_fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event.OwenEventBean;
import com.qixiu.intelligentcommunity.mvp.beans.parameter.BaseParameter;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.OwnerEventDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenEventAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpaceItemsDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.IntConstant.CNSTANT_1;


/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class OwenEventFragment extends BaseFragment implements FragmentInterface, SwipeRefreshLayout.OnRefreshListener, XRecyclerView.LoadingListener, OKHttpUIUpdataListener, OnRecyclerItemListener {

    private SwipeRefreshLayout mSrl_own_eventl;
    private XRecyclerView rv_own_event;
    private OwenEventAdapter mOwenEventAdapter;
    private OKHttpRequestModel mOkHttpRequestModel;
    private BaseParameter mBaseParameter;
    private ImageView mIv_notdata;

    @Override
    protected void onInitData() {
        mBaseParameter = new BaseParameter();
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        if (getUserVisibleHint()) {
            requestData();
        }
    }

    @Override
    protected void onInitView(View view) {
        mIv_notdata = (ImageView) view.findViewById(R.id.iv_notdata);
        mSrl_own_eventl = (SwipeRefreshLayout) view.findViewById(R.id.srl_own_eventl);

        mSrl_own_eventl.setOnRefreshListener(this);
        rv_own_event = (XRecyclerView) view.findViewById(R.id.rv_own_event);
        rv_own_event.setPullRefreshEnabled(false);
        rv_own_event.setLoadingListener(this);
        rv_own_event.addItemDecoration(new SpaceItemsDecoration(5));
        rv_own_event.setPadding(ArshowContextUtil.dp2px(5), 0,
                ArshowContextUtil.dp2px(5), 0);
        //  rv_own_event.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_own_event.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOwenEventAdapter = new OwenEventAdapter();
        rv_own_event.setAdapter(mOwenEventAdapter);
        mOwenEventAdapter.setOnItemClickListener(this);

    }

    private boolean isMore;

    private void requestData() {
        if (!isMore) {
            mBaseParameter.setPageNo(CNSTANT_1);
        }
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("pageNo", mBaseParameter.getPageNo() + StringConstants.EMPTY_STRING);
        stringMap.put("pageSize", mBaseParameter.getPageSize() + StringConstants.EMPTY_STRING);
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        stringMap.put(IntentDataKeyConstant.UID_KEY, uid);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.activityListUrl, stringMap, new OwenEventBean(), false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //判断是否显示
        if (isVisibleToUser && isVisible()) {
            //加载数据的方法
            requestData();
        }
    }

    @Override
    protected int getLayoutResource() {

        return R.layout.fragment_own_event;
    }

    @Override
    public void moveToPosition(int position) {

    }

    @Override
    public void onRefresh() {
        isMore = false;
        requestData();
    }

    @Override
    public void onLoadMore() {

        isMore = true;
        requestData();
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (mSrl_own_eventl.isRefreshing()) {
            mSrl_own_eventl.setRefreshing(false);
        }

        rv_own_event.loadMoreComplete();
        if (data instanceof OwenEventBean) {

            OwenEventBean owenEventBean = (OwenEventBean) data;
            OwenEventBean.OwenEventInfo owenEventInfo = owenEventBean.getO();
            List<OwenEventBean.OwenEventInfo.ListBean> list = owenEventInfo.getList();
            if (!isMore) {
                mOwenEventAdapter.refreshData(list);
                if (list.size() <= 0) {
                    if (mIv_notdata.getVisibility() != View.VISIBLE)
                        mIv_notdata.setVisibility(View.VISIBLE);
                } else {
                    if (mIv_notdata.getVisibility() != View.GONE)
                        mIv_notdata.setVisibility(View.GONE);
                }

            } else {
                if (list.size() <= 0) {
                    ToastUtil.toast("没有更多数据啦");
                } else {
                    mOwenEventAdapter.addDatas(list);
                }

            }


            mBaseParameter.setPageNo(mBaseParameter.getPageNo() + 1);
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (mSrl_own_eventl.isRefreshing()) {
            mSrl_own_eventl.setRefreshing(false);
        }
        rv_own_event.loadMoreComplete();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (c_codeBean.getC() == 0) {
            if (mIv_notdata.getVisibility() != View.VISIBLE) {
                mIv_notdata.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.mipmap.kongbaiye2x).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv_notdata);
            }

        }
        if (mSrl_own_eventl.isRefreshing()) {
            mSrl_own_eventl.setRefreshing(false);
        }
        rv_own_event.loadMoreComplete();
    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof OwenEventBean.OwenEventInfo.ListBean) {
            OwenEventBean.OwenEventInfo.ListBean listBean = (OwenEventBean.OwenEventInfo.ListBean) data;
            Intent intent = new Intent(getActivity(), OwnerEventDetailActivity.class);
            intent.putExtra(IntentDataKeyConstant.AID, listBean.getId() + StringConstants.EMPTY_STRING);
            startActivity(intent);
        }
    }
}
