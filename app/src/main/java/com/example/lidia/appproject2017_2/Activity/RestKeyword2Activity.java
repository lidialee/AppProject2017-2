package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
    RadioGroup radiaGroup;

    @BindView(R.id.rest2_dog)
    RadioButton radiaDog;

    @BindView(R.id.rest2_cat)
    RadioButton radiaCat;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rest2_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rest2_next:
                    Intent intent = new Intent(RestKeyword2Activity.this, RestImage3Activity.class);
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
            switch (i) {
                case R.id.rest2_dog:
                    dogLayout.setVisibility(View.VISIBLE);
                    catLayout.setVisibility(View.GONE);
                    guideText.setVisibility(View.GONE);
                    break;
                case R.id.rest2_cat:
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
        setContentView(R.layout.activity_register_rest_keyword);
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
