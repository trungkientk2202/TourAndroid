package com.da.tourandroid.model;

public class QuanLyTour {

    private QuanLyTourID id;

    private String ghiChu;

    private Tour tour;

    public QuanLyTour(QuanLyTourID id, String ghiChu,Tour tour) {
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

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
