package com.da.tourandroid.model;
public class PhanHoi {
    private PhanHoiID id;

    private String noiDung;

    private String thoiGian;
    private Tour tour;

    private KhachHang khachHang;

    public PhanHoi() {
    }

    public PhanHoi(PhanHoiID id, String noiDung, String thoiGian,Tour tour,KhachHang khachHang) {
        this.id = id;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.tour=tour;
        this.khachHang=khachHang;
    }

    public PhanHoiID getId() {
        return id;
    }

    public void setId(PhanHoiID id) {
        this.id = id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
}
