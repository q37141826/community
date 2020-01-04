package com.qixiu.intelligentcommunity.mvp.view.holder.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.mine.PointsDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

/**
 * Created by HuiHeZe on 2017/9/8.
 */

public class PointsHolder extends RecyclerBaseHolder<PointsDetailsBean.OBean.ListBean> {


    private TextView textView_isGainOrPayPoints, textView_time, textView_gameName, textView_points;
    public PointsHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_isGainOrPayPoints = (TextView) itemView.findViewById(R.id.textView_isGainOrPayPoints);
        textView_time = (TextView) itemView.findViewById(R.id.textView_time);
        textView_gameName = (TextView) itemView.findViewById(R.id.textView_gameName);
        textView_points = (TextView) itemView.findViewById(R.id.textView_points);
    }

    @Override
    public void bindHolder(int position) {
            PointsDetailsBean.OBean.ListBean bean = (PointsDetailsBean.OBean.ListBean) mData;
            String fuhao="-";
            if (bean.getInter_type() == 1) {
                textView_isGainOrPayPoints.setText("魔豆获取");
                fuhao="+";
            } else {
                textView_isGainOrPayPoints.setText("魔豆支出");
                fuhao="-";
            }
            textView_time.setText(bean.getAddtime());
            textView_gameName.setText(bean.getType()==1?"(  商城获取  )":"(  大转盘游戏  )");
            textView_points.setText(fuhao+"  "+bean.getInter()+"");
    }
}
