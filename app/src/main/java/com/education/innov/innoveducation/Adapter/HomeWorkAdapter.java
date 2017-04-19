package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Activities.CommentsActivity;
import com.education.innov.innoveducation.Entities.HomeWork;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Views.CourseViewHolder;
import com.education.innov.innoveducation.Views.HomeWorkViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 05/04/2017.
 */
public class HomeWorkAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    ArrayList<HomeWork> homeWorks ;

    public HomeWorkAdapter(Context context, ArrayList<HomeWork> homeWorks) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.homeWorks=homeWorks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View HomeWorkRow = inflater.inflate(R.layout.row_item_homework, parent, false);
        return new HomeWorkViewHolder(HomeWorkRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

      //  final int itemType = getItemViewType(position);


        HomeWorkViewHolder mHolder = (HomeWorkViewHolder) holder;
        mHolder.tvDateEndHomework.setText(homeWorks.get(position).getEndDate());
        mHolder.tvdatestartHomework.setText(homeWorks.get(position).getStartDate());
        mHolder.tvFullNameHomework.setText(homeWorks.get(position).getAuthor());
        mHolder.tvDescriptionHomework.setText(homeWorks.get(position).getDescription());
        mHolder.tvMatiereHomework.setText(homeWorks.get(position).getSubject());
        Picasso.with(context).load(homeWorks.get(position).getUrlImageAuthor().toString()).into(mHolder.image_profile_Homework);
        mHolder.TvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class) ;
                String idHomeWork = homeWorks.get(position).getId();
                intent.putExtra("idhommeWork", idHomeWork);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return homeWorks.size();
    }
}
