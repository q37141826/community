package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForSelect;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.PopoItemBean;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class WuyePayActivity extends BaseActivity implements View.OnClickListener,  OKHttpUIUpdataListener {
    private RelativeLayout relativeLayout_title_wuyepay,relativeLayout_carpay;
    private  View view_line;
    private Button btn_gotopay;
    private ImageView iv_paytime_icon;
    //显示支付的周期长度
    private EditText textView_showpayhowlong, edittext_input_payhaomuch,edittext_input_carNum;
    //支付周期选项
    List<PopoItemBean> selections = new ArrayList<>();
    //选项弹窗
    MyPopForSelect pop;

    //传给下个界面的参数
    int type = 1;
    int cost_type = 1;
    String money;
    @Override
    protected void onInitData() {
        PopoItemBean  bean;
        if(type==1){
            bean=new PopoItemBean();
            bean.setText("按季度缴费");
            selections.add(bean);
            bean=new PopoItemBean();
            bean.setText("按年度缴费");
            selections.add(bean);
            textView_showpayhowlong.setText("按季度缴费");
        }else {
            bean=new PopoItemBean();
            bean.setText("按月度缴费");
            selections.add(bean);
            bean=new PopoItemBean();
            bean.setText("按季度缴费");
            selections.add(bean);
            bean=new PopoItemBean();
            bean.setText("按年度缴费");
            selections.add(bean);
            textView_showpayhowlong.setText("按月度缴费");
        }
        if(type==1){
            getMoney(2);
        }
    }

    @Override
    protected void onInitView() {
        view_line=findViewById(R.id.view_line);
        edittext_input_carNum= (EditText) findViewById(R.id.edittext_input_carNum);
        relativeLayout_carpay= (RelativeLayout) findViewById(R.id.relativeLayout_carpay);
        edittext_input_payhaomuch = (EditText) findViewById(R.id.edittext_input_payhaomuch);
        iv_paytime_icon = (ImageView) findViewById(R.id.iv_paytime_icon);
        textView_showpayhowlong = (EditText) findViewById(R.id.textView_showpayhowlong);
        relativeLayout_title_wuyepay = (RelativeLayout) findViewById(R.id.relativeLayout_title_wuyepay);
        btn_gotopay = (Button) findViewById(R.id.btn_gotopay);
        initTitle();
        initclick();
    }

    private void initclick() {
        textView_showpayhowlong.setOnClickListener(this);
        btn_gotopay.setOnClickListener(this);

    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        Bundle bundle = getIntent().getExtras();
        String titileStr = bundle.getString("titile");
        if (titileStr.equals("物业缴费")) {
            type = 1;
            edittext_input_payhaomuch.setEnabled(false);
            relativeLayout_carpay.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        } else {
            type = 2;
            relativeLayout_carpay.setVisibility(View.VISIBLE);
//            edittext_input_payhaomuch.setFocusableInTouchMode(true);
//            edittext_input_payhaomuch.setFocusable(true);
//            edittext_input_payhaomuch.requestFocus();
        }
        title.setTitle(titileStr);
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setRightText("缴费记录");
        title.setRightTextVisible(View.VISIBLE);
        title.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                CommonUtils.startIntent(WuyePayActivity.this, PayRecordActivity.class, bundle);
            }
        });
        relativeLayout_title_wuyepay.addView(title.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wuye_pay;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gotopay:
                //// TODO: 2017/6/23 传入参数取支付界面
                getPayData();
                break;
            case R.id.textView_showpayhowlong:
                pop = new MyPopForSelect(selections, R.layout.layout_popupwindow_selected, this, textView_showpayhowlong, new MyPopForSelect.Pop_itemSelectListener() {
                    @Override
                    public void getSelectedString(PopoItemBean data) {
                        textView_showpayhowlong.setText(data.getText());
                        switch (data.getText().toString()) {
                            case "按月度缴费":
                                cost_type = 1;
                                if (type == 1) {
                                    getMoney(1);
                                }
                                break;
                            case "按季度缴费":
                                cost_type = 2;
                                if (type == 1) {
                                    getMoney(2);
                                }
                                break;
                            case "按年度缴费":
                                cost_type = 3;
                                if (type == 1) {
                                    getMoney(3);
                                }
                                break;
                        }
                    }
                });
                break;
        }
    }



    private void getMoney(int type) {
        OkHttpUtils.post().url(ConstantUrl.payWuyeUrl)
                .addParams("uid",Preference.get(ConstantString.USERID, "") )//Preference.get(ConstantString.USERID, "")//// TODO: 2017/6/23 以后吧用户的changge过来
                .addParams("type", type + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    edittext_input_payhaomuch.setText(messageBean.getO());

                } catch (Exception e) {
                }
            }
        });
    }

    public void getPayData() {
      money =edittext_input_payhaomuch.getText().toString();
        if(money.equals("")){
            ToastUtil.showToast(this,"请输入金额");
            return;
        }
        OKHttpRequestModel model = new OKHttpRequestModel(this);
        Map<String, String> map = new HashMap<>();
        map.put("uid",Preference.get(ConstantString.USERID, ""));//// TODO: 2017/6/27以后改为  Preference.get(ConstantString.USERID, "")
        map.put("type", type + "");
        map.put("cost_type", cost_type + "");
        map.put("money",money);
        if(type==2){
            String car_num=edittext_input_carNum.getText().toString();
            if(car_num.equals("")){
                ToastUtil.showToast(this,"请输入车牌号");
                return;
            }
            if(!CommonUtils.is_carNum(car_num)){
                ToastUtil.showToast(this,"请输入正确车牌号");
                return;
            }
            map.put("car_num",car_num);
        }
        model.okhHttpPost(ConstantUrl.gotoPayUrl,map,new BaseBean<>());

    }

    @Override
    public void onSuccess(Object data, int i) {
        BaseBean bean= (BaseBean) data;
          String payid= bean.getO().toString();
        Intent intent;
        if(type==1){
            intent =new Intent(this,AliWeixinPayActivity.class);
            //做个付款类型标记
            Preference.put(ConstantString.payWhat,WuyePayActivity.class.getSimpleName());
        }else {
            intent =new Intent(this,ConfirmCarPayActivity.class);
            intent.putExtra("carNum",edittext_input_carNum.getText().toString());
        }
        intent.putExtra("type",type);
        intent.putExtra("money",money);
        intent.putExtra("payid",payid);
        startActivity(intent);
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }

}
