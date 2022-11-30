package com.da.tourandroid.model;

import java.util.Date;

public class KhachHang {
    private String sdt;

    private String ten;

    private String matKhau;

    private boolean phai;

    private Date ngaySinh;

    private String zalo;

    public KhachHang() {
    }

    public KhachHang(String sdt, String ten, String matKhau, boolean phai, Date ngaySinh, String zalo) {
        this.sdt = sdt;
        this.ten = ten;
        this.matKhau = matKhau;
        this.phai = phai;
        this.ngaySinh = ngaySinh;
        this.zalo = zalo;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isPhai() {
        return phai;
    }

    public void setPhai(boolean phai) {
        this.phai = phai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }
}
