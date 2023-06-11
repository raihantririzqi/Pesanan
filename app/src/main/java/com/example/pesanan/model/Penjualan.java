package com.example.pesanan.model;

public class Penjualan {
    String id_penjualan,tgl_penjualan,id_pelanggan,id_kasir,status;

    public String getId_penjualan() {
        return id_penjualan;
    }

    public void setId_penjualan(String id_penjualan) {
        this.id_penjualan = id_penjualan;
    }

    public String getTgl_penjualan() {
        return tgl_penjualan;
    }

    public void setTgl_penjualan(String tgl_penjualan) {
        this.tgl_penjualan = tgl_penjualan;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getId_kasir() {
        return id_kasir;
    }

    public void setId_kasir(String id_kasir) {
        this.id_kasir = id_kasir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
