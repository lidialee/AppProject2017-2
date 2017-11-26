package com.example.lidia.appproject2017_2.Class;


public class Cafe {
    private String Uid;
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

    public Cafe() {}

    public Cafe(String Uid,String ownerUid,String name, String wholeAddress, String sectionArea,
                String phone, String web, String time, String plusDescription,
                String caution, int animalType, int animalSize, String things, int isFood) {
        this.Uid = Uid;
        this.ownerUid = ownerUid;
        this.name = name;
        this.wholeAddress = wholeAddress;
        this.sectionArea = sectionArea;
        this.phone = phone;
        this.web = web;
        this.time = time;
        this.plusDescription = plusDescription;
        this.caution = caution;
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.things = things;
        this.isFood = isFood;
    }

    public static Cafe newCafe(String Uid,String owneruid,String name, String wholeAddress, String sectionArea,
                               String phone, String web, String time, String plusDescription,
                               String caution, int animalType, int animalSize, String things, int isFood) {

        return new Cafe(Uid,owneruid,name, wholeAddress,sectionArea,phone,web,
                        time,plusDescription,caution,animalType,animalSize, things,isFood);
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getIsFood() {
        return isFood;
    }

    public void setIsFood(int isFood) {
        this.isFood = isFood;
    }
}
