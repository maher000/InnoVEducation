package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 05/04/2017.
 */
public class HomeWorkViewHolder extends RecyclerView.ViewHolder {

    public TextView TvComment;
    public CircleImageView image_profile_Homework;
    public TextView tvFullNameHomework, tvMatiereHomework, tvdatestartHomework,
            tvDateEndHomework, tvDescriptionHomework, tvDetailHomework;
    public EditText EdtCommentHomework;
    public Button btnAddCommentHomewotk;

    public HomeWorkViewHolder(View view) {
        super(view);
        TvComment = (TextView) view.findViewById(R.id.tvCommentHomework);
        tvFullNameHomework = (TextView) view.findViewById(R.id.tvFullNameHomework);
        tvMatiereHomework = (TextView) view.findViewById(R.id.tvMatiereHomework);
        tvdatestartHomework = (TextView) view.findViewById(R.id.tvdatestartHomework);
        tvDateEndHomework = (TextView) view.findViewById(R.id.tvDateEndHomework);
        tvDescriptionHomework = (TextView) view.findViewById(R.id.tvDescriptionHomework);
        tvDetailHomework = (TextView) view.findViewById(R.id.tvDetailHomework);
        EdtCommentHomework = (EditText) view.findViewById(R.id.EdtCommentHomework);
        btnAddCommentHomewotk = (Button) view.findViewById(R.id.btnAddCommentHomewotk);
        image_profile_Homework = (CircleImageView) view.findViewById(R.id.image_profile_Homework);


    }

}