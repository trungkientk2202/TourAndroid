package com.da.tourandroid.model;


public class LoaiTaiKhoan {
    private long maLoaiTK;

    private String tenLoaiTK;

    private String ghiChu;

    public LoaiTaiKhoan(long maLoaiTK, String tenLoaiTK, String ghiChu) {
        this.maLoaiTK = maLoaiTK;
        this.tenLoaiTK = tenLoaiTK;
        this.ghiChu = ghiChu;
    }

    public LoaiTaiKhoan() {
    }

    public long getMaLoaiTK() {
        return maLoaiTK;
    }

    public void setMaLoaiTK(long maLoaiTK) {
        this.maLoaiTK = maLoaiTK;
    }

    public String getTenLoaiTK() {
        return tenLoaiTK;
    }

    public void setTenLoaiTK(String tenLoaiTK) {
        this.tenLoaiTK = tenLoaiTK;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
