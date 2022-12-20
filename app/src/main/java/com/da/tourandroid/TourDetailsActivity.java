package com.da.tourandroid;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.da.tourandroid.adapter.TimelineAdapter;
import com.da.tourandroid.adapter.TourRecommendAdapter;
import com.da.tourandroid.adapter.UserAdapter;
import com.da.tourandroid.adapter.UserFeedbackAdapter;
import com.da.tourandroid.adapter.UserSearchAdapter;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TourDetailsActivity extends AppCompatActivity {
    ImageView img_tour, imageViewBack;
    TextView txt_name, txt_price,thoiGianBatDau,textView_descDetails;
    RatingBar rb;
    ArrayList<LichTrinh> listTimeline;
    ArrayList<Tour> listRelatedTour;
    ArrayList<PhanHoi> listFeedback;
    ArrayList<ThamGiaTour> thamGiaTours;
    ArrayList<KhachHang> listUserSearch;
    RecyclerView relatedTourRecycle, timelineRecycleView, feedbackRecycleView, userRecycleView;
    TourRecommendAdapter tourRecommendAdapter;
    TimelineAdapter timelineAdapter;
    UserFeedbackAdapter userFeedbackAdapter;
    UserAdapter userAdapter;
    AppCompatButton btnAddUser;
    AppCompatButton btnFeedback;
    AppCompatButton btnAction;

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        requestQueue= Volley.newRequestQueue(TourDetailsActivity.this);
        map();
        dataInit();
        Glide.with(TourDetailsActivity.this)
                .load(Common.getTour().getImage())
                .into(img_tour);

        img_tour.setImageResource(R.drawable.da_nang);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void map() {
        img_tour = findViewById(R.id.imgView_tour);
        txt_name = findViewById(R.id.txt_name_tour);
        timelineRecycleView = findViewById(R.id.lv_comment);
        feedbackRecycleView = findViewById(R.id.feedback_tour_recycler);
        rb = findViewById(R.id.ratingBar3);
        btnAction = findViewById(R.id.btnAction);
        txt_price = findViewById(R.id.txt_tour_price);
        relatedTourRecycle = findViewById(R.id.related_tour_recycler);
        imageViewBack = findViewById(R.id.imageView_back);
        thoiGianBatDau=findViewById(R.id.thoiGianBatDau);
        textView_descDetails=findViewById(R.id.textView_descDetails);
        btnFeedback = findViewById(R.id.btn_feedback);
        btnAddUser = findViewById(R.id.btn_add_user);
        userRecycleView = findViewById(R.id.user_tour_recycler);
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
                            tour.setMoTa(objTour.getString("moTa"));
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
                            diaDiem.setMoTa( objDiaDiem.getString("moTa"));
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
        tourRecommendAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataFeedbacks(int id_res) {
        listFeedback.clear();
        String url = Common.getHost() + "phanHoi/findByMaTour/" + id_res;
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
                            tour.setMoTa( objTour.getString("moTa"));
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

    @SuppressLint("NotifyDataSetChanged")
    private void getDataUsers(int id) {
        thamGiaTours.clear();
        String url = Common.getHost() + "tgtour/findByMaTour/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        ThamGiaTour thamGiaTour=new ThamGiaTour();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            JSONObject objID = jsonObject.getJSONObject("id");
                            thamGiaTour.setId(new ThamGiaTourID(objID.getInt("maTour"),
                                    objID.getString("sdt")));
                            thamGiaTour.setCheckIn(jsonObject.getBoolean("checkIn"));
                            thamGiaTour.setGhiChu(jsonObject.getString("ghiChu"));
                            thamGiaTour.setDiemHen(jsonObject.getString("diemHen"));

                            JSONObject objKhachHang = jsonObject.getJSONObject("khachHang");
                            KhachHang khachHang=new KhachHang();
                            khachHang.setSdt(objKhachHang.getString("sdt"));
                            khachHang.setTen(objKhachHang.getString("ten"));
                            khachHang.setMatKhau(objKhachHang.getString("matKhau"));
                            khachHang.setPhai(objKhachHang.getBoolean("phai"));
                            khachHang.setNgaySinh( !objKhachHang.getString("ngaySinh").equals("null") ?new SimpleDateFormat("yyyy-MM-dd").parse(objKhachHang.getString("ngaySinh")):null);
                            khachHang.setZalo(objKhachHang.getString("zalo"));
                            thamGiaTour.setKhachHang(khachHang);

                            thamGiaTours.add(thamGiaTour);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    userRecycleView.setLayoutManager(layoutManager2);
                    userAdapter = new UserAdapter(this, R.layout.items_tour_recycler, thamGiaTours);
                    userRecycleView.setAdapter(userAdapter);
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

        userAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataUsersSearch(KhachHang khachHang) {
        listUserSearch.clear();

        listUserSearch.add(khachHang);

        userAdapter.notifyDataSetChanged();
    }

    private void dataInit() {
        listTimeline = new ArrayList<>();
        listRelatedTour = new ArrayList<>();
        listFeedback = new ArrayList<>();
        thamGiaTours = new ArrayList<>();
        listUserSearch = new ArrayList<>();
        KhachHang khachHang=new KhachHang();
        //Get tour info
        Intent intent = getIntent();
        float rating = intent.getFloatExtra("rating", 4);

        rb.setRating(rating);

        switch (Common.getDetailMode()){
            case 1:
                btnAction.setVisibility(View.INVISIBLE);
                btnAddUser.setVisibility(View.INVISIBLE);
                btnFeedback.setVisibility(View.INVISIBLE);
                break;
            case 2:
                btnAction.setVisibility(View.VISIBLE);
                btnAddUser.setVisibility(View.VISIBLE);
                if(Common.getMode()==1) {
                    btnAction.setText("Start tour");
                    btnAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(Common.getQuanLyTour().getTour()!=null){
                                Toast.makeText(view.getContext(), "You are on another tour, can't start a new tour!", Toast.LENGTH_LONG).show();
                            }else{
                                String url = Common.getHost() + "tour/changeStatus/" + Common.getTour().getMaTour();
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                        response -> {
                                            try {
                                                JSONObject objID = response.getJSONObject("maTour");
                                                btnAction.setVisibility(View.GONE);
                                                Toast.makeText(view.getContext(), "Start tour successfully!", Toast.LENGTH_LONG).show();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
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
                    });
                }else{
                    btnAction.setVisibility(View.INVISIBLE);
                }
                break;
            case 3:
                btnAddUser.setVisibility(View.VISIBLE);
                btnAction.setVisibility(View.VISIBLE);
                if(Common.getMode()==1) {
                    btnAction.setText("Finish tour");
                    btnAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = Common.getHost() + "tour/changeStatus/"+Common.getTour().getMaTour();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                    response -> {
                                        try {

                                            btnAction.setVisibility(View.GONE);
                                            Toast.makeText(view.getContext(),"Finish tour successfully!",Toast.LENGTH_LONG).show();
                                            JSONObject objID = response.getJSONObject("maTour");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
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
                    });
                }else{

                    if(Common.getThamGiaTour().isCheckIn()){
                        btnAction.setText("Checked in");
                        btnAction.setBackgroundColor(Color.GRAY);
                    }else {
                        btnAction.setBackgroundColor(Color.rgb(26,115,232));
                        btnAction.setText("Check in");
                        btnAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = Common.getHost() + "tgtour/checkIn/" + Common.getThamGiaTour().getTour().getMaTour() + "/" + Common.getThamGiaTour().getKhachHang().getSdt();
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                        response -> {
                                            try {
                                                JSONObject objID = response.getJSONObject("id");
                                                btnAction.setBackgroundColor(Color.GRAY);
                                                Common.getThamGiaTour().setCheckIn(true);
                                                btnAction.setText("Checked in");
                                                Toast.makeText(view.getContext(), "Check in successfully!", Toast.LENGTH_LONG).show();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
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
                        });
                    }
                }
                break;
            case 4:
                btnAddUser.setVisibility(View.INVISIBLE);
                btnAction.setVisibility(View.INVISIBLE);
                btnAddUser.setVisibility(View.INVISIBLE);
                break;
        }
        if(Common.getMode()==1){
            if(Common.getDetailMode()==1 ||Common.getDetailMode()==4){
                btnAddUser.setVisibility(View.INVISIBLE);
            }else{
                btnAddUser.setVisibility(View.VISIBLE);
            }
            btnFeedback.setVisibility(View.INVISIBLE);
        }else{
            btnFeedback.setVisibility(View.VISIBLE);
            btnAddUser.setVisibility(View.INVISIBLE);
            if(Common.getTour().getTrangThai()==1){
                // tour chua bat dau, an button feedback
                btnFeedback.setVisibility(View.INVISIBLE);
            }
        }
        //Set tour info
        txt_name.setText(Common.getTour().getDiemDen());

        thoiGianBatDau.setText(Common.getTour().getNgayBatDau());
        textView_descDetails.setText(Common.getTour().getMoTa());
        txt_price.setText(Common.getTour().getGia()+"");

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

        //Set users info
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        userRecycleView.setLayoutManager(layoutManager3);
        userAdapter = new UserAdapter(this, R.layout.items_tour_recycler, thamGiaTours);
        getDataUsers((int) Common.getTour().getMaTour());
        userRecycleView.setAdapter(userAdapter);


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

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TourDetailsActivity.this);
                dialog.setContentView(R.layout.dialog_feedback);

                EditText editTextName = dialog.findViewById(R.id.editText_addTask);
                Button buttonAdd = dialog.findViewById(R.id.button_addTask);
                Button buttonCancel = dialog.findViewById(R.id.button_cancel);

                buttonAdd.setOnClickListener(view -> {
                    String name = editTextName.getText().toString();

                    if (name.equals(""))
                        Toast.makeText(getApplicationContext(), "This field is require!", Toast.LENGTH_SHORT).show();
                    else {
                        String json="{\"maTour\":\""+Common.getTour().getMaTour()+"\","
                                +"\"sdt\":\""+Common.getKhachHang().getSdt()+"\","
                                +"\"noiDung\":\""+name+"\","
                                +"\"thoiGian\":\""+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+"\"}";
                        String url = Common.getHost() + "phanHoi/add";
                        try {
                            JSONObject req=new JSONObject(json);
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, req,
                                    response -> {
                                        try {
                                            if(!response.get("noiDung").equals(null)){
                                                //add thành công
                                                Toast.makeText(view.getContext(), "Add feedback successfully", Toast.LENGTH_LONG).show();
                                                khachHang.setSdt(null);
                                                //reset value search
                                                editTextName.setText("");
                                                //get feedback
                                                getDataFeedbacks((int) Common.getTour().getMaTour());
                                            }else{
                                                Toast.makeText(view.getContext(), "You already added a feedback!", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            Log.i("e:",e.toString());
                                            e.printStackTrace();
                                        }
                                    }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        getDataFeedbacks((int) Common.getTour().getMaTour());
                    }
                });

                buttonCancel.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TourDetailsActivity.this);
                dialog.setContentView(R.layout.dialog_user);

                EditText editTextName = dialog.findViewById(R.id.editText_phone);
                AppCompatButton buttonAdd = dialog.findViewById(R.id.button_addUser);
                AppCompatButton buttonCancel = dialog.findViewById(R.id.button_cancel);
                ImageView imageViewSearch = dialog.findViewById(R.id.imageView_search);
                RecyclerView userSearchRecycleView = dialog.findViewById(R.id.user_recycler);



                RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false);
                userSearchRecycleView.setLayoutManager(layoutManager4);
                //search member
                imageViewSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userSearchRecycleView.setAdapter(null);
                        khachHang.setSdt(null);
                        if (!editTextName.getText().equals("")) {
                            String url = Common.getHost() + "khachHang/find/" + editTextName.getText();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                    response -> {
                                        try {
                                            khachHang.setSdt(response.getString("sdt"));
                                            khachHang.setTen(response.getString("ten"));
                                            khachHang.setMatKhau(response.getString("matKhau"));
                                            khachHang.setPhai(response.getBoolean("phai"));
                                            khachHang.setNgaySinh(!response.getString("ngaySinh").equals("null") ?new SimpleDateFormat("yyyy-MM-dd").parse(response.getString("ngaySinh")):null);
                                            khachHang.setZalo(response.getString("zalo"));

                                            //display khach hang
                                            UserSearchAdapter userSearchAdapter = new UserSearchAdapter(dialog.getContext(), R.layout.items_search, listUserSearch);
                                            getDataUsersSearch(khachHang);
                                            userSearchRecycleView.setAdapter(userSearchAdapter);
                                        } catch (JSONException | ParseException e) {
                                            Toast.makeText(TourDetailsActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
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
                });


                buttonAdd.setOnClickListener(view -> {
                    if(khachHang.getSdt()==null){
                        return;
                    }
                   String json="{\"maTour\":\""+Common.getTour().getMaTour()+"\","
                           +"\"sdt\":\""+khachHang.getSdt()+"\","
                           +"\"checkIn\":false,"
                           +"\"ghiChu\":\"add new member\","
                           +"\"diaDiemDon\":\"\","
                           +"\"vitri\":\"\"}";
                   String url = Common.getHost() + "tgtour/add";
                    try {
                        JSONObject req=new JSONObject(json);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, req,
                                response -> {
                                    try {
                                        if(!response.get("id").equals(null)){
                                            //add thành công
                                            Toast.makeText(view.getContext(), "Add user successfully", Toast.LENGTH_LONG).show();
                                            khachHang.setSdt(null);
                                            //reset value search
                                            editTextName.setText("");
                                            //get list user
                                            getDataUsers((int) Common.getTour().getMaTour());
                                        }else{
                                            Toast.makeText(view.getContext(), "Member is available in the tour!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.i("e:",e.toString());
                                        e.printStackTrace();
                                    }
                                }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
                getDataUsers((int) Common.getTour().getMaTour());
                buttonCancel.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });
    }
}
