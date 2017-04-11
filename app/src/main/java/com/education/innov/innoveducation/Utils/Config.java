package com.education.innov.innoveducation.Utils;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Syrine on 11/04/2017.
 */

public class Config {



    public static FirebaseAuth mAuth =  FirebaseAuth.getInstance();
    public static FirebaseUser user_connected = FirebaseAuth.getInstance().getCurrentUser() ;
    private DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference();

}
