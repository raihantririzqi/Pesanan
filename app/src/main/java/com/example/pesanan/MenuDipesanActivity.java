package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesanan.Api.ApiRequest;
import com.example.pesanan.Api.Retroserver;
import com.example.pesanan.Session.SessionManager;
import com.example.pesanan.model.DetailPenjualan;
import com.example.pesanan.model.Menu;
import com.example.pesanan.model.Pelanggan;
import com.example.pesanan.model.ResponseMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDipesanActivity extends AppCompatActivity {
    private static final String[] FROM = {"nama_menu","harga","total_menu","total_harga"};
    private static final int[] TO = {R.id.namamenu,R.id.hargamenu,R.id.jumlahmenu,R.id.totalharga};
    private SimpleAdapter adapter;
    private ListView listView;
    private List<Menu> dataList = new ArrayList<Menu>();
    private List<Map<String, String>> mItem = new ArrayList<>();
    private TextView textView;
    private SessionManager sessionManager;
    private String id_pelanggan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dipesan);

        sessionManager = new SessionManager(getApplicationContext());

        listView = findViewById(R.id.listPesan);
        textView = findViewById(R.id.totalTextview);
        adapter = new SimpleAdapter(this, mItem, R.layout.list_keranjang,FROM, TO);
        listView.setAdapter(adapter);

        id_pelanggan = sessionManager.getId();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseMenu> act = api.getDipesan(id_pelanggan);
        act.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                String code = response.body().getCode();
                if(code.equals("1")){
                    dataList = response.body().getData();
                    for(Menu menu : dataList){
                        String id_detail = menu.getId_detail();
                        String nama_menu = menu.getNama_menu();
                        String harga = menu.getHarga();
                        String total_menu = menu.getTotal_menu();
                        String total_harga = menu.getTotal_harga();

                        Map<String, String> data = new HashMap<>();

                        data.put("id_detail", id_detail);
                        data.put("nama_menu", nama_menu);
                        data.put("harga", harga);
                        data.put("total_menu", total_menu);
                        data.put("total_harga", total_harga);
                        mItem.add(data);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MenuDipesanActivity.this, "Anda Tidak Memesan Apapun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Toast.makeText(MenuDipesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Map<String, String> selectData = mItem.get(position);
                sessionManager.createDetailId(selectData.get("id_detail"));
                startActivity(new Intent(MenuDipesanActivity.this, DetailPesananActivity.class));
            }
        });
        getTotalHarga(id_pelanggan);
    }

    private void getTotalHarga(String id_pelanggan) {
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseMenu> act = api.getTotalHarga(id_pelanggan);
        act.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                String code = response.body().getCode();
                if(code.equals("1")){
                    dataList = response.body().getData();
                    for(Menu menu : dataList){
                        String total_harga = menu.getTotal_harga();
                        textView.setText("Total Harga : " + total_harga);
                    }
                }else{
                    textView.setText("Total Harga : ");
                    Toast.makeText(MenuDipesanActivity.this, "Anda Tidak Memesan Apapun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Toast.makeText(MenuDipesanActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}