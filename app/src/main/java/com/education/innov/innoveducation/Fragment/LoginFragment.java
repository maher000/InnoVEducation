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
import com.google.firebase.iid.FirebaseInstanceId;


public class LoginFragment extends Fragment {
    EditText EdtPasswordUser, EdtEmailUser;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference mDBase = Config.mDatabase;
    SharedPreferences sharedpreferences;
    Button btnlogin;
    String email, password;
    Activity activity;
    ProgressDialog progress;
    Teacher teacher;
    Child child;
    Parent parent;

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
                            ((MainActivity) activity).ShowErorMessage("check your informations please !");
                        }
                    }
                });

    }

    private void getUserInformation() {
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference ref_teacher = mDBase.child("teachers");
        final DatabaseReference ref_child = mDBase.child("child");
        final DatabaseReference ref_parent = mDBase.child("parents");
        ref_teacher.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    teacher = dataSnapshot.getValue(Teacher.class);
                    if (teacher.getIdUser().equals(id)) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("role", "teacher");
                        editor.commit();
                        System.out.println("le role est teacher");
                        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                getActivity(), "mypref", Context.MODE_PRIVATE);
                        complexPreferences.putObject("current_user", teacher);
                        complexPreferences.commit();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Log.i("DBCount", "# of online users = " + String.valueOf(dataSnapshot.getChildrenCount()));
                } else {
                    ref_parent.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                parent = dataSnapshot.getValue(Parent.class);
                                if (parent.getIdUser().equals(id)) {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("role", "parent");
                                    editor.commit();
                                    System.out.println("le role est parent");
                                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                            getActivity(), "mypref", getActivity().MODE_PRIVATE);
                                    complexPreferences.putObject("current_user", parent);
                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                                    Log.i("DBCount", "# of online users = " + String.valueOf(dataSnapshot.getChildrenCount()));
                            } else {
                                ref_child.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            child = dataSnapshot.getValue(Child.class);
                                            if (child.getIdUser().equals(id)) {
                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                editor.putString("role", "child");
                                                editor.commit();
                                                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                                        getActivity(), "mypref", Context.MODE_PRIVATE);
                                                complexPreferences.putObject("current_user", child);
                                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                                                Log.i("DBCount", "# of online users = " + String.valueOf(dataSnapshot.getChildrenCount()));
                                            }}

                                    @Override
                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                                    @Override
                                    public void onChildRemoved(DataSnapshot dataSnapshot) {}
                                    @Override
                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });}}
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {}
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });}}
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });}}