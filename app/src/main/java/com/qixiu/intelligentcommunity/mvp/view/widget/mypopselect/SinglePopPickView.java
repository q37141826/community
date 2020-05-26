package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;

import java.util.List;

public class SinglePopPickView {

    private View contentView;
    private Context context;
    private RelativeLayout relativeLayout_back_for_dismiss;
    private PopupWindow popupWindow;
    private MyPickerView picker_view;
    private SelectedLister selectedLister;
    private List<String> datas;
    private String currentSelectedData;
    private Button btn_cancle;
    private Button btn_confirm;
    private PopupWindow.OnDismissListener dismissListener;

    public SinglePopPickView(Context context, View contentView, List<String> datas, SelectedLister selectedLister) {
        this.contentView = contentView;
        this.context = context;
        this.datas = datas;
        this.selectedLister = selectedLister;
        setAllView();
    }


    private void setAllView() {
        contentView = View.inflate(context, R.layout.pop_single_picker, null);
        relativeLayout_back_for_dismiss = contentView.findViewById(R.id.relativeLayout_back_for_dismiss);
        picker_view = contentView.findViewById(R.id.picker_view);
        btn_cancle = contentView.findViewById(R.id.btn_cancle);
        btn_confirm = contentView.findViewById(R.id.btn_confirm);
        picker_view.setShowLines(5);
        picker_view.settextPadding(140,0,0,0);
        btn_confirm.setOnClickListener(v -> {
            selectedLister.onSelected(currentSelectedData);
            popupWindow.dismiss();
        });
        btn_cancle.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        picker_view.setOnSelectListener(text -> {
            currentSelectedData = text;
        });
        setPop();
        showAtLocation(Gravity.CENTER, 0, 0);
        //设置一个空的监听事件，抢夺左侧的界面焦点

    }

    private void setPop() {
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        relativeLayout_back_for_dismiss.setOnClickListener(v -> popupWindow.dismiss());
        setData(datas);
    }

    private void setData(List<String> datas) {
        picker_view.setData(datas);
        currentSelectedData = datas.get(datas.size()/2);
    }

    private void showAtLocation(int gravity, int x, int y) {
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(contentView, gravity, x, y);
        }
    }

    public void show() {
        showAtLocation(Gravity.CENTER, 0, 0);
    }

    public void setDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        if(popupWindow!=null){
            popupWindow.setOnDismissListener(dismissListener);
        }
    }



    public interface SelectedLister<T> {
        void onSelected(T t);
    }

}
