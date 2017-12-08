package com.example.lidia.appproject2017_2.Class;

public class Love {
    String storeUid;
    String storeType;
    String loveUid;

    public Love() {
    }

    public Love(String storeUid, String storeType,String loveUid) {
        this.storeUid = storeUid;
        this.storeType = storeType;
        this.loveUid = loveUid;
    }

    public String getLoveUid() {
        return loveUid;
    }

    public void setLoveUid(String loveUid) {
        this.loveUid = loveUid;
    }

    public String getStoreUid() {
        return storeUid;
    }

    public void setStoreUid(String storeUid) {
        this.storeUid = storeUid;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public static Love newLove(String storeUid, String type,String uid) {
        return new Love(storeUid, type,uid);
    }

}
