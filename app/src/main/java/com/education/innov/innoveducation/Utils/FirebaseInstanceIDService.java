package com.education.innov.innoveducation.Utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;


/**
 * Created by Ch.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN ="REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
         String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, token);
       /* System.out.println("testtest");
        System.out.println(token);



        SharedPreferences.Editor editor = getSharedPreferences("deviceToken", MODE_PRIVATE).edit();
        editor.putString("Token",token );
        editor.commit();
*/
    }

}