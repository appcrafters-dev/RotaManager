package com.deeppatel.rotamanager.presentation.admin.ManageStaff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.models.User;

import java.util.List;


public class StaffMembersAdapter extends RecyclerView.Adapter<StaffMembersAdapter.ViewHolder> {
    final List<User> members;
    final Activity activity;

    public StaffMembersAdapter(List<User> members, Activity activity) {
        this.members = members;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.staff_members_listview, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User member = members.get(position);
        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
//        holder.memberImage.setImageResource(listdata[position].getPhotoURI());
//        Picasso.get().load(members.get(position).getPhotoURI()).into(holder.memberImage);
        holder.forwardButton.setImageResource(R.drawable.ic_baseline_chevron_right_24);

        holder.staffMembers.setOnClickListener(view -> Navigate.to(activity, ProfileActivity.class, "member", member));
    }


    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView memberImage;
        public ImageView forwardButton;
        public TextView name;
        public TextView email;

        public GridLayout staffMembers;

        public ViewHolder(View itemView) {
            super(itemView);
            memberImage = itemView.findViewById(R.id.photoId);
            forwardButton = itemView.findViewById(R.id.forward_button);
            name = itemView.findViewById(R.id.member_name);
            email = itemView.findViewById(R.id.member_email);
            staffMembers = itemView.findViewById(R.id.staff_members_list);
        }
    }
}


