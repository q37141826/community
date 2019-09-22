package com.qixiu.intelligentcommunity.mvp.view.holder.store.shopcar;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.ShopCarCallBackInterface;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.AddGoodsToCarsBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.shopcar.ShopCarEditCountBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.ArshowDialogUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ShopCarHolder extends RecyclerBaseHolder implements View.OnClickListener, ShopCarCallBackInterface, OKHttpUIUpdataListener<BaseBean>, ArshowDialogUtils.ArshowDialogListener {

    private final TextView mTv_shopcar_add;
    private final TextView mTv_repertory;
    private final TextView mTv_subtract;
    private final ImageView mIv_shopcar_select_icon;
    private final TextView mTv_good_title;
    private final TextView mTv_shopcar_color;
    private final TextView mTv_size;
    private final TextView mTv_pice;
    private final ImageView mIv_good_picture;
    private final View rl_select;
    AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean shopCarInfo = null;
    private int mNumber = 1;
    private int tempNumber = 1;
    private ShopCarOnItemSelectedListener shopCarOnItemSelectedListener;
    private int position;
    private final OKHttpRequestModel mOkHttpRequestModel;
    private final View mRl_item_delect;

    public ShopCarHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        mRl_item_delect = itemView.findViewById(R.id.rl_item_delect);
        mRl_item_delect.setOnClickListener(this);
        mTv_good_title = (TextView) itemView.findViewById(R.id.tv_good_title);

        mTv_shopcar_color = (TextView) itemView.findViewById(R.id.tv_shopcar_color);
        mTv_size = (TextView) itemView.findViewById(R.id.tv_size);
        mTv_pice = (TextView) itemView.findViewById(R.id.tv_pice);
        mIv_good_picture = (ImageView) itemView.findViewById(R.id.iv_good_picture);
        mTv_shopcar_add = (TextView) itemView.findViewById(R.id.tv_shopcar_add);
        mTv_shopcar_add.setOnClickListener(this);
        mTv_repertory = (TextView) itemView.findViewById(R.id.tv_repertory);
        mTv_subtract = (TextView) itemView.findViewById(R.id.tv_subtract);
        mIv_shopcar_select_icon = (ImageView) itemView.findViewById(R.id.iv_shopcar_select_icon);
        rl_select = itemView.findViewById(R.id.rl_select);
        rl_select.setOnClickListener(this);
        mTv_subtract.setOnClickListener(this);
        mOkHttpRequestModel = new OKHttpRequestModel(this);

    }

    @Override
    public void setShopCarOnItemSelectedListener(ShopCarOnItemSelectedListener shopCarOnItemSelectedListener) {
        this.shopCarOnItemSelectedListener = shopCarOnItemSelectedListener;
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (data instanceof ShopCarEditCountBean) {
            ShopCarEditCountBean shopCarEditCountBean = (ShopCarEditCountBean) data;
            mNumber = shopCarEditCountBean.getO().getGoods_num();
            shopCarInfo.setGoods_num(mNumber + StringConstants.STRING_EMPTY);
            if (shopCarOnItemSelectedListener != null) {
                shopCarOnItemSelectedListener.shopCarOnItemSelected(position);
            }
        } else {
            if (ConstantUrl.STORE_SHOPCAR_DELECTED != data.getUrl()) {


                ToastUtil.showToast(BaseApplication.getContext(), "库存编辑失败，库存已不足");
            } else {
                RecyclerBaseAdapter recyclerBaseAdapter = (RecyclerBaseAdapter) mAdapter;
                recyclerBaseAdapter.getDatas().remove(this.mData);
                recyclerBaseAdapter.notifyItemRemoved(position);
                if (recyclerBaseAdapter.getDatas().size() <= 0) {
                    recyclerBaseAdapter.holderNotifyRefresh(null);
                }
            }


        }
        if (ConstantUrl.STORE_SHOPCAR_DELECTED != data.getUrl()) {
            setShowRepertoryNum();
        }


    }

    @Override
    public void onError(Call call, Exception e, int i) {
        setShowRepertoryNum();
        ToastUtil.showToast(BaseApplication.getContext(), R.string.not_netwroking);
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        setShowRepertoryNum();
        ToastUtil.showToast(BaseApplication.getContext(), c_codeBean.getM());
    }

    private void setShowRepertoryNum() {
        tempNumber = mNumber;
        mTv_repertory.setText(mNumber + "");
        setEditEnable(true);
    }

    @Override
    public void onClickPositive(DialogInterface dialogInterface, int which) {
        Map<String, String> requestParameter = new HashMap<>();
        requestParameter.put(StringConstants.STRING_USER_ID, Preference.get(IntentDataKeyConstant.ID, StringConstants.STRING_EMPTY));
        requestParameter.put(IntentDataKeyConstant.CART_ID, shopCarInfo.getId());
        //删除
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SHOPCAR_DELECTED, requestParameter, new BaseBean(), true);

    }

    @Override
    public void onClickNegative(DialogInterface dialogInterface, int which) {

    }

    public interface ShopCarOnItemSelectedListener {

        void shopCarOnItemSelected(int position);
    }


    @Override
    public void bindHolder(int position) {
        this.position = position;
        if (mData instanceof AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean) {
            shopCarInfo = (AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean) mData;
            mTv_good_title.setText(shopCarInfo.getGoods_name());
            //     mTv_shopcar_color.setText(shopCarInfo);
            mTv_pice.setText(StringConstants.STRING_RMB + shopCarInfo.getGoods_price());
            Glide.with(BaseApplication.getContext()).load(ConstantUrl.hosturl + shopCarInfo.getThumb_url()).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv_good_picture);
        }
        mNumber = (int) Double.parseDouble(shopCarInfo.getGoods_num());
        tempNumber = mNumber;

        mTv_repertory.setText(mNumber + "");
        if (shopCarInfo != null) {
            mIv_shopcar_select_icon.setImageResource(shopCarInfo.isSelected() ? R.mipmap.shopcar_select : R.mipmap.shopcar_notselect);

        }


    }


    private void setEditEnable(boolean editEnable) {
        mTv_subtract.setEnabled(editEnable);
        mTv_shopcar_add.setEnabled(editEnable);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shopcar_add:
            case R.id.tv_subtract:
                setEditEnable(false);
                if (mTv_repertory != null && !TextUtils.isEmpty(mTv_repertory.getText())) {
                    int currentShowNumber = Integer.parseInt(mTv_repertory.getText().toString());
                    if (v.getId() == R.id.tv_shopcar_add) {
                        tempNumber = currentShowNumber + 1;

                    } else {

                        if (currentShowNumber > 1) {
                            tempNumber = currentShowNumber - 1;
                        } else {
                            tempNumber = 1;
                            ToastUtil.showToast(BaseApplication.getContext(), "数量不能少于1");
                            setEditEnable(true);
                            return;
                        }
                    }
                    Map<String, String> map = new HashMap();
                    map.put(StringConstants.STRING_USER_ID, Preference.get(IntentDataKeyConstant.ID, StringConstants.STRING_EMPTY));
                    map.put(IntentDataKeyConstant.CART_ID, shopCarInfo.getId());
                    if (shopCarInfo.getGoods_id() != null) {
                        map.put(IntentDataKeyConstant.GOODS_ID, shopCarInfo.getGoods_id());
                    }
                    map.put(IntentDataKeyConstant.GOODS_NUM, tempNumber + StringConstants.STRING_EMPTY);

                    mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_SHOPCAR_EDIT_COUNT, map, new ShopCarEditCountBean(), true);

                }
                break;
            case R.id.rl_select:
                if (shopCarInfo != null) {
                    shopCarInfo.setSelected(!shopCarInfo.isSelected());
                    mIv_shopcar_select_icon.setImageResource(shopCarInfo.isSelected() ? R.mipmap.shopcar_select : R.mipmap.shopcar_notselect);
                    if (shopCarOnItemSelectedListener != null) {
                        shopCarOnItemSelectedListener.shopCarOnItemSelected(position);
                    }

                }

                break;

            case R.id.rl_item_delect:
                ArshowDialogUtils.showDialog(itemView.getContext(), "确定删除吗？", this);

                break;
        }
    }
}
