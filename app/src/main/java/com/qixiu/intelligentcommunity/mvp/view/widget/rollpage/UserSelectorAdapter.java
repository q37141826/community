package com.qixiu.intelligentcommunity.mvp.view.widget.rollpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.model.home_modle.HomeSelectorModle;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.NewWuyePayActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.WuyePayActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.HomeWebActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.ServiceActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.serve.ServeFragment;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.DrawableUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadHelpUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadHousePropertyUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadNeighbourUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadRepairUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadSecondhandMarketUrl;

public class UserSelectorAdapter extends LoopPagerAdapter {

    List<List<Parcelable>> datas = new ArrayList<>();

    public UserSelectorAdapter(RollPagerView viewPager) {
        super(viewPager);
    }


    public void refreshData(List<List<Parcelable>> datas) {

        if (this.datas.size() > 0) {
            this.datas.clear();
        }
        this.datas.addAll(datas == null ? new ArrayList<List<Parcelable>>() : datas);
        notifyDataSetChanged();
    }


    @Override
    public View getView(ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.home_loop_selector, null);
        TextView textViewFirst = view.findViewById(R.id.textView_First);
        TextView textView_Second = view.findViewById(R.id.textView_Second);
        TextView textView_Third = view.findViewById(R.id.textView_Third);
        TextView textView_Forth = view.findViewById(R.id.textView_Forth);
        for (int i = 0; i < datas.get(position).size(); i++) {
            List<Parcelable> data = datas.get(position);
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j) instanceof HomeSelectorModle) {
                    HomeSelectorModle homeSelectorModle = (HomeSelectorModle) data.get(j);
                    switch (j) {
                        case 0:
                            textViewFirst.setText(homeSelectorModle.getText());
                            DrawableUtils.setTopDrawableResouce(textViewFirst, view.getContext(), homeSelectorModle.getDrawbleRes());
                            textViewFirst.setOnClickListener(new SelectorItemClick(view.getContext(), textViewFirst.getText().toString()));
                            break;
                        case 1:
                            textView_Second.setText(homeSelectorModle.getText());
                            DrawableUtils.setTopDrawableResouce(textView_Second, view.getContext(), homeSelectorModle.getDrawbleRes());
                            textView_Second.setOnClickListener(new SelectorItemClick(view.getContext(), textView_Second.getText().toString()));

                            break;
                        case 2:
                            textView_Third.setText(homeSelectorModle.getText());
                            DrawableUtils.setTopDrawableResouce(textView_Third, view.getContext(), homeSelectorModle.getDrawbleRes());
                            textView_Third.setOnClickListener(new SelectorItemClick(view.getContext(), textView_Third.getText().toString()));

                            break;
                        case 3:
                            textView_Forth.setText(homeSelectorModle.getText());
                            DrawableUtils.setTopDrawableResouce(textView_Forth, view.getContext(), homeSelectorModle.getDrawbleRes());
                            textView_Forth.setOnClickListener(new SelectorItemClick(view.getContext(), textView_Forth.getText().toString()));

                            break;
                    }
                }
            }
        }

        return view;
    }

    @Override
    public int getRealCount() {
        return datas == null ? 0 : datas.size();
    }


    public String firstPage[] = {"物业缴费", "在线报修", "求助帮忙", "服务定制"};
    public String secondPage[] = {"二手市场", "邻里议事厅", "房产租赁", "停车缴费"};

    public class SelectorItemClick implements View.OnClickListener {
        Context context;
        String text;

        public SelectorItemClick(Context context, String text) {
            this.context = context;
            this.text = text;
        }

        @Override
        public void onClick(View view) {
            Bundle bundle=new Bundle();
            if (firstPage[0].equals(text)) {
                bundle.putString("titile", "物业缴费");
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                CommonUtils.startIntent(context, NewWuyePayActivity.class, bundle);
                return;
            }
            if (firstPage[1].equals(text)) {
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent repair = new Intent(context, HomeWebActivity.class);
                repair.setAction(IntentDataKeyConstant.HOME_ONLINE_REPAIRS_ACTION);
                repair.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadRepairUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                context.startActivity(repair);
                return;
            }
            if (firstPage[2].equals(text)) {
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent seekhelpIntent = new Intent(context, HomeWebActivity.class);
                seekhelpIntent.setAction(IntentDataKeyConstant.HOME_HELP_ACTION);
                seekhelpIntent.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadHelpUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                context.startActivity(seekhelpIntent);
                return;
            }
            if (firstPage[3].equals(text)) {
                CommonUtils.startIntent(context, ServiceActivity.class);
                return;
            }
            if (secondPage[0].equals(text)) {
                Intent secondHand = new Intent(context, HomeWebActivity.class);
                secondHand.setAction(IntentDataKeyConstant.HOME_SECONDHAND_ACTION);
                secondHand.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadSecondhandMarketUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                context. startActivity(secondHand);
                return;
            }
            if (secondPage[1].equals(text)) {
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                Intent neighIntent = new Intent(context, HomeWebActivity.class);
                neighIntent.setAction(IntentDataKeyConstant.HOME_NEIGH_ACTION);
                neighIntent.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadNeighbourUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                context.startActivity(neighIntent);
                return;
            }
            if (secondPage[2].equals(text)) {
                Intent houseproperty = new Intent(context, HomeWebActivity.class);
                houseproperty.setAction(IntentDataKeyConstant.HOME_HOUSEPROPERTY_LEASE_ACTION);
                houseproperty.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadHousePropertyUrl + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                context.startActivity(houseproperty);
                return;
            }
            if (secondPage[3].equals(text)) {
                if (Preference.get(ConstantString.UTYPE, "").equals(4 + "")) {
                    ToastUtil.toast(R.string.no_permission);
                    return;
                }
                bundle.putString("titile", "停车缴费");
                CommonUtils.startIntent(context, WuyePayActivity.class, bundle);
                return;
            }

        }
    }

}
