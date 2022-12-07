package com.da.tourandroid.model;

public class LichTrinh {

    private LichTrinhID id;

    private String noiDungLichTrinh;

    private String thoiGianBatDau;
    private int trangThai;

    private Tour tour;

    private DiaDiem diaDiem;

    public LichTrinh() {
    }

    public LichTrinh(LichTrinhID id, String noiDungLichTrinh, String thoiGianBatDau,int trangThai,Tour tour,DiaDiem diaDiem) {
        this.id = id;
        this.noiDungLichTrinh = noiDungLichTrinh;
        this.thoiGianBatDau = thoiGianBatDau;
        this.tour=tour;
        this.diaDiem=diaDiem;
        this.trangThai=trangThai;
    }

    public LichTrinhID getId() {
        return id;
    }

    public void setId(LichTrinhID id) {
        this.id = id;
    }

    public String getNoiDungLichTrinh() {
        return noiDungLichTrinh;
    }

    public void setNoiDungLichTrinh(String noiDungLichTrinh) {
        this.noiDungLichTrinh = noiDungLichTrinh;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public DiaDiem getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(DiaDiem diaDiem) {
        this.diaDiem = diaDiem;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
