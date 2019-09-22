package com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle;

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

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.view.activity.upload.UploadPictureActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.utlis.HaowanContextUtil;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class AnswersEditDynamicActivity extends UploadPictureActivity implements TextWatcherAdapterInterface {

    private TextView mTv_title;
    private RecyclerView mRv_answers;
    private EditText mEt_answers_title;
    private EditText mEt_answers_content;
    private Button mBt_release;
    private IntelligentTextWatcher mIntelligentTextWatcher;
    private IntelligentTextWatcher mIntelligentTextWatcher2;


    @Override
    protected void onOtherClick(View view) {
        switch (view.getId()) {

            case R.id.bt_release:

                String answersTitle = mEt_answers_title.getText().toString();
                String answersContent = mEt_answers_content.getText().toString();
                if (TextUtils.isEmpty(answersTitle)|| TextUtils.isEmpty(answersContent) || TextUtils.isEmpty(answersTitle.trim()) ||TextUtils.isEmpty(answersContent.trim()) ) {
                    ToastUtil.toast("请填写标题或内容");
                    return;
                }
                mZProgressHUD.show();
                Map<String, String> mMap = new HashMap<>();
                if (answersTitle != null)
                    mMap.put(StringConstants.TITLE_STRING, answersTitle.trim());
                if (answersContent != null)
                    mMap.put(StringConstants.CONTENT_STRING, answersContent.trim());
                requestUpLoad(ConstantUrl.questionsUrl, mMap);
                break;

        }
    }

    @Override
    public void onSuccess(Object data, int i) {
        Intent intent = new Intent();
        intent.setAction(IntentDataKeyConstant.NOTIFY_ANSWERCIRCLE_RELEASESUCCESS_ACTION);
        sendBroadcast(intent);
        super.onSuccess(data, i);
    }

    @Override

    protected void onTitleRightClick() {

    }

    @Override
    protected void onBackClick() {
        finish();
    }

    @Override
    protected void onInitData() {


    }


    @Override
    protected void onUpLoad() {

    }


    @Override
    protected void onDestroy() {
        if (mEt_answers_title != null && mIntelligentTextWatcher != null) {
            mEt_answers_title.removeTextChangedListener(mIntelligentTextWatcher);
        }
        if (mEt_answers_content != null && mIntelligentTextWatcher2 != null) {
            mEt_answers_content.removeTextChangedListener(mIntelligentTextWatcher2);
        }
        super.onDestroy();
    }

    @Override
    public void initUpLoadView() {
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mEt_answers_title = (EditText) findViewById(R.id.et_answers_title);
        mEt_answers_content = (EditText) findViewById(R.id.et_answers_content);
        mBt_release = (Button) findViewById(R.id.bt_release);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_more.setVisibility(View.GONE);
        mTv_title.setText("问答区");
        mRv_answers = (RecyclerView) findViewById(R.id.rv_answers);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRv_answers.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, HaowanContextUtil.dp2px(10)));
        mRv_answers.setLayoutManager(gridLayoutManager);
        mBt_release.setOnClickListener(this);
        mIntelligentTextWatcher = new IntelligentTextWatcher(mEt_answers_title.getId(), this);
        mIntelligentTextWatcher.setEmojiDisabled(true, mEt_answers_title);
        mEt_answers_title.addTextChangedListener(mIntelligentTextWatcher);
        mIntelligentTextWatcher2 = new IntelligentTextWatcher(mEt_answers_content.getId(), this);
        mIntelligentTextWatcher2.setEmojiDisabled(true, mEt_answers_content);
        mEt_answers_content.addTextChangedListener(mIntelligentTextWatcher2);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRv_answers;
    }

    @Override
    protected int getLayoutResource() {

        return R.layout.activity_answers_editdynamic;
    }


    @Override
    public void beforeTextChanged(int editTextId, CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(int editTextId, CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(int editTextId, Editable s) {

    }
}
