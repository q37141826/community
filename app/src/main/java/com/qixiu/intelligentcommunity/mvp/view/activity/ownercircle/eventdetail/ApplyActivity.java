package com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.eventdetail;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.utlis.CheckStringUtils;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.R.id.et_apply_name;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.activityApplyUrl;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class ApplyActivity extends TitleActivity implements OKHttpUIUpdataListener, TextWatcherAdapterInterface, View.OnFocusChangeListener {

    private String mId;
    private EditText mEt_apply_name;
    private EditText mEt_apply_way;
    private String mApplyName;
    private String mApplyWay;
    private OKHttpRequestModel mOkHttpRequestModel;
    private ZProgressHUD mZProgressHUD;
    private IntelligentTextWatcher mIntelligentTextWatcher;
    private IntelligentTextWatcher mIntelligentTextWatcherWay;
    private View mRl_clean_apply_name;
    private View mRl_clean_apply_way;

    @Override
    protected void onOtherClick(View view) {
        switch (view.getId()) {
            case R.id.bt_apply_confirm:
                mApplyName = mEt_apply_name.getText().toString();
                mApplyWay = mEt_apply_way.getText().toString();
                if (TextUtils.isEmpty(mApplyName)) {
                    ToastUtil.toast("请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(mApplyWay)) {
                    ToastUtil.toast("请填写联系方式");
                    return;
                }
                if (!CheckStringUtils.isMobileNO(mApplyWay)) {
                    ToastUtil.toast("请填写正确的电话号码");
                    return;
                }
                mZProgressHUD.setMessage("提交中...");
                mZProgressHUD.show();
                requestData();
                break;
            case R.id.rl_clean_apply_name:
                mEt_apply_name.setText(StringConstants.EMPTY_STRING);
                break;
            case R.id.rl_clean_apply_way:
                mEt_apply_way.setText(StringConstants.EMPTY_STRING);
                break;


        }
    }

    private void requestData() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put(IntentDataKeyConstant.NAME, mApplyName.trim());
        stringMap.put(IntentDataKeyConstant.PHONE, mApplyWay.trim());

        if (!TextUtils.isEmpty(mId)) {
            stringMap.put(IntentDataKeyConstant.AID, mId);
        }
        mOkHttpRequestModel.okhHttpPost(activityApplyUrl, stringMap, new BaseBean(), false);
    }

    @Override
    protected void onTitleRightClick() {

    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mZProgressHUD = new ZProgressHUD(this);
        mTv_more.setVisibility(View.GONE);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        mRl_clean_apply_name = findViewById(R.id.rl_clean_apply_name);

        mRl_clean_apply_way = findViewById(R.id.rl_clean_apply_way);
        mEt_apply_name = (EditText) findViewById(et_apply_name);
        mEt_apply_way = (EditText) findViewById(R.id.et_apply_way);
        Button bt_apply_confirm = (Button) findViewById(R.id.bt_apply_confirm);
        bt_apply_confirm.setOnClickListener(this);
        tv_title.setText("填写个人信息");
        mIntelligentTextWatcher = new IntelligentTextWatcher(mEt_apply_name.getId(), this);
        mIntelligentTextWatcher.setEmojiDisabled(true, mEt_apply_name);
        mIntelligentTextWatcherWay = new IntelligentTextWatcher(mEt_apply_way.getId(), this);
        mIntelligentTextWatcher.setEmojiDisabled(true, mEt_apply_way);
        mEt_apply_name.addTextChangedListener(mIntelligentTextWatcher);
        mEt_apply_way.addTextChangedListener(mIntelligentTextWatcherWay);
        mEt_apply_name.setOnFocusChangeListener(this);
        mEt_apply_way.setOnFocusChangeListener(this);
        mRl_clean_apply_name.setOnClickListener(this);
        mRl_clean_apply_way.setOnClickListener(this);
    }

    @Override
    protected void onBackClick() {
        finish();
    }

    @Override
    protected void onInitData() {
        mId = getIntent().getStringExtra(IntentDataKeyConstant.AID);
        mOkHttpRequestModel = new OKHttpRequestModel(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_apply;
    }

    @Override
    public void onSuccess(Object data, int i) {
        mZProgressHUD.dismissWithSuccess("提交成功");
        AsyncTask<String, Integer, Long> task = new AsyncTask<String, Integer, Long>() {

            @Override
            protected Long doInBackground(String... params) {
                SystemClock.sleep(501);
                return null;
            }

            @Override
            protected void onPostExecute(Long result) {
                super.onPostExecute(result);
                setResult(IntentRequestCodeConstant.TOEVENTAPPLY_RESULTCODE);
                finish();
            }
        };
        task.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEt_apply_name != null) {
            mEt_apply_name.removeTextChangedListener(mIntelligentTextWatcher);
            mIntelligentTextWatcher = null;
        }
        if (mEt_apply_way != null) {
            mEt_apply_way.removeTextChangedListener(mIntelligentTextWatcherWay);
            mIntelligentTextWatcherWay = null;
        }

    }

    @Override
    public void onError(Call call, Exception e, int i) {
        mZProgressHUD.dismissWithFailure("提交失败");
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        mZProgressHUD.dismissWithFailure("提交失败");
    }

    @Override
    public void beforeTextChanged(int editTextId, CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(int editTextId, CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(int editTextId, Editable s) {
        switch (editTextId) {
            case R.id.et_apply_name:
                int phoneShowWay = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                if (mRl_clean_apply_name.getVisibility() != phoneShowWay) {
                    mRl_clean_apply_name.setVisibility(phoneShowWay);
                }

                break;
            case R.id.et_apply_way:
                int passwordShowWay = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                if (mRl_clean_apply_way.getVisibility() != passwordShowWay) {
                    mRl_clean_apply_way.setVisibility(passwordShowWay);
                }

                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case et_apply_name:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_apply_way.getVisibility() == View.VISIBLE) {
                    mRl_clean_apply_way.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mEt_apply_name.getText().toString())) {
                    mRl_clean_apply_name.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.et_apply_way:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_apply_name.getVisibility() == View.VISIBLE) {
                    mRl_clean_apply_name.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mEt_apply_way.getText().toString())) {
                    mRl_clean_apply_way.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
