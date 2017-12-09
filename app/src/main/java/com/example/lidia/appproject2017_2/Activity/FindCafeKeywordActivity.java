package com.example.lidia.appproject2017_2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.lidia.appproject2017_2.Activity.ResultActivity.ResultCafeActivity;
import com.example.lidia.appproject2017_2.Activity.ResultActivity.ResultPensionActivity;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Listener.OnSearchCafeResultListener;
import com.example.lidia.appproject2017_2.Model.CafeModel;
import com.example.lidia.appproject2017_2.R;

import java.io.Serializable;
import java.util.List;

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

    @BindView(R.id.find_c_key_group_type)
    RadioGroup petTypeGroup;
    @BindView(R.id.find_c_key_cat_radio)
    RadioButton radioCat;
    @BindView(R.id.find_c_key_dog_radio)
    RadioButton radioDog;

    @BindView(R.id.find_c_key_group_size)
    RadioGroup sizeGroup;
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

    @BindView(R.id.find_c_key_group_food)
    RadioGroup foodGroup;
    @BindView(R.id.find_c_key_yes)
    RadioButton foodYes;
    @BindView(R.id.find_c_key_no)
    RadioButton foodNo;


    private CafeModel cafeModel = new CafeModel();
    private ProgressDialog mProgressDialog;
    private int petType;
    private int petSize;  // 1 = small 2 = big  3 = all ( cat is all )
    private int isFood;   // 1 = yes  2= no
    private String areaSection="";


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
                    showProgressDialog();
                    String thing = makeThingString();

                    if(!checkVaild(thing,isFood)){
                        hideProgressDialog();
                        return;
                    }else{
                        Intent intent = new Intent(FindCafeKeywordActivity.this, ResultCafeActivity.class);
                        intent.putExtra("areaSection", areaSection);
                        intent.putExtra("petSize", petSize);
                        intent.putExtra("petType", petType);
                        intent.putExtra("thing", thing);
                        intent.putExtra("isFood", isFood);

                        hideProgressDialog();
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                /** 동물 타입 라디오타입 리스너, 선택에 따라 뷰 바꾸기
                 *  1 = 강아지,  2 = 고양이  **/
                case R.id.find_c_key_dog_radio:
                    petType = 1;
                    break;
                case R.id.find_c_key_cat_radio:
                    petType = 2;
                    petSize = 3; // 무조건 3으로 고정
                    break;
                /**  1= small 2 = big 이 선택지는 강아지만을 위한 경우 **/
                case R.id.find_c_key_small:
                    petSize = 1;
                    break;
                case R.id.find_c_key_big:
                    petSize = 2;
                    break;

                /**  1= 제공 2 = 제공안함 **/
                case R.id.find_c_key_yes:
                    isFood= 1;
                    break;
                case R.id.find_c_key_no:
                    isFood= 2;
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

        // 앞에서 받아온 지역 타입 저장해놓기
        areaSection = getIntent().getExtras().getString("areaType");

        // 라디오 버튼 리스너
        petTypeGroup.setOnCheckedChangeListener(radioGroupListener);
        sizeGroup.setOnCheckedChangeListener(radioGroupListener);
        foodGroup.setOnCheckedChangeListener(radioGroupListener);

    }

    private String makeThingString(){
        String thingString = "";
        /** 끝에 반드시 빈칸을 남겨두자. 나중에 split의 기준이 됩니다.**/

        if(radioDog.isChecked()){
            if(dogFense.isChecked())
                thingString += "팬스 ";
            if(dogSmallCage.isChecked())
                thingString += "소형캔넬 ";
            if(dogBigCage.isChecked())
                thingString += "대형캔넬 ";
            if(dogToliet.isChecked())
                thingString +="배변패드 ";
            if(dogRoom.isChecked())
                thingString +="개인룸 ";

        }
        else if(radioCat.isChecked()){
            if(catCage.isChecked())
                thingString += "캔넬 ";
            if(catSandToliet.isChecked())
                thingString += "배변패드 or 모래화장실 ";
            if(catTower.isChecked())
                thingString += "캣타워 ";
            if(catRoom.isChecked())
                thingString += "개인룸 ";
        }
        return thingString;
    }


    private boolean checkVaild(String thing, int isFood){
        if(areaSection.equals("")){
            Snackbar.make(getWindow().getDecorView().getRootView(), "지역이 선택되지 않은 에러 발생", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(petType == 0){
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 타입을 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(petSize == 0){
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 사이즈을 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(thing.equals("")){
            Snackbar.make(getWindow().getDecorView().getRootView(), "제공하는 서비스를 1개 이상 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(isFood == 100){
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 음식 제공여부를 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(" 가게를 찾는 중입니다 . \n 잠시만 기다려주세요");
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
