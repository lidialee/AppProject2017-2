package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PensionKeyword2Activity extends BasicActivity {

    @BindView(R.id.pension2_back)
    ImageView backStep;

    @BindView(R.id.pension2_next)
    ImageView nextStep;

    @BindView(R.id.dog_layout)
    RelativeLayout dogLayout;

    @BindView(R.id.cat_layout)
    RelativeLayout catLayout;

    @BindView(R.id.guide_text)
    TextView guideText;

    @BindView(R.id.pension2_group_type)
    RadioGroup radiaGroup;

    @BindView(R.id.pension2_dog)
    RadioButton radiaDog;

    @BindView(R.id.pension2_cat)
    RadioButton radiaCat;

    // 이동 이미지 클릭리스너
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pension2_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.pension2_next:
                    Intent intent = new Intent(PensionKeyword2Activity.this, PensionImage3Activity.class);
                    // 넘어갈때 인텐트로 스트링 엄청 넘어간다
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
            }
        }
    };

    // 동물 타입 라디오타입 리스너, 선택에 따라 뷰 바꾸기
    RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.pension2_dog:
                    dogLayout.setVisibility(View.VISIBLE);
                    catLayout.setVisibility(View.GONE);
                    guideText.setVisibility(View.GONE);
                    break;
                case R.id.pension2_cat:
                    dogLayout.setVisibility(View.GONE);
                    catLayout.setVisibility(View.VISIBLE);
                    guideText.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pension_keyword);
        ButterKnife.bind(this);

        backStep.setOnClickListener(listener);
        nextStep.setOnClickListener(listener);
        radiaGroup.setOnCheckedChangeListener(radioListener);
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


}
