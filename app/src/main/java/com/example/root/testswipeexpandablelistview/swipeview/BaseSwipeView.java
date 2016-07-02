package com.example.root.testswipeexpandablelistview.swipeview;

import android.view.View;

public interface BaseSwipeView
{
    /**
     * 当前是否打开
     *
     * @return 是否打开
     */
    boolean isOpened();

    /**
     * 打开某个item
     * @param view
     */
    void openItem(View view);

    /**
     * 动画打开某个item
     * @param view
     */
    void smoothOpenItem(View view);

    /**
     * 关闭打开的item
     */
    void closeOpenedItem();

    /**
     * 动画关闭打开的item
     */
    void smoothCloseOpenedItem();

    /**
     * 动画时长
     * @param milliSeconds
     */
    void setAnimationDuration(int milliSeconds);
}
