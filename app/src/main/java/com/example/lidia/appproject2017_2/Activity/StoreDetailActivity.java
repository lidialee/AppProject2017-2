package com.example.lidia.appproject2017_2.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Adapter.SliderAdapter;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.DialogFragment.CheckLocaDialog;
import com.example.lidia.appproject2017_2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreDetailActivity extends BasicActivity {


    @BindView(R.id.storeDetail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.storeDetail_storeType)
    TextView storeType;

    @BindView(R.id.storeDetail_storeTitle)
    TextView storeTitle;

    @BindView(R.id. likeCount)
    TextView likeCount;

    @BindView(R.id.storeDetail_address)
    TextView address;

    @BindView(R.id.storeDetail_phone)
    TextView phone;

    @BindView(R.id.storeDetail_web)
    TextView web;

    @BindView(R.id.storeDetail_price_time_fixed)
    TextView priceOrTimeFixed;                          // 타입에 따라 바꿔야 겠지 , 기타의 경우는 완전히 없애기

    @BindView(R.id.storeDetail_price_time_unfixed)      // 기타의 경우 완전히 레이아웃에서 없애기
    TextView priceOrTimeUserInput;

    @BindView(R.id.storeDetail_plue_text_unfixed)
    TextView plus;

    @BindView(R.id.storeDetail_caution_text_unfixed)
    TextView caution;

    @BindView(R.id.storeDetail_available_animalType_unfixed)
    TextView animalTypeUserInput;

    @BindView(R.id.storeDetail_animalSized_unfixed)
    TextView animalSize;

    @BindView(R.id.storeDetail_things_unfixed)
    TextView thing;

    @BindView(R.id.storeDetail_environ_food_fixed)            // 타입에 따라 바꿔야 겠지 , 기타의 경우는 완전히 없애기
    TextView environOrFoodFixed;

    @BindView(R.id.storeDetail_environ_food_unfixed)    // 타입에 따라 바꿔야 겠지 , 기타의 경우는 완전히 없애기
    TextView environOrFoodUserInput;

    @BindView(R.id.storeDetail_map)
    MapView map;

    @BindView(R.id.storeDetail_petType_image)                 // 타입에 따라서 고양이 사진으로 혹은 강아지 사진으로 바꿔야 한다
    ImageView petTypeImage;

    @BindView(R.id.storeDetail_back)
    ImageView back;

    @BindView(R.id.storeDetail_review)
    ImageView review;

    @BindView(R.id.storeDetail_heart)
    CheckBox like;

    private List<String> list = new ArrayList<>();
    private SliderAdapter sliderAdapter;
    private GoogleMap mainMap;
    private double lat, log;
    private Pension pension = null;
    private Cafe cafe = null;
    private Rest rest = null;
    private Etc etc = null;

    // 아래는 모두 영상용이다

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.storeDetail_heart:
                    if(!like.isChecked()){
                        // 클릭할때 마다 해당 가게의 like값 - 1
                        Snackbar.make(getWindow().getDecorView().getRootView(), "좋아요를 취소하셨습니다", Snackbar.LENGTH_SHORT).show();
                        System.out.println(like.isChecked()+" -----------");
                    }else{
                        // 클릭할때 마다 해당 가게의 like값 + 1
                        Snackbar.make(getWindow().getDecorView().getRootView(), "좋아요를 클릭하셨습니다", Snackbar.LENGTH_SHORT).show();
                        System.out.println(like.isChecked()+" -----------");
                    }
                    break;
                case R.id.storeDetail_review:
                    Intent intent = new Intent(StoreDetailActivity.this,CommentActivity.class);
                    // 인텐트에 해당 가게의 uid를 보내야겠지?
                    startActivity(intent);
                    break;
                case R.id.storeDetail_back:
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        ButterKnife.bind(this);

       // String storeUid = getIntent().getStringExtra("storeUid");

        sliderAdapter = new SliderAdapter(StoreDetailActivity.this, list);

        // 이건 등록 이후 확인
        //final Bundle bundle = getIntent().getExtras();

        int storeType = getIntent().getIntExtra("type",1);

        switch (storeType){
            case 1:
                pension = (Pension) getIntent().getSerializableExtra("pension");
                setData(pension,savedInstanceState);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }



        // 뷰 페이지
        list.add("https://images.trvl-media.com/hotels/18000000/17410000/17407900/17407880/0295344f_z.jpg");
        list.add("https://s-ec.bstatic.com/images/hotel/max1024x768/878/87842344.jpg");
        viewPager= findViewById(R.id.storeDetail_viewpager);
        viewPager.setAdapter(sliderAdapter);


        like.setOnClickListener(listener);
        review.setOnClickListener(listener);
        back.setOnClickListener(listener);

    }

    private void setData(final Pension p, Bundle savedInstanceState){
        // 아래는 임시 보여주기 입니다, 나중에 지우세요
        storeTitle.setText(p.getName());
        address.setText(p.getWholeAddress());
        phone.setText(p.getPhone());
        web.setText(p.getWeb());
        priceOrTimeUserInput.setText(p.getPrice());
        plus.setText(p.getPlusDescription());
        caution.setText(p.getCaution());
        thing.setText(p.getThings());
        environOrFoodUserInput.setText(p.getEnvironment());
        animalSize.setText(p.getAnimalSize());
        animalTypeUserInput.setText(p.getAnimalType());

        // 구글맵
        MapsInitializer.initialize(this);
        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mainMap = googleMap;
                lat = p.getLat();
                log = p.getLog();

                LatLng latLng = new LatLng(lat,log);
                mainMap.addMarker(new MarkerOptions().position(latLng));
                mainMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            }
        });
    }


}
