package com.deeppatel.rotamanager.presentation.admin.Scheduler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeEntry;

import org.joda.time.DateTime;

import java.util.List;

public class TimeEntriesAdapter extends RecyclerView.Adapter<TimeEntriesAdapter.ViewHolder>{
    List<TimeEntry> timeEntries;
    Activity activity;

    public TimeEntriesAdapter(List<TimeEntry> timeEntries, Activity activity) {
        this.timeEntries = timeEntries;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TimeEntriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.time_entry_list_card, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(TimeEntriesAdapter.ViewHolder holder, int position) {
        final TimeEntry timeEntry = timeEntries.get(position);

        DateTime date = timeEntry.getDate();
        holder.name.setText(timeEntry.getUser().getName());
        holder.day.setText(Utils.dateTimeToDayString(date));
        holder.date.setText(String.valueOf(date.getDayOfMonth()));
        holder.time.setText(String.format("%S to %S", timeEntry.getFromString(), timeEntry.getToString()));
    }

    @Override
    public int getItemCount() {
        return timeEntries.size();
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
