package com.example.root.testswipeexpandablelistview.customview.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.example.root.testswipeexpandablelistview.R;
import com.example.root.testswipeexpandablelistview.swipeview.BaseSwipeViewHolder;

import butterknife.BindView;

public abstract class BaseCustomViewHolder extends BaseSwipeViewHolder
{
    @BindView(R.id.layout_time)
    LinearLayout timeLayout;

    public String numberString;

    protected void initializeViews()
    {
        int i = Integer.parseInt(numberString);

        if (i % 2 == 0)
        {
            timeLayout.setVisibility(View.GONE);
        }
        else
        {
            timeLayout.setVisibility(View.VISIBLE);
        }

        super.initializeViews();
    }
}
