package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.education.innov.innoveducation.Activities.AddClassRoomActivity;
import com.education.innov.innoveducation.Activities.AddCourssesActivity;
import com.education.innov.innoveducation.Activities.AddHomeWorkActivity;
import com.education.innov.innoveducation.Activities.AddPostActivity;
import com.education.innov.innoveducation.Activities.ClassroomInfoActivity;
import com.education.innov.innoveducation.Adapter.ClassePagerAdapter;
import com.education.innov.innoveducation.Adapter.ViewPagerAdapter;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.gson.Gson;
import com.sa90.materialarcmenu.ArcMenu;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ClasseFragment extends Fragment {


    private String firstname, lastname, id, urlImage;
    private SharedPreferences shared;
    private SharedPreferences sp;
    private ComplexPreferences complexPreferences, complexPreferencesClassRoom;
    private FloatingActionButton btnAddHomeWork, btnAddClassroom, btn_add_post_layout_post, btn_add_course_layout, btn_add_post_teacher;
    private TextView tvName_AdminClassRoom, tvName_ClassRomm;
    private CircleImageView ImgAdminClassRoom;
    private ImageView btnInfo;
    private Teacher teacher;
    private Child child;
    private Parent parent;
    private String Role;
    private ClassRoom classroom;
    private static String json;
    private ArcMenu arcmenu_android_example_layout;

    public ClasseFragment() {

    }

    public static ClasseFragment newInstance(String param1, String param2) {
        ClasseFragment fragment = new ClasseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        shared = getActivity().getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        System.out.println("le role est" + Role);
        complexPreferencesClassRoom = ComplexPreferences.getComplexPreferences(getActivity(), "prefs_classrooms", getActivity().MODE_PRIVATE);
        classroom = complexPreferencesClassRoom.getObject("my_class_room", ClassRoom.class);


        View view = inflater.inflate(R.layout.fragment_classe, container, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.VpPagerClasse);
        pager.setAdapter(buildAdapter());
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.classe_sliding_tabs);
        tabLayout.setupWithViewPager(pager);
        btn_add_post_teacher = (FloatingActionButton) view.findViewById(R.id.btn_add_post_teacher);
        arcmenu_android_example_layout = (ArcMenu) view.findViewById(R.id.arcmenu_android_example_layout);
        btnAddHomeWork = (FloatingActionButton) view.findViewById(R.id.btn_add_home_work_layout_homework);
        btnAddClassroom = (FloatingActionButton) view.findViewById(R.id.btn_add_classroom_layout_homework);
        btn_add_post_layout_post = (FloatingActionButton) view.findViewById(R.id.btn_add_post_layout_post);
        btn_add_course_layout = (FloatingActionButton) view.findViewById(R.id.btn_add_course_layout);
        btnInfo = (ImageView) view.findViewById(R.id.iv_class_room_info);

        tvName_AdminClassRoom = (TextView) view.findViewById(R.id.tvName_AdminClassRoom);
        tvName_ClassRomm = (TextView) view.findViewById(R.id.tvName_ClassRomm);
        ImgAdminClassRoom = (CircleImageView) view.findViewById(R.id.ImgAdminClassRoom);
        if (classroom != null) {
            tvName_AdminClassRoom.setText(classroom.getAuthor());
            tvName_ClassRomm.setText(classroom.getName());
            Picasso.with(getActivity()).load(classroom.getUrlImageAuthor()).into(ImgAdminClassRoom);
        }
        if (Role != null) {
            getInfomationUser();
            switch (Role.trim()) {
                case "parent":
                    btnInfo.setVisibility(View.GONE);
                    btn_add_post_teacher.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addPost();
                        }
                    });
                    arcmenu_android_example_layout.setVisibility(View.GONE);
                    break;
                case "child":
                    btnInfo.setVisibility(View.GONE);
                    btn_add_post_teacher.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addPost();
                        }
                    });
                    arcmenu_android_example_layout.setVisibility(View.GONE);
                    break;
                case "teacher":
                    btn_add_post_teacher.setVisibility(View.GONE);
                    btnAddHomeWork.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addHomeWork();
                        }
                    });
                    btnAddClassroom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addClassroom();
                        }
                    });
                    btn_add_post_layout_post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addPost();
                        }
                    });
                    btn_add_course_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addCoursse();
                        }
                    });
                    btnInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), ClassroomInfoActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(i);
                        }
                    });
                    break;
            }
        }


        return view;
    }

    private PagerAdapter buildAdapter() {
        return (new ClassePagerAdapter(getActivity().getSupportFragmentManager()));
    }

    // add a home work
    private void addHomeWork() {
        startActivity(new Intent(getActivity(), AddHomeWorkActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void addClassroom() {
        startActivity(new Intent(getActivity(), AddClassRoomActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void addPost() {
        startActivity(new Intent(getActivity(), AddPostActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void addCoursse() {
        startActivity(new Intent(getActivity(), AddCourssesActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    public void getInfomationUser() {
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        json = sp.getString("current_user", "");
        if (Role != null) {
            json = sp.getString("current_user", "");
            System.out.println(json + "ffggdd");
            if (json != null) {

                switch (Role.trim()) {
                    case "teacher":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
                        teacher = complexPreferences.getObject("current_user", Teacher.class);
                        firstname = teacher.getFirstName();
                        lastname = teacher.getLastName();
                        urlImage = teacher.getUrlImage();
                        System.out.println(firstname + lastname + urlImage + "syriiine is trying");
                        System.out.println(teacher + "tttttttttttttt");
                        break;
                    case "parent":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
                        parent = complexPreferences.getObject("current_user", Parent.class);
                        firstname = parent.getFirstName();
                        lastname = parent.getLastName();
                        urlImage = parent.getUrlImage();
                        break;
                    case "child":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
                        child = complexPreferences.getObject("current_user", Child.class);
                        System.out.println(child + "ffggdds");
                        firstname = child.getFirstName();
                        lastname = child.getLastName();
                        urlImage = child.getUrlImage();
                        break;
                }
            }
        }
    }

    public void getInfoClass() {

    }
}