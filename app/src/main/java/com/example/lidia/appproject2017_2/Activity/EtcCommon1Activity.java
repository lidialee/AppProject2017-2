package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lidia.appproject2017_2.DialogFragment.CheckLocaDialog;
import com.example.lidia.appproject2017_2.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EtcCommon1Activity extends BasicActivity {
    @BindView(R.id.etc_x)
    ImageView close;

    @BindView(R.id.etc_next)
    ImageView nextStep;

    @BindView(R.id.etc_address_btn)
    ImageView mapIcon;

    @BindView(R.id.etc_type_edit)
    EditText typeEdit;

    @BindView(R.id.etc_title_edit)
    EditText nameEdit;

    @BindView(R.id.etc_address_edit)
    EditText areaEdit;

    @BindView(R.id.etc_phone)
    EditText phoneEdit;

    @BindView(R.id.etc_web)
    EditText webEdit;

    @BindView(R.id.etc_plus)
    EditText plusEdit;

    @BindView(R.id.etc_caution)
    EditText cautionEdit;

    private String area = "";                                    // area = splitArea()함수를 통해서 받아오는 값,
    private String type,name, wholeArea, phone, web, plus, caution; // wholeArea = 사용자 입력 주소
    private String price ="기입 x";
    private double lat, log;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.etc_x:
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.etc_next:
                    /** checkVaild에서 false 보내면 번들도, 인텐트도 보내지 않는다. **/
                    if (!checkVaild()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "빈칸이 존재합니다", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(EtcCommon1Activity.this, EtcKeyword2Activity.class);
                    intent.putExtras(makeBundle());
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.etc_address_btn:
                    setMapDialog();
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
        mapIcon.setOnClickListener(listener);
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

    private void setMapDialog() {
        final Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> list = null;
        String userInput = areaEdit.getText().toString();
        try {
            /** 서버로 부터 주소값이 들어온다**/
            list = geocoder.getFromLocationName(userInput, 10); // 지역 이름 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(), "주소를 입력해주세요", Snackbar.LENGTH_SHORT).show();

        }

        if (list != null) {
            if (list.size() == 0) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "해당되는 주소 정보는 없습니다", Snackbar.LENGTH_SHORT).show();
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);

                /** 위경도 가져오기, 그리고 위치 확인 다이아로그 띄우기**/
                lat = addr.getLatitude();
                log = addr.getLongitude();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                android.support.v4.app.DialogFragment mapDialog = CheckLocaDialog.newInstance(lat, log);
                mapDialog.show(fragmentTransaction, "dialog");

                /**
                 * 공백을 기준으로 2번째 문자열 가져오기
                 * 주소가 무조건 대한민국으로 시작하기 때문에
                 * 대한민국 바로 다음의 지역 (서을특별시, 인천광역시 등등)을 리턴값으로 하는 함수**/
                wholeArea = addr.getAddressLine(0);
                String[] addList = wholeArea.split(" ");
                area = addList[1];

            }
        }
    }


    /**
     * 각 입력값 체크함수
     * 하나라도 true면 false를 리턴하여
     * 다음 액티비티로 이동하지 않도록 한다
     * <p>
     * 주! 여기서 각 스트링값도 저장한다
     **/
    private boolean checkVaild() {
        Boolean[] list = new Boolean[8];

        name = nameEdit.getText().toString();
        list[0] = name.equals("");

        wholeArea = areaEdit.getText().toString();
        list[1] = wholeArea.equals("");

        phone = phoneEdit.getText().toString();
        list[2] = phone.equals("");

        web = webEdit.getText().toString();
        list[3] = web.equals("");

        type = typeEdit.getText().toString();
        list[4] = type.equals("");

        plus = plusEdit.getText().toString();
        list[5] = plus.equals("");

        caution = cautionEdit.getText().toString();
        list[6] = caution.equals("");

        list[7] = area.equals("");

        for (Boolean aList : list) {
            if (aList)
                return false;
        }
        return true;
    }

    /**
     * 번들 만들기
     **/
    private Bundle makeBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("name", name);
        bundle.putString("wholeAddress", wholeArea);
        bundle.putString("area", area);
        bundle.putString("phone", phone);
        bundle.putString("web", web);
        bundle.putString("plus", plus);
        bundle.putString("caution", caution);
        bundle.putDouble("lat", lat);
        bundle.putDouble("log", log);
        return bundle;
    }
}
