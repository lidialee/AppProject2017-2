package com.example.lidia.appproject2017_2.Model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnPensionChangedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PensionModel {
    private String ownerUid;
    private String name;
    private String wholeAddress;
    private String sectionArea;
    private String phone;
    private String web;
    private String price;
    private String plusDescription;
    private String caution;
    private int animalType;
    private int animalSize;
    private String things;
    private String environment;
    private double lat;
    private double log;

    private List<Pension> pensionList = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private int fileNumber = 1;
    private OnPensionChangedListener pensionChangedListener;
    private Ascending ascending ;


    // 생성자
    public PensionModel() {
        ascending = new Ascending();
    }


    public void setPensionChangedListener(OnPensionChangedListener pensionChangedListener) {
        this.pensionChangedListener = pensionChangedListener;
    }

    /**
     * < Storage >의 경우
     * storeImages (폴더
     * - 상점uid
     * - 1
     * - 2
     * - 3
     * - 상점 uid... 형식으로
     */





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
                    mDatabase.child("allPension").child(storeUid)
                            .setValue(Pension.newPension(storeUid,ownerUid,"호텔/팬션",name,wholeAddress,sectionArea,phone,web,
                                    price,plusDescription,caution,animalType,animalSize,things,environment,lat,log,0));


                    // PensionImage3Activity 에서 이미 상점 타입을 보내줬어여
                    storeRef.setValue(Pension.newPension(storeUid,ownerUid,"호텔/팬션",name,wholeAddress,sectionArea
                                ,phone,web,price,plusDescription,caution,animalType,animalSize,things,environment,lat,log,0));

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

    public void getPension(String area) {
        mDatabase.child("PensionORHotel").child(area).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Pension> tempList = new ArrayList<>();

                for (DataSnapshot e : dataSnapshot.getChildren()) {
                    Pension pension = e.getValue(Pension.class);
                    tempList.add(pension);
                }

                pensionList = tempList;
                if (pensionChangedListener != null){
                    Collections.sort(pensionList,ascending);
                    pensionChangedListener.getPension(pensionList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public List<Pension> getPensionList() {
        return this.pensionList;
    }

    private void unPackInfo(Bundle bundle) {
        this.ownerUid = bundle.getString("userUid");
        this.name = bundle.getString("name");
        this.wholeAddress= bundle.getString("wholeAddress");
        this.sectionArea= bundle.getString("area");
        this.phone= bundle.getString("phone");
        this.web= bundle.getString("web");
        this.price= bundle.getString("price");
        this.plusDescription= bundle.getString("plus");
        this.caution = bundle.getString("caution");
        this.animalType = bundle.getInt("animalType");
        this.animalSize= bundle.getInt("animalSize");
        this.things= bundle.getString("thing");
        this.environment= bundle.getString("environment");
        this.lat= bundle.getDouble("lat");
        this.log= bundle.getDouble("log");

    }

    class Ascending implements Comparator<Pension>{

        @Override
        public int compare(Pension p1, Pension p2) {
            if(p1.getLove()>p2.getLove())
                return -1;
            else if(p1.getLove() < p2.getLove())
                return 1;
            else
                return 0;
        }
    }

}
