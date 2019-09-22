package com.qixiu.intelligentcommunity.mvp.view.activity.store.address_manage;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AddressListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.picker.AddressInitTask;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class AddAddressActivity extends NewTitleActivity implements View.OnClickListener {
    private String name, phone, province, city, town, address, code;
    private EditText edittext_name_addaddress, edittext_phone_addaddress, edittext_city_addaddress, edittext_address_addaddress, edittext_code_addaddress;
    private Button btn_saveaddress;
    private TextView textview_province, textview_city, textview_town;
    private RelativeLayout relativwlayout_selectaddress;
    private boolean IS_EDIT = false;
    private AddressListBean.OBean addressbean = null;
    private TextView tv_addressedit_head_title;
    private boolean  IS_READY=false;
    @Override
    protected void onInitData() {
        setclick();//设置点击
        getdata();//设置传过来的值
        setdata();//设置控件显示数据
    }

    private void setdata() {
        if (addressbean != null) {
            edittext_name_addaddress.setText(name);
            edittext_phone_addaddress.setText(phone);
            textview_province.setText(province);
            textview_city.setText(city);
            textview_town.setText(town);
            edittext_address_addaddress.setText(address);
            edittext_code_addaddress.setText(code);
        }
    }

    private void getdata() {
        try {
            addressbean = (AddressListBean.OBean) getIntent().getExtras().getSerializable("address");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.this.  finish();
            }
        });
        if (addressbean != null) {
            IS_EDIT = true;
            btn_saveaddress.setText("确认修改");
            mTitleView.setTitle("编辑地址");
            name = addressbean.getConsignee();
            phone = addressbean.getMobile();
            province = addressbean.getProvince();
            city = addressbean.getCity();
            town = addressbean.getDistrict();
            address = addressbean.getAddress();
            code = addressbean.getZipcode();
        } else {
            IS_EDIT = false;
            mTitleView.setTitle("新增地址");
            btn_saveaddress.setText("保存");
            return;
        }
    }

    private void setclick() {
        relativwlayout_selectaddress.setOnClickListener(this);
        btn_saveaddress.setOnClickListener(this);
    }



    @Override
    protected void onInitViewNew() {

//        AddAddressFragment addAddressFragment = new AddAddressFragment();
//        switchFragment(addAddressFragment, R.id.fl_add_address_fragment);
        edittext_name_addaddress = (EditText) findViewById(R.id.edittext_name_addaddress);
        edittext_phone_addaddress = (EditText) findViewById(R.id.edittext_phone_addaddress);
        edittext_city_addaddress = (EditText) findViewById(R.id.edittext_city_addaddress);
        edittext_address_addaddress = (EditText) findViewById(R.id.edittext_address_addaddress);
        edittext_code_addaddress = (EditText) findViewById(R.id.edittext_code_addaddress);
        btn_saveaddress = (Button) findViewById(R.id.btn_saveaddress);
        textview_province = (TextView) findViewById(R.id.textview_province);
        textview_city = (TextView) findViewById(R.id.textview_city);
        textview_town = (TextView) findViewById(R.id.textview_town);
        relativwlayout_selectaddress = (RelativeLayout) findViewById(R.id.relativwlayout_selectaddress);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_address;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_saveaddress:
                btn_saveaddress.setEnabled(false);
                //重新获取一下填写的信息
                regetdata();
                if(!IS_READY){
                    return;
                }
                if (IS_EDIT) {
                    //// TODO: 2017/5/12 把这两个地方的网络地址在constant中修改
                    startEditAddress();
                } else {
                    startAddAdress();
                }
                break;
            case R.id.relativwlayout_selectaddress:
                AddressInitTask execute = new AddressInitTask(this);
                //todo被测试要求不准设置默认地址
                execute.execute("", "", "");
                execute.setOnAddressPickListene(new AddressInitTask.PickListene() {
                    @Override
                    public void setOnAddressPickListene(String province, String city, String count) {
                        AddAddressActivity.this.province = province;
                        AddAddressActivity.this.city = city;
                        town = count;
                        textview_province.setText(province);
                        textview_city.setText(city);
                        textview_town.setText(town);
                    }
                });
                break;
        }
    }

    private void startAddAdress() {
        String token = MD5Util.getToken(ConstantUrl.AddAddressTag);
        OkHttpUtils.post().url(ConstantUrl.AddAddressUrl)
                .addParams("token", token)
                .addParams("userId", Preference.get("id", ""))
                .addParams("consignee", name)
                .addParams("province", province)
                .addParams("city", city)
                .addParams("district", town)
                .addParams("zipcode", code)
                .addParams("mobile", phone)
                .addParams("address", address).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                btn_saveaddress.setEnabled(true);
            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                ToastUtil.showToast(AddAddressActivity.this,messageBean.getM());
                if(messageBean.getC()==1){
                    finish();
                }
                btn_saveaddress.setEnabled(true);
            }
        });

    }

    private void regetdata() {
        name = edittext_name_addaddress.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastUtil.toast("请填写收货人姓名");
            IS_READY=false;
            btn_saveaddress.setEnabled(true);
            return;
        }
        phone = edittext_phone_addaddress.getText().toString();
        if(TextUtils.isEmpty(phone)||! CommonUtils.isMobileNO(phone)){
            ToastUtil.toast("请输入正确格式的电话");
            IS_READY=false;
            btn_saveaddress.setEnabled(true);
            return;
        }
        province = textview_province.getText().toString();
        city = textview_city.getText().toString();
        town = textview_town.getText().toString();
        if(TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||TextUtils.isEmpty(town)){
            ToastUtil.toast("请选择地址");
            IS_READY=false;
            btn_saveaddress.setEnabled(true);
            return;
        }
        address = edittext_address_addaddress.getText().toString();

        code = edittext_code_addaddress.getText().toString();
        if(TextUtils.isEmpty(code)){
            ToastUtil.toast("请输入正确格式的邮编");
            IS_READY=false;
            btn_saveaddress.setEnabled(true);
            return;
        }
        if(TextUtils.isEmpty(address)){
            ToastUtil.toast("请填写地址");
            IS_READY=false;
            btn_saveaddress.setEnabled(true);
            return;
        }
        IS_READY=true;
    }

    private void startEditAddress() {
        String token = MD5Util.getToken(ConstantUrl.EditAddressTag);
        OkHttpUtils.post().url(ConstantUrl.EditressUrl)
                .addParams("token", token)
                .addParams("userId", Preference.get("id", ""))
                .addParams("address_id", addressbean.getAddress_id())
                .addParams("consignee", name)
                .addParams("province", province)
                .addParams("city", city)
                .addParams("district", town)
                .addParams("zipcode", code)
                .addParams("mobile", phone)
                .addParams("address", address).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                btn_saveaddress.setEnabled(true);
            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                ToastUtil.showToast(AddAddressActivity.this,messageBean.getM());
                if (messageBean.getC() == 1) {
                    finish();
                }
                btn_saveaddress.setEnabled(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edittext_name_addaddress.setSelection(edittext_name_addaddress.getText().toString().length());
    }
}
