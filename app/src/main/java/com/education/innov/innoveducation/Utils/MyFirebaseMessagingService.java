package com.education.innov.innoveducation.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.education.innov.innoveducation.Activities.MainActivity;
import com.education.innov.innoveducation.Entities.Notification;
import com.education.innov.innoveducation.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;


/**
 * Created by Ch.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if("mm".equals("g")){
            return;
        }
        System.out.println("******************** +  getMessageType "+remoteMessage.getMessageType());
        System.out.println("******************** +  getFrom "+remoteMessage.getFrom());
        System.out.println("******************** +  getMessageId "+remoteMessage.getMessageId());
        System.out.println("******************** +  getTo "+remoteMessage.getTo());
        System.out.println("******************** +  getData "+remoteMessage.getData());
        System.out.println("******************** +  getData().get(message) "+remoteMessage.getData().get("message"));
        System.out.println("******************** +  getData().get(title) "+remoteMessage.getData().get("title"));

        Notification n = new Notification();
        n.setDate(new Date().toString());
        n.setContenue(remoteMessage.getData().get("title") +" : "+remoteMessage.getData().get("message"));
        // child(CurrentUser)
        Config.mDatabase.child("Notifications").child("09428835").push().setValue(n);
        showNotification(remoteMessage.getFrom(),remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        System.out.println("******************** + 2");


    }


    private void showNotification(String from , String title,String message) {

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent);
        builder.mNumber=1;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }

}
