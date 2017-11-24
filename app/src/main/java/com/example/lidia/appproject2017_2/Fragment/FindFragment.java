package com.example.lidia.appproject2017_2.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Activity.CafeCommon1Activity;
import com.example.lidia.appproject2017_2.Activity.EtcCommon1Activity;
import com.example.lidia.appproject2017_2.Activity.FindAreaActivity;
import com.example.lidia.appproject2017_2.Activity.MainFindActivity;
import com.example.lidia.appproject2017_2.Activity.PensionCommon1Activity;
import com.example.lidia.appproject2017_2.Activity.RestCommon1Activity;
import com.example.lidia.appproject2017_2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment{
    private static final int REQUEST_CODE_LOCATION = 2;
    private GoogleMap mainMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private ImageView find;
    private Dialog findDialog;
    private ImageView pension, cafe, rest, etc;



    // !!!!!! 이거 보내는 값만 다르지 하나로 뭉쳐버려
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            Intent intent = new Intent(getContext(), FindAreaActivity.class);
            if (id == R.id.find)
                findDialog.show();
            else {
                switch (id){
                    case R.id.find_dialog_pension:
                        intent.putExtra("storeNumber",1);
                        break;
                    case R.id.find_dialog_cafe:
                        intent.putExtra("storeNumber",2);
                        break;
                    case R.id.find_dialog_rest:
                        intent.putExtra("storeNumber",3);
                        break;
                    case R.id.find_dialog_etc:
                        intent.putExtra("storeNumber",4);
                        break;
                }
                startActivity(intent);
                findDialog.dismiss();
            }
        }
    };

    // fragment 지도 클릭 리스너
    private GoogleMap.OnMapClickListener mapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {}
    };

    // 지도 위 마커 클릭 리스너
    private GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }
    };
    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        // 가게 찾기 아이콘
        find = rootView.findViewById(R.id.find);
        // 구글 맵 초기화 과정
        MapView mapView = rootView.findViewById(R.id.googleMap_find);
        MapsInitializer.initialize(getActivity());
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mainMap = googleMap;
                mainMap.setOnMarkerClickListener(markerClickListener);
                mainMap.setOnMapClickListener(mapClickListener);
                mainMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(),12));
            }
        });


        // 찾기 아이콘에 리스너 부착
        find.setOnClickListener(listener);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingGPS();
        initiateDialog();
    }

    private LatLng getMyLocation() {
        LatLng currentLatLng = null;
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location currL = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            currentLatLng = new LatLng(currL.getLatitude(),currL.getLongitude());
        }
        return currentLatLng;
    }

    private void settingGPS(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

    }

    private void initiateDialog() {
        findDialog = new Dialog(getContext());
        findDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        findDialog.setContentView(R.layout.findicon_dialog);
        //findDialog.setCanceledOnTouchOutside(false);

        pension = findDialog.findViewById(R.id.find_dialog_pension);
        Glide.with(this).load(R.drawable.pension2).into(pension);

        cafe = findDialog.findViewById(R.id.find_dialog_cafe);
        Glide.with(this).load(R.drawable.cafe2).into(cafe);

        rest = findDialog.findViewById(R.id.find_dialog_rest);
        Glide.with(this).load(R.drawable.rest2).into(rest);

        etc = findDialog.findViewById(R.id.find_dialog_etc);
        Glide.with(this).load(R.drawable.etc2).into(etc);


        pension.setOnClickListener(listener);
        cafe.setOnClickListener(listener);
        rest.setOnClickListener(listener);
        etc.setOnClickListener(listener);

    }

}

