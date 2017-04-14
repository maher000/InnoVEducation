package com.education.innov.innoveducation.Activities;

import android.os.Bundle;
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
    FirebaseUser mFirebaseUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setUpToolbar();
        chatView = (ChatView) findViewById(R.id.chat_view);
        if(mFirebaseAuth !=null) {
            mFirebaseUser=mFirebaseAuth.getCurrentUser();
            getMessages();
            chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
                @Override
                public boolean sendMessage(ChatMessage chatMessage) {
                        Message m = new Message(chatMessage.getMessage(), chatMessage.getTimestamp(), chatMessage.getType(), mFirebaseUser.getUid(), "jOhgluEJPLgXK37HyMHPBzXRBB53");
                    addMessage(m);
                    return true;
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
                System.out.println("syrine");
                System.out.println("maher"+dataSnapshot.getValue(Message.class));
                Message m = dataSnapshot.getValue(Message.class);

               if (mFirebaseUser.getUid().equals(m.getSenderId()) && "jOhgluEJPLgXK37HyMHPBzXRBB53".equals(m.getReciverId())) {

                    chatView.addMessage(new ChatMessage(m.getMessage(), m.getTimestamp(),  ChatMessage.Type.SENT));
                }else if (mFirebaseUser.getUid().equals(m.getReciverId()) && "jOhgluEJPLgXK37HyMHPBzXRBB53".equals(m.getSenderId())) {

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
        toolbar.setTitle("natalina del capo");
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
