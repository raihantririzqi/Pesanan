package com.example.pesanan.model;

import java.util.List;

public class ResponsePelanggan {
    String code,message;

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

    public List<Pelanggan> getData() {
        return data;
    }

    public void setData(List<Pelanggan> data) {
        this.data = data;
    }

    List<Pelanggan> data;
}
