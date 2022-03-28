package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.member.RequestTimeChangeRequest;

import java.util.List;

public class MemberTimetableAdapter extends RecyclerView.Adapter<MemberTimetableAdapter.ViewHolder> {
    private List<MemberTimetableModel> listdata;
    private Activity currentActivity;

    public MemberTimetableAdapter(List<MemberTimetableModel> listdata, Activity currentActivity) {
        this.listdata = listdata;
        this.currentActivity = currentActivity;
    }
    @Override
    public MemberTimetableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.member_timetable_card, parent, false);
        MemberTimetableAdapter.ViewHolder viewHolder = new MemberTimetableAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberTimetableAdapter.ViewHolder holder, int position) {
        final MemberTimetableModel myListData = listdata.get(position);
        holder.date.setText(listdata.get(position).getDate());
        holder.day.setText(listdata.get(position).getDay());
        holder.from.setText(listdata.get(position).getFrom());
        holder.to.setText(listdata.get(position).getTo());

        holder.more.setVisibility(View.VISIBLE);
        holder.more.setClickable(true);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("!!!!!!!!!","aaaaaa");
                new Navigate().toArguements(currentActivity, RequestTimeChangeRequest.class,myListData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
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
