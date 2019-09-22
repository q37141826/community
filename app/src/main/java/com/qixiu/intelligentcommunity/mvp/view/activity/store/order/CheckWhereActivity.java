package com.qixiu.intelligentcommunity.mvp.view.activity.store.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.CheckWhereBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.CheckWhereAdapter;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class CheckWhereActivity extends NewTitleActivity {
    private String order_id;
    private RecyclerView recycleView_checktrance;
    private ImageView imageView_checkwhere;
    private TextView textView_tranceportState, textView_tranceportName, textView_tranceportId;
    private CheckWhereAdapter adapter;
    RelativeLayout relativelayout_back_checkwhere,relativelaout_all,relativelayout_nothing;
    private ImageView imageView_check_nothing;


    @Override
    protected void onInitViewNew() {
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setTitle("物流详情");
        relativelayout_nothing= (RelativeLayout) findViewById(R.id.relativelayout_nothing);
        relativelaout_all= (RelativeLayout) findViewById(R.id.relativelaout_all);
        imageView_check_nothing= (ImageView) findViewById(R.id.imageView_check_nothing);

        recycleView_checktrance = (RecyclerView) findViewById(R.id.recycleView_checktrance);
        imageView_checkwhere = (ImageView) findViewById(R.id.imageView_checkwhere);
        textView_tranceportState = (TextView) findViewById(R.id.textView_tranceportState);
        textView_tranceportName = (TextView) findViewById(R.id.textView_tranceportName);
        textView_tranceportId = (TextView) findViewById(R.id.textView_tranceportId);
        order_id = getIntent().getStringExtra("order_id");
    }

    @Override
    protected void onInitData() {
        String token = MD5Util.getToken(ConstantUrl.CheckWhereTag);
        OkHttpUtils.post().url(ConstantUrl.CheckWhereUrl).addParams("token", token)
                .addParams("order_id", order_id)
                .addParams("userId", Preference.get(ConstantString.USERID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                Gson gson = GetGson.init();
                try {
//                    int i1 = s.indexOf("Url");
//                    int i2=s.lastIndexOf("\"");
//                    String url=s.substring(i1+5,i2);
//                    url=url.replace("\"","").replace("\\","");
                    CheckWhereBean checkWhereBean = gson.fromJson(s, CheckWhereBean.class);
                    Glide.with(CheckWhereActivity.this).load(ConstantUrl.hosturl +checkWhereBean.getImgUrl()).into(imageView_checkwhere);
                    textView_tranceportState.setText(checkWhereBean.getState().equals("3")?"已签收":"未签收");
                    textView_tranceportName.setText(checkWhereBean.getShipping_name());
                    textView_tranceportId.setText(checkWhereBean.getNu());
                    adapter = new CheckWhereAdapter();
                    recycleView_checktrance.setLayoutManager(new LinearLayoutManager(CheckWhereActivity.this));
                    recycleView_checktrance.setAdapter(adapter);
                    adapter.refreshData(checkWhereBean.getData());
                } catch (Exception e) {
                    relativelaout_all.setVisibility(View.GONE);
                    imageView_check_nothing.setVisibility(View.VISIBLE);
                    relativelayout_nothing.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_check_where;
    }


    @Override
    public void onClick(View v) {

    }
}
