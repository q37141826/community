package com.qixiu.intelligentcommunity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import com.qixiu.intelligentcommunity.my_interface.Communication;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class BaseReceiverFactory extends ReceiverFactory {

    private BaseReceiver mOrderReceiver;

    private BaseReceiverFactory() {

    }

    public static ReceiverFactory getInstance() {

        return new BaseReceiverFactory();
    }

    /**
     * 不要重复的调用该方法，会出现广播重复注册异常，所以需要做保护
     *
     * @param context
     * @param intentFilter
     * @param communication
     */
    @Override
    public void buildReceiver(Context context, IntentFilter intentFilter, Communication communication) {
        if (mOrderReceiver == null) {
            mOrderReceiver = new BaseReceiver(communication);
            context.registerReceiver(mOrderReceiver, intentFilter);
        }


    }

    @Override
    public void destroyBuildReceiver(Context context) {
        if (mOrderReceiver != null)
            context.unregisterReceiver(mOrderReceiver);
    }

    private static class BaseReceiver extends BroadcastReceiver {

        private final WeakReference<Communication> mCommunicationWeakReference;

        public BaseReceiver(Communication communication) {

            mCommunicationWeakReference = new WeakReference<>(communication);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Communication communication = mCommunicationWeakReference.get();
            if (communication != null) communication.startCommunicate(context, intent);
        }
    }


}
