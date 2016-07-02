package com.example.root.testswipeexpandablelistview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.testswipeexpandablelistview.R;
import com.example.root.testswipeexpandablelistview.customview.DemoExpandableListView1;

import java.util.Arrays;

import butterknife.ButterKnife;

public class Fragment1 extends Fragment
{
    DemoExpandableListView1 listView1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_1, null);

        listView1 = ButterKnife.findById(view,R.id.list_view_1);
        listView1.initialize(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4"),
                Arrays.asList("5", "6", "7", "8", "1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4")
        );
        listView1.expandAllGroups();

        return view;
    }
}
