package com.example.root.testswipeexpandablelistview.swipeview;

import android.view.View;
import android.widget.LinearLayout;

import com.example.root.testswipeexpandablelistview.R;

import butterknife.BindView;

public abstract class BaseSwipeViewHolder
{
    @BindView(R.id.layout_surface)
    protected View surfaceLayout;

    @BindView(R.id.layout_options)
    protected LinearLayout optionsLayout;

    public void enableOptions()
    {
        if (optionsLayout == null)
        {
            throw new NullPointerException("Options layout cannot be null");
        }

        for (int i = 0; i < optionsLayout.getChildCount(); i++)
        {
            View optionView = optionsLayout.getChildAt(i);
            optionView.setEnabled(true);
        }
    }

    public void disableOptions()
    {
        if (optionsLayout == null)
        {
            throw new NullPointerException("Options layout cannot be null");
        }

        for (int i = 0; i < optionsLayout.getChildCount(); i++)
        {
            View optionView = optionsLayout.getChildAt(i);
            optionView.setEnabled(false);
        }
    }

    protected void initializeViews()
    {
        disableOptions();
        resetOptionsLayoutHeight();
    }

    /**
     * 必须要被调用, 设置options layout的高度
     */
    private void resetOptionsLayoutHeight()
    {
        if (surfaceLayout == null)
        {
            throw new NullPointerException("Surface layout cannot be null");
        }

        if (optionsLayout == null)
        {
            throw new NullPointerException("Options layout cannot be null");
        }

        surfaceLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        optionsLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        optionsLayout.getLayoutParams().height = surfaceLayout.getMeasuredHeight();
        optionsLayout.invalidate();
    }
}
