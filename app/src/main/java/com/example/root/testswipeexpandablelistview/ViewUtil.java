package com.example.root.testswipeexpandablelistview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hxyan_000 on 2016/7/2.
 */
public class ViewUtil
{
    public static void setEntireViewClickable(View view, boolean clickable)
    {
        if (view == null)
        {
            return;
        }

        view.setClickable(clickable);

        if (view instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup)view;

            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View childView = viewGroup.getChildAt(i);
                setEntireViewClickable(childView, clickable);
            }
        }
    }
}
