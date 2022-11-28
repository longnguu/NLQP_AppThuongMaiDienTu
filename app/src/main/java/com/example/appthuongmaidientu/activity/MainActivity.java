package com.example.appthuongmaidientu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appthuongmaidientu.R;

public class MainActivity extends AppCompatActivity {
    ImageView iconMess,iconCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iconMess = (ImageView) findViewById(R.id.topnavMess);
        iconMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UIMessenger.class);
                startActivity(intent);
            }
        });
    }
}