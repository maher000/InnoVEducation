package com.education.innov.innoveducation.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.education.innov.innoveducation.Fragment.ClassCoursesFragment;
import com.education.innov.innoveducation.Fragment.HomeworksFragment;
import com.education.innov.innoveducation.Fragment.ClassHomeFragment;

/**
 * Created by Syrine on 03/04/2017.
 */
public class ClassePagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 3;

    private FragmentManager mFragmentManager;

    public ClassePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragmentManager = fragmentManager;

    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return ClassHomeFragment.newInstance(0, "Home");
            case 1:
                return ClassCoursesFragment.newInstance(1, "Courses");

            case 2 :
                return  HomeworksFragment.newInstance(2,"Homeworks");
            default:
                return null;
        }
    }
    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Courses";
            case 2 :
                return "Homeworks";
        }
        return "default";
    }

}