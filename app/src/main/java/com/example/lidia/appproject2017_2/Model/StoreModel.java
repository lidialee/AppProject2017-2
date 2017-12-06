package com.example.lidia.appproject2017_2.Model;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class StoreModel {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public StoreModel(int storeType, String area) {
        switch (storeType){
            case 1:
                mDatabase = mDatabase.child("PensionORHotel").child(area);
                break;
            case 2:
                mDatabase = mDatabase.child("Cafe").child(area);
                break;
            case 3:
                mDatabase = mDatabase.child("Rest").child(area);
                break;
            case 4:
                mDatabase = mDatabase.child("Etc").child(area);
                break;
        }
    }

    public void setLoveCount(int storeType, String uid){
        mDatabase.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                return null;
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

}
