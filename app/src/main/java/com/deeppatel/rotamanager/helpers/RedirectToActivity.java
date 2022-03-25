package com.deeppatel.rotamanager.helpers;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

public class RedirectToActivity {
    public RedirectToActivity(){}

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
        fromContext.startActivity(intent);
    }

    public void redirectActivityAfterFinish(Activity fromContext, Class toContext){
        Intent intent = new Intent(fromContext, toContext);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        fromContext.startActivity(intent);
        fromContext.finish();
    }
}
