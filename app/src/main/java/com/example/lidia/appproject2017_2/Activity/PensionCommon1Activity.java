package com.example.lidia.appproject2017_2.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PensionCommon1Activity extends BasicActivity {

    @BindView(R.id.pension_x)
    ImageView close;

    @BindView(R.id.pension_next)
    ImageView nextStep;

    @BindView(R.id.pension_address_btn)
    ImageView mapIcon;

    @BindView(R.id.pension_address_edit)
    EditText mapEdit;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pension_x:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.pension_next:
                    Intent intent = new Intent(PensionCommon1Activity.this, PensionKeyword2Activity.class);
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
        setContentView(R.layout.activity_register_pension_common);
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


    private void 
}
