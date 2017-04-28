package com.education.innov.innoveducation.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Course;
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
    public static String activeClassroom;
    public static Course course=null;

    public MyApp(){

    }

    private  MyApp(Activity ctx){
        this.ctx=ctx;

    }
    public static synchronized MyApp getInstance(Activity context) {

        if (teacher==null && child==null&&parent==null) {
            mInstance = new MyApp(context);

        }
        return mInstance;
    }




}
