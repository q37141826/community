package com.qixiu.intelligentcommunity.utlis;

import android.text.TextUtils;

import java.util.UUID;

public class DeviceIdUtil {
    private static final String DEVICE_ID = "device_id";
    private static final String UUID_KEY = "uuid_key";

    private DeviceIdUtil() {
    }

    public static String getDeviceId(){
        String deviceId= Preference.get(DEVICE_ID,"");
        if(TextUtils.isEmpty(deviceId)){
            return getUUid();
        }else {
            return deviceId;
        }
    }
    public static void saveDeviceId(String deviceId){
        Preference.put(DEVICE_ID,deviceId);
    }


    private static String getUUid() {
        if (TextUtils.isEmpty(Preference.get(UUID_KEY, ""))) {
            saveUUid();
        }
        return Preference.get(UUID_KEY, "");
    }

    private static void saveUUid() {
        Preference.put(UUID_KEY, MD5Util.MD5(UUID.randomUUID().toString()));
    }
}
