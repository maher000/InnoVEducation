package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Entities.Course;
import com.education.innov.innoveducation.Entities.Lesson;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Views.CourseViewHolder;
import com.education.innov.innoveducation.Views.LessonsViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 21/04/2017.
 */

public class LessonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Lesson> lessons;
    private LayoutInflater inflater;
    private Context context;

    public LessonsAdapter(Context context, ArrayList<Lesson> lessons) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.lessons = lessons;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View LessonsRow = inflater.inflate(R.layout.row_item_lesson, parent, false);
        return new LessonsViewHolder(LessonsRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);
        LessonsViewHolder mHolder = (LessonsViewHolder) holder;
        mHolder.tvDateVideo.setText(lessons.get(position).getDateCreation());
        mHolder.tvNameVideo.setText(lessons.get(position).getTitle());
        Picasso.with(context).load(lessons.get(position).getUrlMiniature().toString()).into(mHolder.miniature_video);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }
}
