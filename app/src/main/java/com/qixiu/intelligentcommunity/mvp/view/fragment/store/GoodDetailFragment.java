package com.qixiu.intelligentcommunity.mvp.view.fragment.store;

import android.Manifest;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntConstant;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.GoodsDetailSpecEngine;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.PhoneBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.spec.SingleSpecBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.order.ConfirmOrderActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.good.GoodsDetailAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.good.GoodsDetailSpecListAtapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.itemdecoration.DividerItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.rollpage.ImageUrlAdapter;
import com.qixiu.intelligentcommunity.my_interface.Communication;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class GoodDetailFragment extends BaseFragment implements View.OnClickListener, OKHttpUIUpdataListener, Communication, ViewTreeObserver.OnGlobalLayoutListener {

    private RecyclerView mRv_good_details;
    private Button mBt_buy;
    private Button mBt_addto_shopcar;
    private View mRl_product_detail;
    private PopupWindow pw;
    private GoodsDetailAdapter mGoodsDetailAdapter;
    private TextView mTv_repertory;
    private int mNumber = 1;

    private View mHead;
    private OKHttpRequestModel mOkHttpRequestModel;
    private String goods_id;
    private GoodsDetailBean.GoodsDetailInfos mGoodsDetailInfos;
    private TextView mTv_goodsdetail_ppw_price;
    private String spec_key;
    private TextView mTv_good_detail_title;
    private TextView mTv_good_detail_price;
    private TextView mTv_monthlysales;
    private View mTv_gooddetail_payphone;
    private GoodsDetailSpecListAtapter mGoodsDetailSpecListAtapter;
    private GoodsDetailReceiver mGoodsDetailReceiver;
    private TextView mTv_repertory_remainder;
    private String mStore_count;
    private String mStorePrice;
    private boolean isCanGoodsPay;
    private Button mBt_confirm;
    private AppBarLayout abl_goodsdetail;
    private NestedScrollView mNsv_gooddetail;
    private View mLl_goodsdetail_main;
    private TextView mMTv_comment_total_count;
    private RollPagerView mRpv_good_detail;
    private ImageUrlAdapter mImageUrlAdapter;
    private PhoneBean phoneBean;


    @Override
    protected void onInitData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            goods_id = arguments.getString(IntentDataKeyConstant.GOODS_ID);
        }

        mOkHttpRequestModel = new OKHttpRequestModel(this);
        requestData();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentDataKeyConstant.BROADCAST_GOODSDETAIL_ACTION);
        mGoodsDetailReceiver = new GoodsDetailReceiver(this);
        getActivity().registerReceiver(mGoodsDetailReceiver, intentFilter);
    }

    private void requestData() {
        BaseBean baseBean = new GoodsDetailBean();
        Map<String, String> map = new HashMap();
        String userId = Preference.get(IntentDataKeyConstant.ID, "");
        if (userId != null) {
            map.put("userId", userId);
        }
        if (goods_id != null) {
            map.put(IntentDataKeyConstant.GOODS_ID, goods_id);
        }
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_GOODDETAIL, map, baseBean, true);
    }

    /**
     * 用于接收每次选择规格后请求服务器的一个回调后数据处理
     *
     * @param content
     */
    @Override
    public void startCommunicate(Object... content) {
        Intent intent = null;
        for (int i = 0; i < content.length; i++) {
            if (content[i] instanceof Intent) {
                intent = (Intent) content[i];
            }
        }
        if (intent == null) return;

        Map<Integer, Object> specInfo = GoodsDetailSpecEngine.getSpecInfo(mGoodsDetailSpecListAtapter.getDatas());
        List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> specGoodsPriceBeanList = (List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean>) specInfo.get(1);
        if (mTv_repertory_remainder == null)
            return;
        if (intent.hasExtra(IntentDataKeyConstant.BROADCAST_GOODSDETAIL_SPEC_KEY)) {
            //返回点击规格后请求数据，从holder中以广播的形式将数据传递到startCommunicate（）中并接收
            SingleSpecBean.SingleSpecInfo singleSpecInfo = intent.getParcelableExtra(IntentDataKeyConstant.BROADCAST_GOODSDETAIL_SPEC_KEY);
            String temp_store_count = singleSpecInfo.getStore_count();
            //获取到服务器给返回的商品树立那个
            double store_count = Double.parseDouble(temp_store_count);
            if (store_count < 0) {
                store_count = 0;
            }
            //设置商品详情商品剩余库存数量
            mTv_repertory_remainder.setText(StringConstants.STRING_REPERTORY_START + (int) store_count + StringConstants.STRING_REPERTORY_END);
            //并将全局的商品数量随之更改
            mStore_count = (int) store_count + StringConstants.STRING_EMPTY;
            //针对编辑的库存数量值一个设置
            if (mNumber == 0) {
                mNumber = 1;
                mTv_repertory.setText(mNumber + StringConstants.STRING_EMPTY);
            }
            mStorePrice = singleSpecInfo.getPrice();
            String storeCountString = mTv_repertory.getText().toString();
            //由于限制编辑的库存值是不能等于0的并且如果已编辑数量后选择规格但是服务器返回的数量小于已编辑的数量时也要做显示调整。
            if (store_count > 0 && Double.parseDouble(storeCountString) > store_count) {
                mTv_repertory.setText(mStore_count);
                mNumber = (int) store_count;
            }
            mTv_goodsdetail_ppw_price.setText("¥" + Double.parseDouble(mStorePrice) * Double.parseDouble(mTv_repertory.getText().toString()) + StringConstants.STRING_EMPTY);
            isCanGoodsPay = true;
            //这里针对如果没有选择规格确定按钮是不可以用的
            if (specGoodsPriceBeanList.size() == 0 && store_count > 0) {
                confirmButtonEnable(true);
            } else {
                confirmButtonEnable(false);
            }

        } else {
            //这里是代表请求数据失败或者没有请求到数据时的一个操作
            mTv_repertory_remainder.setText(StringConstants.STRING_REPERTORY_START + StringConstants.STRING_0 + StringConstants.STRING_REPERTORY_END);
            mTv_goodsdetail_ppw_price.setText("¥" + mGoodsDetailInfos.getGoods_Info().getShop_price());

            mStore_count = StringConstants.STRING_0;
            mNumber = IntConstant.NUM_1;
            mTv_repertory.setText(mNumber + StringConstants.STRING_EMPTY);

            isCanGoodsPay = false;

            if (specGoodsPriceBeanList.size() == 0) {
                confirmButtonEnable(false);
                ToastUtil.showToast(BaseApplication.getContext(), R.string.goodsdetail_notcargo_selected);
            }

        }

    }

    @Override
    public void onGlobalLayout() {
        mNsv_gooddetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mNsv_gooddetail.getHeight());
        mLl_goodsdetail_main.setLayoutParams(layoutParams);
    }


    public static class GoodsDetailReceiver extends BroadcastReceiver {

        private final WeakReference<Communication> mWeakReference;

        public GoodsDetailReceiver(Communication communication) {

            mWeakReference = new WeakReference(communication);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Communication communication = mWeakReference.get();
            if (communication != null) {
                communication.startCommunicate(intent);
            }
        }
    }

    @Override
    protected void onInitView(View view) {

        mTv_good_detail_title = findViewById(R.id.tv_good_detail_title);
        mLl_goodsdetail_main = findViewById(R.id.ll_goodsdetail_main);
        mNsv_gooddetail = findViewById(R.id.nsv_gooddetail);
        mMTv_comment_total_count = findViewById(R.id.tv_comment_total_count);
        ViewTreeObserver viewTreeObserver = mNsv_gooddetail.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
        mTv_good_detail_price = findViewById(R.id.tv_good_detail_price);
        mTv_monthlysales = findViewById(R.id.tv_monthlysales);
        mTv_gooddetail_payphone = findViewById(R.id.tv_gooddetail_payphone);
        mTv_gooddetail_payphone.setOnClickListener(this);

        mRpv_good_detail = findViewById(R.id.rpv_good_detail);
        mImageUrlAdapter = new ImageUrlAdapter(mRpv_good_detail);
        mRpv_good_detail.setHintView(new ColorPointHintView(getActivity(), getResources().getColor(R.color.white), getResources().getColor(R.color.home_back)));
        mRpv_good_detail.setAdapter(mImageUrlAdapter);
        mRl_product_detail = findViewById(R.id.rl_product_detail);
        mRv_good_details = findViewById(R.id.rv_good_details);
        mBt_buy = findViewById(R.id.bt_buy);
        mBt_addto_shopcar = findViewById(R.id.bt_addto_shopcar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv_good_details.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));
        mRv_good_details.setLayoutManager(linearLayoutManager);
        mGoodsDetailAdapter = new GoodsDetailAdapter();
        mRv_good_details.setAdapter(mGoodsDetailAdapter);
        mRv_good_details.setNestedScrollingEnabled(false);
        //mRv_good_details.add
        initListener();
    }

    private void initListener() {
        mBt_buy.setOnClickListener(this);
        mBt_addto_shopcar.setOnClickListener(this);
        mRl_product_detail.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_good_details;
    }

    private boolean isImmediatelyPay;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_buy:

                if (mGoodsDetailInfos != null) {
                    isImmediatelyPay = true;
                    showGoodsDetailPopuWindow(false);
                } else {
                    ToastUtil.showToast(BaseApplication.getContext(), "请检查你的网络环境");

                }
                break;
            case R.id.bt_addto_shopcar:

                if (mGoodsDetailInfos != null) {
                    isImmediatelyPay = false;
                    showGoodsDetailPopuWindow(false);
                } else {
                    ToastUtil.showToast(BaseApplication.getContext(), "请检查你的网络环境");

                }

                break;
            case R.id.rl_product_detail:
                if (mGoodsDetailInfos != null) {
                    showGoodsDetailPopuWindow(true);
                } else {
                    ToastUtil.showToast(BaseApplication.getContext(), "请检查你的网络环境");
                }

                break;

            case R.id.bt_confirm:
                Map<Integer, Object> specInfo = GoodsDetailSpecEngine.getSpecInfo(mGoodsDetailSpecListAtapter.getDatas());
                List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> specGoodsPriceBeanList = (List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean>) specInfo.get(1);
                if (specGoodsPriceBeanList.size() > 0) {
                    ToastUtil.toast("请选择" + specGoodsPriceBeanList.get(0).getName());
                    return;
                }
                spec_key = specInfo.get(0) == null ? null : (String) specInfo.get(0);
                if (isCanGoodsPay) {
                    if (isImmediatelyPay) {
                        //跳转到确认订单界面
                        Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                        intent.putExtra(IntentDataKeyConstant.GOODS_ID, mGoodsDetailInfos.getGoods_Info().getGoods_id());
                        intent.putExtra(IntentDataKeyConstant.SPEC_KEY, spec_key);
                        intent.putExtra(IntentDataKeyConstant.GOODS_NUM, mTv_repertory.getText().toString());
                        String string = mTv_goodsdetail_ppw_price.getText().toString();
                        if (string.contains("¥")) {
                            string = string.replaceAll("¥", "");
                        }

                        intent.putExtra(IntentDataKeyConstant.TOTOLPRICE_KEY, Double.parseDouble(string));
                        intent.putExtra(IntentDataKeyConstant.POSTAGE_KEY, Double.parseDouble(mGoodsDetailInfos.getGoods_Info().getPostage()));
                        startActivity(intent);
                    } else {
                        //加入购物车
                        addGoodsToCars();
                    }
                    if (pw != null && pw.isShowing()) {
                        pw.dismiss();
                    }
                } else {
                    ToastUtil.toast("抱歉，该规格商品已没有啦");
                }
                break;
            case R.id.tv_gooddetail_payphone:
                String phone = Preference.get(IntentDataKeyConstant.PHONE_KEY, StringConstants.STRING_EMPTY);
//                if (!TextUtils.isEmpty(phone)) {
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//                    getActivity().startActivity(intent);
//                    return;
//                }
                mOkHttpRequestModel.okhHttpPost(ConstantUrl.callUrl, null, new PhoneBean(), true);
                break;

            case R.id.rl_goodsdetail_ppw_close:
                if (pw.isShowing()) {
                    pw.dismiss();
                    if (mGoodsDetailSpecListAtapter != null) {
                        mStore_count = mGoodsDetailInfos.getGoods_Info().getStore_count();
                        mStorePrice = mGoodsDetailInfos.getGoods_Info().getShop_price();
                        isCanGoodsPay = false;
                        for (int i = 0; i < mGoodsDetailSpecListAtapter.getDatas().size(); i++) {
                            GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean specGoodsPriceBean = mGoodsDetailSpecListAtapter.getDatas().get(i);
                            specGoodsPriceBean.setSelectSpecLine(false);
                            for (int j = 0; j < specGoodsPriceBean.getSpec().size(); j++) {
                                mGoodsDetailSpecListAtapter.getDatas().get(i).getSpec().get(j).setSelect(false);
                            }
                        }
                    }

                }
                break;
            case R.id.tv_shopcar_add:
                double mStore_count_add = Double.parseDouble(mStore_count);
                if (mTv_repertory != null && !TextUtils.isEmpty(mTv_repertory.getText())) {

                    if (mStore_count == null || mStore_count_add == 0) return;


                    int addTempNumber = Integer.parseInt(mTv_repertory.getText().toString());


                    if (addTempNumber < mStore_count_add) {
                        mNumber = addTempNumber + 1;
                        mTv_goodsdetail_ppw_price.setText(mNumber * Double.parseDouble(mStorePrice) + "");

                    } else {
                        ToastUtil.showToast(getContext(), "库存不足");
                        mNumber = Integer.parseInt(mStore_count);
                    }
                    mTv_repertory.setText(mNumber + "");

                }
                break;
            case R.id.tv_subtract:

                double mStore_count_subtract = Double.parseDouble(mStore_count);
                if (mTv_repertory != null && !TextUtils.isEmpty(mTv_repertory.getText())) {

                    if (mStore_count == null || mStore_count_subtract == 0) return;

                    int sybtractTempNumber = Integer.parseInt(mTv_repertory.getText().toString());
                    if (sybtractTempNumber > 1) {
                        mNumber = sybtractTempNumber - 1;
                        mTv_goodsdetail_ppw_price.setText(mNumber * Double.parseDouble(mStorePrice) + "");

                    } else {
                        mTv_goodsdetail_ppw_price.setText(mStorePrice);
                        mNumber = 1;
                        ToastUtil.showToast(BaseApplication.getContext(), "数量不能少于1");
                    }
                    mTv_repertory.setText(mNumber + "");
                }

                break;


        }
    }

    private void addGoodsToCars() {
        if (Double.parseDouble(mStore_count) <= 0) {
            ToastUtil.toast("已经没有库存");
            return;
        }

        Map<String, String> stringStringMap = new HashMap<>();
        if (goods_id != null) {

            stringStringMap.put(IntentDataKeyConstant.GOODS_ID, goods_id);
        }
        stringStringMap.put("user_id", Preference.get(IntentDataKeyConstant.ID, ""));
        stringStringMap.put("goods_num", mNumber + "");
        if (spec_key != null) {
            stringStringMap.put("spec_key", spec_key);
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setUrl(ConstantUrl.STORE_ADDGOODS_TOSHOPCAR);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_ADDGOODS_TOSHOPCAR, stringStringMap, baseBean, true);
    }

    /**
     * 加入购物车按钮是否可用设置
     *
     * @param isEnable
     */
    public void confirmButtonEnable(boolean isEnable) {
        if (mBt_confirm != null) {
            mBt_confirm.setEnabled(isEnable);
            mBt_confirm.setBackgroundColor(isEnable ? getResources().getColor(R.color.txt_B0B0B0) : getResources().getColor(R.color.F5F5F5));
            Resources resources = getResources();
            mBt_confirm.setTextColor(isEnable ? resources.getColor(R.color.white) : resources.getColor(R.color.txt_cfcfcf));
        }

    }

    /**
     * 公用的popupwindow显示产品详情，及添加购物车操作
     *
     * @param isProductDetail
     */
    private void showGoodsDetailPopuWindow(boolean isProductDetail) {

        View contentView = null;
        //判断是点击产品详情的popuWindow还是加入购物车的
        if (!isProductDetail) {
            contentView = View.inflate(getActivity(), R.layout.layout_goodsdetail_ppw, null);
            ImageView iv_goodsdetail_ppw_picture = (ImageView) contentView.findViewById(R.id.iv_goodsdetail_ppw_picture);
            Glide.with(getActivity()).load(ConstantUrl.hosturl + mGoodsDetailInfos.getGoods_images_list().get(IntConstant.NUM_0)).into(iv_goodsdetail_ppw_picture);
            TextView iv_goodsdetail_ppw_title = (TextView) contentView.findViewById(R.id.tv_goodsdetail_ppw_title);
            mTv_repertory_remainder = (TextView) contentView.findViewById(R.id.tv_repertory_remainder);
            mTv_repertory_remainder.setText(StringConstants.STRING_REPERTORY_START + mGoodsDetailInfos.getGoods_Info().getStore_count() + StringConstants.STRING_REPERTORY_END);
            iv_goodsdetail_ppw_title.setText(mGoodsDetailInfos.getGoods_Info().getGoods_name());

            mTv_goodsdetail_ppw_price = (TextView) contentView.findViewById(R.id.tv_goodsdetail_ppw_price);
            mTv_goodsdetail_ppw_price.setText("¥" + mGoodsDetailInfos.getGoods_Info().getShop_price());
            //规格

            RecyclerView rv_good_details_spec = (RecyclerView) contentView.findViewById(R.id.rv_good_details_spec);
            showSpecList(rv_good_details_spec);
            //改变库存
            mTv_repertory = (TextView) contentView.findViewById(R.id.tv_repertory);
            TextView tv_shopcar_add = (TextView) contentView.findViewById(R.id.tv_shopcar_add);
            tv_shopcar_add.setOnClickListener(this);
            TextView tv_subtract = (TextView) contentView.findViewById(R.id.tv_subtract);
            tv_subtract.setOnClickListener(this);

            //确定按钮
            mBt_confirm = (Button) contentView.findViewById(R.id.bt_confirm);
            mBt_confirm.setOnClickListener(this);
            if (mStore_count != null && Double.parseDouble(mStore_count) <= 0) {
                confirmButtonEnable(false);
            }

        } else {

            contentView = View.inflate(getActivity(), R.layout.layout_gooddetail_productdetail, null);
            TextView tv_goodsdetail_productdetail_content = (TextView) contentView.findViewById(R.id.tv_goodsdetail_productdetail_content);
            tv_goodsdetail_productdetail_content.setText(mGoodsDetailInfos.getGoods_Info().getGoods_remark());
        }
        View rl_goodsdetail_ppw_close = contentView.findViewById(R.id.rl_goodsdetail_ppw_close);
        rl_goodsdetail_ppw_close.setOnClickListener(this);
        contentView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_in_2));


        pw = new PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT,
                true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置pw铺满屏幕
        pw.setClippingEnabled(false);

        if (!pw.isShowing())
            pw.showAtLocation(
                    getActivity().getWindow().getDecorView().findViewById(android.R.id.content),
                    Gravity.BOTTOM, 0, 0);

    }

    /**
     * 显示加入购物车popu中的规格列表
     *
     * @param rv_good_details_spec
     */
    private void showSpecList(RecyclerView rv_good_details_spec) {


        if (mGoodsDetailSpecListAtapter == null) {
            mGoodsDetailSpecListAtapter = new GoodsDetailSpecListAtapter();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_good_details_spec.setLayoutManager(linearLayoutManager);
        rv_good_details_spec.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, ArshowContextUtil.dp2px(10)));
        rv_good_details_spec.setAdapter(mGoodsDetailSpecListAtapter);
        List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> spec_goods_price = mGoodsDetailInfos.getSpec_goods_price();

        if (spec_goods_price.size() == 0) {
            isCanGoodsPay = true;
        }
        mGoodsDetailSpecListAtapter.refreshData(spec_goods_price);
        Bundle bundle = new Bundle();
        bundle.putString(IntentDataKeyConstant.GOODS_ID, mGoodsDetailInfos.getGoods_Info().getGoods_id());
        mGoodsDetailSpecListAtapter.setArguments(bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoodsDetailReceiver != null) {
            getActivity().unregisterReceiver(mGoodsDetailReceiver);
        }

        Log.e("LOGCAT", "onDestroy");
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (data != null) {
            if (data instanceof GoodsDetailBean) {
                GoodsDetailBean goodsDetailBean = (GoodsDetailBean) data;
                mGoodsDetailInfos = goodsDetailBean.getO();
                if (mGoodsDetailInfos != null) {

                    showCommentContentData();
                    showGoodsDetailInfos();
                    showPicturesData();
                }

            } else if (data instanceof PhoneBean) {
                phoneBean = (PhoneBean) data;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBean.getO()));
             if(hasPermission(getContext(),new String[]{Manifest.permission.CALL_PHONE})){
                 getActivity().startActivity(intent);
             }else {
                 hasRequse(getActivity(),0,new String[]{Manifest.permission.CALL_PHONE});
             }

            } else {
                ToastUtil.toast("添加购物车成功");
//                Intent shopCarIntent = new Intent(getActivity(), StoreShopCarActivity.class);
//                startActivity(shopCarIntent);
            }

        }


    }

    /**
     * 显示商品详情中有关商品信息
     */
    private void showGoodsDetailInfos() {

        GoodsDetailBean.GoodsDetailInfos.GoodsInfoBean goods_info = mGoodsDetailInfos.getGoods_Info();
        mStore_count = mGoodsDetailInfos.getGoods_Info().getStore_count();
        mStorePrice = mGoodsDetailInfos.getGoods_Info().getShop_price();
        mTv_good_detail_title.setText(goods_info.getGoods_name());
        mTv_good_detail_price.setText(goods_info.getShop_price());
        mTv_monthlysales.setText(goods_info.getSales_sum());

    }

    /**
     * 显示轮播图的数据
     */
    private void showPicturesData() {
        List<String> goods_images_list = mGoodsDetailInfos.getGoods_images_list();
        List<String> imageUrl = new ArrayList<>();
        for (int i = 0; i < goods_images_list.size(); i++) {
            imageUrl.add(ConstantUrl.hosturl + goods_images_list.get(i));
        }
//        int imagePathCount = goods_images_list.size();
//        String[] imagePaths = new String[imagePathCount];
//        for (int i = 0; i < imagePathCount; i++) {
//            imagePaths[i] = ConstantUrl.hosturls + goods_images_list.get(i);
//        }
//        mSfv_goodsdetail_picture.initData(imagePaths);
        mImageUrlAdapter.refreshData(imageUrl);
    }

    private boolean isMore;

    /**
     * 显示评论列表内容
     */

    private void showCommentContentData() {
        List<GoodsDetailBean.GoodsDetailInfos.GoodsCommentBean> goods_comment = mGoodsDetailInfos.getGoods_comment();
        mMTv_comment_total_count.setText(StringConstants.STRING_COMMENTCOUNT + goods_comment.size() + StringConstants.STRING_BACKBRACE);

        if (!isMore) {
            mGoodsDetailAdapter.refreshData(goods_comment);
        } else {
            if (goods_comment != null && goods_comment.size() > 0) {
                mGoodsDetailAdapter.addDatas(goods_comment);
            } else {
                ToastUtil.toast("没有更多数据啦");
            }
        }

    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (c_codeBean != null && ConstantUrl.STORE_ADDGOODS_TOSHOPCAR.equals(c_codeBean.getUrl())) {
            StringBuffer stringBuffer = new StringBuffer();
            switch (c_codeBean.getC()) {
                case -3:
                    stringBuffer.append("至多可以放20种商品");

                    break;
                case -5:
                    stringBuffer.append("商品不存在或已下架");
                    break;
                case -7:
                case -8:
                    stringBuffer.append("商品规格不合法或不存在");
                    break;
                case -10:
                case -11:
                    stringBuffer.append("商品库存不足");
                    break;

            }
            ToastUtil.toast(stringBuffer.toString());
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0&&hasPermission(getContext(),new String[]{Manifest.permission.CALL_PHONE})){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBean.getO()));
            getActivity().startActivity(intent);
        }
    }
}
