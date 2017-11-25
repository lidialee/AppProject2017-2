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

public class EtcImage3Activity extends BasicActivity {

    @BindView(R.id.etc3_back)
    ImageView backStep;

    @BindView(R.id.etc3_done)
    ImageView done;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.etc3_back:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.etc3_done:
                    // 이게 둘리면 이제 서버로 저장되야겠지
                    Intent intent = new Intent(EtcImage3Activity.this, MainFindActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_etc_image);
        ButterKnife.bind(this);

        backStep.setOnClickListener(listener);
        done.setOnClickListener(listener);
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
