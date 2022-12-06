package com.da.tourandroid.utils;

import com.da.tourandroid.model.KhachHang;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.TaiKhoan;
import com.da.tourandroid.model.Tour;

import java.util.ArrayList;

public class Common {

    public static String token;
    public static TaiKhoan taiKhoan;

    public static KhachHang khachHang;
    public static int mode;
    public static Tour tour;
    public static LichTrinh lichTrinh;
//    public static final String host="https://6d06-115-79-44-8.ap.ngrok.io/";
    public static final String host="http://192.168.1.106:8080/";

    public Common() {
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Common.token = token;
    }

    public static TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public static void setTaiKhoan(TaiKhoan taiKhoan) {
        Common.taiKhoan = taiKhoan;
    }

    public static String getHost() {
        return host;
    }

    public static KhachHang getKhachHang() {
        return khachHang;
    }

    public static void setKhachHang(KhachHang khachHang) {
        Common.khachHang = khachHang;
    }

    public static int getMode() {
        return mode;
    }

    public static void setMode(int mode) {
        Common.mode = mode;
    }

    public static Tour getTour() {
        return tour;
    }

    public static void setTour(Tour tour) {
        Common.tour = tour;
    }

    public static LichTrinh getLichTrinh() {
        return lichTrinh;
    }

    public static void setLichTrinh(LichTrinh lichTrinh) {
        Common.lichTrinh = lichTrinh;
    }
}
