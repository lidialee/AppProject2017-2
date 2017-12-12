package com.example.lidia.appproject2017_2.Model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lidia.appproject2017_2.Activity.DetailActivity.PensionDetailActivity;
import com.example.lidia.appproject2017_2.Class.Area;
import com.example.lidia.appproject2017_2.Class.Love;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnAllPensionListener;
import com.example.lidia.appproject2017_2.Listener.OnCheckAlreadyLove;
import com.example.lidia.appproject2017_2.Listener.OnGetImageListener;
import com.example.lidia.appproject2017_2.Listener.OnLoveChangeListener;
import com.example.lidia.appproject2017_2.Listener.OnPensionChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchPensionResultListener;
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


public class PensionModel {
    private static final String TAG = "pension Model";
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
    private List<String> mImageList = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private OnPensionChangedListener pensionChangedListener;
    private OnGetImageListener imageListener;
    private OnLoveChangeListener loveChangeListener;
    private OnAllPensionListener allPensionListener;
    private OnSearchPensionResultListener searchPensionResultListener;
    private Ascending ascending ;
    private int fileNumber = 1;



    // 생성자
    public PensionModel() {
        ascending = new Ascending();
    }


    public void setPensionChangedListener(OnPensionChangedListener pensionChangedListener) {
        this.pensionChangedListener = pensionChangedListener;
    }

    public void setImageListener(OnGetImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public void setLoveChangeListener(OnLoveChangeListener loveChangeListener) {
        this.loveChangeListener = loveChangeListener;
    }

    public void setAllPensionListener(OnAllPensionListener allPensionListener) {
        this.allPensionListener = allPensionListener;
    }

    public void setSearchPensionResultListener(OnSearchPensionResultListener searchPensionResultListener) {
        this.searchPensionResultListener = searchPensionResultListener;
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

    // 검색용 서치
    public void searchPension(String areaSection, final int petSize, final int pettype, final String things, final String environment ){
        mDatabase.child("PensionORHotel").child(areaSection).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Pension> temp = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Pension p = child.getValue(Pension.class);
                    if(p.getAnimalType() == pettype ){
                        if(p.getAnimalSize() == petSize || p.getAnimalSize() == 3){
                            String oneThings = p.getThings();
                            String oneEnvirn = p.getEnvironment();
                            if(oneThings.equals(things) && oneEnvirn.equals(environment)){
                                temp.add(p);
                            }
                        }
                    }
                }
                pensionList = temp;
                if(searchPensionResultListener != null)
                    searchPensionResultListener.searchResult(pensionList);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("searchPension 문제");
            }
        });
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

    // Pension별 image string 가져오기
    public void getPensionImages(String pensionKey) {
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
                    Pension p = child.getValue(Pension.class);
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
                    Pension p = child.getValue(Pension.class);
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // 지도 표시용 모두 가져오기
    public void getAllPension(){
        final List<Pension> temp = new ArrayList<>();
            mDatabase.child("allPension").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot e : dataSnapshot.getChildren()) {
                        Pension pension = e.getValue(Pension.class);
                        temp.add(pension);
                        System.out.println(pension.getName());

                        if(allPensionListener!=null){
                            allPensionListener.getAllPension(temp);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println(databaseError.getMessage());
                }
            });

    }

    public void onePension(String uid, final Context context) {
        mDatabase.child("allPension").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Pension ps= dataSnapshot.getValue(Pension.class);
                    Intent intent = new Intent(context, PensionDetailActivity.class);
                    intent.putExtra("pension",ps);
                    intent.putExtra("type",1);
                    context.startActivity(intent);

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
    public List<String> getImageList() {
        return mImageList;
    }


}
