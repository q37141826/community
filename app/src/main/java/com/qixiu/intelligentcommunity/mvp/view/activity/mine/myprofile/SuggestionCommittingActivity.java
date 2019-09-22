package com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class SuggestionCommittingActivity extends BaseActivity {
    private EditText edittext_sugesstion;
    private Button btn_confirm_suggestion;
    private RelativeLayout relativeLayout_title_suggestion;

    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitView() {
        edittext_sugesstion = (EditText) findViewById(R.id.edittext_sugesstion);
        btn_confirm_suggestion = (Button) findViewById(R.id.btn_confirm_suggestion);
        relativeLayout_title_suggestion = (RelativeLayout) findViewById(R.id.relativeLayout_title_suggestion);
        initTitle();
        btn_confirm_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_confirm_suggestion.setEnabled(false);
                startCommit();
            }
        });

    }


    private void startCommit() {
        String content = edittext_sugesstion.getText().toString();
        if (content.equals("")) {
            ToastUtil.showToast(this, "请输入需要反馈的内容");
            return;
        }
        OkHttpUtils.post().url(ConstantUrl.suggestionUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("content", content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                btn_confirm_suggestion.setEnabled(true);
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    ToastUtil.showToast(SuggestionCommittingActivity.this,messageBean.getM());
                    btn_confirm_suggestion.setEnabled(true);
                    finish();
                } catch (Exception e) {
                }
            }
        });


    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setTitle("意见反馈");
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout_title_suggestion.addView(title.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_suggestion_committing;
    }
}
