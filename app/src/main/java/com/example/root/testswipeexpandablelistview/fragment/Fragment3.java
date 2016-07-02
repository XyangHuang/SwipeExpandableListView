package com.example.root.testswipeexpandablelistview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.testswipeexpandablelistview.R;
import com.example.root.testswipeexpandablelistview.customview.DemoListView1;

import java.util.Arrays;

import butterknife.ButterKnife;

public class Fragment3 extends Fragment
{
    DemoListView1 listView1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_3, null);

        listView1 = ButterKnife.findById(view,R.id.list_view_1);
        listView1.initialize(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4"));

        return view;
    }
}
