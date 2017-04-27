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
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private String id, city, country, lastname, codePostale, Birthday, phone, Education, firstname, email;
    private EditText tvCity, tvCountry, tvCodePostal, tvBirthday, tvPhone, tvEmail, tvEducation, tvLastName, tvFirstName;
    private CircleImageView imageProfile;
    private Switch SwitchUpdate;
    private Teacher teacher;
    private Button btnUpdateprofile;
    private SharedPreferences shared;
    private SharedPreferences sp;
    private ComplexPreferences complexPreferences;
    private String Role;
    private Child child;
    private Parent parent;

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
        shared = getActivity().getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        tvCity = (EditText) view.findViewById(R.id.tvCity);
        tvCountry = (EditText) view.findViewById(R.id.tvCountry);
        tvCodePostal = (EditText) view.findViewById(R.id.tvCodePostal);
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
                    tvBirthday.setEnabled(false);
                    tvPhone.setEnabled(false);
                    tvEmail.setEnabled(false);
                    tvEducation.setEnabled(false);
                    btnUpdateprofile.setVisibility(View.GONE);
                }
            }
        });
        if (Role != null) {
            getInfomationUser();
        }


        return view;
    }

    private void UpdateProfil() {

        if (Role != null) {
            System.out.println("profileRole" + Role);
            switch (Role.trim()) {
                case "teacher":
                    teacher.setCodePostal(tvCodePostal.getText().toString());
                    teacher.setContry(tvCountry.getText().toString());
                    teacher.setBirthday(tvBirthday.getText().toString());
                    teacher.setCity(tvCity.getText().toString());
                    teacher.setPhone(tvPhone.getText().toString());
                    teacher.setLastName(tvLastName.getText().toString());
                    teacher.setAdresse(tvEducation.getText().toString());
                    teacher.setFirstName(tvFirstName.getText().toString());
                    if (id != null)
                        FirebaseDatabase.getInstance().getReference().child("teachers").child(id).setValue(teacher).addOnCompleteListener
                                (new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SwitchUpdate.setChecked(false);
                                            tvCity.setEnabled(false);
                                            tvCountry.setEnabled(false);
                                            tvCodePostal.setEnabled(false);
                                            tvBirthday.setEnabled(false);
                                            tvPhone.setEnabled(false);
                                            tvEmail.setEnabled(false);
                                            tvEducation.setEnabled(false);
                                            btnUpdateprofile.setVisibility(View.GONE);
                                            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                                    getActivity(), "mypref", Context.MODE_PRIVATE);
                                            complexPreferences.putObject("current_user", teacher);
                                            complexPreferences.commit();
                                        }
                                    }
                                });
                    break;
                case "parent":
                    parent.setCodePostal(tvCodePostal.getText().toString());
                    parent.setContry(tvCountry.getText().toString());
                    parent.setCity(tvCity.getText().toString());
                    parent.setPhone(tvPhone.getText().toString());
                    parent.setLastName(tvLastName.getText().toString());
                    parent.setAdresse(tvEducation.getText().toString());
                    parent.setFirstName(tvFirstName.getText().toString());
                    parent.setBirthday(tvBirthday.getText().toString());
                    if (id != null)
                        FirebaseDatabase.getInstance().getReference().child("parents").child(id).setValue(parent).addOnCompleteListener
                                (new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SwitchUpdate.setChecked(false);
                                            tvCity.setEnabled(false);
                                            tvCountry.setEnabled(false);
                                            tvCodePostal.setEnabled(false);
                                            tvBirthday.setEnabled(false);
                                            tvPhone.setEnabled(false);
                                            tvEmail.setEnabled(false);
                                            tvEducation.setEnabled(false);
                                            btnUpdateprofile.setVisibility(View.GONE);
                                            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                                    getActivity(), "mypref", Context.MODE_PRIVATE);
                                            complexPreferences.putObject("current_user", parent);
                                            complexPreferences.commit();
                                        }
                                    }
                                });
                    break;
                case "child":
                    child.setCodePostal(tvCodePostal.getText().toString());
                    child.setContry(tvCountry.getText().toString());
                    child.setPhone(tvPhone.getText().toString());
                    child.setLastName(tvLastName.getText().toString());
                    child.setCity(tvCity.getText().toString());
                    child.setAdresse(tvEducation.getText().toString());
                    child.setFirstName(tvFirstName.getText().toString());
                    child.setBirthday(tvBirthday.getText().toString());
                    if (id != null)
                        FirebaseDatabase.getInstance().getReference().child("child").child(id).setValue(child).addOnCompleteListener
                                (new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SwitchUpdate.setChecked(false);
                                            tvCity.setEnabled(false);
                                            tvCountry.setEnabled(false);
                                            tvCodePostal.setEnabled(false);
                                            tvBirthday.setEnabled(false);
                                            tvPhone.setEnabled(false);
                                            tvEmail.setEnabled(false);
                                            tvEducation.setEnabled(false);
                                            btnUpdateprofile.setVisibility(View.GONE);

                                            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                                    getActivity(), "mypref", Context.MODE_PRIVATE);
                                            complexPreferences.putObject("current_user", child);
                                            complexPreferences.commit();
                                        }
                                    }
                                });
                    break;

            }
        }
    }

    public void getInfomationUser() {

        if (Role != null) {
            System.out.println("profileRole"+Role);
                switch (Role.trim()) {
                    case "teacher":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getContext(), "mypref",Activity.MODE_PRIVATE);
                        teacher = complexPreferences.getObject("current_user", Teacher.class);
                        System.out.println("profileTeacher"+teacher.toString());
                        tvCity.setText(teacher.getCity());
                        tvCountry.setText(teacher.getContry());
                        tvCodePostal.setText(teacher.getCodePostal());
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        tvFirstName.setText(teacher.getFirstName());
                        tvLastName.setText(teacher.getLastName());
                        tvPhone.setText(teacher.getPhone());
                        tvEducation.setText(teacher.getAdresse());
                        tvBirthday.setText(teacher.getBirthday());
                        Picasso.with(getActivity()).load(teacher.getUrlImage()).into(imageProfile);
                        break;
                    case "parent":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getContext(), "mypref",Activity.MODE_PRIVATE);
                        parent = complexPreferences.getObject("current_user", Parent.class);
                        tvCity.setText(parent.getCity());
                        tvCountry.setText(parent.getContry());
                        tvCodePostal.setText(parent.getCodePostal());
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                            tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        tvFirstName.setText(parent.getFirstName());
                        tvLastName.setText(parent.getLastName());
                        tvPhone.setText(parent.getPhone());
                        tvEducation.setText(parent.getAdresse());
                        tvBirthday.setText(parent.getBirthday());
                        Picasso.with(getActivity()).load(parent.getUrlImage()).into(imageProfile);
                        break;
                    case "child":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getContext(), "mypref",Activity.MODE_PRIVATE);
                        child = complexPreferences.getObject("current_user", Child.class);
                        tvCity.setText(child.getCity());
                        tvCountry.setText(child.getContry());
                        tvCodePostal.setText(child.getCodePostal());
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                            tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        tvFirstName.setText(child.getFirstName());
                        tvLastName.setText(child.getLastName());
                        tvPhone.setText(child.getPhone());
                        tvEducation.setText(child.getAdresse());
                        tvBirthday.setText(child.getBirthday());
                        Picasso.with(getActivity()).load(child.getUrlImage()).into(imageProfile);
                        break;
                }
            }


        }
    }



