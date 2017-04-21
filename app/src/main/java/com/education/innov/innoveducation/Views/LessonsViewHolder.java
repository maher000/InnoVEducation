package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.R;


/**
 * Created by Syrine on 21/04/2017.
 */

public class LessonsViewHolder extends RecyclerView.ViewHolder {

    public ImageView miniature_video;
    public TextView tvNameVideo, tvDateVideo ;


    public LessonsViewHolder(View view) {
        super(view);

        miniature_video = (ImageView) view.findViewById(R.id.miniature_video);
        tvDateVideo = (TextView) view.findViewById(R.id.tvDateVideo);
        tvNameVideo = (TextView) view.findViewById(R.id.tvNameVideo);


    }

}

