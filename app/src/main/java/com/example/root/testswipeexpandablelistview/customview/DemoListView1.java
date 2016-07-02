package com.example.root.testswipeexpandablelistview.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.root.testswipeexpandablelistview.customview.adapter.DemoListAdapter;
import com.example.root.testswipeexpandablelistview.swipeview.BaseBaseSwipeListView;

import java.util.ArrayList;
import java.util.List;

/**
 * View that shows order list.
 */
public class DemoListView1 extends BaseBaseSwipeListView
{
    // region Variables

    private Context context;

    private List<String> numberStrings = new ArrayList<>();

    private DemoListAdapter adapter;

    // endregion

    // region Constructor

    public DemoListView1(Context context)
    {
        super(context);

        this.context = context;
    }

    public DemoListView1(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.context = context;
    }

    public DemoListView1(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        this.context = context;
    }

    public void initialize(List<String> orderedOrders)
    {
        this.numberStrings = orderedOrders;

        initializeListView();
        initializeViews();
    }

    // endregion

    // region Methods

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void initializeListView()
    {
        setVerticalScrollBarEnabled(false);
        setDividerHeight(0);
        setDivider(null);
    }

    private void initializeViews()
    {
        // set adapter.
        adapter = new DemoListAdapter(context, numberStrings);
        setAdapter(adapter);

        setSwipeListViewListener(new SwipeListViewListener() {
            @Override
            public void onSwipeItemClick(int childPosition) {
                onClickChildItem(childPosition);
            }

            @Override
            public boolean onSwipeItemLongClick(View view, int position) {
                return false;
            }
        });
    }

    private void onClickChildItem(int childPosition)
    {
        String orderModel = numberStrings.get(childPosition);

        Toast.makeText(context, orderModel, Toast.LENGTH_SHORT).show();
    }

    // endregion
}
