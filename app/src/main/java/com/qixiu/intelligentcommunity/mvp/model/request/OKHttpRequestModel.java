package com.qixiu.intelligentcommunity.mvp.model.request;

import android.text.TextUtils;
import android.util.Log;

import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.model.request.parameter.OKHttpRequestParameter;
import com.qixiu.intelligentcommunity.utlis.LogUtil;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.SplitStringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class OKHttpRequestModel<T> {
    public static final String TAG = "OKHttpRequestModel";
    private final OKHttpUIUpdataListener mOkHttpUIUpdataListener;

    public OKHttpRequestModel(OKHttpUIUpdataListener<T> okHttpUIUpdataListener) {

        this.mOkHttpUIUpdataListener = okHttpUIUpdataListener;
    }

    public void okhHttpPost(String url, Map<String, String> map, final BaseBean<T> baseBean, Map<String, File> mapFiles) {

        okhHttpPost(url, map, baseBean, mapFiles, false);


    }

    public void okhHttpPost(String url, Map<String, String> map, final BaseBean<T> baseBean, Map<String, File> mapFiles, boolean isToken) {

        Map<String, String> paramenterStringMap = new HashMap<>();
        if (map != null) {
            paramenterStringMap.putAll(map);
        }

        if (isToken && !TextUtils.isEmpty(url)) {
            paramenterStringMap.put("token", MD5Util.getToken(SplitStringUtils.cutStringPenult(url, "/")));
        } else {

        }
        baseBean.setUrl(url);

        OKHttpExecutor.okHttpExecut(baseBean, OKHttpRequestParameter.addStringParameter(OKHttpRequestParameter.addFilesParameter(OkHttpUtils.post().url(url), mapFiles), paramenterStringMap), mOkHttpUIUpdataListener);

    }

    public void okHttpGet(String url, Map<String, String> stringMap, final BaseBean<T> baseBean) {

        okHttpGet(url, stringMap, baseBean, false);
    }

    public void okHttpGet(String url, Map<String, String> stringMap, final BaseBean<T> baseBean, boolean isToken) {

        Map<String, String> paramenterStringMap = new HashMap<>();
        if (stringMap != null) {
            paramenterStringMap.putAll(stringMap);
        }
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        if (isToken && !TextUtils.isEmpty(url)) {
            paramenterStringMap.put("token", MD5Util.getToken(SplitStringUtils.cutStringPenult(url, "/")));
        } else {

        }
        baseBean.setUrl(url);
        OKHttpExecutor.okHttpExecut(baseBean, OKHttpRequestParameter.addStringParameter(getBuilder, paramenterStringMap), mOkHttpUIUpdataListener);


    }

    public void okhHttpPost(String url, Map<String, String> map, final BaseBean<T> baseBean) {

        okhHttpPost(url, map, baseBean, false);

    }


    public void okhHttpPost(String url, Map<String, String> map, final BaseBean<T> baseBean, boolean isToken) {
        Map<String, String> paramenterStringMap = new HashMap<>();
        if (map != null) {
            paramenterStringMap.putAll(map);
        } else {
            map = new HashMap<>();
        }
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        if (isToken && !TextUtils.isEmpty(url)) {
            paramenterStringMap.put("token", MD5Util.getToken(SplitStringUtils.cutStringPenult(url, "/")));
        } else {

        }
        baseBean.setUrl(url);
        if (LogUtil.isDebug) {
            Set<String> strings = map.keySet();
            StringBuffer stringBuffer = new StringBuffer("");
            for (String key : strings) {
                stringBuffer.append(key + ":  " + map.get(key).toString() + "\n");
            }
            Log.d(TAG, "okhHttpPost: " + stringBuffer.toString());
        }
        OKHttpExecutor.okHttpExecut(baseBean, OKHttpRequestParameter.addStringParameter(builder, paramenterStringMap), mOkHttpUIUpdataListener);
    }


}
