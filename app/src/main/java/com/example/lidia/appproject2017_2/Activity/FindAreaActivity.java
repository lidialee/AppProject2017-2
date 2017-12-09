package com.example.lidia.appproject2017_2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindAreaActivity extends BasicActivity {

    @BindView(R.id.seoul)
    ImageView seoul;

    @BindView(R.id.busan)
    ImageView busan;

    @BindView(R.id.degu)
    ImageView degu;

    @BindView(R.id.inchen)
    ImageView inchen;

    @BindView(R.id.dejen)
    ImageView dejen;

    @BindView(R.id.gwangju)
    ImageView gwangju;

    @BindView(R.id.wolsan)
    ImageView wolsan;

    @BindView(R.id.sejong)
    ImageView sejong;

    @BindView(R.id.geongii)
    ImageView geongii;

    @BindView(R.id.gangwondo)
    ImageView gangwondo;

    @BindView(R.id.chungnorth)
    ImageView chungnorth;

    @BindView(R.id.chungsouth)
    ImageView chungsouth;

    @BindView(R.id.junranorth)
    ImageView junranorth;

    @BindView(R.id.junrasouth)
    ImageView junrasouth;

    @BindView(R.id.geongnorth)
    ImageView geongnorth;

    @BindView(R.id.geongsouth)
    ImageView geongsouth;

    @BindView(R.id.jeju)
    ImageView jeju;

    @BindView(R.id.area_back)
    ImageView back;

    private int storeType;
    private int areaType;

    // 1 = 팬션  2 = 카페  3 = 음식점  4 = 기타
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.area_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.seoul:
                    goToStoreKeywordActivity("서울특별시") ;
                    break;
                case R.id.busan:
                    goToStoreKeywordActivity("부산광역시") ;
                    break;
                case R.id.degu:
                    goToStoreKeywordActivity("대구광역시") ;
                    break;
                case R.id.inchen:
                    goToStoreKeywordActivity("인천광역시") ;
                    break;
                case R.id.dejen:
                    goToStoreKeywordActivity("대전광역시") ;
                    break;
                case R.id.gwangju:
                    goToStoreKeywordActivity("광주광역시") ;
                    break;
                case R.id.wolsan:
                    goToStoreKeywordActivity("울산광역시") ;
                    break;
                case R.id.sejong:
                    goToStoreKeywordActivity("세종특별자치시") ;
                    break;
                case R.id.geongii:
                    goToStoreKeywordActivity("경기도") ;
                    break;
                case R.id.gangwondo:
                    goToStoreKeywordActivity("강원도") ;
                    break;
                case R.id.chungnorth:
                    goToStoreKeywordActivity("충청북도") ;
                    break;
                case R.id.chungsouth:
                    goToStoreKeywordActivity("충청남도") ;
                    break;
                case R.id.junranorth:
                    goToStoreKeywordActivity("전라북도") ;
                    break;
                case R.id.junrasouth:
                    goToStoreKeywordActivity("전라남도") ;
                    break;
                case R.id.geongnorth:
                    goToStoreKeywordActivity("경상북도") ;
                    break;
                case R.id.geongsouth:
                    goToStoreKeywordActivity("경상남도") ;
                    break;
                case R.id.jeju:
                    goToStoreKeywordActivity("제주특별자치도") ;
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_area);
        ButterKnife.bind(this);

        // 앞에서 사용자가 선택한 가게 타입 받아오기
        storeType = getIntent().getExtras().getInt("storeType");
        System.out.println("앞에서 선택한 가게타입은 : " + storeType);

        // 리스너 부착
        setListenerToView();
    }

    private void setListenerToView(){
        seoul.setOnClickListener(listener);
        busan.setOnClickListener(listener);
        degu.setOnClickListener(listener);
        inchen.setOnClickListener(listener);
        dejen.setOnClickListener(listener);
        gwangju.setOnClickListener(listener);
        wolsan.setOnClickListener(listener);
        sejong.setOnClickListener(listener);
        geongii.setOnClickListener(listener);
        gangwondo.setOnClickListener(listener);
        chungnorth.setOnClickListener(listener);
        chungsouth.setOnClickListener(listener);
        junranorth.setOnClickListener(listener);
        junrasouth.setOnClickListener(listener);
        geongnorth.setOnClickListener(listener);
        geongsouth.setOnClickListener(listener);
        jeju.setOnClickListener(listener);
        back.setOnClickListener(listener);
    }

    private void goToStoreKeywordActivity(String areaType){
        Intent intent = null;
        int storeType = getIntent().getExtras().getInt("storeType");
        switch (storeType){
            case 1:
                intent = new Intent(FindAreaActivity.this, FindPensionKeywordActivity.class);
                intent.putExtra("storeType",storeType);
                intent.putExtra("areaType",areaType);
                break;
            case 2:
                intent = new Intent(FindAreaActivity.this, FindCafeKeywordActivity.class);
                intent.putExtra("storeType",storeType);
                intent.putExtra("areaType",areaType);
                break;
            case 3:
                intent = new Intent(FindAreaActivity.this, FindRestKeywordActivity.class);
                intent.putExtra("storeType",storeType);
                intent.putExtra("areaType",areaType);
                break;
            case 4:
                intent = new Intent(FindAreaActivity.this, FindEtcKeywordActivity.class);
                intent.putExtra("storeType",storeType);
                intent.putExtra("areaType",areaType);
                break;
        }
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}




