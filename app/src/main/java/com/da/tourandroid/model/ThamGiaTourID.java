package com.da.tourandroid.model;

public class ThamGiaTourID {
    private long maTour;
    private String sdt;

    public ThamGiaTourID(long maTour, String sdt) {
        this.maTour = maTour;
        this.sdt = sdt;
    }

    public ThamGiaTourID() {
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
