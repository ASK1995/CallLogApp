package com.example.android.calllog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.calllog.databinding.CallLogFragmentBinding;

public class AllCallLogFragment  extends Fragment {

    CallLogFragmentBinding binding;
    CallLogAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.call_log_fragment,container,false);
        initComponents();
        return binding.getRoot();
    }

    public void initComponents(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CallLogAdapter(getContext());
        binding.recyclerView.setAdapter(adapter);
        loadData();
    }

    public void loadData(){
        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
        adapter.addAllCallLog(callLogUtils.readCallLogs());
        adapter.notifyDataSetChanged();

    }
}
