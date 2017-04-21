package com.education.innov.innoveducation.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.google.gson.Gson;

/**
 * Created by maher on 20/04/2017.
 */

public  class  MyApp extends Application {
    private static Activity ctx;
    private static MyApp mInstance;
    public static Teacher teacher;
    public static Parent parent;
    public static Child child;
    public static String role;
    private static SharedPreferences sp;
    private static SharedPreferences mPrefs;
    private static Gson gson = new Gson();
    private static String json;

    MyApp(){

    }

    private  MyApp(Activity ctx){
        this.ctx=ctx;
        if(ctx!=null) {
            sp = ctx.getSharedPreferences("role_user", Activity.MODE_PRIVATE);
            mPrefs = ctx.getPreferences(Context.MODE_PRIVATE);
            role = sp.getString("role", null);
            json = mPrefs.getString("current_user", "");
            if (role != null) {
                json = mPrefs.getString("current_user", "");
                if (json != null) {

                    switch (role.trim()) {
                        case "teacher":
                            teacher = gson.fromJson(json, Teacher.class);
                            System.out.println(teacher + "tttttttttttttt");
                            break;
                        case "parent":
                            parent = gson.fromJson(json, Parent.class);
                            break;
                        case "child":
                            child = gson.fromJson(json, Child.class);
                            break;
                    }
                }
            }
        }


    }
    public static synchronized MyApp getInstance(Activity context) {

        if (teacher==null && child==null&&parent==null) {
            mInstance = new MyApp(context);

        }
        return mInstance;
    }




}
