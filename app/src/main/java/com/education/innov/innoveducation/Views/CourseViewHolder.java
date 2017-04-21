package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 05/04/2017.
 */
public class CourseViewHolder extends RecyclerView.ViewHolder {


    public CircleImageView image_profile_courses;
    public TextView tvFullNameCourses, tvMatiereCourses, dateCoursses;

    public CourseViewHolder(View view) {
        super(view);
        image_profile_courses = (CircleImageView) view.findViewById(R.id.image_profile_courses);
        tvFullNameCourses = (TextView) view.findViewById(R.id.tvFullNameCourses);
        tvMatiereCourses = (TextView) view.findViewById(R.id.tvMatiereCourses);
        dateCoursses = (TextView) view.findViewById(R.id.tvdateCoursses);


    }

}