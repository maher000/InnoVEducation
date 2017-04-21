package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Views.CourseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.CRC32;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 05/04/2017.
 */
public class MyClassroomsAdapter extends RecyclerView.Adapter<MyClassroomsAdapter.MyViewHolder2> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ClassRoom> classRooms;

    public MyClassroomsAdapter(Context context, ArrayList<ClassRoom> classRooms) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.classRooms = classRooms;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = inflater.inflate(R.layout.row_item_class_room, parent, false);
        MyViewHolder2 holder = new MyViewHolder2(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position) {

        final int itemType = getItemViewType(position);
        ClassRoom current = classRooms.get(position);
        holder.setData(current, position);
        holder.setListeners();

    }

    @Override
    public int getItemCount() {
        return classRooms.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvResponsable, tvCountry, tvCreatedAt, tvInstitut;
        CircleImageView imgProfile;
        int position;
        ClassRoom current;

        public MyViewHolder2(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvClass_room_Name);
            imgProfile = (CircleImageView) itemView.findViewById(R.id.image_profile_text);
            tvInstitut = (TextView) itemView.findViewById(R.id.tvInstitut);
            tvResponsable = (TextView) itemView.findViewById(R.id.tv_responsable);
            tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvDateText);
        }

        public void setData(ClassRoom current, int position) {
            this.tvName.setText(current.getName());
            this.tvCreatedAt.setText(current.getCreationDate());
            this.tvResponsable.setText(current.getAuthor());
            this.tvCountry.setText(current.getCountry());
            this.tvInstitut.setText(current.getAdress());//institut
            this.position = position;
            this.current = current;
            String source = current.getUrlImageAuthor();
            Picasso.with(context).load(source).into(imgProfile);
        }

        public void setListeners() {
            //imgDelete.setOnClickListener(MyViewHolder2.this);
            //imgAdd.setOnClickListener(MyViewHolder2.this);
        }


        @Override
        public void onClick(View v) {
          /*  switch (v.getId()) {
                case R.id.img_row_delete:
                     removeItem(position);
                    break;

                case R.id.img_row_add:
                    //  addItem(position, current);
                    break;
            }*/
        }

    }
}
