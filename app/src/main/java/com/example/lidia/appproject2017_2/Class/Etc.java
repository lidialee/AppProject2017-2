package com.example.lidia.appproject2017_2.Class;

import java.io.Serializable;

public class Etc implements Serializable {
    private String Uid;
    private String ownerUid;
    private String storeType;
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
    private int love;

    public Etc() {}

    public Etc(String Uid,String ownerUid,String storeType,String name, String wholeAddress,
                String sectionArea, String phone, String web, String plusDescription,
                String caution, int animalType, int animalSize, String things,double lat,double log,int love) {
        this.Uid = Uid;
        this.ownerUid = ownerUid;
        this.storeType = storeType;
        this.name = name;
        this.wholeAddress = wholeAddress;
        this.sectionArea = sectionArea;
        this.phone = phone;
        this.web = web;
        this.plusDescription = plusDescription;
        this.caution = caution;
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.things = things;
        this.lat = lat;
        this.log = log;
        this.love = love;

    }

    public static Etc newEtc(String Uid,String ownerUid,String storeType,String name, String wholeAddress,
                             String sectionArea, String phone, String web, String plusDescription, String caution,
                             int animalType, int animalSize, String things,double lat,double log,int love) {

        return new Etc(Uid,ownerUid,storeType,name, wholeAddress,sectionArea,phone,web,
                        plusDescription,caution,animalType,animalSize, things,lat,log,love);
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWholeAddress() {
        return wholeAddress;
    }

    public void setWholeAddress(String wholeAddress) {
        this.wholeAddress = wholeAddress;
    }

    public String getSectionArea() {
        return sectionArea;
    }

    public void setSectionArea(String sectionArea) {
        this.sectionArea = sectionArea;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPlusDescription() {
        return plusDescription;
    }

    public void setPlusDescription(String plusDescription) {
        this.plusDescription = plusDescription;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public int getAnimalType() {
        return animalType;
    }

    public void setAnimalType(int animalType) {
        this.animalType = animalType;
    }

    public int getAnimalSize() {
        return animalSize;
    }

    public void setAnimalSize(int animalSize) {
        this.animalSize = animalSize;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }
}
