package com.example.lidia.appproject2017_2.Fragment.RankingShop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lidia.appproject2017_2.Adapter.RestRecyclerAdapter;
import com.example.lidia.appproject2017_2.Class.Rest;
import com.example.lidia.appproject2017_2.Listener.OnRestChangedListener;
import com.example.lidia.appproject2017_2.Model.RestModel;
import com.example.lidia.appproject2017_2.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFoodFragment extends Fragment {

    private RecyclerView recyclerView;
    private Spinner areaSpinner;
    private String choiceArea = "서울특별시";
    private RestModel restModel = new RestModel();
    private RestRecyclerAdapter recyclerAdapter;

    // 스피너 아이템 고르기 리스너
    AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            choiceArea = areaSpinner.getSelectedItem().toString();
            restModel.getRest(choiceArea);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            choiceArea = "서울특별시";
        }
    };

    // 리사이클러뷰 리스너
    OnRestChangedListener restListChangeListener = new OnRestChangedListener() {
        @Override
        public void getRest(List<Rest> restList) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerAdapter.setRestList(restList);
        }
    };


    public RankFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rank_food, container, false);
        recyclerView = rootView.findViewById(R.id.base_recycler_rest);

        areaSpinner = rootView.findViewById(R.id.area_spinner_ranking_rest);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.arealist,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(arrayAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        restModel.getRest(choiceArea);

        recyclerAdapter = new RestRecyclerAdapter(restModel.getRestList(),getContext());
        restModel.setRestChangedListener(restListChangeListener);
        recyclerView.setAdapter(recyclerAdapter);

        // 스피너 선택하면 나타나기
        areaSpinner.setOnItemSelectedListener(spinnerListener);

        // 스피너 사이즈 줄이기
        try {
            Field popup =Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow)popup.get(areaSpinner);
            popupWindow.setHeight(1000);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
