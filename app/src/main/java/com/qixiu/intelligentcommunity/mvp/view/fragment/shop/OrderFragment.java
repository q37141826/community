package com.qixiu.intelligentcommunity.mvp.view.fragment.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.MyOrderAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.my_interface.Communication;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;
import com.qixiu.intelligentcommunity.receiver.OrderReceiverFactory;
import com.qixiu.intelligentcommunity.receiver.ReceiverFactory;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.wxpay.WxPay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderFragment extends BaseFragment implements FragmentInterface, MyOrderAdapter.MyOrderRefreshListener, XRecyclerView.LoadingListener, OKHttpUIUpdataListener<BaseBean>, Communication {
    int position = 0;
    private TextView textView_order;
    //每次请求回来的数据量
    // int size;
    //页数订单状态
    int order_status, ship_status, pageSum = 20, pageNumber = 1;
    //第几页代表订单状态
    public static final String ARGS_PAGE = "args_page";
    public static final String ARGS_TYPE = "arge_type";
    SwipeRefreshLayout swiprefresh_order;
    XRecyclerView recycleView_myorederall;

    private boolean isMore;
    MyOrderAdapter adapter;
    List<OrderBean.OBean.ListBean> list = new ArrayList();
    private OKHttpRequestModel mOkHttpRequestModel;
    private ReceiverFactory mReceiverFactory;
    BroadcastReceiver recever;
    private BroadcastReceiver receverDelete;

    public OrderFragment() {

    }


    @Override
    protected void onInitData() {
        IntentFilter filter = new IntentFilter(IntentDataKeyConstant.BROADCAST_COMMENTS_OK);
        recever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                for (int i = 0; i < list.size(); i++) {
                    String stringExtra = intent.getStringExtra(IntentDataKeyConstant.ID);
                    if (list.get(i).getOrder_id().equals(stringExtra)) {
                        list.get(i).setIs_common(2 + "");
                    }
                }
                adapter.notifyDataSetChanged();
            }
        };
        getActivity().registerReceiver(recever, filter);
        IntentFilter filterDelete = new IntentFilter(IntentDataKeyConstant.BROADCAST_DELETE_OK);
        receverDelete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               refresh();
            }
        };
        getActivity().registerReceiver(receverDelete, filterDelete);
    }

    @Override
    protected void onInitView(View view) {
        textView_order = (TextView) view.findViewById(R.id.textView_order);
        swiprefresh_order = (SwipeRefreshLayout) view.findViewById(R.id.swiprefresh_order);
        recycleView_myorederall = (XRecyclerView) view.findViewById(R.id.recycleView_myorederall);
        adapter = new MyOrderAdapter();
        //设置删除后刷新的监听
        adapter.setMyOrderRefreshListener(this);
        mReceiverFactory = OrderReceiverFactory.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentDataKeyConstant.BROADCAST_MYODER_WXPAY_ACTION);
        mReceiverFactory.buildReceiver(getActivity(), intentFilter, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleView_myorederall.setLayoutManager(linearLayoutManager);
        recycleView_myorederall.setAdapter(adapter);

        //设置加载事件
        recycleView_myorederall.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
//
//        recycleView_myorederall.setPullRefreshEnabled(false);
//        recycleView_myorederall.setLoadingListener(this);
//        recycleView_myorederall.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));
        CommonUtils.setXrecyclerView(recycleView_myorederall, this, getContext(), false, 1, null);
        if (mOkHttpRequestModel == null) mOkHttpRequestModel = new OKHttpRequestModel(this);

        if (getUserVisibleHint()) {
            refresh();
        }

        swiprefresh_order.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isMore = false;
                refresh();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.orderlist_fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            isMore = false;
            refresh();
        }
    }

    // todo  请求订单数据的方法，将结果填入list，请求完毕后刷新停止
    private void refresh() {
        if (!isMore)
            pageNumber = 1;
        String token = MD5Util.getToken(ConstantUrl.OrederListTag);
        Map<String, String> map = new HashMap<>();
        map.put("userId", Preference.get(StringConstants.STRING_ID, ""));
        map.put("pageNumber", pageNumber + "");
        try {
            map.remove("order_status");
            map.remove("ship_status");
        } catch (Exception e) {
        }
        switch (position) {
            case 0:
                break;
            case 1:
                order_status = 3;
                ship_status = 2;
                map.put("ship_status", ship_status + "");
                map.put("order_status", order_status + "");
                break;
            case 2:
                ship_status = 2;
                order_status = 1;
                map.put("order_status", order_status + "");
                map.put("ship_status", ship_status + "");
                break;
            case 3:
                ship_status = 1;
                order_status = 1;
                map.put("order_status", order_status + "");
                map.put("ship_status", ship_status + "");
                break;
            case 4:
                order_status = 2;
                map.put("order_status", order_status + "");
                break;
        }
        Log.e("currentposition", position + "");
        startGetdata(map);
    }

    private void startGetdata(Map map) {
        BaseBean baseBean = new OrderBean();
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.OrederListUrl, map, baseBean, true);


    }


    public void setPosition(int position) {
        this.position = position;

    }

    @Override
    public void moveToPosition(int position) {

    }

    @Override
    public void onOrderRefresh(OrderBean.OBean.ListBean mdata) {
        adapter.getDatas().remove(mdata);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        isMore = true;
        refresh();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReceiverFactory.destroyBuildReceiver(getActivity());
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (data instanceof OrderBean) {
            OrderBean orderBean = (OrderBean) data;
            list = orderBean.getO().getList();
            int size = list.size();
            if (!isMore) {
                swiprefresh_order.setRefreshing(false);
                adapter.refreshData(list);
            } else {
                recycleView_myorederall.loadMoreComplete();
                if (size > 0) {
                    adapter.addDatas(list);
                } else {
                    ToastUtil.toast("已经没有更多数据了");
                }

            }
            if (adapter.getDatas().size() == 0) {
                textView_order.setVisibility(View.VISIBLE);
            } else {
                textView_order.setVisibility(View.GONE);
            }

            pageNumber += 1;
        }

    }

    @Override
    public void onError(Call call, Exception e, int i) {
        ToastUtil.showToast(getContext(), "失败");
        textView_order.setVisibility(View.VISIBLE);
        swiprefresh_order.setRefreshing(false);
        recycleView_myorederall.loadMoreComplete();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        ToastUtil.showToast(getContext(), "失败");
        swiprefresh_order.setRefreshing(false);
        recycleView_myorederall.loadMoreComplete();
    }

    @Override
    public void startCommunicate(Object... content) {
        if (content == null) return;
        List<OrderBean.OBean.ListBean> datas = adapter.getDatas();
        if (WxPay.payOrderPosition < datas.size()) {
            datas.remove(WxPay.payOrderPosition);
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(recever);
        getActivity().unregisterReceiver(receverDelete);
    }
}
