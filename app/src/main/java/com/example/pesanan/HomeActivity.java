package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.pesanan.Session.SessionManager;

public class HomeActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private Button btn_logout,btn_menu,btn_dipesan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(getApplicationContext());

        btn_logout = findViewById(R.id.logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                sessionManager.logout();
                finish();
            }
        });
        btn_menu = findViewById(R.id.button_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,DaftarMenuActivity.class));
            }
        });
        btn_dipesan = findViewById(R.id.menu_pesan_btn);
        btn_dipesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MenuDipesanActivity.class));
            }
        });
    }
}