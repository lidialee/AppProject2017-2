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
import android.widget.Toast;

import com.example.lidia.appproject2017_2.Activity.ResultActivity.ResultCafeActivity;
import com.example.lidia.appproject2017_2.Activity.ResultActivity.ResultEtcActivity;
import com.example.lidia.appproject2017_2.Activity.ResultActivity.ResultRestActivity;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Listener.OnSearchEtcResultListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchRestResultListener;
import com.example.lidia.appproject2017_2.Model.EtcModel;
import com.example.lidia.appproject2017_2.Model.RestModel;
import com.example.lidia.appproject2017_2.R;

import java.io.Serializable;
import java.util.List;

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

    @BindView(R.id.find_e_key_group_type)
    RadioGroup petTypeGroup;
    @BindView(R.id.find_e_key_cat_radio)
    RadioButton radioCat;
    @BindView(R.id.find_e_key_dog_radio)
    RadioButton radioDog;

    @BindView(R.id.find_e_key_group_size)
    RadioGroup sizeGroup;
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

    private ProgressDialog mProgressDialog;
    private int petType;
    private int petSize;  // 1 = small 2 = big  3 = all ( cat is all )
    private String areaSection = "";

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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
                    showProgressDialog();
                    String thing = makeThingString();

                    if (!checkVaild(thing)) {
                        hideProgressDialog();
                        return;
                    } else {
                        Intent intent = new Intent(FindEtcKeywordActivity.this, ResultEtcActivity.class);
                        intent.putExtra("areaSection", areaSection);
                        intent.putExtra("petSize", petSize);
                        intent.putExtra("petType", petType);
                        intent.putExtra("thing", thing);

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
            switch (i) {
                /** 동물 타입 라디오타입 리스너, 선택에 따라 뷰 바꾸기
                 *  1 = 강아지,  2 = 고양이  **/
                case R.id.find_e_key_dog_radio:
                    petType = 1;
                    break;
                case R.id.find_e_key_cat_radio:
                    petType = 2;
                    petSize = 3; // 무조건 3으로 고정
                    break;
                /**  1= small 2 = big 이 선택지는 강아지만을 위한 경우 **/
                case R.id.find_e_key_small:
                    petSize = 1;
                    break;
                case R.id.find_e_key_big:
                    petSize = 2;
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

        // 앞에서 받아온 지역 타입 저장해놓기
        areaSection = getIntent().getExtras().getString("areaType");

        // 라디오 버튼 리스너
        petTypeGroup.setOnCheckedChangeListener(radioGroupListener);
        sizeGroup.setOnCheckedChangeListener(radioGroupListener);

    }

    private String makeThingString() {
        String thingString = "";
        if (fense.isChecked())
            thingString += "팬스 ";
        if (toliet.isChecked())
            thingString += "배변패드 or 모래화장실";
        if (cage.isChecked())
            thingString += "소형캔넬 ";
        return thingString;
    }


    private boolean checkVaild(String thing) {
        if (areaSection.equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "지역이 선택되지 않은 에러 발생", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (petType == 0) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 타입을 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (petSize == 0) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 사이즈을 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (thing.equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "제공하는 서비스를 1개 이상 선택해주세요", Snackbar.LENGTH_SHORT).show();
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
