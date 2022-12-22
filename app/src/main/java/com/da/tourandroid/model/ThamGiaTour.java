package com.da.tourandroid.model;

public class ThamGiaTour {
    private ThamGiaTourID id;

    private boolean diemDanh;

    private String ghiChu;

    private String diemHen;

    private Tour tour;
    private KhachHang khachHang;
    private String vitri;

    public ThamGiaTour(ThamGiaTourID id, boolean diemDanh, String ghiChu, String diemHen,Tour tour,KhachHang khachHang,String vitri) {
        this.id = id;
        this.diemDanh = diemDanh;
        this.ghiChu = ghiChu;
        this.diemHen = diemHen;
        this.tour=tour;
        this.vitri=vitri;
        this.khachHang=khachHang;
    }

    public ThamGiaTour() {
    }

    public ThamGiaTourID getId() {
        return id;
    }

    public void setId(ThamGiaTourID id) {
        this.id = id;
    }

    public boolean isDiemDanh() {
        return diemDanh;
    }

    public void setDiemDanh(boolean checkIn) {
        this.diemDanh = checkIn;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDiemHen() {
        return diemHen;
    }

    public void setDiemHen(String diemHen) {
        this.diemHen = diemHen;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
}
