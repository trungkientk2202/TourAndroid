package com.da.tourandroid.model;

public class Tour {
    private long maTour;

    private String diemDen;

    private String moTa;

    private String diemDi;

    private Long gia;

    public LoaiTour loaiTour;

    public Tour(long maTour, String diemDen, String moTa, String diemDi, Long gia, LoaiTour loaiTour) {
        this.maTour = maTour;
        this.diemDen = diemDen;
        this.moTa = moTa;
        this.diemDi = diemDi;
        this.gia = gia;
        this.loaiTour = loaiTour;
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
}
