package com.da.tourandroid.utils;

import com.da.tourandroid.model.TaiKhoan;

public class Common {

    public static String token;
    public static TaiKhoan taiKhoan;
    public static final String host="https://77e9-115-79-44-8.ap.ngrok.io/";

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

}
