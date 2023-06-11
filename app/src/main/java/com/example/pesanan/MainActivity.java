package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesanan.Api.ApiRequest;
import com.example.pesanan.Api.Retroserver;
import com.example.pesanan.Session.SessionManager;
import com.example.pesanan.model.Pelanggan;
import com.example.pesanan.model.ResponsePelanggan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername,editPassword;
    private TextView textView;
    private Button button_login,button_registrasi;
    private List<Pelanggan> dataList = new ArrayList<>();
    private String id_pelanggan;
    private SessionManager sessionManager;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.editTextTextPersonName);
        editPassword = findViewById(R.id.editTextTextPassword);

        sessionManager = new SessionManager(getApplicationContext());

        username = sessionManager.getUsername();

        if(sessionManager.isLoggedIn()){
            username = sessionManager.getUsername();
            password = sessionManager.getPassword();
            loginTask(username,password);
        }

        button_login = findViewById(R.id.Login_btn);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                loginTask(username,password);
            }
        });
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        Button button = findViewById(R.id.Registrasi_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void loginTask(String username, String password) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsePelanggan> act = api.apiLogin(username,password);
        act.enqueue(new Callback<ResponsePelanggan>() {
            @Override
            public void onResponse(Call<ResponsePelanggan> call, Response<ResponsePelanggan> response) {
                String code = response.body().getCode();
                dataList = response.body().getData();
                if(code.equals("1")){
                    for(Pelanggan pelanggan : dataList){
                        id_pelanggan = pelanggan.getId_pelanggan();
                    }
                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponsePelanggan> act = api.lastLogin(id_pelanggan);
                    act.enqueue(new Callback<ResponsePelanggan>() {
                        @Override
                        public void onResponse(Call<ResponsePelanggan> call, Response<ResponsePelanggan> response) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            sessionManager.createLoginSession(id_pelanggan);
                            sessionManager.saveUsername(username,password);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponsePelanggan> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponsePelanggan> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}