package com.deeppatel.rotamanager.presentation.admin.TimeChangeRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.models.TimeChangeRequestsByDate;

import java.util.List;

public class TimeChangeRequestsByDateAdapter extends RecyclerView.Adapter<TimeChangeRequestsByDateAdapter.ParentViewHolder> {
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<TimeChangeRequestsByDate> timeChangeRequests;
    private final Activity currentActivity;

    public TimeChangeRequestsByDateAdapter(List<TimeChangeRequestsByDate> timeChangeRequests, Activity currentActivity) {
        this.timeChangeRequests = timeChangeRequests;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_time_change_request_child , viewGroup, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position)
    {
        TimeChangeRequestsByDate timeChangeRequestsByDate = timeChangeRequests.get(position);
        parentViewHolder.title.setText(timeChangeRequestsByDate.getTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.requestsRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(timeChangeRequestsByDate.getTimeChangeRequests().size());
        parentViewHolder.requestsRecyclerView.setLayoutManager(layoutManager);

        TimeChangeRequestChildAdapter timeChangeRequestChildAdapter = new TimeChangeRequestChildAdapter(timeChangeRequestsByDate.getTimeChangeRequests(), currentActivity);
        parentViewHolder.requestsRecyclerView.setAdapter(timeChangeRequestChildAdapter);
        parentViewHolder.requestsRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() { return timeChangeRequests.size(); }

    static class ParentViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final RecyclerView requestsRecyclerView;

        ParentViewHolder(final View itemView) { super(itemView);
            title = itemView.findViewById(R.id.parent_item_title);
            requestsRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}