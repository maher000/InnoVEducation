package com.education.innov.innoveducation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.ClassroomRequest;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 05/04/2017.
 */
public class SearchClassRoomAdapter extends RecyclerView.Adapter<SearchClassRoomAdapter.MyViewHolder2> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ClassRoom> classRooms;

    private String Role;
    private Child child;
    private Teacher teacher;
    private SharedPreferences shared;
    private ComplexPreferences complexPreferences;
    private String author;
    private String urlImageAuthor;
    private DatabaseReference mDBase = Config.mDatabase;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public SearchClassRoomAdapter(Context context, ArrayList<ClassRoom> classRooms) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.classRooms = classRooms;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = inflater.inflate(R.layout.row_item_class_room_search, parent, false);
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
        Button btnJoin;
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
            btnJoin = (Button) itemView.findViewById(R.id.btnJoinSearch);
            if (MyApp.child != null) {
                if (MyApp.child.getClassRommId().equals("NONE"))
                    btnJoin.setVisibility(View.GONE);
            }
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
            btnJoin.setOnClickListener(MyViewHolder2.this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnJoinSearch:
                    join();
                    break;

                //case R.id.img_row_add:
                //  addItem(position, current);
                //    break;
            }
        }

        private void join() {

            getInfomationUser();
            //   btnJoin.setClickable(false);
            btnJoin.setText("request sent");
            ClassroomRequest req = new ClassroomRequest();
            req.setAdminClassroomId(current.getIdAdminstrator());
            req.setClassroomId(current.getId());
            req.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            req.setSenderName(author);
            req.setUrlImgSender(urlImageAuthor);
            req.setSenderType(Role);

            Date date = new Date();
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-mm-yyyy hh:mm");
            String dateS = simpleDate.format(date);
            req.setDate(dateS);

            req.setClassroomName(current.getName());

            String id = current.getId() + FirebaseAuth.getInstance().getCurrentUser().getUid();
            req.setId(id);

            mDBase.child("classroomRequest").child(id).setValue(req).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    } else {
                        System.out.println("error" + task.getException().getMessage());
                    }

                }
            });


        }

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
                        author = teacher.getFirstName() + " " + teacher.getLastName();
                        urlImageAuthor = teacher.getUrlImage();
                    }
                    System.out.println(teacher + "tttttttttttttt");
                    break;
                case "child":
                    complexPreferences = ComplexPreferences.getComplexPreferences(context, "mypref", context.MODE_PRIVATE);
                    child = complexPreferences.getObject("current_user", Child.class);
                    if (child != null) {
                        author = child.getFirstName() + " " + child.getLastName();
                        urlImageAuthor = child.getUrlImage();
                    }
                    break;
            }
        }
    }
}
