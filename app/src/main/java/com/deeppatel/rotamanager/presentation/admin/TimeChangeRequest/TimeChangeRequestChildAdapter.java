package com.deeppatel.rotamanager.presentation.admin.TimeChangeRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.presentation.member.TimeChangeRequest.TimeChangeRequestActivity;
import com.deeppatel.rotamanager.models.TimeChangeRequest;

import org.joda.time.DateTime;

import java.util.List;

public class TimeChangeRequestChildAdapter extends RecyclerView.Adapter<TimeChangeRequestChildAdapter.ChildViewHolder> {
    private final List<TimeChangeRequest> timeChangeRequests;
    private final Activity currentActivity;

    public TimeChangeRequestChildAdapter(List<TimeChangeRequest> timeChangeRequests, Activity currentActivity) {
        this.timeChangeRequests = timeChangeRequests;
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
        TimeChangeRequest timeChangeRequest = timeChangeRequests.get(position);

        DateTime date = timeChangeRequest.getTimeEntry().getDate();
        childViewHolder.cardDate.setText(String.valueOf(date.getDayOfMonth()));
        childViewHolder.cardDay.setText(Utils.dateTimeToDayString(date));
        childViewHolder.new_timing.setText(String.format("%s has requested for the time change: %S to %S", timeChangeRequest.getTimeEntry().getUser().getName(), timeChangeRequest.getFromString(), timeChangeRequest.getToString()));
        childViewHolder.card.setOnClickListener(v -> Navigate.to(currentActivity, TimeChangeRequestActivity.class, "time_change_request", timeChangeRequest));
    }

    @Override
    public int getItemCount() { return timeChangeRequests.size(); }

    static class ChildViewHolder extends RecyclerView.ViewHolder {

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
