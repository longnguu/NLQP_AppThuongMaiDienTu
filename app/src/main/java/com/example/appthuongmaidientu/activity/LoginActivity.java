package com.example.appthuongmaidientu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp, login_btn, btn_ForgetPassword;
    ImageView img;
    TextView logoText, slgText;
    TextInputLayout til_phone, til_password;
    TextInputEditText edt_phone, edt_password;
    CountryCodePicker ccp;
    SharedPreferences sharedPreferences;
    CheckBox checkBox_rememberUP;
    User user;

    SharedPreferences saveLogin;

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
            ccp.setCountryForPhoneCode(+84);
            edt_phone.setText(sharedPreferences.getString("PHONE", ""));
            edt_password.setText(sharedPreferences.getString("PASSWORD", ""));
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

//                Pair[] pairs = new Pair[7];
//
//                pairs[0] = new Pair<View, String>(img, "logo_img");
//                pairs[1] = new Pair<View, String>(logoText, "logo_Name_Tran");
//                pairs[2] = new Pair<View, String>(slgText, "logo_Slg_Tran");
//                pairs[3] = new Pair<View, String>(til_phone, "username_Tran");
//                pairs[4] = new Pair<View, String>(til_password, "password_Tran");
//                pairs[5] = new Pair<View, String>(login_btn, "btn_LogIn_Tran");
//                pairs[6] = new Pair<View, String>(callSignUp, "btn_callSignUp_Tran");
//
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

                startActivity(intent/*, options.toBundle()*/);
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

    public void rememberUP(String phone, String pass, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (status == false) {
            editor.clear();
        } else {
            editor.putString("PHONE", phone);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();
    }

    public void checkLogin(View view) {
        String phone = edt_phone.getText().toString();
        String password = edt_password.getText().toString();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng không để trống!",
                    Toast.LENGTH_LONG).show();
        } else {
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
        }
    }

    public void saveUP(View view) {
        String phone = edt_phone.getText().toString();
        String pass = edt_password.getText().toString();
        boolean status = checkBox_rememberUP.isChecked();
        // rememberUP(phone, pass, status);
    }

    public void callForgetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
        startActivity(intent);
    }
}