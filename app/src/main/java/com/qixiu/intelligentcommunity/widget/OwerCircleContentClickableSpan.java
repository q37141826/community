package com.qixiu.intelligentcommunity.widget;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.qixiu.intelligentcommunity.R;

/**
 * Description：
 * Author：XieXianyong
 * Date： 2017/6/23 21:21
 */
public class OwerCircleContentClickableSpan extends ClickableSpan {

    private Context mContext;

    public OwerCircleContentClickableSpan(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View widget) {
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mContext.getResources().getColor(R.color.memory_url_text_color));
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
