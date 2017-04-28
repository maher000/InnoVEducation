package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 07/04/2017.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView tvFullName , tvDateNotif;
    public CircleImageView image_profile;


    public NotificationViewHolder(View view) {
        super(view);

        tvFullName = (TextView) view.findViewById(R.id.tvFullNameNotification);
        tvDateNotif = (TextView) view.findViewById(R.id.tvDateNotification);
        image_profile=(CircleImageView) view.findViewById(R.id.image_profile);




    }

}