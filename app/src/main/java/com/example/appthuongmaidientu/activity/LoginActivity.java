package com.example.appthuongmaidientu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp, login_btn, btn_ForgetPassword;
    ImageView img;
    TextView logoText, slgText;
    TextInputLayout til_phone, til_password;
    TextInputEditText edt_phone, edt_password;
    SharedPreferences sharedPreferences;
    CheckBox checkBox_rememberUP;
    User user;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        AnhXa();
        edt_phone.setText(getIntent().getStringExtra("phone"));
        edt_password.setText(getIntent().getStringExtra("password"));

        //Hiển thị tài khoản đã lưu
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("REMEMBER", false) == true) {
            edt_phone.setText(sharedPreferences.getString("PHONE", ""));
            edt_password.setText(sharedPreferences.getString("PASSWORD", ""));
            checkBox_rememberUP.setChecked(true);
        }
        btn_ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForgetPassword(view);
            }
        });


        //Đăng nhập
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        edt_phone = findViewById(R.id.edt_phone);
        edt_password = findViewById(R.id.edt_password);
        checkBox_rememberUP = findViewById(R.id.cb_savePassword);

        img = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoName);
        slgText = findViewById(R.id.logoSlogan);
        til_phone = findViewById(R.id.til_phone);
        til_password = findViewById(R.id.til_password);

        login_btn = findViewById(R.id.login_btn);
        callSignUp = findViewById(R.id.signUp_btn);

        btn_ForgetPassword = findViewById(R.id.btn_ForgetPassword);
    }


    public void checkLogin(View view) {
        String phone = edt_phone.getText().toString();
        String password = edt_password.getText().toString();
        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng không để trống!",
                    Toast.LENGTH_LONG).show();
        } else {
            databaseReference.child("users").child("+84"+phone.substring(1)).child("matKhau").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue(String.class).equals(password)){
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công",
                                Toast.LENGTH_SHORT).show();
                        if (checkBox_rememberUP.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("PHONE", phone);
                            editor.putString("PASSWORD", password);
                            editor.putBoolean("REMEMBER", true);
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("PHONE", phone);
                            editor.putString("PASSWORD", password);
                            editor.putBoolean("REMEMBER", false);
                            editor.commit();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("phone", phone);
                        startActivity(intent);
                    }else Toast.makeText(getApplicationContext(), "Sai thông tin tài khoản hoặc mật khẩu",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public void callForgetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
        startActivity(intent);
    }
}