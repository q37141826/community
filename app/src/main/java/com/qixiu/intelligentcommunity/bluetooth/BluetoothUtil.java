package com.qixiu.intelligentcommunity.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.engine.BluetoothEngine;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothDevicePackaging;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothMatchBean;
import com.qixiu.intelligentcommunity.utlis.Preference;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by my on 2018/10/12.
 */

public class BluetoothUtil {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);//配对的单通道线程池
    private static final ExecutorService executorServiceOpen = Executors.newFixedThreadPool(1);//开锁的线程池

//    public interface BluetoothCallBack<T>{
//        void  callBack(T t);
//    }

    //传输数据
    public static void startTransprot(final BluetoothEngine mBluetoothEngine, final BluetoothDevice mDevice,
                                      final Object data, final BluetoothService.ConnectCallBack connectCallBack) {
        // 开启线程去执行
        executorServiceOpen.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (data != null && data instanceof BluetoothMatchBean) {
                        BluetoothMatchBean bluetoothMatchBean = (BluetoothMatchBean) data;
                        final BluetoothDevicePackaging bluetoothDevicePackaging = new BluetoothDevicePackaging();
                        bluetoothDevicePackaging.setBluetoothDevice(mDevice);
                        bluetoothDevicePackaging.setHardwareId(bluetoothMatchBean.getO().getDevice());
                        mBluetoothEngine.connectToBluetooth(bluetoothDevicePackaging, connectCallBack);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    public interface PairListenner {
        void onSucsses();

        void onFialed();
    }

    public static synchronized void startPair(final Object data, final BluetoothDevice mDevice, final BluetoothEngine mBluetoothEngine, final BluetoothService.ConnectCallBack connectCallBack) {
//        // 开启线程去执行
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {//!mBluetoothEngine.isBluetoothPair(mDevice.getAddress())
//                        try {
//                            Method creMethod = BluetoothDevice.class.getMethod("createBond");
//                            Boolean  invoke = (Boolean) creMethod.invoke(mDevice);
//                            open(data, mBluetoothEngine, mDevice, connectCallBack);
//                            Log.e("bluestate", "配对结果"+invoke);
//                        } catch (Exception e) {
//                            connectCallBack.onFailed(mDevice, e);
//                            e.printStackTrace();
//                        }
//                    } else {
//                        open(data, mBluetoothEngine, mDevice, connectCallBack);
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//        });

        Observable.create(new Observable.OnSubscribe<BluetoothDevice>() {
            @Override
            public void call(Subscriber<? super BluetoothDevice> subscriber) {
                subscriber.onNext(mDevice);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())//指定subscribe()发生在io线程
                .observeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//指定subscribe的回掉发生在主线程
                .subscribe(new Observer<BluetoothDevice>() {
                    @Override
                    public void onCompleted() {
                        open(data, mBluetoothEngine, mDevice, connectCallBack);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BluetoothDevice mDevice) {
                        if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {//!mBluetoothEngine.isBluetoothPair(mDevice.getAddress())
                            try {
                                Method creMethod = BluetoothDevice.class.getMethod("createBond");
                                Boolean invoke = (Boolean) creMethod.invoke(mDevice);
                                Log.e("bluestate", "配对结果" + invoke);
                            } catch (Exception e) {
                                connectCallBack.onFailed(mDevice, e);
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private static void open(Object data, BluetoothEngine mBluetoothEngine, BluetoothDevice mDevice, BluetoothService.ConnectCallBack connectCallBack) {
        if (data != null && data instanceof BluetoothMatchBean) {
            BluetoothMatchBean bluetoothMatchBean = (BluetoothMatchBean) data;
            final BluetoothDevicePackaging bluetoothDevicePackaging = new BluetoothDevicePackaging();
            bluetoothDevicePackaging.setBluetoothDevice(mDevice);
            bluetoothDevicePackaging.setHardwareId(bluetoothMatchBean.getO().getDevice());
            mBluetoothEngine.connectToBluetooth(bluetoothDevicePackaging, connectCallBack);
        }
    }


    public static BluetoothMatchBean getTestBean() {
        String data = "{\"c\":1,\"m\":\"\\u5339\\u914d\\u6210\\u529f\",\"o\":{\"id\":622,\"mac\":\"WHJ0006015\",\"device\":\"ID5056C06015\",\"ssid\":\"WHJ0006015\",\"estate\":50,\"period\":\"\",\"building\":\"\",\"unit\":\"\",\"backup\":\"\\u516c\\u53f8\\u81ea\\u5df1\\u7684\\u84dd\\u7259\\u8bbe\\u5907\"},\"e\":\"\"}";
        Gson gson = new Gson();
        BluetoothMatchBean bean = gson.fromJson(data, BluetoothMatchBean.class);
        return bean;
    }

    public static final String BLUETOOTH_KEY = "BLUETOOTH_KEY";

    public static void putIntoSp(Object data) {
        if (data instanceof BluetoothMatchBean) {
            BluetoothMatchBean bean = (BluetoothMatchBean) data;
            Gson gson = new Gson();
            String oringeData = Preference.get(BLUETOOTH_KEY, "");
            if (!TextUtils.isEmpty(oringeData)) {
                ListBlueToothMatchBean listBlueToothMatchBean = gson.fromJson(oringeData, ListBlueToothMatchBean.class);
                listBlueToothMatchBean.getList().add(bean);
                String toSaveData = gson.toJson(listBlueToothMatchBean);
                Preference.put(BLUETOOTH_KEY, toSaveData);
            } else {
                ListBlueToothMatchBean listBlueToothMatchBean = new ListBlueToothMatchBean();
                List<BluetoothMatchBean> list = new ArrayList<>();
                list.add(bean);
                listBlueToothMatchBean.setList(list);
                String toSaveData = gson.toJson(listBlueToothMatchBean);
                Preference.put(BLUETOOTH_KEY, toSaveData);
            }
        }
    }

    public static BluetoothMatchBean findData(String name) {
        BluetoothMatchBean bluetoothMatchBean = null;
        Gson gson = new Gson();
        String oringeData = Preference.get(BLUETOOTH_KEY, "");
        if (!TextUtils.isEmpty(oringeData)) {
            ListBlueToothMatchBean listBlueToothMatchBean = gson.fromJson(oringeData, ListBlueToothMatchBean.class);
            for (int i = 0; i < listBlueToothMatchBean.getList().size(); i++) {
                if (listBlueToothMatchBean.getList().get(i).getO().getSsid().contains(name)) {
                    bluetoothMatchBean = listBlueToothMatchBean.getList().get(i);
                }
            }
        }
        return bluetoothMatchBean;
    }


    public static class ListBlueToothMatchBean {
        List<BluetoothMatchBean> list;

        public List<BluetoothMatchBean> getList() {
            return list;
        }

        public void setList(List<BluetoothMatchBean> list) {
            this.list = list;
        }
    }

    /**
     * Clears the internal cache and forces a refresh of the services from the
     * remote device.//用反射的办法清除蓝牙缓存
     */
    public static boolean refreshDeviceCache(BluetoothGatt mBluetoothGatt) {
        if (mBluetoothGatt != null) {
            try {
                BluetoothGatt localBluetoothGatt = mBluetoothGatt;
                Method localMethod = localBluetoothGatt.getClass().getMethod(
                        "refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(
                            localBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception localException) {
                Log.i("ble", "An exception occured while refreshing device");
            }
        }
        return false;
    }

    public static  boolean isHaveMyBlueTooth(List<BluetoothDevice> devices){
        for (int i = 0; i <devices.size() ; i++) {
            String mDeviceName = devices.get(i).getName();
            if (mDeviceName.length() > 10) {
                mDeviceName = devices.get(i).getName().substring(0, 10);
            }
            if (BluetoothUtil.findData(mDeviceName) != null) {
                return true;
            }
        }
        return false;
    }

}
