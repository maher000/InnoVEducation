package com.education.innov.innoveducation.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.mukesh.countrypicker.fragments.CountryPicker;

public class AddChildActivity extends SwipeBackActivity {
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSubmit;
    private String email, password;
    private LinearLayout LayoutErrorMessage;
    private TextView tvErrorMsg;
    private ProgressDialog progress;


    StorageReference storageRef = Config.storage.getReference("images_users");
    StorageReference imagesRef;
    DatabaseReference mDBase = Config.mDatabase;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseAuth mAuth2 = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        initViews();

        LayoutErrorMessage = (LinearLayout) findViewById(R.id.LayoutErrorMessage);
        tvErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);
        etEmail = (EditText) findViewById(R.id.et_add_child_email);
        etPassword = (EditText) findViewById(R.id.et_add_child_password);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                AddChild();
            }
        });

    }

    private void AddChild() {
        if (!ShowErorMessage()) {
            progress = new ProgressDialog(this);
            progress.setMessage("Uploading data ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
            final String parentId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            mAuth2.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                /*******************************************/
                                String id = mAuth2.getCurrentUser().getUid();
                                Child child = new Child();
                                child.setIdUser(id);
                                child.setParentId(parentId);
                                child.setActive("false");
                                Config.mDatabase.child("child").child(id).setValue(child);
                                Config.mDatabase.child("parents").child(parentId).child("childId").child(id).setValue(id);
                                mAuth2.getCurrentUser().sendEmailVerification();
                                finish();
                                /*******************************************/

                                progress.dismiss();

                            } else {
                                progress.dismiss();
                                dispalyError(task.getException().getMessage().toString());
                            }
                        }
                    });
        }
    }

    private void initViews() {
        progressBar = (CircleProgressBar) findViewById(R.id.progressbar1);
        swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_layout);
        swipeBackLayout.setEnableFlingBack(false);
        swipeBackLayout.setOnPullToBackListener(new SwipeBackLayout.SwipeBackListener() {
            @Override
            public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
                progressBar.setProgress((int) (progressBar.getMax() * fractionAnchor));
            }
        });
    }

    public boolean ShowErorMessage() {
        String msg = "";
        if (!new Test().TestConnection(this)) {
            msg = "there is no internet connection";
            return dispalyError(msg);
        }
        if (email.trim().isEmpty() || password.trim().isEmpty())
            return dispalyError("All fields are required");

        return false;

    }

    private boolean dispalyError(String message) {
        tvErrorMsg.setText(message);
        tvErrorMsg.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        LayoutErrorMessage.setVisibility(View.VISIBLE);
        LayoutErrorMessage.postDelayed(new Runnable() {
            public void run() {
                LayoutErrorMessage.setVisibility(View.INVISIBLE);
            }
        }, 3000);
        return true;
    }
}
