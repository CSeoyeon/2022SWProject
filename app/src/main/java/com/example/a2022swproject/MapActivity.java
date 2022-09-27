package com.example.a2022swproject;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a2022swproject.databinding.ActivityMainBinding;
import com.example.a2022swproject.databinding.FragmentHomemapBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class MapActivity  extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentHomemapBinding binding;

    private MapView mapView;
    private static NaverMap map_naverMap;

    //location marker
    private static final int PERMISSION_REQUEST_CODE =1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private FusedLocationSource fusedLocationSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentHomemapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        fusedLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        this.map_naverMap = naverMap;
        map_naverMap.setLocationSource(fusedLocationSource);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(fusedLocationSource);

        //마커 표시
        Marker marker = new Marker();
        marker.setPosition(new LatLng(36.80040160733433,  127.07508644619917));
        marker.setMap(naverMap);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(fusedLocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)){
            if(!fusedLocationSource.isActivated()){
                map_naverMap.setLocationTrackingMode(LocationTrackingMode.None);
                Log.v("위치 파악 못함", "" );
                return;
            }
            else{
                map_naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                Log.v("위치 파악함", "" );
            }
        }
        /*if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                map_naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }*/
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



}
