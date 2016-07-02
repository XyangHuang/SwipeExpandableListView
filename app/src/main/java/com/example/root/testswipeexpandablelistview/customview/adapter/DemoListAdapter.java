package com.example.root.testswipeexpandablelistview.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.root.testswipeexpandablelistview.R;
import com.example.root.testswipeexpandablelistview.customview.DemoExpandableListView2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoListAdapter extends BaseAdapter
{
    // region Variables

    private LayoutInflater inflater;

    private List<String> numberStrings = new ArrayList<>();

    private Context context;

    // endregion

    // region Constructor

    /**
     * Construct an instance of {@link DemoExpandableListView2} by specified {@link Context} and list of {@OrderModel}.
     */
    public DemoListAdapter(Context context, List<String> numberStrings)
    {
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        setOrderModelList(numberStrings);
    }

    // endregion

    // region Methods

    @Override
    public int getCount() {
        return numberStrings.size();
    }

    @Override
    public Object getItem(int position) {
        return numberStrings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
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

        holder.numberString = numberStrings.get(position);
        holder.initializeViews();

        return convertView;
    }

    public void setOrderModelList(List<String> orderedOrders)
    {
        this.numberStrings.clear();

        if (orderedOrders != null)
        {
            this.numberStrings.addAll(orderedOrders);
        }
    }

    // endregion

    // region View Holder class

    public class ChildViewHolder extends BaseCustomViewHolder
    {
        @BindView(R.id.layout_time)
        LinearLayout timeLayout;

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
        }

        @Override
        protected void initializeViews()
        {
            super.initializeViews();
        }
    }

    // endregion
}
