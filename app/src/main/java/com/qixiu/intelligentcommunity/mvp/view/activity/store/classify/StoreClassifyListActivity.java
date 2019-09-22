package com.qixiu.intelligentcommunity.mvp.view.activity.store.classify;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntConstant;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.PhoneBean;
import com.qixiu.intelligentcommunity.mvp.beans.parameter.BaseParameter;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreClassItemBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.classify.StoreClassifyBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.search.SearchHotBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.GoodDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.search.StoreSearchListActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreClassifuMenuAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.classify.StoreClassifyListAdapter;
import com.qixiu.intelligentcommunity.mvp.view.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.R.id.iv_nodata_showbg;
import static com.qixiu.intelligentcommunity.R.id.pushPrograssBar;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class StoreClassifyListActivity extends NewTitleActivity implements XRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, OKHttpUIUpdataListener, OnRecyclerItemListener, View.OnTouchListener {

    private XRecyclerView mRv_classifylist;
    private SwipeRefreshLayout mSrl_classifylist;
    private StoreClassifyListAdapter mStoreClassifyListAdapter;
    boolean isMore;
    private String mId;
    private OKHttpRequestModel mOkHttpRequestModel;
    private BaseParameter mBaseParameter;
    private ImageView mIv_nodata_showbg;
    private RecyclerView recyclerview_classify_menu;
    private StoreClassifuMenuAdapter storeClassifuMenuAdapter;
    private ImageView imageViewServicePhone, imageViewTicket;
    private EditText edittextSearchGoods;
    private PhoneBean phoneBean;
    private StoreClassItemBean storeClassItemBean;

    @Override
    protected void onInitData() {
        mId = getIntent().getStringExtra(IntentDataKeyConstant.ID);


        mOkHttpRequestModel = new OKHttpRequestModel(this);

        mBaseParameter = new BaseParameter();
        mBaseParameter.setPageSize(10);
        requestMenue();
        requestData();
        //作为分类列表时，需要在声明周期方法中做数据的初始化，搜索列表不需要
        //   filterReuqestData();

    }

    private void requestMenue() {
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_CLASSIFY, null, new StoreClassItemBean());
    }


    @Override
    protected void onInitViewNew() {
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setTitle("分类列表");
        mIv_nodata_showbg = (ImageView) findViewById(iv_nodata_showbg);
        mSrl_classifylist = (SwipeRefreshLayout) findViewById(R.id.srl_classifylist);
        mRv_classifylist = (XRecyclerView) findViewById(R.id.rv_classifylist);
        recyclerview_classify_menu = findViewById(R.id.recyclerview_classify_menu);
        imageViewServicePhone = findViewById(R.id.imageViewServicePhone);
        imageViewTicket = findViewById(R.id.imageViewTicket);
        edittextSearchGoods = findViewById(R.id.edittextSearchGoods);
        mRv_classifylist.addItemDecoration(new LinearSpacesItemDecoration(ArshowContextUtil.dp2px(1)));
        mSrl_classifylist.setOnRefreshListener(this);
        mRv_classifylist.setPullRefreshEnabled(false);
        mRv_classifylist.setLoadingListener(this);
        mRv_classifylist.setLoadingMoreEnabled(true);
        mRv_classifylist.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mRv_classifylist.setLayoutManager(new LinearLayoutManager(this));
        mStoreClassifyListAdapter = new StoreClassifyListAdapter();
        mRv_classifylist.setAdapter(mStoreClassifyListAdapter);
        mStoreClassifyListAdapter.setOnItemClickListener(this);
        recyclerview_classify_menu.setLayoutManager(new LinearLayoutManager(this));
        storeClassifuMenuAdapter = new StoreClassifuMenuAdapter();
        recyclerview_classify_menu.setAdapter(storeClassifuMenuAdapter);
        storeClassifuMenuAdapter.setOnItemClickListener(this);
        imageViewServicePhone.setOnClickListener(this);
        edittextSearchGoods.setFocusable(false);
        edittextSearchGoods.setEnabled(true);
        edittextSearchGoods.setOnTouchListener(this);
    }


    private void requestData() {

        if (!isMore) mBaseParameter.setPageNo(IntConstant.NUM_1);
        BaseBean baseBean = new StoreClassifyBean();
        //初始化数据操作
        Map<String, String> map = new HashMap();
        if (!TextUtils.isEmpty(mId)) {
            map.put(IntentDataKeyConstant.ID, mId);
        }
        map.put(IntentDataKeyConstant.PAGENO, mBaseParameter.getPageNo() + "");

//        if (keyWord != null) {
//            map.put(StringConstant.STRING_KEYWORDS, keyWord);
//        }

        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_CLASSIFYLIST, map, baseBean, true);


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_classifylist;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewServicePhone:
                mOkHttpRequestModel.okhHttpPost(ConstantUrl.callUrl, null, new PhoneBean(), true);
                break;
        }
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
        if (!isMore) {
            mSrl_classifylist.setRefreshing(false);
        } else {
            mRv_classifylist.loadMoreComplete();
        }

        if (data != null) {
            if (data instanceof StoreClassifyBean) {
                StoreClassifyBean storeClassifyBean = (StoreClassifyBean) data;
                StoreClassifyBean.StoreClassifyInfo storeClassifyInfo = storeClassifyBean.getO();
                List<StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean> list = storeClassifyInfo.getGoods().getList();
//                if (!StoreClassifyItemActivity.class.getSimpleName().equals(getTag())) {
//                    ll_search_tag.setVisibility(View.GONE);
//                    srl_storeclassify.setVisibility(View.VISIBLE);
//
//                }
                if (!isMore) {
                    if (list != null && list.size() <= 0) {
                        mIv_nodata_showbg.setVisibility(View.VISIBLE);
                    } else {
                        mStoreClassifyListAdapter.refreshData(list);
                        if (mIv_nodata_showbg.getVisibility() == View.VISIBLE) {
                            mIv_nodata_showbg.setVisibility(View.GONE);

                        }
                    }


                } else {
                    if (list.size() > 0) {
                        mStoreClassifyListAdapter.addDatas(list);
                    } else {
                        ToastUtil.toast("已经没有数据了");
                    }

                }
                mBaseParameter.setPageNo(mBaseParameter.getPageNo() + 1);

            } else if (data instanceof SearchHotBean) {
//                SearchHotBean storeClassifyBean = (SearchHotBean) data;
//                mSearchHotAdapter.refreshData(storeClassifyBean.getO().getKeywords());

            }

        }

        if (data instanceof PhoneBean) {
            phoneBean = (PhoneBean) data;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBean.getO()));
            if (hasPermission(new String[]{Manifest.permission.CALL_PHONE})) {
                getActivity().startActivity(intent);
            } else {
                hasRequse(0, new String[]{Manifest.permission.CALL_PHONE});
            }

        }

        if(data instanceof StoreClassItemBean){
            storeClassItemBean = (StoreClassItemBean) data;
            for (int j = 0; j < storeClassItemBean.getO().getCate().size(); j++) {
                StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean itemBean = storeClassItemBean.getO().getCate().get(j);
                if (itemBean.getId().equals(mId)) {
                    itemBean.setSelected(true);
                }
            }
            storeClassifuMenuAdapter.refreshData(storeClassItemBean.getO().getCate());
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (!isMore) {
            mSrl_classifylist.setRefreshing(false);
        } else {
            mRv_classifylist.loadMoreComplete();
        }

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (!isMore) {
            mSrl_classifylist.setRefreshing(false);
        } else {
            mRv_classifylist.loadMoreComplete();
        }
        mIv_nodata_showbg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean) {
            StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean goodListBean = (StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean) data;
            Intent intent = new Intent(this, GoodDetailActivity.class);
            intent.putExtra(IntentDataKeyConstant.GOODS_ID, goodListBean.getGoods_id());
            startActivity(intent);
        }
        if (data instanceof  StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean) {
            mStoreClassifyListAdapter.refreshData(null);
            StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean itemBean = ( StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean) data;
            for (int i = 0; i < storeClassItemBean.getO().getCate().size(); i++) {
                StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean itemBeanCurrent =storeClassItemBean.getO().getCate().get(i);
                itemBeanCurrent.setSelected(false);
            }
            itemBean.setSelected(true);
            storeClassifuMenuAdapter.notifyDataSetChanged();
            mId = itemBean.getId();
            requestData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && hasPermission(new String[]{Manifest.permission.CALL_PHONE})) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBean.getO()));
            getActivity().startActivity(intent);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(getContext(), StoreSearchListActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
