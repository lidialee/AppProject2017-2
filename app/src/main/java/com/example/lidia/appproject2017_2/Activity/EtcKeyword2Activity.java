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

public class EtcKeyword2Activity extends BasicActivity {
    @BindView(R.id.etc2_back)
    ImageView backStep;

    @BindView(R.id.etc2_next)
    ImageView nextStep;

//    @BindView(R.id.etc2_things_layout)
//    RelativeLayout dogLayout;
//

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.etc2_back:
                    finish();
                    break;
                case R.id.etc2_next:
                    Intent intent = new Intent(EtcKeyword2Activity.this, EtcImage3Activity.class);
                    // 넘어갈때 인텐트로 스트링 엄청 넘어간다
                    startActivity(intent);
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

    }
}
