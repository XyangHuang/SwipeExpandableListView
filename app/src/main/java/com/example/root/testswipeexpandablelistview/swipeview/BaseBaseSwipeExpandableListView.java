package com.example.root.testswipeexpandablelistview.swipeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

/**
 * Base ExpandableListView list view that can swipe.
 */
public class BaseBaseSwipeExpandableListView extends ExpandableListView implements BaseSwipeView
{
    // region Variables

    private SwipeViewHelper swipeViewHelper;
    private SwipeExpandableListViewListener swipeExpandableListViewListener;

    public interface SwipeExpandableListViewListener
    {
        void onSwipeChildItemClick(int groupPosition, int childPosition);

        boolean onSwipeItemLongClick(View view, int position);
    }

    // endregion

    // region Constructor

    public BaseBaseSwipeExpandableListView(Context context)
    {
        super(context);

        initialize();
    }

    public BaseBaseSwipeExpandableListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public BaseBaseSwipeExpandableListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    public void initialize()
    {
        swipeViewHelper = new SwipeViewHelper();

        initializeListener();
    }

    // endregion

    // region Methods

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setSwipeExpandableListViewListener(SwipeExpandableListViewListener swipeExpandableListViewListener)
    {
        this.swipeExpandableListViewListener = swipeExpandableListViewListener;
    }

    private void initializeListener()
    {
        setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!swipeViewHelper.isSwiping)
                {
                    if (swipeExpandableListViewListener != null)
                    {
                        swipeExpandableListViewListener.onSwipeChildItemClick(groupPosition, childPosition);
                    }

                    return true;
                }

                return false;
            }
        });

        setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (swipeViewHelper.isSwiping)
                {
                    return true;
                }

                return swipeExpandableListViewListener != null && swipeExpandableListViewListener.onSwipeItemLongClick(view, position);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                swipeViewHelper.isSwiping = false;
                swipeViewHelper.tempIsOptionsLayoutShown = swipeViewHelper.isOptionsLayoutShown;

                if (swipeViewHelper.isOptionsLayoutShown)
                {
                    swipeViewHelper.close();

                    return true;
                }

                performActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:

                // 如果按下时有item是打开状态
                if (swipeViewHelper.tempIsOptionsLayoutShown)
                {
                    return true;
                }

                if (swipeViewHelper.pointSwipeChild == null)
                {
                    break;
                }

                return performActionMove(ev);
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(true);

                // 如果按下时有item是打开状态
                if (swipeViewHelper.tempIsOptionsLayoutShown)
                {
                    return true;
                }

                if (swipeViewHelper.pointSwipeChild == null)
                {
                    break;
                }

                performActionUp();
                break;
        }

        return super.onTouchEvent(ev);
    }

    // 处理action_down事件
    private void performActionDown(MotionEvent ev)
    {
        int downX = (int) ev.getX();
        int downY = (int) ev.getY();

        // 获取当前点的item
        View childView = getChildAt(pointToPosition(downX, downY) - getFirstVisiblePosition());
        swipeViewHelper.performActionDown(ev, childView);
    }

    // 处理action_move事件
    private boolean performActionMove(MotionEvent ev)
    {
        boolean result = swipeViewHelper.performActionMove(ev);

        if (result)
        {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return result || super.onTouchEvent(ev);
    }

    // 处理action_up事件
    private void performActionUp()
    {
        getParent().requestDisallowInterceptTouchEvent(false);
        swipeViewHelper.performActionUp();
    }

    @Override
    public boolean isOpened()
    {
        return swipeViewHelper.isOpened();
    }

    @Override
    public void closeOpenedItem()
    {
        swipeViewHelper.closeOpenedItem();
    }

    @Override
    public void smoothCloseOpenedItem()
    {
        swipeViewHelper.smoothCloseOpenedItem();
    }

    @Override
    public void openItem(View view)
    {
        swipeViewHelper.openItem(view);
    }

    @Override
    public void smoothOpenItem(View view)
    {
        swipeViewHelper.smoothOpenItem(view);
    }

    @Override
    public void setAnimationDuration(int milliSeconds)
    {
        swipeViewHelper.setAnimationDuration(milliSeconds);
    }

    // endregion
}
