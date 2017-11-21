package com.example.lidia.appproject2017_2.Fragment;


import android.content.Context;
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
import android.widget.Toast;

import com.example.lidia.appproject2017_2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
public class FindFragment extends Fragment implements OnMapReadyCallback {
    private static final int REQUEST_CODE_LOCATION = 2;
    private Context context;
    private double lat =0, log =0;
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private List<Marker> markerList = new ArrayList<>();
    private MarkerOptions newMarkerOption = new MarkerOptions();
    private int number =1;
    private boolean isClick = false;



    private GoogleMap.OnMapClickListener mapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            newMarkerOption = new MarkerOptions();
            newMarkerOption.position(latLng).title("마커 넘버"+ number);
            number++;
            googleMap.addMarker(newMarkerOption);

        }
    };

    private GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Toast.makeText(getContext(),"마커 터치 리스너",Toast.LENGTH_LONG).show();
            return false;
        }
    };

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.context = getActivity();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap_find);
        if (mapFragment == null) {
            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            transaction.replace(R.id.googleMap_find, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

//  이 콜백 함수,
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settingGPS();
        Location userLocation = getMyLocation();
        if(userLocation!=null){
            lat = userLocation.getLatitude();
            log = userLocation.getLongitude();
            System.out.println("onActivityCreated 에서 longtitude=" + log + ", latitude=" + lat);
        }

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        System.out.println("호출 : onMapReady");
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(markerClickListener);
        this.googleMap.setOnMapClickListener(mapClickListener);

       // 이렇게 관리 할수도 있습니다 HashMap<Marker,String> markers = new HashMap<>();
        settingCurrentState(lat,log);
    }

    // 역
    private void settingCurrentState(double lat, double log){
        LatLng latLng = new LatLng(lat,log);
        MarkerOptions curLocaMarker = new MarkerOptions();
        curLocaMarker.position(latLng);
        curLocaMarker.alpha(0);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.addMarker(curLocaMarker);

    }


    private Location getMyLocation() {
        System.out.println("호출 : getMyLocation");
        Location currentLocation = null;
        // 여기 getContext()때문에 오류 생길 수 있습니다
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                log = currentLocation.getLongitude();
                lat = currentLocation.getLatitude();
                System.out.println("longtitude=" + log + ", latitude=" + lat);

            }
        }
        return currentLocation;
    }

    private void settingGPS(){
        System.out.println("호출 : settingGPS");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

    }
}

