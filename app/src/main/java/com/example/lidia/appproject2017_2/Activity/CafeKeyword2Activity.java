package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CafeKeyword2Activity extends AppCompatActivity {
    @BindView(R.id.cafe2_back)
    ImageView backStep;

    @BindView(R.id.cafe2_next)
    ImageView nextStep;

    @BindView(R.id.cafe2_dog_layout)
    RelativeLayout dogLayout;

    @BindView(R.id.cafe2_cat_layout)
    RelativeLayout catLayout;

    @BindView(R.id.cafe2_guide_text)
    TextView guideText;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cafe2_back:
                    finish();
                    break;
                case R.id.cafe2_next:
                    Intent intent = new Intent(CafeKeyword2Activity.this, CafeImage3Activity.class);
                    // 넘어갈때 인텐트로 스트링 엄청 넘어간다
                    startActivity(intent);
                    break;
                case R.id.cafe2_dog_layout:
                    dogLayout.setVisibility(View.VISIBLE);
                    catLayout.setVisibility(View.GONE);
                    guideText.setVisibility(View.GONE);
                    break;
                case R.id.cafe2_cat_layout:
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
        setContentView(R.layout.activity_register_cafe_keyword);
        ButterKnife.bind(this);

        backStep.setOnClickListener(listener);
        nextStep.setOnClickListener(listener);
        dogLayout.setOnClickListener(listener);
        catLayout.setOnClickListener(listener);
    }
}
