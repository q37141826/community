package com.qixiu.intelligentcommunity.engine;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.qixiu.intelligentcommunity.bluetooth.BluetoothService;
import com.qixiu.intelligentcommunity.bluetooth.BluetoothServiceBle;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothDevicePackaging;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by gyh on 2017/6/28 0028.
 */

public class BluetoothEngine {

    private final BluetoothServiceBle.Callback mCallback;

    public BluetoothEngine(BluetoothServiceBle.Callback callback) {
        this.mCallback = callback;
    }

    /**
     * 绑定扫描蓝牙服务
     *
     * @param context
     */
    public void bindService(Context context) {
        Intent bindIntent = new Intent(context, BluetoothServiceBle.class);
        context.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }


    public boolean isBluetoothEnable() {
        try {
            BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
            return blueadapter.isEnabled();

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 开始扫描
     */
    public void startScanBluetooth(Context context) {
        if (mBluetoothService != null) {
            mBluetoothService.scanDevice();
        } else {
            bindService(context);
        }


    }

    /**
     * 连接
     */
    public void connectToBluetooth(BluetoothDevicePackaging bluetoothDevicePackaging, BluetoothService.ConnectCallBack connectCallBack) {
        BluetoothService.connectToDevice(bluetoothDevicePackaging, connectCallBack);
    }

    private BluetoothServiceBle mBluetoothService;
    private ServiceConnection mFhrSCon = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothServiceBle.BluetoothBinder) service).getService();
            mBluetoothService.setScanCallback(mCallback);
            mBluetoothService.scanDevice();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }
    };

    /**
     * 注销绑定的蓝牙服务
     *
     * @param context
     */
    public void clearResource(Context context) {
        if (mBluetoothService != null) {
            context.unbindService(mFhrSCon);
        }

    }


    public boolean turnOffBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }
        return false;

    }

    /**
     * 关闭扫描
     */
    public void cancelScan() {
        mBluetoothService.cancelScan();
    }

    /**
     * 判断蓝牙是否已经配对了
     */
    public boolean isBluetoothPair(String bluetoothAddress) {
        if (TextUtils.isEmpty(bluetoothAddress)) {
            return false;
        }
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = blueadapter.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothAddress.equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public void startBluetoothPair(BluetoothDevice bluetoothDevice, OnBluetoothPairListener onBluetoothPairListener) {
        if (onBluetoothPairListener == null)
            return;
        try {
            Method creMethod = BluetoothDevice.class.getMethod("createBond");
            creMethod.invoke(bluetoothDevice);
            onBluetoothPairListener.onPairSuccess();
        } catch (Exception e) {
            onBluetoothPairListener.onPairFailed();
            e.printStackTrace();
        }
    }

    public interface OnBluetoothPairListener {

        void onPairSuccess();

        void onPairFailed();

    }
}
