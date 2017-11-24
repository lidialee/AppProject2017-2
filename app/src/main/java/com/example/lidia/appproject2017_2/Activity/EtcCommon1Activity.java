package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EtcCommon1Activity extends BasicActivity {
    @BindView(R.id.etc_x)
    ImageView close;

    @BindView(R.id.etc_next)
    ImageView nextStep;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.etc_x:
                    finish();
                    break;
                case R.id.etc_next:
                    Intent intent = new Intent(EtcCommon1Activity.this, EtcKeyword2Activity.class);
                    // 넘어갈때 인텐트로 스트링 엄청 넘어간다
                    startActivity(intent);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_etc_common);
        ButterKnife.bind(this);

        nextStep.setOnClickListener(listener);
        close.setOnClickListener(listener);
    }
}
