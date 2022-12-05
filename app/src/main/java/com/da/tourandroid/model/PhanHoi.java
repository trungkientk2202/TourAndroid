package com.da.tourandroid.model;
public class PhanHoi {
    private PhanHoiID id;

    private String noiDung;

    private String thoiGian;
    private Tour tour;

    public PhanHoi() {
    }

    public PhanHoi(PhanHoiID id, String noiDung, String thoiGian,Tour tour) {
        this.id = id;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.tour=tour;
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
}
