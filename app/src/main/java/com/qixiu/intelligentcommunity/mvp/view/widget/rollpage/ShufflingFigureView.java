package com.qixiu.intelligentcommunity.mvp.view.widget.rollpage;


import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 此类暂时替代无限翻页功能，功能有待后期增加  新增可自行设置点的样式方法 setpointStyle()
 *
 * @author gyh
 */
public final class ShufflingFigureView extends RelativeLayout implements OnPageChangeListener, OnTouchListener, OnGestureListener {

    public LinearLayout ll_points;
    private TextView tv_sf;
    private ViewPager vp_sf;
    private String[] title;
    private List<ImageView> ivs;
    private boolean isRoll;
    private final long DEFAULT_ROLLTIME = 1500;
    private long rollTime;
    public boolean isUpScrool = true;
    public int downY;
    public int downX;
    public int moveY;
    public int moveX;
    //间距
    private int margin;
    private int mPointWidth;
    private int mSelectPointWidth;
    private int mSelectPointHeight;
    private int defaultPointWidth;
    private int defaultPointHeight;
    private int mDefaultPointHeight;
    private int selectPointWidth;
    private int selectPointHeight;
    private int defaultResourceId;
    private int selectResourceId;

    private int mSelectResourceId;
    private int mDefaultResourceId;

    public ShufflingFigureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.layout_rl_sf, this);
        vp_sf = (ViewPager) findViewById(R.id.vp_sf);
        ll_points = (LinearLayout) findViewById(R.id.ll_points);
        rollTime = DEFAULT_ROLLTIME;
        myTask = new MyTask(this);
        // tv_sf = (TextView) findViewById(R.id.tv_sf);

    }

    public void setCurrentItem(int postion) {
        vp_sf.setCurrentItem(postion + 1);
    }

    public ShufflingFigureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShufflingFigureView(Context context) {
        this(context, null);
    }

    public void initData(String[] imagesPath) {
        margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                getResources().getDisplayMetrics());
        //该集合需要在这里实例化，避免重复调用initData导致图片重复添加
        if (ivs == null) {
            ivs = new ArrayList<ImageView>();
        } else {
            if (ivs.size() > 0) {
                ivs.clear();
            }

        }

        if (ll_points.getChildCount() > 0) {
            ll_points.removeAllViews();
        }
        // 将图片的总数量+2,将0的index插入最后一张图片，将新得到的总量-1的index插入第一张图片，以这种方式实现无限轮播
        if (defaultPointWidth > 0) {
            mPointWidth = defaultPointWidth;
        } else {
            mPointWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    getResources().getDisplayMetrics());
        }
        if (defaultPointHeight > 0) {
            mDefaultPointHeight = defaultPointHeight;
        } else {
            mDefaultPointHeight = mPointWidth;
        }

        if (selectPointWidth > 0) {
            mSelectPointWidth = selectPointWidth;
        } else {
            //选中的点的宽度
            mSelectPointWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42,
                    getResources().getDisplayMetrics());
        }
        if (selectPointHeight > 0) {
            mSelectPointHeight = selectPointWidth;
        } else {
            //选中的点的高度
            mSelectPointHeight = mPointWidth;
        }


        for (int i = 0; i < imagesPath.length + 2; i++) {

            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // 如果位置是length加1说明是最后一张图片，也就是要显示第一张图片的样子
            if (i == 0) {
                Glide.with(getContext()).load(imagesPath[imagesPath.length - 1]).into(iv);


                //测试的图片
                // iv.setImageResource(imagesPath[imagesPath.length - 1]);
            } else if (i == imagesPath.length + 1) {
                Glide.with(getContext()).load(imagesPath[0]).into(iv);


                //测试的图片
                //  iv.setImageResource(imagesPath[0]);
            } else {
                Glide.with(getContext()).load(imagesPath[i - 1]).into(iv);


                //测试的图片
                //  iv.setImageResource(imagesPath[i - 1]);
                // 初始化点的数量，点的数量根据给定资源的图片数量来决定

                if (defaultResourceId > 0) {
                    mDefaultResourceId = defaultResourceId;
                } else {
                    mDefaultResourceId = R.drawable.shape_dot_circle_empty;
                }
                View point = new View(getContext());
                point.setBackgroundResource(mDefaultResourceId);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPointWidth,
                        mDefaultPointHeight);
                params.leftMargin = margin;
                point.setLayoutParams(params);
                //避免重复调用initData导致点数重复添加
                if (ll_points.getChildCount() < imagesPath.length)
                    ll_points.addView(point);
            }
            ivs.add(iv);

        }
        View childAt = ll_points.getChildAt(0);
        if (childAt != null) {
            if (selectResourceId > 0) {
                mSelectResourceId = selectResourceId;
            } else {
                mSelectResourceId = R.drawable.shape_adv_point;
            }
            childAt.setBackgroundResource(mSelectResourceId);
            childAt.setLayoutParams(getPointLayoutParms(mSelectPointWidth, mSelectPointHeight));
        }
        // tv_sf.setText(title[0]);

        initViewPage(vp_sf, ivs);
    }

    public void setpointStyle(int defaultPointWidth, int defaultPointHeight, int selectPointWidth, int selectPointHeight, int defaultResourceId, int selectResourceId) {
        this.defaultPointWidth = defaultPointWidth;
        this.defaultPointHeight = defaultPointHeight;
        this.selectPointWidth = selectPointWidth;
        this.selectPointHeight = selectPointHeight;
        this.defaultResourceId = defaultResourceId;
        this.selectResourceId = selectResourceId;

    }

    public ViewGroup.LayoutParams getPointLayoutParms(int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                height);
        params.leftMargin = margin;
        return params;
    }

    @SuppressWarnings("deprecation")
    private void initViewPage(ViewGroup group, List<ImageView> ivs) {
        ViewPager vp = (ViewPager) group;
        vp.setAdapter(new ShufflingFigureAdapter(ivs));
        // 跳转到角标为1的图片
        vp.setCurrentItem(1);
        vp.setOnTouchListener(this);
        vp.setOnPageChangeListener(this);

    }


    public void setRollTime(long roolTime) {
        if (roolTime > DEFAULT_ROLLTIME)
            this.rollTime = roolTime;
        else
            this.rollTime = DEFAULT_ROLLTIME;
    }

    /**
     * 设置点击轮播图抬起时是否需要再次自动滚动（适用于不需要自动滚动的轮播图） 默认是再次滚动的
     *
     * @param isUpScrool
     */
    public void setUpScroll(boolean isUpScrool) {
        this.isUpScrool = isUpScrool;
    }

    public void scrollToNext() {
        vp_sf.setCurrentItem(vp_sf.getCurrentItem() + 1);

    }

    public void scrollToLast() {
        vp_sf.setCurrentItem(vp_sf.getCurrentItem() - 1);

    }

    /**
     * 设置是否是自动滚动
     *
     * @param isRoll
     */
    public void setIsRoll(Boolean isRoll) {
        this.isRoll = isRoll;
        hand.removeCallbacks(myTask);
        hand.postDelayed(myTask, rollTime);

    }

    /**
     * 该方法需提前做好初始化的操作才可获取
     *
     * @return int
     */
    public int getCurrentItem() {
        if (ivs != null) {

            int currentItem = vp_sf.getCurrentItem();
            // 这里的currentItem=0,和currentItem=ivs.size()-1是我们后加进去的额外图片，但是当
            // 我们通过点击这个图片的位置对应的获取数据时，就会出现错乱，导致bug等，避免这种情况发生，做了以下的处理
            if (currentItem == 0) {
                return ivs.size() - 3;
            } else if (currentItem == ivs.size() - 1) {
                return 0;
            }
            return currentItem - 1;
        }
        return -1;
    }

    private static class MyTask implements Runnable {

        public final WeakReference<ShufflingFigureView> wrShufflingFigureView;

        public MyTask(ShufflingFigureView shufflingFigureView) {
            wrShufflingFigureView = new WeakReference(shufflingFigureView);


        }

        @Override
        public void run() {

            ShufflingFigureView shufflingFigureView = wrShufflingFigureView.get();
            if (shufflingFigureView != null) {
                shufflingFigureView.hand.removeCallbacks(shufflingFigureView.myTask);
                if (shufflingFigureView.isRoll) {
                    shufflingFigureView.hand.postDelayed(shufflingFigureView.myTask,
                            shufflingFigureView.rollTime);
                    shufflingFigureView.vp_sf.setCurrentItem(
                            shufflingFigureView.vp_sf.getCurrentItem() + 1);
                }
            }


        }

    }

    private Handler hand = new Handler();

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == OnScrollListener.SCROLL_STATE_IDLE) {
            if (pos == ivs.size() - 2 || pos == 1) {
                vp_sf.setCurrentItem(pos, false);

            }
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    private int pos = 0;
    private int currentPosition = 1;

    @Override
    public void onPageSelected(int position) {
        this.pos = position;
        if (pos == ivs.size() - 1) {
            pos = 1;
        } else if (pos == 0) {
            pos = ivs.size() - 2;
        }
        if (pos != currentPosition) {
            // tv_sf.setText(title[pos - 1]);

            ll_points.getChildAt(currentPosition - 1).setBackgroundResource(
                    mDefaultResourceId);
            ll_points.getChildAt(pos - 1).setBackgroundResource(mSelectResourceId);


            ll_points.getChildAt(currentPosition - 1).setLayoutParams(getPointLayoutParms(mPointWidth, mDefaultPointHeight));
            ll_points.getChildAt(pos - 1).setLayoutParams(getPointLayoutParms(mSelectPointWidth, mSelectPointHeight));
        }
        currentPosition = pos;
      /*  hand.removeCallbacks(myTask);
        hand.postDelayed(myTask, rollTime);*/

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 手势识别器时刻观察着ontouch事件
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Toast.makeText(getContext(), "没有触摸到", 0).show();
                isRoll = false;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isUpScrool) {
                    isRoll = true;
                    hand.removeCallbacks(myTask);
                    hand.postDelayed(myTask, rollTime);
                }
                break;
            default:
                break;
        }
        return false;
    }

    private MyTask myTask;
    /**
     * 手势识别器，对触摸事件实时监听，处理与外界事件抢夺问题
     */
    private GestureDetector detector = new GestureDetector(this);

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        ShufflingFigureView.this.performClick();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //        if (Math.abs(distanceX) > Math.abs(distanceY))
        //        {
        // 请求父亲不要拦截我的滚动事件,此事件是逐级向父控件传递的
        ShufflingFigureView.this.requestDisallowInterceptTouchEvent(true);
        //        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
