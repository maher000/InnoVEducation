package com.education.innov.innoveducation.Utils;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Syrine on 11/04/2017.
 */

public class Config {


    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    Context ctx ;
    void config (){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
    }

}
