package com.example.lidia.appproject2017_2.Activity;

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

    // 1 = 팬션  2 = 카페  3 = 음식점  4 = 기타
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            Intent intent = null;
            if (id == R.id.area_back)
                finish();
            else{
                switch (storeType){
                    case 1:
                        intent = new Intent(FindAreaActivity.this, FindPensionKeywordActivity.class);
                        intent.putExtra("area",1);
                        break;
                    case 2:
                        intent = new Intent(FindAreaActivity.this, FindCafeKeywordActivity.class);
                        intent.putExtra("area",2);
                        break;
                    case 3:
                        intent = new Intent(FindAreaActivity.this, FindRestKeywordActivity.class);
                        intent.putExtra("area",3);
                        break;
                    case 4:
                        intent = new Intent(FindAreaActivity.this, FindEtcKeywordActivity.class);
                        intent.putExtra("area",4);
                        break;
                }
                startActivity(intent);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_area);
        ButterKnife.bind(this);

        // 앞에서 사용자가 선택한 가게 타입 받아오기
        storeType = getIntent().getExtras().getInt("storeNumber");

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
}
