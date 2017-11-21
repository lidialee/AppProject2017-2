package com.example.lidia.appproject2017_2.Class;


public class Store {
    // 이름, 주소명(풀 주소명), 위치(위도,경도), 영업시간, 가게사진( 최대 5장 ), 대표 메뉴판(, 추가 유의 사항
    // 키워드1 = 위치 (넓은범위+좁은범위 or 그냥 크게 넓은 단위만)
    // 키워드2 = 반려동물호텔, 반려동물 동반팬션 , 반려동물 동반카페, 반려동물 동반음식점, 기타 (5 가지)
    // 키워드3 = 주요 타켓 반려동물 타입 --> 고양이,개,종류에 구애 받지 않음,기타 4가지
    // 키워드4 = 가게 내 환경 (팬스 설치, 소/중/대 케이지구비, 캣타워구비,배변패드 구비,목줄필요여부, <팬션경우>산책로, 운동장, 풀장)
    private int storeType;  // 반려동물호텔, 반려동물 동반팬션 , 반려동물 동반카페, 반려동물 동반음식점, 기타 (5 가지)
    private String etcName;
    private String ownerUid;
    private String Uid;
    private String name;
    private String strLocation;
    private String phone;
    private String webSite;
    private double lat;
    private double log;
    private String startTime;
    private String endTime;
    private int likeCount;
    private String image1, image2, image3, image4, image5;
    private String description;
    private int keyLocation;        // 유일 1개
    private int keyTargetAnimal1;    // 2개
    private int keyTargetAnimal2;
    private int keyEnvironment1;     // 4개
    private int keyEnvironment2;
    private int keyEnvironment3;
    private int keyEnvironment4;



    public Store() {}

    public Store(String ownerUid, String Uid,int storeType,String etcName, String name, String strLocation, String phone,
                 String webSite, double lat, double log, String startTime, String endTime,
                 int likeCount, String image1,String image2,String image3,String image4,String image5,
                 String description,int keyLocation, int keyTargetAnimal1, int keyTargetAnimal2,
                 int keyEnvironment1,int keyEnvironment2,int keyEnvironment3,int keyEnvironment4) {
        this.ownerUid = ownerUid;
        this.Uid = Uid;
        this.storeType = storeType;
        this.etcName = etcName;
        this.name = name;
        this.strLocation = strLocation;
        this.phone = phone;
        this.webSite = webSite;
        this.lat = lat;
        this.log = log;
        this.startTime = startTime;
        this.endTime = endTime;
        this.likeCount = likeCount;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.description =description;
        this.keyLocation = keyLocation;
        this.keyTargetAnimal1 = keyTargetAnimal1;
        this.keyTargetAnimal2 = keyTargetAnimal2;
        this.keyEnvironment1 = keyEnvironment1;
        this.keyEnvironment2 = keyEnvironment2;
        this.keyEnvironment3 = keyEnvironment3;
        this.keyEnvironment4 = keyEnvironment4;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrLocation() {
        return strLocation;
    }

    public void setStrLocation(String strLocation) {
        this.strLocation = strLocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }
    public int getKeyLocation() {
        return keyLocation;
    }

    public void setKeyLocation(int keyLocation) {
        this.keyLocation = keyLocation;
    }

    public int getKeyTargetAnimal1() {
        return keyTargetAnimal1;
    }

    public void setKeyTargetAnimal1(int keyTargetAnimal1) {
        this.keyTargetAnimal1 = keyTargetAnimal1;
    }

    public int getKeyTargetAnimal2() {
        return keyTargetAnimal2;
    }

    public void setKeyTargetAnimal2(int keyTargetAnimal2) {
        this.keyTargetAnimal2 = keyTargetAnimal2;
    }

    public int getKeyEnvironment1() {
        return keyEnvironment1;
    }

    public void setKeyEnvironment1(int keyEnvironment1) {
        this.keyEnvironment1 = keyEnvironment1;
    }

    public int getKeyEnvironment2() {
        return keyEnvironment2;
    }

    public void setKeyEnvironment2(int keyEnvironment2) {
        this.keyEnvironment2 = keyEnvironment2;
    }

    public int getKeyEnvironment3() {
        return keyEnvironment3;
    }

    public void setKeyEnvironment3(int keyEnvironment3) {
        this.keyEnvironment3 = keyEnvironment3;
    }

    public int getKeyEnvironment4() {
        return keyEnvironment4;
    }

    public void setKeyEnvironment4(int keyEnvironment4) {
        this.keyEnvironment4 = keyEnvironment4;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtcName() {
        return etcName;
    }

    public void setEtcName(String etcName) {
        this.etcName = etcName;
    }

    //    public static Store newStore(String butlerUID,String cafeteriaUID ,String UID, int catType,
//
//                             String name, String imageUrl, double lat, double log, int sex, String descrption) {
//        return new Store(storeType, name, strLocation, phone, webSite, lat,  log,  startTime,  endTime,
//         likeCount,  image1, image2, image3,image4, image5 );
//    }

}
