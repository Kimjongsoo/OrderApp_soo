package org.techtown.orderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class Tab_review extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_review);

        FloatingActionButton fab= findViewById(R.id.fab);
        fab.setOnClickListener(new FABClickListener());

    }

    class FABClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            Intent intent2=new Intent(getApplicationContext(),ReviewActivity.class);
            startActivity(intent2);

        }
    }

}
