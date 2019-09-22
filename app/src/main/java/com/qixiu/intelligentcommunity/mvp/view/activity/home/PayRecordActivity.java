package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomePayRecordBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.HomePayRecordAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class PayRecordActivity extends BaseActivity {
    private ImageView imageView_kongbai_payrecord;
    private RelativeLayout relativeLayout_title_payrecord;
    private RecyclerView recyclerview_payRecord;
    private HomePayRecordAdapter adapter;
    //缴哪种费
    int type = 1;
    //
    private int pageNo = 1, pageSize = 10;

    @Override
    protected void onInitData() {
        //获取上个界面的数据
        Bundle extras = getIntent().getExtras();
        type = extras.getInt("type");
        //
        adapter = new HomePayRecordAdapter();
        recyclerview_payRecord.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_payRecord.setAdapter(adapter);
        getSetData();
    }

    @Override
    protected void onInitView() {
        imageView_kongbai_payrecord= (ImageView) findViewById(R.id.imageView_kongbai_payrecord);
        relativeLayout_title_payrecord = (RelativeLayout) findViewById(R.id.relativeLayout_title_payrecord);
        recyclerview_payRecord = (RecyclerView) findViewById(R.id.recyclerview_payRecord);
        inittiltle();
    }

    private void inittiltle() {
        TitleView title = new TitleView(this);
        title.setTitle("缴费记录");
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout_title_payrecord.addView(title.getView());

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pay_record;
    }

    public void getSetData() {
        OkHttpUtils.post().url(ConstantUrl.payRecordUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, "")+"")//Preference.get(ConstantString.USERID, "")//// TODO: 2017/6/23 先用 1这个userid抓取数据
                .addParams("type", type + "")
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    HomePayRecordBean bean = GetGson.init().fromJson(s, HomePayRecordBean.class);
                    if(pageNo==1){
                        bean.getO().getList().get(0).setIS_LAST(true);
                        adapter.refreshData(bean.getO().getList());
                    }else {
                        adapter.addDatas(bean.getO().getList());
                    }
                } catch (Exception e) {
                    ToastUtil.showToast(PayRecordActivity.this,GetGson.parseMessageBean(s).getM());
                }
                if(adapter.getDatas().size()==0&&pageNo==1){
                    imageView_kongbai_payrecord.setVisibility(View.VISIBLE);
                }else {
                    imageView_kongbai_payrecord.setVisibility(View.GONE);
                }
            }
        });


    }
}
