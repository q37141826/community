package com.qixiu.intelligentcommunity.mvp.view.activity.store;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.parameter.BaseParameter;
import com.qixiu.intelligentcommunity.mvp.beans.store.impl.GoodsListImpl;
import com.qixiu.intelligentcommunity.mvp.beans.store.more.StoreMoreBeans;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.classify.StoreAllClassifyActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class StoreMoreActivity extends NewTitleActivity implements XRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, OKHttpUIUpdataListener, View.OnTouchListener, OnRecyclerItemListener {
    private XRecyclerView mRv_storelist;
    private StoreAdapter mStoreAdapter;
    private TextView mTv_default;
    private View mV_default;
    private TextView mTv_count;
    private View mV_count;
    private TextView mTv_price;
    private View mV_price;
    private SwipeRefreshLayout mSrl_storemore;
    private OKHttpRequestModel mOkHttpRequestModel;
    private BaseParameter mBaseParameter;
    private EditText mEt_store_more;

    private String sales_sum = StringConstants.STRING_1;
    private String shop_price = StringConstants.STRING_1;
    /**
     * 针对默认，销量，价格分类，点击后记录的位置
     */
    private int selectPosition;
    private String mSeach;
    private boolean isMore;
    private ZProgressHUD mZProgressHUD;
    private View mIv_notdata;

    @Override
    protected void onInitViewNew() {

        mZProgressHUD = new ZProgressHUD(this);
        mTitleView.setTitle("商超");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View rl_default = findViewById(R.id.rl_default);
        View rl_search_right = findViewById(R.id.rl_search_right);
        mEt_store_more = (EditText) findViewById(R.id.et_store_more);
        mEt_store_more.setOnTouchListener(this);
        rl_search_right.setOnClickListener(this);
        mTv_default = (TextView) findViewById(R.id.tv_default);
        mV_default = findViewById(R.id.v_default);


        View rl_count = findViewById(R.id.rl_count);
        mTv_count = (TextView) findViewById(R.id.tv_count);
        mV_count = findViewById(R.id.v_count);

        View rl_price = findViewById(R.id.rl_price);
        mTv_price = (TextView) findViewById(R.id.tv_price);
        mV_price = findViewById(R.id.v_price);

        rl_default.setOnClickListener(this);
        rl_count.setOnClickListener(this);
        rl_price.setOnClickListener(this);

        mIv_notdata = findViewById(R.id.iv_notdata);
        mSrl_storemore = (SwipeRefreshLayout) findViewById(R.id.srl_storemore);
        mSrl_storemore.setOnRefreshListener(this);
        mRv_storelist = (XRecyclerView) findViewById(R.id.rv_showMorelist);
        mRv_storelist.setPullRefreshEnabled(false);
        mRv_storelist.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, ArshowContextUtil.dp2px(1)));
        mRv_storelist.setLoadingListener(this);
        mRv_storelist.setLoadingMoreEnabled(true);
        mRv_storelist.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mRv_storelist.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mStoreAdapter = new StoreAdapter();
        mStoreAdapter.setOnItemClickListener(this);
        mRv_storelist.setAdapter(mStoreAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_default:

                mTv_default.setTextColor(getResources().getColor(R.color.font_grey));
                mTv_count.setTextColor(getResources().getColor(R.color.home_back));
                mTv_price.setTextColor(getResources().getColor(R.color.home_back));
                if (mV_default.getVisibility() != View.VISIBLE) {
                    mV_default.setVisibility(View.VISIBLE);
                }
                if (mV_count.getVisibility() != View.GONE) {
                    mV_count.setVisibility(View.GONE);
                }
                if (mV_price.getVisibility() != View.GONE) {
                    mV_price.setVisibility(View.GONE);
                }
                if (!StringConstants.STRING_1.equals(shop_price)) {
                    shop_price = StringConstants.STRING_1;
                }
                if (!StringConstants.STRING_1.equals(sales_sum)) {
                    sales_sum = StringConstants.STRING_1;
                }

                selectPosition = 0;
                mSeach = mEt_store_more.getText().toString();
                isMore = false;
                mZProgressHUD.show();
                requestData();
                break;
            case R.id.rl_count:

                mTv_count.setTextColor(getResources().getColor(R.color.font_grey));
                mTv_default.setTextColor(getResources().getColor(R.color.home_back));
                mTv_price.setTextColor(getResources().getColor(R.color.home_back));
                if (mV_count.getVisibility() != View.VISIBLE) {
                    mV_count.setVisibility(View.VISIBLE);
                }
                if (mV_default.getVisibility() != View.GONE) {
                    mV_default.setVisibility(View.GONE);
                }
                if (mV_price.getVisibility() != View.GONE) {
                    mV_price.setVisibility(View.GONE);
                }
                if (selectPosition == 1) {
                    if (StringConstants.STRING_1.equals(sales_sum)) {
                        sales_sum = StringConstants.STRING_2;
                    } else {
                        sales_sum = StringConstants.STRING_1;
                    }
                }

                selectPosition = 1;
                mSeach = mEt_store_more.getText().toString();
                isMore = false;
                mZProgressHUD.show();
                requestData();
                break;
            case R.id.rl_price:

                mTv_price.setTextColor(getResources().getColor(R.color.font_grey));
                mTv_count.setTextColor(getResources().getColor(R.color.home_back));
                mTv_default.setTextColor(getResources().getColor(R.color.home_back));
                if (mV_price.getVisibility() != View.VISIBLE) {
                    mV_price.setVisibility(View.VISIBLE);
                }

                if (mV_default.getVisibility() != View.GONE) {
                    mV_default.setVisibility(View.GONE);
                }
                if (mV_count.getVisibility() != View.GONE) {
                    mV_count.setVisibility(View.GONE);
                }
                if (selectPosition == 2) {
                    if (StringConstants.STRING_1.equals(shop_price)) {
                        shop_price = StringConstants.STRING_2;
                    } else {
                        shop_price = StringConstants.STRING_1;
                    }
                }

                selectPosition = 2;
                mSeach = mEt_store_more.getText().toString();
                isMore = false;
                mZProgressHUD.show();
                requestData();
                break;

            case R.id.rl_search_right:
                Intent allClassifyIntent = new Intent(this, StoreAllClassifyActivity.class);
                startActivity(allClassifyIntent);

                break;

        }
    }

    @Override
    protected void onInitData() {
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        mBaseParameter = new BaseParameter();
        requestData();
    }

    private void requestData() {


        Map<String, String> stringMap = new HashMap<>();
        if (!isMore) {
            mBaseParameter.setPageNo(1);
        }
        stringMap.put(IntentDataKeyConstant.PAGENO, mBaseParameter.getPageNo() + StringConstants.EMPTY_STRING);
        stringMap.put(IntentDataKeyConstant.PAGESIZE, mBaseParameter.getPageSize() + StringConstants.EMPTY_STRING);
        String sum = sales_sum;
        String price = shop_price;
        //根据选择位置来传不同字段值
        switch (selectPosition) {
            case 0:
                sum = StringConstants.EMPTY_STRING;
                price = StringConstants.EMPTY_STRING;
                break;
            case 1:
                price = StringConstants.EMPTY_STRING;
                break;
            case 2:
                sum = StringConstants.EMPTY_STRING;
                break;

        }
        stringMap.put("sales_sum", sum);
        if (TextUtils.isEmpty(mSeach)) {
            mSeach = StringConstants.EMPTY_STRING;
        }
        stringMap.put("seach", mSeach);
        stringMap.put("shop_price", price);

        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_CATEGORY, stringMap, new StoreMoreBeans());

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_storemore;
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
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }

        if (!isMore) {
            mSrl_storemore.setRefreshing(false);
        } else {
            mRv_storelist.loadMoreComplete();
        }


        if (data instanceof StoreMoreBeans) {
            StoreMoreBeans storeMoreBeans = (StoreMoreBeans) data;
            StoreMoreBeans.StoreMoreInfo storeMoreInfo = storeMoreBeans.getO();
            StoreMoreBeans.StoreMoreInfo.GoodsBean goods = storeMoreInfo.getGoods();
            if (!isMore) {
                if (goods.getList() != null && goods.getList().size() <= 0) {
                    if (mIv_notdata.getVisibility() != View.VISIBLE) {
                        mIv_notdata.setVisibility(View.VISIBLE);
                    }


                } else {

                    if (mIv_notdata.getVisibility() != View.GONE) {
                        mIv_notdata.setVisibility(View.GONE);

                    }
                }
                mStoreAdapter.refreshData(goods.getList());

            } else {
                if (goods.getList().size() > 0) {
                    mStoreAdapter.addDatas(goods.getList());
                } else {
                    ToastUtil.toast("已经没有数据了");
                }

            }
            mBaseParameter.setPageNo(mBaseParameter.getPageNo() + 1);
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (!isMore) {
            mSrl_storemore.setRefreshing(false);
        } else {
            mRv_storelist.loadMoreComplete();
        }

        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (!isMore) {
            mSrl_storemore.setRefreshing(false);
        } else {
            mRv_storelist.loadMoreComplete();
        }
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int width = mEt_store_more.getCompoundDrawables()[2].getBounds().width();
            if (event.getX() + 3 * width >= (mEt_store_more.getRight() - 1 / 2 * width)) {
                mZProgressHUD.show();
                mSeach = mEt_store_more.getText().toString();
                isMore = false;
                requestData();
                return true;
            }
        }

        return false;
    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof GoodsListImpl) {

            GoodsListImpl goodsList = (GoodsListImpl) data;
            Intent intent = new Intent(this, GoodDetailActivity.class);
            intent.putExtra(IntentDataKeyConstant.GOODS_ID, goodsList.getGoods_id());
            startActivity(intent);
        }
    }
}
