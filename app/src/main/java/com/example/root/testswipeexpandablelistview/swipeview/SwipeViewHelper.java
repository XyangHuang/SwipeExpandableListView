package com.example.root.testswipeexpandablelistview.swipeview;

import android.view.MotionEvent;
import android.view.View;

import com.example.root.testswipeexpandablelistview.R;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class SwipeViewHelper implements BaseSwipeView
{
    // region Variables
    private final static int ANIMATION_INTERVAL = 2;   // 动画刷新间隔5ms
    private final static int SWIPE_DISTANCE = 20;      // 拉开多少px算打开

    private int animationDuration = 200; // 动画时间默认为200ms

    private int downX;            // 按下点的x值
    private int downY;            // 按下点的y值
    private int optionsLayoutWidth;   // 操作布局的宽度

    public boolean isOptionsLayoutShown;    // 操作布局是否正在显示

    public View pointSwipeChild;    // 当前处理的child item
    public View surfaceLayout;    // 当前处理的child item

    public int scrollDistance;    // 当前处理的child item的LayoutParams

    public boolean isSwiping;     // 是否正在滑动(用来判断是否处理child item的click事件)
    public boolean tempIsOptionsLayoutShown; // 用来判断是否处理child item的touch(move/up)事件

    // endregion

    // region Constructor

    // endregion

    // region Methods

    // 处理action_down事件
    public void performActionDown(MotionEvent ev, View view)
    {
        downX = (int) ev.getX();
        downY = (int) ev.getY();

        // 获取当前点的item
        pointSwipeChild = view;

        if (pointSwipeChild != null)
        {
            surfaceLayout = ButterKnife.findById(pointSwipeChild, R.id.layout_surface);
            View optionsLayout = ButterKnife.findById(pointSwipeChild, R.id.layout_options);

            if (surfaceLayout == null || optionsLayout == null)
            {
                pointSwipeChild = null;
                return;
            }

            optionsLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            optionsLayoutWidth = optionsLayout.getMeasuredWidth();

            scrollDistance = 0;
        }
    }

    // 处理action_move事件
    public boolean performActionMove(MotionEvent ev)
    {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();

        if (Math.abs(nowX - downX) > Math.abs(nowY - downY))
        {
            // 如果向左滑动
            if (nowX < downX)
            {
                isSwiping = true;

                // 计算要偏移的距离
                int scroll = (nowX - downX) / 2;

                // 如果大于了删除按钮的宽度， 则最大为删除按钮的宽度
                if (-scroll >= optionsLayoutWidth)
                {
                    scroll = -optionsLayoutWidth;
                }

                scrollDistance = -scroll;
                surfaceLayout.scrollTo(scrollDistance, 0);
            }

            return true;
        }

        return false;
    }

    // 处理action_up事件
    public void performActionUp()
    {
        // 偏移量大于SWIPE_DISTANCE，则显示button
        // 否则恢复默认
        if (scrollDistance >= SWIPE_DISTANCE)
        {
            isOptionsLayoutShown = true;
            open();
        }
        else
        {
            close();
        }
    }

    /**
     * 打开当前item
     */
    public void open()
    {
        startAnimation(true);
    }

    /**
     * 关闭打开的item
     */
    public void close()
    {
        startAnimation(false);
    }

    /**
     * 打开关闭动画
     * @param needOpen true need open, false need close
     */
    private void startAnimation(final boolean needOpen)
    {
        final int needModeDistance;

        if (needOpen)
        {
            needModeDistance = optionsLayoutWidth - scrollDistance;
        }
        else
        {
            needModeDistance = scrollDistance;
        }

        final double a = 2 * needModeDistance / Math.pow(animationDuration, 2.0); // 得到加速度

        final int[] leftTime = new int[1];
        leftTime[0] = animationDuration;

        Observable.interval(ANIMATION_INTERVAL, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>()
                {
                    @Override
                    public void onCompleted()
                    {
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        unsubscribe();
                    }

                    @Override
                    public void onNext(Long aLong)
                    {
                        leftTime[0] -= ANIMATION_INTERVAL;

                        // 需要打开
                        if (needOpen)
                        {
                            if (leftTime[0] <= 0)
                            {
                                scrollDistance = optionsLayoutWidth;
                            }
                            else
                            {
                                double leftDistance = a * Math.pow(leftTime[0], 2.0) / 2;
                                scrollDistance = optionsLayoutWidth - (int)leftDistance;
                            }
                        }
                        // 需要关闭
                        else
                        {
                            if (leftTime[0] <= 0)
                            {
                                scrollDistance = 0;
                            }
                            else
                            {
                                double leftDistance = a * Math.pow(leftTime[0], 2.0) / 2;
                                scrollDistance = (int)leftDistance;
                            }
                        }

                        surfaceLayout.scrollTo(scrollDistance, 0);

                        BaseSwipeViewHolder viewHolder = (BaseSwipeViewHolder)pointSwipeChild.getTag();

                        // 打开完毕
                        if (needOpen)
                        {
                            if (scrollDistance == optionsLayoutWidth)
                            {
                                isOptionsLayoutShown = true;
                                viewHolder.enableOptions();
                                unsubscribe();
                            }
                        }
                        // 关闭完成
                        else
                        {
                            if (scrollDistance == 0)
                            {
                                isOptionsLayoutShown = false;
                                viewHolder.disableOptions();
                                unsubscribe();
                            }
                        }
                    }
                });
    }

    /**
     * 当前是否打开
     *
     * @return 是否打开
     */
    @Override
    public boolean isOpened()
    {
        return !isOptionsLayoutShown;
    }

    /**
     * 打开某个item
     * @param view
     */
    @Override
    public void openItem(View view)
    {
        closeOpenedItem();

        pointSwipeChild = view;

        if (pointSwipeChild != null)
        {
            surfaceLayout = ButterKnife.findById(pointSwipeChild, R.id.layout_surface);
            View optionsLayout = ButterKnife.findById(pointSwipeChild, R.id.layout_options);

            if (surfaceLayout == null || optionsLayout == null)
            {
                pointSwipeChild = null;
                return;
            }

            optionsLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            optionsLayoutWidth = optionsLayout.getMeasuredWidth();

            isOptionsLayoutShown = true;
            surfaceLayout.scrollTo(optionsLayoutWidth, 0);

            BaseSwipeViewHolder viewHolder = (BaseSwipeViewHolder)pointSwipeChild.getTag();
            viewHolder.enableOptions();
        }
    }

    /**
     * 动画打开某个item
     * @param view
     */
    @Override
    public void smoothOpenItem(View view)
    {
        closeOpenedItem();

        pointSwipeChild = view;

        if (pointSwipeChild != null)
        {
            surfaceLayout = ButterKnife.findById(pointSwipeChild, R.id.layout_surface);
            View optionsLayout = ButterKnife.findById(pointSwipeChild, R.id.layout_options);

            if (surfaceLayout == null || optionsLayout == null)
            {
                pointSwipeChild = null;
                return;
            }

            optionsLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            optionsLayoutWidth = optionsLayout.getMeasuredWidth();

            isOptionsLayoutShown = true;
            scrollDistance = 0;
            open();
        }
    }

    /**
     * 关闭打开的item
     */
    @Override
    public void closeOpenedItem()
    {
        if (isOptionsLayoutShown)
        {
            isOptionsLayoutShown = false;
            surfaceLayout.scrollTo(0, 0);

            BaseSwipeViewHolder viewHolder = (BaseSwipeViewHolder)pointSwipeChild.getTag();
            viewHolder.disableOptions();
        }
    }

    /**
     * 动画关闭打开的item
     */
    @Override
    public void smoothCloseOpenedItem()
    {
        if (isOptionsLayoutShown)
        {
            close();
        }
    }

    /**
     * 动画时长
     * @param milliSeconds
     */
    @Override
    public void setAnimationDuration(int milliSeconds)
    {
        if (milliSeconds > 0)
        {
            this.animationDuration = milliSeconds;
        }
    }

    // endregion
}
