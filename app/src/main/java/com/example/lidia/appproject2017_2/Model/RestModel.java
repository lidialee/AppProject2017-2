package com.example.lidia.appproject2017_2.Model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Listener.OnCafeChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnGetImageListener;
import com.example.lidia.appproject2017_2.Listener.OnRestChangedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class RestModel {

    private String ownerUid;
    private String name;
    private String wholeAddress;
    private String sectionArea;
    private String phone;
    private String web;
    private String time;
    private String plusDescription;
    private String caution;
    private int animalType;
    private int animalSize;
    private String things;
    private int isFood;
    private double lat;
    private double log;

    private List<Rest> restList = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private int fileNumber = 1;
    private OnRestChangedListener restChangedListener;
    private AscendingRest ascendingRest;
    private OnGetImageListener imageListener;
    private List<String> mImageList = new ArrayList<>();


    public RestModel() {
       ascendingRest = new AscendingRest();
    }

    public void setRestChangedListener(OnRestChangedListener restChangedListener) {
        this.restChangedListener = restChangedListener;
    }
    public void setImageListener(OnGetImageListener imageListener) {
        this.imageListener = imageListener;
    }


    public void storeImage(final List<InputStream> list, final DatabaseReference storeRef, final String storeUid, final Bundle bundle) {

        StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pettogether-11ca5.appspot.com");
        for (InputStream is : list) {
            UploadTask task = storage.child("storeImagesStorage").child(storeUid).child("number_" + fileNumber).putStream(is);
            task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String imageUrl = taskSnapshot.getDownloadUrl().toString();

                    // 스토리지가 아니라 따로 이미지 url 저장 db
                    mDatabase.child("ImageDatabase").child(storeUid).push().setValue(imageUrl);
                    unPackInfo(bundle);
                    // 쉽게 가게 찾을 수 있도록 데이터의 중첩이 생겨도 그냥 통째 집어넣는는 db
                    mDatabase.child("allRest").child(storeUid).setValue(Rest.newRest(storeUid,ownerUid,"음식점",name,wholeAddress,sectionArea,phone,web,time,plusDescription,caution,animalType,animalSize,things,isFood,lat,log,0));
                    // PensionImage3Activity 에서 이미 상점 타입을 보내줬어여
                    storeRef.setValue(Rest.newRest(storeUid,ownerUid,"음식점",name,wholeAddress,sectionArea,phone,web,time,plusDescription,caution,animalType,animalSize,things,isFood,lat,log,0));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("실패", "" + e.getLocalizedMessage());
                }
            });
            fileNumber++;
        }
    }
    public void getRest(String area) {
        mDatabase.child("Rest").child(area).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Rest> tempList = new ArrayList<>();

                for (DataSnapshot e : dataSnapshot.getChildren()) {
                    Rest rest= e.getValue(Rest.class);
                    tempList.add(rest);
                }

                restList = tempList;
                if (restChangedListener != null){
                    Collections.sort(restList,ascendingRest);
                    restChangedListener.getRest(restList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public List<Rest> getRestList() {
        return restList;
    }

    private void unPackInfo(Bundle bundle) {
        this.ownerUid = bundle.getString("userUid");
        this.name = bundle.getString("name");
        this.wholeAddress= bundle.getString("wholeAddress");
        this.sectionArea= bundle.getString("area");
        this.phone= bundle.getString("phone");
        this.web= bundle.getString("web");
        this.time= bundle.getString("time");
        this.plusDescription= bundle.getString("plus");
        this.caution = bundle.getString("caution");
        this.animalType = bundle.getInt("animalType");
        this.animalSize= bundle.getInt("animalSize");
        this.things= bundle.getString("thing");
        this.isFood= bundle.getInt("isFood");
        this.lat= bundle.getDouble("lat");
        this.log= bundle.getDouble("log");

    }

    class AscendingRest implements Comparator<Rest> {
        @Override
        public int compare(Rest p1, Rest p2) {
            if(p1.getLove()>p2.getLove())
                return -1;
            else if(p1.getLove() < p2.getLove())
                return 1;
            else
                return 0;
        }
    }
    // post별 image string 가져오기
    public void getRestImages(String pensionKey) {
        mDatabase.child("ImageDatabase")
                .child(pensionKey)
                .addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot e : dataSnapshot.getChildren()) {
                            String a = e.getValue(String.class);
                            mImageList.add(a);
                        }
                        if (imageListener != null) {
                            imageListener.getImage(mImageList);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(databaseError.getDetails(), "");
                    }
                });
    }
    public List<String> getImageList() {
        return mImageList;
    }
}
