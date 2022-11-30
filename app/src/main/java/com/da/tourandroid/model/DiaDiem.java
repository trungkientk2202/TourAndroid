package com.da.tourandroid.model;

public class DiaDiem {

    private long maDiaDiem;

    private String tenDiaDiem;

    private String moTa;

    private String tinhThanh;

    public DiaDiem(long maDiaDiem, String tenDiaDiem, String moTa, String tinhThanh) {
        this.maDiaDiem = maDiaDiem;
        this.tenDiaDiem = tenDiaDiem;
        this.moTa = moTa;
        this.tinhThanh = tinhThanh;
    }

    public DiaDiem() {
    }

    public long getMaDiaDiem() {
        return maDiaDiem;
    }

    public void setMaDiaDiem(long maDiaDiem) {
        this.maDiaDiem = maDiaDiem;
    }

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }
}
