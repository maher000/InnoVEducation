package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 07/04/2017.
 */

public class ClassInfoViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView image_profile ;
    public  TextView tvFullNameSend ;
    public Button btnAccpet,btnIgnore;


    public ClassInfoViewHolder(View view) {
        super(view);

        tvFullNameSend = (TextView) view.findViewById(R.id.tvFullNameClassroomInfo);
        btnAccpet=(Button) view.findViewById(R.id.btnAcceptClassroom);
        btnIgnore=(Button) view.findViewById(R.id.btnIgnoreClassroomInfo);
        image_profile=(CircleImageView) view.findViewById(R.id.image_profile);




    }

}