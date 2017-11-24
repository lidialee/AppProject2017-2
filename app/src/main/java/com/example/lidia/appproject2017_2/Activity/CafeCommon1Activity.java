package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CafeCommon1Activity extends BasicActivity {

    @BindView(R.id.cafe_x)
    ImageView close;

    @BindView(R.id.cafe_next)
    ImageView nextStep;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cafe_x:
                    finish();
                    break;
                case R.id.cafe_next:
                    Intent intent = new Intent(CafeCommon1Activity.this, CafeKeyword2Activity.class);
                    // 넘어갈때 인텐트로 스트링 엄청 넘어간다
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cafe_common);
        ButterKnife.bind(this);
        nextStep.setOnClickListener(listener);
        close.setOnClickListener(listener);
    }
}
