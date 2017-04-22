package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 08/04/2017.
 */

public class FreindsOnlineViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView profile_image_online;
    public TextView tvFullNameOnline;
    public ImageView icon_online;


    public FreindsOnlineViewHolder(View view) {
        super(view);

        profile_image_online = (CircleImageView) view.findViewById(R.id.profile_image_online);
        tvFullNameOnline = (TextView) view.findViewById(R.id.tvFullNameOnline);
        icon_online = (ImageView) view.findViewById(R.id.icon_online);


    }

}