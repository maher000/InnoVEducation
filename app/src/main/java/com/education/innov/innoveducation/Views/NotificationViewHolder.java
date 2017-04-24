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
CircleImageView image_profile ;
    TextView tvFullNameComment , tvDateNotif;


    public NotificationViewHolder(View view) {
        super(view);

        tvFullNameComment = (TextView) view.findViewById(R.id.tvFullNameNotification);
        tvDateNotif = (TextView) view.findViewById(R.id.tvDateNotification);




    }

}