package com.education.innov.innoveducation.Utils;

import android.content.Context;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Syrine on 11/04/2017.
 */

public class Config {


    public static  DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference("images");
    public static String CHILD_CLASSROOM="classrooms";
    public static String CHILD_MESSAGE="message";
    public static String CHILD_TEACHER="teachers";
    public static String CHILD_TOKEN="Tokens";
    public static String CHILD_PARENT="parents";
    public static String CHILD_STUDENT="students";
    public static String CHILD_CHILD="child";






}
