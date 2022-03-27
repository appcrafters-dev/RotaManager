package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.MainActivity;
import com.deeppatel.rotamanager.admin.AdminDashboard;
import com.deeppatel.rotamanager.members.MemberHomePage;
import com.deeppatel.rotamanager.models.User;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedirectToActivity {
    public void redirectActivityOnly(Activity fromContext, Class toContext){
        Intent intent = new Intent(fromContext, toContext);
        fromContext.startActivity(intent);
    }

    public void redirectWithProps(Activity fromContext, Class toContext, List<StaffMemberDataModel> listdata, int position){
        Intent intent = new Intent(fromContext, toContext);
        intent.putExtra("name", listdata.get(position).getName());
        intent.putExtra("email", listdata.get(position).getEmail());
        intent.putExtra("phone", listdata.get(position).getPhone());
        intent.putExtra("designation", listdata.get(position).getDesignation());
        intent.putExtra("gender", listdata.get(position).getGender());
        intent.putExtra("photoURI", listdata.get(position).getPhotoURI());
        intent.putExtra("uid", listdata.get(position).getUid());
        fromContext.startActivity(intent);
    }

    public  static void replace(Activity fromContext, Class toContext){
        Intent intent = new Intent(fromContext, toContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        fromContext.startActivity(intent);
        fromContext.finish();
    }

    public void redirectActivityAfterFinish(Activity fromContext, Class toContext){
        Intent intent = new Intent(fromContext, toContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        fromContext.startActivity(intent);
        fromContext.finish();
    }

    public static void redirectBasedOnUser(Activity context, User user) {
        if (user == null) {
            RedirectToActivity.replace(context, LoginActivity.class);
        } else if (user.getRole() != null && user.getRole().equals("admin")) {
            RedirectToActivity.replace(context, AdminDashboard.class);
        } else {
            RedirectToActivity.replace(context, MemberHomePage.class);
        }
    }
}
