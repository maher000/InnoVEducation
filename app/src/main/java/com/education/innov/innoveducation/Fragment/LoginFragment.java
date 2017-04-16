package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.education.innov.innoveducation.Activities.CompleteInformationUserActivity;
import com.education.innov.innoveducation.Activities.HomeActivity;
import com.education.innov.innoveducation.Activities.MainActivity;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LoginFragment extends Fragment {
    EditText EdtPasswordUser, EdtEmailUser;
    FirebaseAuth auth = Config.mAuth;
    DatabaseReference mDBase = Config.mDatabase;
    SharedPreferences sharedpreferences;
    Button btnlogin;
    String email, password;
    Activity activity;
    ProgressDialog progress;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(int page, String title) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        sharedpreferences = getActivity().getSharedPreferences("role_user", Context.MODE_PRIVATE);
        activity = getActivity();
        EdtPasswordUser = (EditText) view.findViewById(R.id.EdtPasswordUser);
        EdtEmailUser = (EditText) view.findViewById(R.id.EdtEmailUser);
        btnlogin = (Button) view.findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(getActivity());
                progress.setMessage("Uploading dat ...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
                email = EdtEmailUser.getText().toString();
                password = EdtPasswordUser.getText().toString();
                if (!new Test().TestConnection(getActivity())) {
                    ((MainActivity) activity).ShowErorMessage("there is no internet connection");
                } else if (email.isEmpty() || password.isEmpty()) {
                    ((MainActivity) activity).ShowErorMessage("All fields are requireds");
                } else {
                    Login();
                }

            }
        });
        return view;
    }

    private void Login() {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /*******************************************/
                            String token = FirebaseInstanceId.getInstance().getToken();
                            Log.d("log", "Token: " + token);
                            mDBase.child("Tokens").child(auth.getCurrentUser().getUid()).child("Token").setValue(token);


                            mDBase.child("teachers").orderByChild("id").equalTo(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                editor.putString("role", "teacher");
                                                editor.commit() ;

                                            } else {
                                                mDBase.child("parents").orderByChild("id").equalTo(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                                                        new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                                    editor.putString("role", "parent");
                                                                    editor.commit() ;
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                            }
                                                        });
                                            }
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });


                            /*******************************************/
                            progress.dismiss();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                        } else {
                            ((MainActivity) activity).ShowErorMessage("check your information please !");
                        }
                    }
                });

    }
}