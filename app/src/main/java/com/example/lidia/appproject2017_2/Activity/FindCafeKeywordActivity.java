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

public class FindCafeKeywordActivity extends BasicActivity {
    @BindView(R.id.find_c_key_container_size)
    LinearLayout sizeLayout;

    @BindView(R.id.find_c_key_container_thing)
    LinearLayout thingLayout;

    @BindView(R.id.find_c_key_container_food)
    LinearLayout foodLayout;

    @BindView(R.id.find_c_key_dog_thing_layout)
    RelativeLayout dogThingLayout;

    @BindView(R.id.find_c_key_cat_thing_layout)
    RelativeLayout catThingLayout;

    @BindView(R.id.find_c_key_back)
    ImageView back;

    @BindView(R.id.find_c_key_done)
    ImageView done;

    @BindView(R.id.find_c_key_cat_radio)
    RadioButton radioCat;

    @BindView(R.id.find_c_key_dog_radio)
    RadioButton radioDog;

    @BindView(R.id.find_c_key_small)
    RadioButton radioSmallMediumSize;

    @BindView(R.id.find_c_key_big)
    RadioButton radioBigSize;

    @BindView(R.id.find_c_key_dog_fense)
    CheckBox dogFense;

    @BindView(R.id.find_c_key_dog_s_cage)
    CheckBox dogSmallCage;

    @BindView(R.id.find_c_key_dog_b_cage)
    CheckBox dogBigCage;

    @BindView(R.id.find_c_key_dog_toliet)
    CheckBox dogToliet;

    @BindView(R.id.find_c_key_dog_room)
    CheckBox dogRoom;

    @BindView(R.id.find_c_key_cat_cage)
    CheckBox catCage;

    @BindView(R.id.find_c_key_cat_tower)
    CheckBox catTower;

    @BindView(R.id.find_c_key_cat_sand)
    CheckBox catSandToliet;

    @BindView(R.id.find_c_key_cat_room)
    CheckBox catRoom;

    @BindView(R.id.find_c_key_yes)
    RadioButton foodYes;

    @BindView(R.id.find_c_key_no)
    RadioButton foodNo;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.find_c_key_cat_radio:
                    sizeLayout.setVisibility(View.GONE);
                    thingLayout.setVisibility(View.VISIBLE);
                    dogThingLayout.setVisibility(View.GONE);
                    catThingLayout.setVisibility(View.VISIBLE);
                    foodLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_c_key_dog_radio:
                    sizeLayout.setVisibility(View.VISIBLE);
                    thingLayout.setVisibility(View.VISIBLE);
                    dogThingLayout.setVisibility(View.VISIBLE);
                    catThingLayout.setVisibility(View.GONE);
                    foodLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_c_key_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.find_c_key_done:
                    // 여기서 이제 디비에서 검색할 시간.
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cafe_keyword);
        ButterKnife.bind(this);

        radioCat.setOnClickListener(listener);
        radioDog.setOnClickListener(listener);
        back.setOnClickListener(listener);
        done.setOnClickListener(listener);

//        Toast.makeText(this,getIntent().getExtras().getInt("areaType")+" : 확인",Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,getIntent().getExtras().getInt("storeType")+" : 가게확인",Toast.LENGTH_SHORT).show();

    }
}
