package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesanan.Api.ApiRequest;
import com.example.pesanan.Api.Retroserver;
import com.example.pesanan.Session.SessionManager;
import com.example.pesanan.model.DetailPenjualan;
import com.example.pesanan.model.ResponseDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananActivity extends AppCompatActivity {
    private TextView textUp,textDown;
    private SessionManager sessionManager;
    private List<DetailPenjualan> dataList = new ArrayList<>();
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        textUp = findViewById(R.id.Up);
        textDown = findViewById(R.id.Down);
        editText = findViewById(R.id.editTextTextPersonName2);
        if(editText.getText().toString().equals("0") || editText.getText().toString().equals("")){
            textDown.setEnabled(false);
        }
        sessionManager = new SessionManager(getApplicationContext());
        textUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valueString = editText.getText().toString();
                int value = Integer.parseInt(valueString);
                int newValue = value + 1;
                editText.setText(String.valueOf(newValue));
            }
        });
        textDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ValueStr = editText.getText().toString();
                int value = Integer.parseInt(ValueStr);
                int newValue = value - 1;
                editText.setText(String.valueOf(newValue));
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = charSequence.toString();
                if(value.equals("0")){
                    textDown.setEnabled(false);
                    textUp.setEnabled(true);
                } else if (value.equals("")) {
                    textDown.setEnabled(false);
                    textUp.setEnabled(false);
                } else{
                    textDown.setEnabled(true);
                    textUp.setEnabled(true);
                }
                String total_menu = editText.getText().toString();
                updateJumlah(total_menu, sessionManager.getIdDetail());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        getJumlah(sessionManager.getIdDetail());
    }

    private void updateJumlah(String total_menu, String idDetail) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseDetail> act = api.updateJumlah(total_menu,idDetail);
        act.enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {

            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {

            }
        });
    }

    private void getJumlah(String idDetail) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseDetail> act = api.getJumlah(idDetail);
        act.enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                dataList = response.body().getData();
                for(DetailPenjualan detailPenjualan : dataList){
                    String jumlah = detailPenjualan.getTotal_menu();
                    editText.setText(jumlah);
                }
            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {
                Toast.makeText(DetailPesananActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}