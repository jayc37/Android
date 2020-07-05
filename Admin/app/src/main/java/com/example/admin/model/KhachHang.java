package com.example.admin.model;

public class KhachHang {
    public int id;
    public String tenKH;
    public String sDT;
    public String email;

    public KhachHang(int id, String tenKH, String sDT, String email) {
        this.id = id;
        this.tenKH = tenKH;
        this.sDT = sDT;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
