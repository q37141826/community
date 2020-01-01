package com.qixiu.intelligentcommunity.mvp.view.fragment.store;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.parameter.BaseParameter;
import com.qixiu.intelligentcommunity.mvp.beans.store.ClassifyIntef;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreClassItemBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.ToSeeAllBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.GoodDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreMoreActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreShopCarActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.classify.StoreAllClassifyActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.classify.StoreClassifyListActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.search.StoreSearchListActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreClassifyAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreClassifyAdapterNew;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.classify.StoreAllClassifyAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.itemdecoration.DashlineItemDivider;
import com.qixiu.intelligentcommunity.mvp.view.itemdecoration.DiyDividerItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.rollpage.ImageUrlAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.XrecyclerViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class StoreFragment extends BaseFragment implements XRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, OKHttpUIUpdataListener, OnItemClickListener, OnRecyclerItemListener, View.OnTouchListener {

    private XRecyclerView mRv_storelist;
    private StoreAdapter mStoreAdapter;
    private SwipeRefreshLayout mSrl_store;
    private OKHttpRequestModel mOkHttpRequestModel;
    private BaseParameter mBaseParameter;
    private RollPagerView mRoll_view_pager_home;
    private ImageUrlAdapter mImageUrlAdapter;

    private EditText et_store_title;
    private StoreBean storeBean;
    StoreClassifyAdapterNew mStoreAllClassifyAdapter;
    RecyclerView recyclerviewHomeStoreClassify;

    @Override
    protected void onInitData() {
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        mBaseParameter = new BaseParameter();
        mStoreAllClassifyAdapter = new StoreClassifyAdapterNew();
        recyclerviewHomeStoreClassify.setAdapter(mStoreAllClassifyAdapter);
        mStoreAllClassifyAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onInitView(View view) {

        mSrl_store = (SwipeRefreshLayout) view.findViewById(R.id.srl_store);
        mSrl_store.setOnRefreshListener(this);

        initRv(view);


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isMore = false;
            getData();
        }
    }

    /**
     * 初始化XRecyclerView
     *
     * @param view
     */
    private void initRv(View view) {
        /**
         *上面布局作为头部添加到XRecyclerView中
         */
        View storeHeadView = View.inflate(view.getContext(), R.layout.layout_storehead, null);
        initAdv(storeHeadView);
//        mLl_dressup = storeHeadView.findViewById(R.id.ll_dressup);
//        mLl_bag = storeHeadView.findViewById(R.id.ll_bag);
//        mLl_leatherboot = storeHeadView.findViewById(R.id.ll_leatherboot);
//        mLl_shorts = storeHeadView.findViewById(R.id.ll_shorts);
//        mLl_equally = storeHeadView.findViewById(R.id.ll_equally);
//        mLl_all = storeHeadView.findViewById(R.id.ll_all);
//
//        mIv_dressup = (ImageView) storeHeadView.findViewById(R.id.iv_dressup);
//        mIv_bag = (ImageView) storeHeadView.findViewById(R.id.iv_bag);
//        mIv_leatherboot = (ImageView) storeHeadView.findViewById(R.id.iv_leatherboot);
//        mIv_shorts = (ImageView) storeHeadView.findViewById(R.id.iv_shorts);
//        mIv_equally = (ImageView) storeHeadView.findViewById(R.id.iv_equally);
//
//
//        mTv_dressup = (TextView) storeHeadView.findViewById(R.id.tv_dressup);
//        mTv_bag = (TextView) storeHeadView.findViewById(R.id.tv_bag);
//        mTv_leatherboot = (TextView) storeHeadView.findViewById(R.id.tv_leatherboot);
        et_store_title = storeHeadView.findViewById(R.id.et_store_title);
//        mTv_shorts = (TextView) storeHeadView.findViewById(R.id.tv_shorts);
//        mTv_equally = (TextView) storeHeadView.findViewById(R.id.tv_equally);
//        mTv_all = (TextView) storeHeadView.findViewById(R.id.tv_all);
        View rl_show_more = storeHeadView.findViewById(R.id.rl_show_more);
        et_store_title.setFocusable(false);
        et_store_title.setOnTouchListener(this);

//        mLl_dressup.setOnClickListener(this);
//        mLl_bag.setOnClickListener(this);
//        mLl_leatherboot.setOnClickListener(this);
//        mLl_shorts.setOnClickListener(this);
//        mLl_equally.setOnClickListener(this);
//        mLl_all.setOnClickListener(this);
        rl_show_more.setOnClickListener(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            childAt.setCompoundDrawablesWithIntrinsicBounds(0, view == childAt ? selectResourceid[i] : defaultResourceid[i], 0, 0);
//        } else {
//            childAt.setCompoundDrawables(null, getResources().getDrawable(view == childAt ? selectResourceid[i] : defaultResourceid[i]), null, null);
//        }
        mRv_storelist = (XRecyclerView) view.findViewById(R.id.rv_storelist);
        mRv_storelist.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mStoreAdapter = new StoreAdapter();
        mRv_storelist.addHeaderView(storeHeadView);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        XrecyclerViewUtil.setXrecyclerView(mRv_storelist, this, getContext(), false, 1, manager);
        mRv_storelist.setAdapter(mStoreAdapter);
        mStoreAdapter.setOnItemClickListener(this);
        initStoreTitleView(storeHeadView);
    }

    private void initStoreTitleView(View storeHeadView) {

        View rl_shopcar = storeHeadView.findViewById(R.id.rl_shopcar);
        rl_shopcar.setOnClickListener(this);
    }

    /**
     * 初始化轮播图的View
     *
     * @param view
     */
    private void initAdv(View view) {
        RollPagerView rpv_home_adv = (RollPagerView) view.findViewById(R.id.roll_view_pager_home);
        rpv_home_adv.setHintView(new ColorPointHintView(getActivity(), getResources().getColor(R.color.white), getResources().getColor(R.color.home_back)));
        mImageUrlAdapter = new ImageUrlAdapter(rpv_home_adv);
        rpv_home_adv.setAdapter(mImageUrlAdapter);
        ViewGroup.LayoutParams layoutParams = rpv_home_adv.getLayoutParams();
        layoutParams.height = ArshowContextUtil.getWidthPixels() / 2;
        rpv_home_adv.setOnItemClickListener(this);
        rpv_home_adv.setLayoutParams(layoutParams);
        recyclerviewHomeStoreClassify = view.findViewById(R.id.recyclerviewHomeStoreClassify);
        recyclerviewHomeStoreClassify.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main_store;
    }

    @Override
    public void onRefresh() {
        isMore = false;
        getData();
    }

    @Override
    public void onLoadMore() {
        isMore = true;
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.ll_dressup:
//            case R.id.ll_bag:
//            case R.id.ll_leatherboot:
//            case R.id.ll_shorts:
//            case R.id.ll_equally:
//                Object tag = v.getTag(R.id.tag_store_classify);
//                if (tag == null || TextUtils.isEmpty(String.valueOf(tag))) {
//                    return;
//                }
//                String id = String.valueOf(tag);
//                Intent classifyListIntent = new Intent(getActivity(), StoreClassifyListActivity.class);
//                classifyListIntent.putExtra(IntentDataKeyConstant.ID, id);
//                classifyListIntent.putExtra(IntentDataKeyConstant.DATA, storeBean.getO());
//                startActivity(classifyListIntent);
//                break;

            case R.id.rl_show_more:
                Intent showMoreIntent = new Intent(getActivity(), StoreClassifyListActivity.class);
                startActivity(showMoreIntent);
                break;
            case R.id.rl_shopcar:

                Intent intent = new Intent(getContext(), StoreShopCarActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSuccess(Object data, int i) {

        if (!isMore) {
            mSrl_store.setRefreshing(false);
        } else {
            mRv_storelist.loadMoreComplete();
        }
        if (data instanceof StoreBean) {
            storeBean = (StoreBean) data;
            StoreBean.StoreInfo storeInfo = storeBean.getO();
            if (!isMore) {
                //显示广告数据
                disposeAdv(storeInfo.getAd());
                //显示商城分类数据
                disposeClass(storeInfo.getCate());
            }
            //显示商品列表数据
            disposeGoodsList(storeInfo.getGoods());
        }
    }

    private void disposeClass(List<StoreBean.StoreInfo.ClassifyItemBean> cate) {
        List<ClassifyIntef> datas = new ArrayList<>();
        datas.addAll(cate);
        ToSeeAllBean toSeeAllBean = new ToSeeAllBean();
        toSeeAllBean.setName("全部分类");
        toSeeAllBean.setImageRes(R.mipmap.all_calssify);
        datas.add(toSeeAllBean);
        mStoreAllClassifyAdapter.refreshData(datas);
    }

//    private void disposeClass(List<StoreBean.StoreInfo.ClassifyItemBean> cate) {
//
//
//        for (int i = 0; i < cate.size(); i++) {
//            switch (i) {
//                case 0:
//                    mTv_dressup.setText(cate.get(i).getName());
//                    mLl_dressup.setTag(R.id.tag_store_classify, cate.get(i).getId());
//                    Glide.with(this).load(ConstantUrl.hosturl + cate.get(i).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).crossFade().into(mIv_dressup);
//                    break;
//
//                case 1:
//                    mTv_bag.setText(cate.get(i).getName());
//                    mLl_bag.setTag(R.id.tag_store_classify, cate.get(i).getId());
//                    Glide.with(this).load(ConstantUrl.hosturl + cate.get(i).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).crossFade().into(mIv_bag);
//                    break;
//
//                case 2:
//
//                    mTv_leatherboot.setText(cate.get(i).getName());
//                    mLl_leatherboot.setTag(R.id.tag_store_classify, cate.get(i).getId());
//                    Glide.with(this).load(ConstantUrl.hosturl + cate.get(i).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).crossFade().into(mIv_leatherboot);
//                    break;
//
//                case 3:
//                    mTv_shorts.setText(cate.get(i).getName());
//                    mLl_shorts.setTag(R.id.tag_store_classify, cate.get(i).getId());
//                    Glide.with(this).load(ConstantUrl.hosturl + cate.get(i).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).crossFade().into(mIv_shorts);
//                    break;
//
//                case 4:
//                    mTv_equally.setText(cate.get(i).getName());
//                    mLl_equally.setTag(R.id.tag_store_classify, cate.get(i).getId());
//                    Glide.with(this).load(ConstantUrl.hosturl + cate.get(i).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).crossFade().into(mIv_equally);
//                    break;
//
//            }
//        }
//    }

    private void disposeGoodsList(StoreBean.StoreInfo.GoodsBean goods) {
        if (goods.getList().size() <= 0) {
            if (isMore) {
                ToastUtil.toast("没有更多数据啦");
            }
        } else {

            if (!isMore) {

                mStoreAdapter.refreshData(goods.getList());

            } else {

                mStoreAdapter.addDatas(goods.getList());

            }
            mBaseParameter.setPageNo(mBaseParameter.getPageNo() + 1);
        }


    }

    private void disposeAdv(List<StoreBean.StoreInfo.AdvBean> ad) {
        if (ad != null) {
            List<String> strings = new ArrayList<>();

            for (int i = 0; i < ad.size(); i++) {
                strings.add(ConstantUrl.hosturl + ad.get(i).getAd_code());
            }
            mImageUrlAdapter.refreshData(strings);
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (!isMore) {
            mSrl_store.setRefreshing(false);
        } else {
            mRv_storelist.loadMoreComplete();
        }
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (!isMore) {
            mSrl_store.setRefreshing(false);
        } else {
            mRv_storelist.loadMoreComplete();
        }
    }

    private boolean isMore;

    public void getData() {
        Map<String, String> stringMap = new HashMap<>();
        if (!isMore) {
            mBaseParameter.setPageNo(1);
        }
        stringMap.put(IntentDataKeyConstant.PAGENO, mBaseParameter.getPageNo() + StringConstants.EMPTY_STRING);
        stringMap.put(IntentDataKeyConstant.PAGESIZE, mBaseParameter.getPageSize() + StringConstants.EMPTY_STRING);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SHOP, stringMap, new StoreBean());
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof StoreBean.StoreInfo.GoodsBean.GoodsListBean) {
            StoreBean.StoreInfo.GoodsBean.GoodsListBean goodsListBean = (StoreBean.StoreInfo.GoodsBean.GoodsListBean) data;

            Intent intent = new Intent(getActivity(), GoodDetailActivity.class);

            intent.putExtra(IntentDataKeyConstant.GOODS_ID, goodsListBean.getGoods_id());
            startActivity(intent);
        }
        if (data instanceof ToSeeAllBean) {
            Intent allClassifyIntent = new Intent(getActivity(), StoreAllClassifyActivity.class);
            startActivity(allClassifyIntent);
        }
        if (data instanceof StoreBean.StoreInfo.ClassifyItemBean) {
            StoreBean.StoreInfo.ClassifyItemBean bean = (StoreBean.StoreInfo.ClassifyItemBean) data;
            Intent classifyListIntent = new Intent(getActivity(), StoreClassifyListActivity.class);
            classifyListIntent.putExtra(IntentDataKeyConstant.ID, bean.getId());
            startActivity(classifyListIntent);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(getContext(), StoreSearchListActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
