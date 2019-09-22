package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.FragmentAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.answer_fragment.OwenAnswerFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.event_fragment.OwenEventFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.owner_fragment.OwnerOwnFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.hviewpager.HackyViewPager;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class OwnerCircleFragmentNew extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private TabLayout tablelayout_owner_circle;
    private HackyViewPager viewpager_owner_circle;
    private String[] titles = new String[]{"邻里动态", "问答交流", "活动区"};
    private List<FragmentInterface> fragmens = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onInitData() {
        fragmens.add(new OwnerOwnFragment());
        fragmens.add(new OwenAnswerFragment());
        fragmens.add(new OwenEventFragment());
        mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmens);
        mFragmentAdapter.setPageTitle(titles);
        viewpager_owner_circle.setAdapter(mFragmentAdapter);
        viewpager_owner_circle.setOffscreenPageLimit(fragmens.size() - 1);
        tablelayout_owner_circle.setupWithViewPager(viewpager_owner_circle);
        tablelayout_owner_circle.addOnTabSelectedListener(this);

    }

    @Override
    protected void onInitView(View view) {
        tablelayout_owner_circle = (TabLayout) view.findViewById(R.id.tablelayout_owner_circle);
        viewpager_owner_circle = (HackyViewPager) view.findViewById(R.id.viewpager_owner_circle);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (getBundle() != null && getBundle().getBoolean(IntentDataKeyConstant.ISEVENT_KEY)) {
                viewpager_owner_circle.setCurrentItem(fragmens.size() - 1,false);
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.owner_circle_list_new;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        MainActivity activity = (MainActivity) getActivity();
        activity.setOwnerCircleTitle(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
