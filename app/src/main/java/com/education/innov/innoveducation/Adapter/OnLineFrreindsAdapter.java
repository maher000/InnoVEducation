package com.education.innov.innoveducation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Views.FreindsOnlineViewHolder;
import com.education.innov.innoveducation.Views.HomeWorkViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 08/04/2017.
 */

public class OnLineFrreindsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<User> users;

    private LayoutInflater inflater;
    private Context context;
    private String Role ,id;
    private Child child;
    private Teacher teacher;
    private Parent parent ;
    private SharedPreferences shared;
    private ComplexPreferences complexPreferences;

    public OnLineFrreindsAdapter(Context context, ArrayList<User> users) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View FreindRow = inflater.inflate(R.layout.row_item_freind_online, parent, false);
        return new FreindsOnlineViewHolder(FreindRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);
      //  getInfomationUser();

        FreindsOnlineViewHolder mHolder = (FreindsOnlineViewHolder) holder;

        mHolder.tvFullNameOnline.setText(users.get(position).getFirstName() + " " + users.get(position).getLastName());
        Picasso.with(context).load(users.get(position).getUrlImage()).into(mHolder.profile_image_online);
        if(users.get(position).getConnected().trim().equals("no")){
            mHolder.icon_online.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public void getInfomationUser() {

        shared = context.getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {

            switch (Role.trim()) {
                case "teacher":
                    complexPreferences = ComplexPreferences.getComplexPreferences(context, "mypref", context.MODE_PRIVATE);
                    teacher = complexPreferences.getObject("current_user", Teacher.class);
                    if (teacher != null) {
                        FirebaseDatabase.getInstance().getReference().child("teachers").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                child("active_class_room").addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );
                    }
                    System.out.println(teacher + "tttttttttttttt");
                    break;
     /*           case "child":
                    complexPreferences = ComplexPreferences.getComplexPreferences(context, "mypref", context.MODE_PRIVATE);
                    child = complexPreferences.getObject("current_user", Child.class);
                    if (child != null) {
                        FirebaseDatabase.getInstance().getReference().child("child").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                child("classRommId").addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                    }
                    break; */
                case "parent":
                    complexPreferences = ComplexPreferences.getComplexPreferences(context, "mypref", context.MODE_PRIVATE);
                    parent = complexPreferences.getObject("current_user", Parent.class);
                    if (child != null) {
                        FirebaseDatabase.getInstance().getReference().child("child").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                child("classRommId").addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                    }
                    break;
            }
        }
    }

}

