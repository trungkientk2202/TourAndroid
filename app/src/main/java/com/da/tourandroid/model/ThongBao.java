package com.da.tourandroid.model;

public class ThongBao {
    private long id;
    private String loaiThongBao;
    private String thoiGian;
    private String noiDung;

    public ThongBao(long id, String loaiThongBao, String thoiGian, String noiDung) {
        this.id = id;
        this.loaiThongBao = loaiThongBao;
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
    }

    public ThongBao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoaiThongBao() {
        return loaiThongBao;
    }

    public void setLoaiThongBao(String loaiThongBao) {
        this.loaiThongBao = loaiThongBao;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
