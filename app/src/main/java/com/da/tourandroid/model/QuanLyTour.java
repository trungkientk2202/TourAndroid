package com.da.tourandroid.model;

public class QuanLyTour {

    private QuanLyTourID id;

    private String ghiChu;

    public QuanLyTour(QuanLyTourID id, String ghiChu) {
        this.id = id;
        this.ghiChu = ghiChu;
    }

    public QuanLyTour() {
    }

    public QuanLyTourID getId() {
        return id;
    }

    public void setId(QuanLyTourID id) {
        this.id = id;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
