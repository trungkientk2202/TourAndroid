package com.da.tourandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.da.tourandroid.db.Database;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        transparentStatusAndNavigation(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

        dataInit();
    }

    private void dataInit() {
        databaseInit();

        // run once
        dataDelete();
//        dataInsert();
    }

    public static void transparentStatusAndNavigation(Activity activity) {

        Window window = activity.getWindow();

        // make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            window.getDecorView().setSystemUiVisibility(visibility);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            int windowManager = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            windowManager = windowManager | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            setWindowFlag(window, windowManager, false);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

    }

    private static void setWindowFlag(Window window, final int bits, boolean on) {
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    private void databaseInit() {
        //init database
        database = new Database(this, "tour.sqlite", null, 1);
        //init TaiKhoan table
        database.QueryData("CREATE TABLE IF NOT EXISTS TaiKhoan(" +
                "SDT NCHAR(10) PRIMARY KEY," +
                "Ten VARCHAR(50)," +
                "MatKhau VARCHAR(50)," +
                "Phai bit," +
                "NgaySinh date," +
                "Zalo NCHAR(10))");
        //init KhachHang table
        database.QueryData("CREATE TABLE IF NOT EXISTS KhachHang(" +
                "SDT NCHAR(10) PRIMARY KEY," +
                "Ten VARCHAR(50)," +
                "MatKhau VARCHAR(50)," +
                "Phai bit," +
                "NgaySinh date," +
                "Zalo NCHAR(10))");
        //init token table
        database.QueryData("CREATE TABLE IF NOT EXISTS Token(" +
                "strtoken VARCHAR(300) PRIMARY KEY)");
    }

    private void dataInsert() {

    }

    private void dataDelete() {
        //TaiKhoan
        database.QueryData("DELETE FROM TaiKhoan");
        //KhachHang
        database.QueryData("DELETE FROM KhachHang");
        //Token
        database.QueryData("DELETE FROM Token");
    }
}