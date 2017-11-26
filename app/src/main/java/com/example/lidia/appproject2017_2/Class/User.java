package com.example.lidia.appproject2017_2.Class;

public class User {
    private String name;
    private String email;
    private String userImage;
    private String userUid;
    private int isOwner;    //  0 = userBox  1 = ownerBox
    private int petType;         // 0 (x)  1 (dogBox)  2(catBox)  3(etcBox)

    public User(){}

    // 일반 회원가입
    public User(String name, String email, String userImage,
                String userUid, int isOwner, int petType) {
        this.name = name;
        this.email = email;
        this.userImage = userImage;
        this.userUid = userUid;
        this.isOwner = isOwner;
        this.petType = petType;
    }

    //  구글 소셜 로그인
    public User(String name, String email, String userImage, String userUid) {
        this(name, email,userImage,userUid,0,0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public int getUserType() {
        return isOwner;
    }

    public void setUserType(int owner) {
        isOwner = owner;
    }

    public int getAnimalType() {
        return petType;
    }

    public void setAnimalType(int animalType) {
        this.petType = animalType;
    }


}

