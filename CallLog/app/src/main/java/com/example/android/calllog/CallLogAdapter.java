package com.example.android.calllog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.calllog.databinding.ListRowBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CallLogAdapter  extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder>{

    ArrayList<CallLogInfo> callLogInfoArrayList;
    Context context;

    public CallLogAdapter(Context context){
        callLogInfoArrayList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListRowBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_row,parent,false);
        return new CallLogViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogViewHolder holder, int position) {
        holder.bind(callLogInfoArrayList.get(position));
    }

    public void addAllCallLog(ArrayList<CallLogInfo> list){
        callLogInfoArrayList.clear();
        callLogInfoArrayList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return callLogInfoArrayList.size();
    }

    class CallLogViewHolder extends RecyclerView.ViewHolder{
        ListRowBinding itemBinding;
        public CallLogViewHolder(ListRowBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
        }

        public void bind(final CallLogInfo callLog){
            switch(Integer.parseInt(callLog.getCallType()))
            {
                case CallLog.Calls.OUTGOING_TYPE:
                    itemBinding.callType.setText("Outgoing");
                    itemBinding.callType.setTextColor(Color.GREEN);

                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    itemBinding.callType.setText("Incoming");
                    itemBinding.callType.setTextColor(Color.BLUE);
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    itemBinding.callType.setText("Missed");
                    itemBinding.callType.setTextColor(Color.RED);
                    break;
            }

            Date dateObj = new Date(callLog.getDate());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy   hh:mm a");
            itemBinding.textViewCallNumber.setText(callLog.getNumber());
            itemBinding.textViewCallDate.setText(formatter.format(dateObj));
        }
    }
}