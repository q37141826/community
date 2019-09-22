package com.qixiu.intelligentcommunity.mvp.model.request.parameter;

import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class OKHttpRequestParameter {

    public static PostFormBuilder addFilesParameter(PostFormBuilder postFormBuilder, Map<String, File> mapFiles) {
        if (mapFiles != null && mapFiles.size() > 0) {
            for (String key : mapFiles.keySet()) {
                postFormBuilder.addFile(key, mapFiles.get(key).getName(), mapFiles.get(key));

            }
        }
        return postFormBuilder;
    }

    public static OkHttpRequestBuilder addStringParameter(OkHttpRequestBuilder okHttpRequestBuilder, Map<String, String> mapString) {
        GetBuilder getBuilder = null;
        PostFormBuilder postFormBuilder = null;
        if (okHttpRequestBuilder instanceof GetBuilder) {
            getBuilder = (GetBuilder) okHttpRequestBuilder;
        } else {
            postFormBuilder = (PostFormBuilder) okHttpRequestBuilder;
        }
        if (mapString != null && mapString.size() > 0) {
            for (String key : mapString.keySet()) {
                if (getBuilder != null) {
                    getBuilder.addParams(key, mapString.get(key));
                } else if (postFormBuilder != null) {
                    postFormBuilder.addParams(key, mapString.get(key));
                }
            }
        }
        return okHttpRequestBuilder;
    }
}
