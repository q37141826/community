package com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event.OwenEventDetailBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenPicBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.eventdetail.ApplyActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenPicAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.eventdetail.OwenEventDetailAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.itemdecoration.LinearSpacesItemDecoration;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.base64.DateFormatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.ConstantUrl.activityDelUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.activityInfoUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.activityReplyUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.activitySendComent;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.eventReadUrl;


/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class OwnerEventDetailActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener, OKHttpUIUpdataListener, OnRecyclerItemListener, RecyclerBaseAdapter.OnHolderNotifyRefreshListener {

    private String mAid;
    private XRecyclerView mRv_event_detail;
    private OwenEventDetailAdapter mOwenEventDetailAdapter;
    private SwipeRefreshLayout mSrl_event_detail;
    private OKHttpRequestModel mOkHttpRequestModel;
    private TextView mTv_event_detail_title;
    private TextView mTv_event_detail_title_date;
    private TextView mTv_event_detail_date;
    private TextView mTv_event_detail_address;
    private TextView mTv_event_detail_linkman;
    private TextView mTv_event_detail_alreadycount;
    private TextView mTv_event_detail_way;
    private TextView mTv_event_detail_introduce;
    private ImageView mIv_event_detail_picture;
    private TextView mTv_eventdetail_comment;
    private MyPopForInput popInput;
    private OwenEventDetailBean.OwenEventDetailInfo mOwenEventDetailInfo;
    private ZProgressHUD mZProgressHUD;
    private OwenEventDetailBean.EOwenEventDetailInfo mEOwenEventDetailInfo;
    public static int MAIN_COMMENT_WAY = 100;
    public static int ITEM_COMMENT_WAY = 110;
    private int commentWay;
    private RecyclerView mRv_event_detail_imgs;
    private OwenOwenPicAdapter adapter;

    @Override
    protected void onOtherClick(View view) {
        switch (view.getId()) {

            case R.id.tv_eventdetail_comment:
                commentWay = MAIN_COMMENT_WAY;
                popInput = new MyPopForInput(mContext);
                popInput.setHintText("评论....");
                popInput.setSendListener(this);
                break;
            default:
                TextView buttonView = (TextView) popInput.getButtonView();
                if (getString(R.string.cancels).equals(buttonView.getText())) {
                    popInput.dismissWindow();
                    ArshowContextUtil.hideSoftKeybord(buttonView);
                    return;
                }

                if (TextUtils.isEmpty(popInput.getText())) {
                    ToastUtil.toast("请输入内容");
                    return;
                }
                mZProgressHUD.setMessage("发送中..");
                mZProgressHUD.show();
                switch (commentWay) {

                    case 100:
                        if (mOwenEventDetailInfo != null) {
                            sendComment();
                        }

                        break;
                    case 110:
                        if (mEOwenEventDetailInfo != null) {
                            send(mEOwenEventDetailInfo.getId() + StringConstants.EMPTY_STRING, mEOwenEventDetailInfo.getUid() + StringConstants.EMPTY_STRING);
                        }

                        break;
                }


                if (popInput != null) {
                    popInput.dismissWindow();
                }
                break;


        }
    }

    private void sendComment() {
        mZProgressHUD.setMessage("发送中");
        mZProgressHUD.show();
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(mOwenEventDetailInfo.getId() + StringConstants.EMPTY_STRING)) {
            stringMap.put("pid", mOwenEventDetailInfo.getId() + StringConstants.EMPTY_STRING);
        }
        if (!TextUtils.isEmpty(uid)) {
            stringMap.put(IntentDataKeyConstant.UID_KEY, uid);
        }
        if (!TextUtils.isEmpty(mOwenEventDetailInfo.getId() + StringConstants.EMPTY_STRING)) {
            stringMap.put("ob_uid", mOwenEventDetailInfo.getId() + StringConstants.EMPTY_STRING);
        }
        if (!TextUtils.isEmpty(popInput.getText().toString())) {
            stringMap.put(StringConstants.CONTENT_STRING, popInput.getText().toString().trim());
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setUrl(activitySendComent);
        mOkHttpRequestModel.okhHttpPost(activitySendComent, stringMap, baseBean, false);
    }

    @Override
    protected void onTitleRightClick() {

        if (mOwenEventDetailInfo == null) {
            ToastUtil.toast(R.string.not_netwroking);
            return;
        }

        try {

            int i = DateFormatUtils.compareCurentDatePreciseToDay("yyyy.MM.dd", mOwenEventDetailInfo.getEtime());
            if (i < 0) {
                ToastUtil.toast("活动已结束");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(this, ApplyActivity.class);
        intent.putExtra(IntentDataKeyConstant.AID, mAid);
        startActivityForResult(intent, IntentRequestCodeConstant.TOEVENTAPPLY_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (IntentRequestCodeConstant.TOEVENTAPPLY_REQUESTCODE == requestCode && resultCode == IntentRequestCodeConstant.TOEVENTAPPLY_RESULTCODE) {
            requestData();
        }
    }

    @Override
    protected void onInitView() {
        super.onInitView();

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("活动详情");
        mTv_more.setText("我要报名");
        ViewGroup parent = (ViewGroup) mTv_more.getParent();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.rightMargin = ArshowContextUtil.dp2px(13);
        parent.setLayoutParams(layoutParams);
        mSrl_event_detail = (SwipeRefreshLayout) findViewById(R.id.srl_event_detail);
        mSrl_event_detail.setOnRefreshListener(this);
        mZProgressHUD = new ZProgressHUD(this);
        initRv();

    }

    private void initRv() {
        View headView = View.inflate(this, R.layout.layout_event_detail_head, null);
        mTv_event_detail_title = (TextView) headView.findViewById(R.id.tv_event_detail_title);
        mTv_event_detail_title_date = (TextView) headView.findViewById(R.id.tv_event_detail_title_date);
        mTv_event_detail_date = (TextView) headView.findViewById(R.id.tv_event_detail_date);
        mTv_event_detail_address = (TextView) headView.findViewById(R.id.tv_event_detail_address);
        mTv_event_detail_linkman = (TextView) headView.findViewById(R.id.tv_event_detail_linkman);
        mTv_event_detail_alreadycount = (TextView) headView.findViewById(R.id.tv_event_detail_alreadycount);
        mTv_event_detail_way = (TextView) headView.findViewById(R.id.tv_event_detail_way);
        mTv_event_detail_introduce = (TextView) headView.findViewById(R.id.tv_event_detail_introduce);
        mTv_eventdetail_comment = (TextView) headView.findViewById(R.id.tv_eventdetail_comment);
        initimags(headView);
        mTv_eventdetail_comment.setOnClickListener(this);
        mRv_event_detail = (XRecyclerView) findViewById(R.id.rv_event_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv_event_detail.addItemDecoration(new LinearSpacesItemDecoration(LinearLayoutManager.VERTICAL, ArshowContextUtil.dp2px(10)));
        mRv_event_detail.setLayoutManager(linearLayoutManager);
        mRv_event_detail.setPullRefreshEnabled(false);
        mRv_event_detail.addHeaderView(headView);
    }

    private void initimags(View headView) {
        mRv_event_detail_imgs = (RecyclerView) headView.findViewById(R.id.rv_event_detail_imgs);


        //装填图片
        adapter = new OwenOwenPicAdapter();
        mRv_event_detail_imgs.setAdapter(adapter);
    }

    @Override
    protected void onBackClick() {
        finish();
    }

    @Override
    protected void onInitData() {
        mZProgressHUD.show();
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        mAid = getIntent().getStringExtra(IntentDataKeyConstant.AID);
        mOwenEventDetailAdapter = new OwenEventDetailAdapter();
        mOwenEventDetailAdapter.setOnHolderNotifyRefreshListener(this);
        mRv_event_detail.setLoadingMoreEnabled(false);
        mRv_event_detail.setAdapter(mOwenEventDetailAdapter);
        mOwenEventDetailAdapter.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(mAid)) {
            stringMap.put(IntentDataKeyConstant.ID, mAid);
        }
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        if (!TextUtils.isEmpty(uid)) {
            stringMap.put(IntentDataKeyConstant.UID_KEY, uid);
        }
        OwenEventDetailBean owenEventDetailBean = new OwenEventDetailBean();
        owenEventDetailBean.setUrl(activityInfoUrl);
        mOkHttpRequestModel.okhHttpPost(activityInfoUrl, stringMap, owenEventDetailBean, false);
        mOkHttpRequestModel.okhHttpPost(eventReadUrl, stringMap, new BaseBean(), false);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_ownerevent_detail;
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (mSrl_event_detail.isRefreshing()) {
            mSrl_event_detail.setRefreshing(false);
        }
        if (data instanceof OwenEventDetailBean) {
            OwenEventDetailBean owenEventDetailBean = (OwenEventDetailBean) data;
            mOwenEventDetailInfo = owenEventDetailBean.getO();
            disposeHeadData(mOwenEventDetailInfo);
            disposeRvdata(owenEventDetailBean.getE());
            disposeImagesData(mOwenEventDetailInfo);
        } else {
            BaseBean baseBean = (BaseBean) data;
            if (activityDelUrl.equals(baseBean.getUrl())) {
                List<OwenEventDetailBean.EOwenEventDetailInfo> datas = mOwenEventDetailAdapter.getDatas();
                datas.remove(mEOwenEventDetailInfo);
                mOwenEventDetailAdapter.notifyDataSetChanged();
            } else {
                requestData();

            }

        }
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }
    }

    private void disposeImagesData(final OwenEventDetailBean.OwenEventDetailInfo mData) {
        if (mData.getImgs().size() != 0) {
            List<OwenOwenPicBean> list = new ArrayList<>();
            int type = 1;//是哪种图片布局，一张图平摊，四张图是2x2，其他的都是九宫格
            if (mData.getImgs().size() == 4) {
                type = 2;
                mRv_event_detail_imgs.setLayoutManager(new GridLayoutManager(mContext, 2));
            } else if (mData.getImgs().size() == 1) {
                type = 1;
                mRv_event_detail_imgs.setLayoutManager(new GridLayoutManager(mContext, 1));
            } else {
                type = 3;
                mRv_event_detail_imgs.setLayoutManager(new GridLayoutManager(mContext, 3));
            }
            for (int i = 0; i < mData.getImgs().size(); i++) {
                OwenOwenPicBean bean = new OwenOwenPicBean();
                bean.setImagestring(mData.getImgs().get(i));
                bean.setType(type);
                list.add(bean);
            }
            adapter.refreshData(list);
            adapter.setOnItemClickListener(new OnRecyclerItemListener<OwenOwenPicBean>() {
                @Override
                public void onItemClick(View v, OwenOwenPicBean data) {
                    ArrayList<String> datas = new ArrayList<String>();
                    for (int i = 0; i < mData.getImgs().size(); i++) {
                        datas.add(ConstantUrl.hostImageurl + mData.getImgs().get(i));
                    }
                    PhotoPreview.builder().setShowDeleteButton(false).setPhotos(datas).setCurrentItem(mRv_event_detail_imgs.getChildLayoutPosition(v)).start(
                            OwnerEventDetailActivity.this);
                }


            });
        }
    }

    private void disposeRvdata(List<OwenEventDetailBean.EOwenEventDetailInfo> eOwenEventDetailInfos) {
        mOwenEventDetailAdapter.refreshData(eOwenEventDetailInfos);

    }

    private void disposeHeadData(OwenEventDetailBean.OwenEventDetailInfo owenEventDetailInfo) {
        mTv_event_detail_title.setText(owenEventDetailInfo.getTitle());
        mTv_event_detail_title_date.setText(owenEventDetailInfo.getStime());
        mTv_event_detail_date.setText(getString(R.string.start_time) + owenEventDetailInfo.getStime() + "—" + owenEventDetailInfo.getEtime());
        mTv_event_detail_address.setText(getString(R.string.address) + owenEventDetailInfo.getAddress());
        mTv_event_detail_linkman.setText(getString(R.string.linkman) + owenEventDetailInfo.getUname());
        mTv_event_detail_alreadycount.setText(getString(R.string.already_join_count) + owenEventDetailInfo.getNum());
        mTv_event_detail_way.setText(getString(R.string.contact_way) + owenEventDetailInfo.getPhone());
        mTv_event_detail_introduce.setText(getString(R.string.introduce) + owenEventDetailInfo.getContent());
    }


    @Override
    public void onError(Call call, Exception e, int i) {
        if (mSrl_event_detail.isRefreshing()) {
            mSrl_event_detail.setRefreshing(false);
        }
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismissWithFailure("网络异常");
        }
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (mSrl_event_detail.isRefreshing()) {
            mSrl_event_detail.setRefreshing(false);
        }

        if (mZProgressHUD.isShowing()) {
            if (activityDelUrl.equals(c_codeBean.getUrl())) {
                mZProgressHUD.dismissWithFailure("删除失败");
            } else {
                mZProgressHUD.dismissWithFailure(activitySendComent.equals(c_codeBean.getUrl()) ? "发送失败" : "加载失败");
            }

        }
    }

    @Override
    public void onItemClick(View v, Object data) {
        commentWay = ITEM_COMMENT_WAY;
        if (data instanceof OwenEventDetailBean.EOwenEventDetailInfo) {
            mEOwenEventDetailInfo = (OwenEventDetailBean.EOwenEventDetailInfo) data;
            if ((mEOwenEventDetailInfo.getUid() + "").equals(Preference.get(ConstantString.USERID, ""))) {
                setDialog(mEOwenEventDetailInfo.getId() + "", "是否删除该评论");
            } else {
                popInput = new MyPopForInput(mContext);
                popInput.setHintText("回复" + mEOwenEventDetailInfo.getUids().getNickname() + "...");
                popInput.setSendListener(this);
            }
        }

    }

    private void send(String pid, String oId) {
        mZProgressHUD.show();
        mZProgressHUD.setMessage("发送中");
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
            stringMap.put(StringConstants.CONTENT_STRING, popInput.getText().toString());
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setUrl(activityReplyUrl);
        mOkHttpRequestModel.okhHttpPost(activityReplyUrl, stringMap, baseBean, false);
    }

    private void setDialog(final String pid, final String notice) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除评论
                startdelete(pid);
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

    private void startdelete(String pid) {
//        mZProgressHUD.setMessage("删除中...");
//        mZProgressHUD.show();
        Map<String, String> stringMap = new HashMap<>();
        if (!TextUtils.isEmpty(pid)) {
            stringMap.put(IntentDataKeyConstant.ID, pid);
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setUrl(activityDelUrl);
        mOkHttpRequestModel.okhHttpPost(activityDelUrl, stringMap, baseBean, false);
    }

    @Override
    public void onHolderNotifyRefresh(Object data) {
        requestData();
    }
}
