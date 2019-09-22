package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/20.
 */

public class MyPopForSelect {
    PopupWindow popupWindow;
    private RecyclerView recyclerview_popwindow_select;
    private View contentView;
    private Pop_itemSelectListener call;
    private EditText editText;
    //触发pop弹出来的view
    View clickView;
    //recyclerview父布局
    private RelativeLayout relativeLayout_pop_recyclefather, relativeLayout_pop_selector;
    //隐藏布局
    private FrameLayout framelayout_forPop_Dismiss;

    private Context context;
    //条目最大宽度
    private int with = 0;
    private MyPopForSelectAdapter mAdapter;

    public MyPopForSelect(List<PopoItemBean> list, int layoutId, Context context, View clickView, Pop_itemSelectListener call) {
        this.call = call;
        if (layoutId <= 0) {
            return;
        }
        this.context = context;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                int unitsize=36;
                if(list.get(i).getText().length() >=4){
                    unitsize=28;
                }
                if (list.get(i).getText().length() * ArshowContextUtil.dp2px(unitsize) > with) {
                    with = list.get(i).getText().length() * ArshowContextUtil.dp2px(unitsize);
                }
            }
        }

        //默认：可提供 R.layout.layout_popupwindow_selected
        contentView = View.inflate(context, layoutId, null);
        relativeLayout_pop_recyclefather = (RelativeLayout) contentView.findViewById(R.id.relativeLayout_pop_recyclefather);
        relativeLayout_pop_selector = (RelativeLayout) contentView.findViewById(R.id.relativeLayout_pop_selector);
//        framelayout_forPop_Dismiss = (FrameLayout) contentView.findViewById(R.id.framelayout_forPop_Dismiss);
        this.clickView = clickView;
        //设置弹窗
        setPop();
        //设置recyclerview
        setRecyclerView(contentView, list);
    }

    public void setClickViewView(View clickView) {
        if (clickView != null) {
            this.clickView = clickView;
        }

    }

    public View getContentView() {
        return contentView;
    }

    public void showAtLocation(int gravity, int x, int y) {
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(contentView, gravity, x, y);
        }
    }

    public  void show(){
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        }
    }
    private void setPop() {
        //根据字数计算每个条目的长度
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
//
//        framelayout_forPop_Dismiss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
        relativeLayout_pop_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        setPopPosition();
    }

    public void setData(List<PopoItemBean> list) {
        if (mAdapter != null) {
            mAdapter.refreshData(list);
        }
    }

    private void setRecyclerView(View contentView, List<PopoItemBean> list) {
        recyclerview_popwindow_select = (RecyclerView) contentView.findViewById(R.id.recyclerview_popwindow_select);
        recyclerview_popwindow_select.setLayoutManager(new LinearLayoutManager(contentView.getContext()));
        mAdapter = new MyPopForSelectAdapter();
        recyclerview_popwindow_select.setAdapter(mAdapter);
        mAdapter.refreshData(list);

        //选择监听事件
        mAdapter.setOnItemClickListener(new OnRecyclerItemListener<PopoItemBean>() {
            @Override
            public void onItemClick(View v, PopoItemBean data) {
//                data.setSelect(true);
//                v.setBackgroundColor(v.getResources().getColor(R.color.txt_d7d7d7));
//                int childCount = recyclerview_popwindow_select.getChildCount();
//                for (int i = 0; i < childCount; i++) {
//                    if (v != recyclerview_popwindow_select.getChildAt(i)) {
//                        recyclerview_popwindow_select.getChildAt(i).setBackgroundColor(v.getResources().getColor(R.color.txt_fafafa));
//                        MyPopForSelectHolder childViewHolder = (MyPopForSelectHolder) recyclerview_popwindow_select.getChildViewHolder(recyclerview_popwindow_select.getChildAt(i));
//                        childViewHolder.getData().setSelect(false);
//
//                    }
//                }

                popupWindow.dismiss();
                if (call != null) {
                    call.getSelectedString(data);
                }
            }
        });
    }

    public interface Pop_itemSelectListener {
        void getSelectedString(PopoItemBean data);
    }

    public void setPopCenterVetical() {
        //获取参照物坐标
        int loacation[] = new int[2];
        clickView.getLocationInWindow(loacation);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerview_popwindow_select.getLayoutParams();
//        layoutParams.setMargins(loacation[0]-relativeLayout_pop_recyclefather.getWidth()/2, loacation[1] + ArshowContextUtil.dp2px(10), 0, 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //注意paras会让xml文档的布局样式重置，所以这里要重新写一遍
        recyclerview_popwindow_select.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) relativeLayout_pop_recyclefather.getLayoutParams();
        layoutParams1.width = with;
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams1.setMargins(0, loacation[1] + ArshowContextUtil.dp2px(10), 0, 0);
        relativeLayout_pop_recyclefather.setLayoutParams(layoutParams1);
        showAtLocation(Gravity.CENTER, 0, 0);
    }

    public void setPopPosition() {
        //获取参照物坐标
        int loacation[] = new int[2];
        clickView.getLocationInWindow(loacation);
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) relativeLayout_pop_recyclefather.getLayoutParams();
        layoutParams1.width = with;
//        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //注意减去状态栏的高度
        layoutParams1.addRule(RelativeLayout.ALIGN_RIGHT,clickView.getId());
        layoutParams1.setMargins(loacation[0]+clickView.getWidth()-with, loacation[1]+clickView.getHeight()/2, 0, 0);
        relativeLayout_pop_recyclefather.setLayoutParams(layoutParams1);
        showAtLocation(Gravity.CENTER, 0, 0);
    }

    public void setPopPosition(int x, int y) {
        //获取参照物坐标
        int loacation[] = new int[2];
        clickView.getLocationInWindow(loacation);
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) relativeLayout_pop_recyclefather.getLayoutParams();
        layoutParams1.width = with;
//        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //注意减去状态栏的高度
        layoutParams1.setMargins(loacation[0] + x, loacation[1] + y, 0, 0);
        relativeLayout_pop_recyclefather.setLayoutParams(layoutParams1);
        showAtLocation(Gravity.CENTER, 0, 0);
    }

    public void setPopPosition(int x, int y, int with) {
        //获取参照物坐标
        int loacation[] = new int[2];
        clickView.getLocationInWindow(loacation);
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) relativeLayout_pop_recyclefather.getLayoutParams();
        layoutParams1.width = with;
//        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //注意减去状态栏的高度
        layoutParams1.setMargins(loacation[0] + x, loacation[1] + y, 0, 0);
        relativeLayout_pop_recyclefather.setLayoutParams(layoutParams1);
        showAtLocation(Gravity.CENTER, 0, 0);
    }

    public void setPopWithMacthScreen(int x, int y) {
        //获取参照物坐标
        int loacation[] = new int[2];
        clickView.getLocationInWindow(loacation);
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) relativeLayout_pop_recyclefather.getLayoutParams();
        layoutParams1.width = ArshowContextUtil.getWidthPixels();
//        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //注意减去状态栏的高度
        layoutParams1.setMargins(0, loacation[1] + y, 0, 0);
        relativeLayout_pop_recyclefather.setLayoutParams(layoutParams1);
        showAtLocation(Gravity.CENTER, 0, 0);
    }


}
