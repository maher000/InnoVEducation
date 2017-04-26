package com.education.innov.innoveducation.Views;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 04/04/2017.
 */
public class ImagePostViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView image_profile_image;
    public TextView tvFullNameImage, tvMatiereImage, tvDateImage, tvDescriptionImage,
            tvDetailImage, tvCommentsImage;

    public EditText EdtCommentImage;
    public Button btnAddCommentImage;
    public ImageView image_post;
    public CardView mainLayout;

    public ImagePostViewHolder(View view) {
        super(view);

        image_profile_image = (CircleImageView) view.findViewById(R.id.image_profile_image);
        tvFullNameImage = (TextView) view.findViewById(R.id.tvFullNameImage);
        tvMatiereImage = (TextView) view.findViewById(R.id.tvMatiereImage);
        tvDateImage = (TextView) view.findViewById(R.id.tvDateImage);
        tvDescriptionImage  = (TextView) view.findViewById(R.id.tvDescriptionImage);
        tvDetailImage = (TextView) view.findViewById(R.id.tvDetailImage);
        tvCommentsImage = (TextView) view.findViewById(R.id.tvCommentsImage);
        EdtCommentImage = (EditText) view.findViewById(R.id.EdtCommentImage);
        btnAddCommentImage = (Button) view.findViewById(R.id.btnAddCommentImage);
        image_post = (ImageView) view.findViewById(R.id.image_post);
        mainLayout=(CardView) view.findViewById(R.id.mainLayout);


    }

}

