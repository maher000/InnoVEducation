package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
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
import android.widget.EditText;

import com.education.innov.innoveducation.Activities.CompleteInformationChildActivity;
import com.education.innov.innoveducation.Activities.HomeActivity;
import com.education.innov.innoveducation.Activities.MainActivity;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;


public class LoginFragment extends Fragment {
    private EditText EdtPasswordUser, EdtEmailUser;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference mDBase = Config.mDatabase;
    private SharedPreferences sharedpreferences;
    private Button btnlogin;
    private String email, password;
    private Activity activity;
    private ProgressDialog progress;
    private Teacher teacher;
    private Child child;
    private Parent parent;

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
                            mDBase.child("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Token").setValue(token);
                            System.out.println("id of user " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            /*******************************************/
                            progress.dismiss();
                            getUserInformation();
                        } else {
                            ((MainActivity) activity).ShowErorMessage("check your information please !");
                        }
                    }
                });

    }

    private void getUserInformation() {
        final String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("loginId"+id);
        final Query ref_teacher = mDBase.child("teachers").orderByKey().equalTo(id);
        final Query ref_child = mDBase.child("child").orderByKey().equalTo(id);
        final Query ref_parent = mDBase.child("parents").orderByKey().equalTo(id);
        System.out.println("getUserInformation");
        ref_teacher.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                        teacher = dataSnapshot.getValue(Teacher.class);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("role", "teacher");
                        editor.commit();
                        System.out.println("le role est teacher");
                    System.out.println("teacherFromLogin"+teacher);
                        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                getActivity(), "mypref", Context.MODE_PRIVATE);
                        complexPreferences.putObject("current_user", teacher);
                        complexPreferences.commit();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    return;
                } else
                    ref_teacher.removeEventListener(this);
                }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ref_parent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    parent = dataSnapshot.getValue(Parent.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("role", "parent");
                    editor.commit();
                    System.out.println("le role est parent");
                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                            getActivity(), "mypref", getActivity().MODE_PRIVATE);
                    complexPreferences.putObject("current_user", parent);
                    complexPreferences.commit();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;

                } else
                    ref_parent.removeEventListener(this);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ref_child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    child = dataSnapshot.getValue(Child.class);
                    if(child.getActive().trim().equals("yes".toLowerCase())) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("role", "child");
                        editor.commit();
                        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                getActivity(), "mypref", Context.MODE_PRIVATE);
                        complexPreferences.putObject("current_user", child);
                        complexPreferences.commit();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), CompleteInformationChildActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);}
                }}
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }



}