package com.qixiu.intelligentcommunity.mvp.model.home_modle;

import android.content.Context;
import android.widget.ImageButton;

import com.qixiu.intelligentcommunity.R;

public class PointSelector {
    private ImageButton ivbtn_yes;
    private ImageButton ivbtn_no;

    public void setSelectOk(boolean selectOk) {
        isSelectOk = selectOk;
    }

    public boolean isSelectOk() {
        return isSelectOk;
    }

    private boolean isSelectOk = false;
    private static PointSelector mInstance;
    private Context mContext;

    private PointSelector() {
    }

    public PointSelector(Context mContext, ImageButton ivbtn_yes, ImageButton ivbtn_no) {
        this.ivbtn_yes = ivbtn_yes;
        this.ivbtn_no = ivbtn_no;
        this.mContext = mContext;
        refreshState();
        setClick();
    }

    private void setClick() {
        if (ivbtn_yes != null && ivbtn_no != null) {
            ivbtn_yes.setOnClickListener(v -> {
                isSelectOk = true;
                refreshState();
            });
            ivbtn_no.setOnClickListener(v -> {
                isSelectOk = false;
                refreshState();
            });
        }
    }

    private void refreshState() {
        if (isSelectOk) {
            ivbtn_yes.setImageResource(R.mipmap.wuye_point_selected_2x);
            ivbtn_no.setImageResource(R.mipmap.wuye_point_no_selected_2x);
        } else {
            ivbtn_yes.setImageResource(R.mipmap.wuye_point_no_selected_2x);
            ivbtn_no.setImageResource(R.mipmap.wuye_point_selected_2x);
        }
    }


}
