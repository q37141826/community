package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerDetailsBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event.OwenEventDetailBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class AnswerDetailsCommentsInnerHolder extends RecyclerBaseHolder {
    TextView textView_comments_answer_details_inner, textView_answer_details_name1_inner, textView_answer_details_name2_inner;

    public AnswerDetailsCommentsInnerHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_comments_answer_details_inner = (TextView) itemView.findViewById(R.id.textView_comments_answer_details_inner);
        textView_answer_details_name1_inner = (TextView) itemView.findViewById(R.id.textView_answer_details_name1_inner);
        textView_answer_details_name2_inner = (TextView) itemView.findViewById(R.id.textView_answer_details_name2_inner);
    }

    @Override
    public void bindHolder(int position) {
        if (mData instanceof OwenAnswerDetailsBean.EBean.PpuidBean) {
            OwenAnswerDetailsBean.EBean.PpuidBean ppuidBean = (OwenAnswerDetailsBean.EBean.PpuidBean) mData;
            String addstr = " ";
            StringBuffer buffer = new StringBuffer("");
            textView_answer_details_name1_inner.setText(ppuidBean.getUid().getNickname());
            textView_answer_details_name2_inner.setText(ppuidBean.getOb_uid());
            String start = ppuidBean.getUid().getNickname() + " 回复 " + ppuidBean.getOb_uid();
//            for (int i = 0; i < start.length(); i++) {
//                if (isChinese(start.charAt(i) + "")) {
//                    buffer.append(addstr);
//                    buffer.append(addstr);
//                    buffer.append(addstr);
//                    buffer.append(addstr);
//                    buffer.append(addstr);
//                    buffer.append(addstr);
//                } else {
//                    buffer.append(addstr);
//                    buffer.append(addstr);
//
//                }
//

            int i = start.length() / 12 + 2;
            if (i > start.length()) {
                i = start.length();
            }
            String substring = start.substring(0, i);

            start = start + substring;
            SpannableStringBuilder
                    span = new SpannableStringBuilder(start + ppuidBean.getContent());
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, start.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView_comments_answer_details_inner.setText(span);
//            textView_comments_answer_details_inner.setText(((OwenAnswerDetailsBean.EBean.PpuidBean) mData).getContent());
            //      textView_comments_answer_details_inner.setText(buffer.toString() + ppuidBean.getContent());

        } else {
            OwenEventDetailBean.EOwenEventDetailInfo.PpuidInfoBean ppuidBean = (OwenEventDetailBean.EOwenEventDetailInfo.PpuidInfoBean) mData;
            String addstr = " ";
            StringBuffer buffer = new StringBuffer("");
            textView_answer_details_name1_inner.setText(ppuidBean.getUid().getNickname());
            textView_answer_details_name2_inner.setText(ppuidBean.getOb_uid());
            String start = ppuidBean.getUid().getNickname() + " 回复 " + ppuidBean.getOb_uid();
            int i = start.length() / 12 + 2;
            if (i > start.length()) {
                i = start.length();
            }
            String substring = start.substring(0, i);

            start = start + substring;
            SpannableStringBuilder
                    span = new SpannableStringBuilder(start + ppuidBean.getContent());
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, start.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView_comments_answer_details_inner.setText(span);
//            textView_comments_answer_details_inner.setText(buffer.toString() + ppuidBean.getContent());
        }

    }

    public boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }
}
