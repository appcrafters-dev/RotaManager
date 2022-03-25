package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;

public class MemberTimeChangeRequestAdapter extends RecyclerView.Adapter<MemberTimeChangeRequestAdapter.ViewHolder>{
    private MemberTimeChangeRequestModel[] listdata;
    private Activity currentActivity;

    public MemberTimeChangeRequestAdapter(MemberTimeChangeRequestModel[] listdata, Activity currentActivity) {
        this.listdata = listdata;
        this.currentActivity = currentActivity;
    }
    @Override
    public MemberTimeChangeRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.time_change_request_list_members_card, parent, false);
        MemberTimeChangeRequestAdapter.ViewHolder viewHolder = new MemberTimeChangeRequestAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberTimeChangeRequestAdapter.ViewHolder holder, int position) {
        final MemberTimeChangeRequestModel myListData = listdata[position];
        holder.date.setText(listdata[position].getDate());
        holder.day.setText(listdata[position].getDay());
        holder.status.setText(listdata[position].getStatus());
        holder.time.setText(listdata[position].getTime());
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView day;
        public TextView status;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.cardDate);
            this.day = (TextView) itemView.findViewById(R.id.cardDay);
            this.status = (TextView) itemView.findViewById(R.id.status);
            this.time = (TextView) itemView.findViewById(R.id.new_timing);
        }
    }
}