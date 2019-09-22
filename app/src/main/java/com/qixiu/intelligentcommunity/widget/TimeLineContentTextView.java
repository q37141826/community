package com.qixiu.intelligentcommunity.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Browser;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author XieXianyong
 * Date: 2016.12.12
 * Description: 正文 textview处理(@ http://等)
 */
public class TimeLineContentTextView extends TextView {
    private static final String PHONE = "[1][34578][0-9]{9}";// 手机
    private static final String AT = "@[\\.\\w\\p{InCJKUnifiedIdeographs}-]{1,26}";// @人
    private static final String URL = "http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]";// url
    private static final String TOPIC = "#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#";// ##话题
    private static final String EMOJI = "\\[(\\S+?)\\]";// emoji 表情
    private static final String ALL = "(" + PHONE + ")" + "|" + "(" + AT + ")" + "|" + "(" + URL + ")" + "|" + "(" + TOPIC + ")" + "|" + "(" + EMOJI + ")";
    private Context context;

    public TimeLineContentTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public TimeLineContentTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineContentTextView(Context context) {
        this(context, null);
    }

    public void setContent(CharSequence text) {

        if (TextUtils.isEmpty(text)) {
            return;
        }
        Pattern pattern = Pattern.compile(ALL);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        Matcher matcher = pattern.matcher(spannableStringBuilder);

        while (matcher.find()) {
            final String phone = matcher.group(1);
            final String at = matcher.group(2);
            final String url = matcher.group(3);
            final String topic = matcher.group(4);
            final String emoji = matcher.group(5);

            // 处理phone
            if (phone != null) {
                int start = matcher.start(1);
                int end = start + phone.length();
                OwerCircleContentClickableSpan myClickableSpan = new OwerCircleContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Context context = widget.getContext();
                        // 用intent启动拨打电话
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                };
                spannableStringBuilder.setSpan(myClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
            // 处理@用户
            if (at != null) {
                int start = matcher.start(2);
                int end = start + at.length();
                OwerCircleContentClickableSpan myClickableSpan = new OwerCircleContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {

                    }
                };
                spannableStringBuilder.setSpan(myClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
            // 处理url
            if (url != null) {
                int start = matcher.start(3);
                int end = start + url.length();
                OwerCircleContentClickableSpan myClickableSpan = new OwerCircleContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Uri uri = Uri.parse(url);
                        Context context = widget.getContext();
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
                        context.startActivity(intent);
                    }
                };
                spannableStringBuilder.setSpan(myClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
            //处理#话题#
            if (topic != null) {
                int start = matcher.start(4);
                int end = start + topic.length();
                OwerCircleContentClickableSpan myClickableSpan = new OwerCircleContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {

                    }
                };
                spannableStringBuilder.setSpan(myClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

        }
        setText(spannableStringBuilder);
        setMovementMethod(new CircleMovementMethod(getResources().getColor(R.color.default_clickable_color)));// 不设置 没有点击事件
        setHighlightColor(Color.TRANSPARENT); // 设置点击后的颜色为透明

    }

    @Override
    public boolean hasFocusable() {
        return false;
    }
}
