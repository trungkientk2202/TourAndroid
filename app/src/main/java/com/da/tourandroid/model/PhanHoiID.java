package com.da.tourandroid.model;

public class PhanHoiID  {
    private long maTour;
    private String sdt;

    public PhanHoiID(long maTour, String sdt) {
        this.maTour = maTour;
        this.sdt = sdt;
    }

    public PhanHoiID() {
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
}
