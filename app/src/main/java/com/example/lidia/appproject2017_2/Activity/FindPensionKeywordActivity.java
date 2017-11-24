package com.example.lidia.appproject2017_2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.lidia.appproject2017_2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindPensionKeywordActivity extends BasicActivity {

    @BindView(R.id.find_p_key_container_size)
    LinearLayout sizeLayout;

    @BindView(R.id.find_p_key_container_thing)
    LinearLayout thingLayout;

    @BindView(R.id.find_p_key_container_envir)
    LinearLayout environLayout;

    @BindView(R.id.find_p_key_dog_thing_layout)
    RelativeLayout dogThingLayout;

    @BindView(R.id.find_p_key_cat_thing_layout)
    RelativeLayout catThingLayout;

    @BindView(R.id.find_p_key_cat_radio)
    RadioButton radioCat;

    @BindView(R.id.find_p_key_dog_radio)
    RadioButton radioDog;

    @BindView(R.id.find_p_key_back)
    ImageView back;

    @BindView(R.id.find_p_key_done)
    ImageView done;

    /**
     * 정말 중요한 거
     * 고양이의 경우 따로 크기를 받지 않기 때문에
     * 크기 검색에 있어서 디폴트값으로 '모두가능' 이라고 검색했다고 해야합니다
     * **/

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.find_p_key_cat_radio:
                    sizeLayout.setVisibility(View.GONE);
                    thingLayout.setVisibility(View.VISIBLE);
                    dogThingLayout.setVisibility(View.GONE);
                    catThingLayout.setVisibility(View.VISIBLE);
                    environLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_p_key_dog_radio:
                    sizeLayout.setVisibility(View.VISIBLE);
                    thingLayout.setVisibility(View.VISIBLE);
                    dogThingLayout.setVisibility(View.VISIBLE);
                    catThingLayout.setVisibility(View.GONE);
                    environLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.find_p_key_back:
                   finish();
                    break;
                case R.id.find_p_key_done:
                    // 여기서 이제 디비에서 검색할 시간.
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pension_keyword);
        ButterKnife.bind(this);

        radioCat.setOnClickListener(listener);
        radioDog.setOnClickListener(listener);
        back.setOnClickListener(listener);
        done.setOnClickListener(listener);


    }
}
