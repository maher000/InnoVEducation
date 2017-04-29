
package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Entities.Notification;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Views.CommentsViewHolder;
import com.education.innov.innoveducation.Views.NotificationViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 07/04/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications ){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.notifications=notifications;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View notiftRow = inflater.inflate(R.layout.row_item_notification, parent, false);
        return new NotificationViewHolder(notiftRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);


        NotificationViewHolder mHolder = (NotificationViewHolder) holder;
        mHolder.tvFullName.setText(notifications.get(position).getContenue());
        mHolder.tvDateNotif.setText(notifications.get(position).getDate());
        Picasso.with(context).load(notifications.get(position).getUrlImage()).into(mHolder.image_profile);

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
