package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pesanan.Api.ApiRequest;
import com.example.pesanan.Api.Retroserver;
import com.example.pesanan.Session.SessionManager;
import com.example.pesanan.model.Menu;
import com.example.pesanan.model.ResponseMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarMenuActivity extends AppCompatActivity {
    private List<Menu> dataList = new ArrayList<>();
    private List<Map<String, String>> dItem = new ArrayList<>();
    private SimpleAdapter adapter;
    private static final String[] FROM = {"nama_menu","harga"};
    private static final int[] TO = {R.id.nama_menu,R.id.harga_menu};
    private ListView listView;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_menu);

        sessionManager = new SessionManager(getApplicationContext());

        adapter = new SimpleAdapter(this, dItem,R.layout.list_menu, FROM, TO);
        listView = findViewById(R.id.listvieew);
        listView.setAdapter(adapter);

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseMenu> act = api.getMenu();
        act.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                dataList = response.body().getData();
                for(Menu menu : dataList){
                    String nama_menu = menu.getNama_menu();
                    String harga = menu.getHarga();
                    String id_menu = menu.getId_menu();

                    Map<String, String> data = new HashMap<>();
                    data.put("nama_menu", nama_menu);
                    data.put("harga", harga);
                    data.put("id_menu", id_menu);
                    dItem.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Toast.makeText(DaftarMenuActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Map<String, String> select = dItem.get(position);
                String id_menu = select.get("id_menu");
                sessionManager.createMenuSession(id_menu);
                startActivity(new Intent(DaftarMenuActivity.this,HomePesanActivity.class));
            }
        });
    }
}