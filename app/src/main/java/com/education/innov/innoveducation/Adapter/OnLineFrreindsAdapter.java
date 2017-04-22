package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Views.FreindsOnlineViewHolder;
import com.education.innov.innoveducation.Views.HomeWorkViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 08/04/2017.
 */

public class OnLineFrreindsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<User> users;

    private LayoutInflater inflater;
    private Context context;

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


        FreindsOnlineViewHolder mHolder = (FreindsOnlineViewHolder) holder;

       mHolder.tvFullNameOnline.setText(users.get(position).getFirstName() + " " + users.get(position).getLastName());
        Picasso.with(context).load(users.get(position).getUrlImage()).into(mHolder.profile_image_online);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
