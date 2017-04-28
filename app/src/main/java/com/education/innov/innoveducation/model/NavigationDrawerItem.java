package com.education.innov.innoveducation.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;


import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerItem {

    private String title;
    private int imageId;
    private static String role_user;
    private static SharedPreferences shared;
    private static Context ctx;

    public static List<NavigationDrawerItem> getData(String Role) {
        role_user = Role ;
        List<NavigationDrawerItem> dataList = new ArrayList<>();

        int[] imageIds = getImages();
        String[] titles = getTitles();

        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setImageId(imageIds[i]);
            dataList.add(navItem);
        }
        return dataList;
    }

    private static int[] getImages() {

        return new int[]{
                R.drawable.ic_action_name,
                R.drawable.ic_action_name,
                R.drawable.ic_action_name,
                R.drawable.ic_action_name,
                R.drawable.ic_action_name,
                R.drawable.ic_action_name,
                R.drawable.ic_action_name,
                R.drawable.ic_action_name};
    }

    private static String[] getTitles() {
        if (role_user.trim().equals("parent")) {
            return new String[]{
                    "Add Child", "Associations", "Evénements à proximité", "Contacter nous", "Profile", "Log out"
            };
        } else
            return new String[]{
                    "Associations", "Evénements à proximité", "Contacter nous", "Profile", "Log out"
            };


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


}
