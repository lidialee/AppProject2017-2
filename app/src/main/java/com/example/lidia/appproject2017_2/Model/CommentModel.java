package com.example.lidia.appproject2017_2.Model;


import com.example.lidia.appproject2017_2.Class.Comment;
import com.example.lidia.appproject2017_2.Class.User;
import com.example.lidia.appproject2017_2.Listener.OnCommentChangeListener;
import com.example.lidia.appproject2017_2.Listener.UserInfoEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentModel {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private List<Comment> commentList = new ArrayList<>();
    private OnCommentChangeListener commentChangeListener;
    private UserModel userModel = new UserModel();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public void setCommentChangeListener(OnCommentChangeListener commentChangeListener) {
        this.commentChangeListener = commentChangeListener;
    }

    public CommentModel() {}

    public void getStoreComments(String storeUid){
        database.child("Comments").child(storeUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Comment> tempList = new ArrayList<>();

                for (DataSnapshot e : dataSnapshot.getChildren()) {
                    Comment comment = e.getValue(Comment.class);
                    tempList.add(comment);
                }

                commentList = tempList;
                if (commentChangeListener != null){
                    commentChangeListener.getComment(commentList);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getMyComment(){

    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    // 이제 여기할 차례
    public void writeComment(final String cm, final String stUid) {
        final DatabaseReference commentRef = database.child("Comments").child(stUid).push();
        final String commentKey = commentRef.getKey();

        userModel.getUserInfo(auth.getCurrentUser().getUid());
        userModel.setUserInfoEventListener(new UserInfoEventListener() {
            @Override
            public void getUser(User user) {
                if (user != null) {
                    commentRef.setValue(Comment.newComment(cm,user.getName(),makeTime(),commentKey,stUid,user.getUserUid(),user.getUserImage()));
                } else
                    System.out.println("유저가 안옴 문제 있음");
            }
        });

    }

    private String makeTime() {
        SimpleDateFormat simple = new SimpleDateFormat("yy.MM.dd a hh:mm", Locale.KOREA);
        return simple.format(new Date());
    }

}
