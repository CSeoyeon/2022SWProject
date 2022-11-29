package com.example.a2022swproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a2022swproject.mainFunction.userBoard.BoardFragment.BoardListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a2022swproject.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMainBinding binding;

    private MapView mapView;
    private static NaverMap naverMap;
    private double latitude, longitude;
    private String address = "현재 위치를 받아 오지 못하였습니다.";

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private Marker marker = new Marker();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menuicon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_homeMap, R.id.navigation_boardList, R.id.navigation_userInformation)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        MainActivity.naverMap = naverMap;

        naverMap.setMapType(NaverMap.MapType.Navi);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //위도 경도 받아오기
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
        });

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    //longitude, latitude -> String address
    private String AddressToString(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        try {
            address = geocoder.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    public String getAddress(){
        return AddressToString(latitude, longitude);
    }

    public void setMarker( double latitude, double longitude, int resourceID) {
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setIcon(OverlayImage.fromResource(resourceID));
        marker.setMap(naverMap);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
                Log.v("위치 파악 못함", "");
                return;
            } else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                Log.v("위치 파악함", "");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //appBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_nav_menu, menu);
        return true;
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