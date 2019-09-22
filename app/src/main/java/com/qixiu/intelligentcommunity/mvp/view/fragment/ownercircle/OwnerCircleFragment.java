package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.enums.OwnerCircleEnums;
import com.qixiu.intelligentcommunity.listener.ItemClickListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.EditDynamicActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.adapte.OwnerCircleAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.adapte.OwnerCircleMyAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.CommentConfig;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.OwnerCicleMyBean;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.OwnerCircleBean;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.ProgressStyle;
import com.qixiu.intelligentcommunity.mvp.view.widget.xrecyclerview.XRecyclerView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.widget.FootprintCommentDialog;
import com.qixiu.intelligentcommunity.widget.MyItemAnimator;
import com.qixiu.intelligentcommunity.widget.SegmentedGroup;
import com.qixiu.intelligentcommunity.widget.recyclerview.RecyclerHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.qixiu.intelligentcommunity.enums.OwnerCircleEnums.OWNER_MY;
import static com.qixiu.intelligentcommunity.enums.OwnerCircleEnums.OWNER_WHOLE;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class OwnerCircleFragment extends BaseFragment {

    @BindView(R.id.rl_fp_content)
    XRecyclerView mRlContent;
    OwnerCircleAdapter mAdapter;
    private LinearLayout mLlOwner;
    private LinearLayout mLlQuestion;
    private LinearLayout mLlActivity;
    private SegmentedGroup mFootprintRG;
    private OwnerCircleEnums tabType;
    OwnerCircleMyAdapter mMyAdapter;
    private int mWholePageNo = 1;//业主全部列表分页加载的页码
    private int mMyPageNo = 1;//业主我的列表分页加载的页码
    private boolean isRefreshing = true;
    private List<OwnerCircleBean.OOwnerCircleBean.OwnerCircleEntity> mOwnerCirclelist;
    private List<OwnerCicleMyBean.OwnerCicleMyDataBean> mOwnerCircleMylist;
    public static OwnerCircleFragment instance;

    @Override
    protected void onInitData() {
        tabType = OWNER_WHOLE;
        mRlContent.setLayoutManager(new LinearLayoutManager(this.getContext()));
        View headView = LayoutInflater.from(mContext).inflate(R.layout.owner_circle_list_header, null);
        initHeadView(headView);
        initRecyclerView(headView);
        addListener();
        instance = this;
        mRlContent.setRefreshing(false);

    }

    private void addListener() {
        mFootprintRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_owner_whole:
                        tabType = OWNER_WHOLE;
                        mOwnerCircleMylist.clear();
                        break;
                    case R.id.rb_owner_my:
                        tabType = OWNER_MY;
                        mOwnerCirclelist.clear();
//                        mOwnerCircleMylist.clear();
                        doGetMyData(0);
                        break;

                }
                mRlContent.setRefreshing(true);
                initAdapter(tabType);
            }
        });
    }

    private void initRecyclerView(View headView) {
        mRlContent.addHeaderView(headView);
        mRlContent.setItemAnimator(new MyItemAnimator());
        mRlContent.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRlContent.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRlContent.setArrowImageView(R.mipmap.ic_font_downgrey);
        mOwnerCirclelist = new ArrayList<>();
        mOwnerCircleMylist = new ArrayList<>();
        initAdapter(tabType);

        mRlContent.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//下拉刷新
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        switch (tabType) {
                            case OWNER_WHOLE://全部
                                mWholePageNo = 1;
                                isRefreshing = true;
                                doGetAllOwenrWhole(1);//全部接口
                                break;
                            case OWNER_MY://我的
                                mMyPageNo = 1;
                                isRefreshing = true;
                                doGetMyData(1);//我的接口
                                break;
                        }
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {//上拉加载
                switch (tabType) {
                    case OWNER_WHOLE:
                        new android.os.Handler().postDelayed(new Runnable() {
                            public void run() {
                                isRefreshing = false;
                                if (!isRefreshing) {
                                    mWholePageNo++;
                                }
                                doGetAllOwenrWhole(1);
                            }
                        }, 1000);
                        break;
                    case OWNER_MY:
                        new android.os.Handler().postDelayed(new Runnable() {
                            public void run() {
                                isRefreshing = false;
                                if (!isRefreshing) {
                                    mMyPageNo++;
                                }
                                doGetAllOwenrWhole(1);
                            }
                        }, 1000);
                        break;
                }

            }
        });

    }

    private void initAdapter(OwnerCircleEnums tabType) {
        switch (this.tabType) {
            case OWNER_WHOLE:
                mAdapter = new OwnerCircleAdapter(mContext, mOwnerCirclelist);
                mRlContent.setAdapter(mAdapter);
                setFootprintOnclick();
                mAdapter.notifyDataSetChanged();
                break;
            case OWNER_MY:
                mMyAdapter = new OwnerCircleMyAdapter(mContext, mOwnerCircleMylist);
                mRlContent.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 跳转到详情
     */
    private void setFootprintOnclick() {
        mAdapter.setListener(new ItemClickListener() {
            @Override
            public void OnItemClickListener(RecyclerHolder view, int position) {
            }

            @Override
            public void OnItemLongClickListener(RecyclerHolder view, int position) {

            }
        });
    }

    /**
     * 获取全部接口
     *
     * @param frame
     */

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private int total = 1;//总页码
    private boolean mHasLoadedOnce;

    private void doGetAllOwenrWhole(final int frame) {
        if (frame == 0) {
        }
        OkHttpUtils.post().url(ConstantUrl.ownerCircleWholeListUrl)
                .addParams("uid", "2")//用户id
                .addParams("pageNo", "" + mWholePageNo)//当前页码数
                .addParams("pageSize", "10").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.showToast(mContext, "" + 1);
            }

            @Override
            public void onResponse(String s, int i) {
                OwnerCircleBean ownerCircleBean = GetGson.init().fromJson(s, OwnerCircleBean.class);
                if (ownerCircleBean.getO().getList() == null) {
                    ToastUtil.showToast(mContext, "没有查到数据");
                } else {
                    mOwnerCirclelist = ownerCircleBean.getO().getList();
                    mHasLoadedOnce = true;
                    if (mOwnerCirclelist != null && mOwnerCirclelist.size() > 0) {
                        mAdapter = new OwnerCircleAdapter(mContext, mOwnerCirclelist);
                        mRlContent.setAdapter(mAdapter);
                        setFootprintOnclick();
                        mAdapter.notifyDataSetChanged();
                        if (total == mOwnerCirclelist.size()) { //显示没有更多数据了
                            mRlContent.setNoMore(true);
                            mAdapter.notifyDataSetChanged();
                        } else {  //正常加载
                            mAdapter.notifyDataSetChanged();
                            mRlContent.refreshComplete();
                        }
                    } else {
                        mRlContent.setNoMore(true);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }


    /**
     * 我的接口
     *
     * @param frame
     */

    private void doGetMyData(final int frame) {
        if (frame == 0) {
            /**
             * 加载进度圈
             * 后续加入
             */
        }
        OkHttpUtils.post().url(ConstantUrl.ownerCircleMyListUrl)
                .addParams("uid", "2")//用户id
                .addParams("pageNo", "" + mMyPageNo)//当前页码数
                .addParams("pageSize", "10").build().execute(new StringCallback() {//每页个数
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(String s, int i) {
                OwnerCicleMyBean ownerCicleMyBean = GetGson.init().fromJson(s, OwnerCicleMyBean.class);
                mOwnerCircleMylist = ownerCicleMyBean.getO().getList().get(0).getData();
                if (ownerCicleMyBean.getO().getList().get(0).getData() == null) {
                    ToastUtil.showToast(mContext, "没有查到数据");
                } else {
                    mOwnerCircleMylist = ownerCicleMyBean.getO().getList().get(0).getData();
                    mHasLoadedOnce = true;
                    mOwnerCircleMylist = ownerCicleMyBean.getO().getList().get(0).getData();
                    if (mOwnerCircleMylist != null && mOwnerCircleMylist.size() > 0) {
                        mMyAdapter = new OwnerCircleMyAdapter(mContext, mOwnerCircleMylist);
                        mRlContent.setAdapter(mMyAdapter);
                        mMyAdapter.notifyDataSetChanged();
                        if (total == mOwnerCircleMylist.size()) { //显示没有更多数据了
                            mRlContent.setNoMore(true);
                            mMyAdapter.notifyDataSetChanged();
                        } else {  //正常加载
                            mMyAdapter.notifyDataSetChanged();
                            mRlContent.refreshComplete();
                        }
                    } else {
                        mRlContent.setNoMore(true);
                        mMyAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    /**
     * 初始化头部布局
     *
     * @param headView
     */
    private void initHeadView(View headView) {
        //初始化布局，活动和问答区
        mLlOwner = (LinearLayout) headView.findViewById(R.id.ll_owner);
        mLlQuestion = (LinearLayout) headView.findViewById(R.id.ll_question);
        mLlActivity = (LinearLayout) headView.findViewById(R.id.ll_activity);
        mFootprintRG = (SegmentedGroup) headView.findViewById(R.id.rg_footprint);
        mLlOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDynamicActivity.start(mContext);
            }
        });

    }


    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main_owbercicle;
    }


    /**
     * 动态删除
     */
    public void deleteFootprint(final int Position, int id) {
        OkHttpUtils.post().url(ConstantUrl.ownerCircleDeleteUrl)
                .addParams("uid", "2")//用户id
                .addParams("id", "" + id).build().execute(new StringCallback() {//业主圈ID
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.showToast(mContext, "删除失败");
            }

            @Override
            public void onResponse(String s, int i) {
                mOwnerCirclelist.remove(Position);
                mAdapter.notifyDataSetChanged();
                ToastUtil.showToast(mContext, "删除成功");
            }
        });
    }

    /**
     * 显示评论对话框
     *
     * @param view          点击的view
     * @param commentConfig 评论配置
     */
    public void showCommentDialog(final View view, final CommentConfig commentConfig) {
        final FootprintCommentDialog memoryCommentDialog = new FootprintCommentDialog(getActivity());
        memoryCommentDialog.show();
        if (commentConfig.commentType == CommentConfig.Type.REPLY)
            memoryCommentDialog.setEditTextHint(
                    String.format(getString(R.string.footprint_report), commentConfig.replyNickName));
        final int[] coord = new int[2];
        if (mRlContent != null) {
            view.getLocationOnScreen(coord);
        }
        memoryCommentDialog.setMemoryCommentListener(new FootprintCommentDialog.FootprintCommentListener() {

            public void comment(String comment) {
                commentConfig.content = comment;
                addComment(commentConfig);
            }

            public void onShow(int[] inputViewCoordinatesOnScreen) {
                if (mRlContent != null) {
                    // 点击某条评论则这条评论刚好在输入框上面，点击评论按钮则输入框刚好挡住按钮
                    int span = view.getId() == R.id.iv_send ? 0 : view.getHeight();
                    //    mRlContent.smoothScrollBy(coord[1] + span - inputViewCoordinatesOnScreen[1], 1000);
                }
            }
        });
    }

    /**
     * 评论接口
     *
     * @param commentConfig
     */
    private void addComment(CommentConfig commentConfig) {
    }


}
