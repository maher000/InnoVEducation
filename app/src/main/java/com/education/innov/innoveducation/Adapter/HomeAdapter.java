package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.MediaController;

import com.education.innov.innoveducation.Activities.CommentsActivity;
import com.education.innov.innoveducation.Activities.CompleteInformationUserActivity;
import com.education.innov.innoveducation.Activities.CourseActivity;
import com.education.innov.innoveducation.Activities.HomeActivity;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Views.FilePostViewHolder;
import com.education.innov.innoveducation.Views.ImagePostViewHolder;
import com.education.innov.innoveducation.Views.TextPostViewHolder;
import com.education.innov.innoveducation.Views.VideoPostViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    ArrayList<post> posts;
    User user;
    Teacher teacher;
    String id;
    Child child;
    Parent parent;
    int i;
    DatabaseReference mDBase = Config.mDatabase;
   // private List<Teacher> users = new ArrayList<>();
    private List<User> list_final = new ArrayList<>();




    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_HEADER = 1;
    public static final int ITEM_TYPE_Text = 2;
    public static final int ITEM_TYPE_FILE = 3;

    public HomeAdapter(ArrayList<post> posts,ArrayList<Teacher> teachers, Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.posts = posts;
       // this.users=teachers;
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
        } else if (viewType == ITEM_TYPE_FILE) {
            View FileRow = inflater.inflate(R.layout.row_item_file, parent, false);
            return new FilePostViewHolder(FileRow); // view holder for header items
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final int itemType = getItemViewType(position);



        if (itemType == ITEM_TYPE_NORMAL) {
            ImagePostViewHolder mHolder = (ImagePostViewHolder) holder;
            mHolder.tvDescriptionImage.setText(posts.get(position).getDescription().toString());
               mHolder.tvFullNameImage.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
            mHolder.tvMatiereImage.setText(posts.get(position).getSubject().toString());
            Picasso.with(context).load(posts.get(position).getUrlFile().toString()).into(mHolder.image_post);
               Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_image);
            mHolder.tvCommentsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    String idHomeWork = "1";
                    intent.putExtra("idhommeWork", idHomeWork);
                    context.startActivity(intent);
                }
            });
        } else if (itemType == ITEM_TYPE_HEADER) {

            VideoPostViewHolder mHolder = (VideoPostViewHolder) holder;
            mHolder.tvDescriptionVideo.setText(posts.get(position).getDescription().toString());
                mHolder.tvFullNameVideo.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
            mHolder.tvMatiereVideo.setText(posts.get(position).getSubject().toString());
                Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_video);
            mHolder.tvCommentsVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    String idHomeWork = "1";
                    intent.putExtra("idhommeWork", idHomeWork);
                    context.startActivity(intent);
                }
            });
            try {
                mHolder.PostVideo.setUp(posts.get(position).getUrlFile().toString()
                        , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "video name");


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        } else if (itemType == ITEM_TYPE_Text) {
            TextPostViewHolder mHolder = (TextPostViewHolder) holder;
            mHolder.tvDescriptionText.setText(posts.get(position).getDescription().toString());
                mHolder.tvFullNameText.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
            mHolder.tvMatiereText.setText(posts.get(position).getSubject().toString());
             Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_text);
            mHolder.tvCommentsText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    String idHomeWork = "1";
                    intent.putExtra("idhommeWork", idHomeWork);
                    context.startActivity(intent);

                }
            });
        } else if (itemType == ITEM_TYPE_FILE) {
            FilePostViewHolder mHolder = (FilePostViewHolder) holder;
            mHolder.tvDescriptionFile.setText(posts.get(position).getDescription().toString());
            mHolder.tvFullNameFile.setText(posts.get(position).getOwner().getFirstName().toString() + " " + posts.get(position).getOwner().getLastName().toString());
            mHolder.tvMatiereFile.setText(posts.get(position).getSubject().toString());
            Picasso.with(context).load(posts.get(position).getOwner().getUrlImage().toString()).into(mHolder.image_profile_file);
            mHolder.tvCommentsFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    String idHomeWork = "1";
                    intent.putExtra("idhommeWork", idHomeWork);
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (posts.get(position).getType().equals("image")) {
            return ITEM_TYPE_NORMAL;
        } else if (posts.get(position).getType().equals("video")) {
            return ITEM_TYPE_HEADER;
        } else if (posts.get(position).getType().equals("text")) {
            return ITEM_TYPE_Text;
        } else if (posts.get(position).getType().equals("file")) {
            return ITEM_TYPE_FILE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}