package com.example.lidia.appproject2017_2.Model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.CafeDetailActivity;
import com.example.lidia.appproject2017_2.Activity.DetailActivity.PensionDetailActivity;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnAllCafeListener;
import com.example.lidia.appproject2017_2.Listener.OnCafeChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnGetImageListener;
import com.example.lidia.appproject2017_2.Listener.OnLoveChangeListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchCafeResultListener;
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



public class CafeModel {
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

    private List<Cafe> cafeList = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private int fileNumber = 1;
    private OnCafeChangedListener cafeChangedListener;
    private OnLoveChangeListener loveChangeListener;
    private OnGetImageListener imageListener;
    private OnAllCafeListener allCafeListener;
    private OnSearchCafeResultListener cafeResultListener;
    private AscendingCafe ascendingCafe;
    private List<String> mImageList = new ArrayList<>();

    public CafeModel() {
        ascendingCafe = new AscendingCafe();
    }


    public void setCafeChangedListener(OnCafeChangedListener cafeChangedListener) {
        this.cafeChangedListener = cafeChangedListener;
    }
    public void setImageListener(OnGetImageListener imageListener) {
        this.imageListener = imageListener;
    }
    public void setLoveChangeListener(OnLoveChangeListener loveChangeListener) {
        this.loveChangeListener = loveChangeListener;
    }
    public void setAllCafeListener(OnAllCafeListener allCafeListener) {
        this.allCafeListener = allCafeListener;
    }

    public void setCafeResultListener(OnSearchCafeResultListener cafeResultListener) {
        this.cafeResultListener = cafeResultListener;
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
                    mDatabase.child("allCafe").child(storeUid).setValue(Cafe.newCafe(storeUid,ownerUid,"카페",name,wholeAddress,sectionArea,phone,web,time,plusDescription,caution,animalType,animalSize,things,isFood,lat,log,0));

                    // PensionImage3Activity 에서 이미 상점 타입을 보내줬어여
                    storeRef.setValue(Cafe.newCafe(storeUid,ownerUid,"카페",name,wholeAddress,sectionArea,phone,web,time,plusDescription,caution,animalType,animalSize,things,isFood,lat,log,0));

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

    public void getCafe(String area) {
        mDatabase.child("Cafe").child(area).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Cafe> tempList = new ArrayList<>();

                for (DataSnapshot e : dataSnapshot.getChildren()) {
                    Cafe cafe = e.getValue(Cafe.class);
                    tempList.add(cafe);
                }

                cafeList = tempList;
                if (cafeChangedListener != null){
                    Collections.sort(cafeList,ascendingCafe);
                    cafeChangedListener.getCafe(cafeList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public List<Cafe> getCafeList() {
        return cafeList;
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

    class AscendingCafe implements Comparator<Cafe> {
        @Override
        public int compare(Cafe p1, Cafe p2) {
            if(p1.getLove()>p2.getLove())
                return -1;
            else if(p1.getLove() < p2.getLove())
                return 1;
            else
                return 0;
        }
    }

    // post별 image string 가져오기
    public void getCafeImages(String pensionKey) {
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
    // 카운드 계산 +1
    public void onLoveClicked(DatabaseReference pensionRef, final String storeUid) {
        pensionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Cafe p = child.getValue(Cafe.class);
                    if(p.getUid().equals(storeUid)){
                        int current = p.getLove();
                        current += 1;

                        if(loveChangeListener!=null)
                            loveChangeListener.changeLove(current);

                        child.getRef().child("love").setValue(current);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // 카운트 계산 -1
    public void onLoveUnClicked(DatabaseReference pensionRef, final String storeUid) {
        pensionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Cafe p = child.getValue(Cafe.class);
                    if(p.getUid().equals(storeUid)){
                        int current = p.getLove();
                        if(current>0){
                            current -= 1;
                            if(loveChangeListener!=null)
                                loveChangeListener.changeLove(current);
                            child.getRef().child("love").setValue(current);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void getAllCafe(){
        final List<Cafe> temp = new ArrayList<>();
            mDatabase.child("allCafe").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot e : dataSnapshot.getChildren()) {
                        Cafe cafe = e.getValue(Cafe.class);
                        temp.add(cafe);

                        if(allCafeListener!=null){
                            allCafeListener.getAllCafe(temp);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println(databaseError.getMessage());
                }
            });
    }
    // 검색용 서치
    public void searchCafe(String areaSection, final int petSize, final int pettype, final String things, final int isFood  ){
        mDatabase.child("Cafe").child(areaSection).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Cafe> temp = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Cafe p = child.getValue(Cafe.class);
                    if(p.getAnimalType() == pettype){
                        if(p.getAnimalSize() == petSize || p.getAnimalSize() == 3){
                            String oneThings = p.getThings();
                            int food = p.getIsFood();
                            if(oneThings.contains(things) && food ==  isFood){
                                temp.add(p);
                                System.out.println("++++++++++++++++++++++++++++");
                            }
                        }
                    }
                }
                cafeList = temp;
                if(cafeResultListener != null)
                    cafeResultListener.searchResult(cafeList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("searchPension 문제");
            }
        });
    }

    public void oneCafe(String uid, final Context context) {
        mDatabase.child("allCafe").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cafe ps= dataSnapshot.getValue(Cafe.class);
                Intent intent = new Intent(context, CafeDetailActivity.class);
                intent.putExtra("cafe",ps);
                intent.putExtra("type",2);
                context.startActivity(intent);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public List<String> getImageList() {
        return mImageList;
    }

}
