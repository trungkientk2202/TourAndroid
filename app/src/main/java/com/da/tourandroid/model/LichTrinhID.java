package com.da.tourandroid.model;

public class LichTrinhID {

    private long maTour;

    private long maDiaDiem;

    private long sttLichTrinh;

    public LichTrinhID() {
    }

    public LichTrinhID(long maTour, long maDiaDiem, long sttLichTrinh) {
        this.maTour = maTour;
        this.maDiaDiem = maDiaDiem;
        this.sttLichTrinh = sttLichTrinh;
    }

    public long getMaTour() {
        return maTour;
    }

    public void setMaTour(long maTour) {
        this.maTour = maTour;
    }

    public long getMaDiaDiem() {
        return maDiaDiem;
    }

    public void setMaDiaDiem(long maDiaDiem) {
        this.maDiaDiem = maDiaDiem;
    }

    public long getSttLichTrinh() {
        return sttLichTrinh;
    }

    public void setSttLichTrinh(long sttLichTrinh) {
        this.sttLichTrinh = sttLichTrinh;
    }
}
