package com.example.lidia.appproject2017_2.Activity.ResultActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lidia.appproject2017_2.Activity.BasicActivity;
import com.example.lidia.appproject2017_2.Adapter.EtcRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.ResultCRecyclerAdapter;
import com.example.lidia.appproject2017_2.Adapter.ResultERecyclerAdapter;
import com.example.lidia.appproject2017_2.Class.Cafe;
import com.example.lidia.appproject2017_2.Class.Etc;
import com.example.lidia.appproject2017_2.Listener.OnEtcChangedListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchCafeResultListener;
import com.example.lidia.appproject2017_2.Listener.OnSearchEtcResultListener;
import com.example.lidia.appproject2017_2.Model.CafeModel;
import com.example.lidia.appproject2017_2.Model.EtcModel;
import com.example.lidia.appproject2017_2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultEtcActivity extends BasicActivity {
    @BindView(R.id.research_e_back)
    ImageView back;

    @BindView(R.id.research_e_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.area_e)
    TextView area;

    @BindView(R.id.pet_e)
    TextView pet;


    private EtcModel etcModel = new EtcModel();
    private ResultERecyclerAdapter recyclerAdapter;
    private String areaSection;
    private int petSize;
    private int petType;
    private String thing;

    // 리사이클러뷰 리스너
    OnSearchEtcResultListener searchEtcResultListener = new OnSearchEtcResultListener() {
        @Override
        public void searchResult(List<Etc> list) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerAdapter.setEtcList(list);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_etc);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setInfo(getIntent());
        etcModel.searchEtc(areaSection,petSize,petType,thing);

        recyclerAdapter = new ResultERecyclerAdapter(etcModel.getEtcList(),this);
        etcModel.setEtcResultListener(searchEtcResultListener);
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

        area.setText(areaSection);

        if(petType == 1){
            pet.setText("반려견");
        }
        if(petType == 2){
            pet.setText("반려묘");
        }
    }
}
