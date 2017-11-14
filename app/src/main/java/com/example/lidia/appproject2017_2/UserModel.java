package com.example.lidia.appproject2017_2;


import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private List<User> userList = new ArrayList<>();
    private UserEventListener userEventListener;
    private UserInfoEventListener userInfoEventListener;
    private User mUser ;
    private String userUid;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public UserModel() {
        mUser = new User();
    }

    public void setUserInfoEventListener(UserInfoEventListener listener){
        this.userInfoEventListener = listener;
    }

    public void setUserEventListener(UserEventListener listener){
        this.userEventListener = listener;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    // 일반 회원가입
    public void  writeUserToDatabase(String name, String email, String image, String uid, int isOwner, int petType){
        User user = new User(name, email,image,uid,isOwner,petType);
        mDatabase.child("UserDatabase").child(uid).setValue(user);
    }

    public void writeGoogleUserToDatabase(String name, String email, String image, String uid){
        User user = new User(name, email,image,uid);
        mDatabase.child("UserDatabase").child(uid).setValue(user);
    }

    public void getUserInfo(final String uid) {
        mDatabase.child("UserDatabase").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User targetUser = dataSnapshot.getValue(User.class);

                        if (userInfoEventListener != null)
                            userInfoEventListener.getUser(targetUser);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getMessage());
                    }
                });
    }


    public void updateProfileWithImage(final Uri newImage, final String userUid, final String email, final String newName, final int userType, final int petType) {

        StorageReference storage = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://pettogether-11ca5.appspot.com");

        // 기존의 유저이미지 Storage에서 삭제.
        storage.child("userProfileUri/" + userUid).delete();

        // 새로운 이미지 Storage에 저장하고 uri와함께 Database에 저장한다
        UploadTask uploadTask = storage.child("userProfileUri").child(userUid).putFile(newImage);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String newUri = taskSnapshot.getDownloadUrl().toString();

                User updateUser = new User(newName,email,newUri,userUid,userType,petType);
                mDatabase.child("UserDatabase").child(userUid).setValue(updateUser);
                mUser = updateUser;
            }
        });
    }


}
