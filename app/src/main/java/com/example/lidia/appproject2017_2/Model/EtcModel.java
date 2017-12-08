package com.example.lidia.appproject2017_2.Model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lidia.appproject2017_2.Class.Area;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Listener.OnAllEtcListener;
import com.example.lidia.appproject2017_2.Listener.OnEtcChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnGetImageListener;
import com.example.lidia.appproject2017_2.Listener.OnLoveChangeListener;
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


public class EtcModel {
    private String specificType;
    private String ownerUid;
    private String name;
    private String wholeAddress;
    private String sectionArea;
    private String phone;
    private String web;
    private String plusDescription;
    private String caution;
    private int animalType;
    private int animalSize;
    private String things;
    private double lat;
    private double log;

    private List<Etc> etcList = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private int fileNumber = 1;
    private OnEtcChangedListener etcChangedListener;
    private OnLoveChangeListener loveChangeListener;
    private OnGetImageListener imageListener;
    private OnAllEtcListener allEtcListener;
    private AscendingEtc ascendingEtc;
    private List<String> mImageList = new ArrayList<>();

    public EtcModel() {
        ascendingEtc = new AscendingEtc();
    }

    public void setEtcChangedListener(OnEtcChangedListener etcChangedListener) {
        this.etcChangedListener = etcChangedListener;
    }
    public void setImageListener(OnGetImageListener imageListener) {
        this.imageListener = imageListener;
    }
    public void setLoveChangeListener(OnLoveChangeListener loveChangeListener) {
        this.loveChangeListener = loveChangeListener;
    }

    public void setAllEtcListener(OnAllEtcListener allEtcListener) {
        this.allEtcListener = allEtcListener;
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
                    mDatabase.child("allEtc").child(storeUid).setValue(Etc.newEtc(storeUid,ownerUid,specificType,name,wholeAddress,sectionArea,phone,web,plusDescription,caution,animalType,animalSize,things,lat,log,0));
                    // EtcImage3Activity 에서 이미 상점 타입을 보내줬어여
                    storeRef.setValue(Etc.newEtc(storeUid,ownerUid,specificType,name,wholeAddress,sectionArea,phone,web,plusDescription,caution,animalType,animalSize,things,lat,log,0));

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
    public void getEtc(String area) {
        mDatabase.child("Etc").child(area).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Etc> tempList = new ArrayList<>();

                for (DataSnapshot e : dataSnapshot.getChildren()) {
                    Etc etc= e.getValue(Etc.class);
                    tempList.add(etc);
                }

                etcList = tempList;
                if (etcChangedListener != null){
                    Collections.sort(etcList,ascendingEtc);
                    etcChangedListener.getEtc(etcList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public List<Etc> getEtcList() {
        return etcList;
    }

    private void unPackInfo(Bundle bundle) {
        this.ownerUid = bundle.getString("userUid");
        this.specificType = bundle.getString("type");
        this.name = bundle.getString("name");
        this.wholeAddress= bundle.getString("wholeAddress");
        this.sectionArea= bundle.getString("area");
        this.phone= bundle.getString("phone");
        this.web= bundle.getString("web");
        this.plusDescription= bundle.getString("plus");
        this.caution = bundle.getString("caution");
        this.animalType = bundle.getInt("animalType");
        this.animalSize= bundle.getInt("animalSize");
        this.things= bundle.getString("thing");
        this.lat= bundle.getDouble("lat");
        this.log= bundle.getDouble("log");

    }

    class AscendingEtc implements Comparator<Etc> {
        @Override
        public int compare(Etc p1, Etc p2) {
            if(p1.getLove()>p2.getLove())
                return -1;
            else if(p1.getLove() < p2.getLove())
                return 1;
            else
                return 0;
        }
    }
    // post별 image string 가져오기
    public void getEtcImages(String EtcKey) {
        mDatabase.child("ImageDatabase")
                .child(EtcKey)
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
                    Etc p = child.getValue(Etc.class);
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
                    Etc p = child.getValue(Etc.class);
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
    public void getAllEtc(){
        final List<Etc> temp = new ArrayList<>();
        for(int i =0 ; i<17;i++){
            mDatabase.child("Etc").child(Area.list[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot e : dataSnapshot.getChildren()) {
                        Etc etcone = e.getValue(Etc.class);
                        temp.add(etcone);

                        if(allEtcListener!=null){
                            allEtcListener.getAllEtc(temp);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println(databaseError.getMessage());
                }
            });
        }
    }
    public List<String> getImageList() {
        return mImageList;
    }
}
