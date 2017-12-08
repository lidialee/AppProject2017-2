package com.example.lidia.appproject2017_2.Model;


import com.example.lidia.appproject2017_2.Class.Love;
import com.example.lidia.appproject2017_2.Listener.OnCheckAlreadyLove;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class StoreModel {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private OnCheckAlreadyLove alreadyLoveLitener;

    public StoreModel() {
    }

    public void setAlreadyLoveLitener(OnCheckAlreadyLove alreadyLoveLitener) {
        this.alreadyLoveLitener = alreadyLoveLitener;
    }
    // 여기
    public void isLoveAlready(String userUid, final String storeUid){
        System.out.println("!!!!!!!!!!!  isLoveAlready ");
        mDatabase.child("UserLoveList").child(userUid).child(storeUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot e : dataSnapshot.getChildren()) {
                            Love love = e.getValue(Love.class);

                            assert love != null;

                            if(love.getStoreUid().equals(storeUid)){
                                alreadyLoveLitener.isLove(true);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getMessage());
                    }
                });
    }

    public void addLoveList(String storeUid, String userUid,String storeType){
        DatabaseReference dabaf =  mDatabase.child("UserLoveList").child(userUid).child(storeUid).push();
        String loveUid = dabaf.getKey();
        dabaf.setValue(Love.newLove(storeUid, storeType,loveUid));
    }

    public void removeLoveList(String userUid, final String storeUid){
        mDatabase.child("UserLoveList").child(userUid).child(storeUid).setValue(null);
    }

}
