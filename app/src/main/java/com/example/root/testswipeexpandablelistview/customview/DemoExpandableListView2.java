package com.example.root.testswipeexpandablelistview.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.root.testswipeexpandablelistview.customview.adapter.DemoExpandableListAdapter;
import com.example.root.testswipeexpandablelistview.swipeview.BaseBaseSwipeExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * View that shows order list.
 */
public class DemoExpandableListView2 extends BaseBaseSwipeExpandableListView
{
    // region Variables

    private Context context;

    private List<String> numberStrings1 = new ArrayList<>();

    private List<String> numberStrings2 = new ArrayList<>();

    private DemoExpandableListAdapter adapter;

    // endregion

    // region Constructor

    public DemoExpandableListView2(Context context)
    {
        super(context);

        this.context = context;
    }

    public DemoExpandableListView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.context = context;
    }

    public DemoExpandableListView2(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        this.context = context;
    }

    public void initialize(List<String> toBeArrangedOrders, List<String> orderedOrders)
    {
        this.numberStrings1 = toBeArrangedOrders;
        this.numberStrings2 = orderedOrders;

        initializeListView();
        initializeViews();
    }

    // endregion

    // region Methods

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

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

        setGroupIndicator(null);
        setChildIndicator(null);
    }

    private void initializeViews()
    {
        // set adapter.
        adapter = new DemoExpandableListAdapter(context, numberStrings1, numberStrings2);
        setAdapter(adapter);

        setSwipeExpandableListViewListener(new SwipeExpandableListViewListener() {
            @Override
            public void onSwipeChildItemClick(int groupPosition, int childPosition) {
                onClickChildItem(groupPosition, childPosition);
            }

            @Override
            public boolean onSwipeItemLongClick(View view, int position) {
                return false;
            }
        });
    }

    public DemoExpandableListAdapter getExpandableListAdapter()
    {
        return adapter;
    }

    private void onClickChildItem(int groupPosition, int childPosition)
    {
        String orderModel;

        if (groupPosition == DemoExpandableListAdapter.ORDER_GROUP_1)
        {
            orderModel = numberStrings1.get(childPosition);
        }
        else
        {
            orderModel = numberStrings2.get(childPosition);
        }

        Toast.makeText(context, orderModel, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断view是否是group view
     * @param itemView
     * @return
     */
    private boolean isGroupView(View itemView)
    {
        return itemView != null
                && itemView.getTag() != null
                && itemView.getTag() instanceof DemoExpandableListAdapter.GroupViewHolder;
    }

    /**
     * 展开所有组
     */
    public void expandAllGroups()
    {
        if (adapter == null)
        {
            return;
        }

        for (int i = 0; i < adapter.getGroupCount(); i++)
        {
            expandGroup(i);
        }
    }

    // endregion
}
