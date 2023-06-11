package com.example.pesanan.Api;

import com.example.pesanan.model.ResponseDetail;
import com.example.pesanan.model.ResponseMenu;
import com.example.pesanan.model.ResponsePelanggan;
import com.example.pesanan.model.ResponsePenjualan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponsePelanggan> apiLogin(@Field("username") String username,@Field("password") String password);

    @FormUrlEncoded
    @POST("last_login.php")
    Call<ResponsePelanggan> lastLogin(@Field("id_pelanggan") String id_pelanggan);

    @GET("menu.php")
    Call<ResponseMenu> getMenu();

    @FormUrlEncoded
    @POST("detail_pesanan.php")
    Call<ResponsePenjualan> validasi(@Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("menuP.php")
    Call<ResponseMenu> getOneMenu(@Field("id_menu") String id_menu);

    @FormUrlEncoded
    @POST("insertPenjualan.php")
    Call<ResponsePenjualan> insertPenjualan(@Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("memesan.php")
    Call<ResponseDetail> insertPesanan(@Field("id_penjualan") String id_penjualn,@Field("id_menu") String id_menu, @Field("total_menu") String total_menu);

    @FormUrlEncoded
    @POST("validasiPesanan.php")
    Call<ResponseDetail> validasiMenu(@Field("id_penjualan") String id_penjualan,@Field("id_menu") String id_menu);

    @FormUrlEncoded
    @POST("updateJumlah.php")
    Call<ResponseDetail> updateTotal(@Field("id_menu") String id_menu, @Field("id_penjualan") String id_penjualan, @Field("total_menu") String total_menu);

    @FormUrlEncoded
    @POST("diPesan.php")
    Call<ResponseMenu> getDipesan(@Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("total.php")
    Call<ResponseMenu> getTotalHarga(@Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("getJumlah.php")
    Call<ResponseDetail> getJumlah(@Field("id_detail") String id_detail);

    @FormUrlEncoded
    @POST("updateBeli.php")
    Call<ResponseDetail> updateJumlah(@Field("total_menu") String total_menu,@Field("id_detail") String id_detail);
}
