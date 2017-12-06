package com.example.lidia.appproject2017_2.Activity.DetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Activity.BasicActivity;
import com.example.lidia.appproject2017_2.Activity.CommentActivity;
import com.example.lidia.appproject2017_2.Adapter.SliderAdapter;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Listener.OnGetImageListener;
import com.example.lidia.appproject2017_2.Model.CafeModel;
import com.example.lidia.appproject2017_2.Model.EtcModel;
import com.example.lidia.appproject2017_2.Model.PensionModel;
import com.example.lidia.appproject2017_2.Model.RestModel;
import com.example.lidia.appproject2017_2.Model.StoreModel;
import com.example.lidia.appproject2017_2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestDetailActivity extends BasicActivity {


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
    private RestModel restModel = new RestModel();
    private String areaType;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    // 아래는 모두 영상용이다

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.storeDetail_heart:
                    if (!like.isChecked()) {
                        // 클릭할때 마다 해당 가게의 like값 - 1
                        Snackbar.make(getWindow().getDecorView().getRootView(), "좋아요를 취소하셨습니다", Snackbar.LENGTH_SHORT).show();
                        restModel.onLoveUnClicked(databaseReference.child("Rest").child(areaType));
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "좋아요를 클릭하셨습니다", Snackbar.LENGTH_SHORT).show();
                        restModel.onLoveClicked(databaseReference.child("Rest").child(areaType));
                    }
                    break;
                case R.id.storeDetail_review:
                    Intent intent = new Intent(RestDetailActivity.this,CommentActivity.class);
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


        // 구글맵 초기화
        MapsInitializer.initialize(this);
        map.onCreate(savedInstanceState);
        map.onResume();

        Rest rest = (Rest) getIntent().getSerializableExtra("rest");
        setRestData(rest);
        areaType = rest.getSectionArea();

        viewPager= findViewById(R.id.storeDetail_viewpager);

        like.setOnClickListener(listener);
        review.setOnClickListener(listener);
        back.setOnClickListener(listener);

    }



    private void setRestData(final Rest r){
        storeType.setText("음식점");
        storeTitle.setText(r.getName());
        address.setText(r.getWholeAddress());
        phone.setText(r.getPhone());
        web.setText(r.getWeb());
        priceOrTimeFixed.setText("영업시간");
        priceOrTimeUserInput.setText(r.getTime());
        plus.setText(r.getPlusDescription());
        caution.setText(r.getCaution());
        thing.setText(r.getThings());
        environOrFoodFixed.setText("반려동물음식 판매여부");

        // 반려동물 사이즈 입력
        switch (r.getIsFood()){
            case 1:
                environOrFoodUserInput.setText("판매함");
                break;
            case 2:
                environOrFoodUserInput.setText("판매하지않음");
                break;
        }

        // 반려동물 사이즈 입력
        switch (r.getAnimalSize()){
            case 1:
                animalSize.setText("소+중형");
                break;
            case 2:
                animalSize.setText("대형");
                break;
            case 3:
                animalSize.setText("모두가능");
                break;
        }

        // 반려동물 타입
        switch (r.getAnimalType()){
            case 1:
                animalTypeUserInput.setText("반려견");
                petTypeImage.setImageResource(R.drawable.dogblack);
                break;
            case 2:
                animalTypeUserInput.setText("반려묘");
                petTypeImage.setImageResource(R.drawable.catblack);
                break;
        }

        // 이미지 가져와서 넣기
        restModel.getRestImages(r.getUid());
        restModel.setImageListener(new OnGetImageListener() {
            @Override
            public void getImage(List<String> imageList) {
                list.addAll(imageList);
                sliderAdapter = new SliderAdapter(RestDetailActivity.this, list);
                viewPager.setAdapter(sliderAdapter);
            }
        });

        // 위치 셋팅하기
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mainMap = googleMap;
                lat = r.getLat();
                log = r.getLog();

                LatLng latLng = new LatLng(lat,log);
                mainMap.addMarker(new MarkerOptions().position(latLng));
                mainMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            }
        });
    }

}
