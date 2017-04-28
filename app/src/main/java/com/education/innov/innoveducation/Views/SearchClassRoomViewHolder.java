package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 28/04/2017.
 */

public class SearchClassRoomViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName, tvResponsable, tvCountry, tvCreatedAt, tvInstitut;
    public Button btnJoin;
    public CircleImageView imgProfile;

    public SearchClassRoomViewHolder(View view) {
        super(view);

        tvName = (TextView) view.findViewById(R.id.tvClass_room_Name);
        imgProfile = (CircleImageView) view.findViewById(R.id.image_profile_text);
        tvInstitut = (TextView) view.findViewById(R.id.tvInstitut);
        tvResponsable = (TextView) view.findViewById(R.id.tv_responsable);
        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        tvCreatedAt = (TextView) view.findViewById(R.id.tvDateText);
        btnJoin = (Button) view.findViewById(R.id.btnJoinSearch);

    }

}
