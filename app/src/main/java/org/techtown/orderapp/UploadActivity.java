package org.techtown.orderapp;

import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity implements OnMapReadyCallback {

    Float mapX, mapY;
    EditText placeNameEditText;
    Geocoder Geocoder;
    String address;
    Button FindPlaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        android.support.v4.app.FragmentManager fragmentManager= getSupportFragmentManager();
        SupportMapFragment mapFragment=(SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        placeNameEditText = (EditText) findViewById(R.id.placeName);
        Geocoder = new Geocoder(this);
        FindPlaceButton = (Button) findViewById(R.id.findPlace);

    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL= new LatLng(37.56,126.97);
        final MarkerOptions markerOptions=new MarkerOptions();
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));

        Geocoder =new Geocoder(UploadActivity.this, Locale.getDefault());
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mapX=(float) point.latitude;
                mapY=(float)point.longitude;
                Toast.makeText(getApplicationContext(), mapX + "," + mapY, Toast.LENGTH_SHORT).show();
                map.clear();
                List<Address> addressList = null;
                try {
                    addressList = Geocoder.getFromLocation(mapX, mapY, 10);
                    String add = "";
                    if (addressList.size() > 0) {
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++) {
                            add += addressList.get(0).getAddressLine(i) + "n";
                        }
                    }
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "Selected Place is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (addressList.size() != 0) {
                    System.out.println(addressList.get(0).toString());
                    // 콤마를 기준으로 split

                    address = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addressList.get(0).getLocality();
                    String state = addressList.get(0).getAdminArea();
                    String country = addressList.get(0).getCountryName();
                    String postalCode = addressList.get(0).getPostalCode();
                    String knownName = addressList.get(0).getFeatureName();
                    System.out.println(address);

                    Address location = addressList.get(0);
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    System.out.println(latitude);
                    System.out.println(longitude);

                    // 좌표(위도, 경도) 생성
//                    LatLng pointXY = new LatLng((double)(latitude*1E6),(double)(longitude*1E6));

                    // 마커 생성
                    MarkerOptions mOptions2 = new MarkerOptions();
                    mOptions2.title("search result");
                    mOptions2.snippet(address);
                    mOptions2.position(point);
                    map.clear();
                    // 마커 추가
                    map.addMarker(mOptions2).showInfoWindow();
                    // 해당 좌표로 화면 줌
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                } else {
                    Toast.makeText(getApplicationContext(), "Selected Place is not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FindPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = placeNameEditText.getText().toString();
                List<Address> addressList = null;
                try {
                    addressList = Geocoder.getFromLocationName(str, 10);
                    String add = "";
                    if (addressList.size() > 0) {
                        add = (addressList.get(0).getFeatureName() + ", " + addressList.get(0).getLocality() + ", " + addressList.get(0).getAdminArea() + ", " + addressList.get(0).getCountryName());
                    }
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "Selected Place is not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (addressList.size() != 0) {
                    System.out.println(addressList.get(0).toString());
                    // 콤마를 기준으로 split
                    address = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addressList.get(0).getLocality();
                    String state = addressList.get(0).getAdminArea();
                    String country = addressList.get(0).getCountryName();
                    String postalCode = addressList.get(0).getPostalCode();
                    String knownName = addressList.get(0).getFeatureName();
                    System.out.println(address);

                    Address location = addressList.get(0);
                    float latitude = (float)location.getLatitude();
                    float longitude = (float)location.getLongitude();
                    System.out.println(latitude);
                    System.out.println(longitude);

                    mapX = latitude;
                    mapY = longitude;

                    // 좌표(위도, 경도) 생성
                    LatLng point = new LatLng(latitude, longitude);
                    // 마커 생성
                    MarkerOptions mOptions2 = new MarkerOptions();
                    mOptions2.title("search result");
                    mOptions2.snippet(address);
                    mOptions2.position(point);
                    // 마커 추가
                    map.clear();
                    map.addMarker(mOptions2).showInfoWindow();
                    // 해당 좌표로 화면 줌
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                } else {
                    Toast.makeText(getApplicationContext(), "Selected Place is not found", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
