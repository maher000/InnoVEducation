package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.education.innov.innoveducation.Activities.CompleteInformationUserActivity;
import com.education.innov.innoveducation.Activities.HomeActivity;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Views.ImagePostViewHolder;
import com.education.innov.innoveducation.Views.TextPostViewHolder;
import com.education.innov.innoveducation.Views.VideoPostViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    ArrayList<post> posts;
    User user;
    DatabaseReference mDBase = Config.mDatabase;

    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_HEADER = 1;
    public static final int ITEM_TYPE_Text = 2;

    public HomeAdapter(ArrayList<post> posts, Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_NORMAL) {
            View ImageRow = inflater.inflate(R.layout.row_item_image, parent, false);
            return new ImagePostViewHolder(ImageRow); // view holder for normal items
        } else if (viewType == ITEM_TYPE_HEADER) {
            View VideoRow = inflater.inflate(R.layout.row_item_video, parent, false);
            return new VideoPostViewHolder(VideoRow); // view holder for header items
        } else if (viewType == ITEM_TYPE_Text) {
            View TexteRow = inflater.inflate(R.layout.row_item_text, parent, false);
            return new TextPostViewHolder(TexteRow); // view holder for header items
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final int itemType = getItemViewType(position);

        String id_user = posts.get(position).getUserId().toString();
        mDBase.child("user").child(id_user).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            posts.get(position).setOwner(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );

        if (itemType == ITEM_TYPE_NORMAL) {
            ImagePostViewHolder mHolder = (ImagePostViewHolder) holder;
            mHolder.tvDescriptionImage.setText(posts.get(position).getDescription().toString());
//            mHolder.tvFullNameImage.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
//            mHolder.tvMatiereImage.setText(posts.get(position).getSubject().toString());
            Picasso.with(context).load(posts.get(position).getUrlFile().toString()).into(mHolder.image_post);
            //          Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_image);
        } else if (itemType == ITEM_TYPE_HEADER) {
            VideoPostViewHolder mHolder = (VideoPostViewHolder) holder;
            mHolder.tvDescriptionVideo.setText(posts.get(position).getDescription().toString());
            //       mHolder.tvFullNameVideo.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
            //       mHolder.tvMatiereVideo.setText(posts.get(position).getSubject().toString());
            //       mHolder.PostVideo.setVideoPath(posts.get(position).getSubject().toString());
            //     Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_video);

        } else if (itemType == ITEM_TYPE_Text) {
            TextPostViewHolder mHolder = (TextPostViewHolder) holder;
            mHolder.tvDescriptionText.setText(posts.get(position).getDescription().toString());
            //   mHolder.tvFullNameText.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
            //  mHolder.tvMatiereText.setText(posts.get(position).getSubject().toString());
            //  Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_text);

        }
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("syriiiiine" + (posts.get(position).getType().equals("Texte")));
        if (posts.get(position).getType().equals("image")) {
            return ITEM_TYPE_NORMAL;
        } else if (posts.get(position).getType().equals("video")) {
            return ITEM_TYPE_HEADER;
        } else if (posts.get(position).getType().equals("texte")) {
            return ITEM_TYPE_Text;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}