package com.qixiu.intelligentcommunity.mvp.view.fragment.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clj.fastble.data.ScanResult;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.Util;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothService;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothServiceBle;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothUtil;
import com.qixiu.intelligentcommunity.bluetooth.IBluetoothServiceEventReceiver;
import com.qixiu.intelligentcommunity.bluetooth.ReleseSingle;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.BluetoothEngine;
import com.qixiu.intelligentcommunity.listener.MainBlueToothIntf;
import com.qixiu.intelligentcommunity.listener.OnClickSwitchListener;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothMatchBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomeHotGoodsBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomeNoticeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.NewsStateBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreBean;
import com.qixiu.intelligentcommunity.mvp.model.home_modle.HomeSelectorModle;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.BlueToothManageActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.NewWuyePayActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.WuyePayActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.get_goods.GetGoodsActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.BrowserInnerActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.HomeWebActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.OwnerEventDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.GoodDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreMoreActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.classify.StoreClassifyListActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.EventAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.TestNormalAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.StoreAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.VerticalSwipeRefreshLayout;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.rollpage.ImageUrlAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.rollpage.UserSelectorAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.XrecyclerViewUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadCommunityManager;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadGame;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadHelpUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadHousePropertyUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadNeighbourUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadNewsUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadNoticeUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadRepairUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadSecondhandMarketUrl;


/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class HomeFragment extends BaseFragment implements OKHttpUIUpdataListener<BaseBean>, View.OnClickListener, IBluetoothServiceEventReceiver, BluetoothService.ConnectCallBack,
        OnRecyclerItemListener<HomeBean.EBean>, XRecyclerView.LoadingListener {

    private LinearLayout linearlayout_onkeyCall, linearlayout_progress_circle;
    private RollPagerView roll_view_pager_home;//轮播图控件
    private ImageUrlAdapter mHomeAdvAdapter;
    private StoreAdapter mStoreAdapter;
    //测试用的轮播图
    private List<String> images = new ArrayList<>();
    //活动图
//    private List<String> eventImages = new ArrayList<>();
//    private List<View> eventPager = new ArrayList<>();
    private int pageNo = 1, pageSize = 10;
    private RecyclerView recyclerview_event_home;
    private XRecyclerView recyclerview_home_hot_goods;
    //适配器
    private EventAdapter eventAdapter;
    //游戏图
    ImageView imageView_game_home;
    //    private ViewPager viewPager_event_home;
    //缴费等业务点击的图标
    private TextView textView_wuyePay, textView_carPay, textView_fixonline_homefragment, textView_goodsget_home;
    private TextView mTv_neighborhood;
    private ImageView mImageView_onkeyOpen, imageView_commnityManager_home;
    private ZProgressHUD mZProgressHUD;
    private TextView mTv_seekhelp;
    //公告新闻状态图标
    private ImageView imageView_home_have_notice, imageView_home_have_news, imageView_hotgoods;

    //活动区导航图标
    List<ImageView> progresImages = new ArrayList<>();
    private int recyclerState = 0;  //活动区滑动监听状态；
    private int recyclelength = 0;//活动区滑动距离
    private boolean IS_FIRST_IN = true;

    private BluetoothEngine mBluetoothEngine;
    private OKHttpRequestModel mOkHttpRequestModel;
    private TextView mTv_second_hand_market;
    private View mImageView_notice_home;
    private View mImageView_news_home;
    private TextView mTv_houseproperty_lease;
    private ImageView mImageView_event_home;
    private OnClickSwitchListener onClickSwitchListener;
    private boolean IS_SIDE = false;

    private String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    //头部隐藏的线程对象、
    private VisbleRunnable runnable;
    private RollPagerView roll_user_selector;
    private TextView textViewNoticeMsg;
    private TextView textViewNoticeDate;
    private TextView textViewNews;
    private TextView textViewNewsDate;
    private TextView textViewMoreEvent;
    private TextView textViewMoreGoods;

    public void setBlueToothIntf(MainBlueToothIntf blueToothIntf) {
        this.blueToothIntf = blueToothIntf;
    }

    private MainBlueToothIntf blueToothIntf;


    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        this.onClickSwitchListener = onClickSwitchListener;
    }

    //首页刷新
    private VerticalSwipeRefreshLayout swip_home;

    @Override
    protected void onInitData() {

        mOkHttpRequestModel = new OKHttpRequestModel(this);
        getGoodsData();
        eventAdapter = new EventAdapter();
        eventAdapter.setOnItemClickListener(this);
        recyclerview_event_home.setAdapter(eventAdapter);

        //设置游戏区图像
        setGameCenter();
        //设置新闻状态
        setNewsState();
        swip_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                onInitData();
            }
        });
        mStoreAdapter.setOnItemClickListener(new OnRecyclerItemListener() {
            @Override
            public void onItemClick(View v, Object data) {
                StoreBean.StoreInfo.GoodsBean.GoodsListBean goodsListBean = (StoreBean.StoreInfo.GoodsBean.GoodsListBean) data;
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                intent.putExtra(IntentDataKeyConstant.GOODS_ID, goodsListBean.getGoods_id());
                startActivity(intent);
            }
        });
        //头部滚动图
        requestHeadBanner();
        requestNoticeData();
    }

    private void requestNoticeData() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", Preference.get(ConstantString.USERID, ""));
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.homeNoticeUrl, map, new HomeNoticeBean());
    }

    private void requestHeadBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", Preference.get(ConstantString.USERID, "1"));
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.homeUrl, map, new HomeBean());
    }

    private void setGameCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(windowWith - ArshowContextUtil.dp2px(10), (windowWith - ArshowContextUtil.dp2px(10)) * 276 / 1085);
        imageView_game_home.setScaleType(ImageView.ScaleType.CENTER_CROP);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView_game_home.setLayoutParams(params);
        imageView_game_home.setImageResource(R.mipmap.home_game);
    }

    private void setNewsState() {
        OkHttpUtils.post().url(ConstantUrl.newsStateUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    NewsStateBean newsStateBean = GetGson.init().fromJson(s, NewsStateBean.class);
                    if (newsStateBean.getO().getNew_type() == 1) {
                        imageView_home_have_news.setVisibility(View.VISIBLE);
                    } else {
                        imageView_home_have_news.setVisibility(View.GONE);
                    }
                    if (newsStateBean.getO().getPost_type() == 1) {
                        imageView_home_have_notice.setVisibility(View.VISIBLE);
                    } else {
                        imageView_home_have_notice.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setEventProgress() {
        //设置活动区导航图样式
        //如果是刷新动作，先移除所有，偏移量清零
        progresImages.clear();
        linearlayout_progress_circle.removeAllViews();
        recyclelength = 0;
        IS_FIRST_IN = true;
        if (eventAdapter.getDatas().size() != 0) {
            for (int i = 0; i < (eventAdapter.getDatas().size()); i++) {
                ImageView imageprogress = new ImageView(getContext());
                progresImages.add(imageprogress);
                if (i == 0) {
                    imageprogress.setImageResource(R.mipmap.tuoyuan_black3x);
                } else {
                    imageprogress.setImageResource(R.mipmap.circle_black3x);
                }
                linearlayout_progress_circle.addView(imageprogress);
            }
            ((ImageView) linearlayout_progress_circle.getChildAt(0)).setImageResource(R.mipmap.tuoyuan_black3x);
            recyclerview_event_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    recyclerState = newState;
                    IS_FIRST_IN = false;
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    try {
                        if (recyclerState != 0) {
                            recyclelength += dx;//如果还在滑动，就不停地加上滑动距离
                        }
                        int position = (recyclelength + windowWith * 2 / 3) / windowWith;
                        if (position >= progresImages.size()) {
                            setProgress(progresImages.size() - 1);
                        } else if (position < 0) {
                            setProgress(0);
                        } else {
                            setProgress((recyclelength + windowWith * 2 / 3) / windowWith);
                        }
                        if (IS_FIRST_IN) {
                            IS_FIRST_IN = false;
                            setProgress(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setProgress(int position) {
        for (int i = 0; i < progresImages.size(); i++) {
            LinearLayout.LayoutParams paras = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paras.setMargins(5, 0, 5, 0);
            progresImages.get(i).setLayoutParams(paras);
            if (i == position) {
                progresImages.get(position).setImageResource(R.mipmap.tuoyuan_black3x);
            } else {
                progresImages.get(i).setImageResource(R.mipmap.circle_black3x);
            }
        }
    }

    /**
     * 对轮播图的数据进行处理
     *
     * @param info
     */
    private void disposeAdv(List<String> info) {
        mHomeAdvAdapter.refreshData(info);
    }

//    private void initBluetoothInfo() {
//        mBluetoothEngine = new BluetoothEngine(callback);
//        BluetoothService.registerBroadcastReceiver(getActivity());
//    }

    @Override
    protected void onInitView(View view) {
        mStoreAdapter = new StoreAdapter();
        swip_home = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swip_home);
        recyclerview_home_hot_goods = (XRecyclerView) view.findViewById(R.id.recyclerview_home_hot_goods);
        View headView = View.inflate(getContext(), R.layout.home_fragment_head, null);
        initHeadView(headView);
        recyclerview_home_hot_goods.addHeaderView(headView);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        XrecyclerViewUtil.setXrecyclerView(recyclerview_home_hot_goods, this, getContext(), false, 1, manager);
        recyclerview_home_hot_goods.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);

        recyclerview_home_hot_goods.setAdapter(mStoreAdapter);
        BluetoothService.initialize(getActivity().getApplicationContext(), this);
//        initBluetoothInfo();

    }


    private void initHeadView(View view) {
        imageView_hotgoods = (ImageView) view.findViewById(R.id.imageView_hotgoods);
        imageView_home_have_notice = (ImageView) view.findViewById(R.id.imageView_home_have_notice);
        imageView_home_have_news = (ImageView) view.findViewById(R.id.imageView_home_have_news);
        linearlayout_progress_circle = (LinearLayout) view.findViewById(R.id.linearlayout_progress_circle);
        mZProgressHUD = new ZProgressHUD(getActivity());
        mZProgressHUD.setMessage("扫描中");
        imageView_commnityManager_home = (ImageView) view.findViewById(R.id.imageView_commnityManager_home);
        imageView_game_home = (ImageView) view.findViewById(R.id.imageView_game_home);
        mImageView_onkeyOpen = view.findViewById(R.id.imageView_onkeyOpen);
        mImageView_notice_home = view.findViewById(R.id.imageView_notice_home);
        mImageView_news_home = view.findViewById(R.id.imageView_news_home);
        mImageView_event_home = (ImageView) view.findViewById(R.id.imageView_event_home);
        recyclerview_event_home = (RecyclerView) view.findViewById(R.id.recyclerview_event_home);
        linearlayout_onkeyCall = (LinearLayout) view.findViewById(R.id.linearlayout_onkeyCall);
        textViewMoreEvent = view.findViewById(R.id.textViewMoreEvent);
        textViewMoreGoods = view.findViewById(R.id.textViewMoreGoods);
        //社区业务板块
        initSevice(view);

//        roll_view_pager_home.setHintView(new IconHintView(getContext(), R.mipmap.main_bottom_ownercircle, R.mipmap.main_bottom_ownercircle_select));
//        viewPager_event_home = (ViewPager) view.findViewById(R.id.viewPager_event_home);
//        viewPager_event_home.setOffscreenPageLimit(3); // viewpager缓存页数
//        viewPager_event_home.setPageMargin(30); // 设置各页面的间距
        //轮播页
        initLoopPage(view);
        initclick();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview_event_home.setLayoutManager(manager);
        initNotice(view);
    }

    private void initNotice(View view) {
        textViewNoticeMsg = view.findViewById(R.id.textViewNoticeMsg);
        textViewNoticeDate = view.findViewById(R.id.textViewNoticeDate);
        textViewNews = view.findViewById(R.id.textViewNews);
        textViewNewsDate = view.findViewById(R.id.textViewNewsDate);

    }

    private void initSevice(View view) {
        textView_goodsget_home = (TextView) view.findViewById(R.id.textView_goodsget_home);
        mTv_neighborhood = (TextView) view.findViewById(R.id.tv_neighborhood);
        mTv_seekhelp = (TextView) view.findViewById(R.id.tv_seekhelp);
        mTv_second_hand_market = (TextView) view.findViewById(R.id.tv_second_hand_market);
        mTv_houseproperty_lease = (TextView) view.findViewById(R.id.tv_houseproperty_lease);
        textView_fixonline_homefragment = (TextView) view.findViewById(R.id.textView_fixonline_homefragment);
        textView_carPay = (TextView) view.findViewById(R.id.textView_carPay);
        textView_wuyePay = (TextView) view.findViewById(R.id.textView_wuyePay);
    }

    private void initLoopPage(View view) {
        roll_view_pager_home = (RollPagerView) view.findViewById(R.id.roll_view_pager_home);
        roll_user_selector = (RollPagerView) view.findViewById(R.id.roll_user_selector);
        roll_view_pager_home.setHintView(new ColorPointHintView(getActivity(), getResources().getColor(R.color.white), getResources().getColor(R.color.theme_color)));
        ColorPointHintView colorPointHintView = new ColorPointHintView(getActivity(), getResources().getColor(R.color.white), getResources().getColor(R.color.theme_color));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Util.dip2px(getContext(), 2));
        colorPointHintView.setLayoutParams(layoutParams);
        roll_user_selector.setHintView(colorPointHintView);
//        roll_view_pager_home.setHintView(new IconHintView(getActivity(),(R.mipmap.circle_white2x),(R.mipmap.tuoyuan_white2x)));
        initSelectorSetting();
    }

    private void initSelectorSetting() {
        UserSelectorAdapter userSelectorAdapter = new UserSelectorAdapter(roll_user_selector);
        roll_user_selector.setAdapter(userSelectorAdapter);
        String firstPage[] = {"物业缴费", "在线报修", "求助帮忙", "服务定制"};
        String secondPage[] = {"二手市场", "邻里议事厅", "房产租赁", "停车缴费"};
        int firstPageRes[] = {R.mipmap.home_wuyepay2x, R.mipmap.home_repair2x, R.mipmap.home_help2x, R.mipmap.home_service2x,};
        int secondPageRes[] = {R.mipmap.home_secondhandmarket2x, R.mipmap.home_neibor_discussing2x, R.mipmap.home_house_lend2x,
                R.mipmap.home_car_pay2x,};
        List<List<Parcelable>> selectorDatas = new ArrayList<>();
        List<Parcelable> datapage01 = new ArrayList<>();
        List<Parcelable> datapage02 = new ArrayList<>();
        putSelectortData(firstPage, firstPageRes, datapage01);
        putSelectortData(secondPage, secondPageRes, datapage02);
        selectorDatas.add(datapage01);
        selectorDatas.add(datapage02);
        userSelectorAdapter.refreshData(selectorDatas);
    }

    private void putSelectortData(String[] firstPage, int[] firstPageRes, List<Parcelable> data) {
        for (int i = 0; i < firstPage.length; i++) {
            HomeSelectorModle homeSelectorModle = new HomeSelectorModle(firstPage[i], firstPageRes[i]);
            data.add(homeSelectorModle);
        }
    }


    private void initclick() {
        imageView_game_home.setOnClickListener(this);
        imageView_commnityManager_home.setOnClickListener(this);
        textView_fixonline_homefragment.setOnClickListener(this);
        textView_goodsget_home.setOnClickListener(this);
        textView_carPay.setOnClickListener(this);
        textView_wuyePay.setOnClickListener(this);
        mTv_neighborhood.setOnClickListener(this);
        mImageView_onkeyOpen.setOnClickListener(this);
        mImageView_notice_home.setOnClickListener(this);
        mImageView_news_home.setOnClickListener(this);
        mTv_seekhelp.setOnClickListener(this);
        mTv_second_hand_market.setOnClickListener(this);
        mTv_houseproperty_lease.setOnClickListener(this);
        mImageView_event_home.setOnClickListener(this);
        mImageView_event_home.setOnClickListener(this);
        imageView_hotgoods.setOnClickListener(this);
        textViewMoreEvent.setOnClickListener(this);
        textViewMoreGoods.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main_home02;
    }


    @Override
    public void onSuccess(BaseBean data, int i) {
        mZProgressHUD.setMessage("开锁中...");
        if (data != null && data instanceof HomeHotGoodsBean) {
            HomeHotGoodsBean bean = (HomeHotGoodsBean) data;
            StoreBean.StoreInfo.GoodsBean storeInfo = new StoreBean.StoreInfo().getGoods();
            List<StoreBean.StoreInfo.GoodsBean.GoodsListBean> list = new ArrayList<>();
            for (int j = 0; j < bean.getO().getList().size(); j++) {
                StoreBean.StoreInfo.GoodsBean.GoodsListBean listBean = new StoreBean.StoreInfo.GoodsBean.GoodsListBean();
                listBean.setGoods_id(bean.getO().getList().get(j).getGoods_id());
                listBean.setGoods_name(bean.getO().getList().get(j).getGoods_name());
                listBean.setGoods_remark(bean.getO().getList().get(j).getGoods_remark());
                listBean.setShop_price(bean.getO().getList().get(j).getShop_price());
                listBean.setThumb_url(bean.getO().getList().get(j).getThumb_url());
                list.add(listBean);
            }
            if (pageNo == 1) {
                mStoreAdapter.refreshData(list);
//                linearlayout_onkeyCall.post(runnable);
            } else {
                mStoreAdapter.addDatas(list);
            }
            if (list.size() == 0) {
                if (pageNo != 1) {
                    ToastUtil.toast(R.string.nomore_loading);
                }
            }
        }

        if (data instanceof HomeBean) {
            final HomeBean homeBean = (HomeBean) data;
//                    ToastUtil.showToast(getContext(), homeBean.getM());
            images.clear();
            for (int j = 0; j < homeBean.getO().size(); j++) {
                images.add(ConstantUrl.hostImageurl + homeBean.getO().get(j).getAd_code());
            }
            //加载活动图
            eventAdapter.refreshData(homeBean.getE());
            roll_view_pager_home.setAdapter(new TestNormalAdapter(images, getContext()));//// TODO: 2017/6/16 放入网络数据
            //轮播图
            mHomeAdvAdapter = new ImageUrlAdapter(roll_view_pager_home);//这里互相设置
            roll_view_pager_home.setAdapter(mHomeAdvAdapter);//// TODO: 2017/6/16 放入网络数据
            //轮播点击跳转
            roll_view_pager_home.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String url = homeBean.getO().get(position).getAd_link();
                    if (TextUtils.isEmpty(url)) {
                        url = "www.baidu.com";
                    }
                    Intent intent = new Intent(getContext(), BrowserInnerActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "详情");
                    if (!url.contains("/")) {
                        return;
                    }
                    getContext().startActivity(intent);
                }
            });
            disposeAdv(images);
            //设置活动图进度条和监听事件
//                setEventProgress();
            swip_home.setRefreshing(false);
        }

        swip_home.setRefreshing(false);
        recyclerview_home_hot_goods.loadMoreComplete();

        if (data instanceof HomeNoticeBean) {
            HomeNoticeBean homeNoticeBean = (HomeNoticeBean) data;
            setNoticeData(homeNoticeBean);
        }
    }

    private void setNoticeData(HomeNoticeBean homeNoticeBean) {
        textViewNoticeMsg.setText(homeNoticeBean.getO().getNotice_one().getTitle());
        textViewNoticeDate.setText(homeNoticeBean.getO().getNotice_one().getAddtime_desc());
        textViewNews.setText(homeNoticeBean.getO().getNews_one().getTitle());
        textViewNewsDate.setText(homeNoticeBean.getO().getNews_one().getAddtime_desc());
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        mZProgressHUD.dismissWithFailure("蓝牙校验失败");
        swip_home.setRefreshing(false);
        recyclerview_home_hot_goods.loadMoreComplete();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        mZProgressHUD.dismissWithFailure("蓝牙校验失败");
        swip_home.setRefreshing(false);
        recyclerview_home_hot_goods.loadMoreComplete();
        //todo
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.textView_wuyePay:
                bundle.putString("titile", "物业缴费");
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                CommonUtils.startIntent(getContext(), NewWuyePayActivity.class, bundle);
                break;
            case R.id.textView_carPay:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                bundle.putString("titile", "停车缴费");
                CommonUtils.startIntent(getContext(), NewWuyePayActivity.class, bundle);
                break;
            case R.id.tv_neighborhood:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent neighIntent = new Intent(getActivity(), HomeWebActivity.class);
                neighIntent.setAction(IntentDataKeyConstant.HOME_NEIGH_ACTION);
                neighIntent.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadNeighbourUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(neighIntent);
                break;

            case R.id.imageView_event_home:
            case R.id.textViewMoreEvent:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                if (onClickSwitchListener != null) {
                    onClickSwitchListener.onClickSwitch(1);
                }
                break;
            case R.id.imageView_onkeyOpen:
                blueToothIntf.bluetoothCall();
                break;
            case R.id.tv_seekhelp://求助帮忙
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent seekhelpIntent = new Intent(getActivity(), HomeWebActivity.class);
                seekhelpIntent.setAction(IntentDataKeyConstant.HOME_HELP_ACTION);
                seekhelpIntent.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadHelpUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(seekhelpIntent);
                break;
            case R.id.tv_second_hand_market:
                Intent secondHand = new Intent(getActivity(), HomeWebActivity.class);
                secondHand.setAction(IntentDataKeyConstant.HOME_SECONDHAND_ACTION);
                secondHand.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadSecondhandMarketUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(secondHand);
                break;
            case R.id.tv_houseproperty_lease:
                Intent houseproperty = new Intent(getActivity(), HomeWebActivity.class);
                houseproperty.setAction(IntentDataKeyConstant.HOME_HOUSEPROPERTY_LEASE_ACTION);
                houseproperty.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadHousePropertyUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(houseproperty);
                break;
            case R.id.imageView_notice_home:
//                if(Preference.get(ConstantString.UTYPE,"").equals(4+"")){
//                    ToastUtil.toast("游客模式下无法访问");
//                    return;
//                }
                //调用阅读接口
                readNewsOrNotice(1);
                Intent notice = new Intent(getActivity(), HomeWebActivity.class);
                notice.setAction(IntentDataKeyConstant.HOME_NOTICE_ACTION);
                notice.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadNoticeUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(notice);
                break;
            case R.id.imageView_news_home:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                //调用阅读接口
                readNewsOrNotice(2);
                Intent news = new Intent(getActivity(), HomeWebActivity.class);
                news.setAction(IntentDataKeyConstant.HOME_NEWS_ACTION);
                news.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadNewsUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(news);
                break;
            //在线报修
            case R.id.textView_fixonline_homefragment:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent repair = new Intent(getActivity(), HomeWebActivity.class);
                repair.setAction(IntentDataKeyConstant.HOME_ONLINE_REPAIRS_ACTION);
                repair.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadRepairUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(repair);
                break;
            //快递收取
            case R.id.textView_goodsget_home:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                CommonUtils.startIntent(getContext(), GetGoodsActivity.class);
                break;
            case R.id.imageView_commnityManager_home://社区管家
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent commnityManager = new Intent(getActivity(), HomeWebActivity.class);
                commnityManager.setAction(IntentDataKeyConstant.HOME_COMMNITYMANAGER_ACTION);
                commnityManager.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadCommunityManager + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(commnityManager);
                break;
            case R.id.imageView_game_home:
                Intent gameManager = new Intent(getActivity(), HomeWebActivity.class);
                gameManager.setAction(IntentDataKeyConstant.HOME_GAME_ACTION);
                gameManager.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadGame + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(gameManager);
                break;
            case R.id.imageView_hotgoods:
            case R.id.textViewMoreGoods:
//                Bundle bundle1 = new Bundle();
                Intent intent =new Intent();
                intent.putExtra(IntentDataKeyConstant.ID,IntentDataKeyConstant.GOODES_DEFULT_ID);
                CommonUtils.startIntent(getContext(), StoreClassifyListActivity.class);
//                ((MainActivity) getContext()).swtichToFragment(((MainActivity) getContext()).getFootView(3), bundle1);
                break;
        }
    }

    private void startOneKeyOpen() {
        if (!hasPermission(getActivity(), permissions)) {
            hasRequse(getActivity(), 0, permissions);
            return;
        }
        if (TextUtils.isEmpty(Preference.get(ConstantString.UTYPE, ""))) {
            BlueToothManageActivity.start(getContext());
            return;
        }
//                Intent serverIntent = new Intent(getActivity(), AnyScanActivity.class);
//                startActivityForResult(serverIntent, IntentRequestCodes.BT_SELECT_DEVICE);
        times = 0;
        if (Build.VERSION.SDK_INT >= 23 && !isLocationOpen(getContext())) {
            try {
                Intent enableLocate = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(enableLocate, 100011);
            } catch (Exception e) {
                ToastUtil.toast("请手动打开定位功能");
            }
        }
        if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
            ToastUtil.toast(R.string.no_permission);
            return;
        }
        boolean bluetoothEnable = mBluetoothEngine.isBluetoothEnable();
        if (!bluetoothEnable) {
            turnOnBluetooth();
            return;
        }
        EventBus.getDefault().post(new ReleseSingle());//释放先前的蓝牙连接
        showProgressAndOpen();
        timer.start();

    }

    private void showProgressAndOpen() {
        mZProgressHUD.show();
        mZProgressHUD.setMessage("扫描中...");
        mBluetoothEngine.startScanBluetooth(getActivity());
    }

    public static boolean isLocationOpen(final Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); //gps定位
        boolean isGpsProvider = manager.isProviderEnabled(LocationManager.GPS_PROVIDER); //网络定位
        boolean isNetWorkProvider = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsProvider || isNetWorkProvider;
    }

    private void readNewsOrNotice(final int type) {
        OkHttpUtils.post().url(ConstantUrl.readUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("type", type + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    BaseBean baseBean = GetGson.parseBaseBean(s);
                    if (baseBean.getC() == 1) {
                        if (type == 1) {
                            imageView_home_have_notice.setVisibility(View.GONE);
                        } else {
                            imageView_home_have_news.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        destroyBluetoothInfos();
    }

    private void destroyBluetoothInfos() {
        mBluetoothEngine.clearResource(getActivity());
        BluetoothService.unregisterBroadcastReceiver(getActivity());
        BluetoothService.disconnect();
    }


    private BluetoothDevice mDevice;
    List<BluetoothDevice> mBluetoothDevices = new ArrayList<>();

    private BluetoothServiceBle.Callback callback = new BluetoothServiceBle.Callback() {


        @Override
        public void onStartScan() {
            if (mBluetoothDevices.size() > 0) {
                mBluetoothDevices.clear();
            }
        }

        @Override
        public void onScanning(ScanResult result) {
            mDevice = result.getDevice();
            if (mDevice != null && !TextUtils.isEmpty(mDevice.getName()) && mDevice.getName().contains(StringConstants.BLUETOOTH_HARDWARENAME_PREFIX)) {
                mBluetoothDevices.add(mDevice);//添加到列表
                checkBluetoothDevice(mDevice);
            }
        }

        @Override
        public void onScanComplete() {//todo
            if (!mBluetoothEngine.isBluetoothEnable()) {
                return;
            }
            if (mBluetoothDevices.size() <= 0) {
                mZProgressHUD.setMessage("蓝牙信号弱，请稍等");
                EventBus.getDefault().post(new ReleseSingle());
                times++;
                if (times <= 3) {
                    restartScan();//重启扫描
                } else {
                    mZProgressHUD.dismissWithFailure("没有找到设备");
                }
                Log.e("times", times + "");
            }

        }

        @Override
        public void onConnecting() {


        }

        @Override
        public void onConnectFail() {

            mZProgressHUD.dismiss();
        }

        @Override
        public void onDisConnected() {

        }

        @Override
        public void onServicesDiscovered() {
        }
    };

    private void restartScan() {
        mZProgressHUD.show();
        mBluetoothEngine.startScanBluetooth(getContext());
    }


    public void checkBluetoothDevice(final BluetoothDevice mDevice) {
        Map<String, String> stringMap = new HashMap<>();
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        if (!TextUtils.isEmpty(uid)) {
            stringMap.put(IntentDataKeyConstant.UID_KEY, uid);
        }
        String mDeviceName = mDevice.getName();
        if (mDeviceName.length() > 10) {
            mDeviceName = mDevice.getName().substring(0, 10);
        }
        if (BluetoothUtil.findData(mDeviceName) != null) {
            BluetoothUtil.startPair(BluetoothUtil.findData(mDeviceName), mDevice, mBluetoothEngine, HomeFragment.this);
            return;
        }
        stringMap.put(StringConstants.MAC_STRING, mDeviceName);
        BluetoothMatchBean bluetoothMatchBean = new BluetoothMatchBean();
        bluetoothMatchBean.setUrl(ConstantUrl.BLUETOOTH_MATCH_URL);
        OKHttpRequestModel okHttpRequestModel = new OKHttpRequestModel(new OKHttpUIUpdataListener() {
            @Override
            public void onSuccess(final Object data, int i) {
                BluetoothUtil.putIntoSp(data);
                BluetoothUtil.startPair(data, mDevice, mBluetoothEngine, HomeFragment.this);
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mZProgressHUD.dismissWithFailure("网络错误");
                    }
                });
            }

            @Override
            public void onFailure(C_CodeBean c_codeBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mZProgressHUD.setMessage("矫正干扰...");
                        if (!mBluetoothEngine.isBluetoothEnable()) {
                            mZProgressHUD.dismiss();
                        }
                    }
                });
            }
        });
        okHttpRequestModel.okhHttpPost(ConstantUrl.BLUETOOTH_MATCH_URL, stringMap, bluetoothMatchBean, false);
//            mBluetoothEngine.cancelScan();
//            Log.e("step","取消浏览");
    }

    private void startOpen(Object data, BluetoothDevice mDevice) {
        BluetoothUtil.startTransprot(mBluetoothEngine, mDevice, data, HomeFragment.this);
    }

    @Override
    public void bluetoothEnabling() {

    }

    @Override
    public void bluetoothEnabled() {

    }

    @Override
    public void bluetoothDisabling() {

    }

    @Override
    public void bluetoothDisabled() {

    }

    @Override
    public void connectedTo(@NotNull String name, @NotNull String address) {

    }

    @Override
    public void onSuccess(BluetoothDevice bluetoothDevice) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mZProgressHUD.dismissWithSuccess("开锁成功");
                mBluetoothEngine.cancelScan();
            }
        });
        EventBus.getDefault().post(new ReleseSingle());
//        if (mBluetoothEngine.turnOffBluetooth()) {
//            Log.e("close_bluetooth", "陈宫");
//        }
        times = 0;
        timer.onFinish();
    }

    @Override
    public void onFailed(BluetoothDevice bluetoothDevice, Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mZProgressHUD.dismissWithFailure("开锁失败");
                mBluetoothEngine.cancelScan();
            }
        });
        Log.e("bluestate", "开锁失败");
        EventBus.getDefault().post(new ReleseSingle());
    }

    @Override
    public void onItemClick(View v, HomeBean.EBean data) {
        if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
            ToastUtil.toast(R.string.no_permission);
            return;
        }
        Intent intent = new Intent(getActivity(), OwnerEventDetailActivity.class);
        intent.putExtra(IntentDataKeyConstant.AID, data.getId() + StringConstants.EMPTY_STRING);
        startActivity(intent);
    }

    private void turnOnBluetooth() {
        //请求打开Bluetooth
        Intent requestBluetoothOn = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);

        //设置Bluetooth设备可以被其它Bluetooth设备扫描到
        requestBluetoothOn
                .setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        //设置Bluetooth设备可见时间
//        requestBluetoothOn.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
//                1000);
        //请求开启Bluetooth
        this.startActivityForResult(requestBluetoothOn, 1000);
    }

    Timer timer = new Timer();
    int times = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (mBluetoothEngine.isBluetoothEnable()) {
                timer.start();
                showProgressAndOpen();
                mBluetoothDevices.clear();
            }
        }
    }

    private class Timer extends CountDownTimer {
        public Timer() {
            super(12 * 1000, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("test", millisUntilFinished + "");
        }

        @Override
        public void onFinish() {
            Log.i("test", "finishi" + mBluetoothDevices.size());
            if (mBluetoothDevices.size() == 0 || !BluetoothUtil.isHaveMyBlueTooth(mBluetoothDevices)) {
                mZProgressHUD.dismissWithFailure("没有找到设备");//如果在规定时间内搜索到的蓝牙设备为0或者没有自己有权限通讯的设备
            }
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getGoodsData();
    }


    public void getGoodsData() {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        BaseBean bean = new HomeHotGoodsBean();
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.homeHotShopUrl, map, bean);
    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            linearlayout_onkeyCall.setVisibility(View.GONE);
//        } else {
//            linearlayout_onkeyCall.post(runnable);
//        }
//    }

    //这个线程主要是为了防止头部超出界限出现回弹现象而设置的，其原理是刷新的时候吧头部一部分布局
    class VisbleRunnable implements Runnable {
        ViewGroup view;

        public VisbleRunnable(ViewGroup view) {
            this.view = view;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(50);
                view.setVisibility(View.VISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
