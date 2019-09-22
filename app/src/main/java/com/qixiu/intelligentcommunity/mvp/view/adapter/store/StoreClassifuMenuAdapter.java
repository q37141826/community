package com.qixiu.intelligentcommunity.mvp.view.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreClassItemBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

public class StoreClassifuMenuAdapter extends RecyclerBaseAdapter {

    @Override
    public int getLayoutId() {
        return R.layout.adapter_store_classify;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new ClassifyMenuHolder(itemView,context,this);
    }


    public class ClassifyMenuHolder extends RecyclerBaseHolder{
        TextView textStoreMenue;
        public ClassifyMenuHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
            super(itemView, context, adapter);
            textStoreMenue=itemView.findViewById(R.id.textStoreMenue);
        }

        @Override
        public void bindHolder(int position) {
            if(mData instanceof  StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean){
                StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean bean= ( StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean) mData;
                textStoreMenue.setText(bean.getName());
                if(bean.isSelected()){
                    textStoreMenue.setTextColor(mContext.getResources().getColor(R.color.theme_color));
                }else {
                    textStoreMenue.setTextColor(mContext.getResources().getColor(R.color.font_A8A8A8));
                }
            }
        }
    }
}
