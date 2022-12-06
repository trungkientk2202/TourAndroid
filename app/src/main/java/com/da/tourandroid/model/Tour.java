package com.da.tourandroid.model;

import java.util.Date;

public class Tour {
    private long maTour;

    private String diemDen;

    private String moTa;

    private String diemDi;

    private Long gia;

    public LoaiTour loaiTour;
    private int trangThai;
    private String image;
    private String ngayBatDau;

    public Tour(long maTour, String diemDen, String moTa, String diemDi, Long gia, LoaiTour loaiTour,int trangThai,String image,String ngayBatDau) {
        this.maTour = maTour;
        this.diemDen = diemDen;
        this.moTa = moTa;
        this.diemDi = diemDi;
        this.gia = gia;
        this.loaiTour = loaiTour;
        this.trangThai=trangThai;
        this.image=image;
        this.ngayBatDau=ngayBatDau;
    }

    public Tour() {
    }

    public long getMaTour() {
        return maTour;
    }

    public void setMaTour(long maTour) {
        this.maTour = maTour;
    }

    public String getDiemDen() {
        return diemDen;
    }

    public void setDiemDen(String diemDen) {
        this.diemDen = diemDen;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDiemDi() {
        return diemDi;
    }

    public void setDiemDi(String diemDi) {
        this.diemDi = diemDi;
    }

    public Long getGia() {
        return gia;
    }

    public void setGia(Long gia) {
        this.gia = gia;
    }

    public LoaiTour getLoaiTour() {
        return loaiTour;
    }

    public void setLoaiTour(LoaiTour loaiTour) {
        this.loaiTour = loaiTour;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
}
