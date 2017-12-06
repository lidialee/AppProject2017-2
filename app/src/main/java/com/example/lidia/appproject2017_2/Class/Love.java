package com.example.lidia.appproject2017_2.Class;

public class Love {
    String storeUid;

    public Love() {}

    public Love(String storeUid) {
        this.storeUid = storeUid;
    }


    public static Love newLove(String uid) {
        return new Love(uid);
    }

    public String getStoreUid() {
        return storeUid;
    }

    public void setStoreUid(String storeUid) {
        this.storeUid = storeUid;
    }
}
