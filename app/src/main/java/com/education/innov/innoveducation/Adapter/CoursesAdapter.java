package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Entities.Course;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Views.CourseViewHolder;
import com.education.innov.innoveducation.Views.ImagePostViewHolder;
import com.education.innov.innoveducation.Views.TextPostViewHolder;
import com.education.innov.innoveducation.Views.VideoPostViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 05/04/2017.
 */
public class CoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Course> courses;
    private LayoutInflater inflater;
    private Context context;

    public CoursesAdapter(Context context, ArrayList<Course> courses) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courses = courses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View CoursesRow = inflater.inflate(R.layout.row_item_course, parent, false);
        return new CourseViewHolder(CoursesRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);
        CourseViewHolder mHolder = (CourseViewHolder) holder;
        mHolder.dateCoursses.setText(courses.get(position).getCreationDate());
        mHolder.tvFullNameCourses.setText(courses.get(position).getAuthor());
        mHolder.tvMatiereCourses.setText(courses.get(position).getName());
        Picasso.with(context).load(courses.get(position).getUrlImageAuthor().toString()).into(mHolder.image_profile_courses);


    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
