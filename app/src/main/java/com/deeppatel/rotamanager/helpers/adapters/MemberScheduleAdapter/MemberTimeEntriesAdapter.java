package com.deeppatel.rotamanager.helpers.adapters.MemberScheduleAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.member.MySchedule.RequestTimeChangeActivity;

import org.joda.time.DateTime;

import java.util.List;

public class MemberTimeEntriesAdapter extends RecyclerView.Adapter<MemberTimeEntriesAdapter.ViewHolder> {
    private final List<TimeEntry> timeEntries;
    private final Activity activity;
    private final boolean isAdminView;

    public MemberTimeEntriesAdapter(List<TimeEntry> timeEntries, Activity activity, boolean isAdminView) {
        this.timeEntries = timeEntries;
        this.activity = activity;
        this.isAdminView = isAdminView;
    }

    @NonNull
    @Override
    public MemberTimeEntriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.member_timetable_card, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MemberTimeEntriesAdapter.ViewHolder holder, int position) {
        final TimeEntry timeEntry = timeEntries.get(position);

        DateTime date = timeEntry.getDate();

        holder.day.setText(Utils.dateTimeToDayString(date));
        holder.date.setText(String.valueOf(date.getDayOfMonth()));
        holder.from.setText(String.format("From: %S", timeEntry.getFromString()));
        holder.to.setText(String.format("To: %S",timeEntry.getToString()));

        if (!isAdminView){
            holder.more.setVisibility(View.VISIBLE);
            holder.more.setClickable(true);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigate.to(activity, RequestTimeChangeActivity.class, "time_entry", timeEntry);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return timeEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView day;
        public TextView from;
        public TextView to;
        public View more;
        public View card;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.cardDate);
            this.day = (TextView) itemView.findViewById(R.id.cardDay);
            this.from = (TextView) itemView.findViewById(R.id.time_change_request_from);
            this.to = (TextView) itemView.findViewById(R.id.time_change_request_to);
            this.more = (View) itemView.findViewById(R.id.more_options);
            this.card = (View) itemView.findViewById(R.id.card);
        }
    }
}
