package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.presentation.admin.AdminDashboard;
import com.deeppatel.rotamanager.presentation.member.MemberHomeActivity;

public class Navigate {
    public static <T extends Parcelable> void to(Activity fromContext, Class toContext, String key, T value) {
        Intent intent = new Intent(fromContext, toContext);
        intent.putExtra(key, value);
        fromContext.startActivity(intent);
    }
    public static void to(Activity fromContext, Class toContext, String key, String value) {
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
            Navigate.replace(context, MemberHomeActivity.class);
        }
    }
}
