package com.education.innov.innoveducation.Views;

import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.education.innov.innoveducation.R;

import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
/**
 * Created by Syrine on 04/04/2017.
 */
public class VideoPostViewHolder extends RecyclerView.ViewHolder {

   public CircleImageView image_profile_video ;
   public TextView tvFullNameVideo , tvMatiereVideo , tvDateVideo , tvDescriptionVideo , tvDetailVideo ,  tvCommentsVideo  ;
   public JCVideoPlayerStandard PostVideo ;
   public EditText EdtCommentVideo;
   public Button btnAddCommentVideo;
    public CardView mainLayout;

    private TextView headerLabel;

    public VideoPostViewHolder(View view) {


        super(view);


        image_profile_video  = (CircleImageView) view.findViewById(R.id.image_profile_video);
        tvFullNameVideo = ( TextView) view.findViewById(R.id.tvFullNameVideo) ;
        tvMatiereVideo = ( TextView) view.findViewById(R.id.tvMatiereVideo) ;
        tvDateVideo = ( TextView) view.findViewById(R.id.tvDateVideo) ;
        tvDescriptionVideo = ( TextView) view.findViewById(R.id.tvDescriptionVideo) ;
        tvDetailVideo = ( TextView) view.findViewById(R.id.tvDetailVideo) ;
        tvCommentsVideo = ( TextView) view.findViewById(R.id.tvCommentsVideo) ;
        PostVideo = ( JCVideoPlayerStandard) view.findViewById(R.id.videoplayer) ;
        EdtCommentVideo = ( EditText) view.findViewById(R.id.EdtCommentVideo) ;
        btnAddCommentVideo = ( Button) view.findViewById(R.id.btnAddCommentVideo) ;
        mainLayout=(CardView) view.findViewById(R.id.mainLayoutVideo);


    }


}
