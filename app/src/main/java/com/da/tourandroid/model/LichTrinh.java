package com.da.tourandroid.model;

public class LichTrinh {

    private LichTrinhID id;

    private String noiDungLichTrinh;

    private String thoiGianBatDau;

    public LichTrinh() {
    }

    public LichTrinh(LichTrinhID id, String noiDungLichTrinh, String thoiGianBatDau) {
        this.id = id;
        this.noiDungLichTrinh = noiDungLichTrinh;
        this.thoiGianBatDau = thoiGianBatDau;
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
}
