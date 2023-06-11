package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesanan.Api.ApiRequest;
import com.example.pesanan.Api.Retroserver;
import com.example.pesanan.Session.SessionManager;
import com.example.pesanan.model.Menu;
import com.example.pesanan.model.Penjualan;
import com.example.pesanan.model.ResponseDetail;
import com.example.pesanan.model.ResponseMenu;
import com.example.pesanan.model.ResponsePenjualan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePesanActivity extends AppCompatActivity {
    private List<Map<String, String>> dataL = new ArrayList<>();
    private List<Menu> dataList = new ArrayList<>();
    private List<Penjualan> dataListPenjualan = new ArrayList<>();
    private Button button;
    private SimpleAdapter adapter;
    private static final String[] FROM = {"nama_menu","harga"};
    private static final int[] TO = {R.id.nama_menu,R.id.harga_menu};
    private SessionManager sessionManager;
    private String id_menu;
    private String id_pelanggan;
    private String id_penjualan;
    private String total_menu;
    private ListView listView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pesan);

        editText = findViewById(R.id.total_menu);

        listView = findViewById(R.id.list);
        adapter = new SimpleAdapter(this, dataL, R.layout.list_menu, FROM, TO);
        listView.setAdapter(adapter);

        sessionManager = new SessionManager(getApplicationContext());

        id_menu = sessionManager.getIdMenu();
        id_pelanggan = sessionManager.getId();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseMenu> act = api.getOneMenu(id_menu);
        act.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                String code = response.body().getCode();
                dataList = response.body().getData();
                if(code.equals("1")){
                    for(Menu menu : dataList){
                        String nama_menu = menu.getNama_menu();
                        String harga = menu.getHarga();

                        Map<String, String> data = new HashMap<>();
                        data.put("nama_menu", nama_menu);
                        data.put("harga", harga);
                        dataL.add(data);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Toast.makeText(HomePesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });

        button = findViewById(R.id.Pesan_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiPenjualan(id_pelanggan);
            }
        });
    }

    private void validasiPenjualan(String id_pelanggan) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsePenjualan> act = api.validasi(id_pelanggan);
        act.enqueue(new Callback<ResponsePenjualan>() {
            @Override
            public void onResponse(Call<ResponsePenjualan> call, Response<ResponsePenjualan> response) {
                dataListPenjualan = response.body().getData();
                id_menu = sessionManager.getIdMenu();
                String total_menu = editText.getText().toString();
                if(dataListPenjualan != null){
                    for(Penjualan penjualan : dataListPenjualan){
                        id_penjualan = penjualan.getId_penjualan();
                        validasiMenu(id_penjualan,id_menu);
                    }
                }else{
                    postPenjualan(id_pelanggan);
                }
            }

            @Override
            public void onFailure(Call<ResponsePenjualan> call, Throwable t) {

            }
        });
    }

    private void validasiMenu(String id_penjualan, String id_menu) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseDetail> act = api.validasiMenu(id_penjualan,id_menu);
        act.enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                String code = response.body().getCode();
                total_menu = editText.getText().toString();
                if(!total_menu.isEmpty()) {
                    if (code.equals("1")) {
                        updateTotal(id_menu, id_penjualan, total_menu);
                    } else {
                        pesanTask(id_penjualan, id_menu, total_menu);
                    }
                }else{
                    Toast.makeText(HomePesanActivity.this, "Isi Jumlah Yang di inginkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {
                Toast.makeText(HomePesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotal(String id_menu, String id_penjualan, String total_menu) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseDetail> act = api.updateTotal(id_menu,id_penjualan,total_menu);
        act.enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                String code = response.body().getCode();
                if(code.equals("1")){
                    Toast.makeText(HomePesanActivity.this, "Berhasil Memesan", Toast.LENGTH_SHORT).show();
                    editText.getText().clear();
                }else{
                    Toast.makeText(HomePesanActivity.this, "Gagal Memesan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {
                Toast.makeText(HomePesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pesanTask(String id_penjualan, String id_menu, String total_menu) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseDetail> act = api.insertPesanan(id_penjualan,id_menu,total_menu);
        act.enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                String code = response.body().getCode();
                if(code.equals("1")){
                    Toast.makeText(HomePesanActivity.this, "Berhasil Memesan", Toast.LENGTH_SHORT).show();
                    editText.getText().clear();
                }else{
                    Toast.makeText(HomePesanActivity.this, "Gagal Memesan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {
                Toast.makeText(HomePesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postPenjualan(String id_pelanggan) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsePenjualan> act = api.insertPenjualan(id_pelanggan);
        act.enqueue(new Callback<ResponsePenjualan>() {
            @Override
            public void onResponse(Call<ResponsePenjualan> call, Response<ResponsePenjualan> response) {
                String code = response.body().getCode();
                if(code.equals("1")){
                    validasiPenjualan(id_pelanggan);
                }
            }

            @Override
            public void onFailure(Call<ResponsePenjualan> call, Throwable t) {
                Toast.makeText(HomePesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}