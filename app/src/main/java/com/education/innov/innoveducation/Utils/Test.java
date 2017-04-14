package com.education.innov.innoveducation.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.innov.innoveducation.Activities.MainActivity;

/**
 * Created by Syrine on 13/04/2017.
 */

public class Test {

    NetworkInfo activeNetworkInfo;

    public boolean TestConnection (Context ctx){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {

            return true ;
        }
        return false ;
    }

}
