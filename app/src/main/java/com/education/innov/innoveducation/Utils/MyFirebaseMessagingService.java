package com.education.innov.innoveducation.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import helloexpress.helloexp.MainActivity;
import helloexpress.helloexp.R;


/**
 * Created by bechirkaddech on 12/4/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("******************** +  getMessageType "+remoteMessage.getMessageType());
        System.out.println("******************** +  getFrom "+remoteMessage.getFrom());
        System.out.println("******************** +  getMessageId "+remoteMessage.getMessageId());
        System.out.println("******************** +  getTo "+remoteMessage.getTo());
        System.out.println("******************** +  getData "+remoteMessage.getData());
        System.out.println("******************** +  getData().get(message) "+remoteMessage.getData().get("message"));
        System.out.println("******************** +  getData().get(title) "+remoteMessage.getData().get("title"));


        showNotification(remoteMessage.getFrom(),remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        System.out.println("******************** + 2");


    }


    private void showNotification(String from , String title,String message) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                 .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }

}
