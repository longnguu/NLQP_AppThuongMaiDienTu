package com.example.appthuongmaidientu.activity;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.os.Bundle;
=======
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
>>>>>>> 97e2606599bf910e3ad30e4080e4326165845e3e

import com.example.appthuongmaidientu.R;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD
=======
    ImageView iconMess,iconCart;
>>>>>>> 97e2606599bf910e3ad30e4080e4326165845e3e

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
=======
        iconMess = (ImageView) findViewById(R.id.topnavMess);
        iconMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UIMessenger.class);
                startActivity(intent);
            }
        });
>>>>>>> 97e2606599bf910e3ad30e4080e4326165845e3e
    }
}