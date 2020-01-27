package org.techtown.orderapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback{

    private FusedLocationProviderClient mFusedLocationClient;
    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION=0;
    private GoogleMap mMap;
    Location mCurrentLocation;
    static String markermenu = "";
    Button Upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else {
            getLastLocation();
        }

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos){
                    case 0:

                    case 1:
                        Intent intent =new Intent(getApplicationContext(),Tab_menu.class);
                        startActivity(intent);
                    case 2:
                        Intent intent2=new Intent(getApplicationContext(),Tab_review.class);
                        startActivity(intent2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos){
                    case 0:

                    case 1:
                        Intent intent =new Intent(getApplicationContext(),Tab_menu.class);
                        startActivity(intent);
                    case 2:
                        Intent intent2=new Intent(getApplicationContext(),Tab_review.class);
                        startActivity(intent2);
                }
            }
        });

        Upload = (Button)findViewById(R.id.Upload);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UploadIntent = new Intent(getApplicationContext(),UploadActivity.class);
                startActivity(UploadIntent);
            }
        });

    }

    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                HomeActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
        );
    }

    private void getLastLocation() {
        @SuppressLint("MissingPermission")
        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.

                if (location != null) {
                    mCurrentLocation = location;
                    Toast.makeText(getApplicationContext(),
                            "위도:"+mCurrentLocation.getLatitude()+" 경도: "+mCurrentLocation.getLongitude(),
                            Toast.LENGTH_SHORT)
                            .show();
                    //  updateUI();
                    LatLng newLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    if (mMap != null)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation,15));
                } else
                    Toast.makeText(getApplicationContext(),
                            "No Location Detected",
                            Toast.LENGTH_SHORT)
                            .show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        setMarker(markermenu);

    }

        public void setMarker(String str){
            MarkerOptions markerOptions = new MarkerOptions();


            if(str.equals("한식")){
                markerOptions.position(new LatLng(37.644413,127.028375)).title("수유손칼국수").snippet("한식");
                mMap.addMarker(markerOptions);
                markerOptions.position(new LatLng(37.638514,127.024926)).title("석관동떡볶이").snippet("한식");
                mMap.addMarker(markerOptions);

            }
            else if(str.equals("중식")){
                markerOptions.position(new LatLng(37.636617,127.022867)).title("홍콩반점").snippet("중식");
                mMap.addMarker(markerOptions);

            }
            else if(str.equals("일식")){
                markerOptions.position(new LatLng(37.639059,127.024158)).title("상미규카츠").snippet("일식");
                mMap.addMarker(markerOptions);

            }
            else if(str.equals("양식")){
                markerOptions.position(new LatLng(37.638551,127.025352)).title("올뜨레마레").snippet("양식");
                mMap.addMarker(markerOptions);
            }
            else{
                markerOptions.position(new LatLng(37.644413,127.028375)).title("수유손칼국수").snippet("한식");
                mMap.addMarker(markerOptions);
                markerOptions.position(new LatLng(37.638514,127.024926)).title("석관동떡볶이").snippet("한식");
                mMap.addMarker(markerOptions);

                markerOptions.position(new LatLng(37.636617,127.022867)).title("홍콩반점").snippet("중식");
                mMap.addMarker(markerOptions);

                markerOptions.position(new LatLng(37.639059,127.024158)).title("상미규카츠").snippet("일식");
                mMap.addMarker(markerOptions);

                markerOptions.position(new LatLng(37.638551,127.025352)).title("올뜨레마레").snippet("양식");
                mMap.addMarker(markerOptions);
            }







    }
}
