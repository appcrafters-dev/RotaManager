package com.deeppatel.rotamanager.helpers;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.AdminDashboard;
import com.deeppatel.rotamanager.admin.NewStaffMember;
import com.deeppatel.rotamanager.admin.StaffMemberList;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;


public class StaffMemberDataAdapter extends RecyclerView.Adapter<StaffMemberDataAdapter.ViewHolder>{
    private StaffMemberDataModel[] listdata;
    private Activity currentActivity;

    public StaffMemberDataAdapter(StaffMemberDataModel[] listdata, Activity currentActivity) {
        this.listdata = listdata;
        this.currentActivity = currentActivity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.staff_members_listview, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StaffMemberDataModel myListData = listdata[position];
        holder.name.setText(listdata[position].getName());
        holder.email.setText(listdata[position].getEmail());
//        holder.memberImage.setImageResource(listdata[position].getPhotoURI());
        Picasso.get().load(listdata[position].getPhotoURI()).into(holder.memberImage);
        holder.staffMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RedirectToActivity().redirectActivityAfterFinish(currentActivity, NewStaffMember.class);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView memberImage;
        public TextView name;
        public TextView email;

        public GridLayout staffMembers;
        public ViewHolder(View itemView) {
            super(itemView);
            this.memberImage = (ImageView) itemView.findViewById(R.id.photoId);
            this.name = (TextView) itemView.findViewById(R.id.member_name);
            this.email = (TextView) itemView.findViewById(R.id.member_email);
            staffMembers = (GridLayout) itemView.findViewById(R.id.staff_members_list);
        }
    }
}


