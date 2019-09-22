package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class MyPopForInput implements View.OnTouchListener, View.OnKeyListener, View.OnClickListener, TextWatcherAdapterInterface {
    private Dialog dialog;
    private PopupWindow popupWindow;
    private View contentView;
    private TextView btn_input_pop;
    private AppCompatEditText edittext_input_pop;
    private View framelayout_inputPop_Dismiss;
    private String text;
    private View.OnClickListener listener;


    public MyPopForInput(Context context) {
        if (dialog == null) {
            dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.pop_mypop_input);
            dialog.findViewById(R.id.framelayout_inputPop_Dismiss).setOnTouchListener(this);
            btn_input_pop =
                    (TextView) dialog.findViewById(R.id.btn_input_pop);

            edittext_input_pop =
                    (AppCompatEditText) dialog.findViewById(R.id.edittext_input_pop);
            edittext_input_pop.setOnKeyListener(this);
            IntelligentTextWatcher intelligentTextWatcher = new IntelligentTextWatcher(edittext_input_pop.getId(), this);
            intelligentTextWatcher.setEmojiDisabled(true, edittext_input_pop);
            edittext_input_pop.addTextChangedListener(intelligentTextWatcher);
        }
        dialog.show();

//        contentView = View.inflate(context, R.layout.pop_mypop_input, null);
//        popupWindow = new PopupWindow(contentView);
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setFocusable(true);
//        edittext_input_pop = (AppCompatEditText) contentView.findViewById(R.id.edittext_input_pop);
//        btn_input_pop = (TextView) contentView.findViewById(R.id.btn_input_pop);
//        framelayout_inputPop_Dismiss = contentView.findViewById(R.id.framelayout_inputPop_Dismiss);
//        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
//        framelayout_inputPop_Dismiss.setOnTouchListener(this);

    }

    public void setSendListener(final View.OnClickListener listener) {
        this.listener = listener;
        btn_input_pop.setOnClickListener(this);


    }

    public String getText() {
        return edittext_input_pop.getText().toString();
    }

    public View getButtonView() {
        return btn_input_pop;
    }

    public void setHintText(CharSequence charSequence) {
        edittext_input_pop.setHint(charSequence);
    }

    public void setHintText(int resId) {
        edittext_input_pop.setHint(resId);
    }

    public void dismissWindow() {
        dialog.dismiss();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ArshowContextUtil.hideSoftKeybord(v);
        dialog.dismiss();
        return true;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            ArshowContextUtil.hideSoftKeybord(v);
            dialog.dismiss();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (!BaseApplication.getContext().getString(R.string.cancels).equals(btn_input_pop.getText())) {
            if (listener != null) {
                listener.onClick(v);
            }
        }
        dialog.dismiss();
    }

    @Override
    public void beforeTextChanged(int editTextId, CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(int editTextId, CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(int editTextId, Editable s) {
        String toString = edittext_input_pop.getText().toString();
        if (TextUtils.isEmpty(toString) || TextUtils.isEmpty(toString.trim())) {
            if (!BaseApplication.getContext().getString(R.string.cancels).equals(btn_input_pop.getText()))
                btn_input_pop.setText(BaseApplication.getContext().getString(R.string.cancels));
        } else {
            if (!BaseApplication.getContext().getString(R.string.send).equals(btn_input_pop.getText()))
                btn_input_pop.setText(BaseApplication.getContext().getString(R.string.send));
        }
    }
}
