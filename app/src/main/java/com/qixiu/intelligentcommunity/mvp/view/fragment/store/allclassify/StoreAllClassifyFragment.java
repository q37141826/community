package com.qixiu.intelligentcommunity.mvp.view.fragment.store.allclassify;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreClassItemBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.classify.StoreClassifyListActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.classify.StoreAllClassifyAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.TitleFragment;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/26.
 */
public class StoreAllClassifyFragment extends TitleFragment implements AdapterView.OnItemClickListener, OKHttpUIUpdataListener {

    public static String ALL_CLASSIFY_DATA = "allClassifyData";


    private GridView mGv_allclassify;
    private OKHttpRequestModel mOkHttpRequestModel;
    private StoreAllClassifyAdapter mStoreAllClassifyAdapter;


    @Override
    protected void onInitData() {
        mOkHttpRequestModel = new OKHttpRequestModel(this);

        requestData();
//        storeAllClassifyAdapter.setData(parcelableArrayList);
//        gvAllclassify.setAdapter(storeAllClassifyAdapter);
    }

    private void requestData() {

        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_CLASSIFY, null, new StoreClassItemBean());
    }


    @Override
    protected void onInitViewNew(View view) {
        mTitleView.setTitle("全部分类");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGv_allclassify = (GridView) view.findViewById(R.id.gv_allclassify);
        mStoreAllClassifyAdapter = new StoreAllClassifyAdapter();
        mGv_allclassify.setAdapter(mStoreAllClassifyAdapter);
        mGv_allclassify.setOnItemClickListener(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_store_allclassify;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean itemAtPosition = (StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean) parent.getItemAtPosition(position);
        intent.setClass(getActivity(), StoreClassifyListActivity.class);
        intent.putExtra(IntentDataKeyConstant.ID, itemAtPosition.getId());
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (data instanceof StoreClassItemBean) {
            StoreClassItemBean.StoreClassItemInfo dataInfo = ((StoreClassItemBean) data).getO();
            mStoreAllClassifyAdapter.refreshData(dataInfo.getCate());
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }
}
