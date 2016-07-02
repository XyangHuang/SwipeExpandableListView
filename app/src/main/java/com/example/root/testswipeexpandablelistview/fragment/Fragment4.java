package com.example.root.testswipeexpandablelistview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.testswipeexpandablelistview.R;
import com.example.root.testswipeexpandablelistview.customview.DemoListView2;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment4 extends Fragment
{
    @BindView(R.id.list_view_1)
    DemoListView2 listView1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_4, null);

        ButterKnife.bind(this, view);
        listView1.initialize(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4", "1", "2", "3", "4"));

        return view;
    }

    @OnClick(R.id.btn_notify)
    void onClickNotifyButton()
    {
        listView1.closeOpenedItem();
        listView1.notifyDataSetChanged();
    }
}
