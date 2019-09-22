package com.qixiu.intelligentcommunity.receiver;

import android.content.Context;
import android.content.IntentFilter;

import com.qixiu.intelligentcommunity.my_interface.Communication;


/**
 * Created by Administrator on 2017/6/1 0001.
 */

public abstract class ReceiverFactory {

    public abstract void buildReceiver(Context context, IntentFilter intentFilter, Communication communication);

    public abstract void destroyBuildReceiver(Context context);


}
