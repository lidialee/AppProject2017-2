package com.example.lidia.appproject2017_2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindEtcKeywordActivity extends BasicActivity {
    @BindView(R.id.find_e_key_container_size)
    LinearLayout sizeLayout;

    @BindView(R.id.find_e_key_container_thing)
    LinearLayout thingCantainerLayout;

    @BindView(R.id.find_e_key_thing_layout)
    RelativeLayout thingLayout;

    @BindView(R.id.find_e_key_back)
    ImageView back;

    @BindView(R.id.find_e_key_done)
    ImageView done;

    @BindView(R.id.find_e_key_cat_radio)
    RadioButton radioCat;

    @BindView(R.id.find_e_key_dog_radio)
    RadioButton radioDog;

    @BindView(R.id.find_e_key_small)
    RadioButton radioSmallSize;

    @BindView(R.id.find_e_key_big)
    RadioButton radioBigSize;

    @BindView(R.id.find_e_key_fense)
    CheckBox fense;

    @BindView(R.id.find_e_key_toliet)
    CheckBox toliet;

    @BindView(R.id.find_e_key_cage)
    CheckBox cage;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.find_e_key_cat_radio:
                    sizeLayout.setVisibility(View.GONE);
                    thingCantainerLayout.setVisibility(View.VISIBLE);
                    thingLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_e_key_dog_radio:
                    sizeLayout.setVisibility(View.VISIBLE);
                    thingCantainerLayout.setVisibility(View.VISIBLE);
                    thingLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_e_key_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.find_e_key_done:
                    // 여기서 이제 디비에서 검색할 시간.
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_etc_keyword);
        ButterKnife.bind(this);

        radioCat.setOnClickListener(listener);
        radioDog.setOnClickListener(listener);
        back.setOnClickListener(listener);
        done.setOnClickListener(listener);

//        Toast.makeText(this,getIntent().getExtras().getInt("areaType")+" : 지역확인",Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,getIntent().getExtras().getInt("storeType")+" : 가게확인",Toast.LENGTH_SHORT).show();

    }
}
