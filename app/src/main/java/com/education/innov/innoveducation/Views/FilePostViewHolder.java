package com.education.innov.innoveducation.Views;

import android.support.v7.widget.CardView;
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
public class FilePostViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView image_profile_file;
    public TextView tvFullNameFile,tvMatiereFile , tvDateFile ,
            tvDescriptionFile ,tvDetailFile , tvCommentsFile ,tvNameFile ;
    public EditText EdtCommentFile ;
    public Button btnAddCommentFile ;
    public CardView downladFile;

    public FilePostViewHolder(View view) {
        super(view);
        image_profile_file = (CircleImageView) view.findViewById(R.id.image_profile_File);
        tvFullNameFile = (TextView) view.findViewById(R.id.tvFullNameFile) ;
        tvNameFile = (TextView) view.findViewById(R.id.tvFileName) ;
        tvMatiereFile = (TextView) view.findViewById(R.id.tvMatiereFile) ;
        tvDateFile = (TextView) view.findViewById(R.id.tvDateFile) ;
        tvDescriptionFile = (TextView) view.findViewById(R.id.tvDescriptionFile) ;
        tvDetailFile = (TextView) view.findViewById(R.id.tvDetailFile) ;
        tvCommentsFile = (TextView) view.findViewById(R.id.tvCommentsFile) ;
        EdtCommentFile = (EditText) view.findViewById(R.id.EdtCommentFile) ;
        btnAddCommentFile = (Button) view.findViewById(R.id.btnAddCommentFile) ;
        downladFile=(CardView) view.findViewById(R.id.file_name_download);





    }

}