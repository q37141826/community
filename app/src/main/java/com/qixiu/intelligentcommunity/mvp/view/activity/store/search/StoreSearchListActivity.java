package com.qixiu.intelligentcommunity.mvp.view.activity.store.search;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.parameter.BaseParameter;
import com.qixiu.intelligentcommunity.mvp.beans.store.classify.StoreClassifyBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.impl.GoodsListImpl;
import com.qixiu.intelligentcommunity.mvp.beans.store.search.SearchHotBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.GoodDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreShopCarActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.R.id.iv_nodata_showbg;
import static com.qixiu.intelligentcommunity.R.id.ll_search_tag;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class StoreSearchListActivity extends NewTitleActivity implements TagFlowLayout.OnTagClickListener, SwipeRefreshLayout.OnRefreshListener, XRecyclerView.LoadingListener, OKHttpUIUpdataListener, OnRecyclerItemListener {

    private TagFlowLayout mTfl_lately_visit;
    private SwipeRefreshLayout mSrl_storesearch;
    private StoreAdapter mStoreAdapter;
    private ZProgressHUD mZProgressHUD;
    private OKHttpRequestModel mOkHttpRequestModel;
    private BaseParameter mBaseParameter;
    private View mLl_search_tag;
    private boolean isMore;
    private ImageView mIv_nodata_showbg;
    private XRecyclerView mRl_storesearch;
    private List<String> mKeywords;
    private EditText mEt_search;
    private String mEt_keywords;

    @Override
    protected void onInitViewNew() {
        mZProgressHUD = new ZProgressHUD(this);
        mTitleView.setTitle("商城");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setRightImage(R.mipmap.shopcar_white);
        mTitleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StoreSearchListActivity.this, StoreShopCarActivity.class);
                startActivity(intent);
            }
        });

        mTfl_lately_visit = (TagFlowLayout) findViewById(R.id.tfl_lately_visit);
        mTfl_lately_visit.setOnTagClickListener(this);


        mEt_search = (EditText) findViewById(R.id.et_search);
        View tv_search = findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        mLl_search_tag = findViewById(ll_search_tag);
        mIv_nodata_showbg = (ImageView) findViewById(iv_nodata_showbg);
        mSrl_storesearch = (SwipeRefreshLayout) findViewById(R.id.srl_storesearch);
        mSrl_storesearch.setOnRefreshListener(this);
        mRl_storesearch = (XRecyclerView) findViewById(R.id.rl_storesearch);
        mRl_storesearch.setPullRefreshEnabled(false);
        mRl_storesearch.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, ArshowContextUtil.dp2px(1)));
        mRl_storesearch.setLoadingListener(this);
        mRl_storesearch.setLoadingMoreEnabled(true);
        mRl_storesearch.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mRl_storesearch.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mStoreAdapter = new StoreAdapter();
        mStoreAdapter.setOnItemClickListener(this);
        mRl_storesearch.setAdapter(mStoreAdapter);
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        if (mKeywords != null && position < mKeywords.size()) {
            mEt_search.setText(mKeywords.get(position));
            mEt_search.setSelection(mKeywords.get(position).length());
            mZProgressHUD.show();
            isMore = false;
            requestListData();
            return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        if (mSrl_storesearch.getVisibility() == View.VISIBLE) {
            isMore = false;

            requestListData();
        }
    }

    @Override
    public void onLoadMore() {
        if (mSrl_storesearch.getVisibility() == View.VISIBLE) {
            isMore = true;
            requestListData();
        }
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }

        if (!isMore) {
            mSrl_storesearch.setRefreshing(false);
        } else {
            mRl_storesearch.loadMoreComplete();
        }

        if (data instanceof SearchHotBean) {
            SearchHotBean storeClassifyBean = (SearchHotBean) data;
            mKeywords = storeClassifyBean.getO().getKeywords();
            mTfl_lately_visit.setAdapter(new SearchFlowAdapter(mKeywords));
        } else if (data instanceof StoreClassifyBean) {
            StoreClassifyBean storeClassifyBean = (StoreClassifyBean) data;
            StoreClassifyBean.StoreClassifyInfo storeClassifyInfo = storeClassifyBean.getO();
            List<StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean> list = storeClassifyInfo.getGoods().getList();
            mLl_search_tag.setVisibility(View.GONE);
            mSrl_storesearch.setVisibility(View.VISIBLE);


            if (!isMore) {
                if (list != null && list.size() <= 0) {
                    mIv_nodata_showbg.setVisibility(View.VISIBLE);
                } else if (mIv_nodata_showbg.getVisibility() == View.VISIBLE) {
                    mIv_nodata_showbg.setVisibility(View.GONE);
                }
                mStoreAdapter.refreshData(list);
            } else {
                if (list.size() > 0) {
                    mStoreAdapter.addDatas(list);
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
            mSrl_storesearch.setRefreshing(false);
        } else {
            mRl_storesearch.loadMoreComplete();
        }
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (!isMore) {
            mSrl_storesearch.setRefreshing(false);
        } else {
            mRl_storesearch.loadMoreComplete();
        }
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }
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

    private class SearchFlowAdapter extends TagAdapter<String> {

        public SearchFlowAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String data) {

            TextView textView = (TextView) View.inflate(parent.getContext(), R.layout.item_history_visit, null);
            textView.setText(data);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);

            return textView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                mZProgressHUD.show();
                isMore = false;
                requestListData();

                break;


        }
    }

    @Override
    protected void onInitData() {
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        mBaseParameter = new BaseParameter();
        requestHotData();

    }

    private void requestListData() {
        mEt_keywords = mEt_search.getText().toString();
        if (!isMore) {
            mBaseParameter.setPageNo(1);
        }
        if (TextUtils.isEmpty(mEt_keywords)) {
            mEt_keywords = StringConstants.EMPTY_STRING;
        }
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("keywords", mEt_keywords);
        stringMap.put(IntentDataKeyConstant.PAGENO, mBaseParameter.getPageNo() + StringConstants.EMPTY_STRING);
        stringMap.put(IntentDataKeyConstant.PAGESIZE, mBaseParameter.getPageSize() + StringConstants.EMPTY_STRING);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SEARCHLIST, stringMap, new StoreClassifyBean());
    }

    private void requestHotData() {

        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SEARCHHOT_KEYWORDS, null, new SearchHotBean());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_store_search;
    }
}
