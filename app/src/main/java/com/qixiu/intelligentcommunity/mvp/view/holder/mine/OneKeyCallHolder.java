package com.qixiu.intelligentcommunity.mvp.view.holder.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.mine.OnekeyCallBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

import org.w3c.dom.Text;

/**
 * Created by HuiHeZe on 2017/6/21.
 */

public class OneKeyCallHolder extends RecyclerBaseHolder<OnekeyCallBean.OBean.ListBean> {
    TextView textView_name_onekeycall_item, textView_phone_onekeycall_item, textView_onekeycall_item;
    CallPermission call;
    public OneKeyCallHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_name_onekeycall_item = (TextView) itemView.findViewById(R.id.textView_name_onekeycall_item);
        textView_phone_onekeycall_item = (TextView) itemView.findViewById(R.id.textView_phone_onekeycall_item);
        textView_onekeycall_item = (TextView) itemView.findViewById(R.id.textView_onekeycall_item);
    }

    @Override
    public void bindHolder(int position) {

        textView_name_onekeycall_item.setText(mData.getName());
        textView_phone_onekeycall_item.setText(mData.getPhone());
        textView_onekeycall_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call= (CallPermission) mContext;
                call.call(mData.getPhone());
            }
        });
    }

    public interface CallPermission{
        void call(String phone);
    }
}
