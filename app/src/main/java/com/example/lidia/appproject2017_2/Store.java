package com.example.lidia.appproject2017_2;


import java.util.List;

public class Store {
    // 이름, 주소명(풀 주소명), 위치(위도,경도), 영업시간, 가게사진( 최대 6장 ), 대표 메뉴판(, 추가 유의 사항
    // 키워드1 = 위치 (넓은범위+좁은범위 or 그냥 크게 넓은 단위만)
    // 키워드2 = 반려동물호텔, 반려동물 동반팬션 , 반려동물 동반카페, 반려동물 동반음식점, 기타 (5 가지)
    // 키워드3 = 주요 타켓 반려동물 타입 --> 고양이,개,기타 3가지
    // 키워드4 = 가게 내 환경 (팬스 설치, 소/중/대 케이지구비, 캣타워구비,배변패드 구비, <팬션경우>산책로, 운동장, 풀장)
    private int storeType;
    private String name;
    private String strLocation;
    private String phone;
    private String webSite;
    private double lat;
    private double log;
    private String startTime;
    private String endTime;
    private String[] imageList = new String[5];
    private String[] menuList = new String[5];


    public Store(int storeType, String name, String strLocation, String phone,
                 String webSite, double lat, double log, String startTime, String endTime,
                 String[] imagelist, String[] menulist) {
        int num =0;
        this.storeType = storeType;
        this.name = name;
        this.strLocation = strLocation;
        this.phone = phone;
        this.webSite = webSite;
        this.lat = lat;
        this.log = log;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
