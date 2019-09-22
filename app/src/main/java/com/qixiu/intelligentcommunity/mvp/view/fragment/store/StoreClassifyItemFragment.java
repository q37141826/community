package com.qixiu.intelligentcommunity.mvp.view.fragment.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntConstant;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.OnClickItemListener;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.parameter.BaseParameter;
import com.qixiu.intelligentcommunity.mvp.beans.store.classify.StoreClassifyBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.search.SearchHotBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.GoodDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreClassifyItemActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.DefaultBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreClassifyAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.DefaultBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.SpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by gyh on 2017/5/2 0002.
 * 分类列表，搜索列表fragment，多个界面共用着一个fragment
 */

public class StoreClassifyItemFragment extends BaseFragment implements OnRecyclerItemListener, SwipeRefreshLayout.OnRefreshListener,
        OKHttpUIUpdataListener<BaseBean>,  AdapterView.OnItemClickListener, XRecyclerView.LoadingListener {


    private XRecyclerView mRl_storeclassify;
    private SwipeRefreshLayout srl_storeclassify;
    private String activityTag;
    private View ll_search_tag;
    private StoreClassifyAdapter mStoreClassifyAdapter;
    private OKHttpRequestModel mOkHttpRequestModel;
    private BaseParameter mBaseParameter;
    String id = null;
    private String keyWord;
    private GridView mGv_store_search;
    private SearchHotAdapter mSearchHotAdapter;
    private OnClickItemListener onClickHotItemListener;
    private ImageView iv_nodata_showbg;

    public void setLaunchActivityTag(String activityTag) {

        this.activityTag = activityTag;
    }


    public void setOnClickHotItemListener(OnClickItemListener onClickHotItemListener) {

        this.onClickHotItemListener = onClickHotItemListener;
    }

    /**
     * 不同界面请求数据的时机不同 需要抽取，及做判断
     */
    private void requestData() {
        if (!isMore) mBaseParameter.setPageNo(IntConstant.NUM_1);
        BaseBean baseBean = new StoreClassifyBean();
        //初始化数据操作
        Map<String, String> map = new HashMap();
        if (id != null) {
            map.put(IntentDataKeyConstant.ID, id);
        }
        map.put(IntentDataKeyConstant.PAGENO, mBaseParameter.getPageNo() + "");

        if (keyWord != null) {
            map.put(StringConstants.STRING_KEYWORDS, keyWord);
        }

        mOkHttpRequestModel.okhHttpPost(StoreClassifyItemActivity.class.getSimpleName().equals(getTag()) ? ConstantUrl.STORE_CLASSIFYLIST : ConstantUrl.STORE_SEARCHLIST, map, baseBean, true);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden && !StoreClassifyItemActivity.class.getSimpleName().equals(getTag())) {
            mSearchHotAdapter.refreshData(null);
            ll_search_tag.setVisibility(View.VISIBLE);
            srl_storeclassify.setVisibility(View.GONE);
            if (iv_nodata_showbg.getVisibility() == View.VISIBLE) {
                iv_nodata_showbg.setVisibility(View.GONE);
            }

        } else {
            filterReuqestData();

        }
    }

    @Override
    protected void onInitData() {
        Bundle arguments = getArguments();

        if (arguments != null) {
            id = arguments.getString(IntentDataKeyConstant.ID);
        }
        mOkHttpRequestModel = new OKHttpRequestModel(this);

        mBaseParameter = new BaseParameter();
        mBaseParameter.setPageSize(10);
        //作为分类列表时，需要在声明周期方法中做数据的初始化，搜索列表不需要
        filterReuqestData();

    }

    private void filterReuqestData() {
        if (StoreClassifyItemActivity.class.getSimpleName().equals(getTag())) {
            requestData();
        } else {
            requestHotSearch();
        }
    }

    private void requestHotSearch() {
        BaseBean searchHotBean = new SearchHotBean();
        searchHotBean.setUrl(ConstantUrl.STORE_SEARCHHOT_KEYWORDS);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SEARCHHOT_KEYWORDS, null, searchHotBean, true);
    }

    @Override
    protected void onInitView(View view) {
        mRl_storeclassify = (XRecyclerView) view.findViewById(R.id.rl_storeclassify);
        srl_storeclassify = (SwipeRefreshLayout) view.findViewById(R.id.srl_storeclassify);
        iv_nodata_showbg = (ImageView) view.findViewById(R.id.iv_nodata_showbg);

        ll_search_tag = view.findViewById(R.id.ll_search_tag);

        mGv_store_search = (GridView) view.findViewById(R.id.gv_store_search);
        mRl_storeclassify.setPullRefreshEnabled(false);
        mRl_storeclassify.setHasFixedSize(true);

        mRl_storeclassify.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mRl_storeclassify.setLoadingListener(this);

        srl_storeclassify.setColorSchemeResources(R.color.green);


        mGv_store_search.setOnItemClickListener(this);
        mSearchHotAdapter = new SearchHotAdapter();
        mGv_store_search.setAdapter(mSearchHotAdapter);
        srl_storeclassify.setOnRefreshListener(this);


        initRv();


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        keyWord = (String) parent.getItemAtPosition(position);
        if (onClickHotItemListener != null) {
            onClickHotItemListener.onClickItem(keyWord);
        }

        requestData();
    }

    private static class SearchHotAdapter extends DefaultBaseAdapter {

        @Override
        public int getLayoutId() {
            return R.layout.item_storeclassify_gv;
        }

        @Override
        public Object createViewHolder(View itemView, Context context, int viewType) {
            return new SearchHotHolder(itemView);
        }


        private static class SearchHotHolder extends DefaultBaseHolder {

            private final TextView mTv_searchhot;

            public SearchHotHolder(View contentView) {
                super(contentView);
                mTv_searchhot = (TextView) contentView.findViewById(R.id.tv_searchhot);
            }

            @Override
            public void setData(Object data) {
                if (data instanceof String) {
                    String title = (String) data;
                    mTv_searchhot.setText(title);
                }

            }
        }
    }

    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //  Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.shape_divider_line);
        mRl_storeclassify.addItemDecoration(new SpacesItemDecoration(ArshowContextUtil.dp2px(1)));
        mStoreClassifyAdapter = new StoreClassifyAdapter();
        mStoreClassifyAdapter.setOnItemClickListener(this);
        mRl_storeclassify.setLayoutManager(linearLayoutManager);
        mRl_storeclassify.setAdapter(mStoreClassifyAdapter);

    }

    @Override
    protected int getLayoutResource() {

        return R.layout.fragment_storeclassify;
    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean) {
            StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean goodListBean = (StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean) data;
            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
            intent.putExtra(IntentDataKeyConstant.GOODS_ID, goodListBean.getGoods_id());
            startActivity(intent);

        }

        //进入商品详情页
    }

    @Override
    public void onRefresh() {
        isMore = false;
        requestData();
        //初始化数据
    }

    @Override
    public void onLoadMore() {
        isMore = true;
        requestData();
    }

    private boolean isMore;

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (!isMore) {
            srl_storeclassify.setRefreshing(false);
        } else {
            mRl_storeclassify.loadMoreComplete();
        }

        if (data != null) {
            if (data instanceof StoreClassifyBean) {
                StoreClassifyBean storeClassifyBean = (StoreClassifyBean) data;
                StoreClassifyBean.StoreClassifyInfo storeClassifyInfo = storeClassifyBean.getO();
                List<StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean> list = storeClassifyInfo.getGoods().getList();
                if (!StoreClassifyItemActivity.class.getSimpleName().equals(getTag())) {
                    ll_search_tag.setVisibility(View.GONE);
                    srl_storeclassify.setVisibility(View.VISIBLE);

                }
                if (!isMore) {
                    if (list != null && list.size() <= 0) {
                        iv_nodata_showbg.setVisibility(View.VISIBLE);
                    } else if (iv_nodata_showbg.getVisibility() == View.VISIBLE) {
                        iv_nodata_showbg.setVisibility(View.GONE);
                    }
                    mStoreClassifyAdapter.refreshData(list);
                } else {
                    if (list.size() > 0) {
                        mStoreClassifyAdapter.addDatas(list);
                    } else {
                        ToastUtil.toast("已经没有数据了");
                    }

                }
                mBaseParameter.setPageNo(mBaseParameter.getPageNo() + 1);

            } else if (data instanceof SearchHotBean) {
                SearchHotBean storeClassifyBean = (SearchHotBean) data;
                mSearchHotAdapter.refreshData(storeClassifyBean.getO().getKeywords());

            }

        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (!isMore) {
            srl_storeclassify.setRefreshing(false);
        } else {
            mRl_storeclassify.loadMoreComplete();
        }

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (!ConstantUrl.STORE_SEARCHHOT_KEYWORDS.equals(c_codeBean.getUrl())) {
            mStoreClassifyAdapter.refreshData(null);
            iv_nodata_showbg.setVisibility(View.VISIBLE);
        }
        if (!isMore) {
            srl_storeclassify.setRefreshing(false);
        } else {
            mRl_storeclassify.loadMoreComplete();
        }
    }

}
