package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;

import java.util.List;

public class TimeEntryListAdapter extends RecyclerView.Adapter<TimeEntryListAdapter.ViewHolder>{
    private List<TimeEntryListModel> listdata;
    private Activity currentActivity;

    public TimeEntryListAdapter(List<TimeEntryListModel> listdata, Activity currentActivity) {
        this.listdata = listdata;
        this.currentActivity = currentActivity;
    }
    @Override
    public TimeEntryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.time_entry_list_card, parent, false);
        TimeEntryListAdapter.ViewHolder viewHolder = new TimeEntryListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeEntryListAdapter.ViewHolder holder, int position) {
        final TimeEntryListModel myListData = listdata.get(position);
        holder.name.setText(listdata.get(position).getName());
        holder.day.setText(listdata.get(position).getDay());
        holder.date.setText(listdata.get(position).getDate());
        holder.time.setText(listdata.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,day,date,time;
        public LinearLayout time_entry_list_card;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.member_name);
            this.day = (TextView) itemView.findViewById(R.id.cardDay);
            this.date = (TextView) itemView.findViewById(R.id.cardDate);
            this.name = (TextView) itemView.findViewById(R.id.time_entry_list_name);
            this.time = (TextView) itemView.findViewById(R.id.time_entry_list_time);
            time_entry_list_card = (LinearLayout) itemView.findViewById(R.id.time_entry_list_card);
        }
    }
}
