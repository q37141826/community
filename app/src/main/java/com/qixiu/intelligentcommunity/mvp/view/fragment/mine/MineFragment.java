package com.qixiu.intelligentcommunity.mvp.view.fragment.mine;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.mine.NumBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.HomeWebActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.AuthorizationActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.ChangePasswordActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.MyPointsActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile.MyprofileActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile.OnekeyCallActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile.SettingActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreShopCarActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.order.MyOrderActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadAuthorizationUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadCommunityManager;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadGame;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadMyCardUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadMyRelease;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadReleaseGoodsUrl;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    Bundle bundle;
    private CircleImageView cirCularImageView_head_mine;
    private ImageView imageView_editmyprofile;
    private TextView textView_nickname_mine,
            textView_myOrder_mine, textView_myShopcar_mine, textView_myPoints_mine,
            textView_houseMessage_mine, textView_shopMessage_mine;
    private RelativeLayout relativeLayout_onekeycall_mine, relativeLayout_myProduceCard_mine,
            relativeLayout_myPowerUser_mine, relativeLayout_changePsw_mine,
            relativeLayout_suggstioncommit_mine, relativeLayout_head_mine, mBelowme_03, relativeLayout_identify_mine,
            relativeGotoSuperMarket,relativeGotoGameCenter,relativeLayout_community_manager;
    private LinearLayout linearlayout_house_mine, linearlayout_shop_mine;
    String permissions[] = {Manifest.permission.CALL_PHONE};
    private String utype = "";
    private String auth = "";
    private BroadcastReceiver receiver;

    @Override
    protected void onInitData() {
        String url = Preference.get(ConstantString.HEAD, "");
        Glide.with(getContext()).load(url).error(R.mipmap.headplace).into(cirCularImageView_head_mine);
        textView_nickname_mine.setText(Preference.get(ConstantString.NICK_NAME, "").equals("") ? Preference.get(ConstantString.PHONE, "") : Preference.get(ConstantString.NICK_NAME, ""));
    }

    @Override
    protected void onInitView(View view) {
        relativeLayout_head_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_head_mine);
        cirCularImageView_head_mine = (CircleImageView) view.findViewById(R.id.cirCularImageView_head_mine);
        imageView_editmyprofile = (ImageView) view.findViewById(R.id.imageView_editmyprofile);
        textView_nickname_mine = (TextView) view.findViewById(R.id.textView_nickname_mine);
        textView_myOrder_mine = (TextView) view.findViewById(R.id.textView_myOrder_mine);
        textView_myShopcar_mine = (TextView) view.findViewById(R.id.textView_myShopcar_mine);
        textView_myPoints_mine = (TextView) view.findViewById(R.id.textView_myPoints_mine);
        textView_houseMessage_mine = (TextView) view.findViewById(R.id.textView_houseMessage_mine);//房屋出租消息条数
        textView_shopMessage_mine = (TextView) view.findViewById(R.id.textView_shopMessage_mine);//二手商品消息条数
        relativeLayout_onekeycall_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_onekeycall_mine);
        relativeLayout_myProduceCard_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_myProduceCard_mine);
        relativeLayout_myPowerUser_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_myPowerUser_mine);
        relativeLayout_changePsw_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_changePsw_mine);
        relativeLayout_suggstioncommit_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_suggstioncommit_mine);
        relativeGotoSuperMarket = (RelativeLayout) view.findViewById(R.id.relativeGotoSuperMarket);
        relativeGotoGameCenter = (RelativeLayout) view.findViewById(R.id.relativeGotoGameCenter);
        relativeLayout_community_manager = (RelativeLayout) view.findViewById(R.id.relativeLayout_community_manager);
//        mBelowme_03 = (RelativeLayout) view.findViewById(R.id.belowme_03);
        relativeLayout_identify_mine = (RelativeLayout) view.findViewById(R.id.relativeLayout_identify_mine);
        linearlayout_house_mine = (LinearLayout) view.findViewById(R.id.linearlayout_house_mine);
        linearlayout_shop_mine = (LinearLayout) view.findViewById(R.id.linearlayout_shop_mine);
        setClick();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getSetData();
            }
        };
        IntentFilter intentFilter = new IntentFilter("com.qixiu.example.broadcast.normal");
        intentFilter.setPriority(600);
        getContext().registerReceiver(receiver, intentFilter);
    }

    private void setClick() {
        relativeLayout_head_mine.setOnClickListener(this);
        imageView_editmyprofile.setOnClickListener(this);
        textView_myOrder_mine.setOnClickListener(this);
        textView_myShopcar_mine.setOnClickListener(this);
        textView_myPoints_mine.setOnClickListener(this);
        linearlayout_house_mine.setOnClickListener(this);
        linearlayout_shop_mine.setOnClickListener(this);
        relativeLayout_onekeycall_mine.setOnClickListener(this);
        relativeLayout_myProduceCard_mine.setOnClickListener(this);
        relativeLayout_myPowerUser_mine.setOnClickListener(this);
        relativeLayout_changePsw_mine.setOnClickListener(this);
        relativeLayout_suggstioncommit_mine.setOnClickListener(this);
        relativeLayout_identify_mine.setOnClickListener(this);
//        mBelowme_03.setOnClickListener(this);
        relativeGotoSuperMarket.setOnClickListener(this);
        relativeGotoGameCenter.setOnClickListener(this);
        relativeLayout_community_manager.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main_mine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout_head_mine:
            case R.id.imageView_editmyprofile:    //编辑我的资料
                if (Preference.getBoolean("IS_FROM_APP")) {
                    return;
                }
                CommonUtils.startIntent(getContext(), MyprofileActivity.class);
                break;
            case R.id.textView_myOrder_mine:  //我的订单
                CommonUtils.startIntent(getContext(), MyOrderActivity.class);
                break;
            case R.id.textView_myShopcar_mine://我的购物车
                CommonUtils.startIntent(getContext(), StoreShopCarActivity.class);
                break;
            case R.id.textView_myPoints_mine://我的积分
                CommonUtils.startIntent(getContext(), MyPointsActivity.class);
                break;
            case R.id.linearlayout_house_mine://房屋租售消息
                //  ToastUtil.showToast(getContext(), "敬请期待");
//                bundle = new Bundle();
//                bundle.putInt("position", 0);
//                CommonUtils.startIntent(getContext(), MyPublishActivity.class, bundle);

                Intent releaseSale = new Intent(getActivity(), HomeWebActivity.class);
                releaseSale.setAction(IntentDataKeyConstant.HOME_RELEASESALE_ACTION);
                releaseSale.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadMyRelease + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(releaseSale);
                break;
            case R.id.linearlayout_shop_mine://商品消息

                Intent releaseGood = new Intent(getActivity(), HomeWebActivity.class);
                releaseGood.setAction(IntentDataKeyConstant.HOME_RELEASEGOOD_ACTION);
                releaseGood.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadReleaseGoodsUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(releaseGood);
//                bundle = new Bundle();
//                bundle.putInt("position", 1);
//                CommonUtils.startIntent(getContext(), MyPublishActivity.class, bundle);
                break;

            case R.id.relativeLayout_onekeycall_mine://拨打电话
//                if(!hasPermission(getContext(),permissions)){
//                    hasRequse(getActivity(),1,permissions);
//                    return;
//                }
                CommonUtils.startIntent(getContext(), OnekeyCallActivity.class);
                break;
            case R.id.relativeLayout_myProduceCard_mine://我的明信片
                if (!StringConstants.STRING_4.equals(utype) && StringConstants.STRING_1.equals(auth)) {
                    Intent myCard = new Intent(getActivity(), HomeWebActivity.class);
                    myCard.setAction(IntentDataKeyConstant.HOME_MYCARD_ACTION);
                    myCard.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadMyCardUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                    startActivity(myCard);
                } else {
                    ToastUtil.toast(R.string.no_permission);
                }

                break;
            case R.id.relativeLayout_myPowerUser_mine://我的授权用户
                Intent authorization = new Intent(getActivity(), HomeWebActivity.class);
                authorization.setAction(IntentDataKeyConstant.HOME_AUTHORIZATION_ACTION);
                authorization.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadAuthorizationUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(authorization);
                break;
            case R.id.relativeLayout_changePsw_mine://修改密码
                if (Preference.getBoolean("IS_FROM_APP")) {
                    return;
                }
                CommonUtils.startIntent(getContext(), ChangePasswordActivity.class);
                break;
            case R.id.relativeLayout_suggstioncommit_mine://意见反馈
                CommonUtils.startIntent(getContext(), SettingActivity.class);
                break;
            case R.id.belowme_03://意见反馈
                Intent news = new Intent(getActivity(), HomeWebActivity.class);
                news.setAction(IntentDataKeyConstant.HOME_MYRELEASE_ACTION);
                news.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadMyRelease + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(news);
                break;
            case R.id.relativeLayout_identify_mine:
                if (auth.equals(2 + "")) {
                    ToastUtil.toast("已经提交审核，请耐心等待");
                    return;
                }
                Intent intent = new Intent(getContext(), AuthorizationActivity.class);
                bundle = new Bundle();
                bundle.putString(ConstantString.PHONE, Preference.get(ConstantString.PHONE, ""));
                bundle.putString(ConstantString.PASSWORD, Preference.get(ConstantString.PASSWORD, ""));
                intent.putExtras(bundle);
                intent.putExtra(ConstantString.FROM_WHERE, "mine");
                startActivity(intent);
                break;

            case R.id.relativeGotoSuperMarket:
                // TODO: 2019/9/15 这个地方要跳住哪哪里
                CommonUtils.startIntent(getContext(), StoreActivity.class);
                break;

            case R.id.relativeGotoGameCenter:
                Intent gameManager = new Intent(getActivity(), HomeWebActivity.class);
                gameManager.setAction(IntentDataKeyConstant.HOME_GAME_ACTION);
                gameManager.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadGame + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(gameManager);
                break;


            case R.id.relativeLayout_community_manager:
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent commnityManager = new Intent(getActivity(), HomeWebActivity.class);
                commnityManager.setAction(IntentDataKeyConstant.HOME_COMMNITYMANAGER_ACTION);
                commnityManager.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadCommunityManager + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(commnityManager);
                break;
        }
    }

    public void getSetData() {
        OkHttpUtils.post().url(ConstantUrl.mineUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    NumBean numBean = GetGson.init().fromJson(s, NumBean.class);
                    int house = numBean.getO().getHouse();
                    int product = numBean.getO().getProduct();
                    utype = numBean.getO().getUid().getUtype();
                    auth = numBean.getO().getUid().getAuth();
                    Log.e("auth", auth);
                    Preference.put(ConstantString.NICK_NAME, numBean.getO().getUid().getNickname());
                    Glide.with(getContext()).load(Preference.get(ConstantString.HEAD, "")).error(R.mipmap.headplace).into(cirCularImageView_head_mine);
                    textView_nickname_mine.setText(numBean.getO().getUid().getNickname() == null || numBean.getO().getUid().getNickname().equals("")
                            ? Preference.get(ConstantString.PHONE, "") : numBean.getO().getUid().getNickname());
                    textView_houseMessage_mine.setText(house + "");
                    textView_shopMessage_mine.setText(product + "");
//是否显示去认证的选项
                    if (!auth.equals(1 + "")) {
                        utype = 4 + "";
                    }
                    relativeLayout_identify_mine.setVisibility(utype.equals("4") ? View.VISIBLE : View.GONE);
                    Preference.put(ConstantString.UTYPE, utype);
                } catch (Exception e) {
                    try {
                        MessageBean messageBean = GetGson.parseMessageBean(s);
                        ToastUtil.showToast(getContext(), messageBean.getM());
                    } catch (Exception e1) {

                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSetData();
        try {
            if (Preference.get(ConstantString.UTYPE, "").equals("1")) {
                relativeLayout_myPowerUser_mine.setVisibility(View.VISIBLE);
            } else {
                relativeLayout_myPowerUser_mine.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            relativeLayout_myPowerUser_mine.setVisibility(View.GONE);
        }
        //todo 判断是否为第三方登录的
//        try {
//            if (Preference.getBoolean("IS_FROM_APP")) {
//                relativeLayout_changePsw_mine.setVisibility(View.GONE);
//            } else {
//                relativeLayout_changePsw_mine.setVisibility(View.VISIBLE);
//            }
//        } catch (Exception e) {
//        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        relativeLayout_identify_mine.setVisibility(View.GONE);
        if (!hidden) {
            getSetData();
        }
    }

    //权限回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    CommonUtils.startIntent(getContext(), OnekeyCallActivity.class);
                } else {
                    return;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }
}
