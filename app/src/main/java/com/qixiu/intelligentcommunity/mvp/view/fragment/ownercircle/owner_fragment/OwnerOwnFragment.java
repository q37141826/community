package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.owner_fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.my_interface.Communication;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;
import com.qixiu.intelligentcommunity.my_interface.web.FragmentRefreshInterface;
import com.qixiu.intelligentcommunity.receiver.BaseReceiverFactory;
import com.qixiu.intelligentcommunity.receiver.ReceiverFactory;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class OwnerOwnFragment extends BaseFragment implements FragmentInterface, View.OnClickListener, Communication {
    private RadioButton rb_owner_whole_owen;
    private RadioButton rb_owner_my_owen;
    private RelativeLayout relativeLayout_owen_forTrance;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private OwenFragmentMine fragment_mine;
    private OwenFragmentAll fragment_all;
    //切换fragment的tag
    private String tags[] = new String[2];

    @Override
    protected void onInitData() {

        manager = getChildFragmentManager();
        fragment_all = new OwenFragmentAll();
        fragment_mine = new OwenFragmentMine();
        tags[0] = fragment_all.getTag();
        tags[1] = fragment_mine.getTag();

        manager.beginTransaction().add(R.id.relativeLayout_owen_forTrance, fragment_all, tags[0]).commit();
        manager.beginTransaction().add(R.id.relativeLayout_owen_forTrance, fragment_mine, tags[1]).commit();
        manager.beginTransaction().hide(fragment_mine).commit();
        manager.beginTransaction().show(fragment_all).commit();
//        transaction.replace(R.id.relativeLayout_owen_forTrance,fragment_all).commit();
        ReceiverFactory instance = BaseReceiverFactory.getInstance();
    IntentFilter intentFilter = new IntentFilter(IntentDataKeyConstant.NOTIFY_OWNERCIRCLE_RELEASESUCCESS_ACTION);
    instance.buildReceiver(getActivity().getApplicationContext(), intentFilter, this);
}

    @Override
    protected void onInitView(View view) {
        rb_owner_whole_owen = (RadioButton) view.findViewById(R.id.rb_owner_whole_owen);
        rb_owner_my_owen = (RadioButton) view.findViewById(R.id.rb_owner_my_owen);
        relativeLayout_owen_forTrance = (RelativeLayout) view.findViewById(R.id.relativeLayout_owen_forTrance);
        initClick();
    }

    private void initClick() {
        rb_owner_whole_owen.setOnClickListener(this);
        rb_owner_my_owen.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_owen_owen;
    }

    @Override
    public void moveToPosition(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_owner_whole_owen:
                switchContent(fragment_mine, fragment_all, 0);
                break;
            case R.id.rb_owner_my_owen:
                switchContent(fragment_all, fragment_mine, 1);
                break;
        }


    }

    public void switchContent(Fragment from, Fragment to, int position) {
        transaction = manager.beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            transaction.hide(from)
                    .add(R.id.relativeLayout_owen_forTrance, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    @Override
    public void startCommunicate(Object... content) {
        Intent intent = null;
        for (int i = 0; i < content.length; i++) {
            if (content[i] instanceof Intent) {
                intent = (Intent) content[i];
            }

        }
        if (intent == null) {
            return;
        }
        if (IntentDataKeyConstant.NOTIFY_OWNERCIRCLE_RELEASESUCCESS_ACTION.equals(intent.getAction())) {
            List<Fragment> fragments = manager.getFragments();
            for (int i = 0; i < fragments.size(); i++) {

                Fragment fragment = fragments.get(i);
                if (fragment.isVisible()) {
                    ((FragmentRefreshInterface) fragment).fragmentRefresh();
                }

            }
        }

    }
}
