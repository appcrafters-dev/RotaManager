package com.deeppatel.rotamanager.helpers;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;

import java.util.List;

public class TimeChangeRequestParentAdapter extends RecyclerView.Adapter<TimeChangeRequestParentAdapter.ParentViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<TimeChangeRequestParentModel> itemList;
    private Activity currentActivity;

    public TimeChangeRequestParentAdapter(List<TimeChangeRequestParentModel> itemList, Activity currentActivity) {
        this.itemList = itemList;
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
        TimeChangeRequestParentModel timeChangeRequestParentModel = itemList.get(position);
        parentViewHolder.ParentItemTitle.setText(timeChangeRequestParentModel.getParentItemTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.ChildRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(timeChangeRequestParentModel.getChildItemList().size());

        TimeChangeRequestChildAdapter timeChangeRequestChildAdapter = new TimeChangeRequestChildAdapter(timeChangeRequestParentModel.getChildItemList(), currentActivity);
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(timeChangeRequestChildAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() { return itemList.size(); }

    class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView) { super(itemView);
            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}