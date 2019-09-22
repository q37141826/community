package com.qixiu.intelligentcommunity.mvp.beans.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class BluetoothDevicePackaging {

    private BluetoothDevice mBluetoothDevice;
    private String hardwareId;

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        mBluetoothDevice = bluetoothDevice;
    }


}
