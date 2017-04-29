package com.education.innov.innoveducation.Utils;


import android.app.Notification;
import android.renderscript.RenderScript;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ch on 12/12/2016.
 */

public class psuhNotificationAllUsers {
    private  static String ANDROID_NOTIFICATION_URL = "https://fcm.googleapis.com/fcm/send";
    private static String ANDROID_NOTIFICATION_KEY = "AAAAfcpfe_s:APA91bFBWoYOsgtk1EBU46HNqqEBe-to1E-nximMXfJUpU_9aMlA4LqF02ZQpp2XZD9NdTXKCEAItu9qE6_SZcgZGWmZuIVaj6rSFoX4EN7V96DO2FrjL9qY6pe89u35CiHPbKP64GxH";
    private static String CONTENT_TYPE = "application/json";

    public  static void sendAndroidNotification(String deviceToken,String message,String title)  {
        System.out.print("***************************************************star");
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject obj = new JSONObject();
            JSONObject msgObject = new JSONObject();
            msgObject.put("body", message);
            msgObject.put("title", title);
           // msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
            //   msgObject.put("color", ANDROID_NOTIFICATION_COLOR);
            obj.put("priority", Notification.PRIORITY_HIGH);
            msgObject.put("sound",Notification.DEFAULT_SOUND);

            obj.put("to", deviceToken);
            obj.put("notification", msgObject);
            RequestBody body = RequestBody.create(mediaType, obj.toString());
            Request request = new Request.Builder().url(ANDROID_NOTIFICATION_URL).post(body)
                    .addHeader("content-type", CONTENT_TYPE)
                    .addHeader("authorization", "key=" + ANDROID_NOTIFICATION_KEY).build();

            Response response = client.newCall(request).execute();
        }catch (Exception e){
            System.out.println("notficationError");
            e.printStackTrace();
        }
        System.out.print("***************************************************end");


    }}
