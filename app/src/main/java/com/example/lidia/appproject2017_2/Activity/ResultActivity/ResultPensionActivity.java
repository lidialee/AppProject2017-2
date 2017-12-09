package com.example.lidia.appproject2017_2.Activity.ResultActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Activity.BasicActivity;
import com.example.lidia.appproject2017_2.Adapter.ResultPRecyclerAdapter;
import com.example.lidia.appproject2017_2.Class.Pension;
import com.example.lidia.appproject2017_2.Listener.OnSearchPensionResultListener;
import com.example.lidia.appproject2017_2.Model.PensionModel;
import com.example.lidia.appproject2017_2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultPensionActivity extends BasicActivity {

    @BindView(R.id.research_p_back)
    ImageView back;

    @BindView(R.id.research_p_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.area_p)
    TextView area;

    @BindView(R.id.pet_p)
    TextView pet;

    private PensionModel pensionModel = new PensionModel();
    private ResultPRecyclerAdapter recyclerAdapter;
    private String areaSection;
    private int petSize;
    private int petType;
    private String thing;
    private String environment;

    // 리사이클러뷰 리스너
    OnSearchPensionResultListener searchPensionResultListener = new OnSearchPensionResultListener() {
        @Override
        public void searchResult(List<Pension> list) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerAdapter.setPensionList(list);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pension);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setInfo(getIntent());
        pensionModel.searchPension(areaSection,petSize,petType,thing,environment);

        recyclerAdapter = new ResultPRecyclerAdapter(pensionModel.getPensionList(),this);
        pensionModel.setSearchPensionResultListener(searchPensionResultListener);
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
        environment = intent.getStringExtra("environment");

        area.setText(areaSection);

        if(petType == 1){
            pet.setText("반려견");
        }
        if(petType == 2){
            pet.setText("반려묘");
        }
    }
}
