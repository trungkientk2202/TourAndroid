package com.da.tourandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.da.tourandroid.adapter.TimelineAdapter;
import com.da.tourandroid.adapter.TourAdapter;
import com.da.tourandroid.adapter.TourRecommendAdapter;
import com.da.tourandroid.adapter.UserFeedbackAdapter;
import com.da.tourandroid.model.DiaDiem;
import com.da.tourandroid.model.KhachHang;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.LichTrinhID;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.PhanHoi;
import com.da.tourandroid.model.PhanHoiID;
import com.da.tourandroid.model.ThamGiaTour;
import com.da.tourandroid.model.ThamGiaTourID;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourDetailsActivity extends AppCompatActivity {
    ImageView img_tour, img_saved, imageViewBack;
    TextView txt_name, txt_price,thoiGianBatDau,textView_descDetails;
    RatingBar rb;
    ArrayList<LichTrinh> listTimeline;
    ArrayList<Tour> listRelatedTour;
    ArrayList<PhanHoi> listFeedback;
    RecyclerView relatedTourRecycle, timelineRecycleView, feedbackRecycleView;
    TourRecommendAdapter tourRecommendAdapter;
    TimelineAdapter timelineAdapter;
    UserFeedbackAdapter userFeedbackAdapter;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        requestQueue= Volley.newRequestQueue(TourDetailsActivity.this);
        map();
        dataInit();

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void map() {
        img_tour = findViewById(R.id.img_tour);
        txt_name = findViewById(R.id.txt_name_tour);
        timelineRecycleView = findViewById(R.id.lv_comment);
        feedbackRecycleView = findViewById(R.id.feedback_tour_recycler);
        rb = findViewById(R.id.ratingBar3);
        img_saved = findViewById(R.id.img_save_location);
        txt_price = findViewById(R.id.txt_tour_price);
        relatedTourRecycle = findViewById(R.id.related_tour_recycler);
        imageViewBack = findViewById(R.id.imageView_back);
        thoiGianBatDau=findViewById(R.id.thoiGianBatDau);
        textView_descDetails=findViewById(R.id.textView_descDetails);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataTimeline(int id_res) {
        listTimeline.clear();
        String url = Common.getHost() + "lichTrinh/findByMaTour/" + id_res;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        LichTrinh lichTrinh=new LichTrinh();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject objID = jsonObject.getJSONObject("id");
                            lichTrinh.setId(new LichTrinhID(objID.getInt("maTour"), objID.getInt("maDiaDiem"),objID.getInt("sttLichTrinh")));
                            lichTrinh.setNoiDungLichTrinh(jsonObject.getString("noiDungLichTrinh"));
                            lichTrinh.setThoiGianBatDau(jsonObject.getString("thoiGianBatDau"));
                            lichTrinh.setTrangThai(jsonObject.getInt("trangThai"));

                            JSONObject objTour = jsonObject.getJSONObject("tour");
                            Tour tour = new Tour();
                            tour.setMaTour(objTour.getInt("maTour"));
                            tour.setDiemDen(objTour.getString("diemDen"));
                            tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                            tour.setDiemDi(objTour.getString("diemDi"));
                            tour.setGia(objTour.getLong("gia"));
                            tour.setTrangThai(objTour.getInt("trangThai"));
                            tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                            tour.setImage(objTour.getString("image"));
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

                            listTimeline.add(lichTrinh);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    timelineAdapter = new TimelineAdapter(this, R.layout.items_timeline, listTimeline);
                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    timelineRecycleView.setLayoutManager(layoutManager1);
                    timelineRecycleView.setAdapter(timelineAdapter);
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
        timelineAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataRelatedTours(int id_res) {
        listRelatedTour.clear();

        Tour tour = new Tour();
        tour.setMaTour(0);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour= new LoaiTour(0, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour);
        Tour tour1 = new Tour();
        tour.setMaTour(1);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour1= new LoaiTour(1, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour1);
        Tour tour2 = new Tour();
        tour.setMaTour(2);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour2= new LoaiTour(2, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour2);

        listRelatedTour.add(tour);
        listRelatedTour.add(tour1);
        listRelatedTour.add(tour2);

        tourRecommendAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataFeedbacks(int id_res) {
        listFeedback.clear();
        String url = Common.getHost() + "phanHoi/findByMaTour/" + id_res;
        Toast.makeText(TourDetailsActivity.this,url,Toast.LENGTH_LONG).show();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.i("response phan hoi:",response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        PhanHoi phanHoi=new PhanHoi();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject objID = jsonObject.getJSONObject("id");
                            phanHoi.setId(new PhanHoiID(objID.getInt("maTour"), objID.getString("sdt")));
                            phanHoi.setNoiDung(jsonObject.getString("noiDung"));
                            phanHoi.setThoiGian(jsonObject.getString("thoiGian"));

                            JSONObject objTour = jsonObject.getJSONObject("tour");
                            Tour tour = new Tour();
                            tour.setMaTour(objTour.getInt("maTour"));
                            tour.setDiemDen(objTour.getString("diemDen"));
                            tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                            tour.setDiemDi(objTour.getString("diemDi"));
                            tour.setGia(objTour.getLong("gia"));
                            tour.setTrangThai(objTour.getInt("trangThai"));
                            tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                            tour.setImage(objTour.getString("image"));
                            JSONObject object = objTour.getJSONObject("loaiTour");
                            LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                            tour.setLoaiTour(loaiTour);
                            phanHoi.setTour(tour);

                            JSONObject objKhachHang = jsonObject.getJSONObject("khachHang");
                            KhachHang khachHang=new KhachHang();
                            khachHang.setSdt(objKhachHang.getString("sdt"));
                            khachHang.setTen(objKhachHang.getString("ten"));
                            khachHang.setMatKhau(objKhachHang.getString("matKhau"));
                            khachHang.setPhai(objKhachHang.getBoolean("phai"));
                            khachHang.setNgaySinh( !objKhachHang.getString("ngaySinh").equals("null") ?new SimpleDateFormat("yyyy-MM-dd").parse(objKhachHang.getString("ngaySinh")):null);
                            khachHang.setZalo(objKhachHang.getString("zalo"));
                            phanHoi.setKhachHang(khachHang);

                            listFeedback.add(phanHoi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    feedbackRecycleView.setLayoutManager(layoutManager2);
                    userFeedbackAdapter = new UserFeedbackAdapter(this, R.layout.items_user_feedback, listFeedback);
                    feedbackRecycleView.setAdapter(userFeedbackAdapter);
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
        userFeedbackAdapter.notifyDataSetChanged();
    }

    private void dataInit() {
        listTimeline = new ArrayList<>();
        listRelatedTour = new ArrayList<>();
        listFeedback = new ArrayList<>();

        //Get restaurant info
        Intent intent = getIntent();
        float rating = intent.getFloatExtra("rating", 4);

        rb.setRating(rating);


        //Set tour info
        txt_name.setText(Common.getTour().getDiemDen());
        Glide.with(this)
                .load(Common.getTour().getImage())
                .into(img_tour);
        txt_price.setText(Common.getTour().getGia()+"");
        thoiGianBatDau.setText(Common.getTour().getNgayBatDau());
        textView_descDetails.setText(Common.getTour().getMoTa());

        //Set timeline info
        timelineAdapter = new TimelineAdapter(this, R.layout.items_timeline, listTimeline);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        timelineRecycleView.setLayoutManager(layoutManager1);
        getDataTimeline((int) Common.getTour().getMaTour());
        timelineRecycleView.setAdapter(timelineAdapter);

        //Set other tours info
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedTourRecycle.setLayoutManager(layoutManager);
        tourRecommendAdapter = new TourRecommendAdapter(this, R.layout.items_recommended_tour, listRelatedTour);
        getDataRelatedTours((int) Common.getTour().getMaTour());
        relatedTourRecycle.setAdapter(tourRecommendAdapter);

        //Set feedbacks info
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        feedbackRecycleView.setLayoutManager(layoutManager2);
        userFeedbackAdapter = new UserFeedbackAdapter(this, R.layout.items_user_feedback, listFeedback);
        getDataFeedbacks((int) Common.getTour().getMaTour());
        feedbackRecycleView.setAdapter(userFeedbackAdapter);

        timelineRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        feedbackRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}
