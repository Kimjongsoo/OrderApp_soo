package org.techtown.orderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Tab_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_menu);

        ImageButton imageButton = (ImageButton)findViewById(R.id.korea);

        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.markermenu = "한식";
                Intent intent =new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton1 = (ImageButton)findViewById(R.id.china);
        imageButton1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.markermenu = "중식";
                Intent intent =new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton2 = (ImageButton)findViewById(R.id.japan);
        imageButton2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.markermenu = "일식";
                Intent intent =new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton3 = (ImageButton)findViewById(R.id.weston_food);
        imageButton3.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.markermenu = "양식";
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
