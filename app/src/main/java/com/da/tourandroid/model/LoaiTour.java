package com.da.tourandroid.model;

public class LoaiTour {
    private long maLoaiTour;

    private String tenLoaiTour;

    private String moTa;

    public LoaiTour() {
    }

    public LoaiTour(long maLoaiTour, String tenLoaiTour, String moTa) {
        this.maLoaiTour = maLoaiTour;
        this.tenLoaiTour = tenLoaiTour;
        this.moTa = moTa;
    }

    public long getMaLoaiTour() {
        return maLoaiTour;
    }

    public void setMaLoaiTour(long maLoaiTour) {
        this.maLoaiTour = maLoaiTour;
    }

    public String getTenLoaiTour() {
        return tenLoaiTour;
    }

    public void setTenLoaiTour(String tenLoaiTour) {
        this.tenLoaiTour = tenLoaiTour;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
