package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 07/04/2017.
 */

public class CommentsViewHolder extends RecyclerView.ViewHolder {
CircleImageView image_profile ;
    TextView tvFullNameComment , tvDateComment , tvObjectComment ;


    public CommentsViewHolder(View view) {
        super(view);

        tvFullNameComment = (TextView) view.findViewById(R.id.tvFullNameComment);
        tvDateComment = (TextView) view.findViewById(R.id.tvDateComment);
        tvObjectComment = (TextView) view.findViewById(R.id.tvObjectComment);



    }

}