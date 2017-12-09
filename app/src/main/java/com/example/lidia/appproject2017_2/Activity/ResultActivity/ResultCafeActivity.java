package com.example.lidia.appproject2017_2.Activity.ResultActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Activity.BasicActivity;
import com.example.lidia.appproject2017_2.Adapter.CafeRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.RestRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.ResultCRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.ResultPRecyclerAdapter;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnCafeChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchCafeResultListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchPensionResultListener;
import com.example.lidia.appproject2017_2.Model.CafeModel;
import com.example.lidia.appproject2017_2.Model.PensionModel;
import com.example.lidia.appproject2017_2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultCafeActivity extends BasicActivity {
    @BindView(R.id.research_c_back)
    ImageView back;

    @BindView(R.id.research_c_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.area_c)
    TextView area;

    @BindView(R.id.pet_c)
    TextView pet;

    private CafeModel cafeModel = new CafeModel();
    private ResultCRecyclerAdapter recyclerAdapter;
    private String areaSection;
    private int petSize;
    private int petType;
    private String thing;
    private int isFood;

    // 리사이클러뷰 리스너
    OnSearchCafeResultListener searchCafeResultListener = new OnSearchCafeResultListener() {
        @Override
        public void searchResult(List<Cafe> list) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerAdapter.setCafeList(list);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cafe);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setInfo(getIntent());
        cafeModel.searchCafe(areaSection,petSize,petType,thing,isFood);

        recyclerAdapter = new ResultCRecyclerAdapter(cafeModel.getCafeList(),this);
        cafeModel.setCafeResultListener(searchCafeResultListener);
        recyclerView.setAdapter(recyclerAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setInfo(Intent intent){
        areaSection = intent.getStringExtra("areaSection");
        petSize = intent.getIntExtra("petSize",100);
        petType = intent.getIntExtra("petType",100);
        thing = intent.getStringExtra("thing");
        isFood = intent.getIntExtra("isFood",100);

        area.setText(areaSection);

        if(petType == 1){
            pet.setText("반려견");
        }
        if(petType == 2){
            pet.setText("반려묘");
        }
    }
}
