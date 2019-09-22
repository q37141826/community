package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.Preference;

/**
 * Created by HuiHeZe on 2017/6/20.
 */


public class MyPopForSelectHolder extends RecyclerBaseHolder<PopoItemBean> {
    private TextView textView_pop_selected, textView_pinyin;
    private View view_line;
    private RelativeLayout mV_background;

    public MyPopForSelectHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_pop_selected = (TextView) itemView.findViewById(R.id.textView_pop_selected);
        mV_background = (RelativeLayout) itemView.findViewById(R.id.v_background);
        view_line = itemView.findViewById(R.id.view_line);
        textView_pinyin = (TextView) itemView.findViewById(R.id.textView_pinyin);

    }

    @Override
    public void bindHolder(int position) {
        textView_pop_selected.setText(mData.getText());
        int width = textView_pop_selected.getWidth();
//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width,1);
//        view_line.setLayoutParams(params);
        if (mData.isSelect()) {
            mV_background.setBackgroundColor(mV_background.getResources().getColor(R.color.theme_color));
        } else {
            mV_background.setBackgroundColor(mV_background.getResources().getColor(R.color.gray));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView_pop_selected.setCompoundDrawablesWithIntrinsicBounds(mData.getIcon() > 0 ? mData.getIcon() : 0, 0, 0, 0);
        } else {
            textView_pop_selected.setCompoundDrawables(mData.getIcon() > 0 ? itemView.getResources().getDrawable(mData.getIcon()) : null, null, null, null);
        }
        if (mData.isLast()) {
            view_line.setVisibility(View.GONE);
        } else {
            view_line.setVisibility(View.VISIBLE);
        }
        if (mData.getBackResource() == 0) {
            mV_background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            try {
                mV_background.setBackgroundResource(mData.getBackResource());
                if (mData.getBackResource() == R.drawable.grey_bg) {
                    view_line.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
            }

        }
//        if(mData.isLast()&&mData.getBackResource()==0){
//            mV_background.setBackgroundResource(R.drawable.pop_title_select_bg);
//        }
        if (!TextUtils.isEmpty(mData.getPinyin())) {
            if (Preference.get("pinyin", "").equals(mData.getPinyin())) {
                textView_pinyin.setText("");
            } else {
                Preference.put("pinyin", mData.getPinyin());
                textView_pinyin.setText(mData.getPinyin());
            }

        }
    }
}
