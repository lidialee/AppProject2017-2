package com.example.lidia.appproject2017_2.Activity.ResultActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Activity.BasicActivity;
import com.example.lidia.appproject2017_2.Adapter.RestRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.ResultCRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.ResultRRecyclerAdapter;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Listener.OnRestChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchRestResultListener;
import com.example.lidia.appproject2017_2.Model.RestModel;
import com.example.lidia.appproject2017_2.Model.RestModel;
import com.example.lidia.appproject2017_2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultRestActivity extends BasicActivity {

    @BindView(R.id.research_r_back)
    ImageView back;

    @BindView(R.id.research_r_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.area_r)
    TextView area;

    @BindView(R.id.pet_r)
    TextView pet;

    private RestModel restModel = new RestModel();
    private ResultRRecyclerAdapter recyclerAdapter;
    private String areaSection;
    private int petSize;
    private int petType;
    private String thing;
    private int isFood;

    // 리사이클러뷰 리스너
    OnSearchRestResultListener searchRestResultListener = new OnSearchRestResultListener() {
        @Override
        public void searchResult(List<Rest> list) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerAdapter.setRestList(list);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_rest);
        ButterKnife.bind(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setInfo(getIntent());
        restModel.searchRest(areaSection,petSize,petType,thing,isFood);

        recyclerAdapter = new ResultRRecyclerAdapter(restModel.getRestList(),this);
        restModel.setRestResultListener(searchRestResultListener);
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
