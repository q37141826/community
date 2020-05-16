package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_event.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event.OwenEventDetailBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer.AnswerDetailsCommentsInnerAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class OwenEventDetailHolder extends RecyclerBaseHolder<OwenEventDetailBean.EOwenEventDetailInfo> implements OnRecyclerItemListener, View.OnClickListener, OKHttpUIUpdataListener {


    private final TextView mTv_eventdetail_username;
    private final ImageView mIv_eventdetail_usericon;
    private final TextView mTv_eventdetail_title_item;
    private final RecyclerView mRv_child_comment;
    private final TextView mTv_eventdetail_date_item;
    private final View mRl_eventdetail;
    private MyPopForInput popInput;
    private final OKHttpRequestModel mOkHttpRequestModel;
    private AnswerDetailsCommentsInnerAdapter mAnswerDetailsCommentsInnerAdapter;
    private OwenEventDetailBean.EOwenEventDetailInfo.PpuidInfoBean mPpuidInfoBean;


    public OwenEventDetailHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        mRl_eventdetail = itemView.findViewById(R.id.rl_eventdetail);
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        mIv_eventdetail_usericon = (ImageView) itemView.findViewById(R.id.iv_eventdetail_usericon);
        mTv_eventdetail_username = (TextView) itemView.findViewById(R.id.tv_eventdetail_username);
        mTv_eventdetail_date_item = (TextView) itemView.findViewById(R.id.tv_eventdetail_date_item);
        mTv_eventdetail_title_item = (TextView) itemView.findViewById(R.id.tv_eventdetail_title_item);
        mRv_child_comment = (RecyclerView) itemView.findViewById(R.id.rv_child_comment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv_child_comment.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, ArshowContextUtil.dp2px(10)));
        mRv_child_comment.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void bindHolder(int position) {
        mAnswerDetailsCommentsInnerAdapter = new AnswerDetailsCommentsInnerAdapter();
        mAnswerDetailsCommentsInnerAdapter.setOnItemClickListener(this);
        mRv_child_comment.setAdapter(mAnswerDetailsCommentsInnerAdapter);
        //    mRl_eventdetail.setOnClickListener(this);
        OwenEventDetailBean.EOwenEventDetailInfo.UidsInfoBean uids = mData.getUids();
        if (uids != null) {
            mTv_eventdetail_username.setText(uids.getNickname());
            Glide.with(itemView.getContext()).load(ConstantUrl.hostImageurl + uids.getHead()).crossFade().into(mIv_eventdetail_usericon);
        }

        mTv_eventdetail_title_item.setText(mData.getContent());
        mTv_eventdetail_date_item.setText(mData.getAdd_time());
        mAnswerDetailsCommentsInnerAdapter.refreshData(mData.getPpuid());

    }

    @Override
    public void onItemClick(View v, Object data) {
        if (data instanceof OwenEventDetailBean.EOwenEventDetailInfo.PpuidInfoBean) {
            mPpuidInfoBean = (OwenEventDetailBean.EOwenEventDetailInfo.PpuidInfoBean) data;
            if ((mPpuidInfoBean.getAuid() + "").equals(LoginUtils.getLoginId())) {
                setDialog(mPpuidInfoBean.getId() + "", "确认删除该评论");
            } else {
                popInput = new MyPopForInput(mContext);
                popInput.setHintText("回复" + mPpuidInfoBean.getUid().getNickname() + "...");
                popInput.setSendListener(this);
            }
        }

    }

    private void setDialog(final String pid, final String notice) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (notice.contains("评论")) {
                    //删除评论
                    startdelete(pid);
                }
                dialog.dismiss();
            }


        });

        builder.setMessage(notice);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void send(String pid, String oId) {
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(pid)) {
            stringMap.put("pid", pid);
        }
        if (!TextUtils.isEmpty(uid)) {

            stringMap.put(IntentDataKeyConstant.UID_KEY, uid);
        }
        if (!TextUtils.isEmpty(oId)) {
            stringMap.put("ob_uid", oId);
        }
        if (!TextUtils.isEmpty(popInput.getText().toString())) {
            stringMap.put(StringConstants.CONTENT_STRING, popInput.getText().toString().trim());
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setUrl(ConstantUrl.activityReplyUrl);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.activityReplyUrl, stringMap, baseBean, false);
    }

    private void startdelete(String pid) {
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(pid)) {
            stringMap.put(IntentDataKeyConstant.ID, pid);
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setUrl(ConstantUrl.activityDelUrl);
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.activityDelUrl, stringMap, baseBean, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.rl_eventdetail:
//                if ((mData.getUid() + "").equals(Preference.get(ConstantString.USERID, ""))) {
//                    setDialog(mData.getId() + "", "确认删除该评论");
//                } else {
//                    popInput = new MyPopForInput(mContext);
//                    popInput.setSendListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //请求服务器
//                            send(mData.getId() + StringConstants.EMPTY_STRING, mData.getUid() + StringConstants.EMPTY_STRING);
//                        }
//                    });
//                }
//                break;
            default:
                TextView buttonView = (TextView) popInput.getButtonView();
                if (BaseApplication.getContext().getString(R.string.cancels).equals(buttonView.getText())) {
                    popInput.dismissWindow();
                    ArshowContextUtil.hideSoftKeybord(buttonView);
                    return;
                }
                if (TextUtils.isEmpty(popInput.getText())) {
                    ToastUtil.toast("请输入内容");
                    return;
                }

                send(mData.getId() + StringConstants.EMPTY_STRING, mPpuidInfoBean.getAuid() + StringConstants.EMPTY_STRING);
                break;

        }
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (data instanceof BaseBean) {
            BaseBean baseBean = (BaseBean) data;
            if (ConstantUrl.activityReplyUrl.equals(baseBean.getUrl())) {
                ToastUtil.toast("回复成功");
                RecyclerBaseAdapter recyclerBaseAdapter = (RecyclerBaseAdapter) mAdapter;
                recyclerBaseAdapter.holderNotifyRefresh(null);
                popInput.dismissWindow();
            } else {
                ToastUtil.toast("删除成功");
                List datas = mAnswerDetailsCommentsInnerAdapter.getDatas();
                datas.remove(mPpuidInfoBean);
                mPpuidInfoBean = null;
                mAnswerDetailsCommentsInnerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        popInput.dismissWindow();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

        if (ConstantUrl.activityReplyUrl.equals(c_codeBean.getUrl())) {
            ToastUtil.toast("回复失败");
            popInput.dismissWindow();
        } else {

            ToastUtil.toast("删除失败");
        }
    }
}
