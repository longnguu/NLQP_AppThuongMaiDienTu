package com.example.appthuongmaidientu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appthuongmaidientu.R;

public class Login extends AppCompatActivity {
    Button btn_Login,btn_Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_Login = (Button) findViewById(R.id.login_btn);
        btn_Signup=(Button) findViewById(R.id.signUp_btn);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
        btn_Signup=(Button) findViewById(R.id.signUp_btn);
        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });
    }
}