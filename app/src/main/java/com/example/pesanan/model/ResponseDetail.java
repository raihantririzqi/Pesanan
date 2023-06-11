package com.example.pesanan.model;

import java.util.List;

public class ResponseDetail {
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

    public List<DetailPenjualan> getData() {
        return data;
    }

    public void setData(List<DetailPenjualan> data) {
        this.data = data;
    }

    List<DetailPenjualan> data;

}
