package com.da.tourandroid.utils;

import com.da.tourandroid.model.KhachHang;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.QuanLyTour;
import com.da.tourandroid.model.TaiKhoan;
import com.da.tourandroid.model.ThamGiaTour;
import com.da.tourandroid.model.Tour;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Common {

    public static String token;
    public static TaiKhoan taiKhoan;

    public static KhachHang khachHang;
    public static int mode;
    public static Queue<LichTrinh>lichTrinhs = new LinkedList<>();
    public static Queue<Tour> tours= new LinkedList<>();
    public static Tour tour;
    public static ThamGiaTour thamGiaTour;
    public static QuanLyTour quanLyTour;
    public static int detailMode;

    public static Queue<Integer> notify= new LinkedList<>();

//    public static final String host="https://7a5c-115-79-44-8.ap.ngrok.io";
    public static final String host="http://192.168.1.103:8080/";

    public static String title;

    public static String content;

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

    public static Queue<LichTrinh> getLichTrinhs() {
        return lichTrinhs;
    }

    public static void setLichTrinhs(Queue<LichTrinh> lichTrinhs) {
        Common.lichTrinhs = lichTrinhs;
    }

    public static Queue<Tour> getTours() {
        return tours;
    }

    public static void setTours(Queue<Tour> tours) {
        Common.tours = tours;
    }

    public static Tour getTour() {
        return tour;
    }

    public static void setTour(Tour tour) {
        Common.tour = tour;
    }

    public static int getDetailMode() {
        return detailMode;
    }

    public static void setDetailMode(int detailMode) {
        Common.detailMode = detailMode;
    }

    public static ThamGiaTour getThamGiaTour() {
        return thamGiaTour;
    }

    public static void setThamGiaTour(ThamGiaTour thamGiaTour) {
        Common.thamGiaTour = thamGiaTour;
    }

    public static QuanLyTour getQuanLyTour() {
        return quanLyTour;
    }

    public static void setQuanLyTour(QuanLyTour quanLyTour) {
        Common.quanLyTour = quanLyTour;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Common.title = title;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        Common.content = content;
    }

    public static Queue<Integer> getNotify() {
        return notify;
    }

    public static void setNotify(Queue<Integer> notify) {
        Common.notify = notify;
    }
}
