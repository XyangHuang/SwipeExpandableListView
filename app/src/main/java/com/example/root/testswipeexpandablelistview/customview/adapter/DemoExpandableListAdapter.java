package com.example.root.testswipeexpandablelistview.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.testswipeexpandablelistview.R;
import com.example.root.testswipeexpandablelistview.customview.DemoExpandableListView2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoExpandableListAdapter extends BaseExpandableListAdapter
{
    // region Variables

    public static final int ORDER_GROUP_1 = 0;
    public static final int ORDER_GROUP_2 = 1;

    private LayoutInflater inflater;

    private List<String> groupTitles;

    private List<String> numberStrings1 = new ArrayList<>();

    private List<String> numberStrings2 = new ArrayList<>();

    private Context context;

    // endregion

    // region Constructor

    /**
     * Construct an instance of {@link DemoExpandableListView2} by specified {@link Context} and list of {@OrderModel}.
     */
    public DemoExpandableListAdapter(Context context, List<String> numberStrings1, List<String> numberStrings2)
    {
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        groupTitles = Arrays.asList(
                "1", "2"
        );

        setOrderModelList(numberStrings1, numberStrings2);
    }

    // endregion

    // region Methods

    @Override
    public int getGroupCount()
    {
        return groupTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        if (groupPosition == ORDER_GROUP_1)
        {
            return numberStrings1.size();
        }
        else
        {
            return numberStrings2.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return groupTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        if (groupPosition == ORDER_GROUP_1)
        {
            return numberStrings1.get(childPosition);
        }
        else
        {
            return numberStrings2.get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        GroupViewHolder holder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.view_custom_group_item, parent, false);

            holder = new GroupViewHolder(convertView);

            convertView.setTag(holder);
        }
        else
        {
            holder = (GroupViewHolder)convertView.getTag();
        }

        holder.initializeViews(groupPosition, isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ChildViewHolder holder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.view_custom_swipe_item, parent, false);

            holder = new ChildViewHolder(convertView);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ChildViewHolder) convertView.getTag();
        }

        holder.numberString = numberStrings2.get(childPosition);
        holder.initializeViews();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    public void setOrderModelList(List<String> toBeArrangedOrders, List<String> orderedOrders)
    {
        this.numberStrings1.clear();
        this.numberStrings2.clear();

        if (toBeArrangedOrders != null)
        {
            this.numberStrings1.addAll(toBeArrangedOrders);
        }

        if (orderedOrders != null)
        {
            this.numberStrings2.addAll(orderedOrders);
        }
    }

    /**
     * Set selected item.
     */
    public void setSelectedOrderId(String selectedOrderId)
    {
        notifyDataSetChanged();
    }

    // endregion

    // region View Holder class

    public class GroupViewHolder
    {
        @BindView(R.id.text_group_title)
        TextView groupTitleTextView;

        View view;

        public GroupViewHolder(View view)
        {
            this.view = view;

            ButterKnife.bind(this, view);
        }

        public void initializeViews(int groupPosition, boolean isExpanded)
        {
            groupTitleTextView.setText(groupTitles.get(groupPosition));
        }
    }

    public class ChildViewHolder extends BaseCustomViewHolder
    {
        View optionTextView;

        public ChildViewHolder(View view)
        {
            ButterKnife.bind(this, view);

            optionsLayout = (LinearLayout)view.findViewById(R.id.layout_options);
            optionTextView = view.findViewById(R.id.text_option);

            optionTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "click option", Toast.LENGTH_SHORT).show();
                }
            });

            disableOptions();
        }

        @Override
        protected void initializeViews()
        {
            super.initializeViews();
        }
    }

    // endregion
}
