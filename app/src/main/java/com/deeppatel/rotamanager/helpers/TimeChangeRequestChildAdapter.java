package com.deeppatel.rotamanager.helpers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.ViewTimeChangeRequest;

import java.util.List;

public class TimeChangeRequestChildAdapter extends RecyclerView.Adapter<TimeChangeRequestChildAdapter.ChildViewHolder> {
    private List<TimeChangeRequestChildModel> timeChangeRequestChildModelList;
    private Activity currentActivity;

    TimeChangeRequestChildAdapter(List<TimeChangeRequestChildModel> timeChangeRequestChildModelList, Activity currentActivity) {
        this.timeChangeRequestChildModelList = timeChangeRequestChildModelList;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_change_request_list_admin_card, viewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {
        TimeChangeRequestChildModel timeChangeRequestChildModel = timeChangeRequestChildModelList.get(position);
        childViewHolder.cardDate.setText(timeChangeRequestChildModel.getDate());
        childViewHolder.cardDay.setText(timeChangeRequestChildModel.getDay());
        childViewHolder.new_timing.setText(timeChangeRequestChildModel.getName() + " has requested for the time change: " + timeChangeRequestChildModel.getFrom() + " to " + timeChangeRequestChildModel.getTo());
        childViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Navigate().toArguementsTime(currentActivity, ViewTimeChangeRequest.class,timeChangeRequestChildModel);
            }
        });
    }

    @Override
    public int getItemCount() { return timeChangeRequestChildModelList.size(); }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView cardDay,cardDate,new_timing;
        public View card;
        ChildViewHolder(View itemView) { super(itemView);
            cardDay = itemView.findViewById(R.id.cardDay);
            cardDate = itemView.findViewById(R.id.cardDate);
            new_timing = itemView.findViewById(R.id.new_timing);
            card = (View) itemView.findViewById(R.id.card);
        }
    }
}
