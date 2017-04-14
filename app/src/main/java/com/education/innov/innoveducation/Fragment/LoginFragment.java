package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;


public class LoginFragment extends Fragment {
    EditText EdtPasswordUser, EdtEmailUser;
    FirebaseAuth auth = Config.mAuth;
    DatabaseReference mDBase = Config.mDatabase;
    Button btnlogin;
    String email, password;
    Activity activity;

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
        activity = getActivity();
        EdtPasswordUser = (EditText) view.findViewById(R.id.EdtPasswordUser);
        EdtEmailUser = (EditText) view.findViewById(R.id.EdtEmailUser);
        btnlogin = (Button) view.findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            /*******************************************/

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
