package com.deeppatel.rotamanager.presentation.member.TimeChangeRequest;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.enums.TimeChangeRequestStatus;
import com.deeppatel.rotamanager.models.TimeChangeRequest;

import org.joda.time.DateTime;

import java.util.List;

public class MemberTimeChangeRequestAdapter extends RecyclerView.Adapter<MemberTimeChangeRequestAdapter.ViewHolder>{
    private final List<TimeChangeRequest> timeChangeRequests;
    private final Activity activity;

    public MemberTimeChangeRequestAdapter(List<TimeChangeRequest> timeChangeRequests, Activity activity) {
        this.timeChangeRequests = timeChangeRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MemberTimeChangeRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.time_change_request_list_members_card, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MemberTimeChangeRequestAdapter.ViewHolder holder, int position) {
        final TimeChangeRequest timeChangeRequest = timeChangeRequests.get(position);

        DateTime date = timeChangeRequest.getTimeEntry().getDate();

        holder.day.setText(Utils.dateTimeToDayString(date));
        holder.date.setText(String.valueOf(date.getDayOfMonth()));
        TimeChangeRequestStatus status = timeChangeRequest.getStatus();
        holder.status.setText(status.getStatus());
        holder.status.setTextColor(activity.getColor(status.getColor()));
        holder.time.setText(String.format("%S to %S", timeChangeRequest.getFromString(), timeChangeRequest.getToString()));
    }


    @Override
    public int getItemCount() {
        return timeChangeRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView day;
        public TextView status;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.cardDate);
            this.day = itemView.findViewById(R.id.cardDay);
            this.status = itemView.findViewById(R.id.status);
            this.time = itemView.findViewById(R.id.new_timing);
        }
    }
}