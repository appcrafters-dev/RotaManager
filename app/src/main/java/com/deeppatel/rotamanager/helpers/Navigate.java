package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.admin.AdminDashboard;
import com.deeppatel.rotamanager.member.MemberHomePage;
import com.deeppatel.rotamanager.models.User;

public class Navigate {
    public static void to(Activity fromContext, Class toContext, String key, User value) {
        Intent intent = new Intent(fromContext, toContext);
        intent.putExtra(key, value);
        fromContext.startActivity(intent);
    }

    public static void to(Activity fromContext, Class toContext) {
        Intent intent = new Intent(fromContext, toContext);
        fromContext.startActivity(intent);
    }

    public static void replace(Activity fromContext, Class toContext) {
        Intent intent = new Intent(fromContext, toContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        fromContext.startActivity(intent);
        fromContext.finish();
    }

    public static void redirectBasedOnUser(Activity context, com.deeppatel.rotamanager.models.User user) {
        if (user == null) {
            Navigate.replace(context, LoginActivity.class);
        } else if (user.getRole() != null && user.getRole().equals("admin")) {
            Navigate.replace(context, AdminDashboard.class);
        } else {
            Navigate.replace(context, MemberHomePage.class);
        }
    }
}
