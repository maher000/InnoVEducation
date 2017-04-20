package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    String id, city, country, lastname, codePostale, Birthday, phone, Education, firstname, email;
    EditText tvCity, tvCountry, tvCodePostal, tvRole, tvClassRoom, tvBirthday, tvPhone, tvEmail, tvEducation, tvLastName, tvFirstName;
    CircleImageView imageProfile;
    Switch SwitchUpdate;
    Teacher teacher;
    Button btnUpdateprofile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(int param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvCity = (EditText) view.findViewById(R.id.tvCity);
        tvCountry = (EditText) view.findViewById(R.id.tvCountry);
        tvCodePostal = (EditText) view.findViewById(R.id.tvCodePostal);
        tvRole = (EditText) view.findViewById(R.id.tvRole);
        tvClassRoom = (EditText) view.findViewById(R.id.tvClassRoom);
        tvBirthday = (EditText) view.findViewById(R.id.tvBirthday);
        tvPhone = (EditText) view.findViewById(R.id.tvPhone);
        tvEmail = (EditText) view.findViewById(R.id.tvEmail);
        tvEducation = (EditText) view.findViewById(R.id.tvEducation);
        imageProfile = (CircleImageView) view.findViewById(R.id.imageProfile);
        tvFirstName = (EditText) view.findViewById(R.id.tvFirstName);
        tvLastName = (EditText) view.findViewById(R.id.tvLastName);
        SwitchUpdate = (Switch) view.findViewById(R.id.SwitchUpdate);
        btnUpdateprofile = (Button) view.findViewById(R.id.btnUpdateprofile);
        btnUpdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfil();

            }
        });
        SwitchUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    tvCity.setEnabled(true);
                    tvCountry.setEnabled(true);
                    tvCodePostal.setEnabled(true);
                    tvRole.setEnabled(true);
                    tvClassRoom.setEnabled(true);
                    tvBirthday.setEnabled(true);
                    tvPhone.setEnabled(true);
                    tvEmail.setEnabled(true);
                    tvEducation.setEnabled(true);
                    btnUpdateprofile.setVisibility(View.VISIBLE);

                } else {
                    //do something when unchecked
                    tvCity.setEnabled(false);
                    tvCountry.setEnabled(false);
                    tvCodePostal.setEnabled(false);
                    tvRole.setEnabled(false);
                    tvClassRoom.setEnabled(false);
                    tvBirthday.setEnabled(false);
                    tvPhone.setEnabled(false);
                    tvEmail.setEnabled(false);
                    tvEducation.setEnabled(false);
                    btnUpdateprofile.setVisibility(View.GONE);
                }
            }
        });
        getInfomationUser();
        return view;
    }

    public void getInfomationUser() {

        SharedPreferences sp = getActivity().getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        String role = sp.getString("role", null);
        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        System.out.println("le role est" + role);
        String json = mPrefs.getString("current_user", "");
        if (role == "child") {
        } else if (role.trim().equals("teacher")) {
            System.out.println("bras bouk");
            teacher = gson.fromJson(json, Teacher.class);
            System.out.println(teacher + "hohougou");
            id = teacher.getIdUser();
            city = "Bizerte";
            country = teacher.getContry();
            lastname = teacher.getLastName();
            codePostale = teacher.getCodePostal();
            phone = teacher.getPhone();
            Education = teacher.getAdresse();
            firstname = teacher.getFirstName();
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            tvCity.setText(city);
            tvCountry.setText(country);
            tvCodePostal.setText(codePostale);
            tvRole.setText("teacher");
            tvClassRoom.setText(teacher.getClassRommId());
            tvEmail.setText(email);
            tvFirstName.setText(firstname);
            tvLastName.setText(lastname);
            tvPhone.setText(phone);
            tvEducation.setText(Education);
            Picasso.with(getActivity()).load(teacher.getUrlImage().toString()).into(imageProfile);
            if (Config.currentTeacher == null)
                Config.currentTeacher = gson.fromJson(json, Teacher.class);
            System.out.println("this is information of user connected" + gson.fromJson(json, Teacher.class));
        } else if (role == "parent") {
            if (Config.currentParent == null)
                Config.currentParent = gson.fromJson(json, Parent.class);
            //   obj = gson.fromJson(json, Parent.class);
            //  System.out.println("this is information of user connected" + obj);
        }
    }

    private void UpdateProfil() {

        city = tvCity.getText().toString();
        country = tvCountry.getText().toString();
        lastname = tvLastName.getText().toString();
        codePostale = tvCodePostal.getText().toString();
        phone = tvPhone.getText().toString();
        Education = tvEducation.getText().toString();
        firstname = tvFirstName.getText().toString();
        teacher.setCodePostal(codePostale);
        teacher.setContry(country);
        teacher.setPhone(phone);
        teacher.setLastName(lastname);
        teacher.setAdresse(Education);
        teacher.setFirstName(firstname);


        FirebaseDatabase.getInstance().getReference().child("teachers").child(id).setValue(teacher).addOnCompleteListener
                (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SwitchUpdate.setChecked(false);
                            tvCity.setEnabled(false);
                            tvCountry.setEnabled(false);
                            tvCodePostal.setEnabled(false);
                            tvRole.setEnabled(false);
                            tvClassRoom.setEnabled(false);
                            tvBirthday.setEnabled(false);
                            tvPhone.setEnabled(false);
                            tvEmail.setEnabled(false);
                            tvEducation.setEnabled(false);
                            btnUpdateprofile.setVisibility(View.GONE);
                        }
                    }
                });


    }


}
