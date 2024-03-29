package com.da.tourandroid.model;

public class ThamGiaTour {
    private ThamGiaTourID id;

    private boolean checkIn;

    private String ghiChu;

    private String diaDiemDon;

    private Tour tour;

    public ThamGiaTour(ThamGiaTourID id, boolean checkIn, String ghiChu, String diaDiemDon,Tour tour) {
        this.id = id;
        this.checkIn = checkIn;
        this.ghiChu = ghiChu;
        this.diaDiemDon = diaDiemDon;
        this.tour=tour;
    }

    public ThamGiaTour() {
    }

    public ThamGiaTourID getId() {
        return id;
    }

    public void setId(ThamGiaTourID id) {
        this.id = id;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDiaDiemDon() {
        return diaDiemDon;
    }

    public void setDiaDiemDon(String diaDiemDon) {
        this.diaDiemDon = diaDiemDon;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
