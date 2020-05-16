package com.qixiu.intelligentcommunity.mvp.view.activity.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clj.fastble.data.ScanResult;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.ScreenManager;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothService;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothServiceBle;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothUtil;
import com.qixiu.intelligentcommunity.bluetooth.ReleseSingle;
import com.qixiu.intelligentcommunity.constants.Constant;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.constants.permission.PermissionCollectConstants;
import com.qixiu.intelligentcommunity.engine.BluetoothEngine;
import com.qixiu.intelligentcommunity.engine.jpush.JpushEngine;
import com.qixiu.intelligentcommunity.listener.MainBlueToothIntf;
import com.qixiu.intelligentcommunity.listener.OnClickSwitchListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.PermissionNotice;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothMatchBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.NewsStateBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.UnReadMessageBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.BlueToothManageActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.messagelist.MessageListActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.AnswersEditDynamicActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.EditDynamicActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.StoreShopCarActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.search.StoreSearchListActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.home.HomeFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.mine.MineFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.OwnerCircleFragmentNew;
import com.qixiu.intelligentcommunity.mvp.view.fragment.serve.ServeFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.StoreFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.VersionCheckUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MainActivity extends TitleActivity implements OnClickSwitchListener, View.OnTouchListener, BluetoothService.ConnectCallBack, MainBlueToothIntf, OKHttpUIUpdataListener {

    private RelativeLayout activity_main;
    private TextView mTv_title;
    private TextView mTv_main_home;
    private TextView mTv_main_ownercircle;
    private TextView mTv_main_serve;
    private TextView mTv_main_store;
    private TextView mTv_main_mine;
    private ImageView mIv_main_serve;
    private List<BaseFragment> mFragmentList;
    private LinearLayout mLl_main_bottom;
    private int mBottomTextCount;
    private int[] selectResourceid = {R.mipmap.main_bottom_home_select, R.mipmap.main_bottom_ownercircle_select, R.mipmap.main_bottom_serve_select, R.mipmap.main_bottmo_store_icon_select, R.mipmap.main_bottom_mine_select};
    private int[] defaultResourceid = {R.mipmap.main_bottom_home, R.mipmap.main_bottom_ownercircle, R.mipmap.main_bottom_serve_select, R.mipmap.main_bottmo_store_icon, R.mipmap.main_bottom_mine};
    //当前页面
    private int cunrrentPosition = 0;
    private int cunrrentTitlePosition = 0;
    //消息列表状态
    private int messageState = 0;
    private View mLl_store_title;
    private View mInclude_title;
    List<View> listTextView = new ArrayList<>();
    //退出登录的广播接收者（主页面也要关闭）
    BroadcastReceiver receiver;
    private long lastClickTime;
    Timer timer = new Timer();
    int times = 0;
    private BluetoothDevice mDevice;
    List<BluetoothDevice> mBluetoothDevices = new ArrayList<>();
    private BluetoothEngine mBluetoothEngine;
    ZProgressHUD mZProgressHUD;
    private String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private OKHttpRequestModel okHttpRequestModel;

    @Override
    protected void onInitData() {
        okHttpRequestModel = new OKHttpRequestModel(this);
        mZProgressHUD = new ZProgressHUD(this);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        initBluetoothInfo();
        IntentFilter filter = new IntentFilter(IntentDataKeyConstant.BROADCAST_MAIN_FINISH);
        registerReceiver(receiver, filter);
        getIshaveMessage();
        if (hasPermission(photoPermission)) {
            VersionCheckUtil.checkVersion(this);
            JpushEngine.initJPush(getContext());
        }

    }

    @Override
    public void getUnreadMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", LoginUtils.getLoginId());
        okHttpRequestModel.okhHttpPost(ConstantUrl.unReadMessageUrl, map, new UnReadMessageBean());
    }

    private void initBluetoothInfo() {
        mBluetoothEngine = new BluetoothEngine(callback);
        BluetoothService.registerBroadcastReceiver(getActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
        destroyBluetoothInfos();
    }

    @Subscribe
    public void onEventReceived(PermissionNotice bean) {
        hasRequse(1, photoPermission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasPermission(photoPermission)) {
            VersionCheckUtil.checkVersion(this);
        }
    }

    public void setOwnerCircleTitle(int cunrrentPosition) {
        this.cunrrentTitlePosition = cunrrentPosition;
        if (cunrrentPosition == 2) {
            if (mTv_more.getVisibility() == View.VISIBLE) {
                mTv_more.setVisibility(View.GONE);
            }
            ViewGroup parent = (ViewGroup) mTv_more.getParent();
            parent.setEnabled(false);
        } else {
            if (mTv_more.getVisibility() != View.VISIBLE) {
                mTv_more.setVisibility(View.VISIBLE);
            }

            ViewGroup parent = (ViewGroup) mTv_more.getParent();
            parent.setEnabled(true);


        }
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        EventBus.getDefault().register(this);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        EditText et_store_title = (EditText) findViewById(R.id.et_store_title);
        et_store_title.setFocusable(false);
        et_store_title.setOnTouchListener(this);
        mInclude_title = findViewById(R.id.include_title);
        mLl_store_title = findViewById(R.id.ll_store_title);
        if (!hasPermission(PermissionCollectConstants.bluetoothPermissons)) {
            hasRequse(0, PermissionCollectConstants.bluetoothPermissons);
        }
        RelativeLayout rl_title_right = (RelativeLayout) findViewById(R.id.rl_title_right);
        if (ib_back != null) {
            ib_back.setVisibility(View.GONE);
        }
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setTextColor(getResources().getColor(R.color.white));

        mTv_title.setText(R.string.main_home_txt);
        mTv_main_home = (TextView) findViewById(R.id.tv_main_home);
        mTv_main_ownercircle = (TextView) findViewById(R.id.tv_main_ownercircle);
        mTv_main_serve = (TextView) findViewById(R.id.tv_main_serve);
        mIv_main_serve = (ImageView) findViewById(R.id.iv_main_serve);
        mTv_main_store = (TextView) findViewById(R.id.tv_main_store);
        mTv_main_mine = (TextView) findViewById(R.id.tv_main_mine);
        mLl_main_bottom = (LinearLayout) findViewById(R.id.ll_main_bottom);
        mBottomTextCount = mLl_main_bottom.getChildCount();

        initStoreTitleView();
        iniListener();
        initFragments();
        setOtherStyle(mTv_main_home);
        listTextView.add(mTv_main_home);
        listTextView.add(mTv_main_ownercircle);
        listTextView.add(mTv_main_serve);
        listTextView.add(mTv_main_store);
        listTextView.add(mTv_main_mine);
    }

    public View getFootView(int index) {
        if (index < 0 || index > 4) {
            return null;
        }
        return listTextView.get(index);
    }

    public Fragment getFragment(int index) {
        if (index < 0 || index > 4) {
            return null;
        }
        return mFragmentList.get(index);
    }

    private void initStoreTitleView() {

        View rl_shopcar = findViewById(R.id.rl_shopcar);
        rl_shopcar.setOnClickListener(this);
    }

    private void iniListener() {
        mTv_main_home.setOnClickListener(this);
        mTv_main_ownercircle.setOnClickListener(this);
        mTv_main_serve.setOnClickListener(this);
        mIv_main_serve.setOnClickListener(this);
        mTv_main_store.setOnClickListener(this);
        mTv_main_mine.setOnClickListener(this);
    }

    private void initFragments() {
        mFragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setOnClickSwitchListener(this);
        homeFragment.setBlueToothIntf(this);
        mFragmentList.add(homeFragment);
        mFragmentList.add(new OwnerCircleFragmentNew());
        mFragmentList.add(new ServeFragment());
        mFragmentList.add(new StoreFragment());
        mFragmentList.add(new MineFragment());
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        for (int i = 0; i < mFragmentList.size(); i++) {

            fragmentTransaction.add(R.id.fl_main_fragment, mFragmentList.get(i),
                    mFragmentList.get(i).getClass().getName());
        }
        fragmentTransaction.commit();
        switchPager(0, null);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    /**
     * 切换页面
     *
     * @param index
     */
    public void switchPager(int index, Bundle bundle) {
        cunrrentPosition = index;

        if (index < 0 || index >= mFragmentList.size()) {// 防止角标越界
            index = 0;
        }
        // 更改我的中心的的标题样式
        changeCenterActivityTitleStyle(index);

        hideOther(index);// 隐藏其他
        BaseFragment fragment = mFragmentList.get(index);
        fragment.setBundle(bundle);
        mSupportFragmentManager.beginTransaction().show(fragment).commit();
    }

    private void changeCenterActivityTitleStyle(int index) {
        ViewGroup parent = (ViewGroup) mTv_more.getParent();
        parent.setEnabled(true);
        TextView childAt = (TextView) mLl_main_bottom.getChildAt(index);
        mTv_more.setVisibility(getString(R.string.main_home_txt).equals(childAt.getText()) || (getString(R.string.main_ownercircle_txt).equals(childAt.getText()) && cunrrentTitlePosition != 2) ? View.VISIBLE : View.GONE);
        mTv_title.setText(getString(R.string.main_mine_txt).equals(childAt.getText()) ? getString(R.string.main_personalcenter_txt) : childAt.getText());
        mTv_more.setText(getString(R.string.main_ownercircle_txt).equals(childAt.getText()) ? getString(R.string.main_tltle_right_release_txt) : StringConstants.EMPTY_STRING);
        tv_message_num.setVisibility(getString(R.string.main_home_txt).equals(childAt.getText()) && !tv_message_num.getText().toString().equals("0") ? View.VISIBLE : View.GONE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (messageState == 1 && cunrrentPosition == 0) {
//                mTv_more.setCompoundDrawablesWithIntrinsicBounds(getString(R.string.main_home_txt).equals(childAt.getText()) ? R.mipmap.home_have_message : 0, 0, 0, 0);
//            } else {
        mTv_more.setCompoundDrawablesWithIntrinsicBounds(getString(R.string.main_home_txt).equals(childAt.getText()) ? R.mipmap.home_no_message2x : 0, 0, 0, 0);
//            }
//        } else {
//            if (messageState == 1 && cunrrentPosition == 0) {
//                mTv_more.setCompoundDrawables(getResources().getDrawable(getString(R.string.main_home_txt).equals(childAt.getText()) ? R.mipmap.home_have_message : 0), null, null, null);
//            } else {
//                mTv_more.setCompoundDrawables(getResources().getDrawable(getString(R.string.main_home_txt).equals(childAt.getText()) ? R.mipmap.home_no_message2x : 0), null, null, null);
//            }
//        }
        changeColor();
//        if (getString(R.string.main_home_txt).equals(childAt.getText())) {
//            mInclude_title.setVisibility(View.GONE);
//        } else {
//            mInclude_title.setVisibility(View.VISIBLE);
//        }
    }

    private void changeColor() {
        if (mTv_title.getText().toString().equals("个人中心")) {
            mTv_title.setTextColor(getResources().getColor(R.color.white));
            activity_main.setBackgroundResource(R.mipmap.mine_bg);
        } else {
            mTv_title.setTextColor(getResources().getColor(R.color.white));
            activity_main.setBackgroundResource(R.mipmap.home_bg);
        }
        if (cunrrentPosition == 3) {
//            if (mInclude_title.getVisibility() != View.GONE) {
//                mInclude_title.setVisibility(View.GONE);
//            }
//            mLl_store_title.setVisibility(View.VISIBLE);
            activity_main.setBackgroundResource(R.mipmap.home_bg);
        } else {
            if (mLl_store_title.getVisibility() != View.GONE) {
                mLl_store_title.setVisibility(View.GONE);
            }
            if (mInclude_title.getVisibility() != View.VISIBLE) {
                mInclude_title.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 隐藏其他fragment
     *
     * @param index
     */
    private void hideOther(int index) {
        for (int i = 0; i < mFragmentList.size(); i++) {
            if (i != index) {
                mSupportFragmentManager.beginTransaction().hide(mFragmentList.get(i)).commit();
            }
        }

    }

    @Override
    protected void onOtherClick(View view) {

        switch (view.getId()) {
            case R.id.iv_main_serve:
                startOneKeyOpen();
                break;
            case R.id.rl_shopcar:

                Intent intent = new Intent(this, StoreShopCarActivity.class);
                startActivity(intent);
                break;
            default:
                if (StringConstants.STRING_4.equals(Preference.get(ConstantString.UTYPE, StringConstants.EMPTY_STRING)) && (view == mIv_main_serve || view == mTv_main_serve || view == mTv_main_ownercircle)) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                swtichToFragment(view, null);
                break;


        }


    }


    private void setOtherStyle(View view) {
        mTv_main_home.setTextColor(getResources().getColor(R.color.textColor));
        mTv_main_mine.setTextColor(getResources().getColor(R.color.textColor));
        mTv_main_ownercircle.setTextColor(getResources().getColor(R.color.textColor));
        mTv_main_serve.setTextColor(getResources().getColor(R.color.textColor));
        mTv_main_store.setTextColor(getResources().getColor(R.color.textColor));
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(getResources().getColor(R.color.theme_color));
        }
        if (view == mIv_main_serve) {
            mIv_main_serve.setEnabled(false);
            mTv_main_serve.setEnabled(false);
        }
        for (int i = 0; i < mBottomTextCount; i++) {
            View childAt = mLl_main_bottom.getChildAt(i);
            if (view == childAt) {
                view.setEnabled(false);
                mIv_main_serve.setEnabled(view == mTv_main_serve ? false : true);

            } else {
                childAt.setEnabled(true);
                if (view == mIv_main_serve && childAt == mTv_main_serve) {
                    childAt.setEnabled(false);
                }
            }
            if (childAt != mTv_main_serve) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ((TextView) childAt).setCompoundDrawablesWithIntrinsicBounds(0, view == childAt ? selectResourceid[i] : defaultResourceid[i], 0, 0);
                } else {
                    ((TextView) childAt).setCompoundDrawables(null, getResources().getDrawable(view == childAt ? selectResourceid[i] : defaultResourceid[i]), null, null);
                }
            }


        }

    }

    @Override
    protected void onTitleRightClick() {
        switch (cunrrentPosition) {
            case 0:
                CommonUtils.startIntent(MainActivity.this, MessageListActivity.class);
                break;
            case 1:
                //    EditDynamicActivity.start(this);
                Intent intent = new Intent();
                switch (cunrrentTitlePosition) {

                    case 0:
                        intent.setClass(this, EditDynamicActivity.class);
                        startActivityForResult(intent, IntentRequestCodeConstant.TONATIVEOWNER_RELEASE_REQUESTCODE);
                        break;
                    case 1:
                        intent.setClass(this, AnswersEditDynamicActivity.class);
                        startActivityForResult(intent, IntentRequestCodeConstant.TONATIVEANSWER_RELEASE_REQUESTCODE);
                        break;


                }
                break;
        }
    }


    @Override
    protected void onBackClick() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Preference.get(ConstantString.USERID, "").equals("")) {
            finish();
        }
        getIshaveMessage();
    }

    public void getIshaveMessage() {
        //右上角是否有消息
        OkHttpUtils.post().url(ConstantUrl.newsStateUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    NewsStateBean newsStateBean = GetGson.init().fromJson(s, NewsStateBean.class);
                    messageState = Integer.parseInt(newsStateBean.getE());
                    changeCenterActivityTitleStyle(cunrrentPosition);
                } catch (Exception e) {
                }
            }
        });
    }

    /*
     * 退出方法开始
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastClickTime <= 2000) {
                finish();
            } else {
                ToastUtil.toast("再按一次退出应用");
            }
            lastClickTime = System.currentTimeMillis();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    public void swtichToFragment(View view, Bundle bundle) {
        setOtherStyle(view);
        if (view == mIv_main_serve) {
            switchPager(mLl_main_bottom.indexOfChild(mTv_main_serve), bundle);
        } else {
            switchPager(mLl_main_bottom.indexOfChild(view), bundle);
        }
    }

    @Override
    public void onClickSwitch(int index) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentDataKeyConstant.ISEVENT_KEY, true);
        swtichToFragment(mTv_main_ownercircle, bundle);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(this, StoreSearchListActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }


    //开门
    private void startOneKeyOpen() {
        if (!hasPermission(permissions)) {
            hasRequse(0, permissions);
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
    public void bluetoothCall() {
        startOneKeyOpen();
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (data instanceof UnReadMessageBean) {
            UnReadMessageBean unReadMessageBean = (UnReadMessageBean) data;
            tv_message_num.setText(unReadMessageBean.getO().getMessages_unread() + "");
            if (unReadMessageBean.getO().getMessages_unread() == 0 || !mTv_title.getText().toString().equals(getString(R.string.main_home_txt))) {
                tv_message_num.setVisibility(View.GONE);
            } else {
                tv_message_num.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (IntentRequestCodeConstant.TONATIVEOWNER_RELEASE_REQUESTCODE == requestCode && IntentRequestCodeConstant.TONATIVEOWNER_RELEASE_RESULTCODE == resultCode) {


        } else if (IntentRequestCodeConstant.TONATIVEANSWER_RELEASE_REQUESTCODE == requestCode && IntentRequestCodeConstant.TONATIVEANSWER_RELEASE_RESULTCODE == resultCode) {

        }
        if (requestCode == 1000) {
            if (mBluetoothEngine.isBluetoothEnable()) {
                timer.start();
                showProgressAndOpen();
                mBluetoothDevices.clear();
            }
        }
    }

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

    private void showProgressAndOpen() {
        mZProgressHUD.show();
        mZProgressHUD.setMessage("扫描中...");
        mBluetoothEngine.startScanBluetooth(this);
    }

    private void restartScan() {
        mZProgressHUD.show();
        mBluetoothEngine.startScanBluetooth(this);
    }


    public static boolean isLocationOpen(final Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); //gps定位
        boolean isGpsProvider = manager.isProviderEnabled(LocationManager.GPS_PROVIDER); //网络定位
        boolean isNetWorkProvider = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsProvider || isNetWorkProvider;
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
            BluetoothUtil.startPair(BluetoothUtil.findData(mDeviceName), mDevice, mBluetoothEngine, this);
            return;
        }
        stringMap.put(StringConstants.MAC_STRING, mDeviceName);
        BluetoothMatchBean bluetoothMatchBean = new BluetoothMatchBean();
        bluetoothMatchBean.setUrl(ConstantUrl.BLUETOOTH_MATCH_URL);
        OKHttpRequestModel okHttpRequestModel = new OKHttpRequestModel(new OKHttpUIUpdataListener() {
            @Override
            public void onSuccess(final Object data, int i) {
                BluetoothUtil.putIntoSp(data);
                BluetoothUtil.startPair(data, mDevice, mBluetoothEngine, MainActivity.this);
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


    private void destroyBluetoothInfos() {
        mBluetoothEngine.clearResource(getActivity());
        BluetoothService.unregisterBroadcastReceiver(getActivity());
        BluetoothService.disconnect();
    }

}
