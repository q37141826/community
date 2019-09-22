package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.clj.fastble.data.ScanResult;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothService;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothServiceBle;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothUtil;
import com.qixiu.intelligentcommunity.bluetooth.ReleseSingle;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.BluetoothEngine;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothMatchBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.utlis.Preference;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class BlueToothManageActivity extends NewTitleActivity implements OnRecyclerItemListener, BluetoothService.ConnectCallBack {
    RecyclerView recyclerview;
    private BluetoothEngine mBluetoothEngine;
    ZProgressHUD mZProgressHUD;
    private BlueToothSearchListAdapter adapter;
    private TextView refreshBluetooth;
    private String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private BluetoothDevice selectedDevice;

    public static void start(Context context) {
        Intent intent = new Intent(context, BlueToothManageActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onInitData() {
        mTitleView.setTitle("蓝牙管理");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new BlueToothSearchListAdapter();
        recyclerview.setAdapter(adapter);
        initBluetoothInfo();
        mZProgressHUD = new ZProgressHUD(this);
        adapter.setOnItemClickListener(this);
    }

    private void initBluetoothInfo() {
        mBluetoothEngine = new BluetoothEngine(callback);
        BluetoothService.registerBroadcastReceiver(this);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_blue_tooth_manage;
    }

    @Override
    protected void onInitViewNew() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        refreshBluetooth = (TextView) findViewById(R.id.refreshBluetooth);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        refreshBluetooth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshBluetooth:
                mBluetoothDevices.clear();
                restartScan();
                break;
        }
    }

    private void restartScan() {
        mZProgressHUD.show();
        mBluetoothEngine.startScanBluetooth(this);
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
                BluetoothUtil.startPair(data, mDevice, mBluetoothEngine, BlueToothManageActivity.this);
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mZProgressHUD.dismissWithFailure("网络错误");
                    }
                });
            }

            @Override
            public void onFailure(C_CodeBean c_codeBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mZProgressHUD.dismissWithFailure("没有权限");
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


    List<BluetoothDevice> mBluetoothDevices = new ArrayList<>();

    private BluetoothServiceBle.Callback callback;

    {
        callback = new BluetoothServiceBle.Callback() {


            @Override
            public void onStartScan() {
                if (mBluetoothDevices.size() > 0) {
                    mBluetoothDevices.clear();
                }
            }

            @Override
            public void onScanning(ScanResult result) {
                BluetoothDevice mDevice = result.getDevice();
                if (mDevice != null && !TextUtils.isEmpty(mDevice.getName()) && mDevice.getName().contains(StringConstants.BLUETOOTH_HARDWARENAME_PREFIX)) {
                    mBluetoothDevices.add(mDevice);//添加到列表
                }
            }

            @Override
            public void onScanComplete() {//todo
                if (!mBluetoothEngine.isBluetoothEnable()) {
                    return;
                }
                if (mBluetoothDevices.size() <= 0) {
                    EventBus.getDefault().post(new ReleseSingle());
                }
                mZProgressHUD.dismiss();
                adapter.refreshData(mBluetoothDevices);
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
    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof BluetoothDevice) {
            selectedDevice = (BluetoothDevice) data;
        }
        if (!hasPermission(permissions)) {
            hasRequse(0, permissions);
            return;
        }
        boolean bluetoothEnable = mBluetoothEngine.isBluetoothEnable();
        if (!bluetoothEnable) {
            turnOnBluetooth();
            return;
        }
        mZProgressHUD.show();
        checkBluetoothDevice(selectedDevice);
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

    @Override
    public void onSuccess(BluetoothDevice bluetoothDevice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mZProgressHUD.dismissWithSuccess("开锁成功");
                mBluetoothEngine.cancelScan();
            }
        });
        EventBus.getDefault().post(new ReleseSingle());
    }

    @Override
    public void onFailed(BluetoothDevice bluetoothDevice, Exception e) {
        runOnUiThread(new Runnable() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (mBluetoothEngine.isBluetoothEnable()) {
                mZProgressHUD.show();
                checkBluetoothDevice(selectedDevice);
            }
        }
    }

    public class BlueToothSearchListAdapter extends RecyclerBaseAdapter {

        @Override
        public int getLayoutId() {
            return R.layout.item_bluetooth_search;
        }

        @Override
        public Object createViewHolder(View itemView, Context context, int viewType) {
            return new BlueToothSearchHolder(itemView, context, this);
        }

        public class BlueToothSearchHolder extends RecyclerBaseHolder {
            TextView textViewBlueToothName,
                    textViewBlueToothAddress,
                    textViewBlueToothDiscrib;

            public BlueToothSearchHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
                super(itemView, context, adapter);
                textViewBlueToothName = (TextView) itemView.findViewById(R.id.textViewBlueToothName);
                textViewBlueToothAddress = (TextView) itemView.findViewById(R.id.textViewBlueToothAddress);
                textViewBlueToothDiscrib = (TextView) itemView.findViewById(R.id.textViewBlueToothDiscrib);
            }

            ;

            @Override
            public void bindHolder(int position) {
                if (mData instanceof BluetoothDevice) {
                    BluetoothDevice device = (BluetoothDevice) mData;
                    try {
                        textViewBlueToothAddress.setText(device.getAddress());
                        textViewBlueToothName.setText(device.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyBluetoothInfos();
    }

    private void destroyBluetoothInfos() {
        mBluetoothEngine.clearResource(getActivity());
        BluetoothService.unregisterBroadcastReceiver(getActivity());
        BluetoothService.disconnect();
    }

}
