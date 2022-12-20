package com.da.tourandroid.model;

public class QuanLyTour {

    private QuanLyTourID id;

    private String thongBao;

    private String diemHen;

    private Tour tour;

    public QuanLyTour(QuanLyTourID id, String ghiChu, String diemHen,Tour tour) {
        this.id = id;
        this.thongBao = ghiChu;
        this.diemHen=diemHen;
    }
    public QuanLyTour() {
    }

    public QuanLyTourID getId() {
        return id;
    }

    public void setId(QuanLyTourID id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getThongBao() {
        return thongBao;
    }

    public void setThongBao(String thongBao) {
        this.thongBao = thongBao;
    }

    public String getDiemHen() {
        return diemHen;
    }

    public void setDiemHen(String diemHen) {
        this.diemHen = diemHen;
    }
}
