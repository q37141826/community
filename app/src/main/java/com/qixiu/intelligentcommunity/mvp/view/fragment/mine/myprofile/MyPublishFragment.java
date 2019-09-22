package com.qixiu.intelligentcommunity.mvp.view.fragment.mine.myprofile;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.myprofile.MyPublishAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;

/**
 * Created by HuiHeZe on 2017/6/15.
 */

public class MyPublishFragment extends BaseFragment implements FragmentInterface {
    private RecyclerView recycleview_my_publish;
    private MyPublishAdapter adapter;
    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitView(View view) {
        recycleview_my_publish= (RecyclerView) view.findViewById(R.id.recycleview_my_publish);


        adapter=new MyPublishAdapter();
        recycleview_my_publish.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview_my_publish.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_publish;
    }

    @Override
    public void moveToPosition(int position) {

    }
}
