package com.example.a2022swproject.mainFunction.homeMap;

import static java.lang.String.valueOf;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentHomemapBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Map;

public class HomeMapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomemapBinding binding;
    private HomeMapViewModel homeMapViewModel;
    private NavController navController;
    private MapView mapView;
    private static NaverMap naverMap;

    private double latitude, longitude;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    private Marker marker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeMapViewModel =
                new ViewModelProvider(this).get(HomeMapViewModel.class);

        binding = FragmentHomemapBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(HomeMapFragment.this);

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return  binding.getRoot();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

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


        homeMapViewModel.getMarkerLocation();


        if(homeMapViewModel != null){

            for(Map.Entry<Double, Double> x : homeMapViewModel.getMarkerLocation().entrySet()){
                marker = new Marker();
                double markerLatitude =  x.getKey();
                double markerLongitude = x.getValue();

                Log.v("마커 위도", " " +markerLatitude);
                Log.v("마커 경도", " " +markerLongitude);

                marker.setIcon(OverlayImage.fromResource(R.drawable.marker_chair));
                marker.setPosition(new LatLng(markerLatitude, markerLongitude));
                marker.setMap(naverMap);

            }


        }
        else{;}

        //marker.setPosition(new LatLng(36.8000, 127.0771));
        //marker.setMap(naverMap);
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