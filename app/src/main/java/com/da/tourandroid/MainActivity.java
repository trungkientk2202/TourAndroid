package com.da.tourandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.adapter.FeedbackAdapter;
import com.da.tourandroid.adapter.ViewPageAdapter;
import com.da.tourandroid.model.DiaDiem;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.LichTrinhID;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.PhanHoi;
import com.da.tourandroid.model.PhanHoiID;
import com.da.tourandroid.model.ThamGiaTour;
import com.da.tourandroid.model.ThamGiaTourID;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private RequestQueue requestQueue;
    private ArrayList<LichTrinh> lichTrinhs;
    private ArrayList<Tour> tours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue= Volley.newRequestQueue(MainActivity.this);
        createNotificationChannel();
        setNotification();
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        //Log.i("mode: ", Common.getMode()+"");
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_favourite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_invoice).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;

                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_home:
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.menu_favourite:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.menu_invoice:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.menu_account:
                        viewPager.setCurrentItem(3);
                        break;
                }

                return true;
            }
        });
    }

    private void setNotification() {
        lichTrinhs=new ArrayList<>();
        tours=new ArrayList<>();
        //thông báo các khách hàng tour sắp diễn ra
        if(Common.mode==2) {
            String url = Common.getHost() + "tgtour/findList/" + Common.getKhachHang().getSdt()+"/1";
            Log.i("url: ", url);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        //Log.i("response", response.toString());
                        //Log.i("length", response.length() + "");
                        for (int i = 0; i < response.length(); i++) {
                            ThamGiaTour thamGiaTour=new ThamGiaTour();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject objID = jsonObject.getJSONObject("id");
                                thamGiaTour.setId(new ThamGiaTourID(objID.getInt("maTour"),
                                        objID.getString("sdt")));
                                thamGiaTour.setCheckIn(jsonObject.getBoolean("checkIn"));
                                thamGiaTour.setGhiChu(jsonObject.getString("ghiChu"));
                                thamGiaTour.setDiaDiemDon(jsonObject.getString("diaDiemDon"));
                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                //Log.i("Diem den: ", tour.getDiemDen());
                                tour.setMoTa(objTour.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                tours.add(tour);
                                thamGiaTour.setTour(tour);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        Log.i("tours size:",tours.size()+"");
//                        long i=0;
                        // gửi các thông báo
                        for (Tour tour:tours) {
//                            i++;
                            Log.i("Ma tour:",tour.getMaTour()+"");
                            if(tour.getNgayBatDau().equals("null")){
                                Log.i("Ma tour null:",tour.getMaTour()+"");
                                break;
                            }
                            Common.getTours().offer(tour);
                            Log.i("ngay bat dau",tour.getNgayBatDau());
                            Intent intent =new Intent(MainActivity.this,ReminderBroadcast.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            PendingIntent pendingIntent;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                pendingIntent = PendingIntent.getBroadcast(
                                        getApplication(),
                                        0,
                                        intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
                                );
                            } else {
                                pendingIntent = PendingIntent.getBroadcast(
                                        getApplication(),
                                        0,
                                        intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );
                            }
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            //gửi trước 1 ngày
                            long time= 0;
                            try {
                                time = new SimpleDateFormat("yyyy-MM-dd").parse(tour.getNgayBatDau()).getTime()*1000-24*3600*1000;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.i("time send notify:",time+" "+tour.getMaTour());
                            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
                        }
                    }, error -> Log.i("err:", error.toString())) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " + Common.getToken());
                    return headers;
                }
            };
            requestQueue.add(request);

//            list các tour đang tham ra
            tours=new ArrayList<>();
            url = Common.getHost() + "tgtour/findList/" + Common.getKhachHang().getSdt()+"/2";
            Log.i("url: ", url);
            request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.i("response", response.toString());
                        Log.i("length", response.length() + "");
                        for (int i = 0; i < response.length(); i++) {
                            ThamGiaTour thamGiaTour=new ThamGiaTour();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                //Log.i("jsonObject", jsonObject.toString());
                                JSONObject objID = jsonObject.getJSONObject("id");
                                thamGiaTour.setId(new ThamGiaTourID(objID.getInt("maTour"), objID.getString("sdt")));
                                thamGiaTour.setCheckIn(jsonObject.getBoolean("checkIn"));
                                thamGiaTour.setGhiChu(jsonObject.getString("ghiChu"));
                                thamGiaTour.setDiaDiemDon(jsonObject.getString("diaDiemDon"));
                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                Log.i("Diem den: ", tour.getDiemDen());
                                tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                tours.add(tour);
                                thamGiaTour.setTour(tour);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("Tours size:",tours.size()+"");
                    }, error -> Log.i("err:", error.toString())) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " + Common.getToken());
                    return headers;
                }
            };
            requestQueue.add(request);

            //list lịch trình đang tham gia
            lichTrinhs=new ArrayList<>();
            for (Tour t:tours) {
                url = Common.getHost() + "lichTrinh/findByMaTour/" + t.getMaTour();
                Log.i("url: ", url);
                request = new JsonArrayRequest(Request.Method.GET, url, null,
                        response -> {
                            Log.i("response", response.toString());
                            Log.i("length", response.length() + "");
                            for (int i = 0; i < response.length(); i++) {
                                LichTrinh lichTrinh=new LichTrinh();
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Log.i("jsonObject", jsonObject.toString());
                                    JSONObject objID = jsonObject.getJSONObject("id");
                                    lichTrinh.setId(new LichTrinhID(objID.getInt("maTour"), objID.getInt("maDiaDiem"),objID.getInt("sttLichTrinh")));
                                    lichTrinh.setNoiDungLichTrinh(jsonObject.getString("noiDungLichTrinh"));
                                    lichTrinh.setThoiGianBatDau(jsonObject.getString("thoiGianBatDau"));

                                    JSONObject objTour = jsonObject.getJSONObject("tour");
                                    Tour tour = new Tour();
                                    tour.setMaTour(objTour.getInt("maTour"));
                                    tour.setDiemDen(objTour.getString("diemDen"));
                                    Log.i("Diem den: ", tour.getDiemDen());
                                    tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                                    tour.setDiemDi(objTour.getString("diemDi"));
                                    tour.setGia(objTour.getLong("gia"));
                                    tour.setTrangThai(objTour.getInt("trangThai"));
                                    tour.setImage(objTour.getString("image"));
                                    tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                    JSONObject object = objTour.getJSONObject("loaiTour");
                                    LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                    tour.setLoaiTour(loaiTour);
                                    lichTrinh.setTour(tour);

                                    JSONObject objDiaDiem = jsonObject.getJSONObject("diaDiem");
                                    DiaDiem diaDiem=new DiaDiem();
                                    diaDiem.setMaDiaDiem(objDiaDiem.getInt("maDiaDiem"));
                                    diaDiem.setTenDiaDiem(objDiaDiem.getString("tenDiaDiem"));
                                    diaDiem.setMoTa(objDiaDiem.getString("moTa"));
                                    diaDiem.setTinhThanh(objDiaDiem.getString("tinhThanh"));
                                    lichTrinh.setDiaDiem(diaDiem);

                                    lichTrinhs.add(lichTrinh);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            // gửi thông báo theo từng lịch trình
                            for (LichTrinh lichTrinh:lichTrinhs) {
                                if(lichTrinh.getThoiGianBatDau()==null){
                                    break;
                                }
                                Common.getLichTrinhs().offer(lichTrinh);
                                Intent intent =new Intent(MainActivity.this,ReminderBroadcast.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                PendingIntent pendingIntent;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    pendingIntent = PendingIntent.getBroadcast(
                                            getApplication(),
                                            0,
                                            intent,
                                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
                                    );
                                } else {
                                    pendingIntent = PendingIntent.getBroadcast(
                                            getApplication(),
                                            0,
                                            intent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                                }
                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                try {
                                    //gửi thông báo trước 4h khởi hành
                                    long time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lichTrinh.getThoiGianBatDau()).getTime()*1000-4*3600*1000;
                                    Log.i("lich trinh notify:",time+"");
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),pendingIntent);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, error -> Log.i("err:", error.toString())) {
                    /**
                     * Passing some request headers
                     */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + Common.getToken());
                        return headers;
                    }
                };
                requestQueue.add(request);
            }
        }

        //

    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TourReminderChannel";
            String description = "Channel for Tour Reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyTour", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}