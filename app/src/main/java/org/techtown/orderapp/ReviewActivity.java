package org.techtown.orderapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static final String UPLOAD_URL = "http://52.78.91.73/Order/upload.php";
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;

    Button RegisterButton, ImageSelectButton,FindPlaceButton;
    EditText content;
    File tempSelectFile;
    ImageView PlaceImage;
    Float mapX, mapY;
    EditText placeNameEditText;
    Geocoder Geocoder;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String ImageName="image_name";
    String ImagePath="image_path";
    boolean check=true;
    String address, Content;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        requestStoragePermission();

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        content=(EditText)findViewById(R.id.content);
        RegisterButton = (Button) findViewById(R.id.Register);
        RegisterButton.setEnabled(false);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Content=content.getText().toString();
                ImageUploadToServerFunction();
            }
        });

    }

    public void ImageUploadToServerFunction(){
        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStreamObject);
        byte[] byteArrayVar=byteArrayOutputStreamObject.toByteArray();
        final String ConverImage= Base64.encodeToString(byteArrayVar,Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                String context = "기다려주세요";
                builder = new AlertDialog.Builder(ReviewActivity.this);
                builder.setMessage(context);
                builder.setCancelable(true);
//                builder.setView(R.layout.layout_loading_dialog);
                dialog = builder.create();
                dialog.show();
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
                dialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(ReviewActivity.this, string1, Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
//                imageView.setImageResource(android.R.color.transparent);
            }



            @Override
            protected String doInBackground(Void... voids) {

//
//
//                ImageProcessClass imageProcessClass = new ImageProcessClass();
//                HashMap<String, String> HashMapParams = new HashMap<String, String>();
//                HashMapParams.put("placeName",address);
//                HashMapParams.put("mapX", String.valueOf(mapX));
//                HashMapParams.put("mapY",String.valueOf(mapY));
//                HashMapParams.put(ImageName, GetImageNameEditText);
//                HashMapParams.put(ImagePath, ConvertImage);

                return null;
            }
        }





    }



    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
