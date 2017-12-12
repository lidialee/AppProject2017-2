package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestKeyword2Activity extends BasicActivity {

    @BindView(R.id.rest2_back)
    ImageView backStep;

    @BindView(R.id.rest2_next)
    ImageView nextStep;

    @BindView(R.id.rest2_dog_layout)
    RelativeLayout dogLayout;
    @BindView(R.id.rest2_cat_layout)
    RelativeLayout catLayout;
    @BindView(R.id.rest2_guide_text)
    TextView guideText;

    @BindView(R.id.rest2_group_type)
    RadioGroup radioTypeGroup;
    @BindView(R.id.rest2_dog)
    RadioButton radioDog;
    @BindView(R.id.rest2_cat)
    RadioButton radioCat;

    // 크기 결정
    @BindView(R.id.rest2_group_size)
    RadioGroup radioSizeGroup;
    @BindView(R.id.rest2_radio_small)
    RadioButton radioSmallSize;
    @BindView(R.id.rest2_radio_big)
    RadioButton radioBigSize;
    @BindView(R.id.rest2_radio_all)
    RadioButton radioAll;

    // 강아지 - 물품 체크박스
    @BindView(R.id.rest2_d_fense)
    CheckBox checkFense;
    @BindView(R.id.rest2_d_small)
    CheckBox checkSmall;
    @BindView(R.id.rest2_d_big)
    CheckBox checkBig;
    @BindView(R.id.rest2_d_toliet)
    CheckBox checkToliet;
    @BindView(R.id.rest2_d_room)
    CheckBox checkDogRoom;

    // 고양이 - 물품 체크박스
    @BindView(R.id.rest2_c_cage)
    CheckBox checkCage;
    @BindView(R.id.rest2_c_sand)
    CheckBox checkSand;
    @BindView(R.id.rest2_c_tower)
    CheckBox checkTower;
    @BindView(R.id.rest2_c_room)
    CheckBox checkCatRoom;

    // 음식 여부
    @BindView(R.id.rest2_group_food)
    RadioGroup radioFoodGroup;
    @BindView(R.id.rest2_yes)
    RadioButton foodYes;
    @BindView(R.id.rest2_no)
    RadioButton foodNo;

    private int animalType = 0;
    private int animalSize = 0;
    private int isFood = 0;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rest2_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rest2_next:
                    String thing = makeThingString();

                    if (!checkVaild(thing))
                        return;

                    /**  앞 액티비티에서 온 번들에 데이터를 더 추가해서 마지막으로 보냅시다 **/
                    Bundle bundle2 = getIntent().getExtras();
                    bundle2.putInt("animalType", animalType);
                    bundle2.putInt("animalSize", animalSize);
                    bundle2.putInt("isFood", isFood);
                    bundle2.putString("thing", thing);

                    Intent intent = new Intent(RestKeyword2Activity.this, RestImage3Activity.class);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                    break;

            }
        }
    };

    // 동물 타입 라디오타입 리스너, 선택에 따라 뷰 바꾸기
    RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.rest2_dog:
                    animalType = 1;
                    dogLayout.setVisibility(View.VISIBLE);
                    catLayout.setVisibility(View.GONE);
                    guideText.setVisibility(View.GONE);
                    break;
                case R.id.rest2_cat:
                    animalType = 2;
                    dogLayout.setVisibility(View.GONE);
                    catLayout.setVisibility(View.VISIBLE);
                    guideText.setVisibility(View.GONE);
                    break;

                /**  1= small 2 = big  3 = 모두 가능 **/
                case R.id.rest2_yes:
                    isFood = 1;
                    break;
                case R.id.rest2_no:
                    isFood = 2;
                    break;

                /**  1= small 2 = big  3 = 모두 가능 **/
                case R.id.rest2_radio_small:
                    animalSize = 1;
                    break;
                case R.id.rest2_radio_big:
                    animalSize = 2;
                    break;
                case R.id.rest2_radio_all:
                    animalSize = 3;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_rest_keyword);
        ButterKnife.bind(this);

        backStep.setOnClickListener(listener);
        nextStep.setOnClickListener(listener);

        radioTypeGroup.setOnCheckedChangeListener(radioGroupListener);
        radioSizeGroup.setOnCheckedChangeListener(radioGroupListener);
        radioFoodGroup.setOnCheckedChangeListener(radioGroupListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String makeThingString() {
        String thingString = "";
        /** 끝에 반드시 빈칸을 남겨두자. 나중에 split의 기준이 됩니다.**/

        if (radioDog.isChecked()) {
            if (checkFense.isChecked())
                thingString += "팬스 ";
            if (checkSmall.isChecked())
                thingString += "소형캔넬 ";
            if (checkBig.isChecked())
                thingString += "대형캔넬 ";
            if (checkToliet.isChecked())
                thingString += "배변패드 ";
            if (checkDogRoom.isChecked())
                thingString += "개인룸 ";
        } else if (radioCat.isChecked()) {
            if (checkCage.isChecked())
                thingString += "캔넬 ";
            if (checkSand.isChecked())
                thingString += "배변패드 or 모래화장실 ";
            if (checkTower.isChecked())
                thingString += "캣타워 ";
            if (checkCatRoom.isChecked())
                thingString += "개인룸 ";
        }

        return thingString;
    }

    private boolean checkVaild(String thing) {
        if (animalType == 0) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 타입을 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (animalSize == 0) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 사이즈을 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (isFood == 0) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "반려동물 음식 제공 여부를 선택해주세", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (thing.equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "제공하는 물품을 1개 이상 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
