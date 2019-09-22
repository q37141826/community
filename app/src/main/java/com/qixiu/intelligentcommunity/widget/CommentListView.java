package com.qixiu.intelligentcommunity.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.OwnerCircleArticlesCommentBean;
import com.qixiu.intelligentcommunity.utlis.AndroidUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Author XieXianyong
 * Date: 2017.06.28
 * Description: 评论layout
 */

public class CommentListView extends LinearLayout {
    private int itemColor;
    private int itemSelectorColor;
    private int itemTextSize;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<OwnerCircleArticlesCommentBean> mDatas;
    private LayoutInflater layoutInflater;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setDatas(List<OwnerCircleArticlesCommentBean> datas) {
        if (datas == null) {
            datas = new ArrayList<OwnerCircleArticlesCommentBean>();
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<OwnerCircleArticlesCommentBean> getDatas() {
        return mDatas;
    }

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CommentListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.CommentListView_item_color, getResources().getColor(R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(R.styleable.CommentListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));
            itemTextSize = typedArray.getDimensionPixelSize(R.styleable.CommentListView_item_text_size, AndroidUtils.sp2px(getContext(), 12));

        } finally {
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged() {

        removeAllViews();
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int position) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.itme_owner_circle_comment, null, false);

        TimeLineContentTextView commentTv = (TimeLineContentTextView) convertView.findViewById(R.id.tv_comment);
        commentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);

        final OwnerCircleArticlesCommentBean bean = mDatas.get(position);
        String name = bean.getCommentNice();
        String id = bean.getCommentId();
        String toReplyName = "";
        if (bean.getReplyNice() != null) {
            toReplyName = bean.getReplyNice();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, bean.getArticleUserId()));

        if (!TextUtils.isEmpty(toReplyName)) {

            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName, bean.getReplyId()));
        }
        builder.append(": ");
        //转换表情字符
        String contentBodyStr = bean.getContent();
        builder.append(contentBodyStr);
        commentTv.setContent(builder);

        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            }
        });
        commentTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
                return false;
            }
        });

        return convertView;
    }


    /**
     * 评论人的名称的点击事件
     *
     * @param textStr
     * @param id
     * @return
     */
    private SpannableString setClickableSpan(final String textStr, final String id) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new OwerCircleContentClickableSpan(getContext()) {
                                    @Override
                                    public void onClick(View widget) {
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public static interface OnItemLongClickListener {
        public void onItemLongClick(int position);
    }


}
