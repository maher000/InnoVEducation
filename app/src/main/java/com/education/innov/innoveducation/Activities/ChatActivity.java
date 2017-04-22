package com.education.innov.innoveducation.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.education.innov.innoveducation.Entities.Message;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class ChatActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private FirebaseAuth mFirebaseAuth=FirebaseAuth.getInstance();
    private DatabaseReference mDatabase=Config.mDatabase;;
    private String mMessageId;
    private ChatView chatView;
    private FirebaseUser mFirebaseUser = null;
    private String recieverName="";
    private String recieverId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent=getIntent();
        recieverName=intent.getStringExtra("name");
        recieverId=intent.getStringExtra("id");

        setUpToolbar();
        chatView = (ChatView) findViewById(R.id.chat_view);
        if(mFirebaseAuth !=null) {
            mFirebaseUser=mFirebaseAuth.getCurrentUser();
            getMessages();
            chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
                @Override
                public boolean sendMessage(ChatMessage chatMessage) {
                        Message m = new Message(chatMessage.getMessage(), chatMessage.getTimestamp(), chatMessage.getType(), mFirebaseUser.getUid(), recieverId);
                        addMessage(m);
                    chatView.getInputEditText().setText(""); 
                    return false;
                }
            });

            chatView.setTypingListener(new ChatView.TypingListener() {
                @Override
                public void userStartedTyping() {

                }

                @Override
                public void userStoppedTyping() {

                }
            });
        }else
            System.out.println("nullFireBase");
    }

    private void getMessages(){
        System.out.println(mDatabase.toString());
        mDatabase.child("message").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("maher"+dataSnapshot.getValue(Message.class));
                Message m = dataSnapshot.getValue(Message.class);

               if (mFirebaseUser.getUid().equals(m.getSenderId()) && recieverId.equals(m.getReciverId())) {

                    chatView.addMessage(new ChatMessage(m.getMessage(), m.getTimestamp(),  ChatMessage.Type.SENT));
                }else if (mFirebaseUser.getUid().equals(m.getReciverId()) && recieverId.equals(m.getSenderId())) {

                   chatView.addMessage(new ChatMessage(m.getMessage(), m.getTimestamp(), ChatMessage.Type.RECEIVED));

               }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void addMessage(Message m){
        String idMessage = mDatabase.child("message").push().getKey();
        m.setId(idMessage);
        mDatabase.child("message").child(idMessage).setValue(m);
    }



    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(recieverName);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
