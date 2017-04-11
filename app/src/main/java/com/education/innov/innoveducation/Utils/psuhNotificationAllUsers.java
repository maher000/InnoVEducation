package com.education.innov.innoveducation.Utils;


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
    private static String ANDROID_NOTIFICATION_KEY = "AAAAt5DLutc:APA91bFlH4ZExP08sBfh_Ci8bSZ53NGZcgb-MT_KHAD0KC1q8qgFtXdzv6_m7w1FQC-yk8nGlWRxmCDOd6FrY6u9YavBa2ASWrLFx94kDpEYBxGXAwmHXEUMnMzrfMCyyf-5UElPg20M";
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
            //    msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
            //   msgObject.put("color", ANDROID_NOTIFICATION_COLOR);

            obj.put("to", deviceToken);
            obj.put("notification", msgObject);

            RequestBody body = RequestBody.create(mediaType, obj.toString());
            Request request = new Request.Builder().url(ANDROID_NOTIFICATION_URL).post(body)
                    .addHeader("content-type", CONTENT_TYPE)
                    .addHeader("authorization", "key=" + ANDROID_NOTIFICATION_KEY).build();

            Response response = client.newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.print("***************************************************end");


    }}