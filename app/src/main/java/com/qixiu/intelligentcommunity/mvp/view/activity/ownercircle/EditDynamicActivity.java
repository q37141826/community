package com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.view.activity.upload.UploadPictureActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：业主圈发布
 * Author：
 * Date： 2017/6/20 15:36XieXianyong
 */
public class EditDynamicActivity extends UploadPictureActivity implements OnRecyclerItemListener, TextWatcherAdapterInterface {
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_dynamic_size)
    TextView mTvDynamicSize;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    private IntelligentTextWatcher mIntelligentTextWatcher;

    public static void start(Context context) {
        Intent intent = new Intent(context, EditDynamicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.edit_dynamic_activity;
    }

    @Override
    protected void onInitData() {
        initEtTv();

    }

    private CharSequence temp;// 监听前的文本
    private final int charMaxNum = 250;

    private void initEtTv() {
        mTvDynamicSize.setText("250/250");
        mIntelligentTextWatcher = new IntelligentTextWatcher(mEtContent.getId(), this);
        mIntelligentTextWatcher.setEmojiDisabled(true, mEtContent);
        mEtContent.addTextChangedListener(mIntelligentTextWatcher);
    }

    String mInputContent;

    @OnClick({R.id.btn_send})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_send://发布

                mInputContent = mEtContent.getText().toString();
                if (TextUtils.isEmpty(mInputContent) || TextUtils.isEmpty(mInputContent.trim())) {
                    ToastUtil.showToast(this, R.string.not_content);
                    return;
                }
                mZProgressHUD.show();
                Map<String, String> map = new HashMap();
                map.put(StringConstants.CONTENT_STRING, mInputContent.trim());
                requestUpLoad(ConstantUrl.ownerCircleReleaseUrl, map);
                break;
        }
    }

    @Override
    protected void onOtherClick(View view) {

    }

    @Override
    protected void onTitleRightClick() {

    }

    @Override
    protected void onBackClick() {
        finish();
    }

    private void initGridView() {
        mRvContent.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRvContent.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, ArshowContextUtil.dp2px(9)));
        mRvContent.setLayoutManager(gridLayoutManager);

    }


    @Override
    protected void onUpLoad() {

    }

    @Override
    public void initUpLoadView() {
        mTv_more.setVisibility(View.GONE);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.release);
        initGridView();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRvContent;
    }


    @Override
    protected void onDestroy() {
        if (mIntelligentTextWatcher != null && mEtContent != null) {
            mEtContent.removeTextChangedListener(mIntelligentTextWatcher);
        }
        super.onDestroy();
    }


    @Override
    public void onSuccess(Object data, int i) {
        Intent intent = new Intent();
        intent.setAction(IntentDataKeyConstant.NOTIFY_OWNERCIRCLE_RELEASESUCCESS_ACTION);
        sendBroadcast(intent);
        super.onSuccess(data, i);
    }

    @Override
    public void beforeTextChanged(int editTextId, CharSequence s, int start, int count, int after) {
        temp = s;
    }

    @Override
    public void onTextChanged(int editTextId, CharSequence s, int start, int before, int count) {
        mTvDynamicSize.setText((charMaxNum - s.length()) + "/250");
    }

    @Override
    public void afterTextChanged(int editTextId, Editable s) {
        if (temp.length() > charMaxNum) {
            Toast.makeText(getApplicationContext(), getString(R.string.edit_dynamic_alert), Toast.LENGTH_LONG).show();
        }
    }
}
