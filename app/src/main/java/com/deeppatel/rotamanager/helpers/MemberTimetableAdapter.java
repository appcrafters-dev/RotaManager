package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.EditStaffMember;

public class MemberTimetableAdapter extends RecyclerView.Adapter<MemberTimetableAdapter.ViewHolder> {
    private MemberTimetableModel[] listdata;
    private Activity currentActivity;

    public MemberTimetableAdapter(MemberTimetableModel[] listdata, Activity currentActivity) {
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
        final MemberTimetableModel myListData = listdata[position];
        holder.date.setText(listdata[position].getDate());
        holder.day.setText(listdata[position].getDay());
        holder.from.setText(listdata[position].getFrom());
        holder.to.setText(listdata[position].getTo());

        holder.more.setVisibility(View.VISIBLE);
        holder.more.setClickable(true);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("!!!!!!!!!","aaaaaa");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
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
