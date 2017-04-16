package com.education.innov.innoveducation.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 04/04/2017.
 */
public class TextPostViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView image_profile_text;
    public TextView tvFullNameText, tvMatiereText, tvDateText, tvDescriptionText,
            tvDetailText, tvCommentsText;
    public EditText EdtCommentText;
    public Button btnAddCommentText;


    public TextPostViewHolder(View view) {
        super(view);

        image_profile_text = (CircleImageView) view.findViewById(R.id.image_profile_text);
        tvFullNameText = (TextView) view.findViewById(R.id.tvFullNameText);
        tvMatiereText = (TextView) view.findViewById(R.id.tvMatiereText);
        tvDateText = (TextView) view.findViewById(R.id.tvDateText);
        tvDescriptionText = (TextView) view.findViewById(R.id.tvDescriptionText);
        tvDetailText = (TextView) view.findViewById(R.id.tvDetailText);
        tvCommentsText = (TextView) view.findViewById(R.id.tvCommentsText);
        EdtCommentText = (EditText) view.findViewById(R.id.EdtCommentText);
        btnAddCommentText = (Button) view.findViewById(R.id.btnAddCommentText);


    }

}

