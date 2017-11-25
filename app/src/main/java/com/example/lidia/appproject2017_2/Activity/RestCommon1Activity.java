package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestCommon1Activity extends BasicActivity {
    @BindView(R.id.rest_x)
    ImageView close;

    @BindView(R.id.rest_next)
    ImageView nextStep;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rest_x:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rest_next:
                    Intent intent = new Intent(RestCommon1Activity.this, RestKeyword2Activity.class);
                    // 넘어갈때 인텐트로 스트링 엄청 넘어간다
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_rest_common);
        ButterKnife.bind(this);

        nextStep.setOnClickListener(listener);
        close.setOnClickListener(listener);
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
