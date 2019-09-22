package com.qixiu.intelligentcommunity.mvp.view.fragment.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntConstant;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.AddGoodsToCarsBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreShopCarActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.order.ConfirmOrderActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.shopcar.ShopCarAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.shopcar.ShopCarHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LineDividerItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.my_interface.Communication;
import com.qixiu.intelligentcommunity.receiver.BaseReceiverFactory;
import com.qixiu.intelligentcommunity.receiver.ReceiverFactory;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.SplitStringUtils;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class StoreShopCarFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, OKHttpUIUpdataListener<BaseBean>, ShopCarHolder.ShopCarOnItemSelectedListener, StoreShopCarActivity.OnDelectShopCarListner, Communication, RecyclerBaseAdapter.OnHolderNotifyRefreshListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRv_shopcar;
    private TextView mTv_settleaccounts;
    //购物车结算的一些信息
    private View mRl_settleaccounts_info;
    private TextView mTv_totalprice;
    private TextView mTv_totalpiece;

    private double onSelectedPrice;
    private double onSelectedPostage;

    private ShopCarAdapter mShopCarAdapter;
    private boolean isSelectAll = false;
    private TextView mTv_all_selected;
    private OKHttpRequestModel mOkHttpRequestModel;
    private List<Double> mPostageList;
    private View rl_shopcar_bottom;
    private TextView tv_shopcar_nodata;
    private ReceiverFactory mInstance;

    @Override
    protected void onInitData() {

        mOkHttpRequestModel = new OKHttpRequestModel(this);
        requestShopData();
        mInstance = BaseReceiverFactory.getInstance();
        IntentFilter intentFilter = new IntentFilter(IntentDataKeyConstant.BROADCAST_PAY_SHOPCARREFRESH_ACTION);
        mInstance.buildReceiver(getActivity(), intentFilter, this);
    }

    private void requestShopData() {
        BaseBean baseBean = new AddGoodsToCarsBean();
        Map<String, String> map = new HashMap<>();
        map.put(StringConstants.STRING_USER_ID, Preference.get(IntentDataKeyConstant.ID, StringConstants.STRING_EMPTY));
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_GET_SHOPCAR_GOODS, map, baseBean, true);
    }

    @Override
    protected void onInitView(View view) {

        mRefreshLayout = findViewById(R.id.srl_shopcar);
        mRv_shopcar = findViewById(R.id.rv_shopcar);
        //点击操作的控件
        TextView mTv_settleaccounts = findViewById(R.id.tv_settleaccounts);
        tv_shopcar_nodata = findViewById(R.id.tv_shopcar_nodata);

        mTv_all_selected = findViewById(R.id.tv_all_selected);
        //显示信息内容的控件
        mRl_settleaccounts_info = findViewById(R.id.rl_settleaccounts_info);
        //总价
        mTv_totalprice = findViewById(R.id.tv_totalprice);
        //总件
        mTv_totalpiece = findViewById(R.id.tv_totalpiece);
        rl_shopcar_bottom = findViewById(R.id.rl_shopcar_bottom);
        mTv_all_selected.setOnClickListener(this);
        mTv_settleaccounts.setOnClickListener(this);
        tv_shopcar_nodata.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv_shopcar.addItemDecoration(new LineDividerItemDecoration(getActivity(), LineDividerItemDecoration.VERTICAL_LIST));
        mRv_shopcar.setLayoutManager(linearLayoutManager);
        mShopCarAdapter = new ShopCarAdapter();
        mShopCarAdapter.setShopCarOnItemSelectedListener(this);
        mShopCarAdapter.setOnHolderNotifyRefreshListener(this);
        mRv_shopcar.setAdapter(mShopCarAdapter);


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_shopcar;
    }

    @Override
    public void onRefresh() {
        requestShopData();

    }

//    @Override
//    public void onItemClick(View v, AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean shopCartBean) {
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_settleaccounts:
                if (TextUtils.isEmpty(carIdList)) {
                    ToastUtil.showToast(BaseApplication.getContext(), "请您先选择商品");
                    return;
                }

                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                intent.putExtra(IntentDataKeyConstant.CART_ID, carIdList);
                intent.putExtra(IntentDataKeyConstant.TOTOLPRICE_KEY, onSelectedPrice);
                if (mPostageList != null && mPostageList.size() > 0) {
                    Collections.sort(mPostageList);
                    intent.putExtra(IntentDataKeyConstant.POSTAGE_KEY, mPostageList.get(mPostageList.size() - 1));
                }


                startActivity(intent);
                break;
            case R.id.tv_all_selected:
                isSelectAll = !isSelectAll;
                List<AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean> datas = mShopCarAdapter.getDatas();
                //全部选择
                if (datas != null) {
                    for (int i = IntConstant.NUM_0; i < datas.size(); i++) {
                        AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean shopCartBean = datas.get(i);
                        shopCartBean.setSelected(isSelectAll);

                    }
                    mShopCarAdapter.notifyDataSetChanged();
                    shopCarOnItemSelected(0);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mTv_all_selected.setCompoundDrawablesWithIntrinsicBounds(isSelectAll ? R.mipmap.shopcar_select : R.mipmap.shopcar_notselect, IntConstant.NUM_0, IntConstant.NUM_0, IntConstant.NUM_0);
                } else {
                    mTv_all_selected.setCompoundDrawables(null, getResources().getDrawable(isSelectAll ? R.mipmap.shopcar_select : R.mipmap.shopcar_notselect), null, null);
                }

                break;

            case R.id.tv_shopcar_nodata:
                //requestShopData();
                break;
        }
    }

    public void showAffirmCancelDialog() {


    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (data != null) {
            mRefreshLayout.setRefreshing(false);
            if (data instanceof AddGoodsToCarsBean) {
                AddGoodsToCarsBean addGoodsToCarsBean = (AddGoodsToCarsBean) data;
                AddGoodsToCarsBean.AddGoodsToCarsInfo addGoodsToCarsInfo = addGoodsToCarsBean.getO();
                List<AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean> cart = addGoodsToCarsInfo.getCart();
                mShopCarAdapter.refreshData(cart);

            } else {

                List<AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean> removeShopCartBeens = new ArrayList<>();
                for (int j = 0; j < mShopCarAdapter.getDatas().size(); j++) {
                    AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean shopCartBean = mShopCarAdapter.getDatas().get(j);

                    if (shopCartBean.isSelected()) {
                        removeShopCartBeens.add(shopCartBean);

                    }


                }
                if (!TextUtils.isEmpty(carIdList)) {

                    carIdList = null;
                }
                mShopCarAdapter.getDatas().removeAll(removeShopCartBeens);

                mShopCarAdapter.notifyDataSetChanged();

            }

            showNodataStyle();

        }

    }

    private void showNodataStyle() {
        if (mShopCarAdapter.getDatas().size() <= 0) {
            rl_shopcar_bottom.setVisibility(View.GONE);
            tv_shopcar_nodata.setVisibility(View.VISIBLE);
        } else {

            rl_shopcar_bottom.setVisibility(View.VISIBLE);
            tv_shopcar_nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        mRefreshLayout.setRefreshing(false);
        showNodataStyle();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        mRefreshLayout.setRefreshing(false);
        if (c_codeBean != null) {
            if (ConstantUrl.STORE_GET_SHOPCAR_GOODS.equals(c_codeBean.getUrl())) {
                mShopCarAdapter.refreshData(null);

            }
            showNodataStyle();
        }


    }

    private String carIdList = null;

    /**
     * 当选择条目和全选后做的操作
     *
     * @param position
     */
    @Override
    public void shopCarOnItemSelected(int position) {
        carIdList = null;

        StringBuffer stringBuffer = new StringBuffer();
        //单个条目商品的数量
        double onSelectedSingleNum = IntConstant.NUM_0;
        //总的条目商品数量
        double onSelectedTotalNum = onSelectedSingleNum;
        onSelectedPrice = IntConstant.NUM_DOUBLE_0;
        onSelectedPostage = IntConstant.NUM_DOUBLE_0;
        mPostageList = new ArrayList<>();

        List<AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean> datas = mShopCarAdapter.getDatas();
        for (int i = IntConstant.NUM_0; i < datas.size(); i++) {
            AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean shopCartBean = datas.get(i);
            if (shopCartBean.isSelected()) {
                onSelectedSingleNum = Double.parseDouble(shopCartBean.getGoods_num());
                onSelectedTotalNum += onSelectedSingleNum;
                onSelectedPrice += onSelectedSingleNum * Double.parseDouble(shopCartBean.getGoods_price());
                stringBuffer.append(shopCartBean.getId());
                stringBuffer.append(StringConstants.STRING_COMMA);
                mPostageList.add(shopCartBean.getPostage() == null ? IntConstant.NUM_DOUBLE_0 : Double.parseDouble(shopCartBean.getPostage()));
            }

        }
        carIdList = SplitStringUtils.cutStringEnd(stringBuffer.toString(), StringConstants.STRING_COMMA);
        mTv_totalpiece.setText(StringConstants.STRING_COMMON + (int) onSelectedTotalNum + StringConstants.STRING_PIECE);
        mTv_totalprice.setText(StringConstants.STRING_RMB + onSelectedPrice);
    }

    @Override
    public void onDelected() {
        if (TextUtils.isEmpty(carIdList)) {
            ToastUtil.showToast(BaseApplication.getContext(), "请您先选择商品");
            return;
        }
        ArshowDialog.Builder builder = new ArshowDialog.Builder(getActivity());
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Map<String, String> requestParameter = new HashMap<>();
                requestParameter.put(StringConstants.STRING_USER_ID, Preference.get(IntentDataKeyConstant.ID, StringConstants.STRING_EMPTY));
                requestParameter.put(IntentDataKeyConstant.CART_ID, carIdList);
                //删除
                mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SHOPCAR_DELECTED, requestParameter, new BaseBean(), true);
                dialog.dismiss();


            }
        });

        builder.setMessage("是否删除");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInstance.destroyBuildReceiver(getActivity());
    }

    @Override
    public void startCommunicate(Object... content) {
        requestShopData();
    }

    @Override
    public void onHolderNotifyRefresh(Object data) {
        showNodataStyle();
    }
}
