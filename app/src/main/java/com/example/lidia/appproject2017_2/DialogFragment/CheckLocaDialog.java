package com.example.lidia.appproject2017_2.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public  class CheckLocaDialog extends android.support.v4.app.DialogFragment{

    private LatLng latLng;
    private GoogleMap mainMap;

    public static CheckLocaDialog newInstance(double lat, double log){
        CheckLocaDialog dialog = new CheckLocaDialog();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat",lat);
        bundle.putDouble("log",log);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latLng = new LatLng(getArguments().getDouble("lat"),getArguments().getDouble("log"));
        // 타이틀 없는 스타일 적용
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mapdialog, container, false);
        MapView mapView = rootView.findViewById(R.id.mapdialog);

        MapsInitializer.initialize(getActivity());
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mainMap = googleMap;
                mainMap.addMarker(new MarkerOptions().position(latLng));
                mainMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            }
        });
        return rootView;
    }


    @Override
    public int show(android.support.v4.app.FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

}
