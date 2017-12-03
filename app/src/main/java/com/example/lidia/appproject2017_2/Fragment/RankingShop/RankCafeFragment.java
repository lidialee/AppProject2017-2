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

import com.example.lidia.appproject2017_2.Adapter.PensionRecyclerAdapter;
import com.example.lidia.appproject2017_2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankCafeFragment extends Fragment {


    private RecyclerView recyclerView;
    private Spinner areaSpinner;
    private DatabaseReference database;
    private String choiceArea;
    private int storeType;

    public RankCafeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_rank_cafe, container, false);
        recyclerView = rootView.findViewById(R.id.base_recycler_cafe);

        areaSpinner = rootView.findViewById(R.id.area_spinner_ranking_cafe);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.arealist,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(arrayAdapter);

        database = FirebaseDatabase.getInstance().getReference();

        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //PensionRecyclerAdapter adapter = new PensionRecyclerAdapter();
        //recyclerView.setAdapter(adapter);


        // 스피너 선택하면 나타나기
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choiceArea = areaSpinner.getSelectedItem().toString();
                System.out.println("지역 확인 "+choiceArea);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


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
