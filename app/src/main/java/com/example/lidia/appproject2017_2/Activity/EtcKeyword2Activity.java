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

public class EtcKeyword2Activity extends BasicActivity {
    @BindView(R.id.etc2_back)
    ImageView backStep;

    @BindView(R.id.etc2_next)
    ImageView nextStep;

    @BindView(R.id.etc2_group_type)
    RadioGroup radioTypeGroup;
    @BindView(R.id.etc2_dog)
    RadioButton radiaDog;
    @BindView(R.id.etc2_cat)
    RadioButton radiaCat;

    // 크기 결정
    @BindView(R.id.etc2_group_size)
    RadioGroup radioSizeGroup;
    @BindView(R.id.etc2_radio_small)
    RadioButton radioSmallSize;
    @BindView(R.id.etc2_radio_big)
    RadioButton radioBigSize;
    @BindView(R.id.etc2_radio_all)
    RadioButton radioAll;

    @BindView(R.id.etc2_fense)
    CheckBox checkFense;
    @BindView(R.id.etc2_cage)
    CheckBox checkCage;
    @BindView(R.id.etc2_toliet)
    CheckBox checkToliet;

    private int animalType = 0;
    private int animalSize = 0;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.etc2_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.etc2_next:
                    String thing = makeThingString();

                    if (!checkVaild(thing))
                        return;

                    /**  앞 액티비티에서 온 번들에 데이터를 더 추가해서 마지막으로 보냅시다 **/
                    Bundle bundle2 = getIntent().getExtras();
                    bundle2.putInt("animalType", animalType);
                    bundle2.putInt("animalSize", animalSize);
                    bundle2.putString("thing", thing);

                    Intent intent = new Intent(EtcKeyword2Activity.this, EtcImage3Activity.class);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.etc2_dog:
                    animalType = 1;
                    break;
                case R.id.etc2_cat:
                    animalType = 2;
                    break;

                /**  1= small 2 = big  3 = 모두 가능 **/
                case R.id.etc2_radio_small:
                    animalSize = 1;
                    break;
                case R.id.etc2_radio_big:
                    animalSize = 2;
                    break;
                case R.id.etc2_radio_all:
                    animalSize = 3;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_etc_keyword);
        ButterKnife.bind(this);

        backStep.setOnClickListener(listener);
        nextStep.setOnClickListener(listener);

        radioTypeGroup.setOnCheckedChangeListener(radioGroupListener);
        radioSizeGroup.setOnCheckedChangeListener(radioGroupListener);
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


    /** 끝에 반드시 빈칸을 남겨두자. 나중에 split의 기준이 됩니다.**/
    private String makeThingString() {
        String thingString = "";

        if (checkFense.isChecked())
            thingString += "팬스 ";
        if (checkCage.isChecked())
            thingString += "소형캔넬 ";
        if (checkToliet.isChecked())
            thingString += "배변패드 or 모래화장실 ";

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

        if (thing.equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "제공하는 물품을 1개 이상 선택해주세요", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
