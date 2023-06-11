package com.example.pesanan.model;

import java.util.List;

public class ResponsePenjualan {
    String code,message;
    List<Penjualan> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Penjualan> getData() {
        return data;
    }

    public void setData(List<Penjualan> data) {
        this.data = data;
    }
}
