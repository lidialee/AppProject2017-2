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

import com.example.lidia.appproject2017_2.Activity.ResultActivity.ResultPensionActivity;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnSearchPensionResultListener;
import com.example.lidia.appproject2017_2.Model.PensionModel;
import com.example.lidia.appproject2017_2.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindPensionKeywordActivity extends BasicActivity {

    @BindView(R.id.find_p_key_container_size)
    LinearLayout sizeLayout;

    @BindView(R.id.find_p_key_container_thing)
    LinearLayout thingLayout;

    @BindView(R.id.find_p_key_container_envir)
    LinearLayout environLayout;

    @BindView(R.id.find_p_key_dog_thing_layout)
    RelativeLayout dogThingLayout;

    @BindView(R.id.find_p_key_cat_thing_layout)
    RelativeLayout catThingLayout;

    @BindView(R.id.find_p_key_back)
    ImageView back;

    @BindView(R.id.find_p_key_done)
    ImageView done;

    @BindView(R.id.find_p_key_group_type)
    RadioGroup petTypeGroup;
    @BindView(R.id.find_p_key_cat_radio)
    RadioButton radioCat;
    @BindView(R.id.find_p_key_dog_radio)
    RadioButton radioDog;

    @BindView(R.id.find_p_key_group_size)
    RadioGroup sizeGroup;
    @BindView(R.id.find_p_key_small)
    RadioButton radioSmallMediumSize;
    @BindView(R.id.find_p_key_big)
    RadioButton radioBigSize;


    @BindView(R.id.find_p_key_dog_fense)
    CheckBox dogFense;
    @BindView(R.id.find_p_key_dog_s_cage)
    CheckBox dogSmallCage;
    @BindView(R.id.find_p_key_dog_b_cage)
    CheckBox dogBigCage;
    @BindView(R.id.find_p_key_dog_toliet)
    CheckBox dogToliet;


    @BindView(R.id.find_p_key_cat_cage)
    CheckBox catCage;
    @BindView(R.id.find_p_key_cat_tower)
    CheckBox catTower;
    @BindView(R.id.find_p_key_cat_sand)
    CheckBox catSandToliet;


    @BindView(R.id.find_p_key_path)
    CheckBox path;
    @BindView(R.id.find_p_key_ground)
    CheckBox ground;
    @BindView(R.id.find_p_key_pool)
    CheckBox swimPool;

    private PensionModel pensionModel = new PensionModel();
    private ProgressDialog mProgressDialog;
    private int petType;
    private int petSize;  // 1 = small 2 = big  3 = all ( cat is all )
    private String areaSection="";


    /**
     * 정말 중요한 거
     * 고양이의 경우 따로 크기를 받지 않기 때문에
     * 크기 검색에 있어서 디폴트값으로 '모두가능' 이라고 검색했다고 해야합니다
     **/

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.find_p_key_cat_radio:
                    sizeLayout.setVisibility(View.GONE);
                    thingLayout.setVisibility(View.VISIBLE);
                    dogThingLayout.setVisibility(View.GONE);
                    catThingLayout.setVisibility(View.VISIBLE);
                    environLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_p_key_dog_radio:
                    sizeLayout.setVisibility(View.VISIBLE);
                    thingLayout.setVisibility(View.VISIBLE);
                    dogThingLayout.setVisibility(View.VISIBLE);
                    catThingLayout.setVisibility(View.GONE);
                    environLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_p_key_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.find_p_key_done:
                    showProgressDialog();
                    String thing = makeThingString();
                    String environment = makeEnvironString();

                    if(!checkVaild(thing,environment)){
                        hideProgressDialog();
                        return;
                    }else{
                        System.out.println("일단 베일드 하다 ");
                        System.out.println("선택지역 : "+areaSection);
                        System.out.println("동물타입 : "+petSize);
                        System.out.println("동물사이즈 : "+petSize);
                        System.out.println("thing 조건 1 : "+thing);
                        System.out.println("environment 조건 2 : "+environment);

                        Intent intent = new Intent(FindPensionKeywordActivity.this, ResultPensionActivity.class);
                        intent.putExtra("areaSection", areaSection);
                        intent.putExtra("petSize", petSize);
                        intent.putExtra("petType", petType);
                        intent.putExtra("thing", thing);
                        intent.putExtra("environment", environment);

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
                case R.id.find_p_key_dog_radio:
                    petType = 1;
                    break;
                case R.id.find_p_key_cat_radio:
                    petType = 2;
                    petSize = 3; // 무조건 3으로 고정
                    break;
                /**  1= small 2 = big 이 선택지는 강아지만을 위한 경우 **/
                case R.id.find_p_key_small:
                    petSize = 1;
                    break;
                case R.id.find_p_key_big:
                    petSize = 2;
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pension_keyword);
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
        }
        else if(radioCat.isChecked()){
            if(catCage.isChecked())
                thingString += "캔넬 ";
            if(catSandToliet.isChecked())
                thingString += "배변패드 or 모래화장실 ";
            if(catTower.isChecked())
                thingString += "캣타워 ";
        }

        return thingString;
    }

    private String makeEnvironString(){
        String environmentString = "";
        if(path.isChecked())
            environmentString += "산책로 ";
        if(ground.isChecked())
            environmentString += "운동장 ";
        if(swimPool.isChecked())
            environmentString += "풀장 ";

        return environmentString;
    }

    private boolean checkVaild(String thing, String environment){
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

        if(environment.equals("")){
            Snackbar.make(getWindow().getDecorView().getRootView(), "제공하는 주변시설을 1개 이상 선택해주세요", Snackbar.LENGTH_SHORT).show();
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
