package com.da.tourandroid.model;

public class DienDan {
    private long id;
    private long maTour;
    private String sdt;
    private String noiDung;
    private String thoiGian;
    private boolean laHDV;
    private boolean thongBaoTuHDV;

    public DienDan() {
    }

    public DienDan(long id, long maTour, String sdt, String noiDung, String thoiGian, boolean laHDV,boolean thongBaoTuHDV) {
        this.id = id;
        this.maTour = maTour;
        this.sdt = sdt;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.laHDV = laHDV;
        this.thongBaoTuHDV= thongBaoTuHDV;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMaTour() {
        return maTour;
    }

    public void setMaTour(long maTour) {
        this.maTour = maTour;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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

    public void setLaHDV(boolean laHDV) {
        this.laHDV = laHDV;
    }

    public boolean isLaHDV() {
        return laHDV;
    }

    public boolean isThongBaoTuHDV() {
        return thongBaoTuHDV;
    }

    public void setThongBaoTuHDV(boolean thongBaoTuHDV) {
        this.thongBaoTuHDV = thongBaoTuHDV;
    }
}
